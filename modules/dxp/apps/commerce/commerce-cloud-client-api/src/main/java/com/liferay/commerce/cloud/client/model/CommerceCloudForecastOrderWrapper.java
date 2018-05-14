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
 * This class is a wrapper for {@link CommerceCloudForecastOrder}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudForecastOrder
 * @generated
 */
@ProviderType
public class CommerceCloudForecastOrderWrapper
	implements CommerceCloudForecastOrder,
		ModelWrapper<CommerceCloudForecastOrder> {
	public CommerceCloudForecastOrderWrapper(
		CommerceCloudForecastOrder commerceCloudForecastOrder) {
		_commerceCloudForecastOrder = commerceCloudForecastOrder;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceCloudForecastOrder.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceCloudForecastOrder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceCloudForecastOrderId",
			getCommerceCloudForecastOrderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("syncDate", getSyncDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceCloudForecastOrderId = (Long)attributes.get(
				"commerceCloudForecastOrderId");

		if (commerceCloudForecastOrderId != null) {
			setCommerceCloudForecastOrderId(commerceCloudForecastOrderId);
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
		return new CommerceCloudForecastOrderWrapper((CommerceCloudForecastOrder)_commerceCloudForecastOrder.clone());
	}

	@Override
	public int compareTo(CommerceCloudForecastOrder commerceCloudForecastOrder) {
		return _commerceCloudForecastOrder.compareTo(commerceCloudForecastOrder);
	}

	/**
	* Returns the commerce cloud forecast order ID of this commerce cloud forecast order.
	*
	* @return the commerce cloud forecast order ID of this commerce cloud forecast order
	*/
	@Override
	public long getCommerceCloudForecastOrderId() {
		return _commerceCloudForecastOrder.getCommerceCloudForecastOrderId();
	}

	/**
	* Returns the commerce order ID of this commerce cloud forecast order.
	*
	* @return the commerce order ID of this commerce cloud forecast order
	*/
	@Override
	public long getCommerceOrderId() {
		return _commerceCloudForecastOrder.getCommerceOrderId();
	}

	/**
	* Returns the company ID of this commerce cloud forecast order.
	*
	* @return the company ID of this commerce cloud forecast order
	*/
	@Override
	public long getCompanyId() {
		return _commerceCloudForecastOrder.getCompanyId();
	}

	/**
	* Returns the create date of this commerce cloud forecast order.
	*
	* @return the create date of this commerce cloud forecast order
	*/
	@Override
	public Date getCreateDate() {
		return _commerceCloudForecastOrder.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceCloudForecastOrder.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce cloud forecast order.
	*
	* @return the group ID of this commerce cloud forecast order
	*/
	@Override
	public long getGroupId() {
		return _commerceCloudForecastOrder.getGroupId();
	}

	/**
	* Returns the primary key of this commerce cloud forecast order.
	*
	* @return the primary key of this commerce cloud forecast order
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceCloudForecastOrder.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceCloudForecastOrder.getPrimaryKeyObj();
	}

	/**
	* Returns the sync date of this commerce cloud forecast order.
	*
	* @return the sync date of this commerce cloud forecast order
	*/
	@Override
	public Date getSyncDate() {
		return _commerceCloudForecastOrder.getSyncDate();
	}

	@Override
	public int hashCode() {
		return _commerceCloudForecastOrder.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceCloudForecastOrder.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceCloudForecastOrder.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceCloudForecastOrder.isNew();
	}

	@Override
	public void persist() {
		_commerceCloudForecastOrder.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceCloudForecastOrder.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce cloud forecast order ID of this commerce cloud forecast order.
	*
	* @param commerceCloudForecastOrderId the commerce cloud forecast order ID of this commerce cloud forecast order
	*/
	@Override
	public void setCommerceCloudForecastOrderId(
		long commerceCloudForecastOrderId) {
		_commerceCloudForecastOrder.setCommerceCloudForecastOrderId(commerceCloudForecastOrderId);
	}

	/**
	* Sets the commerce order ID of this commerce cloud forecast order.
	*
	* @param commerceOrderId the commerce order ID of this commerce cloud forecast order
	*/
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceCloudForecastOrder.setCommerceOrderId(commerceOrderId);
	}

	/**
	* Sets the company ID of this commerce cloud forecast order.
	*
	* @param companyId the company ID of this commerce cloud forecast order
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceCloudForecastOrder.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce cloud forecast order.
	*
	* @param createDate the create date of this commerce cloud forecast order
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceCloudForecastOrder.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceCloudForecastOrder.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceCloudForecastOrder.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceCloudForecastOrder.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce cloud forecast order.
	*
	* @param groupId the group ID of this commerce cloud forecast order
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceCloudForecastOrder.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_commerceCloudForecastOrder.setNew(n);
	}

	/**
	* Sets the primary key of this commerce cloud forecast order.
	*
	* @param primaryKey the primary key of this commerce cloud forecast order
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceCloudForecastOrder.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceCloudForecastOrder.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the sync date of this commerce cloud forecast order.
	*
	* @param syncDate the sync date of this commerce cloud forecast order
	*/
	@Override
	public void setSyncDate(Date syncDate) {
		_commerceCloudForecastOrder.setSyncDate(syncDate);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceCloudForecastOrder> toCacheModel() {
		return _commerceCloudForecastOrder.toCacheModel();
	}

	@Override
	public CommerceCloudForecastOrder toEscapedModel() {
		return new CommerceCloudForecastOrderWrapper(_commerceCloudForecastOrder.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceCloudForecastOrder.toString();
	}

	@Override
	public CommerceCloudForecastOrder toUnescapedModel() {
		return new CommerceCloudForecastOrderWrapper(_commerceCloudForecastOrder.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceCloudForecastOrder.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCloudForecastOrderWrapper)) {
			return false;
		}

		CommerceCloudForecastOrderWrapper commerceCloudForecastOrderWrapper = (CommerceCloudForecastOrderWrapper)obj;

		if (Objects.equals(_commerceCloudForecastOrder,
					commerceCloudForecastOrderWrapper._commerceCloudForecastOrder)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceCloudForecastOrder getWrappedModel() {
		return _commerceCloudForecastOrder;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceCloudForecastOrder.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceCloudForecastOrder.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceCloudForecastOrder.resetOriginalValues();
	}

	private final CommerceCloudForecastOrder _commerceCloudForecastOrder;
}