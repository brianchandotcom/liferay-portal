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

import com.liferay.portal.security.sso.ntlm.internal.msrpc.NetlogonAuthenticator;

import java.io.IOException;

import jcifs.dcerpc.DcerpcHandle;

import jcifs.util.Encdec;

/**
 * @author Michael C. Han
 */
public class NetlogonConnection {

	public NetlogonConnection(byte[] clientCredential, byte[] sessionKey) {
		_clientCredential = clientCredential;
		_sessionKey = sessionKey;
	}

	public NetlogonAuthenticator computeNetlogonAuthenticator() {
		int timestamp = (int)System.currentTimeMillis();

		int input = Encdec.dec_uint32le(_clientCredential, 0) + timestamp;

		Encdec.enc_uint32le(input, _clientCredential, 0);

		byte[] credential = NetlogonCredentialUtil.computeNetlogonCredential(
			_clientCredential, _sessionKey);

		return new NetlogonAuthenticator(credential, timestamp);
	}

	public void disconnect() throws IOException {
		if (_dcerpcHandle != null) {
			_dcerpcHandle.close();
		}
	}

	public byte[] getClientCredential() {
		return _clientCredential;
	}

	public DcerpcHandle getDcerpcHandle() {
		return _dcerpcHandle;
	}

	public byte[] getSessionKey() {
		return _sessionKey;
	}

	public void setDcerpcHandle(DcerpcHandle dcerpcHandle) {
		_dcerpcHandle = dcerpcHandle;
	}

	private final byte[] _clientCredential;
	private DcerpcHandle _dcerpcHandle;
	private final byte[] _sessionKey;

}