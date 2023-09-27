/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.google.merchant.internal.jsch;

import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.HostKeyRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.UserInfo;

import com.liferay.commerce.google.merchant.internal.constants.CommerceGoogleMerchantConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Eric Chin
 */
public class FingerprintHostKeyRepository implements HostKeyRepository {

	public FingerprintHostKeyRepository(
		JSch jSch, String configuredFingerprint) {

		_jSch = jSch;
		_configuredFingerprint = configuredFingerprint;
	}

	@Override
	public void add(HostKey hostKey, UserInfo userInfo) {
	}

	@Override
	public int check(String s, byte[] key) {
		try {
			HostKey hostKey = new HostKey(
				CommerceGoogleMerchantConstants.
					COMMERCE_GOOGLE_PARTNER_UPLOAD_URL,
				key);

			String fingerprint = hostKey.getFingerPrint(_jSch);

			if (!fingerprint.equals(_configuredFingerprint)) {
				return NOT_INCLUDED;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return NOT_INCLUDED;
		}

		return OK;
	}

	@Override
	public HostKey[] getHostKey() {
		return new HostKey[0];
	}

	@Override
	public HostKey[] getHostKey(String s, String s1) {
		return new HostKey[0];
	}

	@Override
	public String getKnownHostsRepositoryID() {
		return null;
	}

	@Override
	public void remove(String s, String s1) {
	}

	@Override
	public void remove(String s, String s1, byte[] bytes) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FingerprintHostKeyRepository.class);

	private final String _configuredFingerprint;
	private final JSch _jSch;

}