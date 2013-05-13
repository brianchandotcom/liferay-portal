/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.base.SCProductEntryServiceBaseImpl;
import com.liferay.portlet.softwarecatalog.service.permission.SCPermission;
import com.liferay.portlet.softwarecatalog.service.permission.SCProductEntryPermission;

import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SCProductEntryServiceImpl extends SCProductEntryServiceBaseImpl {

	@Override
	public SCProductEntry addProductEntry(
			String name, String type, String tags, String shortDescription,
			String longDescription, String pageURL, String author,
			String repoGroupId, String repoArtifactId, long[] licenseIds,
			List<byte[]> thumbnails, List<byte[]> fullImages,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		SCPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_PRODUCT_ENTRY);

		return scProductEntryLocalService.addProductEntry(
			getUserId(), name, type, tags, shortDescription, longDescription,
			pageURL, author, repoGroupId, repoArtifactId, licenseIds,
			thumbnails, fullImages, serviceContext);
	}

	@Override
	public void deleteProductEntry(long productEntryId)
		throws PortalException, SystemException {

		SCProductEntryPermission.check(
			getPermissionChecker(), productEntryId, ActionKeys.DELETE);

		scProductEntryLocalService.deleteProductEntry(productEntryId);
	}

	@Override
	public SCProductEntry getProductEntry(long productEntryId)
		throws PortalException, SystemException {

		SCProductEntryPermission.check(
			getPermissionChecker(), productEntryId, ActionKeys.VIEW);

		return scProductEntryLocalService.getProductEntry(productEntryId);
	}

	@Override
	public SCProductEntry updateProductEntry(
			long productEntryId, String name, String type, String tags,
			String shortDescription, String longDescription, String pageURL,
			String author, String repoGroupId, String repoArtifactId,
			long[] licenseIds, List<byte[]> thumbnails, List<byte[]> fullImages)
		throws PortalException, SystemException {

		SCProductEntryPermission.check(
			getPermissionChecker(), productEntryId, ActionKeys.UPDATE);

		return scProductEntryLocalService.updateProductEntry(
			productEntryId, name, type, tags, shortDescription, longDescription,
			pageURL, author, repoGroupId, repoArtifactId, licenseIds,
			thumbnails, fullImages);
	}

}