/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.security.permission.resource;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelResourcePermissionFactory.ModelResourcePermissionConfigurator.class
)
public class BlogsDLFileEntryModelResourcePermissionConfigurator
	implements ModelResourcePermissionFactory.
				   ModelResourcePermissionConfigurator<DLFileEntry> {

	@Override
	public void configureModelResourcePermissionLogics(
		ModelResourcePermission<DLFileEntry> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<DLFileEntry>> consumer) {

		consumer.accept(
			(permissionChecker, name, dlFileEntry, actionId) -> {
				if (!_delegableActionIds.contains(actionId)) {
					return null;
				}

				Repository repository = _repositoryLocalService.fetchRepository(
					dlFileEntry.getRepositoryId());

				if ((repository == null) ||
					!Objects.equals(
						repository.getPortletId(),
						BlogsConstants.RESOURCE_NAME)) {

					return null;
				}

				return _portletResourcePermission.contains(
					permissionChecker, dlFileEntry.getGroupId(),
					ActionKeys.ADD_ENTRY);
			});
	}

	private static final Set<String> _delegableActionIds = SetUtil.fromArray(
		ActionKeys.DELETE, ActionKeys.UPDATE);

	@Reference(target = "(resource.name=" + BlogsConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

}