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

package com.liferay.oauth.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth.service.OAuthUserServiceUtil;

import java.util.Arrays;

/**
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class OAuthUserServiceClpInvoker {
	public OAuthUserServiceClpInvoker() {
		_methodName30 = "getOSGiServiceIdentifier";

		_methodParameterTypes30 = new String[] {  };

		_methodName35 = "addOAuthUser";

		_methodParameterTypes35 = new String[] {
				"java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName36 = "deleteOAuthUser";

		_methodParameterTypes36 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName30.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes30, parameterTypes)) {
			return OAuthUserServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName35.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes35, parameterTypes)) {
			return OAuthUserServiceUtil.addOAuthUser((java.lang.String)arguments[0],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[1]);
		}

		if (_methodName36.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes36, parameterTypes)) {
			return OAuthUserServiceUtil.deleteOAuthUser(((Long)arguments[0]).longValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName30;
	private String[] _methodParameterTypes30;
	private String _methodName35;
	private String[] _methodParameterTypes35;
	private String _methodName36;
	private String[] _methodParameterTypes36;
}