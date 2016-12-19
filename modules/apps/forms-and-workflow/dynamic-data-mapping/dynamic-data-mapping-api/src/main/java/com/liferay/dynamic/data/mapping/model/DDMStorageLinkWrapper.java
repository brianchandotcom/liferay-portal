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
 * This class is a wrapper for {@link DDMStorageLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLink
 * @generated
 */
@ProviderType
public class DDMStorageLinkWrapper implements DDMStorageLink,
	ModelWrapper<DDMStorageLink> {
	public DDMStorageLinkWrapper(DDMStorageLink ddmStorageLink) {
		_ddmStorageLink = ddmStorageLink;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMStorageLink.class;
	}

	@Override
	public String getModelClassName() {
		return DDMStorageLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("storageLinkId", getStorageLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("structureId", getStructureId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long storageLinkId = (Long)attributes.get("storageLinkId");

		if (storageLinkId != null) {
			setStorageLinkId(storageLinkId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long structureId = (Long)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}
	}

	@Override
	public DDMStorageLink toEscapedModel() {
		return new DDMStorageLinkWrapper(_ddmStorageLink.toEscapedModel());
	}

	@Override
	public DDMStorageLink toUnescapedModel() {
		return new DDMStorageLinkWrapper(_ddmStorageLink.toUnescapedModel());
	}

	@Override
	public DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLink.getStructure();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmStorageLink.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmStorageLink.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmStorageLink.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmStorageLink.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DDMStorageLink> toCacheModel() {
		return _ddmStorageLink.toCacheModel();
	}

	@Override
	public int compareTo(DDMStorageLink ddmStorageLink) {
		return _ddmStorageLink.compareTo(ddmStorageLink);
	}

	@Override
	public int hashCode() {
		return _ddmStorageLink.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmStorageLink.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new DDMStorageLinkWrapper((DDMStorageLink)_ddmStorageLink.clone());
	}

	/**
	* Returns the fully qualified class name of this ddm storage link.
	*
	* @return the fully qualified class name of this ddm storage link
	*/
	@Override
	public java.lang.String getClassName() {
		return _ddmStorageLink.getClassName();
	}

	@Override
	public java.lang.String getStorageType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLink.getStorageType();
	}

	/**
	* Returns the uuid of this ddm storage link.
	*
	* @return the uuid of this ddm storage link
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddmStorageLink.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _ddmStorageLink.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmStorageLink.toXmlString();
	}

	/**
	* Returns the class name ID of this ddm storage link.
	*
	* @return the class name ID of this ddm storage link
	*/
	@Override
	public long getClassNameId() {
		return _ddmStorageLink.getClassNameId();
	}

	/**
	* Returns the class pk of this ddm storage link.
	*
	* @return the class pk of this ddm storage link
	*/
	@Override
	public long getClassPK() {
		return _ddmStorageLink.getClassPK();
	}

	/**
	* Returns the company ID of this ddm storage link.
	*
	* @return the company ID of this ddm storage link
	*/
	@Override
	public long getCompanyId() {
		return _ddmStorageLink.getCompanyId();
	}

	/**
	* Returns the primary key of this ddm storage link.
	*
	* @return the primary key of this ddm storage link
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmStorageLink.getPrimaryKey();
	}

	/**
	* Returns the storage link ID of this ddm storage link.
	*
	* @return the storage link ID of this ddm storage link
	*/
	@Override
	public long getStorageLinkId() {
		return _ddmStorageLink.getStorageLinkId();
	}

	/**
	* Returns the structure ID of this ddm storage link.
	*
	* @return the structure ID of this ddm storage link
	*/
	@Override
	public long getStructureId() {
		return _ddmStorageLink.getStructureId();
	}

	@Override
	public void persist() {
		_ddmStorageLink.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmStorageLink.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ddmStorageLink.setClassName(className);
	}

	/**
	* Sets the class name ID of this ddm storage link.
	*
	* @param classNameId the class name ID of this ddm storage link
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ddmStorageLink.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this ddm storage link.
	*
	* @param classPK the class pk of this ddm storage link
	*/
	@Override
	public void setClassPK(long classPK) {
		_ddmStorageLink.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this ddm storage link.
	*
	* @param companyId the company ID of this ddm storage link
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmStorageLink.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmStorageLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddmStorageLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmStorageLink.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_ddmStorageLink.setNew(n);
	}

	/**
	* Sets the primary key of this ddm storage link.
	*
	* @param primaryKey the primary key of this ddm storage link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmStorageLink.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmStorageLink.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the storage link ID of this ddm storage link.
	*
	* @param storageLinkId the storage link ID of this ddm storage link
	*/
	@Override
	public void setStorageLinkId(long storageLinkId) {
		_ddmStorageLink.setStorageLinkId(storageLinkId);
	}

	/**
	* Sets the structure ID of this ddm storage link.
	*
	* @param structureId the structure ID of this ddm storage link
	*/
	@Override
	public void setStructureId(long structureId) {
		_ddmStorageLink.setStructureId(structureId);
	}

	/**
	* Sets the uuid of this ddm storage link.
	*
	* @param uuid the uuid of this ddm storage link
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmStorageLink.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStorageLinkWrapper)) {
			return false;
		}

		DDMStorageLinkWrapper ddmStorageLinkWrapper = (DDMStorageLinkWrapper)obj;

		if (Objects.equals(_ddmStorageLink,
					ddmStorageLinkWrapper._ddmStorageLink)) {
			return true;
		}

		return false;
	}

	@Override
	public DDMStorageLink getWrappedModel() {
		return _ddmStorageLink;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmStorageLink.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmStorageLink.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmStorageLink.resetOriginalValues();
	}

	private final DDMStorageLink _ddmStorageLink;
}