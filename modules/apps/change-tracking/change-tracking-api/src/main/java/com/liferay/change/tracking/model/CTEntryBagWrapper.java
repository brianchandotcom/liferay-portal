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

package com.liferay.change.tracking.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTEntryBag}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryBag
 * @generated
 */
@ProviderType
public class CTEntryBagWrapper extends BaseModelWrapper<CTEntryBag>
	implements CTEntryBag, ModelWrapper<CTEntryBag> {
	public CTEntryBagWrapper(CTEntryBag ctEntryBag) {
		super(ctEntryBag);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("ctEntryBagId", getCtEntryBagId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("ownerCTEntryId", getOwnerCTEntryId());
		attributes.put("ctCollectionId", getCtCollectionId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long ctEntryBagId = (Long)attributes.get("ctEntryBagId");

		if (ctEntryBagId != null) {
			setCtEntryBagId(ctEntryBagId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long ownerCTEntryId = (Long)attributes.get("ownerCTEntryId");

		if (ownerCTEntryId != null) {
			setOwnerCTEntryId(ownerCTEntryId);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}
	}

	/**
	* Returns the company ID of this ct entry bag.
	*
	* @return the company ID of this ct entry bag
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this ct entry bag.
	*
	* @return the create date of this ct entry bag
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the ct collection ID of this ct entry bag.
	*
	* @return the ct collection ID of this ct entry bag
	*/
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	* Returns the ct entry bag ID of this ct entry bag.
	*
	* @return the ct entry bag ID of this ct entry bag
	*/
	@Override
	public long getCtEntryBagId() {
		return model.getCtEntryBagId();
	}

	/**
	* Returns the modified date of this ct entry bag.
	*
	* @return the modified date of this ct entry bag
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the owner ct entry ID of this ct entry bag.
	*
	* @return the owner ct entry ID of this ct entry bag
	*/
	@Override
	public long getOwnerCTEntryId() {
		return model.getOwnerCTEntryId();
	}

	/**
	* Returns the primary key of this ct entry bag.
	*
	* @return the primary key of this ct entry bag
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public java.util.List<CTEntry> getRelatedCTEntries() {
		return model.getRelatedCTEntries();
	}

	/**
	* Returns the user ID of this ct entry bag.
	*
	* @return the user ID of this ct entry bag
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this ct entry bag.
	*
	* @return the user name of this ct entry bag
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this ct entry bag.
	*
	* @return the user uuid of this ct entry bag
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this ct entry bag.
	*
	* @param companyId the company ID of this ct entry bag
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ct entry bag.
	*
	* @param createDate the create date of this ct entry bag
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the ct collection ID of this ct entry bag.
	*
	* @param ctCollectionId the ct collection ID of this ct entry bag
	*/
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	* Sets the ct entry bag ID of this ct entry bag.
	*
	* @param ctEntryBagId the ct entry bag ID of this ct entry bag
	*/
	@Override
	public void setCtEntryBagId(long ctEntryBagId) {
		model.setCtEntryBagId(ctEntryBagId);
	}

	/**
	* Sets the modified date of this ct entry bag.
	*
	* @param modifiedDate the modified date of this ct entry bag
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the owner ct entry ID of this ct entry bag.
	*
	* @param ownerCTEntryId the owner ct entry ID of this ct entry bag
	*/
	@Override
	public void setOwnerCTEntryId(long ownerCTEntryId) {
		model.setOwnerCTEntryId(ownerCTEntryId);
	}

	/**
	* Sets the primary key of this ct entry bag.
	*
	* @param primaryKey the primary key of this ct entry bag
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this ct entry bag.
	*
	* @param userId the user ID of this ct entry bag
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this ct entry bag.
	*
	* @param userName the user name of this ct entry bag
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ct entry bag.
	*
	* @param userUuid the user uuid of this ct entry bag
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CTEntryBagWrapper wrap(CTEntryBag ctEntryBag) {
		return new CTEntryBagWrapper(ctEntryBag);
	}
}