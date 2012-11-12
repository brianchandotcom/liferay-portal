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
 * This class is a wrapper for {@link RepositoryEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RepositoryEntry
 * @generated
 */
public class RepositoryEntryWrapper implements RepositoryEntry,
	ModelWrapper<RepositoryEntry> {
	public RepositoryEntryWrapper(RepositoryEntry repositoryEntry) {
		_repositoryEntry = repositoryEntry;
	}

	public Class<?> getModelClass() {
		return RepositoryEntry.class;
	}

	public String getModelClassName() {
		return RepositoryEntry.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("repositoryEntryId", getRepositoryEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("mappedId", getMappedId());
		attributes.put("manualCheckInRequired", getManualCheckInRequired());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long repositoryEntryId = (Long)attributes.get("repositoryEntryId");

		if (repositoryEntryId != null) {
			setRepositoryEntryId(repositoryEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		String mappedId = (String)attributes.get("mappedId");

		if (mappedId != null) {
			setMappedId(mappedId);
		}

		Boolean manualCheckInRequired = (Boolean)attributes.get(
				"manualCheckInRequired");

		if (manualCheckInRequired != null) {
			setManualCheckInRequired(manualCheckInRequired);
		}
	}

	/**
	* Returns the primary key of this repository entry.
	*
	* @return the primary key of this repository entry
	*/
	public long getPrimaryKey() {
		return _repositoryEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this repository entry.
	*
	* @param primaryKey the primary key of this repository entry
	*/
	public void setPrimaryKey(long primaryKey) {
		_repositoryEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this repository entry.
	*
	* @return the uuid of this repository entry
	*/
	public java.lang.String getUuid() {
		return _repositoryEntry.getUuid();
	}

	/**
	* Sets the uuid of this repository entry.
	*
	* @param uuid the uuid of this repository entry
	*/
	public void setUuid(java.lang.String uuid) {
		_repositoryEntry.setUuid(uuid);
	}

	/**
	* Returns the repository entry ID of this repository entry.
	*
	* @return the repository entry ID of this repository entry
	*/
	public long getRepositoryEntryId() {
		return _repositoryEntry.getRepositoryEntryId();
	}

	/**
	* Sets the repository entry ID of this repository entry.
	*
	* @param repositoryEntryId the repository entry ID of this repository entry
	*/
	public void setRepositoryEntryId(long repositoryEntryId) {
		_repositoryEntry.setRepositoryEntryId(repositoryEntryId);
	}

	/**
	* Returns the group ID of this repository entry.
	*
	* @return the group ID of this repository entry
	*/
	public long getGroupId() {
		return _repositoryEntry.getGroupId();
	}

	/**
	* Sets the group ID of this repository entry.
	*
	* @param groupId the group ID of this repository entry
	*/
	public void setGroupId(long groupId) {
		_repositoryEntry.setGroupId(groupId);
	}

	/**
	* Returns the repository ID of this repository entry.
	*
	* @return the repository ID of this repository entry
	*/
	public long getRepositoryId() {
		return _repositoryEntry.getRepositoryId();
	}

	/**
	* Sets the repository ID of this repository entry.
	*
	* @param repositoryId the repository ID of this repository entry
	*/
	public void setRepositoryId(long repositoryId) {
		_repositoryEntry.setRepositoryId(repositoryId);
	}

	/**
	* Returns the mapped ID of this repository entry.
	*
	* @return the mapped ID of this repository entry
	*/
	public java.lang.String getMappedId() {
		return _repositoryEntry.getMappedId();
	}

	/**
	* Sets the mapped ID of this repository entry.
	*
	* @param mappedId the mapped ID of this repository entry
	*/
	public void setMappedId(java.lang.String mappedId) {
		_repositoryEntry.setMappedId(mappedId);
	}

	/**
	* Returns the manual check in required of this repository entry.
	*
	* @return the manual check in required of this repository entry
	*/
	public boolean getManualCheckInRequired() {
		return _repositoryEntry.getManualCheckInRequired();
	}

	/**
	* Returns <code>true</code> if this repository entry is manual check in required.
	*
	* @return <code>true</code> if this repository entry is manual check in required; <code>false</code> otherwise
	*/
	public boolean isManualCheckInRequired() {
		return _repositoryEntry.isManualCheckInRequired();
	}

	/**
	* Sets whether this repository entry is manual check in required.
	*
	* @param manualCheckInRequired the manual check in required of this repository entry
	*/
	public void setManualCheckInRequired(boolean manualCheckInRequired) {
		_repositoryEntry.setManualCheckInRequired(manualCheckInRequired);
	}

	public boolean isNew() {
		return _repositoryEntry.isNew();
	}

	public void setNew(boolean n) {
		_repositoryEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _repositoryEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_repositoryEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _repositoryEntry.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _repositoryEntry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_repositoryEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _repositoryEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_repositoryEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new RepositoryEntryWrapper((RepositoryEntry)_repositoryEntry.clone());
	}

	public int compareTo(
		com.liferay.portal.model.RepositoryEntry repositoryEntry) {
		return _repositoryEntry.compareTo(repositoryEntry);
	}

	@Override
	public int hashCode() {
		return _repositoryEntry.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.RepositoryEntry> toCacheModel() {
		return _repositoryEntry.toCacheModel();
	}

	public com.liferay.portal.model.RepositoryEntry toEscapedModel() {
		return new RepositoryEntryWrapper(_repositoryEntry.toEscapedModel());
	}

	public com.liferay.portal.model.RepositoryEntry toUnescapedModel() {
		return new RepositoryEntryWrapper(_repositoryEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _repositoryEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _repositoryEntry.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_repositoryEntry.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public RepositoryEntry getWrappedRepositoryEntry() {
		return _repositoryEntry;
	}

	public RepositoryEntry getWrappedModel() {
		return _repositoryEntry;
	}

	public void resetOriginalValues() {
		_repositoryEntry.resetOriginalValues();
	}

	private RepositoryEntry _repositoryEntry;
}