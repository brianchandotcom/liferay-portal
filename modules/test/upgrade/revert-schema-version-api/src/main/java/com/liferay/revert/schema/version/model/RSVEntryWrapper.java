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

package com.liferay.revert.schema.version.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link RSVEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RSVEntry
 * @generated
 */
public class RSVEntryWrapper implements ModelWrapper<RSVEntry>, RSVEntry {

	public RSVEntryWrapper(RSVEntry rsvEntry) {
		_rsvEntry = rsvEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return RSVEntry.class;
	}

	@Override
	public String getModelClassName() {
		return RSVEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("rsvEntryId", getRsvEntryId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long rsvEntryId = (Long)attributes.get("rsvEntryId");

		if (rsvEntryId != null) {
			setRsvEntryId(rsvEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public Object clone() {
		return new RSVEntryWrapper((RSVEntry)_rsvEntry.clone());
	}

	@Override
	public int compareTo(RSVEntry rsvEntry) {
		return _rsvEntry.compareTo(rsvEntry);
	}

	/**
	 * Returns the company ID of this rsv entry.
	 *
	 * @return the company ID of this rsv entry
	 */
	@Override
	public long getCompanyId() {
		return _rsvEntry.getCompanyId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _rsvEntry.getExpandoBridge();
	}

	/**
	 * Returns the mvcc version of this rsv entry.
	 *
	 * @return the mvcc version of this rsv entry
	 */
	@Override
	public long getMvccVersion() {
		return _rsvEntry.getMvccVersion();
	}

	/**
	 * Returns the primary key of this rsv entry.
	 *
	 * @return the primary key of this rsv entry
	 */
	@Override
	public long getPrimaryKey() {
		return _rsvEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _rsvEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the rsv entry ID of this rsv entry.
	 *
	 * @return the rsv entry ID of this rsv entry
	 */
	@Override
	public long getRsvEntryId() {
		return _rsvEntry.getRsvEntryId();
	}

	@Override
	public int hashCode() {
		return _rsvEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _rsvEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _rsvEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _rsvEntry.isNew();
	}

	@Override
	public void persist() {
		_rsvEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_rsvEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this rsv entry.
	 *
	 * @param companyId the company ID of this rsv entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_rsvEntry.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_rsvEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_rsvEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_rsvEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the mvcc version of this rsv entry.
	 *
	 * @param mvccVersion the mvcc version of this rsv entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_rsvEntry.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_rsvEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this rsv entry.
	 *
	 * @param primaryKey the primary key of this rsv entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_rsvEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_rsvEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the rsv entry ID of this rsv entry.
	 *
	 * @param rsvEntryId the rsv entry ID of this rsv entry
	 */
	@Override
	public void setRsvEntryId(long rsvEntryId) {
		_rsvEntry.setRsvEntryId(rsvEntryId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<RSVEntry> toCacheModel() {
		return _rsvEntry.toCacheModel();
	}

	@Override
	public RSVEntry toEscapedModel() {
		return new RSVEntryWrapper(_rsvEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _rsvEntry.toString();
	}

	@Override
	public RSVEntry toUnescapedModel() {
		return new RSVEntryWrapper(_rsvEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _rsvEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RSVEntryWrapper)) {
			return false;
		}

		RSVEntryWrapper rsvEntryWrapper = (RSVEntryWrapper)object;

		if (Objects.equals(_rsvEntry, rsvEntryWrapper._rsvEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public RSVEntry getWrappedModel() {
		return _rsvEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _rsvEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _rsvEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_rsvEntry.resetOriginalValues();
	}

	private final RSVEntry _rsvEntry;

}