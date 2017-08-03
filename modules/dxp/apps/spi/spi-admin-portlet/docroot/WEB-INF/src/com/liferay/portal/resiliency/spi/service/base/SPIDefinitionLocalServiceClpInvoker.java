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

import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Michael C. Han
 * @generated
 */
@ProviderType
public class SPIDefinitionLocalServiceClpInvoker {
	public SPIDefinitionLocalServiceClpInvoker() {
		_methodName0 = "addSPIDefinition";

		_methodParameterTypes0 = new String[] {
				"com.liferay.portal.resiliency.spi.model.SPIDefinition"
			};

		_methodName1 = "createSPIDefinition";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteSPIDefinition";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteSPIDefinition";

		_methodParameterTypes3 = new String[] {
				"com.liferay.portal.resiliency.spi.model.SPIDefinition"
			};

		_methodName4 = "dynamicQuery";

		_methodParameterTypes4 = new String[] {  };

		_methodName5 = "dynamicQuery";

		_methodParameterTypes5 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName6 = "dynamicQuery";

		_methodParameterTypes6 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int"
			};

		_methodName7 = "dynamicQuery";

		_methodParameterTypes7 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName8 = "dynamicQueryCount";

		_methodParameterTypes8 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName9 = "dynamicQueryCount";

		_methodParameterTypes9 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery",
				"com.liferay.portal.kernel.dao.orm.Projection"
			};

		_methodName10 = "fetchSPIDefinition";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getSPIDefinition";

		_methodParameterTypes11 = new String[] { "long" };

		_methodName12 = "getActionableDynamicQuery";

		_methodParameterTypes12 = new String[] {  };

		_methodName13 = "getIndexableActionableDynamicQuery";

		_methodParameterTypes13 = new String[] {  };

		_methodName15 = "deletePersistedModel";

		_methodParameterTypes15 = new String[] {
				"com.liferay.portal.kernel.model.PersistedModel"
			};

		_methodName16 = "getPersistedModel";

		_methodParameterTypes16 = new String[] { "java.io.Serializable" };

		_methodName17 = "getSPIDefinitions";

		_methodParameterTypes17 = new String[] { "int", "int" };

		_methodName18 = "getSPIDefinitionsCount";

		_methodParameterTypes18 = new String[] {  };

		_methodName19 = "updateSPIDefinition";

		_methodParameterTypes19 = new String[] {
				"com.liferay.portal.resiliency.spi.model.SPIDefinition"
			};

		_methodName42 = "getOSGiServiceIdentifier";

		_methodParameterTypes42 = new String[] {  };

		_methodName47 = "addSPIDefinition";

		_methodParameterTypes47 = new String[] {
				"long", "java.lang.String", "java.lang.String", "int",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName48 = "deleteSPIDefinition";

		_methodParameterTypes48 = new String[] { "long" };

		_methodName49 = "deleteSPIDefinition";

		_methodParameterTypes49 = new String[] {
				"com.liferay.portal.resiliency.spi.model.SPIDefinition"
			};

		_methodName50 = "getPortletIdsAndServletContextNames";

		_methodParameterTypes50 = new String[] {  };

		_methodName51 = "getSPIDefinition";

		_methodParameterTypes51 = new String[] { "long" };

		_methodName52 = "getSPIDefinition";

		_methodParameterTypes52 = new String[] { "long", "java.lang.String" };

		_methodName53 = "getSPIDefinitions";

		_methodParameterTypes53 = new String[] {  };

		_methodName54 = "getSPIDefinitions";

		_methodParameterTypes54 = new String[] { "long", "int" };

		_methodName55 = "getSPIDefinitions";

		_methodParameterTypes55 = new String[] { "long", "int[][]" };

		_methodName56 = "startSPI";

		_methodParameterTypes56 = new String[] { "long" };

		_methodName57 = "startSPIinBackground";

		_methodParameterTypes57 = new String[] { "long", "long" };

		_methodName58 = "startSPIinBackground";

		_methodParameterTypes58 = new String[] { "long", "long", "boolean" };

		_methodName59 = "stopSPI";

		_methodParameterTypes59 = new String[] { "long" };

		_methodName60 = "stopSPIinBackground";

		_methodParameterTypes60 = new String[] { "long", "long" };

		_methodName61 = "updateSPIDefinition";

		_methodParameterTypes61 = new String[] { "long", "int", "java.lang.String" };

		_methodName62 = "updateSPIDefinition";

		_methodParameterTypes62 = new String[] {
				"long", "long", "java.lang.String", "int", "java.lang.String",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName63 = "updateTypeSettings";

		_methodParameterTypes63 = new String[] {
				"long", "long", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.addSPIDefinition((com.liferay.portal.resiliency.spi.model.SPIDefinition)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.createSPIDefinition(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.deleteSPIDefinition(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.deleteSPIDefinition((com.liferay.portal.resiliency.spi.model.SPIDefinition)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator<?>)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.fetchSPIDefinition(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getSPIDefinition(((Long)arguments[0]).longValue());
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getActionableDynamicQuery();
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getIndexableActionableDynamicQuery();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.deletePersistedModel((com.liferay.portal.kernel.model.PersistedModel)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName17.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes17, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getSPIDefinitions(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName18.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes18, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getSPIDefinitionsCount();
		}

		if (_methodName19.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes19, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.updateSPIDefinition((com.liferay.portal.resiliency.spi.model.SPIDefinition)arguments[0]);
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName47.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes47, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.addSPIDefinition(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				((Integer)arguments[3]).intValue(),
				(java.lang.String)arguments[4], (java.lang.String)arguments[5],
				(java.lang.String)arguments[6], (java.lang.String)arguments[7],
				(java.lang.String)arguments[8],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[9]);
		}

		if (_methodName48.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes48, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.deleteSPIDefinition(((Long)arguments[0]).longValue());
		}

		if (_methodName49.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes49, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.deleteSPIDefinition((com.liferay.portal.resiliency.spi.model.SPIDefinition)arguments[0]);
		}

		if (_methodName50.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes50, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getPortletIdsAndServletContextNames();
		}

		if (_methodName51.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes51, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getSPIDefinition(((Long)arguments[0]).longValue());
		}

		if (_methodName52.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes52, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getSPIDefinition(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName53.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes53, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getSPIDefinitions();
		}

		if (_methodName54.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes54, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getSPIDefinitions(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName55.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes55, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.getSPIDefinitions(((Long)arguments[0]).longValue(),
				(int[])arguments[1]);
		}

		if (_methodName56.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes56, parameterTypes)) {
			SPIDefinitionLocalServiceUtil.startSPI(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName57.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes57, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.startSPIinBackground(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName58.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes58, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.startSPIinBackground(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Boolean)arguments[2]).booleanValue());
		}

		if (_methodName59.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes59, parameterTypes)) {
			SPIDefinitionLocalServiceUtil.stopSPI(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName60.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes60, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.stopSPIinBackground(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName61.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes61, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.updateSPIDefinition(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				(java.lang.String)arguments[2]);
		}

		if (_methodName62.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes62, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.updateSPIDefinition(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2],
				((Integer)arguments[3]).intValue(),
				(java.lang.String)arguments[4], (java.lang.String)arguments[5],
				(java.lang.String)arguments[6], (java.lang.String)arguments[7],
				(java.lang.String)arguments[8],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[9]);
		}

		if (_methodName63.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes63, parameterTypes)) {
			return SPIDefinitionLocalServiceUtil.updateTypeSettings(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[3]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName0;
	private String[] _methodParameterTypes0;
	private String _methodName1;
	private String[] _methodParameterTypes1;
	private String _methodName2;
	private String[] _methodParameterTypes2;
	private String _methodName3;
	private String[] _methodParameterTypes3;
	private String _methodName4;
	private String[] _methodParameterTypes4;
	private String _methodName5;
	private String[] _methodParameterTypes5;
	private String _methodName6;
	private String[] _methodParameterTypes6;
	private String _methodName7;
	private String[] _methodParameterTypes7;
	private String _methodName8;
	private String[] _methodParameterTypes8;
	private String _methodName9;
	private String[] _methodParameterTypes9;
	private String _methodName10;
	private String[] _methodParameterTypes10;
	private String _methodName11;
	private String[] _methodParameterTypes11;
	private String _methodName12;
	private String[] _methodParameterTypes12;
	private String _methodName13;
	private String[] _methodParameterTypes13;
	private String _methodName15;
	private String[] _methodParameterTypes15;
	private String _methodName16;
	private String[] _methodParameterTypes16;
	private String _methodName17;
	private String[] _methodParameterTypes17;
	private String _methodName18;
	private String[] _methodParameterTypes18;
	private String _methodName19;
	private String[] _methodParameterTypes19;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName47;
	private String[] _methodParameterTypes47;
	private String _methodName48;
	private String[] _methodParameterTypes48;
	private String _methodName49;
	private String[] _methodParameterTypes49;
	private String _methodName50;
	private String[] _methodParameterTypes50;
	private String _methodName51;
	private String[] _methodParameterTypes51;
	private String _methodName52;
	private String[] _methodParameterTypes52;
	private String _methodName53;
	private String[] _methodParameterTypes53;
	private String _methodName54;
	private String[] _methodParameterTypes54;
	private String _methodName55;
	private String[] _methodParameterTypes55;
	private String _methodName56;
	private String[] _methodParameterTypes56;
	private String _methodName57;
	private String[] _methodParameterTypes57;
	private String _methodName58;
	private String[] _methodParameterTypes58;
	private String _methodName59;
	private String[] _methodParameterTypes59;
	private String _methodName60;
	private String[] _methodParameterTypes60;
	private String _methodName61;
	private String[] _methodParameterTypes61;
	private String _methodName62;
	private String[] _methodParameterTypes62;
	private String _methodName63;
	private String[] _methodParameterTypes63;
}