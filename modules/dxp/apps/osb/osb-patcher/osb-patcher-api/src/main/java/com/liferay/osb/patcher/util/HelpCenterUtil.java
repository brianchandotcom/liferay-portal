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
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.channels.Channels;

import org.apache.commons.io.IOUtils;

/**
 * @author Zsolt Balogh
 */
public class HelpCenterUtil {

	public static String addAttachmentComment(
			String fileName, PatcherBuild patcherBuild)
		throws Exception {

		StorageOptions storageOptions = StorageOptions.getDefaultInstance();

		Storage storage = storageOptions.getService();

		PatcherConfiguration patcherConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				PatcherConfiguration.class, patcherBuild.getCompanyId());

		BlobId blobId = BlobId.of(
			patcherConfiguration.googleCloudHotfixBucket(),
			patcherBuild.getFileName());

		Blob blob = storage.get(blobId);

		if (blob == null) {
			throw new PortalException(
				LanguageUtil.format(
					LocaleUtil.getMostRelevantLocale(),
					"file-x-was-not-found-in-the-x-gcs-bucket",
					new Object[] {
						fileName, patcherConfiguration.googleCloudHotfixBucket()
					}));
		}

		String fileSize = String.valueOf(blob.getSize());

		String md5Checksum = StringPool.BLANK;

		try (InputStream fileInputStream = Channels.newInputStream(
				blob.reader())) {

			md5Checksum = getMD5Checksum(fileInputStream);
		}
		catch (Exception exception) {
			throw new Exception(
				"Error processing GCS file for MD5 calculation", exception);
		}

		String body = JSONUtil.put(
			"fileName", fileName
		).put(
			"fileSize", fileSize
		).put(
			"md5Checksum", md5Checksum
		).put(
			"ticketId", patcherBuild.getSupportTicket()
		).put(
			"type", "hotfix"
		).toString();

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.USER_AGENT, _PATCHER_USER_AGENT);

		options.addHeader(
			"Authorization",
			"Bearer " + getAuthenticationToken(patcherBuild.getCompanyId()));

		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);

		options.addHeader(
			"Origin", patcherConfiguration.supportLiferayLfuURL());

		options.setBody(body, ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			StringBundler.concat(
				patcherConfiguration.supportLiferayLfuURL(),
				StringPool.FORWARD_SLASH,
				patcherConfiguration.
					supportLiferayTicketAttachmentApiEndpoint()));
		options.setPost(true);

		String responseString = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (responseCode != HttpURLConnection.HTTP_OK) {
			_log.error(
				StringBundler.concat(
					"Response code ", responseCode, ": ", responseString));

			throw new PortalException(
				"failed-to-connect-to-the-large-file-uploader");
		}

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
			responseString);

		String gcsSessionURL = responseJSONObject.getString(
			"gcsSessionURL", StringPool.BLANK);

		try (InputStream fileInputStream = Channels.newInputStream(
				blob.reader())) {

			uploadAttachment(fileInputStream, fileSize, gcsSessionURL);
		}
		catch (Exception exception) {
			throw new Exception("Error processing GCS file", exception);
		}

		long ticketAttachmentId = responseJSONObject.getLong(
			"ticketAttachmentId", 0);

		completeUpload(ticketAttachmentId, patcherBuild.getCompanyId());

		return responseString;
	}

	public static long fetchAccountEntryId(
			String accountEntryCode, long companyId)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);

		options.addHeader(HttpHeaders.USER_AGENT, _PATCHER_USER_AGENT);

		options.addHeader(
			"Authorization", "Bearer " + getAuthenticationToken(companyId));

		PatcherConfiguration patcherConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				PatcherConfiguration.class, companyId);

		options.setLocation(
			StringBundler.concat(
				patcherConfiguration.supportLiferayURL(),
				StringPool.FORWARD_SLASH,
				patcherConfiguration.supportLiferayAccountSearchApiEndpoint(),
				accountEntryCode));

		options.setPost(false);

		String responseString = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new Exception(
				StringBundler.concat(
					"Response code ", response.getResponseCode(), ": ",
					responseString));
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
			long ticketAttachmentId, long companyId)
		throws Exception {

		String body = JSONUtil.put(
			"commentBody", HelpCenterConstants.HELP_CENTER_UPLOAD_COMMENT
		).toString();

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.USER_AGENT, _PATCHER_USER_AGENT);

		options.addHeader(
			"Authorization", "Bearer " + getAuthenticationToken(companyId));

		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);

		options.setBody(body, ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		PatcherConfiguration patcherConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				PatcherConfiguration.class, companyId);

		options.setLocation(
			String.format(
				"%s/ticket-attachments/%d/complete-upload",
				patcherConfiguration.supportLiferayLfuURL(),
				ticketAttachmentId));

		options.setPost(true);

		String responseString = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (responseCode != HttpURLConnection.HTTP_OK) {
			_log.error(
				StringBundler.concat(
					"Response code ", responseCode, ": ", responseString));

			throw new PortalException("failed-to-upload-file");
		}
	}

	protected static String getAuthenticationToken(long companyId)
		throws Exception {

		if (System.currentTimeMillis() < _tokenExpirationTime) {
			return _accessToken;
		}

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.USER_AGENT, _PATCHER_USER_AGENT);

		options.addHeader(
			"Content-Type", ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED);

		PatcherConfiguration patcherConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				PatcherConfiguration.class, companyId);

		options.addPart(
			"client_id", patcherConfiguration.supportLiferayApiClientId());
		options.addPart(
			"client_secret",
			patcherConfiguration.supportLiferayApiClientSecret());

		options.addPart("grant_type", "client_credentials");

		options.setLocation(
			patcherConfiguration.supportLiferayURL() + "/o/oauth2/token");

		options.setPost(true);

		String responseString = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (responseCode != HttpURLConnection.HTTP_OK) {
			_log.error(
				StringBundler.concat(
					"Response code ", responseCode, ": ", responseString));

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
			InputStream fileInputStream, String fileSize, String gcsSessionURL)
		throws Exception {

		URL url = new URL(gcsSessionURL);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("PUT");
		httpURLConnection.setRequestProperty("User-Agent", _PATCHER_USER_AGENT);
		httpURLConnection.setRequestProperty("Content-Length", fileSize);
		httpURLConnection.setRequestProperty(
			"Content-Type", "application/octet-stream");

		IOUtils.copy(fileInputStream, httpURLConnection.getOutputStream());

		int responseCode = httpURLConnection.getResponseCode();

		InputStream responseInputStream = httpURLConnection.getInputStream();

		String responseBody = IOUtils.toString(
			responseInputStream, StringPool.UTF8);

		if (responseCode != HttpURLConnection.HTTP_OK) {
			_log.error(
				StringBundler.concat(
					"Response code ", responseCode, ": ", responseBody));

			throw new PortalException("failed-to-upload-file");
		}

		httpURLConnection.disconnect();
	}

	private static final String _PATCHER_USER_AGENT = "OSB Patcher Portal/7.4";

	private static final Log _log = LogFactoryUtil.getLog(HelpCenterUtil.class);

	private static String _accessToken;
	private static long _tokenExpirationTime;

}