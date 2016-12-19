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

package com.liferay.document.library.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DLSyncEvent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEvent
 * @generated
 */
@ProviderType
public class DLSyncEventWrapper implements DLSyncEvent,
	ModelWrapper<DLSyncEvent> {
	public DLSyncEventWrapper(DLSyncEvent dlSyncEvent) {
		_dlSyncEvent = dlSyncEvent;
	}

	@Override
	public Class<?> getModelClass() {
		return DLSyncEvent.class;
	}

	@Override
	public String getModelClassName() {
		return DLSyncEvent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("syncEventId", getSyncEventId());
		attributes.put("companyId", getCompanyId());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("event", getEvent());
		attributes.put("type", getType());
		attributes.put("typePK", getTypePK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long syncEventId = (Long)attributes.get("syncEventId");

		if (syncEventId != null) {
			setSyncEventId(syncEventId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		String event = (String)attributes.get("event");

		if (event != null) {
			setEvent(event);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long typePK = (Long)attributes.get("typePK");

		if (typePK != null) {
			setTypePK(typePK);
		}
	}

	@Override
	public DLSyncEvent toEscapedModel() {
		return new DLSyncEventWrapper(_dlSyncEvent.toEscapedModel());
	}

	@Override
	public DLSyncEvent toUnescapedModel() {
		return new DLSyncEventWrapper(_dlSyncEvent.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _dlSyncEvent.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlSyncEvent.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _dlSyncEvent.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlSyncEvent.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DLSyncEvent> toCacheModel() {
		return _dlSyncEvent.toCacheModel();
	}

	@Override
	public int compareTo(DLSyncEvent dlSyncEvent) {
		return _dlSyncEvent.compareTo(dlSyncEvent);
	}

	@Override
	public int hashCode() {
		return _dlSyncEvent.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dlSyncEvent.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new DLSyncEventWrapper((DLSyncEvent)_dlSyncEvent.clone());
	}

	/**
	* Returns the event of this dl sync event.
	*
	* @return the event of this dl sync event
	*/
	@Override
	public java.lang.String getEvent() {
		return _dlSyncEvent.getEvent();
	}

	/**
	* Returns the type of this dl sync event.
	*
	* @return the type of this dl sync event
	*/
	@Override
	public java.lang.String getType() {
		return _dlSyncEvent.getType();
	}

	@Override
	public java.lang.String toString() {
		return _dlSyncEvent.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlSyncEvent.toXmlString();
	}

	/**
	* Returns the company ID of this dl sync event.
	*
	* @return the company ID of this dl sync event
	*/
	@Override
	public long getCompanyId() {
		return _dlSyncEvent.getCompanyId();
	}

	/**
	* Returns the modified time of this dl sync event.
	*
	* @return the modified time of this dl sync event
	*/
	@Override
	public long getModifiedTime() {
		return _dlSyncEvent.getModifiedTime();
	}

	/**
	* Returns the primary key of this dl sync event.
	*
	* @return the primary key of this dl sync event
	*/
	@Override
	public long getPrimaryKey() {
		return _dlSyncEvent.getPrimaryKey();
	}

	/**
	* Returns the sync event ID of this dl sync event.
	*
	* @return the sync event ID of this dl sync event
	*/
	@Override
	public long getSyncEventId() {
		return _dlSyncEvent.getSyncEventId();
	}

	/**
	* Returns the type pk of this dl sync event.
	*
	* @return the type pk of this dl sync event
	*/
	@Override
	public long getTypePK() {
		return _dlSyncEvent.getTypePK();
	}

	@Override
	public void persist() {
		_dlSyncEvent.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlSyncEvent.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this dl sync event.
	*
	* @param companyId the company ID of this dl sync event
	*/
	@Override
	public void setCompanyId(long companyId) {
		_dlSyncEvent.setCompanyId(companyId);
	}

	/**
	* Sets the event of this dl sync event.
	*
	* @param event the event of this dl sync event
	*/
	@Override
	public void setEvent(java.lang.String event) {
		_dlSyncEvent.setEvent(event);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_dlSyncEvent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_dlSyncEvent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_dlSyncEvent.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified time of this dl sync event.
	*
	* @param modifiedTime the modified time of this dl sync event
	*/
	@Override
	public void setModifiedTime(long modifiedTime) {
		_dlSyncEvent.setModifiedTime(modifiedTime);
	}

	@Override
	public void setNew(boolean n) {
		_dlSyncEvent.setNew(n);
	}

	/**
	* Sets the primary key of this dl sync event.
	*
	* @param primaryKey the primary key of this dl sync event
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlSyncEvent.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dlSyncEvent.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the sync event ID of this dl sync event.
	*
	* @param syncEventId the sync event ID of this dl sync event
	*/
	@Override
	public void setSyncEventId(long syncEventId) {
		_dlSyncEvent.setSyncEventId(syncEventId);
	}

	/**
	* Sets the type of this dl sync event.
	*
	* @param type the type of this dl sync event
	*/
	@Override
	public void setType(java.lang.String type) {
		_dlSyncEvent.setType(type);
	}

	/**
	* Sets the type pk of this dl sync event.
	*
	* @param typePK the type pk of this dl sync event
	*/
	@Override
	public void setTypePK(long typePK) {
		_dlSyncEvent.setTypePK(typePK);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLSyncEventWrapper)) {
			return false;
		}

		DLSyncEventWrapper dlSyncEventWrapper = (DLSyncEventWrapper)obj;

		if (Objects.equals(_dlSyncEvent, dlSyncEventWrapper._dlSyncEvent)) {
			return true;
		}

		return false;
	}

	@Override
	public DLSyncEvent getWrappedModel() {
		return _dlSyncEvent;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlSyncEvent.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlSyncEvent.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlSyncEvent.resetOriginalValues();
	}

	private final DLSyncEvent _dlSyncEvent;
}