/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}