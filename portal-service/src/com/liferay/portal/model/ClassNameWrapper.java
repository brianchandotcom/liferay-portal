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

package com.liferay.portal.model;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ClassName}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClassName
 * @generated
 */
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

		attributes.put("classNameId", getClassNameId());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
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

	/**
	* Sets the primary key of this class name.
	*
	* @param primaryKey the primary key of this class name
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_className.setPrimaryKey(primaryKey);
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

	@Override
	public void setClassName(java.lang.String className) {
		_className.setClassName(className);
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
	* Sets the class name ID of this class name.
	*
	* @param classNameId the class name ID of this class name
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_className.setClassNameId(classNameId);
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
	public boolean isNew() {
		return _className.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_className.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _className.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_className.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _className.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _className.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_className.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _className.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_className.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_className.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_className.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ClassNameWrapper((ClassName)_className.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.ClassName className) {
		return _className.compareTo(className);
	}

	@Override
	public int hashCode() {
		return _className.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.ClassName> toCacheModel() {
		return _className.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.ClassName toEscapedModel() {
		return new ClassNameWrapper(_className.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.ClassName toUnescapedModel() {
		return new ClassNameWrapper(_className.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _className.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _className.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_className.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ClassName getWrappedClassName() {
		return _className;
	}

	@Override
	public ClassName getWrappedModel() {
		return _className;
	}

	@Override
	public void resetOriginalValues() {
		_className.resetOriginalValues();
	}

	private ClassName _className;
}