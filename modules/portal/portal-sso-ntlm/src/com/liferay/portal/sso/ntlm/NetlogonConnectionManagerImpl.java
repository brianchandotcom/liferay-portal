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

package com.liferay.portal.sso.ntlm;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.sso.ntlm.configuration.NtlmConfiguration;
import com.liferay.portal.sso.ntlm.msrpc.NetrServerAuthenticate3;
import com.liferay.portal.sso.ntlm.msrpc.NetrServerReqChallenge;

import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;
import java.util.Map;

import jcifs.dcerpc.DcerpcHandle;

import jcifs.smb.NtlmPasswordAuthentication;

import jcifs.util.HMACT64;
import jcifs.util.MD4;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = NetlogonConnectionManager.class)
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
				new byte[8], _negotiateFlags);

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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ntlmConfiguration = Configurable.createConfigurable(
			NtlmConfiguration.class, properties);

		String negotiateFlags = _ntlmConfiguration.negotiateFlags();

		if (negotiateFlags.startsWith("0x")) {
			_negotiateFlags = Integer.valueOf(negotiateFlags.substring(2), 16);
		}
		else {
			_negotiateFlags = 0x600FFFFF;
		}
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

	private volatile int _negotiateFlags;
	private volatile NtlmConfiguration _ntlmConfiguration;

}