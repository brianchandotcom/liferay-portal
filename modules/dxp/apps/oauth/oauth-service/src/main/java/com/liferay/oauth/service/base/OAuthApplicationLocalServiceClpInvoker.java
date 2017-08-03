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

import com.liferay.oauth.service.OAuthApplicationLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class OAuthApplicationLocalServiceClpInvoker {
	public OAuthApplicationLocalServiceClpInvoker() {
		_methodName0 = "addOAuthApplication";

		_methodParameterTypes0 = new String[] {
				"com.liferay.oauth.model.OAuthApplication"
			};

		_methodName1 = "createOAuthApplication";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteOAuthApplication";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteOAuthApplication";

		_methodParameterTypes3 = new String[] {
				"com.liferay.oauth.model.OAuthApplication"
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

		_methodName10 = "fetchOAuthApplication";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getOAuthApplication";

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

		_methodName17 = "getOAuthApplications";

		_methodParameterTypes17 = new String[] { "int", "int" };

		_methodName18 = "getOAuthApplicationsCount";

		_methodParameterTypes18 = new String[] {  };

		_methodName19 = "updateOAuthApplication";

		_methodParameterTypes19 = new String[] {
				"com.liferay.oauth.model.OAuthApplication"
			};

		_methodName46 = "getOSGiServiceIdentifier";

		_methodParameterTypes46 = new String[] {  };

		_methodName51 = "addOAuthApplication";

		_methodParameterTypes51 = new String[] {
				"long", "java.lang.String", "java.lang.String", "int", "boolean",
				"java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName52 = "deleteLogo";

		_methodParameterTypes52 = new String[] { "long" };

		_methodName53 = "deleteOAuthApplication";

		_methodParameterTypes53 = new String[] { "long" };

		_methodName54 = "deleteOAuthApplication";

		_methodParameterTypes54 = new String[] {
				"com.liferay.oauth.model.OAuthApplication"
			};

		_methodName55 = "fetchOAuthApplication";

		_methodParameterTypes55 = new String[] { "java.lang.String" };

		_methodName56 = "getOAuthApplication";

		_methodParameterTypes56 = new String[] { "java.lang.String" };

		_methodName57 = "getOAuthApplications";

		_methodParameterTypes57 = new String[] {
				"long", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName58 = "getOAuthApplicationsCount";

		_methodParameterTypes58 = new String[] { "long" };

		_methodName59 = "search";

		_methodParameterTypes59 = new String[] {
				"long", "java.lang.String", "java.util.LinkedHashMap", "int",
				"int", "com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName60 = "searchCount";

		_methodParameterTypes60 = new String[] {
				"long", "java.lang.String", "java.util.LinkedHashMap"
			};

		_methodName61 = "updateLogo";

		_methodParameterTypes61 = new String[] { "long", "java.io.InputStream" };

		_methodName62 = "updateOAuthApplication";

		_methodParameterTypes62 = new String[] {
				"long", "java.lang.String", "java.lang.String", "boolean",
				"java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.addOAuthApplication((com.liferay.oauth.model.OAuthApplication)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.createOAuthApplication(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.deleteOAuthApplication(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.deleteOAuthApplication((com.liferay.oauth.model.OAuthApplication)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator<?>)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.fetchOAuthApplication(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getOAuthApplication(((Long)arguments[0]).longValue());
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getActionableDynamicQuery();
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getIndexableActionableDynamicQuery();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.deletePersistedModel((com.liferay.portal.kernel.model.PersistedModel)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName17.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes17, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getOAuthApplications(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName18.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes18, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getOAuthApplicationsCount();
		}

		if (_methodName19.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes19, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.updateOAuthApplication((com.liferay.oauth.model.OAuthApplication)arguments[0]);
		}

		if (_methodName46.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes46, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName51.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes51, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.addOAuthApplication(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				((Integer)arguments[3]).intValue(),
				((Boolean)arguments[4]).booleanValue(),
				(java.lang.String)arguments[5], (java.lang.String)arguments[6],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[7]);
		}

		if (_methodName52.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes52, parameterTypes)) {
			OAuthApplicationLocalServiceUtil.deleteLogo(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName53.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes53, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.deleteOAuthApplication(((Long)arguments[0]).longValue());
		}

		if (_methodName54.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes54, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.deleteOAuthApplication((com.liferay.oauth.model.OAuthApplication)arguments[0]);
		}

		if (_methodName55.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes55, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.fetchOAuthApplication((java.lang.String)arguments[0]);
		}

		if (_methodName56.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes56, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getOAuthApplication((java.lang.String)arguments[0]);
		}

		if (_methodName57.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes57, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getOAuthApplications(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName58.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes58, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.getOAuthApplicationsCount(((Long)arguments[0]).longValue());
		}

		if (_methodName59.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes59, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.search(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				(java.util.LinkedHashMap<java.lang.String, java.lang.Object>)arguments[2],
				((Integer)arguments[3]).intValue(),
				((Integer)arguments[4]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[5]);
		}

		if (_methodName60.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes60, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.searchCount(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				(java.util.LinkedHashMap<java.lang.String, java.lang.Object>)arguments[2]);
		}

		if (_methodName61.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes61, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.updateLogo(((Long)arguments[0]).longValue(),
				(java.io.InputStream)arguments[1]);
		}

		if (_methodName62.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes62, parameterTypes)) {
			return OAuthApplicationLocalServiceUtil.updateOAuthApplication(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				((Boolean)arguments[3]).booleanValue(),
				(java.lang.String)arguments[4], (java.lang.String)arguments[5],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[6]);
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
	private String _methodName46;
	private String[] _methodParameterTypes46;
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
}