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

package com.liferay.petra.json.web.service.client.server.simulator;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * @author Igor Beslic
 */
public class BaseHttpsConfigurator extends HttpsConfigurator {

	public BaseHttpsConfigurator(SSLContext sslContext) {
		super(sslContext);
	}

	@Override
	public void configure(HttpsParameters httpsParameters) {
		try {
			SSLContext defaultSSLContext = SSLContext.getDefault();

			SSLEngine sslEngine = defaultSSLContext.createSSLEngine();

			httpsParameters.setCipherSuites(sslEngine.getEnabledCipherSuites());

			httpsParameters.setNeedClientAuth(false);

			httpsParameters.setProtocols(sslEngine.getEnabledProtocols());

			super.configure(httpsParameters);
		}
		catch (NoSuchAlgorithmException nsae) {
			throw new RuntimeException(nsae);
		}
	}

}