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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BrowserTracker}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BrowserTracker
 * @generated
 */
public class BrowserTrackerWrapper implements BrowserTracker,
	ModelWrapper<BrowserTracker> {
	public BrowserTrackerWrapper(BrowserTracker browserTracker) {
		_browserTracker = browserTracker;
	}

	public Class<?> getModelClass() {
		return BrowserTracker.class;
	}

	public String getModelClassName() {
		return BrowserTracker.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("browserTrackerId", getBrowserTrackerId());
		attributes.put("userId", getUserId());
		attributes.put("browserKey", getBrowserKey());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long browserTrackerId = (Long)attributes.get("browserTrackerId");

		if (browserTrackerId != null) {
			setBrowserTrackerId(browserTrackerId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long browserKey = (Long)attributes.get("browserKey");

		if (browserKey != null) {
			setBrowserKey(browserKey);
		}
	}

	/**
	* Returns the primary key of this browser tracker.
	*
	* @return the primary key of this browser tracker
	*/
	public long getPrimaryKey() {
		return _browserTracker.getPrimaryKey();
	}

	/**
	* Sets the primary key of this browser tracker.
	*
	* @param primaryKey the primary key of this browser tracker
	*/
	public void setPrimaryKey(long primaryKey) {
		_browserTracker.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the browser tracker ID of this browser tracker.
	*
	* @return the browser tracker ID of this browser tracker
	*/
	public long getBrowserTrackerId() {
		return _browserTracker.getBrowserTrackerId();
	}

	/**
	* Sets the browser tracker ID of this browser tracker.
	*
	* @param browserTrackerId the browser tracker ID of this browser tracker
	*/
	public void setBrowserTrackerId(long browserTrackerId) {
		_browserTracker.setBrowserTrackerId(browserTrackerId);
	}

	/**
	* Returns the user ID of this browser tracker.
	*
	* @return the user ID of this browser tracker
	*/
	public long getUserId() {
		return _browserTracker.getUserId();
	}

	/**
	* Sets the user ID of this browser tracker.
	*
	* @param userId the user ID of this browser tracker
	*/
	public void setUserId(long userId) {
		_browserTracker.setUserId(userId);
	}

	/**
	* Returns the user uuid of this browser tracker.
	*
	* @return the user uuid of this browser tracker
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTracker.getUserUuid();
	}

	/**
	* Sets the user uuid of this browser tracker.
	*
	* @param userUuid the user uuid of this browser tracker
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_browserTracker.setUserUuid(userUuid);
	}

	/**
	* Returns the browser key of this browser tracker.
	*
	* @return the browser key of this browser tracker
	*/
	public long getBrowserKey() {
		return _browserTracker.getBrowserKey();
	}

	/**
	* Sets the browser key of this browser tracker.
	*
	* @param browserKey the browser key of this browser tracker
	*/
	public void setBrowserKey(long browserKey) {
		_browserTracker.setBrowserKey(browserKey);
	}

	public boolean isNew() {
		return _browserTracker.isNew();
	}

	public void setNew(boolean n) {
		_browserTracker.setNew(n);
	}

	public boolean isCachedModel() {
		return _browserTracker.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_browserTracker.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _browserTracker.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _browserTracker.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_browserTracker.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _browserTracker.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_browserTracker.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_browserTracker.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_browserTracker.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new BrowserTrackerWrapper((BrowserTracker)_browserTracker.clone());
	}

	public int compareTo(com.liferay.portal.model.BrowserTracker browserTracker) {
		return _browserTracker.compareTo(browserTracker);
	}

	@Override
	public int hashCode() {
		return _browserTracker.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.BrowserTracker> toCacheModel() {
		return _browserTracker.toCacheModel();
	}

	public com.liferay.portal.model.BrowserTracker toEscapedModel() {
		return new BrowserTrackerWrapper(_browserTracker.toEscapedModel());
	}

	public com.liferay.portal.model.BrowserTracker toUnescapedModel() {
		return new BrowserTrackerWrapper(_browserTracker.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _browserTracker.toString();
	}

	public java.lang.String toXmlString() {
		return _browserTracker.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_browserTracker.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public BrowserTracker getWrappedBrowserTracker() {
		return _browserTracker;
	}

	public BrowserTracker getWrappedModel() {
		return _browserTracker;
	}

	public void resetOriginalValues() {
		_browserTracker.resetOriginalValues();
	}

	private BrowserTracker _browserTracker;
}