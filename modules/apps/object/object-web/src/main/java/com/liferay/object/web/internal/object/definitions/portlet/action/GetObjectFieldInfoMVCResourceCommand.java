/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.object.definitions.display.context.util.ObjectCodeEditorUtil;
import com.liferay.object.web.internal.object.definitions.display.context.util.ObjectCodeEditorUtil.DDMExpressionFunction;
import com.liferay.object.web.internal.util.ObjectFieldBusinessTypeUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Murilo Stodolni
 */
@Component(
	property = {
		"jakarta.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/get_object_field_info"
	},
	service = MVCResourceCommand.class
)
public class GetObjectFieldInfoMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			ParamUtil.getLong(resourceRequest, "objectFieldId"));

		if (objectField == null) {
			return;
		}

		Locale locale = _portal.getLocale(resourceRequest);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectField.getObjectDefinitionId());

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"defaultValueSidebarElements",
				() -> {
					if (objectField.compareBusinessType(
							ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

						return ObjectCodeEditorUtil.getCodeEditorElements(
							true, false, false, locale,
							objectField.getObjectDefinitionId(),
							objectField1 -> !objectField1.isSystem());
					}

					List<DDMExpressionFunction>
						defaultValueDDMExpressionFunctions =
							_defaultValueDDMExpressionFunctionsMap.get(
								objectField.getBusinessType());

					if (ListUtil.isEmpty(defaultValueDDMExpressionFunctions)) {
						return null;
					}

					return ObjectCodeEditorUtil.getCodeEditorElements(
						defaultValueDDMExpressionFunctions::contains,
						ddmExpressionOperator -> false,
						generalVariable -> {
							if (objectField.compareBusinessType(
									ObjectFieldConstants.BUSINESS_TYPE_DATE) ||
								objectField.compareBusinessType(
									ObjectFieldConstants.
										BUSINESS_TYPE_DATE_TIME)) {

								return generalVariable.equals("currentDate");
							}

							return true;
						},
						false, locale, objectDefinition.getObjectDefinitionId(),
						objectField2 -> false);
				}
			).put(
				"objectFieldBusinessTypes",
				ObjectFieldBusinessTypeUtil.getObjectFieldBusinessTypeMaps(
					locale,
					ListUtil.filter(
						_objectFieldBusinessTypeRegistry.
							getObjectFieldBusinessTypes(),
						objectFieldBusinessType ->
							objectFieldBusinessType.isVisible(
								objectDefinition) &&
							(!StringUtil.equals(
								objectFieldBusinessType.getName(),
								ObjectFieldConstants.
									BUSINESS_TYPE_RELATIONSHIP) ||
							 Validator.isNotNull(
								 objectField.getRelationshipType()))))
			).put(
				"objectRelationshipId",
				() -> {
					if (!StringUtil.equals(
							objectField.getBusinessType(),
							ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

						return null;
					}

					ObjectRelationship objectRelationship =
						_objectRelationshipLocalService.
							fetchObjectRelationshipByObjectFieldId2(
								objectField.getObjectFieldId());

					return objectRelationship.getObjectRelationshipId();
				}
			).put(
				"readOnlySidebarElements",
				ObjectCodeEditorUtil.getCodeEditorElements(
					ddmExpressionFunction ->
						!DDMExpressionFunction.OLD_VALUE.equals(
							ddmExpressionFunction),
					ddmExpressionOperator -> true, generalVariables -> true,
					false, locale, objectDefinition.getObjectDefinitionId(),
					objectField3 -> !objectField3.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION))
			).put(
				"sidebarElements",
				() -> {
					if (StringUtil.equals(
							objectField.getBusinessType(),
							ObjectFieldConstants.BUSINESS_TYPE_FORMULA)) {

						return ObjectCodeEditorUtil.getCodeEditorElements(
							ddmExpressionFunction -> false,
							ddmExpressionOperator ->
								_filterableDDMExpressionOperators.contains(
									ddmExpressionOperator),
							generalVariables -> false, true, locale,
							objectField.getObjectDefinitionId(),
							objectField4 ->
								_filterableObjectFieldBusinessTypes.contains(
									objectField4.getBusinessType()));
					}

					return ObjectCodeEditorUtil.getCodeEditorElements(
						true, false, false, locale,
						objectField.getObjectDefinitionId(),
						objectField5 -> !objectField5.isSystem());
				}
			));
	}

	private static final Map<String, List<DDMExpressionFunction>>
		_defaultValueDDMExpressionFunctionsMap =
			HashMapBuilder.<String, List<DDMExpressionFunction>>put(
				ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
				ListUtil.fromArray(
					DDMExpressionFunction.COMPARE_DATES,
					DDMExpressionFunction.CONDITION,
					DDMExpressionFunction.CONTAINS,
					DDMExpressionFunction.DOES_NOT_CONTAIN,
					DDMExpressionFunction.FUTURE_DATES,
					DDMExpressionFunction.IS_A_URL,
					DDMExpressionFunction.IS_AN_EMAIL,
					DDMExpressionFunction.IS_DECIMAL,
					DDMExpressionFunction.IS_EMPTY,
					DDMExpressionFunction.IS_EQUAL_TO,
					DDMExpressionFunction.IS_GREATER_THAN,
					DDMExpressionFunction.IS_GREATER_THAN_OR_EQUAL_TO,
					DDMExpressionFunction.IS_INTEGER,
					DDMExpressionFunction.IS_LESS_THAN,
					DDMExpressionFunction.IS_LESS_THAN_OR_EQUAL_TO,
					DDMExpressionFunction.IS_NOT_EQUAL_TO,
					DDMExpressionFunction.MATCH,
					DDMExpressionFunction.PAST_DATES,
					DDMExpressionFunction.RANGE)
			).put(
				ObjectFieldConstants.BUSINESS_TYPE_DATE,
				ListUtil.fromArray(
					DDMExpressionFunction.ADD_DAYS,
					DDMExpressionFunction.ADD_MONTHS,
					DDMExpressionFunction.ADD_YEARS,
					DDMExpressionFunction.OLD_VALUE)
			).put(
				ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME,
				ListUtil.fromArray(
					DDMExpressionFunction.ADD_DAYS,
					DDMExpressionFunction.ADD_MONTHS,
					DDMExpressionFunction.ADD_YEARS,
					DDMExpressionFunction.OLD_VALUE)
			).build();
	private static final Set<ObjectCodeEditorUtil.DDMExpressionOperator>
		_filterableDDMExpressionOperators = Collections.unmodifiableSet(
			SetUtil.fromArray(
				ObjectCodeEditorUtil.DDMExpressionOperator.DIVIDED_BY,
				ObjectCodeEditorUtil.DDMExpressionOperator.MINUS,
				ObjectCodeEditorUtil.DDMExpressionOperator.PLUS,
				ObjectCodeEditorUtil.DDMExpressionOperator.TIMES));
	private static final Set<String> _filterableObjectFieldBusinessTypes =
		Collections.unmodifiableSet(
			SetUtil.fromArray(
				ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
				ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
				ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
				ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL));

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private Portal _portal;

}