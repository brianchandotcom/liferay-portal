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

package com.liferay.object.admin.rest.internal.dto.v1_0.converter;

import com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRule;
import com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRuleSetting;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectValidationRule",
	service = DTOConverter.class
)
public class ObjectValidationRuleDTOConverter
	implements DTOConverter
		<com.liferay.object.model.ObjectValidationRule, ObjectValidationRule> {

	@Override
	public String getContentType() {
		return ObjectValidationRule.class.getSimpleName();
	}

	@Override
	public ObjectValidationRule toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectValidationRule
				serviceBuilderObjectValidationRule)
		throws PortalException {

		if (serviceBuilderObjectValidationRule == null) {
			return null;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				serviceBuilderObjectValidationRule.getObjectDefinitionId());

		return new ObjectValidationRule() {
			{
				actions = dtoConverterContext.getActions();
				active = serviceBuilderObjectValidationRule.isActive();
				dateCreated =
					serviceBuilderObjectValidationRule.getCreateDate();
				dateModified =
					serviceBuilderObjectValidationRule.getModifiedDate();
				engine = serviceBuilderObjectValidationRule.getEngine();
				engineLabel = _language.get(
					dtoConverterContext.getLocale(),
					serviceBuilderObjectValidationRule.getEngine());
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

				if (FeatureFlagManagerUtil.isEnabled("LPS-187846")) {
					objectValidationRuleSettings =
						TransformUtil.transformToArray(
							serviceBuilderObjectValidationRule.
								getObjectValidationRuleSettings(),
							objectValidationRuleSetting ->
								_toObjectValidationRuleSetting(
									objectValidationRuleSetting),
							ObjectValidationRuleSetting.class);
					outputType = ObjectValidationRule.OutputType.create(
						serviceBuilderObjectValidationRule.getOutputType());
				}

				script = serviceBuilderObjectValidationRule.getScript();
			}
		};
	}

	private ObjectValidationRuleSetting _toObjectValidationRuleSetting(
		com.liferay.object.model.ObjectValidationRuleSetting
			serviceBuilderObjectValidationRuleSetting) {

		if (serviceBuilderObjectValidationRuleSetting == null) {
			return null;
		}

		return new ObjectValidationRuleSetting() {
			{
				setName(
					() -> {
						if (serviceBuilderObjectValidationRuleSetting.
								compareName(
									ObjectValidationRuleSettingConstants.
										NAME_OBJECT_FIELD_ID)) {

							return ObjectValidationRuleSettingConstants.
								NAME_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE;
						}

						return serviceBuilderObjectValidationRuleSetting.
							getName();
					});
				setValue(
					() -> {
						if (serviceBuilderObjectValidationRuleSetting.
								compareName(
									ObjectValidationRuleSettingConstants.
										NAME_OBJECT_FIELD_ID)) {

							ObjectField objectField =
								_objectFieldLocalService.getObjectField(
									GetterUtil.getLong(
										serviceBuilderObjectValidationRuleSetting.
											getValue()));

							return objectField.getExternalReferenceCode();
						}

						return serviceBuilderObjectValidationRuleSetting.
							getValue();
					});
			}
		};
	}

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}