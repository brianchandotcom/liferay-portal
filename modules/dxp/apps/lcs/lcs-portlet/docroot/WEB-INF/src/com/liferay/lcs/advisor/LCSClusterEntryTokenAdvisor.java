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

import com.liferay.lcs.activation.LCSClusterEntryTokenContentAdvisor;
import com.liferay.lcs.exception.InvalidLCSClusterEntryTokenException;
import com.liferay.lcs.exception.MissingLCSClusterEntryTokenException;
import com.liferay.lcs.exception.MultipleLCSClusterEntryTokenException;
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
import java.io.FilenameFilter;

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
			"LCS activation token mismatches LCS cluster node's LCS cluster " +
				"entry ID");
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
			"LCS activation token is invalid. Delete token file.");
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
					"to match the LCS activation token");
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
		catch (MissingLCSClusterEntryTokenException mlcscete) {
			if (_log.isDebugEnabled()) {
				_log.debug("LCS activation token file is not present");
			}
		}
		catch (MultipleLCSClusterEntryTokenException mlcscete) {
			if (_log.isDebugEnabled()) {
				_log.debug(mlcscete.getMessage());
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

			throw new MissingLCSClusterEntryTokenException(
				"Unable to find LCS activation token");
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
				_log.warn("Unable to process LCS activation token");
			}

			_lcsAlertAdvisor.add(LCSAlert.ERROR_INVALID_TOKEN);

			throw new MissingLCSClusterEntryTokenException(
				"Unable to find LCS activation token");
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
			if ((e instanceof MissingLCSClusterEntryTokenException) ||
				(e instanceof MultipleLCSClusterEntryTokenException)) {

				_log.error(e.getMessage());
			}
			else {
				_log.error(
					"Unable to process the LCS activation token file", e);
			}

			return null;
		}

		return lcsClusterEntryToken;
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
		throws MissingLCSClusterEntryTokenException,
			MultipleLCSClusterEntryTokenException {

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

							sb.append("LCS activation token file name ");
							sb.append(name);
							sb.append(" is deprecated and will not be ");
							sb.append("supported in the next version. Please ");
							sb.append("download the LCS activation token");
							sb.append("file again and replace the old file");
							sb.append("with the new one.");

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
			_lcsAlertAdvisor.add(LCSAlert.ERROR_TOKEN_FILE_IS_MISSING);

			throw new MissingLCSClusterEntryTokenException(
				"LCS activation token file misses at directory " +
					sb.toString());
		}
		else if (lcsClusterEntryTokenFileNames.length > 1) {
			_lcsAlertAdvisor.add(LCSAlert.WARNING_MULTIPLE_TOKENS);

			throw new MultipleLCSClusterEntryTokenException(
				"Only one LCS activation token file is allowed");
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

		_verifyLCSServiceConfiguration(
			lcsServicesConfiguration, LCSConstants.METRICS_LCS_SERVICE_ENABLED,
			LCSConstants.PATCHES_LCS_SERVICE_ENABLED,
			LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED);

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

	private void _verifyLCSServiceConfiguration(
		Map<String, String> lcsServicesConfiguration, String... keys) {

		if (keys == null) {
			return;
		}

		for (String key : keys) {
			if (lcsServicesConfiguration.containsKey(key)) {
				continue;
			}

			lcsServicesConfiguration.put(key, "false");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSClusterEntryTokenAdvisor.class);

	private LCSAlertAdvisor _lcsAlertAdvisor;
	private LCSClusterEntryTokenService _lcsClusterEntryTokenService;

}