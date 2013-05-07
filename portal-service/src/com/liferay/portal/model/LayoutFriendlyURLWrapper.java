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
 * This class is a wrapper for {@link LayoutFriendlyURL}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutFriendlyURL
 * @generated
 */
public class LayoutFriendlyURLWrapper implements LayoutFriendlyURL,
	ModelWrapper<LayoutFriendlyURL> {
	public LayoutFriendlyURLWrapper(LayoutFriendlyURL layoutFriendlyURL) {
		_layoutFriendlyURL = layoutFriendlyURL;
	}

	public Class<?> getModelClass() {
		return LayoutFriendlyURL.class;
	}

	public String getModelClassName() {
		return LayoutFriendlyURL.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("layoutFriendlyURLId", getLayoutFriendlyURLId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("plid", getPlid());
		attributes.put("privateLayout", getPrivateLayout());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("locale", getLocale());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long layoutFriendlyURLId = (Long)attributes.get("layoutFriendlyURLId");

		if (layoutFriendlyURLId != null) {
			setLayoutFriendlyURLId(layoutFriendlyURLId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Boolean privateLayout = (Boolean)attributes.get("privateLayout");

		if (privateLayout != null) {
			setPrivateLayout(privateLayout);
		}

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
		}

		String locale = (String)attributes.get("locale");

		if (locale != null) {
			setLocale(locale);
		}
	}

	/**
	* Returns the primary key of this layout friendly u r l.
	*
	* @return the primary key of this layout friendly u r l
	*/
	public long getPrimaryKey() {
		return _layoutFriendlyURL.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout friendly u r l.
	*
	* @param primaryKey the primary key of this layout friendly u r l
	*/
	public void setPrimaryKey(long primaryKey) {
		_layoutFriendlyURL.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this layout friendly u r l.
	*
	* @return the uuid of this layout friendly u r l
	*/
	public java.lang.String getUuid() {
		return _layoutFriendlyURL.getUuid();
	}

	/**
	* Sets the uuid of this layout friendly u r l.
	*
	* @param uuid the uuid of this layout friendly u r l
	*/
	public void setUuid(java.lang.String uuid) {
		_layoutFriendlyURL.setUuid(uuid);
	}

	/**
	* Returns the layout friendly u r l ID of this layout friendly u r l.
	*
	* @return the layout friendly u r l ID of this layout friendly u r l
	*/
	public long getLayoutFriendlyURLId() {
		return _layoutFriendlyURL.getLayoutFriendlyURLId();
	}

	/**
	* Sets the layout friendly u r l ID of this layout friendly u r l.
	*
	* @param layoutFriendlyURLId the layout friendly u r l ID of this layout friendly u r l
	*/
	public void setLayoutFriendlyURLId(long layoutFriendlyURLId) {
		_layoutFriendlyURL.setLayoutFriendlyURLId(layoutFriendlyURLId);
	}

	/**
	* Returns the group ID of this layout friendly u r l.
	*
	* @return the group ID of this layout friendly u r l
	*/
	public long getGroupId() {
		return _layoutFriendlyURL.getGroupId();
	}

	/**
	* Sets the group ID of this layout friendly u r l.
	*
	* @param groupId the group ID of this layout friendly u r l
	*/
	public void setGroupId(long groupId) {
		_layoutFriendlyURL.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this layout friendly u r l.
	*
	* @return the company ID of this layout friendly u r l
	*/
	public long getCompanyId() {
		return _layoutFriendlyURL.getCompanyId();
	}

	/**
	* Sets the company ID of this layout friendly u r l.
	*
	* @param companyId the company ID of this layout friendly u r l
	*/
	public void setCompanyId(long companyId) {
		_layoutFriendlyURL.setCompanyId(companyId);
	}

	/**
	* Returns the plid of this layout friendly u r l.
	*
	* @return the plid of this layout friendly u r l
	*/
	public long getPlid() {
		return _layoutFriendlyURL.getPlid();
	}

	/**
	* Sets the plid of this layout friendly u r l.
	*
	* @param plid the plid of this layout friendly u r l
	*/
	public void setPlid(long plid) {
		_layoutFriendlyURL.setPlid(plid);
	}

	/**
	* Returns the private layout of this layout friendly u r l.
	*
	* @return the private layout of this layout friendly u r l
	*/
	public boolean getPrivateLayout() {
		return _layoutFriendlyURL.getPrivateLayout();
	}

	/**
	* Returns <code>true</code> if this layout friendly u r l is private layout.
	*
	* @return <code>true</code> if this layout friendly u r l is private layout; <code>false</code> otherwise
	*/
	public boolean isPrivateLayout() {
		return _layoutFriendlyURL.isPrivateLayout();
	}

	/**
	* Sets whether this layout friendly u r l is private layout.
	*
	* @param privateLayout the private layout of this layout friendly u r l
	*/
	public void setPrivateLayout(boolean privateLayout) {
		_layoutFriendlyURL.setPrivateLayout(privateLayout);
	}

	/**
	* Returns the friendly u r l of this layout friendly u r l.
	*
	* @return the friendly u r l of this layout friendly u r l
	*/
	public java.lang.String getFriendlyURL() {
		return _layoutFriendlyURL.getFriendlyURL();
	}

	/**
	* Sets the friendly u r l of this layout friendly u r l.
	*
	* @param friendlyURL the friendly u r l of this layout friendly u r l
	*/
	public void setFriendlyURL(java.lang.String friendlyURL) {
		_layoutFriendlyURL.setFriendlyURL(friendlyURL);
	}

	/**
	* Returns the locale of this layout friendly u r l.
	*
	* @return the locale of this layout friendly u r l
	*/
	public java.lang.String getLocale() {
		return _layoutFriendlyURL.getLocale();
	}

	/**
	* Sets the locale of this layout friendly u r l.
	*
	* @param locale the locale of this layout friendly u r l
	*/
	public void setLocale(java.lang.String locale) {
		_layoutFriendlyURL.setLocale(locale);
	}

	public boolean isNew() {
		return _layoutFriendlyURL.isNew();
	}

	public void setNew(boolean n) {
		_layoutFriendlyURL.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutFriendlyURL.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutFriendlyURL.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutFriendlyURL.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutFriendlyURL.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layoutFriendlyURL.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutFriendlyURL.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_layoutFriendlyURL.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_layoutFriendlyURL.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutFriendlyURL.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutFriendlyURLWrapper((LayoutFriendlyURL)_layoutFriendlyURL.clone());
	}

	public int compareTo(
		com.liferay.portal.model.LayoutFriendlyURL layoutFriendlyURL) {
		return _layoutFriendlyURL.compareTo(layoutFriendlyURL);
	}

	@Override
	public int hashCode() {
		return _layoutFriendlyURL.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.LayoutFriendlyURL> toCacheModel() {
		return _layoutFriendlyURL.toCacheModel();
	}

	public com.liferay.portal.model.LayoutFriendlyURL toEscapedModel() {
		return new LayoutFriendlyURLWrapper(_layoutFriendlyURL.toEscapedModel());
	}

	public com.liferay.portal.model.LayoutFriendlyURL toUnescapedModel() {
		return new LayoutFriendlyURLWrapper(_layoutFriendlyURL.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutFriendlyURL.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutFriendlyURL.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutFriendlyURL.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public LayoutFriendlyURL getWrappedLayoutFriendlyURL() {
		return _layoutFriendlyURL;
	}

	public LayoutFriendlyURL getWrappedModel() {
		return _layoutFriendlyURL;
	}

	public void resetOriginalValues() {
		_layoutFriendlyURL.resetOriginalValues();
	}

	private LayoutFriendlyURL _layoutFriendlyURL;
}