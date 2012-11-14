/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Release}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Release
 * @generated
 */
public class ReleaseWrapper implements Release, ModelWrapper<Release> {
	public ReleaseWrapper(Release release) {
		_release = release;
	}

	public Class<?> getModelClass() {
		return Release.class;
	}

	public String getModelClassName() {
		return Release.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("releaseId", getReleaseId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("servletContextName", getServletContextName());
		attributes.put("buildNumber", getBuildNumber());
		attributes.put("buildDate", getBuildDate());
		attributes.put("verified", getVerified());
		attributes.put("state", getState());
		attributes.put("testString", getTestString());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long releaseId = (Long)attributes.get("releaseId");

		if (releaseId != null) {
			setReleaseId(releaseId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String servletContextName = (String)attributes.get("servletContextName");

		if (servletContextName != null) {
			setServletContextName(servletContextName);
		}

		Integer buildNumber = (Integer)attributes.get("buildNumber");

		if (buildNumber != null) {
			setBuildNumber(buildNumber);
		}

		Date buildDate = (Date)attributes.get("buildDate");

		if (buildDate != null) {
			setBuildDate(buildDate);
		}

		Boolean verified = (Boolean)attributes.get("verified");

		if (verified != null) {
			setVerified(verified);
		}

		Integer state = (Integer)attributes.get("state");

		if (state != null) {
			setState(state);
		}

		String testString = (String)attributes.get("testString");

		if (testString != null) {
			setTestString(testString);
		}
	}

	/**
	* Returns the primary key of this release.
	*
	* @return the primary key of this release
	*/
	public long getPrimaryKey() {
		return _release.getPrimaryKey();
	}

	/**
	* Sets the primary key of this release.
	*
	* @param primaryKey the primary key of this release
	*/
	public void setPrimaryKey(long primaryKey) {
		_release.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the release ID of this release.
	*
	* @return the release ID of this release
	*/
	public long getReleaseId() {
		return _release.getReleaseId();
	}

	/**
	* Sets the release ID of this release.
	*
	* @param releaseId the release ID of this release
	*/
	public void setReleaseId(long releaseId) {
		_release.setReleaseId(releaseId);
	}

	/**
	* Returns the create date of this release.
	*
	* @return the create date of this release
	*/
	public java.util.Date getCreateDate() {
		return _release.getCreateDate();
	}

	/**
	* Sets the create date of this release.
	*
	* @param createDate the create date of this release
	*/
	public void setCreateDate(java.util.Date createDate) {
		_release.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this release.
	*
	* @return the modified date of this release
	*/
	public java.util.Date getModifiedDate() {
		return _release.getModifiedDate();
	}

	/**
	* Sets the modified date of this release.
	*
	* @param modifiedDate the modified date of this release
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_release.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the servlet context name of this release.
	*
	* @return the servlet context name of this release
	*/
	public java.lang.String getServletContextName() {
		return _release.getServletContextName();
	}

	/**
	* Sets the servlet context name of this release.
	*
	* @param servletContextName the servlet context name of this release
	*/
	public void setServletContextName(java.lang.String servletContextName) {
		_release.setServletContextName(servletContextName);
	}

	/**
	* Returns the build number of this release.
	*
	* @return the build number of this release
	*/
	public int getBuildNumber() {
		return _release.getBuildNumber();
	}

	/**
	* Sets the build number of this release.
	*
	* @param buildNumber the build number of this release
	*/
	public void setBuildNumber(int buildNumber) {
		_release.setBuildNumber(buildNumber);
	}

	/**
	* Returns the build date of this release.
	*
	* @return the build date of this release
	*/
	public java.util.Date getBuildDate() {
		return _release.getBuildDate();
	}

	/**
	* Sets the build date of this release.
	*
	* @param buildDate the build date of this release
	*/
	public void setBuildDate(java.util.Date buildDate) {
		_release.setBuildDate(buildDate);
	}

	/**
	* Returns the verified of this release.
	*
	* @return the verified of this release
	*/
	public boolean getVerified() {
		return _release.getVerified();
	}

	/**
	* Returns <code>true</code> if this release is verified.
	*
	* @return <code>true</code> if this release is verified; <code>false</code> otherwise
	*/
	public boolean isVerified() {
		return _release.isVerified();
	}

	/**
	* Sets whether this release is verified.
	*
	* @param verified the verified of this release
	*/
	public void setVerified(boolean verified) {
		_release.setVerified(verified);
	}

	/**
	* Returns the state of this release.
	*
	* @return the state of this release
	*/
	public int getState() {
		return _release.getState();
	}

	/**
	* Sets the state of this release.
	*
	* @param state the state of this release
	*/
	public void setState(int state) {
		_release.setState(state);
	}

	/**
	* Returns the test string of this release.
	*
	* @return the test string of this release
	*/
	public java.lang.String getTestString() {
		return _release.getTestString();
	}

	/**
	* Sets the test string of this release.
	*
	* @param testString the test string of this release
	*/
	public void setTestString(java.lang.String testString) {
		_release.setTestString(testString);
	}

	public boolean isNew() {
		return _release.isNew();
	}

	public void setNew(boolean n) {
		_release.setNew(n);
	}

	public boolean isCachedModel() {
		return _release.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_release.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _release.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _release.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_release.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _release.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_release.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ReleaseWrapper((Release)_release.clone());
	}

	public int compareTo(com.liferay.portal.model.Release release) {
		return _release.compareTo(release);
	}

	@Override
	public int hashCode() {
		return _release.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Release> toCacheModel() {
		return _release.toCacheModel();
	}

	public com.liferay.portal.model.Release toEscapedModel() {
		return new ReleaseWrapper(_release.toEscapedModel());
	}

	public com.liferay.portal.model.Release toUnescapedModel() {
		return new ReleaseWrapper(_release.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _release.toString();
	}

	public java.lang.String toXmlString() {
		return _release.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_release.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Release getWrappedRelease() {
		return _release;
	}

	public Release getWrappedModel() {
		return _release;
	}

	public void resetOriginalValues() {
		_release.resetOriginalValues();
	}

	private Release _release;
}