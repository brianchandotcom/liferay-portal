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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DDMDataProviderInstanceLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLink
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceLinkWrapper
	implements DDMDataProviderInstanceLink,
		ModelWrapper<DDMDataProviderInstanceLink> {
	public DDMDataProviderInstanceLinkWrapper(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {
		_ddmDataProviderInstanceLink = ddmDataProviderInstanceLink;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMDataProviderInstanceLink.class;
	}

	@Override
	public String getModelClassName() {
		return DDMDataProviderInstanceLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("dataProviderInstanceLinkId",
			getDataProviderInstanceLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("dataProviderInstanceId", getDataProviderInstanceId());
		attributes.put("structureId", getStructureId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long dataProviderInstanceLinkId = (Long)attributes.get(
				"dataProviderInstanceLinkId");

		if (dataProviderInstanceLinkId != null) {
			setDataProviderInstanceLinkId(dataProviderInstanceLinkId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long dataProviderInstanceId = (Long)attributes.get(
				"dataProviderInstanceId");

		if (dataProviderInstanceId != null) {
			setDataProviderInstanceId(dataProviderInstanceId);
		}

		Long structureId = (Long)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}
	}

	@Override
	public boolean isCachedModel() {
		return _ddmDataProviderInstanceLink.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmDataProviderInstanceLink.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmDataProviderInstanceLink.isNew();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink toEscapedModel() {
		return new DDMDataProviderInstanceLinkWrapper(_ddmDataProviderInstanceLink.toEscapedModel());
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink toUnescapedModel() {
		return new DDMDataProviderInstanceLinkWrapper(_ddmDataProviderInstanceLink.toUnescapedModel());
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmDataProviderInstanceLink.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink> toCacheModel() {
		return _ddmDataProviderInstanceLink.toCacheModel();
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {
		return _ddmDataProviderInstanceLink.compareTo(ddmDataProviderInstanceLink);
	}

	@Override
	public int hashCode() {
		return _ddmDataProviderInstanceLink.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmDataProviderInstanceLink.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new DDMDataProviderInstanceLinkWrapper((DDMDataProviderInstanceLink)_ddmDataProviderInstanceLink.clone());
	}

	@Override
	public java.lang.String toString() {
		return _ddmDataProviderInstanceLink.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmDataProviderInstanceLink.toXmlString();
	}

	/**
	* Returns the company ID of this d d m data provider instance link.
	*
	* @return the company ID of this d d m data provider instance link
	*/
	@Override
	public long getCompanyId() {
		return _ddmDataProviderInstanceLink.getCompanyId();
	}

	/**
	* Returns the data provider instance ID of this d d m data provider instance link.
	*
	* @return the data provider instance ID of this d d m data provider instance link
	*/
	@Override
	public long getDataProviderInstanceId() {
		return _ddmDataProviderInstanceLink.getDataProviderInstanceId();
	}

	/**
	* Returns the data provider instance link ID of this d d m data provider instance link.
	*
	* @return the data provider instance link ID of this d d m data provider instance link
	*/
	@Override
	public long getDataProviderInstanceLinkId() {
		return _ddmDataProviderInstanceLink.getDataProviderInstanceLinkId();
	}

	/**
	* Returns the primary key of this d d m data provider instance link.
	*
	* @return the primary key of this d d m data provider instance link
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmDataProviderInstanceLink.getPrimaryKey();
	}

	/**
	* Returns the structure ID of this d d m data provider instance link.
	*
	* @return the structure ID of this d d m data provider instance link
	*/
	@Override
	public long getStructureId() {
		return _ddmDataProviderInstanceLink.getStructureId();
	}

	@Override
	public void persist() {
		_ddmDataProviderInstanceLink.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmDataProviderInstanceLink.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this d d m data provider instance link.
	*
	* @param companyId the company ID of this d d m data provider instance link
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmDataProviderInstanceLink.setCompanyId(companyId);
	}

	/**
	* Sets the data provider instance ID of this d d m data provider instance link.
	*
	* @param dataProviderInstanceId the data provider instance ID of this d d m data provider instance link
	*/
	@Override
	public void setDataProviderInstanceId(long dataProviderInstanceId) {
		_ddmDataProviderInstanceLink.setDataProviderInstanceId(dataProviderInstanceId);
	}

	/**
	* Sets the data provider instance link ID of this d d m data provider instance link.
	*
	* @param dataProviderInstanceLinkId the data provider instance link ID of this d d m data provider instance link
	*/
	@Override
	public void setDataProviderInstanceLinkId(long dataProviderInstanceLinkId) {
		_ddmDataProviderInstanceLink.setDataProviderInstanceLinkId(dataProviderInstanceLinkId);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmDataProviderInstanceLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddmDataProviderInstanceLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmDataProviderInstanceLink.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_ddmDataProviderInstanceLink.setNew(n);
	}

	/**
	* Sets the primary key of this d d m data provider instance link.
	*
	* @param primaryKey the primary key of this d d m data provider instance link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmDataProviderInstanceLink.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmDataProviderInstanceLink.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the structure ID of this d d m data provider instance link.
	*
	* @param structureId the structure ID of this d d m data provider instance link
	*/
	@Override
	public void setStructureId(long structureId) {
		_ddmDataProviderInstanceLink.setStructureId(structureId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMDataProviderInstanceLinkWrapper)) {
			return false;
		}

		DDMDataProviderInstanceLinkWrapper ddmDataProviderInstanceLinkWrapper = (DDMDataProviderInstanceLinkWrapper)obj;

		if (Objects.equals(_ddmDataProviderInstanceLink,
					ddmDataProviderInstanceLinkWrapper._ddmDataProviderInstanceLink)) {
			return true;
		}

		return false;
	}

	@Override
	public DDMDataProviderInstanceLink getWrappedModel() {
		return _ddmDataProviderInstanceLink;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmDataProviderInstanceLink.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmDataProviderInstanceLink.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmDataProviderInstanceLink.resetOriginalValues();
	}

	private final DDMDataProviderInstanceLink _ddmDataProviderInstanceLink;
}