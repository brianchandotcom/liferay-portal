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

package com.liferay.object.service.impl;

import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.base.ObjectDefinitionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectDefinition"
	},
	service = AopService.class
)
public class ObjectDefinitionServiceImpl
	extends ObjectDefinitionServiceBaseImpl {

	@Override
	public ObjectDefinition addCustomObjectDefinition(
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			String scope, List<ObjectField> objectFields)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		return _objectDefinitionLocalService.addCustomObjectDefinition(
			getUserId(), labelMap, name, panelAppOrder, panelCategoryKey,
			pluralLabelMap, scope, objectFields);
	}

	@Override
	public ObjectDefinition deleteObjectDefinition(long objectDefinitionId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.DELETE);

		return _objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public ObjectDefinition getObjectDefinition(long objectDefinitionId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.VIEW);

		return _objectDefinitionLocalService.getObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public List<ObjectDefinition> getObjectDefinitions(int start, int end) {
		return _objectDefinitionLocalService.getObjectDefinitions(start, end);
	}

	@Override
	public List<ObjectDefinition> getObjectDefinitions(
		long companyId, int start, int end) {

		return objectDefinitionPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getObjectDefinitionsCount() throws PortalException {
		return _objectDefinitionLocalService.getObjectDefinitionsCount();
	}

	@Override
	public int getObjectDefinitionsCount(long companyId)
		throws PortalException {

		return _objectDefinitionLocalService.getObjectDefinitionsCount(
			companyId);
	}

	@Override
	public ObjectDefinition publishCustomObjectDefinition(
			long objectDefinitionId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.PUBLISH_OBJECT_DEFINITION);

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			getUserId(), objectDefinitionId);
	}

	@Override
	public ObjectDefinition updateCustomObjectDefinition(
			Long objectDefinitionId, long descriptionObjectFieldId,
			long titleObjectFieldId, boolean active,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			String scope)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return _objectDefinitionLocalService.updateCustomObjectDefinition(
			objectDefinitionId, descriptionObjectFieldId, titleObjectFieldId,
			active, labelMap, name, panelAppOrder, panelCategoryKey,
			pluralLabelMap, scope);
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference(target = "(resource.name=" + ObjectConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}