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

package com.liferay.portal.security.sso.ntlm.internal;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration;
import com.liferay.portal.security.sso.ntlm.constants.NtlmConstants;
import com.liferay.portal.security.sso.ntlm.internal.msrpc.NetrServerAuthenticate3;
import com.liferay.portal.security.sso.ntlm.internal.msrpc.NetrServerReqChallenge;

import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;

import jcifs.dcerpc.DcerpcHandle;

import jcifs.smb.NtlmPasswordAuthentication;

import jcifs.util.HMACT64;
import jcifs.util.MD4;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration",
	immediate = true, service = NetlogonConnectionManager.class
)
public class NetlogonConnectionManagerImpl
	implements NetlogonConnectionManager {

	@Override
	public NetlogonConnection connect(
			String domainController, String domainControllerName,
			NtlmServiceAccount ntlmServiceAccount)
		throws IOException, NoSuchAlgorithmException, NtlmLogonException {

		NtlmPasswordAuthentication ntlmPasswordAuthentication =
			new NtlmPasswordAuthentication(
				null, ntlmServiceAccount.getAccount(),
				ntlmServiceAccount.getPassword());

		String endpoint = "ncacn_np:" + domainController + "[\\PIPE\\NETLOGON]";

		DcerpcHandle dcerpcHandle = DcerpcHandle.getHandle(
			endpoint, ntlmPasswordAuthentication);

		dcerpcHandle.bind();

		byte[] clientChallenge = new byte[8];

		BigEndianCodec.putLong(clientChallenge, 0, SecureRandomUtil.nextLong());

		NetrServerReqChallenge netrServerReqChallenge =
			new NetrServerReqChallenge(
				domainControllerName, ntlmServiceAccount.getComputerName(),
				clientChallenge, new byte[8]);

		dcerpcHandle.sendrecv(netrServerReqChallenge);

		MD4 md4 = new MD4();

		md4.update(ntlmServiceAccount.getPassword().getBytes("UTF-16LE"));

		byte[] sessionKey = computeSessionKey(
			md4.digest(), clientChallenge,
			netrServerReqChallenge.getServerChallenge());

		byte[] clientCredential =
			NetlogonCredentialUtil.computeNetlogonCredential(
				clientChallenge, sessionKey);

		NetrServerAuthenticate3 netrServerAuthenticate3 =
			new NetrServerAuthenticate3(
				domainControllerName, ntlmServiceAccount.getAccountName(), 2,
				ntlmServiceAccount.getComputerName(), clientCredential,
				new byte[8], getNegotiateFlags());

		dcerpcHandle.sendrecv(netrServerAuthenticate3);

		byte[] serverCredential =
			NetlogonCredentialUtil.computeNetlogonCredential(
				netrServerReqChallenge.getServerChallenge(), sessionKey);

		if (!Arrays.equals(
				serverCredential,
				netrServerAuthenticate3.getServerCredential())) {

			throw new NtlmLogonException("Session key negotiation failed");
		}

		NetlogonConnection netLogonConnection = new NetlogonConnection(
			clientCredential, sessionKey);

		netLogonConnection.setDcerpcHandle(dcerpcHandle);

		return netLogonConnection;
	}

	protected byte[] computeSessionKey(
			byte[] sharedSecret, byte[] clientChallenge, byte[] serverChallenge)
		throws NoSuchAlgorithmException {

		MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		byte[] zeroes = {0, 0, 0, 0};

		messageDigest.update(zeroes, 0, 4);
		messageDigest.update(clientChallenge, 0, 8);
		messageDigest.update(serverChallenge, 0, 8);

		HMACT64 hmact64 = new HMACT64(sharedSecret);

		hmact64.update(messageDigest.digest());

		return hmact64.digest();
	}

	protected int getNegotiateFlags() {
		int negotiateFlags = 0x600FFFFF;

		try {
			NtlmConfiguration ntlmConfiguration =
				_configurationProvider.getConfiguration(
					NtlmConfiguration.class,
					new CompanyServiceSettingsLocator(
						CompanyThreadLocal.getCompanyId(),
						NtlmConstants.SERVICE_NAME));

			String negotiateFlagsString = ntlmConfiguration.negotiateFlags();

			if (negotiateFlagsString.startsWith("0x")) {
				negotiateFlags = Integer.valueOf(
					negotiateFlagsString.substring(2), 16);
			}
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to get NTLM configuration", ce);
		}

		return negotiateFlags;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NetlogonConnectionManagerImpl.class);

	private ConfigurationProvider _configurationProvider;

}