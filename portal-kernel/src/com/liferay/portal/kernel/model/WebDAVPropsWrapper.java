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

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WebDAVProps}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVProps
 * @generated
 */
public class WebDAVPropsWrapper
	extends BaseModelWrapper<WebDAVProps>
	implements ModelWrapper<WebDAVProps>, WebDAVProps {

	public WebDAVPropsWrapper(WebDAVProps webDAVProps) {
		super(webDAVProps);
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
	public void addProp(String name, String prefix, String uri)
		throws Exception {

		model.addProp(name, prefix, uri);
	}

	@Override
	public void addProp(String name, String prefix, String uri, String text)
		throws Exception {

		model.addProp(name, prefix, uri, text);
	}

	/**
	 * Returns the fully qualified class name of this web dav props.
	 *
	 * @return the fully qualified class name of this web dav props
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this web dav props.
	 *
	 * @return the class name ID of this web dav props
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this web dav props.
	 *
	 * @return the class pk of this web dav props
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this web dav props.
	 *
	 * @return the company ID of this web dav props
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this web dav props.
	 *
	 * @return the create date of this web dav props
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this web dav props.
	 *
	 * @return the modified date of this web dav props
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this web dav props.
	 *
	 * @return the mvcc version of this web dav props
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this web dav props.
	 *
	 * @return the primary key of this web dav props
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the props of this web dav props.
	 *
	 * @return the props of this web dav props
	 */
	@Override
	public String getProps() {
		return model.getProps();
	}

	@Override
	public java.util.Set<com.liferay.portal.kernel.xml.QName> getPropsSet()
		throws Exception {

		return model.getPropsSet();
	}

	@Override
	public String getText(String name, String prefix, String uri)
		throws Exception {

		return model.getText(name, prefix, uri);
	}

	/**
	 * Returns the web dav props ID of this web dav props.
	 *
	 * @return the web dav props ID of this web dav props
	 */
	@Override
	public long getWebDavPropsId() {
		return model.getWebDavPropsId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a web dav props model instance should use the <code>WebDAVProps</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void removeProp(String name, String prefix, String uri)
		throws Exception {

		model.removeProp(name, prefix, uri);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this web dav props.
	 *
	 * @param classNameId the class name ID of this web dav props
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this web dav props.
	 *
	 * @param classPK the class pk of this web dav props
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this web dav props.
	 *
	 * @param companyId the company ID of this web dav props
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this web dav props.
	 *
	 * @param createDate the create date of this web dav props
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this web dav props.
	 *
	 * @param modifiedDate the modified date of this web dav props
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this web dav props.
	 *
	 * @param mvccVersion the mvcc version of this web dav props
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this web dav props.
	 *
	 * @param primaryKey the primary key of this web dav props
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the props of this web dav props.
	 *
	 * @param props the props of this web dav props
	 */
	@Override
	public void setProps(String props) {
		model.setProps(props);
	}

	/**
	 * Sets the web dav props ID of this web dav props.
	 *
	 * @param webDavPropsId the web dav props ID of this web dav props
	 */
	@Override
	public void setWebDavPropsId(long webDavPropsId) {
		model.setWebDavPropsId(webDavPropsId);
	}

	@Override
	public void store() throws Exception {
		model.store();
	}

	@Override
	protected WebDAVPropsWrapper wrap(WebDAVProps webDAVProps) {
		return new WebDAVPropsWrapper(webDAVProps);
	}

}