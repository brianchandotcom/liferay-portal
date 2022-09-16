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

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRule;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectValidationRuleUtil;
import com.liferay.object.admin.rest.resource.v1_0.ObjectValidationRuleResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectValidationRuleService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-validation-rule.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectValidationRuleResource.class
)
public class ObjectValidationRuleResourceImpl
	extends BaseObjectValidationRuleResourceImpl {

	@Override
	public void deleteObjectValidationRule(Long objectValidationRuleId)
		throws Exception {

		_objectValidationRuleService.deleteObjectValidationRule(
			objectValidationRuleId);
	}

	@Override
	public Page<ObjectValidationRule>
			getObjectDefinitionObjectValidationRulesPage(
				Long objectDefinitionId, String search, Pagination pagination)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.UPDATE,
					"postObjectDefinitionObjectValidationRule",
					ObjectDefinition.class.getName(), objectDefinitionId)
			).put(
				"createBatch",
				addAction(
					ActionKeys.UPDATE,
					"postObjectDefinitionObjectValidationRuleBatch",
					ObjectDefinition.class.getName(), objectDefinitionId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW,
					"getObjectDefinitionObjectValidationRulesPage",
					ObjectDefinition.class.getName(), objectDefinitionId)
			).build(),
			booleanQuery -> {
			},
			null, com.liferay.object.model.ObjectValidationRule.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setAttribute(
					"objectDefinitionId", objectDefinitionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> _toObjectValidationRule(
				_objectValidationRuleService.getObjectValidationRule(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectValidationRule getObjectValidationRule(
			Long objectValidationRuleId)
		throws Exception {

		return _toObjectValidationRule(
			_objectValidationRuleService.getObjectValidationRule(
				objectValidationRuleId));
	}

	@Override
	public ObjectValidationRule postObjectDefinitionObjectValidationRule(
			Long objectDefinitionId, ObjectValidationRule objectValidationRule)
		throws Exception {

		return _toObjectValidationRule(
			_objectValidationRuleService.addObjectValidationRule(
				objectDefinitionId,
				GetterUtil.getBoolean(objectValidationRule.getActive()),
				objectValidationRule.getEngine(),
				LocalizedMapUtil.getLocalizedMap(
					objectValidationRule.getErrorLabel()),
				LocalizedMapUtil.getLocalizedMap(
					objectValidationRule.getName()),
				objectValidationRule.getScript()));
	}

	@Override
	public ObjectValidationRule putObjectValidationRule(
			Long objectValidationRuleId,
			ObjectValidationRule objectValidationRule)
		throws Exception {

		return _toObjectValidationRule(
			_objectValidationRuleService.updateObjectValidationRule(
				objectValidationRuleId, objectValidationRule.getActive(),
				objectValidationRule.getEngine(),
				LocalizedMapUtil.getLocalizedMap(
					objectValidationRule.getErrorLabel()),
				LocalizedMapUtil.getLocalizedMap(
					objectValidationRule.getName()),
				objectValidationRule.getScript()));
	}

	private ObjectValidationRule _toObjectValidationRule(
		com.liferay.object.model.ObjectValidationRule
			serviceBuilderObjectValidationRule) {

		return ObjectValidationRuleUtil.toObjectValidationRule(
			HashMapBuilder.put(
				"delete",
				addAction(
					ActionKeys.DELETE, "deleteObjectValidationRule",
					ObjectDefinition.class.getName(),
					serviceBuilderObjectValidationRule.getObjectDefinitionId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectValidationRule",
					ObjectDefinition.class.getName(),
					serviceBuilderObjectValidationRule.getObjectDefinitionId())
			).put(
				"update",
				addAction(
					ActionKeys.UPDATE, "putObjectValidationRule",
					ObjectDefinition.class.getName(),
					serviceBuilderObjectValidationRule.getObjectDefinitionId())
			).build(),
			contextAcceptLanguage.getPreferredLocale(),
			serviceBuilderObjectValidationRule);
	}

	@Reference
	private ObjectValidationRuleService _objectValidationRuleService;

}