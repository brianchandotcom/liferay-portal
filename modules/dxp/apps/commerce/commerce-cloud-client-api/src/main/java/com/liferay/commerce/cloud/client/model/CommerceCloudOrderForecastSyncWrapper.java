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

package com.liferay.commerce.cloud.client.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceCloudOrderForecastSync}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudOrderForecastSync
 * @generated
 */
@ProviderType
public class CommerceCloudOrderForecastSyncWrapper
	implements CommerceCloudOrderForecastSync,
		ModelWrapper<CommerceCloudOrderForecastSync> {
	public CommerceCloudOrderForecastSyncWrapper(
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		_commerceCloudOrderForecastSync = commerceCloudOrderForecastSync;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceCloudOrderForecastSync.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceCloudOrderForecastSync.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceCloudOrderForecastSyncId",
			getCommerceCloudOrderForecastSyncId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("syncDate", getSyncDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceCloudOrderForecastSyncId = (Long)attributes.get(
				"commerceCloudOrderForecastSyncId");

		if (commerceCloudOrderForecastSyncId != null) {
			setCommerceCloudOrderForecastSyncId(commerceCloudOrderForecastSyncId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long commerceOrderId = (Long)attributes.get("commerceOrderId");

		if (commerceOrderId != null) {
			setCommerceOrderId(commerceOrderId);
		}

		Date syncDate = (Date)attributes.get("syncDate");

		if (syncDate != null) {
			setSyncDate(syncDate);
		}
	}

	@Override
	public Object clone() {
		return new CommerceCloudOrderForecastSyncWrapper((CommerceCloudOrderForecastSync)_commerceCloudOrderForecastSync.clone());
	}

	@Override
	public int compareTo(
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		return _commerceCloudOrderForecastSync.compareTo(commerceCloudOrderForecastSync);
	}

	/**
	* Returns the commerce cloud order forecast sync ID of this commerce cloud order forecast sync.
	*
	* @return the commerce cloud order forecast sync ID of this commerce cloud order forecast sync
	*/
	@Override
	public long getCommerceCloudOrderForecastSyncId() {
		return _commerceCloudOrderForecastSync.getCommerceCloudOrderForecastSyncId();
	}

	/**
	* Returns the commerce order ID of this commerce cloud order forecast sync.
	*
	* @return the commerce order ID of this commerce cloud order forecast sync
	*/
	@Override
	public long getCommerceOrderId() {
		return _commerceCloudOrderForecastSync.getCommerceOrderId();
	}

	/**
	* Returns the company ID of this commerce cloud order forecast sync.
	*
	* @return the company ID of this commerce cloud order forecast sync
	*/
	@Override
	public long getCompanyId() {
		return _commerceCloudOrderForecastSync.getCompanyId();
	}

	/**
	* Returns the create date of this commerce cloud order forecast sync.
	*
	* @return the create date of this commerce cloud order forecast sync
	*/
	@Override
	public Date getCreateDate() {
		return _commerceCloudOrderForecastSync.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceCloudOrderForecastSync.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce cloud order forecast sync.
	*
	* @return the group ID of this commerce cloud order forecast sync
	*/
	@Override
	public long getGroupId() {
		return _commerceCloudOrderForecastSync.getGroupId();
	}

	/**
	* Returns the primary key of this commerce cloud order forecast sync.
	*
	* @return the primary key of this commerce cloud order forecast sync
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceCloudOrderForecastSync.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceCloudOrderForecastSync.getPrimaryKeyObj();
	}

	/**
	* Returns the sync date of this commerce cloud order forecast sync.
	*
	* @return the sync date of this commerce cloud order forecast sync
	*/
	@Override
	public Date getSyncDate() {
		return _commerceCloudOrderForecastSync.getSyncDate();
	}

	@Override
	public int hashCode() {
		return _commerceCloudOrderForecastSync.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceCloudOrderForecastSync.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceCloudOrderForecastSync.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceCloudOrderForecastSync.isNew();
	}

	@Override
	public void persist() {
		_commerceCloudOrderForecastSync.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceCloudOrderForecastSync.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce cloud order forecast sync ID of this commerce cloud order forecast sync.
	*
	* @param commerceCloudOrderForecastSyncId the commerce cloud order forecast sync ID of this commerce cloud order forecast sync
	*/
	@Override
	public void setCommerceCloudOrderForecastSyncId(
		long commerceCloudOrderForecastSyncId) {
		_commerceCloudOrderForecastSync.setCommerceCloudOrderForecastSyncId(commerceCloudOrderForecastSyncId);
	}

	/**
	* Sets the commerce order ID of this commerce cloud order forecast sync.
	*
	* @param commerceOrderId the commerce order ID of this commerce cloud order forecast sync
	*/
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceCloudOrderForecastSync.setCommerceOrderId(commerceOrderId);
	}

	/**
	* Sets the company ID of this commerce cloud order forecast sync.
	*
	* @param companyId the company ID of this commerce cloud order forecast sync
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceCloudOrderForecastSync.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce cloud order forecast sync.
	*
	* @param createDate the create date of this commerce cloud order forecast sync
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceCloudOrderForecastSync.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceCloudOrderForecastSync.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceCloudOrderForecastSync.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceCloudOrderForecastSync.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce cloud order forecast sync.
	*
	* @param groupId the group ID of this commerce cloud order forecast sync
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceCloudOrderForecastSync.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_commerceCloudOrderForecastSync.setNew(n);
	}

	/**
	* Sets the primary key of this commerce cloud order forecast sync.
	*
	* @param primaryKey the primary key of this commerce cloud order forecast sync
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceCloudOrderForecastSync.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceCloudOrderForecastSync.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the sync date of this commerce cloud order forecast sync.
	*
	* @param syncDate the sync date of this commerce cloud order forecast sync
	*/
	@Override
	public void setSyncDate(Date syncDate) {
		_commerceCloudOrderForecastSync.setSyncDate(syncDate);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceCloudOrderForecastSync> toCacheModel() {
		return _commerceCloudOrderForecastSync.toCacheModel();
	}

	@Override
	public CommerceCloudOrderForecastSync toEscapedModel() {
		return new CommerceCloudOrderForecastSyncWrapper(_commerceCloudOrderForecastSync.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceCloudOrderForecastSync.toString();
	}

	@Override
	public CommerceCloudOrderForecastSync toUnescapedModel() {
		return new CommerceCloudOrderForecastSyncWrapper(_commerceCloudOrderForecastSync.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceCloudOrderForecastSync.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCloudOrderForecastSyncWrapper)) {
			return false;
		}

		CommerceCloudOrderForecastSyncWrapper commerceCloudOrderForecastSyncWrapper =
			(CommerceCloudOrderForecastSyncWrapper)obj;

		if (Objects.equals(_commerceCloudOrderForecastSync,
					commerceCloudOrderForecastSyncWrapper._commerceCloudOrderForecastSync)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceCloudOrderForecastSync getWrappedModel() {
		return _commerceCloudOrderForecastSync;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceCloudOrderForecastSync.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceCloudOrderForecastSync.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceCloudOrderForecastSync.resetOriginalValues();
	}

	private final CommerceCloudOrderForecastSync _commerceCloudOrderForecastSync;
}