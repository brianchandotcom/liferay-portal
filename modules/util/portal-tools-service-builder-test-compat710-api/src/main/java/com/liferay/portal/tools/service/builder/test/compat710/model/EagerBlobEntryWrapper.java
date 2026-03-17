/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.sql.Blob;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link EagerBlobEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntry
 * @generated
 */
public class EagerBlobEntryWrapper
	implements EagerBlobEntry, ModelWrapper<EagerBlobEntry> {

	public EagerBlobEntryWrapper(EagerBlobEntry eagerBlobEntry) {
		_eagerBlobEntry = eagerBlobEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return EagerBlobEntry.class;
	}

	@Override
	public String getModelClassName() {
		return EagerBlobEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("eagerBlobEntryId", getEagerBlobEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("blob", getBlob());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long eagerBlobEntryId = (Long)attributes.get("eagerBlobEntryId");

		if (eagerBlobEntryId != null) {
			setEagerBlobEntryId(eagerBlobEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Blob blob = (Blob)attributes.get("blob");

		if (blob != null) {
			setBlob(blob);
		}
	}

	@Override
	public Object clone() {
		return new EagerBlobEntryWrapper(
			(EagerBlobEntry)_eagerBlobEntry.clone());
	}

	@Override
	public int compareTo(EagerBlobEntry eagerBlobEntry) {
		return _eagerBlobEntry.compareTo(eagerBlobEntry);
	}

	/**
	 * Returns the blob of this eager blob entry.
	 *
	 * @return the blob of this eager blob entry
	 */
	@Override
	public Blob getBlob() {
		return _eagerBlobEntry.getBlob();
	}

	/**
	 * Returns the eager blob entry ID of this eager blob entry.
	 *
	 * @return the eager blob entry ID of this eager blob entry
	 */
	@Override
	public long getEagerBlobEntryId() {
		return _eagerBlobEntry.getEagerBlobEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _eagerBlobEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this eager blob entry.
	 *
	 * @return the group ID of this eager blob entry
	 */
	@Override
	public long getGroupId() {
		return _eagerBlobEntry.getGroupId();
	}

	/**
	 * Returns the primary key of this eager blob entry.
	 *
	 * @return the primary key of this eager blob entry
	 */
	@Override
	public long getPrimaryKey() {
		return _eagerBlobEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _eagerBlobEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the uuid of this eager blob entry.
	 *
	 * @return the uuid of this eager blob entry
	 */
	@Override
	public String getUuid() {
		return _eagerBlobEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _eagerBlobEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _eagerBlobEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _eagerBlobEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _eagerBlobEntry.isNew();
	}

	@Override
	public void persist() {
		_eagerBlobEntry.persist();
	}

	/**
	 * Sets the blob of this eager blob entry.
	 *
	 * @param blob the blob of this eager blob entry
	 */
	@Override
	public void setBlob(Blob blob) {
		_eagerBlobEntry.setBlob(blob);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_eagerBlobEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the eager blob entry ID of this eager blob entry.
	 *
	 * @param eagerBlobEntryId the eager blob entry ID of this eager blob entry
	 */
	@Override
	public void setEagerBlobEntryId(long eagerBlobEntryId) {
		_eagerBlobEntry.setEagerBlobEntryId(eagerBlobEntryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_eagerBlobEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_eagerBlobEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_eagerBlobEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this eager blob entry.
	 *
	 * @param groupId the group ID of this eager blob entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_eagerBlobEntry.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_eagerBlobEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this eager blob entry.
	 *
	 * @param primaryKey the primary key of this eager blob entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_eagerBlobEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_eagerBlobEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the uuid of this eager blob entry.
	 *
	 * @param uuid the uuid of this eager blob entry
	 */
	@Override
	public void setUuid(String uuid) {
		_eagerBlobEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<EagerBlobEntry>
		toCacheModel() {

		return _eagerBlobEntry.toCacheModel();
	}

	@Override
	public EagerBlobEntry toEscapedModel() {
		return new EagerBlobEntryWrapper(_eagerBlobEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _eagerBlobEntry.toString();
	}

	@Override
	public EagerBlobEntry toUnescapedModel() {
		return new EagerBlobEntryWrapper(_eagerBlobEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _eagerBlobEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EagerBlobEntryWrapper)) {
			return false;
		}

		EagerBlobEntryWrapper eagerBlobEntryWrapper =
			(EagerBlobEntryWrapper)object;

		if (Objects.equals(
				_eagerBlobEntry, eagerBlobEntryWrapper._eagerBlobEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public EagerBlobEntry getWrappedModel() {
		return _eagerBlobEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _eagerBlobEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _eagerBlobEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_eagerBlobEntry.resetOriginalValues();
	}

	private final EagerBlobEntry _eagerBlobEntry;

}
// SB-Hash:1900138681