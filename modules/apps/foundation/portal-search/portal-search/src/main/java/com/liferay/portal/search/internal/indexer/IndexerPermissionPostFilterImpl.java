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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.search.indexer.IndexerPermissionPostFilter;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Michael C. Han
 */
public class IndexerPermissionPostFilterImpl
	implements IndexerPermissionPostFilter {

	public IndexerPermissionPostFilterImpl(
		Supplier<Optional<ModelResourcePermission>>
			modelResourcePermissionSupplier,
		Supplier<Optional<ModelVisibilityContributor>>
			modelVisibilityContributorSupplier) {

		_modelResourcePermissionSupplier = modelResourcePermissionSupplier;
		_modelVisibilityContributorSupplier =
			modelVisibilityContributorSupplier;
	}

	@Override
	public boolean hasPermission(
		PermissionChecker permissionChecker, long entryClassPK) {

		Optional<ModelResourcePermission> optional =
			_modelResourcePermissionSupplier.get();

		return optional.map(
			modelResourcePermission -> _containsView(
				modelResourcePermission, permissionChecker, entryClassPK)
		).orElse(
			true
		);
	}

	@Override
	public boolean isPermissionAware() {
		Optional<ModelResourcePermission> optional =
			_modelResourcePermissionSupplier.get();

		return optional.isPresent();
	}

	@Override
	public boolean isVisible(long classPK, int status) {
		Optional<ModelVisibilityContributor> optional =
			_modelVisibilityContributorSupplier.get();

		return optional.map(
			modelVisibilityContributor ->
				modelVisibilityContributor.isVisible(classPK, status)
		).orElse(
			true
		);
	}

	private Boolean _containsView(
		ModelResourcePermission<?> modelResourcePermission,
		PermissionChecker permissionChecker, long entryClassPK) {

		try {
			return modelResourcePermission.contains(
				permissionChecker, entryClassPK, ActionKeys.VIEW);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerPermissionPostFilterImpl.class);

	private final Supplier<Optional<ModelResourcePermission>>
		_modelResourcePermissionSupplier;
	private final Supplier<Optional<ModelVisibilityContributor>>
		_modelVisibilityContributorSupplier;

}