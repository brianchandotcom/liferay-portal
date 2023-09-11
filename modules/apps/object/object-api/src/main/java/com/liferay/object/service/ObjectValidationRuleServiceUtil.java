/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.object.model.ObjectValidationRule;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for ObjectValidationRule. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectValidationRuleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectValidationRuleService
 * @generated
 */
public class ObjectValidationRuleServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectValidationRuleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectValidationRule addObjectValidationRule(
			long objectDefinitionId, boolean active, String engine,
			Map<java.util.Locale, String> errorLabelMap,
			Map<java.util.Locale, String> nameMap, String outputType,
			String script, boolean system,
			List<com.liferay.object.model.ObjectValidationRuleSetting>
				objectValidationRuleSettings)
		throws PortalException {

		return getService().addObjectValidationRule(
			objectDefinitionId, active, engine, errorLabelMap, nameMap,
			outputType, script, system, objectValidationRuleSettings);
	}

	public static ObjectValidationRule deleteObjectValidationRule(
			long objectValidationRuleId)
		throws PortalException {

		return getService().deleteObjectValidationRule(objectValidationRuleId);
	}

	public static ObjectValidationRule getObjectValidationRule(
			long objectValidationRuleId)
		throws PortalException {

		return getService().getObjectValidationRule(objectValidationRuleId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ObjectValidationRule updateObjectValidationRule(
			long objectValidationRuleId, boolean active, String engine,
			Map<java.util.Locale, String> errorLabelMap,
			Map<java.util.Locale, String> nameMap, String outputType,
			String script,
			List<com.liferay.object.model.ObjectValidationRuleSetting>
				objectValidationRuleSettings)
		throws PortalException {

		return getService().updateObjectValidationRule(
			objectValidationRuleId, active, engine, errorLabelMap, nameMap,
			outputType, script, objectValidationRuleSettings);
	}

	public static ObjectValidationRuleService getService() {
		return _service;
	}

	public static void setService(ObjectValidationRuleService service) {
		_service = service;
	}

	private static volatile ObjectValidationRuleService _service;

}