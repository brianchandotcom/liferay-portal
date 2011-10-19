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
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.TagPropertyKeyException;
import com.liferay.portlet.asset.TagPropertyValueException;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.service.base.AssetTagPropertyLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the asset tag property local service.
 *
 * @author Brian Wing Shun Chan
 */
public class AssetTagPropertyLocalServiceImpl
	extends AssetTagPropertyLocalServiceBaseImpl {

	/**
	 * Adds the asset tag property.
	 *
	 * @param  userId the primary key of the user
	 * @param  tagId the primary key of the tag
	 * @param  key the key to be associated to the value
	 * @param  value the value which will be referred by the key
	 * @return the created asset tag property
	 * @throws PortalException if the user with the primary key could not be
	 * found or if the key or value are invalid
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty addTagProperty(
			long userId, long tagId, String key, String value)
		throws PortalException, SystemException {

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

		assetTagPropertyPersistence.update(tagProperty, false);

		return tagProperty;
	}

	/**
	 * Deletes the asset tag property instance.
	 *
	 * @param  tagId the primary key of the tag
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTagProperties(long tagId) throws SystemException {
		List<AssetTagProperty> tagProperties =
			assetTagPropertyPersistence.findByTagId(tagId);

		for (AssetTagProperty tagProperty : tagProperties) {
			deleteTagProperty(tagProperty);
		}
	}

	/**
	 * Deletes the asset tag property instance.
	 *
	 * @param  tagProperty the asset tag property instance
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTagProperty(AssetTagProperty tagProperty)
		throws SystemException {

		assetTagPropertyPersistence.remove(tagProperty);
	}

	/**
	 * Deletes the asset tag property instance.
	 *
	 * @param  tagPropertyId the primary key of the asset tag property instance
	 * @throws PortalException if the asset tag property with the primary key 
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTagProperty(long tagPropertyId)
		throws PortalException, SystemException {

		AssetTagProperty tagProperty =
			assetTagPropertyPersistence.findByPrimaryKey(tagPropertyId);

		deleteTagProperty(tagProperty);
	}

	/**
	 * Returns all the asset tag property instances
	 *
	 * @return Returns all the asset tag property
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> getTagProperties() throws SystemException {
		return assetTagPropertyPersistence.findAll();
	}

	/**
	 * Returns all the asset tag property with the specified tag
	 *
	 * @param  tagId the primary key of the tag
	 * @return Returns all the asset tag property matching condition
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> getTagProperties(long tagId)
		throws SystemException {

		return assetTagPropertyPersistence.findByTagId(tagId);
	}

	/**
	 * Returns the asset tag property with the specified id
	 *
	 * @param  tagPropertyId the primary key of the asset tag property
	 * @return Returns the asset tag property matching condition
	 * @throws PortalException if the asset tag property with the primary key
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty getTagProperty(long tagPropertyId)
		throws PortalException, SystemException {

		return assetTagPropertyPersistence.findByPrimaryKey(tagPropertyId);
	}

	/**
	 * Returns the asset tag property with the specified tag and key
	 *
	 * @param  tagId the primary key of the tag
	 * @param  key the key that refers to some value
	 * @return Returns the asset tag property matching conditions
	 * @throws PortalException if the asset tag property with the primary key 
	 *         and key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty getTagProperty(long tagId, String key)
		throws PortalException, SystemException {

		return assetTagPropertyPersistence.findByT_K(tagId, key);
	}

	/**
	 * Returns asset tag property keys with the specified group
	 *
	 * @param  groupId the primary key of the group
	 * @return Returns all the keys matching condition
	 * @throws SystemException if a system exception occurred
	 */
	public String[] getTagPropertyKeys(long groupId) throws SystemException {
		return assetTagPropertyKeyFinder.findByGroupId(groupId);
	}

	/**
	 * Returns asset tag property values with the specified group and key
	 *
	 * @param  groupId the primary key of the group
	 * @param  key the key that refers to some value
	 * @return Returns all the asset tag property matching conditions
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> getTagPropertyValues(long groupId, String key)
		throws SystemException {

		return assetTagPropertyFinder.findByG_K(groupId, key);
	}

	/**
	 * Updates the asset tag property.
	 *
	 * @param  tagPropertyId the primary key of the asset tag property
	 * @param  key the key to be associated to the value
	 * @param  value the value which will be referred by the key
	 * @return the updated asset tag property
	 * @throws PortalException if the asset tag property with the primary key   
	 *         could not be found or if the key or value are invalid
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty updateTagProperty(
			long tagPropertyId, String key, String value)
		throws PortalException, SystemException {

		validate(key, value);

		AssetTagProperty tagProperty =
			assetTagPropertyPersistence.findByPrimaryKey(tagPropertyId);

		tagProperty.setModifiedDate(new Date());
		tagProperty.setKey(key);
		tagProperty.setValue(value);

		assetTagPropertyPersistence.update(tagProperty, false);

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
