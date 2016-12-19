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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link WebDAVProps}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVProps
 * @generated
 */
@ProviderType
public class WebDAVPropsWrapper implements WebDAVProps,
	ModelWrapper<WebDAVProps> {
	public WebDAVPropsWrapper(WebDAVProps webDAVProps) {
		_webDAVProps = webDAVProps;
	}

	@Override
	public Class<?> getModelClass() {
		return WebDAVProps.class;
	}

	@Override
	public String getModelClassName() {
		return WebDAVProps.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("webDavPropsId", getWebDavPropsId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("props", getProps());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long webDavPropsId = (Long)attributes.get("webDavPropsId");

		if (webDavPropsId != null) {
			setWebDavPropsId(webDavPropsId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String props = (String)attributes.get("props");

		if (props != null) {
			setProps(props);
		}
	}

	@Override
	public CacheModel<WebDAVProps> toCacheModel() {
		return _webDAVProps.toCacheModel();
	}

	@Override
	public WebDAVProps toEscapedModel() {
		return new WebDAVPropsWrapper(_webDAVProps.toEscapedModel());
	}

	@Override
	public WebDAVProps toUnescapedModel() {
		return new WebDAVPropsWrapper(_webDAVProps.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _webDAVProps.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _webDAVProps.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _webDAVProps.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _webDAVProps.getExpandoBridge();
	}

	@Override
	public int compareTo(WebDAVProps webDAVProps) {
		return _webDAVProps.compareTo(webDAVProps);
	}

	@Override
	public int hashCode() {
		return _webDAVProps.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _webDAVProps.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new WebDAVPropsWrapper((WebDAVProps)_webDAVProps.clone());
	}

	/**
	* Returns the fully qualified class name of this web dav props.
	*
	* @return the fully qualified class name of this web dav props
	*/
	@Override
	public java.lang.String getClassName() {
		return _webDAVProps.getClassName();
	}

	/**
	* Returns the props of this web dav props.
	*
	* @return the props of this web dav props
	*/
	@Override
	public java.lang.String getProps() {
		return _webDAVProps.getProps();
	}

	@Override
	public java.lang.String getText(java.lang.String name,
		java.lang.String prefix, java.lang.String uri)
		throws java.lang.Exception {
		return _webDAVProps.getText(name, prefix, uri);
	}

	@Override
	public java.lang.String toString() {
		return _webDAVProps.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _webDAVProps.toXmlString();
	}

	/**
	* Returns the create date of this web dav props.
	*
	* @return the create date of this web dav props
	*/
	@Override
	public Date getCreateDate() {
		return _webDAVProps.getCreateDate();
	}

	/**
	* Returns the modified date of this web dav props.
	*
	* @return the modified date of this web dav props
	*/
	@Override
	public Date getModifiedDate() {
		return _webDAVProps.getModifiedDate();
	}

	@Override
	public java.util.Set<com.liferay.portal.kernel.xml.QName> getPropsSet()
		throws java.lang.Exception {
		return _webDAVProps.getPropsSet();
	}

	/**
	* Returns the class name ID of this web dav props.
	*
	* @return the class name ID of this web dav props
	*/
	@Override
	public long getClassNameId() {
		return _webDAVProps.getClassNameId();
	}

	/**
	* Returns the class pk of this web dav props.
	*
	* @return the class pk of this web dav props
	*/
	@Override
	public long getClassPK() {
		return _webDAVProps.getClassPK();
	}

	/**
	* Returns the company ID of this web dav props.
	*
	* @return the company ID of this web dav props
	*/
	@Override
	public long getCompanyId() {
		return _webDAVProps.getCompanyId();
	}

	/**
	* Returns the mvcc version of this web dav props.
	*
	* @return the mvcc version of this web dav props
	*/
	@Override
	public long getMvccVersion() {
		return _webDAVProps.getMvccVersion();
	}

	/**
	* Returns the primary key of this web dav props.
	*
	* @return the primary key of this web dav props
	*/
	@Override
	public long getPrimaryKey() {
		return _webDAVProps.getPrimaryKey();
	}

	/**
	* Returns the web dav props ID of this web dav props.
	*
	* @return the web dav props ID of this web dav props
	*/
	@Override
	public long getWebDavPropsId() {
		return _webDAVProps.getWebDavPropsId();
	}

	@Override
	public void addProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri) throws java.lang.Exception {
		_webDAVProps.addProp(name, prefix, uri);
	}

	@Override
	public void addProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri, java.lang.String text) throws java.lang.Exception {
		_webDAVProps.addProp(name, prefix, uri, text);
	}

	@Override
	public void persist() {
		_webDAVProps.persist();
	}

	@Override
	public void removeProp(java.lang.String name, java.lang.String prefix,
		java.lang.String uri) throws java.lang.Exception {
		_webDAVProps.removeProp(name, prefix, uri);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_webDAVProps.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_webDAVProps.setClassName(className);
	}

	/**
	* Sets the class name ID of this web dav props.
	*
	* @param classNameId the class name ID of this web dav props
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_webDAVProps.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this web dav props.
	*
	* @param classPK the class pk of this web dav props
	*/
	@Override
	public void setClassPK(long classPK) {
		_webDAVProps.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this web dav props.
	*
	* @param companyId the company ID of this web dav props
	*/
	@Override
	public void setCompanyId(long companyId) {
		_webDAVProps.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this web dav props.
	*
	* @param createDate the create date of this web dav props
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_webDAVProps.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_webDAVProps.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_webDAVProps.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_webDAVProps.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this web dav props.
	*
	* @param modifiedDate the modified date of this web dav props
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_webDAVProps.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this web dav props.
	*
	* @param mvccVersion the mvcc version of this web dav props
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_webDAVProps.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_webDAVProps.setNew(n);
	}

	/**
	* Sets the primary key of this web dav props.
	*
	* @param primaryKey the primary key of this web dav props
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_webDAVProps.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_webDAVProps.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the props of this web dav props.
	*
	* @param props the props of this web dav props
	*/
	@Override
	public void setProps(java.lang.String props) {
		_webDAVProps.setProps(props);
	}

	/**
	* Sets the web dav props ID of this web dav props.
	*
	* @param webDavPropsId the web dav props ID of this web dav props
	*/
	@Override
	public void setWebDavPropsId(long webDavPropsId) {
		_webDAVProps.setWebDavPropsId(webDavPropsId);
	}

	@Override
	public void store() throws java.lang.Exception {
		_webDAVProps.store();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WebDAVPropsWrapper)) {
			return false;
		}

		WebDAVPropsWrapper webDAVPropsWrapper = (WebDAVPropsWrapper)obj;

		if (Objects.equals(_webDAVProps, webDAVPropsWrapper._webDAVProps)) {
			return true;
		}

		return false;
	}

	@Override
	public WebDAVProps getWrappedModel() {
		return _webDAVProps;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _webDAVProps.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _webDAVProps.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_webDAVProps.resetOriginalValues();
	}

	private final WebDAVProps _webDAVProps;
}