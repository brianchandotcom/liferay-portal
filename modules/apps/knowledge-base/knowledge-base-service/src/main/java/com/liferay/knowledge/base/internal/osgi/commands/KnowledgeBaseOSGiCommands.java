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

package com.liferay.knowledge.base.internal.osgi.commands;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=addImportArticlePermissions",
		"osgi.command.scope=knowledgeBase"
	},
	service = KnowledgeBaseOSGiCommands.class
)
public class KnowledgeBaseOSGiCommands {

	public void addImportArticlePermissions() throws PortalException {
		ResourceAction addKbArticleAction = _getAddKbArticleAction();

		ResourceAction importKbArticlesAction = _getImportKbArticlesAction();

		ActionableDynamicQuery actionableDynamicQuery =
			_resourcePermissionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"name", KBConstants.RESOURCE_NAME_ADMIN)));

		actionableDynamicQuery.setPerformActionMethod(
			(ResourcePermission resourcePermission) -> {
				if (_hasResourceAction(
						resourcePermission, addKbArticleAction)) {

					_addResourceAction(
						resourcePermission, importKbArticlesAction);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	protected void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	private void _addResourceAction(
			ResourcePermission permission, ResourceAction action)
		throws PortalException {

		permission.addResourceAction(action.getActionId());

		_resourcePermissionLocalService.updateResourcePermission(permission);
	}

	private ResourceAction _getAddKbArticleAction() throws PortalException {
		return _resourceActionLocalService.getResourceAction(
			KBConstants.RESOURCE_NAME_ADMIN, KBActionKeys.ADD_KB_ARTICLE);
	}

	private ResourceAction _getImportKbArticlesAction() throws PortalException {
		return _resourceActionLocalService.getResourceAction(
			KBConstants.RESOURCE_NAME_ADMIN, KBActionKeys.IMPORT_KB_ARTICLES);
	}

	private boolean _hasResourceAction(
		ResourcePermission permission, ResourceAction action) {

		return _resourcePermissionLocalService.hasActionId(permission, action);
	}

	private ResourceActionLocalService _resourceActionLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}