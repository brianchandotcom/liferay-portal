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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ClassName}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClassName
 * @generated
 */
@ProviderType
public class ClassNameWrapper implements ClassName, ModelWrapper<ClassName> {
	public ClassNameWrapper(ClassName className) {
		_className = className;
	}

	@Override
	public Class<?> getModelClass() {
		return ClassName.class;
	}

	@Override
	public String getModelClassName() {
		return ClassName.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("classNameId", getClassNameId());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public CacheModel<ClassName> toCacheModel() {
		return _className.toCacheModel();
	}

	@Override
	public ClassName toEscapedModel() {
		return new ClassNameWrapper(_className.toEscapedModel());
	}

	@Override
	public ClassName toUnescapedModel() {
		return new ClassNameWrapper(_className.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _className.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _className.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _className.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _className.getExpandoBridge();
	}

	@Override
	public int compareTo(ClassName className) {
		return _className.compareTo(className);
	}

	@Override
	public int hashCode() {
		return _className.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _className.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new ClassNameWrapper((ClassName)_className.clone());
	}

	/**
	* Returns the fully qualified class name of this class name.
	*
	* @return the fully qualified class name of this class name
	*/
	@Override
	public java.lang.String getClassName() {
		return _className.getClassName();
	}

	/**
	* Returns the value of this class name.
	*
	* @return the value of this class name
	*/
	@Override
	public java.lang.String getValue() {
		return _className.getValue();
	}

	@Override
	public java.lang.String toString() {
		return _className.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _className.toXmlString();
	}

	/**
	* Returns the class name ID of this class name.
	*
	* @return the class name ID of this class name
	*/
	@Override
	public long getClassNameId() {
		return _className.getClassNameId();
	}

	/**
	* Returns the mvcc version of this class name.
	*
	* @return the mvcc version of this class name
	*/
	@Override
	public long getMvccVersion() {
		return _className.getMvccVersion();
	}

	/**
	* Returns the primary key of this class name.
	*
	* @return the primary key of this class name
	*/
	@Override
	public long getPrimaryKey() {
		return _className.getPrimaryKey();
	}

	@Override
	public void persist() {
		_className.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_className.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_className.setClassName(className);
	}

	/**
	* Sets the class name ID of this class name.
	*
	* @param classNameId the class name ID of this class name
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_className.setClassNameId(classNameId);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_className.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_className.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_className.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the mvcc version of this class name.
	*
	* @param mvccVersion the mvcc version of this class name
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_className.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_className.setNew(n);
	}

	/**
	* Sets the primary key of this class name.
	*
	* @param primaryKey the primary key of this class name
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_className.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_className.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the value of this class name.
	*
	* @param value the value of this class name
	*/
	@Override
	public void setValue(java.lang.String value) {
		_className.setValue(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ClassNameWrapper)) {
			return false;
		}

		ClassNameWrapper classNameWrapper = (ClassNameWrapper)obj;

		if (Objects.equals(_className, classNameWrapper._className)) {
			return true;
		}

		return false;
	}

	@Override
	public ClassName getWrappedModel() {
		return _className;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _className.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _className.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_className.resetOriginalValues();
	}

	private final ClassName _className;
}