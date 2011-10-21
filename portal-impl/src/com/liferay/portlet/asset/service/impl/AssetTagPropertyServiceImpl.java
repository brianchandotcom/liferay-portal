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
 * The implementation of the asset tag property service.
 *
 * @author Brian Wing Shun Chan
 */
public class AssetTagPropertyServiceImpl
	extends AssetTagPropertyServiceBaseImpl {

	/**
	 * Adds an asset tag property.
	 *
	 * @param  tagId the primary key of the tag
	 * @param  key the key to be associated to the value
	 * @param  value the value to which the key will refer
	 * @return the created asset tag property
	 * @throws PortalException if the user did not have permission to update
	 *         the asset tag, or if the key or value were invalid
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty addTagProperty(long tagId, String key, String value)
		throws PortalException, SystemException {

		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.UPDATE);

		return assetTagPropertyLocalService.addTagProperty(
			getUserId(), tagId, key, value);
	}

	/**
	 * Deletes the asset tag property with the specified ID.
	 *
	 * @param  tagPropertyId the primary key of the asset tag property instance
	 * @throws PortalException if an asset tag property with the primary key
	 *         could not be found or if the user did not have permission to
	 *         update the asset tag property
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTagProperty(long tagPropertyId)
		throws PortalException, SystemException {

		AssetTagProperty assetTagProperty =
			assetTagPropertyLocalService.getTagProperty(tagPropertyId);

		AssetTagPermission.check(
			getPermissionChecker(), assetTagProperty.getTagId(),
			ActionKeys.UPDATE);

		assetTagPropertyLocalService.deleteTagProperty(tagPropertyId);
	}

	/**
	 * Returns all the asset tag property instances with the specified tag ID.
	 *
	 * @param  tagId the primary key of the tag
	 * @return the matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> getTagProperties(long tagId)
		throws SystemException {

		return assetTagPropertyLocalService.getTagProperties(tagId);
	}

	/**
	 * Returns asset tag properties with the specified group and key.
	 *
	 * @param  companyId the primary key of the company
	 * @param  key the key that refers to some value
	 * @return the matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> getTagPropertyValues(
			long companyId, String key)
		throws SystemException {

		return assetTagPropertyLocalService.getTagPropertyValues(
			companyId, key);
	}

	/**
	 * Updates the asset tag property.
	 *
	 * @param  tagPropertyId the primary key of the asset tag property
	 * @param  key the new key to be associated to the value
	 * @param  value the new value to which the key will refer
	 * @return the updated asset tag property
	 * @throws PortalException if an asset tag property with the primary key
	 *         could not be found, if the user did not have permission to
	 *         update the asset tag, or if the key or value were invalid
	 * @throws SystemException if a system exception occurred
	 */
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