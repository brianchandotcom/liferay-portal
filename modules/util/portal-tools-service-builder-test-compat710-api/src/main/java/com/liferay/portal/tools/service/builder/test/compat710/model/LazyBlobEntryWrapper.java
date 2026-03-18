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
 * This class is a wrapper for {@link LazyBlobEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntry
 * @generated
 */
public class LazyBlobEntryWrapper
	implements LazyBlobEntry, ModelWrapper<LazyBlobEntry> {

	public LazyBlobEntryWrapper(LazyBlobEntry lazyBlobEntry) {
		_lazyBlobEntry = lazyBlobEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return LazyBlobEntry.class;
	}

	@Override
	public String getModelClassName() {
		return LazyBlobEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("lazyBlobEntryId", getLazyBlobEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("blob1", getBlob1());
		attributes.put("blob2", getBlob2());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long lazyBlobEntryId = (Long)attributes.get("lazyBlobEntryId");

		if (lazyBlobEntryId != null) {
			setLazyBlobEntryId(lazyBlobEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Blob blob1 = (Blob)attributes.get("blob1");

		if (blob1 != null) {
			setBlob1(blob1);
		}

		Blob blob2 = (Blob)attributes.get("blob2");

		if (blob2 != null) {
			setBlob2(blob2);
		}
	}

	@Override
	public Object clone() {
		return new LazyBlobEntryWrapper((LazyBlobEntry)_lazyBlobEntry.clone());
	}

	@Override
	public int compareTo(LazyBlobEntry lazyBlobEntry) {
		return _lazyBlobEntry.compareTo(lazyBlobEntry);
	}

	/**
	 * Returns the blob1 of this lazy blob entry.
	 *
	 * @return the blob1 of this lazy blob entry
	 */
	@Override
	public Blob getBlob1() {
		return _lazyBlobEntry.getBlob1();
	}

	/**
	 * Returns the blob2 of this lazy blob entry.
	 *
	 * @return the blob2 of this lazy blob entry
	 */
	@Override
	public Blob getBlob2() {
		return _lazyBlobEntry.getBlob2();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _lazyBlobEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this lazy blob entry.
	 *
	 * @return the group ID of this lazy blob entry
	 */
	@Override
	public long getGroupId() {
		return _lazyBlobEntry.getGroupId();
	}

	/**
	 * Returns the lazy blob entry ID of this lazy blob entry.
	 *
	 * @return the lazy blob entry ID of this lazy blob entry
	 */
	@Override
	public long getLazyBlobEntryId() {
		return _lazyBlobEntry.getLazyBlobEntryId();
	}

	/**
	 * Returns the primary key of this lazy blob entry.
	 *
	 * @return the primary key of this lazy blob entry
	 */
	@Override
	public long getPrimaryKey() {
		return _lazyBlobEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _lazyBlobEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the uuid of this lazy blob entry.
	 *
	 * @return the uuid of this lazy blob entry
	 */
	@Override
	public String getUuid() {
		return _lazyBlobEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _lazyBlobEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _lazyBlobEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _lazyBlobEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _lazyBlobEntry.isNew();
	}

	@Override
	public void persist() {
		_lazyBlobEntry.persist();
	}

	/**
	 * Sets the blob1 of this lazy blob entry.
	 *
	 * @param blob1 the blob1 of this lazy blob entry
	 */
	@Override
	public void setBlob1(Blob blob1) {
		_lazyBlobEntry.setBlob1(blob1);
	}

	/**
	 * Sets the blob2 of this lazy blob entry.
	 *
	 * @param blob2 the blob2 of this lazy blob entry
	 */
	@Override
	public void setBlob2(Blob blob2) {
		_lazyBlobEntry.setBlob2(blob2);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_lazyBlobEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_lazyBlobEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_lazyBlobEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_lazyBlobEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this lazy blob entry.
	 *
	 * @param groupId the group ID of this lazy blob entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_lazyBlobEntry.setGroupId(groupId);
	}

	/**
	 * Sets the lazy blob entry ID of this lazy blob entry.
	 *
	 * @param lazyBlobEntryId the lazy blob entry ID of this lazy blob entry
	 */
	@Override
	public void setLazyBlobEntryId(long lazyBlobEntryId) {
		_lazyBlobEntry.setLazyBlobEntryId(lazyBlobEntryId);
	}

	@Override
	public void setNew(boolean n) {
		_lazyBlobEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this lazy blob entry.
	 *
	 * @param primaryKey the primary key of this lazy blob entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_lazyBlobEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_lazyBlobEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the uuid of this lazy blob entry.
	 *
	 * @param uuid the uuid of this lazy blob entry
	 */
	@Override
	public void setUuid(String uuid) {
		_lazyBlobEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LazyBlobEntry>
		toCacheModel() {

		return _lazyBlobEntry.toCacheModel();
	}

	@Override
	public LazyBlobEntry toEscapedModel() {
		return new LazyBlobEntryWrapper(_lazyBlobEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _lazyBlobEntry.toString();
	}

	@Override
	public LazyBlobEntry toUnescapedModel() {
		return new LazyBlobEntryWrapper(_lazyBlobEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _lazyBlobEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LazyBlobEntryWrapper)) {
			return false;
		}

		LazyBlobEntryWrapper lazyBlobEntryWrapper =
			(LazyBlobEntryWrapper)object;

		if (Objects.equals(
				_lazyBlobEntry, lazyBlobEntryWrapper._lazyBlobEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public LazyBlobEntry getWrappedModel() {
		return _lazyBlobEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _lazyBlobEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _lazyBlobEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_lazyBlobEntry.resetOriginalValues();
	}

	private final LazyBlobEntry _lazyBlobEntry;

}
// LIFERAY-SERVICE-BUILDER-HASH:1400756083