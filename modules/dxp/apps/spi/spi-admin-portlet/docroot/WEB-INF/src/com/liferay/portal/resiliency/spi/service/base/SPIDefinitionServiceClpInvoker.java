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

package com.liferay.portal.resiliency.spi.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.resiliency.spi.service.SPIDefinitionServiceUtil;

import java.util.Arrays;

/**
 * @author Michael C. Han
 * @generated
 */
@ProviderType
public class SPIDefinitionServiceClpInvoker {
	public SPIDefinitionServiceClpInvoker() {
		_methodName28 = "getOSGiServiceIdentifier";

		_methodParameterTypes28 = new String[] {  };

		_methodName33 = "addSPIDefinition";

		_methodParameterTypes33 = new String[] {
				"java.lang.String", "java.lang.String", "int",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName34 = "deleteSPIDefinition";

		_methodParameterTypes34 = new String[] { "long" };

		_methodName35 = "getPortletIdsAndServletContextNames";

		_methodParameterTypes35 = new String[] {  };

		_methodName36 = "getSPIDefinition";

		_methodParameterTypes36 = new String[] { "long" };

		_methodName37 = "getSPIDefinition";

		_methodParameterTypes37 = new String[] { "long", "java.lang.String" };

		_methodName38 = "getSPIDefinitions";

		_methodParameterTypes38 = new String[] {  };

		_methodName39 = "startSPI";

		_methodParameterTypes39 = new String[] { "long" };

		_methodName40 = "startSPIinBackground";

		_methodParameterTypes40 = new String[] { "long" };

		_methodName41 = "stopSPI";

		_methodParameterTypes41 = new String[] { "long" };

		_methodName42 = "stopSPIinBackground";

		_methodParameterTypes42 = new String[] { "long" };

		_methodName43 = "updateSPIDefinition";

		_methodParameterTypes43 = new String[] {
				"long", "java.lang.String", "int", "java.lang.String",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName44 = "updateTypeSettings";

		_methodParameterTypes44 = new String[] {
				"long", "long", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName28.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes28, parameterTypes)) {
			return SPIDefinitionServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName33.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes33, parameterTypes)) {
			return SPIDefinitionServiceUtil.addSPIDefinition((java.lang.String)arguments[0],
				(java.lang.String)arguments[1],
				((Integer)arguments[2]).intValue(),
				(java.lang.String)arguments[3], (java.lang.String)arguments[4],
				(java.lang.String)arguments[5], (java.lang.String)arguments[6],
				(java.lang.String)arguments[7],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[8]);
		}

		if (_methodName34.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes34, parameterTypes)) {
			return SPIDefinitionServiceUtil.deleteSPIDefinition(((Long)arguments[0]).longValue());
		}

		if (_methodName35.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes35, parameterTypes)) {
			return SPIDefinitionServiceUtil.getPortletIdsAndServletContextNames();
		}

		if (_methodName36.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes36, parameterTypes)) {
			return SPIDefinitionServiceUtil.getSPIDefinition(((Long)arguments[0]).longValue());
		}

		if (_methodName37.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes37, parameterTypes)) {
			return SPIDefinitionServiceUtil.getSPIDefinition(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName38.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes38, parameterTypes)) {
			return SPIDefinitionServiceUtil.getSPIDefinitions();
		}

		if (_methodName39.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes39, parameterTypes)) {
			SPIDefinitionServiceUtil.startSPI(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName40.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes40, parameterTypes)) {
			return SPIDefinitionServiceUtil.startSPIinBackground(((Long)arguments[0]).longValue());
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			SPIDefinitionServiceUtil.stopSPI(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return SPIDefinitionServiceUtil.stopSPIinBackground(((Long)arguments[0]).longValue());
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			return SPIDefinitionServiceUtil.updateSPIDefinition(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				((Integer)arguments[2]).intValue(),
				(java.lang.String)arguments[3], (java.lang.String)arguments[4],
				(java.lang.String)arguments[5], (java.lang.String)arguments[6],
				(java.lang.String)arguments[7],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[8]);
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			return SPIDefinitionServiceUtil.updateTypeSettings(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[3]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName28;
	private String[] _methodParameterTypes28;
	private String _methodName33;
	private String[] _methodParameterTypes33;
	private String _methodName34;
	private String[] _methodParameterTypes34;
	private String _methodName35;
	private String[] _methodParameterTypes35;
	private String _methodName36;
	private String[] _methodParameterTypes36;
	private String _methodName37;
	private String[] _methodParameterTypes37;
	private String _methodName38;
	private String[] _methodParameterTypes38;
	private String _methodName39;
	private String[] _methodParameterTypes39;
	private String _methodName40;
	private String[] _methodParameterTypes40;
	private String _methodName41;
	private String[] _methodParameterTypes41;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName43;
	private String[] _methodParameterTypes43;
	private String _methodName44;
	private String[] _methodParameterTypes44;
}