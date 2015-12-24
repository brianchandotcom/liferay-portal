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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

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
	public java.lang.Object clone() {
		return new DDMStorageLinkWrapper((DDMStorageLink)_ddmStorageLink.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink ddmStorageLink) {
		return _ddmStorageLink.compareTo(ddmStorageLink);
	}

	/**
	* Returns the fully qualified class name of this d d m storage link.
	*
	* @return the fully qualified class name of this d d m storage link
	*/
	@Override
	public java.lang.String getClassName() {
		return _ddmStorageLink.getClassName();
	}

	/**
	* Returns the class name ID of this d d m storage link.
	*
	* @return the class name ID of this d d m storage link
	*/
	@Override
	public long getClassNameId() {
		return _ddmStorageLink.getClassNameId();
	}

	/**
	* Returns the class p k of this d d m storage link.
	*
	* @return the class p k of this d d m storage link
	*/
	@Override
	public long getClassPK() {
		return _ddmStorageLink.getClassPK();
	}

	/**
	* Returns the company ID of this d d m storage link.
	*
	* @return the company ID of this d d m storage link
	*/
	@Override
	public long getCompanyId() {
		return _ddmStorageLink.getCompanyId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStorageLink.getExpandoBridge();
	}

	/**
	* Returns the primary key of this d d m storage link.
	*
	* @return the primary key of this d d m storage link
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmStorageLink.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStorageLink.getPrimaryKeyObj();
	}

	/**
	* Returns the storage link ID of this d d m storage link.
	*
	* @return the storage link ID of this d d m storage link
	*/
	@Override
	public long getStorageLinkId() {
		return _ddmStorageLink.getStorageLinkId();
	}

	@Override
	public java.lang.String getStorageType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLink.getStorageType();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLink.getStructure();
	}

	/**
	* Returns the structure ID of this d d m storage link.
	*
	* @return the structure ID of this d d m storage link
	*/
	@Override
	public long getStructureId() {
		return _ddmStorageLink.getStructureId();
	}

	/**
	* Returns the uuid of this d d m storage link.
	*
	* @return the uuid of this d d m storage link
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddmStorageLink.getUuid();
	}

	@Override
	public int hashCode() {
		return _ddmStorageLink.hashCode();
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
	* Sets the class name ID of this d d m storage link.
	*
	* @param classNameId the class name ID of this d d m storage link
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ddmStorageLink.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this d d m storage link.
	*
	* @param classPK the class p k of this d d m storage link
	*/
	@Override
	public void setClassPK(long classPK) {
		_ddmStorageLink.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this d d m storage link.
	*
	* @param companyId the company ID of this d d m storage link
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmStorageLink.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmStorageLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmStorageLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmStorageLink.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_ddmStorageLink.setNew(n);
	}

	/**
	* Sets the primary key of this d d m storage link.
	*
	* @param primaryKey the primary key of this d d m storage link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmStorageLink.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmStorageLink.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the storage link ID of this d d m storage link.
	*
	* @param storageLinkId the storage link ID of this d d m storage link
	*/
	@Override
	public void setStorageLinkId(long storageLinkId) {
		_ddmStorageLink.setStorageLinkId(storageLinkId);
	}

	/**
	* Sets the structure ID of this d d m storage link.
	*
	* @param structureId the structure ID of this d d m storage link
	*/
	@Override
	public void setStructureId(long structureId) {
		_ddmStorageLink.setStructureId(structureId);
	}

	/**
	* Sets the uuid of this d d m storage link.
	*
	* @param uuid the uuid of this d d m storage link
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmStorageLink.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.mapping.model.DDMStorageLink> toCacheModel() {
		return _ddmStorageLink.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink toEscapedModel() {
		return new DDMStorageLinkWrapper(_ddmStorageLink.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmStorageLink.toString();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink toUnescapedModel() {
		return new DDMStorageLinkWrapper(_ddmStorageLink.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmStorageLink.toXmlString();
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

		if (Validator.equals(_ddmStorageLink,
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