/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceReturnConstants;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ObjectValidationRuleEngine.class)
public class CommerceReturnPermissionObjectValidationRuleEngineImpl
	extends BasePermissionObjectValidationRuleEngineImpl {

	@Override
	protected String getObjectDefinitionName() {
		return "CommerceReturn";
	}

	@Override
	protected boolean hasValidationCriteriaMet(Map<String, Object> inputObjects)
		throws Exception {

		Map<String, Object> originalEntryDTO =
			(Map<String, Object>)inputObjects.get("originalEntryDTO");

		if (MapUtil.isEmpty(originalEntryDTO)) {
			return true;
		}

		Map<String, Object> originalProperties =
			(Map<String, Object>)originalEntryDTO.get("properties");

		Map<String, String> returnItemStatusMap =
			(Map<String, String>)originalProperties.get("returnStatus");

		Map<String, Object> entryDTO = (Map<String, Object>)inputObjects.get(
			"entryDTO");

		Map<String, Object> properties = (Map<String, Object>)entryDTO.get(
			"properties");

		if (StringUtil.equalsIgnoreCase(
				returnItemStatusMap.get("key"),
				CommerceReturnConstants.RETURN_STATUS_DRAFT)) {

			if (StringUtil.equals(
					GetterUtil.getString(entryDTO.get("externalReferenceCode")),
					GetterUtil.getString(
						originalEntryDTO.get("externalReferenceCode")))) {

				return true;
			}

			return _portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), null,
				CommerceActionKeys.MANAGE_RETURNS);
		}

		if (StringUtil.equals(
				GetterUtil.getString(entryDTO.get("externalReferenceCode")),
				GetterUtil.getString(
					originalEntryDTO.get("externalReferenceCode"))) &&
			equals(originalProperties, properties, "returnStatus")) {

			return true;
		}

		return _portletResourcePermission.contains(
			PermissionThreadLocal.getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_RETURNS);
	}

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_COMMERCE_RETURN + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}