/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.definition.processor;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.definition.processor.ObjectDefinitionClassNameProcessor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = ObjectDefinitionClassNameProcessor.class)
public class ObjectDefinitionClassNameProcessorImpl
	implements ObjectDefinitionClassNameProcessor {

	@Override
	public long updateObjectDefinitionClassNameId(long classNameId) {
		ObjectDefinition objectDefinition = _getObjectDefinition(
			ObjectDefinitionSettingConstants.NAME_OLD_CLASS_NAME_ID,
			String.valueOf(classNameId));

		if (objectDefinition == null) {
			return classNameId;
		}

		return classNameLocalService.getClassNameId(
			objectDefinition.getClassName());
	}

	@Override
	public String updateObjectDefinitionReferences(String value) {
		value = _updateObjectDefinitionReferences(
			ObjectDefinition::getClassName, _classNamePattern, value);
		value = _updateObjectDefinitionReferences(
			ObjectDefinition::getPortletId, _portletIdPattern, value);

		return _updateObjectDefinitionJSONClassNameId(value);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	@Reference
	protected ObjectDefinitionSettingLocalService
		objectDefinitionSettingLocalService;

	private ObjectDefinition _getObjectDefinition(
		String objectDefinitionSettingName,
		String objectDefinitionSettingValue) {

		ObjectDefinitionSetting objectDefinitionSetting =
			objectDefinitionSetting =
				objectDefinitionSettingLocalService.
					fetchObjectDefinitionSetting(
						CompanyThreadLocal.getCompanyId(),
						objectDefinitionSettingName,
						objectDefinitionSettingValue);

		if (objectDefinitionSetting == null) {
			return null;
		}

		return objectDefinitionLocalService.fetchObjectDefinition(
			objectDefinitionSetting.getObjectDefinitionId());
	}

	private String _updateObjectDefinitionJSONClassNameId(String json) {
		StringBuilder sb = new StringBuilder();

		Matcher matcher = _jsonClassNameIdPattern.matcher(json);

		while (matcher.find()) {
			ObjectDefinition objectDefinition = _getObjectDefinition(
				ObjectDefinitionSettingConstants.NAME_OLD_CLASS_NAME_ID,
				matcher.group(2));

			if (objectDefinition == null) {
				continue;
			}

			long classNameId = classNameLocalService.getClassNameId(
				objectDefinition.getClassName());

			matcher.appendReplacement(
				sb,
				Matcher.quoteReplacement(
					matcher.group(1) + classNameId + matcher.group(3)));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private String _updateObjectDefinitionReferences(
		Function<ObjectDefinition, String> function, Pattern pattern,
		String value) {

		StringBuilder sb = new StringBuilder();

		Matcher matcher = pattern.matcher(value);

		while (matcher.find()) {
			ObjectDefinition objectDefinition = _getObjectDefinition(
				ObjectDefinitionSettingConstants.NAME_OLD_CLASS_NAME,
				ObjectDefinitionConstants.
					CLASS_NAME_PREFIX_CUSTOM_OBJECT_DEFINITION +
						matcher.group(2));

			if (objectDefinition == null) {
				continue;
			}

			matcher.appendReplacement(
				sb, Matcher.quoteReplacement(function.apply(objectDefinition)));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private static final Pattern _classNamePattern = Pattern.compile(
		"(com\\.liferay\\.object\\.model\\.ObjectDefinition#)([a-zA-Z]\\d" +
			"[a-zA-Z]\\d)");
	private static final Pattern _jsonClassNameIdPattern = Pattern.compile(
		"(\"classNameId\"\\s*:\\s*\")(\\d+)(\")");
	private static final Pattern _portletIdPattern = Pattern.compile(
		"(com_liferay_object_web_internal_object_definitions_portlet_" +
			"ObjectDefinitionsPortlet_)([a-zA-Z]\\d[a-zA-Z]\\d)");

}