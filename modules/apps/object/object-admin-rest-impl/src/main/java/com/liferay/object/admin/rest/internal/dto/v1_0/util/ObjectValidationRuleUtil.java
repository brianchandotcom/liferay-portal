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

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRule;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectValidationRuleUtil {

	public static ObjectValidationRule toObjectValidationRule(
			Map<String, Map<String, String>> actions, Locale locale,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			com.liferay.object.model.ObjectValidationRule
				serviceBuilderObjectValidationRule)
		throws PortalException {

		if (serviceBuilderObjectValidationRule == null) {
			return null;
		}

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				serviceBuilderObjectValidationRule.getObjectDefinitionId());

		ObjectValidationRule objectValidationRule = new ObjectValidationRule() {
			{
				active = serviceBuilderObjectValidationRule.isActive();
				dateCreated =
					serviceBuilderObjectValidationRule.getCreateDate();
				dateModified =
					serviceBuilderObjectValidationRule.getModifiedDate();
				engine = serviceBuilderObjectValidationRule.getEngine();
				engineLabel = LanguageUtil.get(
					locale, serviceBuilderObjectValidationRule.getEngine());
				errorLabel = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectValidationRule.getErrorLabelMap());
				id =
					serviceBuilderObjectValidationRule.
						getObjectValidationRuleId();
				name = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectValidationRule.getNameMap());
				objectDefinitionExternalReferenceCode =
					objectDefinition.getExternalReferenceCode();
				objectDefinitionId =
					serviceBuilderObjectValidationRule.getObjectDefinitionId();
				script = serviceBuilderObjectValidationRule.getScript();
			}
		};

		objectValidationRule.setActions(actions);

		return objectValidationRule;
	}

}