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

package com.liferay.portal.ac.profile.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.ac.profile.service.ServiceACProfileLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ServiceACProfileLocalServiceClpInvoker {
	public ServiceACProfileLocalServiceClpInvoker() {
		_methodName0 = "addServiceACProfile";

		_methodParameterTypes0 = new String[] {
				"com.liferay.portal.ac.profile.model.ServiceACProfile"
			};

		_methodName1 = "createServiceACProfile";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteServiceACProfile";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteServiceACProfile";

		_methodParameterTypes3 = new String[] {
				"com.liferay.portal.ac.profile.model.ServiceACProfile"
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

		_methodName10 = "fetchServiceACProfile";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "fetchServiceACProfileByUuidAndCompanyId";

		_methodParameterTypes11 = new String[] { "java.lang.String", "long" };

		_methodName12 = "getServiceACProfile";

		_methodParameterTypes12 = new String[] { "long" };

		_methodName13 = "getActionableDynamicQuery";

		_methodParameterTypes13 = new String[] {  };

		_methodName15 = "getExportActionableDynamicQuery";

		_methodParameterTypes15 = new String[] {
				"com.liferay.portal.kernel.lar.PortletDataContext"
			};

		_methodName16 = "deletePersistedModel";

		_methodParameterTypes16 = new String[] {
				"com.liferay.portal.model.PersistedModel"
			};

		_methodName17 = "getPersistedModel";

		_methodParameterTypes17 = new String[] { "java.io.Serializable" };

		_methodName18 = "getServiceACProfileByUuidAndCompanyId";

		_methodParameterTypes18 = new String[] { "java.lang.String", "long" };

		_methodName19 = "getServiceACProfiles";

		_methodParameterTypes19 = new String[] { "int", "int" };

		_methodName20 = "getServiceACProfilesCount";

		_methodParameterTypes20 = new String[] {  };

		_methodName21 = "updateServiceACProfile";

		_methodParameterTypes21 = new String[] {
				"com.liferay.portal.ac.profile.model.ServiceACProfile"
			};

		_methodName44 = "getBeanIdentifier";

		_methodParameterTypes44 = new String[] {  };

		_methodName45 = "setBeanIdentifier";

		_methodParameterTypes45 = new String[] { "java.lang.String" };

		_methodName50 = "addServiceACProfile";

		_methodParameterTypes50 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.util.Map", "com.liferay.portal.service.ServiceContext"
			};

		_methodName51 = "addServiceACProfileResources";

		_methodParameterTypes51 = new String[] {
				"com.liferay.portal.ac.profile.model.ServiceACProfile",
				"boolean", "boolean"
			};

		_methodName52 = "addServiceACProfileResources";

		_methodParameterTypes52 = new String[] {
				"com.liferay.portal.ac.profile.model.ServiceACProfile",
				"java.lang.String[][]", "java.lang.String[][]"
			};

		_methodName53 = "deleteServiceACProfile";

		_methodParameterTypes53 = new String[] {
				"com.liferay.portal.ac.profile.model.ServiceACProfile"
			};

		_methodName54 = "deleteServiceACProfile";

		_methodParameterTypes54 = new String[] { "long" };

		_methodName55 = "getCompanyServiceACProfiles";

		_methodParameterTypes55 = new String[] {
				"long", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName56 = "getCompanyServiceACProfilesCount";

		_methodParameterTypes56 = new String[] { "long" };

		_methodName57 = "getServiceACProfile";

		_methodParameterTypes57 = new String[] { "long", "java.lang.String" };

		_methodName58 = "updateServiceACProfile";

		_methodParameterTypes58 = new String[] {
				"long", "java.lang.String", "java.lang.String", "java.util.Map",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName59 = "updateServiceACProfileResources";

		_methodParameterTypes59 = new String[] {
				"com.liferay.portal.ac.profile.model.ServiceACProfile",
				"java.lang.String[][]", "java.lang.String[][]"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.addServiceACProfile((com.liferay.portal.ac.profile.model.ServiceACProfile)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.createServiceACProfile(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.deleteServiceACProfile(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.deleteServiceACProfile((com.liferay.portal.ac.profile.model.ServiceACProfile)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator<?>)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.fetchServiceACProfile(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.fetchServiceACProfileByUuidAndCompanyId((java.lang.String)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getServiceACProfile(((Long)arguments[0]).longValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getActionableDynamicQuery();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getExportActionableDynamicQuery((com.liferay.portal.kernel.lar.PortletDataContext)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.deletePersistedModel((com.liferay.portal.model.PersistedModel)arguments[0]);
		}

		if (_methodName17.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes17, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName18.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes18, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getServiceACProfileByUuidAndCompanyId((java.lang.String)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName19.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes19, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getServiceACProfiles(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName20.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes20, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getServiceACProfilesCount();
		}

		if (_methodName21.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes21, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.updateServiceACProfile((com.liferay.portal.ac.profile.model.ServiceACProfile)arguments[0]);
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName45.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes45, parameterTypes)) {
			ServiceACProfileLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName50.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes50, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.addServiceACProfile(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2], (java.lang.String)arguments[3],
				(java.util.Map<java.util.Locale, java.lang.String>)arguments[4],
				(com.liferay.portal.service.ServiceContext)arguments[5]);
		}

		if (_methodName51.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes51, parameterTypes)) {
			ServiceACProfileLocalServiceUtil.addServiceACProfileResources((com.liferay.portal.ac.profile.model.ServiceACProfile)arguments[0],
				((Boolean)arguments[1]).booleanValue(),
				((Boolean)arguments[2]).booleanValue());

			return null;
		}

		if (_methodName52.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes52, parameterTypes)) {
			ServiceACProfileLocalServiceUtil.addServiceACProfileResources((com.liferay.portal.ac.profile.model.ServiceACProfile)arguments[0],
				(java.lang.String[])arguments[1],
				(java.lang.String[])arguments[2]);

			return null;
		}

		if (_methodName53.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes53, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.deleteServiceACProfile((com.liferay.portal.ac.profile.model.ServiceACProfile)arguments[0]);
		}

		if (_methodName54.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes54, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.deleteServiceACProfile(((Long)arguments[0]).longValue());
		}

		if (_methodName55.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes55, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getCompanyServiceACProfiles(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile>)arguments[3]);
		}

		if (_methodName56.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes56, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getCompanyServiceACProfilesCount(((Long)arguments[0]).longValue());
		}

		if (_methodName57.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes57, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.getServiceACProfile(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName58.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes58, parameterTypes)) {
			return ServiceACProfileLocalServiceUtil.updateServiceACProfile(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.util.Map<java.util.Locale, java.lang.String>)arguments[3],
				(com.liferay.portal.service.ServiceContext)arguments[4]);
		}

		if (_methodName59.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes59, parameterTypes)) {
			ServiceACProfileLocalServiceUtil.updateServiceACProfileResources((com.liferay.portal.ac.profile.model.ServiceACProfile)arguments[0],
				(java.lang.String[])arguments[1],
				(java.lang.String[])arguments[2]);

			return null;
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
	private String _methodName20;
	private String[] _methodParameterTypes20;
	private String _methodName21;
	private String[] _methodParameterTypes21;
	private String _methodName44;
	private String[] _methodParameterTypes44;
	private String _methodName45;
	private String[] _methodParameterTypes45;
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
}