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

import com.liferay.oauth.service.OAuthApplicationServiceUtil;

import java.util.Arrays;

/**
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class OAuthApplicationServiceClpInvoker {
	public OAuthApplicationServiceClpInvoker() {
		_methodName36 = "getOSGiServiceIdentifier";

		_methodParameterTypes36 = new String[] {  };

		_methodName41 = "addOAuthApplication";

		_methodParameterTypes41 = new String[] {
				"java.lang.String", "java.lang.String", "int", "boolean",
				"java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName42 = "deleteLogo";

		_methodParameterTypes42 = new String[] { "long" };

		_methodName43 = "deleteOAuthApplication";

		_methodParameterTypes43 = new String[] { "long" };

		_methodName44 = "updateLogo";

		_methodParameterTypes44 = new String[] { "long", "java.io.InputStream" };

		_methodName45 = "updateOAuthApplication";

		_methodParameterTypes45 = new String[] {
				"long", "java.lang.String", "java.lang.String", "boolean",
				"java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName36.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes36, parameterTypes)) {
			return OAuthApplicationServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			return OAuthApplicationServiceUtil.addOAuthApplication((java.lang.String)arguments[0],
				(java.lang.String)arguments[1],
				((Integer)arguments[2]).intValue(),
				((Boolean)arguments[3]).booleanValue(),
				(java.lang.String)arguments[4], (java.lang.String)arguments[5],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[6]);
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			OAuthApplicationServiceUtil.deleteLogo(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			return OAuthApplicationServiceUtil.deleteOAuthApplication(((Long)arguments[0]).longValue());
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			return OAuthApplicationServiceUtil.updateLogo(((Long)arguments[0]).longValue(),
				(java.io.InputStream)arguments[1]);
		}

		if (_methodName45.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes45, parameterTypes)) {
			return OAuthApplicationServiceUtil.updateOAuthApplication(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				((Boolean)arguments[3]).booleanValue(),
				(java.lang.String)arguments[4], (java.lang.String)arguments[5],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[6]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName36;
	private String[] _methodParameterTypes36;
	private String _methodName41;
	private String[] _methodParameterTypes41;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName43;
	private String[] _methodParameterTypes43;
	private String _methodName44;
	private String[] _methodParameterTypes44;
	private String _methodName45;
	private String[] _methodParameterTypes45;
}