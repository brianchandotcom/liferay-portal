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

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BrowserTracker}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BrowserTracker
 * @generated
 */
@ProviderType
public class BrowserTrackerWrapper extends BaseModelWrapper<BrowserTracker>
	implements BrowserTracker, ModelWrapper<BrowserTracker> {
	public BrowserTrackerWrapper(BrowserTracker browserTracker) {
		super(browserTracker);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("browserTrackerId", getBrowserTrackerId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("browserKey", getBrowserKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long browserTrackerId = (Long)attributes.get("browserTrackerId");

		if (browserTrackerId != null) {
			setBrowserTrackerId(browserTrackerId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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
	* Returns the browser key of this browser tracker.
	*
	* @return the browser key of this browser tracker
	*/
	@Override
	public long getBrowserKey() {
		return model.getBrowserKey();
	}

	/**
	* Returns the browser tracker ID of this browser tracker.
	*
	* @return the browser tracker ID of this browser tracker
	*/
	@Override
	public long getBrowserTrackerId() {
		return model.getBrowserTrackerId();
	}

	/**
	* Returns the company ID of this browser tracker.
	*
	* @return the company ID of this browser tracker
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the mvcc version of this browser tracker.
	*
	* @return the mvcc version of this browser tracker
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this browser tracker.
	*
	* @return the primary key of this browser tracker
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this browser tracker.
	*
	* @return the user ID of this browser tracker
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user uuid of this browser tracker.
	*
	* @return the user uuid of this browser tracker
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the browser key of this browser tracker.
	*
	* @param browserKey the browser key of this browser tracker
	*/
	@Override
	public void setBrowserKey(long browserKey) {
		model.setBrowserKey(browserKey);
	}

	/**
	* Sets the browser tracker ID of this browser tracker.
	*
	* @param browserTrackerId the browser tracker ID of this browser tracker
	*/
	@Override
	public void setBrowserTrackerId(long browserTrackerId) {
		model.setBrowserTrackerId(browserTrackerId);
	}

	/**
	* Sets the company ID of this browser tracker.
	*
	* @param companyId the company ID of this browser tracker
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the mvcc version of this browser tracker.
	*
	* @param mvccVersion the mvcc version of this browser tracker
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this browser tracker.
	*
	* @param primaryKey the primary key of this browser tracker
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this browser tracker.
	*
	* @param userId the user ID of this browser tracker
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user uuid of this browser tracker.
	*
	* @param userUuid the user uuid of this browser tracker
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected BrowserTrackerWrapper wrap(BrowserTracker browserTracker) {
		return new BrowserTrackerWrapper(browserTracker);
	}
}