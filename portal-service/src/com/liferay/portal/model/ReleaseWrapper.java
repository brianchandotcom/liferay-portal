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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Release}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Release
 * @generated
 */
@ProviderType
public class ReleaseWrapper implements Release, ModelWrapper<Release> {
	public ReleaseWrapper(Release release) {
		_release = release;
	}

	@Override
	public Class<?> getModelClass() {
		return Release.class;
	}

	@Override
	public String getModelClassName() {
		return Release.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("releaseId", getReleaseId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("servletContextName", getServletContextName());
		attributes.put("schemaVersion", getSchemaVersion());
		attributes.put("buildNumber", getBuildNumber());
		attributes.put("buildDate", getBuildDate());
		attributes.put("verified", getVerified());
		attributes.put("state", getState());
		attributes.put("testString", getTestString());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

		String schemaVersion = (String)attributes.get("schemaVersion");

		if (schemaVersion != null) {
			setSchemaVersion(schemaVersion);
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

	@Override
	public java.lang.Object clone() {
		return new ReleaseWrapper((Release)_release.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Release release) {
		return _release.compareTo(release);
	}

	/**
	* Returns the build date of this release.
	*
	* @return the build date of this release
	*/
	@Override
	public Date getBuildDate() {
		return _release.getBuildDate();
	}

	/**
	* Returns the build number of this release.
	*
	* @return the build number of this release
	*/
	@Override
	public int getBuildNumber() {
		return _release.getBuildNumber();
	}

	@Override
	public java.lang.String getBundleSymbolicName() {
		return _release.getBundleSymbolicName();
	}

	/**
	* Returns the create date of this release.
	*
	* @return the create date of this release
	*/
	@Override
	public Date getCreateDate() {
		return _release.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _release.getExpandoBridge();
	}

	/**
	* Returns the modified date of this release.
	*
	* @return the modified date of this release
	*/
	@Override
	public Date getModifiedDate() {
		return _release.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this release.
	*
	* @return the mvcc version of this release
	*/
	@Override
	public long getMvccVersion() {
		return _release.getMvccVersion();
	}

	/**
	* Returns the primary key of this release.
	*
	* @return the primary key of this release
	*/
	@Override
	public long getPrimaryKey() {
		return _release.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _release.getPrimaryKeyObj();
	}

	/**
	* Returns the release ID of this release.
	*
	* @return the release ID of this release
	*/
	@Override
	public long getReleaseId() {
		return _release.getReleaseId();
	}

	/**
	* Returns the schema version of this release.
	*
	* @return the schema version of this release
	*/
	@Override
	public java.lang.String getSchemaVersion() {
		return _release.getSchemaVersion();
	}

	/**
	* Returns the servlet context name of this release.
	*
	* @return the servlet context name of this release
	*/
	@Override
	public java.lang.String getServletContextName() {
		return _release.getServletContextName();
	}

	/**
	* Returns the state of this release.
	*
	* @return the state of this release
	*/
	@Override
	public int getState() {
		return _release.getState();
	}

	/**
	* Returns the test string of this release.
	*
	* @return the test string of this release
	*/
	@Override
	public java.lang.String getTestString() {
		return _release.getTestString();
	}

	/**
	* Returns the verified of this release.
	*
	* @return the verified of this release
	*/
	@Override
	public boolean getVerified() {
		return _release.getVerified();
	}

	@Override
	public int hashCode() {
		return _release.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _release.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _release.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _release.isNew();
	}

	/**
	* Returns <code>true</code> if this release is verified.
	*
	* @return <code>true</code> if this release is verified; <code>false</code> otherwise
	*/
	@Override
	public boolean isVerified() {
		return _release.isVerified();
	}

	@Override
	public void persist() {
		_release.persist();
	}

	/**
	* Sets the build date of this release.
	*
	* @param buildDate the build date of this release
	*/
	@Override
	public void setBuildDate(Date buildDate) {
		_release.setBuildDate(buildDate);
	}

	/**
	* Sets the build number of this release.
	*
	* @param buildNumber the build number of this release
	*/
	@Override
	public void setBuildNumber(int buildNumber) {
		_release.setBuildNumber(buildNumber);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_release.setCachedModel(cachedModel);
	}

	/**
	* Sets the create date of this release.
	*
	* @param createDate the create date of this release
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_release.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_release.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_release.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_release.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this release.
	*
	* @param modifiedDate the modified date of this release
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_release.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this release.
	*
	* @param mvccVersion the mvcc version of this release
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_release.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_release.setNew(n);
	}

	/**
	* Sets the primary key of this release.
	*
	* @param primaryKey the primary key of this release
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_release.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_release.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the release ID of this release.
	*
	* @param releaseId the release ID of this release
	*/
	@Override
	public void setReleaseId(long releaseId) {
		_release.setReleaseId(releaseId);
	}

	/**
	* Sets the schema version of this release.
	*
	* @param schemaVersion the schema version of this release
	*/
	@Override
	public void setSchemaVersion(java.lang.String schemaVersion) {
		_release.setSchemaVersion(schemaVersion);
	}

	/**
	* Sets the servlet context name of this release.
	*
	* @param servletContextName the servlet context name of this release
	*/
	@Override
	public void setServletContextName(java.lang.String servletContextName) {
		_release.setServletContextName(servletContextName);
	}

	/**
	* Sets the state of this release.
	*
	* @param state the state of this release
	*/
	@Override
	public void setState(int state) {
		_release.setState(state);
	}

	/**
	* Sets the test string of this release.
	*
	* @param testString the test string of this release
	*/
	@Override
	public void setTestString(java.lang.String testString) {
		_release.setTestString(testString);
	}

	/**
	* Sets whether this release is verified.
	*
	* @param verified the verified of this release
	*/
	@Override
	public void setVerified(boolean verified) {
		_release.setVerified(verified);
	}

	@Override
	public CacheModel<com.liferay.portal.model.Release> toCacheModel() {
		return _release.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Release toEscapedModel() {
		return new ReleaseWrapper(_release.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _release.toString();
	}

	@Override
	public com.liferay.portal.model.Release toUnescapedModel() {
		return new ReleaseWrapper(_release.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _release.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ReleaseWrapper)) {
			return false;
		}

		ReleaseWrapper releaseWrapper = (ReleaseWrapper)obj;

		if (Validator.equals(_release, releaseWrapper._release)) {
			return true;
		}

		return false;
	}

	@Override
	public Release getWrappedModel() {
		return _release;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _release.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _release.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_release.resetOriginalValues();
	}

	private final Release _release;
}