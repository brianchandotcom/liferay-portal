/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.handler.util;

import java.security.KeyStore;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class LdapTrustManagerFactory extends TrustManagerFactorySpi {

	public static final TrustManager[] TRUST_MANAGERS =
		{new LdapTrustManager()};

	@Override
	protected TrustManager[] engineGetTrustManagers() {
		return TRUST_MANAGERS;
	}

	@Override
	protected void engineInit(KeyStore keyStore) {
	}

	@Override
	protected void engineInit(
		ManagerFactoryParameters managerFactoryParameters) {
	}

}