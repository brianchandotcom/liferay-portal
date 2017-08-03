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

package com.liferay.vldap.server.internal.handler.util;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class LdapTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(
		X509Certificate[] x509Certificates, String authType) {
	}

	@Override
	public void checkServerTrusted(
		X509Certificate[] x509Certificates, String authType) {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}

}