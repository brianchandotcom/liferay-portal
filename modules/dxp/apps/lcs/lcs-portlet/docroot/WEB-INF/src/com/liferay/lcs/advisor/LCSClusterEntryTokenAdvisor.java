/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lcs.advisor;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.lcs.InvalidLCSClusterEntryTokenException;
import com.liferay.lcs.NoLCSClusterEntryTokenException;
import com.liferay.lcs.activation.LCSClusterEntryTokenContentAdvisor;
import com.liferay.lcs.rest.LCSClusterEntryToken;
import com.liferay.lcs.rest.LCSClusterEntryTokenImpl;
import com.liferay.lcs.rest.LCSClusterEntryTokenService;
import com.liferay.lcs.rest.LCSClusterNode;
import com.liferay.lcs.rest.LCSClusterNodeServiceUtil;
import com.liferay.lcs.security.KeyStoreAdvisor;
import com.liferay.lcs.security.KeyStoreFactory;
import com.liferay.lcs.util.LCSAlert;
import com.liferay.lcs.util.LCSConstants;
import com.liferay.lcs.util.LCSPortletPreferencesUtil;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.lcs.util.PortletPropsValues;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import java.security.Key;
import java.security.KeyStore;

import java.util.Map;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;

import javax.portlet.PortletPreferences;

/**
 * @author Igor Beslic
 */
public class LCSClusterEntryTokenAdvisor {

	public void checkLCSClusterEntry(LCSClusterEntryToken lcsClusterEntryToken)
		throws Exception {

		LCSClusterNode lcsClusterNode =
			LCSClusterNodeServiceUtil.fetchLCSClusterNode();

		if (lcsClusterEntryToken.getLcsClusterEntryId() ==
				lcsClusterNode.getLcsClusterEntryId()) {

			return;
		}

		_lcsAlertAdvisor.add(LCSAlert.ERROR_ENVIRONMENT_MISMATCH);

		deleteLCSCLusterEntryTokenFile();

		throw new InvalidLCSClusterEntryTokenException(
			"LCS cluster entry token mismatches LCS cluster node's LCS " +
				"cluster entry ID");
	}

	public void checkLCSClusterEntryTokenId(long lcsClusterEntryTokenId)
		throws Exception {

		LCSClusterEntryToken lcsClusterEntryToken =
			_lcsClusterEntryTokenService.fetchLCSClusterEntryToken(
				lcsClusterEntryTokenId);

		if (lcsClusterEntryToken != null) {
			return;
		}

		LCSPortletPreferencesUtil.removeCredentials();

		_lcsAlertAdvisor.add(LCSAlert.ERROR_INVALID_TOKEN);

		throw new InvalidLCSClusterEntryTokenException(
			"LCS cluster entry token is invalid. Delete token file.");
	}

	public void checkLCSClusterEntryTokenPreferences(
			LCSClusterEntryToken lcsClusterEntryToken)
		throws Exception {

		PortletPreferences jxPortletPreferences =
			LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences();

		if (jxPortletPreferences == null) {
			return;
		}

		LCSClusterEntryTokenContentAdvisor lcsClusterEntryTokenContentAdvisor =
			new LCSClusterEntryTokenContentAdvisor(
				lcsClusterEntryToken.getContent());

		String lcsAccessSecret =
			lcsClusterEntryTokenContentAdvisor.getAccessSecret();
		String lcsAccessToken =
			lcsClusterEntryTokenContentAdvisor.getAccessToken();

		long lcsClusterEntryId = lcsClusterEntryToken.getLcsClusterEntryId();
		long lcsClusterEntryTokenId =
			lcsClusterEntryToken.getLcsClusterEntryTokenId();

		if (lcsAccessSecret.equals(
				jxPortletPreferences.getValue("lcsAccessSecret", null)) &&
			lcsAccessToken.equals(
				jxPortletPreferences.getValue("lcsAccessToken", null)) &&
			(lcsClusterEntryId ==
				GetterUtil.getLong(
					jxPortletPreferences.getValue(
						"lcsClusterEntryId", null))) &&
			(lcsClusterEntryTokenId ==
				GetterUtil.getLong(
					jxPortletPreferences.getValue(
						"lcsClusterEntryTokenId", null)))) {

			return;
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"LCS portlet will update credentials in portlet preferences " +
					"to match the LCS cluster entry token");
		}

		LCSPortletPreferencesUtil.removeCredentials();
	}

	public void deleteLCSCLusterEntryTokenFile() {
		if (_log.isDebugEnabled()) {
			_log.debug("Deleting LCS activation token file");
		}

		try {
			FileUtil.delete(getLCSClusterEntryTokenFileName());
		}
		catch (FileNotFoundException fnfe) {
			if (_log.isDebugEnabled()) {
				_log.debug("LCS activation token file is not present");
			}
		}
	}

	public Set<LCSAlert> getLCSClusterEntryTokenAlerts() {
		return _lcsAlertAdvisor.getLCSAlerts();
	}

	public LCSClusterEntryToken processLCSClusterEntryToken() throws Exception {
		LCSClusterEntryToken lcsClusterEntryToken =
			processLCSCLusterEntryTokenFile();

		if (lcsClusterEntryToken == null) {
			_lcsAlertAdvisor.add(LCSAlert.WARNING_MISSING_TOKEN);

			throw new NoLCSClusterEntryTokenException(
				"Unable to find LCS cluster entry token");
		}

		LCSClusterEntryTokenContentAdvisor lcsClusterEntryTokenContentAdvisor =
			new LCSClusterEntryTokenContentAdvisor(
				lcsClusterEntryToken.getContent());

		if (!storeLCSPortletCredentials(
				lcsClusterEntryTokenContentAdvisor.getAccessSecret(),
				lcsClusterEntryTokenContentAdvisor.getAccessToken(),
				lcsClusterEntryToken.getLcsClusterEntryId(),
				lcsClusterEntryToken.getLcsClusterEntryTokenId())) {

			if (_log.isWarnEnabled()) {
				_log.warn("Unable to process LCS cluster entry token");
			}

			_lcsAlertAdvisor.add(LCSAlert.ERROR_INVALID_TOKEN);

			throw new NoLCSClusterEntryTokenException(
				"Unable to find LCS cluster entry token");
		}

		storeLCSConfiguration(lcsClusterEntryTokenContentAdvisor);

		return lcsClusterEntryToken;
	}

	public LCSClusterEntryToken processLCSCLusterEntryTokenFile() {
		if (_log.isDebugEnabled()) {
			_log.debug("Detecting LCS activation code");
		}

		LCSClusterEntryToken lcsClusterEntryToken = null;

		try {
			String lcsClusterEntryTokenFileName =
				getLCSClusterEntryTokenFileName();

			File lcsClusterEntryTokenFile = new File(
				lcsClusterEntryTokenFileName);

			byte[] bytes = FileUtil.getBytes(lcsClusterEntryTokenFile);

			String lcsClusterEntryTokenJSON = decrypt(bytes);

			ObjectMapper objectMapper = new ObjectMapper();

			lcsClusterEntryToken = objectMapper.readValue(
				lcsClusterEntryTokenJSON, LCSClusterEntryTokenImpl.class);
		}
		catch (Exception e) {
			if (e instanceof IOException) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to find the LCS cluster entry token file");
				}
			}
			else {
				_log.error(
					"Unable to read the LCS cluster entry token file", e);
			}

			return null;
		}

		return lcsClusterEntryToken;
	}

	public long processLCSClusterEntryTokenPreferences() {
		PortletPreferences portletPreferences =
			LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences();

		if (portletPreferences == null) {
			return 0;
		}

		long lcsClusterEntryTokenId = GetterUtil.getLong(
			portletPreferences.getValue("lcsClusterEntryTokenId", null));

		LCSClusterEntryToken lcsClusterEntryToken =
			processLCSCLusterEntryTokenFile();

		if (lcsClusterEntryToken == null) {
			_lcsAlertAdvisor.add(LCSAlert.WARNING_MISSING_TOKEN);

			if (_log.isWarnEnabled()) {
				_log.warn("The LCS cluster entry token file is missing");
			}

			return lcsClusterEntryTokenId;
		}

		if (lcsClusterEntryTokenId !=
				lcsClusterEntryToken.getLcsClusterEntryTokenId()) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"The cached LCS cluster entry token ID does not match " +
						"the file value");
			}

			_lcsAlertAdvisor.add(LCSAlert.WARNING_TOKEN_MISMATCH);
		}

		return lcsClusterEntryTokenId;
	}

	public void setLCSAlertAdvisor(LCSAlertAdvisor lcsAlertAdvisor) {
		_lcsAlertAdvisor = lcsAlertAdvisor;
	}

	public void setLCSClusterEntryTokenService(
		LCSClusterEntryTokenService lcsClusterEntryTokenService) {

		_lcsClusterEntryTokenService = lcsClusterEntryTokenService;
	}

	protected String decrypt(byte[] bytes) throws Exception {
		KeyStore keyStore = KeyStoreFactory.getInstance(
			PortletPropsValues.DIGITAL_SIGNATURE_KEY_STORE_PATH,
			PortletPropsValues.DIGITAL_SIGNATURE_KEY_STORE_TYPE,
			"_k3y#5t0r3-p45S");

		String keyName = PortletPropsValues.DIGITAL_SIGNATURE_KEY_NAME;

		Key key = keyStore.getCertificate(keyName).getPublicKey();

		byte[] symmetricKeyEncrypted = ArrayUtil.subset(bytes, 0, 256);

		byte[] symmetricKeyBytes = null;

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Decrypt with default key " + keyName);
			}

			symmetricKeyBytes = Encryptor.decryptUnencodedAsBytes(
				key, symmetricKeyEncrypted);
		}
		catch (EncryptorException ee) {
			KeyStoreAdvisor keyStoreAdvisor = new KeyStoreAdvisor();

			keyName = keyStoreAdvisor.getKeyAlias(
				LCSUtil.getLCSPortletBuildNumber(),
				PortletPropsValues.DIGITAL_SIGNATURE_KEY_NAME, keyStore);

			if (_log.isDebugEnabled()) {
				_log.debug("Decrypt with key " + keyName);
			}

			key = keyStore.getCertificate(keyName).getPublicKey();

			symmetricKeyBytes = Encryptor.decryptUnencodedAsBytes(
				key, symmetricKeyEncrypted);
		}

		Key symmetricKey = new SecretKeySpec(symmetricKeyBytes, "AES");

		return Encryptor.decryptUnencodedAsString(
			symmetricKey, ArrayUtil.subset(bytes, 256, bytes.length));
	}

	protected String getLCSClusterEntryTokenFileName()
		throws FileNotFoundException {

		StringBundler sb = new StringBundler(4);

		sb.append(PropsUtil.get("liferay.home"));
		sb.append("/data");

		File liferayDataDir = new File(sb.toString());

		String[] lcsClusterEntryTokenFileNames = liferayDataDir.list(
			new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.startsWith("lcs-cluster-entry-token")) {
						if (_log.isWarnEnabled()) {
							StringBundler sb = new StringBundler();

							sb.append("LCS token file name ");
							sb.append(name);
							sb.append(" is deprecated and will not be ");
							sb.append("supported in the next version. Please ");
							sb.append("download the LCS token file again and ");
							sb.append("replace the old file with the new one.");

							_log.warn(sb.toString());
						}

						return true;
					}

					if (name.startsWith("lcs-aatf")) {
						return true;
					}

					return false;
				}

			});

		if (lcsClusterEntryTokenFileNames.length == 0) {
			throw new FileNotFoundException();
		}
		else if (lcsClusterEntryTokenFileNames.length > 1) {
			_lcsAlertAdvisor.add(LCSAlert.WARNING_MULTIPLE_TOKENS);
		}

		sb.append(StringPool.SLASH);
		sb.append(lcsClusterEntryTokenFileNames[0]);

		return sb.toString();
	}

	protected void storeLCSConfiguration(
			LCSClusterEntryTokenContentAdvisor
				lcsClusterEntryTokenContentAdvisor)
		throws Exception {

		Map<String, String> lcsServicesConfiguration =
			lcsClusterEntryTokenContentAdvisor.getLCSServicesConfiguration();

		for (Map.Entry<String, String> lcsServiceConfigurationEntry :
				lcsServicesConfiguration.entrySet()) {

			LCSPortletPreferencesUtil.store(
				lcsServiceConfigurationEntry.getKey(),
				lcsServiceConfigurationEntry.getValue());
		}

		if (GetterUtil.getBoolean(
				lcsServicesConfiguration.get(
					LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED)) &&
			Validator.isNotNull(
				lcsClusterEntryTokenContentAdvisor.
					getPortalPropertiesBlacklist())) {

			LCSPortletPreferencesUtil.store(
				LCSConstants.PORTAL_PROPERTIES_BLACKLIST,
				lcsClusterEntryTokenContentAdvisor.
					getPortalPropertiesBlacklist());
		}
	}

	protected boolean storeLCSPortletCredentials(
			String lcsAccessSecret, String lcsAccessToken,
			long lcsClusterEntryId, long lcsClusterEntryTokenId)
		throws Exception {

		PortletPreferences jxPortletPreferences =
			LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences();

		if (jxPortletPreferences == null) {
			return false;
		}

		LCSPortletPreferencesUtil.store("lcsAccessSecret", lcsAccessSecret);
		LCSPortletPreferencesUtil.store("lcsAccessToken", lcsAccessToken);

		if (lcsClusterEntryId != 0) {
			LCSPortletPreferencesUtil.store(
				"lcsClusterEntryId", String.valueOf(lcsClusterEntryId));
			LCSPortletPreferencesUtil.store(
				"lcsClusterEntryTokenId",
				String.valueOf(lcsClusterEntryTokenId));
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSClusterEntryTokenAdvisor.class);

	private LCSAlertAdvisor _lcsAlertAdvisor;
	private LCSClusterEntryTokenService _lcsClusterEntryTokenService;

}