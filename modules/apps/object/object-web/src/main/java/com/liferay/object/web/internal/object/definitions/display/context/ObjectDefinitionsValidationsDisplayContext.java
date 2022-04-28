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

package com.liferay.object.web.internal.object.definitions.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineServicesTracker;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Selton Guedes
 */
public class ObjectDefinitionsValidationsDisplayContext
	extends BaseObjectDefinitionsDisplayContext {

	public ObjectDefinitionsValidationsDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission,
		ObjectValidationRuleEngineServicesTracker
			objectValidationRuleEngineServicesTracker) {

		super(httpServletRequest, objectDefinitionModelResourcePermission);

		_objectValidationRuleEngineServicesTracker =
			objectValidationRuleEngineServicesTracker;
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					getPortletURL()
				).setMVCRenderCommandName(
					"/object_definitions/edit_object_validation_rule"
				).setParameter(
					"objectValidationRuleId", "{id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"view", "view",
				LanguageUtil.get(objectRequestHelper.getRequest(), "view"),
				"get", null, "sidePanel"),
			new FDSActionDropdownItem(
				"/o/object-admin/v1.0/object-validation-rules/{id}", "trash",
				"delete",
				LanguageUtil.get(objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	public List<Map<String, String>> getObjectValidationRuleEngines() {
		return Stream.of(
			_objectValidationRuleEngineServicesTracker.
				getObjectValidationRuleEngines()
		).flatMap(
			List::stream
		).map(
			objectValidationRuleEngine -> HashMapBuilder.put(
				"label",
				LanguageUtil.get(
					objectRequestHelper.getLocale(),
					objectValidationRuleEngine.getName())
			).put(
				"name", objectValidationRuleEngine.getName()
			).build()
		).sorted(
			Comparator.comparing(item -> item.get("label"))
		).collect(
			Collectors.toList()
		);
	}

	public JSONObject getObjectValidationRuleJSONObject(
		ObjectValidationRule objectValidationRule) {

		return JSONUtil.put(
			"active", objectValidationRule.isActive()
		).put(
			"engine", objectValidationRule.getEngine()
		).put(
			"engineLabel",
			LanguageUtil.get(
				objectRequestHelper.getLocale(),
				objectValidationRule.getEngine())
		).put(
			"errorLabel",
			LocalizationUtil.getLocalizationMap(
				objectValidationRule.getErrorLabel())
		).put(
			"id", objectValidationRule.getObjectValidationRuleId()
		).put(
			"name",
			LocalizationUtil.getLocalizationMap(objectValidationRule.getName())
		).put(
			"script", objectValidationRule.getScript()
		);
	}

	public HashMap<String, Object> getProps(
			ObjectValidationRule objectValidationRule)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"objectValidationRule",
			getObjectValidationRuleJSONObject(objectValidationRule)
		).put(
			"objectValidationRuleEngines", getObjectValidationRuleEngines()
		).put(
			"readOnly", !hasUpdateObjectDefinitionPermission()
		).build();
	}

	@Override
	protected String getAPIURI() {
		return "/object-validation-rules";
	}

	@Override
	protected UnsafeConsumer<DropdownItem, Exception>
		getCreationMenuDropdownItemUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref("addObjectValidation");
			dropdownItem.setLabel(
				LanguageUtil.get(
					objectRequestHelper.getRequest(), "add-object-validation"));
			dropdownItem.setTarget("event");
		};
	}

	private final ObjectValidationRuleEngineServicesTracker
		_objectValidationRuleEngineServicesTracker;

}