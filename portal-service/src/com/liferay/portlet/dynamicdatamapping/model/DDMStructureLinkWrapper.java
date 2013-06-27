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

package com.liferay.portlet.dynamicdatamapping.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLink
 * @generated
 */
public class DDMStructureLinkWrapper implements DDMStructureLink,
	ModelWrapper<DDMStructureLink> {
	public DDMStructureLinkWrapper(DDMStructureLink ddmStructureLink) {
		_ddmStructureLink = ddmStructureLink;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMStructureLink.class;
	}

	@Override
	public String getModelClassName() {
		return DDMStructureLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("structureLinkId", getStructureLinkId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("structureId", getStructureId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long structureLinkId = (Long)attributes.get("structureLinkId");

		if (structureLinkId != null) {
			setStructureLinkId(structureLinkId);
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

	/**
	* Returns the primary key of this d d m structure link.
	*
	* @return the primary key of this d d m structure link
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmStructureLink.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m structure link.
	*
	* @param primaryKey the primary key of this d d m structure link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmStructureLink.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the structure link ID of this d d m structure link.
	*
	* @return the structure link ID of this d d m structure link
	*/
	@Override
	public long getStructureLinkId() {
		return _ddmStructureLink.getStructureLinkId();
	}

	/**
	* Sets the structure link ID of this d d m structure link.
	*
	* @param structureLinkId the structure link ID of this d d m structure link
	*/
	@Override
	public void setStructureLinkId(long structureLinkId) {
		_ddmStructureLink.setStructureLinkId(structureLinkId);
	}

	/**
	* Returns the fully qualified class name of this d d m structure link.
	*
	* @return the fully qualified class name of this d d m structure link
	*/
	@Override
	public java.lang.String getClassName() {
		return _ddmStructureLink.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ddmStructureLink.setClassName(className);
	}

	/**
	* Returns the class name ID of this d d m structure link.
	*
	* @return the class name ID of this d d m structure link
	*/
	@Override
	public long getClassNameId() {
		return _ddmStructureLink.getClassNameId();
	}

	/**
	* Sets the class name ID of this d d m structure link.
	*
	* @param classNameId the class name ID of this d d m structure link
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ddmStructureLink.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this d d m structure link.
	*
	* @return the class p k of this d d m structure link
	*/
	@Override
	public long getClassPK() {
		return _ddmStructureLink.getClassPK();
	}

	/**
	* Sets the class p k of this d d m structure link.
	*
	* @param classPK the class p k of this d d m structure link
	*/
	@Override
	public void setClassPK(long classPK) {
		_ddmStructureLink.setClassPK(classPK);
	}

	/**
	* Returns the structure ID of this d d m structure link.
	*
	* @return the structure ID of this d d m structure link
	*/
	@Override
	public long getStructureId() {
		return _ddmStructureLink.getStructureId();
	}

	/**
	* Sets the structure ID of this d d m structure link.
	*
	* @param structureId the structure ID of this d d m structure link
	*/
	@Override
	public void setStructureId(long structureId) {
		_ddmStructureLink.setStructureId(structureId);
	}

	@Override
	public boolean isNew() {
		return _ddmStructureLink.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_ddmStructureLink.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _ddmStructureLink.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmStructureLink.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmStructureLink.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStructureLink.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmStructureLink.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStructureLink.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmStructureLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmStructureLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmStructureLink.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DDMStructureLinkWrapper((DDMStructureLink)_ddmStructureLink.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink) {
		return _ddmStructureLink.compareTo(ddmStructureLink);
	}

	@Override
	public int hashCode() {
		return _ddmStructureLink.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> toCacheModel() {
		return _ddmStructureLink.toCacheModel();
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink toEscapedModel() {
		return new DDMStructureLinkWrapper(_ddmStructureLink.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink toUnescapedModel() {
		return new DDMStructureLinkWrapper(_ddmStructureLink.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmStructureLink.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmStructureLink.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLink.persist();
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLink.getStructure();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructureLinkWrapper)) {
			return false;
		}

		DDMStructureLinkWrapper ddmStructureLinkWrapper = (DDMStructureLinkWrapper)obj;

		if (Validator.equals(_ddmStructureLink,
					ddmStructureLinkWrapper._ddmStructureLink)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public DDMStructureLink getWrappedDDMStructureLink() {
		return _ddmStructureLink;
	}

	@Override
	public DDMStructureLink getWrappedModel() {
		return _ddmStructureLink;
	}

	@Override
	public void resetOriginalValues() {
		_ddmStructureLink.resetOriginalValues();
	}

	private DDMStructureLink _ddmStructureLink;
}