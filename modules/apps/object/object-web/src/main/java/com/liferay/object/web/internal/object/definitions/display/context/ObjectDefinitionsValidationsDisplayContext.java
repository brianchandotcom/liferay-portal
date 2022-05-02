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
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineServicesTracker;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

	public Map<String, Object> getProps(
			ObjectValidationRule objectValidationRule)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"objectValidationRule",
			HashMapBuilder.<String, Object>put(
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
				LocalizationUtil.getLocalizationMap(
					objectValidationRule.getName())
			).put(
				"script", objectValidationRule.getScript()
			).build()
		).put(
			"objectValidationRuleElements",
			_createObjectValidationRuleElements(
				objectValidationRule.getEngine())
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

	private Map<String, Object> _createObjectValidationRuleElement(
		List<HashMap<String, String>> items, String key) {

		return HashMapBuilder.<String, Object>put(
			"items", items
		).put(
			"label", LanguageUtil.get(objectRequestHelper.getLocale(), key)
		).build();
	}

	private List<Map<String, Object>> _createObjectValidationRuleElements(
		String engine) {

		List<Map<String, Object>> objectValidationRuleElements =
			new ArrayList<>();

		objectValidationRuleElements.add(
			_createObjectValidationRuleElement(
				ListUtil.toList(
					ObjectFieldLocalServiceUtil.getObjectFields(
						getObjectDefinitionId()),
					objectField -> HashMapBuilder.put(
						"content", objectField.getName()
					).put(
						"label",
						objectField.getLabel(objectRequestHelper.getLocale())
					).put(
						"tooltip", StringPool.BLANK
					).build()),
				"fields"));

		if (engine.equals(ObjectValidationRuleConstants.ENGINE_TYPE_DDM)) {
			objectValidationRuleElements.add(
				_createObjectValidationRuleElement(
					DDMExpressionOperator.getItems(
						objectRequestHelper.getLocale()),
					"operators"));
			objectValidationRuleElements.add(
				_createObjectValidationRuleElement(
					DDMExpressionFunction.getItems(
						objectRequestHelper.getLocale()),
					"functions"));
		}

		return objectValidationRuleElements;
	}

	private final ObjectValidationRuleEngineServicesTracker
		_objectValidationRuleEngineServicesTracker;

	private enum DDMExpressionFunction {

		CONCAT("concat(parameters)", "concat"),
		CONTAINS("contains(field_name, parameter)", "contains"),
		DOES_NOT_CONTAIN(
			"NOT(contains(field_name, parameter))", "does-not-contain"),
		FUTURE_DATES("futureDates(field_name, parameter)", "future-dates"),
		IS_A_URL("isURL(field_name)", "is-a-url"),
		IS_AN_EMAIL("isEmailAddress(field_name)", "is-an-email"),
		IS_DECIMAL("isDecimal(parameter)", "is-decimal"),
		IS_EMPTY("isEmpty(parameter)", "is-empty"),
		IS_EQUAL_TO("field_name == parameter", "is-equal-to"),
		IS_GREATER_THAN("field_name > parameter", "is-greater-than"),
		IS_GREATER_THAN_OR_EQUAL_TO(
			"field_name >= parameter", "is-greater-than-or-equal-to"),
		IS_INTEGER("isInteger(parameter)", "is-integer"),
		IS_LESS_THAN("field_name < parameter", "is-less-than"),
		IS_LESS_THAN_OR_EQUAL_TO(
			"field_name <= parameter", "is-less-than-or-equal-to"),
		IS_NOT_EQUAL_TO("field_name != parameter", "is-not-equal-to"),
		MATCHES("match(field_name, parameter)", "matches"),
		PAST_DATES("pastDates(field_name, parameter)", "past-dates"),
		RANGE(
			"futureDates(field_name, parameter) AND pastDates(" +
				"field_name, parameter)",
			"range"),
		SUM("sum(parameter)", "sum");

		public static List<HashMap<String, String>> getItems(Locale locale) {
			List<HashMap<String, String>> values = new ArrayList<>();

			for (DDMExpressionFunction ddmExpressionFunction : values()) {
				values.add(
					HashMapBuilder.put(
						"content", ddmExpressionFunction._content
					).put(
						"label",
						LanguageUtil.get(locale, ddmExpressionFunction._key)
					).put(
						"tooltip", StringPool.BLANK
					).build());
			}

			return values;
		}

		private DDMExpressionFunction(String content, String key) {
			_content = content;
			_key = key;
		}

		private String _content;
		private String _key;

	}

	private enum DDMExpressionOperator {

		AND("AND", "and"), DIVIDED_BY("field_name / field_name2", "divided-by"),
		MINUS("field_name - field_name2", "minus"), OR("OR", "or"),
		PLUS("field_name + field_name2", "plus"),
		TIMES("field_name * field_name2", "times");

		public static List<HashMap<String, String>> getItems(Locale locale) {
			List<HashMap<String, String>> values = new ArrayList<>();

			for (DDMExpressionOperator ddmExpressionOperator : values()) {
				values.add(
					HashMapBuilder.put(
						"content", ddmExpressionOperator._content
					).put(
						"label",
						LanguageUtil.get(locale, ddmExpressionOperator._key)
					).put(
						"tooltip", StringPool.BLANK
					).build());
			}

			return values;
		}

		private DDMExpressionOperator(String content, String key) {
			_content = content;
			_key = key;
		}

		private String _content;
		private String _key;

	}

}