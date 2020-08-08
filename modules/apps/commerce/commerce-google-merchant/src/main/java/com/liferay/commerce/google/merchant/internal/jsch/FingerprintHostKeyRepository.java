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

package com.liferay.commerce.google.merchant.internal.jsch;

import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.HostKeyRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.UserInfo;

import com.liferay.commerce.google.merchant.internal.constants.CommerceGoogleMerchantConstants;

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

	private final String _configuredFingerprint;
	private final JSch _jSch;

}