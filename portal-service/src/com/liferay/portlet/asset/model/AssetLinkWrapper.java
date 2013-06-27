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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetLink
 * @generated
 */
public class AssetLinkWrapper implements AssetLink, ModelWrapper<AssetLink> {
	public AssetLinkWrapper(AssetLink assetLink) {
		_assetLink = assetLink;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetLink.class;
	}

	@Override
	public String getModelClassName() {
		return AssetLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("linkId", getLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("entryId1", getEntryId1());
		attributes.put("entryId2", getEntryId2());
		attributes.put("type", getType());
		attributes.put("weight", getWeight());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long linkId = (Long)attributes.get("linkId");

		if (linkId != null) {
			setLinkId(linkId);
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

		Long entryId1 = (Long)attributes.get("entryId1");

		if (entryId1 != null) {
			setEntryId1(entryId1);
		}

		Long entryId2 = (Long)attributes.get("entryId2");

		if (entryId2 != null) {
			setEntryId2(entryId2);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Integer weight = (Integer)attributes.get("weight");

		if (weight != null) {
			setWeight(weight);
		}
	}

	/**
	* Returns the primary key of this asset link.
	*
	* @return the primary key of this asset link
	*/
	@Override
	public long getPrimaryKey() {
		return _assetLink.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset link.
	*
	* @param primaryKey the primary key of this asset link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetLink.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the link ID of this asset link.
	*
	* @return the link ID of this asset link
	*/
	@Override
	public long getLinkId() {
		return _assetLink.getLinkId();
	}

	/**
	* Sets the link ID of this asset link.
	*
	* @param linkId the link ID of this asset link
	*/
	@Override
	public void setLinkId(long linkId) {
		_assetLink.setLinkId(linkId);
	}

	/**
	* Returns the company ID of this asset link.
	*
	* @return the company ID of this asset link
	*/
	@Override
	public long getCompanyId() {
		return _assetLink.getCompanyId();
	}

	/**
	* Sets the company ID of this asset link.
	*
	* @param companyId the company ID of this asset link
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetLink.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this asset link.
	*
	* @return the user ID of this asset link
	*/
	@Override
	public long getUserId() {
		return _assetLink.getUserId();
	}

	/**
	* Sets the user ID of this asset link.
	*
	* @param userId the user ID of this asset link
	*/
	@Override
	public void setUserId(long userId) {
		_assetLink.setUserId(userId);
	}

	/**
	* Returns the user uuid of this asset link.
	*
	* @return the user uuid of this asset link
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLink.getUserUuid();
	}

	/**
	* Sets the user uuid of this asset link.
	*
	* @param userUuid the user uuid of this asset link
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_assetLink.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this asset link.
	*
	* @return the user name of this asset link
	*/
	@Override
	public java.lang.String getUserName() {
		return _assetLink.getUserName();
	}

	/**
	* Sets the user name of this asset link.
	*
	* @param userName the user name of this asset link
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_assetLink.setUserName(userName);
	}

	/**
	* Returns the create date of this asset link.
	*
	* @return the create date of this asset link
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _assetLink.getCreateDate();
	}

	/**
	* Sets the create date of this asset link.
	*
	* @param createDate the create date of this asset link
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_assetLink.setCreateDate(createDate);
	}

	/**
	* Returns the entry id1 of this asset link.
	*
	* @return the entry id1 of this asset link
	*/
	@Override
	public long getEntryId1() {
		return _assetLink.getEntryId1();
	}

	/**
	* Sets the entry id1 of this asset link.
	*
	* @param entryId1 the entry id1 of this asset link
	*/
	@Override
	public void setEntryId1(long entryId1) {
		_assetLink.setEntryId1(entryId1);
	}

	/**
	* Returns the entry id2 of this asset link.
	*
	* @return the entry id2 of this asset link
	*/
	@Override
	public long getEntryId2() {
		return _assetLink.getEntryId2();
	}

	/**
	* Sets the entry id2 of this asset link.
	*
	* @param entryId2 the entry id2 of this asset link
	*/
	@Override
	public void setEntryId2(long entryId2) {
		_assetLink.setEntryId2(entryId2);
	}

	/**
	* Returns the type of this asset link.
	*
	* @return the type of this asset link
	*/
	@Override
	public int getType() {
		return _assetLink.getType();
	}

	/**
	* Sets the type of this asset link.
	*
	* @param type the type of this asset link
	*/
	@Override
	public void setType(int type) {
		_assetLink.setType(type);
	}

	/**
	* Returns the weight of this asset link.
	*
	* @return the weight of this asset link
	*/
	@Override
	public int getWeight() {
		return _assetLink.getWeight();
	}

	/**
	* Sets the weight of this asset link.
	*
	* @param weight the weight of this asset link
	*/
	@Override
	public void setWeight(int weight) {
		_assetLink.setWeight(weight);
	}

	@Override
	public boolean isNew() {
		return _assetLink.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_assetLink.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _assetLink.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetLink.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _assetLink.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _assetLink.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetLink.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetLink.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_assetLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_assetLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetLink.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AssetLinkWrapper((AssetLink)_assetLink.clone());
	}

	@Override
	public int compareTo(com.liferay.portlet.asset.model.AssetLink assetLink) {
		return _assetLink.compareTo(assetLink);
	}

	@Override
	public int hashCode() {
		return _assetLink.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetLink> toCacheModel() {
		return _assetLink.toCacheModel();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetLink toEscapedModel() {
		return new AssetLinkWrapper(_assetLink.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.asset.model.AssetLink toUnescapedModel() {
		return new AssetLinkWrapper(_assetLink.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetLink.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetLink.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetLink.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetLinkWrapper)) {
			return false;
		}

		AssetLinkWrapper assetLinkWrapper = (AssetLinkWrapper)obj;

		if (Validator.equals(_assetLink, assetLinkWrapper._assetLink)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public AssetLink getWrappedAssetLink() {
		return _assetLink;
	}

	@Override
	public AssetLink getWrappedModel() {
		return _assetLink;
	}

	@Override
	public void resetOriginalValues() {
		_assetLink.resetOriginalValues();
	}

	private AssetLink _assetLink;
}