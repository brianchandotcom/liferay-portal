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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.TagPropertyKeyException;
import com.liferay.portlet.asset.TagPropertyValueException;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.service.base.AssetTagPropertyLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.Date;
import java.util.List;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * asset tag properties.
 *
 * @author Brian Wing Shun Chan
 */
public class AssetTagPropertyLocalServiceImpl
	extends AssetTagPropertyLocalServiceBaseImpl {

	/**
	 * Adds an asset tag property.
	 *
	 * @param  userId the primary key of the user
	 * @param  tagId the primary key of the tag
	 * @param  key the key to be associated to the value
	 * @param  value the value to which the key will refer
	 * @return the created asset tag property
	 * @throws PortalException if a user with the primary key could not be
	 *         found, or if the key or value were invalid
	 */
	@Override
	public AssetTagProperty addTagProperty(
			long userId, long tagId, String key, String value)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(key, value);

		long tagPropertyId = counterLocalService.increment();

		AssetTagProperty tagProperty = assetTagPropertyPersistence.create(
			tagPropertyId);

		tagProperty.setCompanyId(user.getCompanyId());
		tagProperty.setUserId(user.getUserId());
		tagProperty.setUserName(user.getFullName());
		tagProperty.setCreateDate(now);
		tagProperty.setModifiedDate(now);
		tagProperty.setTagId(tagId);
		tagProperty.setKey(key);
		tagProperty.setValue(value);

		assetTagPropertyPersistence.update(tagProperty);

		return tagProperty;
	}

	/**
	 * Deletes the asset tag property with the specified tag ID.
	 *
	 * @param tagId the primary key of the tag
	 */
	@Override
	public void deleteTagProperties(long tagId) {
		List<AssetTagProperty> tagProperties =
			assetTagPropertyPersistence.findByTagId(tagId);

		for (AssetTagProperty tagProperty : tagProperties) {
			deleteTagProperty(tagProperty);
		}
	}

	/**
	 * Deletes the asset tag property instance.
	 *
	 * @param tagProperty the asset tag property instance
	 */
	@Override
	public void deleteTagProperty(AssetTagProperty tagProperty) {
		assetTagPropertyPersistence.remove(tagProperty);
	}

	/**
	 * Deletes the asset tag property with the specified ID.
	 *
	 * @param  tagPropertyId the primary key of the asset tag property instance
	 * @throws PortalException if an asset tag property with the primary key
	 *         could not be found
	 */
	@Override
	public void deleteTagProperty(long tagPropertyId) throws PortalException {
		AssetTagProperty tagProperty =
			assetTagPropertyPersistence.findByPrimaryKey(tagPropertyId);

		deleteTagProperty(tagProperty);
	}

	/**
	 * Returns all the asset tag property instances.
	 *
	 * @return the asset tag property instances
	 */
	@Override
	public List<AssetTagProperty> getTagProperties() {
		return assetTagPropertyPersistence.findAll();
	}

	/**
	 * Returns all the asset tag property instances with the specified tag ID.
	 *
	 * @param  tagId the primary key of the tag
	 * @return the matching asset tag properties
	 */
	@Override
	public List<AssetTagProperty> getTagProperties(long tagId) {
		return assetTagPropertyPersistence.findByTagId(tagId);
	}

	/**
	 * Returns the asset tag property with the specified ID.
	 *
	 * @param  tagPropertyId the primary key of the asset tag property
	 * @return the matching asset tag property
	 * @throws PortalException if an asset tag property with the primary key
	 *         could not be found
	 */
	@Override
	public AssetTagProperty getTagProperty(long tagPropertyId)
		throws PortalException {

		return assetTagPropertyPersistence.findByPrimaryKey(tagPropertyId);
	}

	/**
	 * Returns the asset tag property with the specified tag ID and key.
	 *
	 * @param  tagId the primary key of the tag
	 * @param  key the key that refers to some value
	 * @return the matching asset tag property
	 * @throws PortalException if an asset tag property with the tag ID and key
	 *         could not be found
	 */
	@Override
	public AssetTagProperty getTagProperty(long tagId, String key)
		throws PortalException {

		return assetTagPropertyPersistence.findByT_K(tagId, key);
	}

	/**
	 * Returns asset tag property keys with the specified group
	 *
	 * @param  groupId the primary key of the group
	 * @return the matching asset tag property keys
	 */
	@Override
	public String[] getTagPropertyKeys(long groupId) {
		return assetTagPropertyKeyFinder.findByGroupId(groupId);
	}

	/**
	 * Returns asset tag properties with the specified group and key.
	 *
	 * @param  groupId the primary key of the group
	 * @param  key the key that refers to some value
	 * @return the matching asset tag properties
	 */
	@Override
	public List<AssetTagProperty> getTagPropertyValues(
		long groupId, String key) {

		return assetTagPropertyFinder.findByG_K(groupId, key);
	}

	/**
	 * Updates the asset tag property.
	 *
	 * @param  tagPropertyId the primary key of the asset tag property
	 * @param  key the new key to be associated to the value
	 * @param  value the new value to which the key will refer
	 * @return the updated asset tag property
	 * @throws PortalException if an asset tag property with the primary key
	 *         could not be found, or if the key or value were invalid
	 */
	@Override
	public AssetTagProperty updateTagProperty(
			long tagPropertyId, String key, String value)
		throws PortalException {

		validate(key, value);

		AssetTagProperty tagProperty =
			assetTagPropertyPersistence.findByPrimaryKey(tagPropertyId);

		tagProperty.setModifiedDate(new Date());
		tagProperty.setKey(key);
		tagProperty.setValue(value);

		assetTagPropertyPersistence.update(tagProperty);

		return tagProperty;
	}

	protected void validate(String key, String value) throws PortalException {
		if (!AssetUtil.isValidWord(key)) {
			throw new TagPropertyKeyException();
		}

		if (!AssetUtil.isValidWord(value)) {
			throw new TagPropertyValueException();
		}
	}

}