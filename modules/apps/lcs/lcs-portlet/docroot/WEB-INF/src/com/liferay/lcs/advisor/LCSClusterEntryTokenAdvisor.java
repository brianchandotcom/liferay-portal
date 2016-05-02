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
import com.liferay.lcs.rest.LCSClusterEntryToken;
import com.liferay.lcs.rest.LCSClusterEntryTokenImpl;
import com.liferay.lcs.rest.LCSClusterEntryTokenService;
import com.liferay.lcs.rest.LCSClusterNode;
import com.liferay.lcs.rest.LCSClusterNodeServiceUtil;
import com.liferay.lcs.security.KeyStoreFactory;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSAlert;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.Encryptor;
import com.liferay.util.portlet.PortletProps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import java.security.Key;
import java.security.KeyStore;

import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * @author Igor Beslic
 */
public class LCSClusterEntryTokenAdvisor {

	public void checkLCSClusterEntry(LCSClusterEntryToken lcsClusterEntryToken)
		throws Exception {

		LCSClusterNode lcsClusterNode =
			LCSClusterNodeServiceUtil.fetchLCSClusterNode(
				_keyGenerator.getKey());

		if (lcsClusterEntryToken.getLcsClusterEntryId() ==
				lcsClusterNode.getLcsClusterEntryId()) {

			return;
		}

		_lcsAlertAdvisor.add(LCSAlert.ERROR_ENVIRONMENT_MISMATCH);

		_lcsAlertAdvisor.remove(LCSAlert.SUCCESS_VALID_TOKEN);

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
			_lcsAlertAdvisor.add(LCSAlert.SUCCESS_VALID_TOKEN);

			return;
		}

		LCSUtil.removeCredentials();

		_lcsAlertAdvisor.add(LCSAlert.ERROR_INVALID_TOKEN);

		throw new InvalidLCSClusterEntryTokenException(
			"LCS cluster entry token is invalid. Delete token file.");
	}

	public void checkLCSClusterEntryTokenPreferences(
			LCSClusterEntryToken lcsClusterEntryToken)
		throws Exception {

		javax.portlet.PortletPreferences jxPortletPreferences =
			LCSUtil.fetchJxPortletPreferences();

		if (jxPortletPreferences == null) {
			return;
		}

		String lcsAccessSecret = extractAccessSecret(lcsClusterEntryToken);
		String lcsAccessToken = extractAccessToken(lcsClusterEntryToken);

		if (!lcsAccessSecret.equals(
				jxPortletPreferences.getValue("lcsAccessSecret", null)) ||
			!lcsAccessToken.equals(
				jxPortletPreferences.getValue("lcsAccessToken", null))) {

			_lcsAlertAdvisor.add(LCSAlert.ERROR_ENVIRONMENT_MISMATCH);

			_lcsAlertAdvisor.remove(LCSAlert.SUCCESS_VALID_TOKEN);

			deleteLCSCLusterEntryTokenFile();

			throw new InvalidLCSClusterEntryTokenException(
				"LCS cluster entry token mismatches portlet preferences");
		}

		long cachedLCSClusterEntryTokenId = GetterUtil.getLong(
			jxPortletPreferences.getValue("lcsClusterEntryTokenId", null));
		long cachedLCSClusterEntryId = GetterUtil.getLong(
			jxPortletPreferences.getValue("lcsClusterEntryId", null));

		if ((cachedLCSClusterEntryId != 0) &&
			(lcsClusterEntryToken.getLcsClusterEntryId() !=
				cachedLCSClusterEntryId) &&
			(cachedLCSClusterEntryTokenId != 0) &&
			(lcsClusterEntryToken.getLcsClusterEntryTokenId() !=
				cachedLCSClusterEntryTokenId)) {

			_lcsAlertAdvisor.add(LCSAlert.ERROR_ENVIRONMENT_MISMATCH);

			_lcsAlertAdvisor.remove(LCSAlert.SUCCESS_VALID_TOKEN);

			deleteLCSCLusterEntryTokenFile();

			throw new InvalidLCSClusterEntryTokenException(
				"LCS cluster entry token mismatches portlet preferences");
		}
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

		if (!LCSUtil.storeLCSPortletCredentials(
				extractAccessSecret(lcsClusterEntryToken),
				extractAccessToken(lcsClusterEntryToken),
				lcsClusterEntryToken.getLcsClusterEntryId(),
				lcsClusterEntryToken.getLcsClusterEntryTokenId())) {

			if (_log.isWarnEnabled()) {
				_log.warn("Unable to process LCS cluster entry token");
			}

			_lcsAlertAdvisor.add(LCSAlert.ERROR_INVALID_TOKEN);

			throw new NoLCSClusterEntryTokenException(
				"Unable to find LCS cluster entry token");
		}

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
			LCSUtil.fetchJxPortletPreferences();

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

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSAlertAdvisor(LCSAlertAdvisor lcsAlertAdvisor) {
		_lcsAlertAdvisor = lcsAlertAdvisor;
	}

	public void setLCSClusterEntryTokenService(
		LCSClusterEntryTokenService lcsClusterEntryTokenService) {

		_lcsClusterEntryTokenService = lcsClusterEntryTokenService;
	}

	protected String decrypt(byte[] bytes) throws Exception {
		String keyStorePassword = PortletProps.get(
			"digital.signature.key.store.password");

		KeyStore keyStore = KeyStoreFactory.getInstance(
			PortletProps.get("digital.signature.key.store.path"),
			PortletProps.get("digital.signature.key.store.type"),
			keyStorePassword);

		String keyName = PortletProps.get("digital.signature.key.name");

		Key key = keyStore.getCertificate(keyName).getPublicKey();

		return Encryptor.decryptUnencodedAsString(key, bytes);
	}

	protected String extractAccessSecret(
		LCSClusterEntryToken lcsClusterEntryToken) {

		String content = lcsClusterEntryToken.getContent();

		String[] tokens = content.split(StringPool.DOUBLE_DASH);

		return tokens[1];
	}

	protected String extractAccessToken(
		LCSClusterEntryToken lcsClusterEntryToken) {

		String content = lcsClusterEntryToken.getContent();

		String[] tokens = content.split(StringPool.DOUBLE_DASH);

		return tokens[0];
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

	private static Log _log = LogFactoryUtil.getLog(
		LCSClusterEntryTokenAdvisor.class);

	private KeyGenerator _keyGenerator;
	private LCSAlertAdvisor _lcsAlertAdvisor;
	private LCSClusterEntryTokenService _lcsClusterEntryTokenService;

}