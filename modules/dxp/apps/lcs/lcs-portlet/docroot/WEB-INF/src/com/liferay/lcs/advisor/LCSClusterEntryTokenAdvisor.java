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
import com.liferay.lcs.exception.LCSClusterEntryTokenDecryptException;
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
import com.liferay.portal.kernel.exception.NoSuchPortletPreferencesException;
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
import java.io.IOException;

import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;

import java.util.Map;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Igor Beslic
 */
public class LCSClusterEntryTokenAdvisor {

	public void checkLCSClusterEntry(LCSClusterEntryToken lcsClusterEntryToken)
		throws InvalidLCSClusterEntryTokenException {

		LCSClusterNode lcsClusterNode =
			LCSClusterNodeServiceUtil.fetchLCSClusterNode();

		if (lcsClusterEntryToken.getLcsClusterEntryId() ==
				lcsClusterNode.getLcsClusterEntryId()) {

			return;
		}

		_lcsAlertAdvisor.add(LCSAlert.ERROR_ENVIRONMENT_MISMATCH);

		deleteLCSCLusterEntryTokenFile();

		StringBundler sb = new StringBundler(12);

		sb.append("Environment ID ");
		sb.append(lcsClusterEntryToken.getLcsClusterEntryId());
		sb.append(" defined in the LCS activation token file does not match ");
		sb.append("the environment ID ");
		sb.append(lcsClusterNode.getLcsClusterEntryId());
		sb.append(" where the server was registered. The LCS activation ");
		sb.append("token file will be deleted. Please redeploy the correct ");
		sb.append("LCS activation token file. If you intend to change the ");
		sb.append("environment, please unregister server ");
		sb.append(lcsClusterNode.getKey());
		sb.append(" from the old environment using the LCS platform ");
		sb.append("dashboard.");

		throw new InvalidLCSClusterEntryTokenException(sb.toString());
	}

	public void checkLCSClusterEntryTokenId(long lcsClusterEntryTokenId)
		throws InvalidLCSClusterEntryTokenException {

		LCSClusterEntryToken lcsClusterEntryToken =
			_lcsClusterEntryTokenService.fetchLCSClusterEntryToken(
				lcsClusterEntryTokenId);

		if (lcsClusterEntryToken != null) {
			return;
		}

		deleteLCSCLusterEntryTokenFile();

		LCSPortletPreferencesUtil.removeCredentials();

		_lcsAlertAdvisor.add(LCSAlert.ERROR_INVALID_TOKEN);

		StringBundler sb = new StringBundler(7);

		sb.append("The LCS activation token file is invalid since the ID ");
		sb.append(lcsClusterEntryTokenId);
		sb.append(" defined in file does not exist on the LCS platform. The ");
		sb.append("LCS activation token file will be deleted. If the ");
		sb.append("activation token file was regenerated, please make sure ");
		sb.append("to download the latest activation token file using the ");
		sb.append("LCS platform dashboard and deploy it.");

		throw new InvalidLCSClusterEntryTokenException(sb.toString());
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
			_log.error(
				"Multiple LCS activation token files detected. Please delete " +
					"the redundant files manually.",
				mlcscete);
		}
	}

	public Set<LCSAlert> getLCSClusterEntryTokenAlerts() {
		return _lcsAlertAdvisor.getLCSAlerts();
	}

	public LCSClusterEntryToken processLCSClusterEntryToken()
		throws IOException, LCSClusterEntryTokenDecryptException,
			   MissingLCSClusterEntryTokenException,
			   MultipleLCSClusterEntryTokenException,
			   NoSuchPortletPreferencesException, ReadOnlyException {

		LCSClusterEntryToken lcsClusterEntryToken =
			processLCSCLusterEntryTokenFile();

		LCSClusterEntryTokenContentAdvisor lcsClusterEntryTokenContentAdvisor =
			new LCSClusterEntryTokenContentAdvisor(
				lcsClusterEntryToken.getContent());

		storeLCSPortletCredentials(
			lcsClusterEntryTokenContentAdvisor.getAccessSecret(),
			lcsClusterEntryTokenContentAdvisor.getAccessToken(),
			lcsClusterEntryToken.getLcsClusterEntryId(),
			lcsClusterEntryToken.getLcsClusterEntryTokenId());

		storeLCSConfiguration(lcsClusterEntryTokenContentAdvisor);

		return lcsClusterEntryToken;
	}

	public LCSClusterEntryToken processLCSCLusterEntryTokenFile()
		throws IOException, LCSClusterEntryTokenDecryptException,
			   MissingLCSClusterEntryTokenException,
			   MultipleLCSClusterEntryTokenException {

		if (_log.isDebugEnabled()) {
			_log.debug("Detecting LCS activation token file");
		}

		LCSClusterEntryToken lcsClusterEntryToken = null;

		String lcsClusterEntryTokenFileName = getLCSClusterEntryTokenFileName();

		File lcsClusterEntryTokenFile = new File(lcsClusterEntryTokenFileName);

		byte[] bytes = FileUtil.getBytes(lcsClusterEntryTokenFile);

		String lcsClusterEntryTokenJSON = null;

		try {
			lcsClusterEntryTokenJSON = decrypt(bytes);
		}
		catch (Exception e) {
			deleteLCSCLusterEntryTokenFile();

			_lcsAlertAdvisor.add(LCSAlert.ERROR_INVALID_TOKEN);

			StringBundler sb = new StringBundler(6);

			sb.append("Unable to decrypt the LCS activation token file ");
			sb.append(lcsClusterEntryTokenFileName);
			sb.append(". The LCS activation token file will be deleted. ");
			sb.append("Please make sure that the LCS portlet is compatible ");
			sb.append("with the LCS platform version where the file was ");
			sb.append("generated.");

			throw new LCSClusterEntryTokenDecryptException(sb.toString(), e);
		}

		ObjectMapper objectMapper = new ObjectMapper();

		lcsClusterEntryToken = objectMapper.readValue(
			lcsClusterEntryTokenJSON, LCSClusterEntryTokenImpl.class);

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

		Certificate certificate = keyStore.getCertificate(keyName);

		Key key = certificate.getPublicKey();

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

			key = certificate.getPublicKey();

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
							StringBundler sb = new StringBundler(7);

							sb.append("LCS activation token file name ");
							sb.append(name);
							sb.append(" is deprecated and will not be ");
							sb.append("supported in the next version. Please ");
							sb.append("download the LCS activation token ");
							sb.append("file again and replace the old file ");
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
			_lcsAlertAdvisor.add(LCSAlert.ERROR_MISSING_TOKEN);

			throw new MissingLCSClusterEntryTokenException(
				"The LCS activation token file is missing from directory " +
					sb.toString());
		}
		else if (lcsClusterEntryTokenFileNames.length > 1) {
			_lcsAlertAdvisor.add(LCSAlert.ERROR_MULTIPLE_TOKENS);

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
		throws ReadOnlyException {

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

	protected void storeLCSPortletCredentials(
			String lcsAccessSecret, String lcsAccessToken,
			long lcsClusterEntryId, long lcsClusterEntryTokenId)
		throws NoSuchPortletPreferencesException, ReadOnlyException {

		PortletPreferences jxPortletPreferences =
			LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences();

		if (jxPortletPreferences == null) {
			throw new NoSuchPortletPreferencesException();
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