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

package com.liferay.asset.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link AssetLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetLink
 * @generated
 */
@ProviderType
public class AssetLinkWrapper extends BaseModelWrapper<AssetLink>
	implements AssetLink, ModelWrapper<AssetLink> {
	public AssetLinkWrapper(AssetLink assetLink) {
		super(assetLink);
	}

	/**
	* Returns the company ID of this asset link.
	*
	* @return the company ID of this asset link
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this asset link.
	*
	* @return the create date of this asset link
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the entry id1 of this asset link.
	*
	* @return the entry id1 of this asset link
	*/
	@Override
	public long getEntryId1() {
		return model.getEntryId1();
	}

	/**
	* Returns the entry id2 of this asset link.
	*
	* @return the entry id2 of this asset link
	*/
	@Override
	public long getEntryId2() {
		return model.getEntryId2();
	}

	/**
	* Returns the link ID of this asset link.
	*
	* @return the link ID of this asset link
	*/
	@Override
	public long getLinkId() {
		return model.getLinkId();
	}

	/**
	* Returns the primary key of this asset link.
	*
	* @return the primary key of this asset link
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the type of this asset link.
	*
	* @return the type of this asset link
	*/
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	* Returns the user ID of this asset link.
	*
	* @return the user ID of this asset link
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this asset link.
	*
	* @return the user name of this asset link
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this asset link.
	*
	* @return the user uuid of this asset link
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the weight of this asset link.
	*
	* @return the weight of this asset link
	*/
	@Override
	public int getWeight() {
		return model.getWeight();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this asset link.
	*
	* @param companyId the company ID of this asset link
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset link.
	*
	* @param createDate the create date of this asset link
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the entry id1 of this asset link.
	*
	* @param entryId1 the entry id1 of this asset link
	*/
	@Override
	public void setEntryId1(long entryId1) {
		model.setEntryId1(entryId1);
	}

	/**
	* Sets the entry id2 of this asset link.
	*
	* @param entryId2 the entry id2 of this asset link
	*/
	@Override
	public void setEntryId2(long entryId2) {
		model.setEntryId2(entryId2);
	}

	/**
	* Sets the link ID of this asset link.
	*
	* @param linkId the link ID of this asset link
	*/
	@Override
	public void setLinkId(long linkId) {
		model.setLinkId(linkId);
	}

	/**
	* Sets the primary key of this asset link.
	*
	* @param primaryKey the primary key of this asset link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the type of this asset link.
	*
	* @param type the type of this asset link
	*/
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	* Sets the user ID of this asset link.
	*
	* @param userId the user ID of this asset link
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this asset link.
	*
	* @param userName the user name of this asset link
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this asset link.
	*
	* @param userUuid the user uuid of this asset link
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the weight of this asset link.
	*
	* @param weight the weight of this asset link
	*/
	@Override
	public void setWeight(int weight) {
		model.setWeight(weight);
	}

	@Override
	protected AssetLinkWrapper wrap(AssetLink assetLink) {
		return new AssetLinkWrapper(assetLink);
	}
}