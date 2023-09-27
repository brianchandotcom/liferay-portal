/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.permission.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = ConstraintResolver.class)
public class ResourcePermissionConstraintResolver
	implements ConstraintResolver<ResourcePermission> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-permissions";
	}

	@Override
	public Class<ResourcePermission> getModelClass() {
		return ResourcePermission.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "duplicate-permissions-were-removed";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, ResourcePermissionConstraintResolver.class);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"companyId", "name", "scope", "primKey", "roleId"};
	}

	@Override
	public void resolveConflict(
		ConstraintResolverContext<ResourcePermission>
			constraintResolverContext) {

		_resourcePermissionLocalService.deleteResourcePermission(
			constraintResolverContext.getSourceCTModel());
	}

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}