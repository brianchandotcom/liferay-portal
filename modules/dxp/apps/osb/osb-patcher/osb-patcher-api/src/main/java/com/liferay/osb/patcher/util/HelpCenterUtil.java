/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.util;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.osb.patcher.configuration.PatcherConfiguration;
import com.liferay.osb.patcher.constants.HelpCenterConstants;
import com.liferay.osb.patcher.model.PatcherBuild;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.net.HttpURLConnection;

import java.nio.channels.Channels;

import java.util.Map;

/**
 * @author Zsolt Balogh
 */
public class HelpCenterUtil {

	public static void addAttachmentComment(
			String fileName, PatcherBuild patcherBuild)
		throws Exception {

		PatcherConfiguration patcherConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				PatcherConfiguration.class, patcherBuild.getCompanyId());

		Blob blob = _getGoogleCloudFileObject(
			patcherConfiguration.googleCloudHotfixBucket(), fileName,
			patcherBuild);

		String fileSize = String.valueOf(blob.getSize());

		String md5Checksum = StringPool.BLANK;

		try (InputStream fileInputStream = Channels.newInputStream(
				blob.reader())) {

			md5Checksum = getMD5Checksum(fileInputStream);
		}
		catch (Exception exception) {
			throw new Exception(
				"Unable to calculate MD5 checksum for GCS file", exception);
		}

		Http.Options options = _initOptions(
			true,
			HashMapBuilder.put(
				"Content-Type", ContentTypes.APPLICATION_JSON
			).put(
				"Origin", patcherConfiguration.supportLiferayLFUURL()
			).build(),
			patcherConfiguration);

		options.setBody(
			JSONUtil.put(
				"fileName", fileName
			).put(
				"fileSize", fileSize
			).put(
				"md5Checksum", md5Checksum
			).put(
				"ticketId", patcherBuild.getSupportTicket()
			).put(
				"type", "hotfix"
			).toString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(
			StringBundler.concat(
				patcherConfiguration.supportLiferayLFUURL(),
				StringPool.FORWARD_SLASH,
				patcherConfiguration.
					supportLiferayTicketAttachmentAPIEndpoint()));
		options.setPost(true);

		String responseString = _sendRequest(options);

		if (Validator.isNull(responseString)) {
			throw new PortalException(
				"failed-to-connect-to-the-large-file-uploader");
		}

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
			responseString);

		String gcsSessionURL = responseJSONObject.getString(
			"gcsSessionURL", StringPool.BLANK);

		try (InputStream fileInputStream = Channels.newInputStream(
				blob.reader())) {

			uploadAttachment(fileInputStream, fileName, gcsSessionURL);
		}
		catch (Exception exception) {
			throw new Exception("Unable to process GCS file", exception);
		}

		long ticketAttachmentId = responseJSONObject.getLong(
			"ticketAttachmentId", 0);

		completeUpload(ticketAttachmentId, patcherConfiguration);
	}

	public static long fetchAccountEntryId(
			String accountEntryCode, long companyId)
		throws Exception {

		PatcherConfiguration patcherConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				PatcherConfiguration.class, companyId);

		Http.Options options = _initOptions(
			true,
			HashMapBuilder.put(
				HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
			).build(),
			patcherConfiguration);

		options.setLocation(
			StringBundler.concat(
				patcherConfiguration.supportLiferayURL(),
				StringPool.FORWARD_SLASH,
				patcherConfiguration.supportLiferayAccountSearchAPIEndpoint(),
				StringPool.APOSTROPHE + accountEntryCode +
					StringPool.APOSTROPHE));
		options.setPost(false);

		String responseString = _sendRequest(options);

		if (Validator.isNull(responseString)) {
			return 0;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			responseString);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		if (itemsJSONArray.length() == 0) {
			return 0;
		}

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			if (itemJSONObject.has("code")) {
				String code = itemJSONObject.getString("code");

				if (code.equals(accountEntryCode)) {
					return itemJSONObject.getLong("id");
				}
			}
		}

		return 0;
	}

	protected static void completeUpload(
			long ticketAttachmentId, PatcherConfiguration patcherConfiguration)
		throws Exception {

		Http.Options options = _initOptions(
			true,
			HashMapBuilder.put(
				"Content-Type", ContentTypes.APPLICATION_JSON
			).build(),
			patcherConfiguration);

		options.setBody(
			JSONUtil.put(
				"commentBody", HelpCenterConstants.HELP_CENTER_UPLOAD_COMMENT
			).toString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(
			String.format(
				"%s/ticket-attachments/%d/complete-upload",
				patcherConfiguration.supportLiferayLFUURL(),
				ticketAttachmentId));
		options.setPost(true);

		_sendRequest(options);
	}

	protected static String getAuthenticationToken(
			PatcherConfiguration patcherConfiguration)
		throws Exception {

		if (System.currentTimeMillis() < _tokenExpirationTime) {
			return _accessToken;
		}

		Http.Options options = _initOptions(
			false,
			HashMapBuilder.put(
				"Content-Type", ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED
			).build(),
			patcherConfiguration);

		options.addPart(
			"client_id", patcherConfiguration.supportLiferayAPIClientId());
		options.addPart(
			"client_secret",
			patcherConfiguration.supportLiferayAPIClientSecret());
		options.addPart("grant_type", "client_credentials");
		options.setLocation(
			patcherConfiguration.supportLiferayURL() + "/o/oauth2/token");
		options.setPost(true);

		String responseString = _sendRequest(options);

		if (Validator.isNull(responseString)) {
			throw new PortalException(
				"failed-to-connect-to-the-authentication-service");
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			responseString);

		String accessToken = jsonObject.getString(
			"access_token", StringPool.BLANK);

		long expiresIn = jsonObject.getLong("expires_in", 0);

		_accessToken = accessToken;
		_tokenExpirationTime =
			System.currentTimeMillis() + ((expiresIn - 60) * 1000);

		return accessToken;
	}

	protected static String getMD5Checksum(InputStream fileInputStream) {
		return DigesterUtil.digestHex(DigesterUtil.MD5, fileInputStream);
	}

	protected static void uploadAttachment(
			InputStream fileInputStream, String fileName, String gcsSessionURL)
		throws Exception {

		Http.Options options = _initOptions(
			false,
			HashMapBuilder.put(
				"Content-Type", ContentTypes.APPLICATION_OCTET_STREAM
			).build(),
			null);

		options.addInputStreamPart(
			"file", fileName, fileInputStream,
			ContentTypes.APPLICATION_OCTET_STREAM);
		options.setLocation(gcsSessionURL);
		options.setPut(true);

		String responseString = _sendRequest(options);

		if (Validator.isNull(responseString)) {
			throw new PortalException("failed-to-upload-file");
		}
	}

	private static Blob _getGoogleCloudFileObject(
			String bucketName, String fileName, PatcherBuild patcherBuild)
		throws Exception {

		StorageOptions storageOptions = StorageOptions.getDefaultInstance();

		Storage storage = storageOptions.getService();

		BlobId blobId = BlobId.of(bucketName, patcherBuild.getFileName());

		Blob blob = storage.get(blobId);

		if (blob == null) {
			throw new PortalException(
				LanguageUtil.format(
					LocaleUtil.getMostRelevantLocale(),
					"file-x-was-not-found-in-the-x-gcs-bucket",
					new Object[] {fileName, bucketName}));
		}

		return blob;
	}

	private static Http.Options _initOptions(
			boolean authenticate, Map<String, String> headers,
			PatcherConfiguration patcherConfiguration)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.USER_AGENT, _PATCHER_USER_AGENT);

		if (authenticate) {
			options.addHeader(
				"Authorization",
				"Bearer " + getAuthenticationToken(patcherConfiguration));
		}

		for (Map.Entry<String, String> header : headers.entrySet()) {
			options.addHeader(header.getKey(), header.getValue());
		}

		return options;
	}

	private static String _sendRequest(Http.Options options) throws Exception {
		String responseString = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (responseCode != HttpURLConnection.HTTP_OK) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Response code ", responseCode, ": ", responseString));
			}

			return null;
		}

		return responseString;
	}

	private static final String _PATCHER_USER_AGENT = "OSB Patcher Portal/7.4";

	private static final Log _log = LogFactoryUtil.getLog(HelpCenterUtil.class);

	private static String _accessToken;
	private static long _tokenExpirationTime;

}