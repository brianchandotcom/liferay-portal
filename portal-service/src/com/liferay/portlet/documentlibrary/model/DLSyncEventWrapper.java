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

package com.liferay.portlet.documentlibrary.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

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

	/**
	* Returns the primary key of this d l sync event.
	*
	* @return the primary key of this d l sync event
	*/
	@Override
	public long getPrimaryKey() {
		return _dlSyncEvent.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d l sync event.
	*
	* @param primaryKey the primary key of this d l sync event
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlSyncEvent.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the sync event ID of this d l sync event.
	*
	* @return the sync event ID of this d l sync event
	*/
	@Override
	public long getSyncEventId() {
		return _dlSyncEvent.getSyncEventId();
	}

	/**
	* Sets the sync event ID of this d l sync event.
	*
	* @param syncEventId the sync event ID of this d l sync event
	*/
	@Override
	public void setSyncEventId(long syncEventId) {
		_dlSyncEvent.setSyncEventId(syncEventId);
	}

	/**
	* Returns the modified time of this d l sync event.
	*
	* @return the modified time of this d l sync event
	*/
	@Override
	public long getModifiedTime() {
		return _dlSyncEvent.getModifiedTime();
	}

	/**
	* Sets the modified time of this d l sync event.
	*
	* @param modifiedTime the modified time of this d l sync event
	*/
	@Override
	public void setModifiedTime(long modifiedTime) {
		_dlSyncEvent.setModifiedTime(modifiedTime);
	}

	/**
	* Returns the event of this d l sync event.
	*
	* @return the event of this d l sync event
	*/
	@Override
	public java.lang.String getEvent() {
		return _dlSyncEvent.getEvent();
	}

	/**
	* Sets the event of this d l sync event.
	*
	* @param event the event of this d l sync event
	*/
	@Override
	public void setEvent(java.lang.String event) {
		_dlSyncEvent.setEvent(event);
	}

	/**
	* Returns the type of this d l sync event.
	*
	* @return the type of this d l sync event
	*/
	@Override
	public java.lang.String getType() {
		return _dlSyncEvent.getType();
	}

	/**
	* Sets the type of this d l sync event.
	*
	* @param type the type of this d l sync event
	*/
	@Override
	public void setType(java.lang.String type) {
		_dlSyncEvent.setType(type);
	}

	/**
	* Returns the type p k of this d l sync event.
	*
	* @return the type p k of this d l sync event
	*/
	@Override
	public long getTypePK() {
		return _dlSyncEvent.getTypePK();
	}

	/**
	* Sets the type p k of this d l sync event.
	*
	* @param typePK the type p k of this d l sync event
	*/
	@Override
	public void setTypePK(long typePK) {
		_dlSyncEvent.setTypePK(typePK);
	}

	@Override
	public boolean isNew() {
		return _dlSyncEvent.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_dlSyncEvent.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _dlSyncEvent.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlSyncEvent.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _dlSyncEvent.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _dlSyncEvent.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlSyncEvent.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlSyncEvent.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_dlSyncEvent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_dlSyncEvent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlSyncEvent.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DLSyncEventWrapper((DLSyncEvent)_dlSyncEvent.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLSyncEvent dlSyncEvent) {
		return _dlSyncEvent.compareTo(dlSyncEvent);
	}

	@Override
	public int hashCode() {
		return _dlSyncEvent.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLSyncEvent> toCacheModel() {
		return _dlSyncEvent.toCacheModel();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent toEscapedModel() {
		return new DLSyncEventWrapper(_dlSyncEvent.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent toUnescapedModel() {
		return new DLSyncEventWrapper(_dlSyncEvent.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlSyncEvent.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlSyncEvent.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlSyncEvent.persist();
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

		if (Validator.equals(_dlSyncEvent, dlSyncEventWrapper._dlSyncEvent)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public DLSyncEvent getWrappedDLSyncEvent() {
		return _dlSyncEvent;
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

	private DLSyncEvent _dlSyncEvent;
}