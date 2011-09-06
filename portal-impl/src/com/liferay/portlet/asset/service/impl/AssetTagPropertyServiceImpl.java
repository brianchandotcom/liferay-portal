/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.service.base.AssetTagPropertyServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetTagPermission;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetTagPropertyServiceImpl
	extends AssetTagPropertyServiceBaseImpl {

	public AssetTagProperty addTagProperty(long tagId, String key, String value)
		throws PortalException, SystemException {

		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.UPDATE);

		return assetTagPropertyLocalService.addTagProperty(
			getUserId(), tagId, key, value);
	}

	public void deleteTagProperty(long tagPropertyId)
		throws PortalException, SystemException {

		AssetTagProperty assetTagProperty =
			assetTagPropertyLocalService.getTagProperty(tagPropertyId);

		AssetTagPermission.check(
			getPermissionChecker(), assetTagProperty.getTagId(),
			ActionKeys.UPDATE);

		assetTagPropertyLocalService.deleteTagProperty(tagPropertyId);
	}

	public List<AssetTagProperty> getTagProperties(long tagId)
		throws SystemException {

		return assetTagPropertyLocalService.getTagProperties(tagId);
	}

	public List<AssetTagProperty> getTagPropertyValues(
			long companyId, String key)
		throws SystemException {

		return assetTagPropertyLocalService.getTagPropertyValues(
			companyId, key);
	}

	public AssetTagProperty updateTagProperty(
			long tagPropertyId, String key, String value)
		throws PortalException, SystemException {

		AssetTagProperty assetTagProperty =
			assetTagPropertyLocalService.getTagProperty(tagPropertyId);

		AssetTagPermission.check(
			getPermissionChecker(), assetTagProperty.getTagId(),
			ActionKeys.UPDATE);

		return assetTagPropertyLocalService.updateTagProperty(
			tagPropertyId, key, value);
	}

}