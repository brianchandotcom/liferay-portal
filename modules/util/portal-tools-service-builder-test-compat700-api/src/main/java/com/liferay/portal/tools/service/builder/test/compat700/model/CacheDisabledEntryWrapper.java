/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CacheDisabledEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheDisabledEntry
 * @generated
 */
public class CacheDisabledEntryWrapper
	implements CacheDisabledEntry, ModelWrapper<CacheDisabledEntry> {

	public CacheDisabledEntryWrapper(CacheDisabledEntry cacheDisabledEntry) {
		_cacheDisabledEntry = cacheDisabledEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CacheDisabledEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CacheDisabledEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cacheDisabledEntryId", getCacheDisabledEntryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cacheDisabledEntryId = (Long)attributes.get(
			"cacheDisabledEntryId");

		if (cacheDisabledEntryId != null) {
			setCacheDisabledEntryId(cacheDisabledEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public Object clone() {
		return new CacheDisabledEntryWrapper(
			(CacheDisabledEntry)_cacheDisabledEntry.clone());
	}

	@Override
	public int compareTo(CacheDisabledEntry cacheDisabledEntry) {
		return _cacheDisabledEntry.compareTo(cacheDisabledEntry);
	}

	/**
	 * Returns the cache disabled entry ID of this cache disabled entry.
	 *
	 * @return the cache disabled entry ID of this cache disabled entry
	 */
	@Override
	public long getCacheDisabledEntryId() {
		return _cacheDisabledEntry.getCacheDisabledEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cacheDisabledEntry.getExpandoBridge();
	}

	/**
	 * Returns the name of this cache disabled entry.
	 *
	 * @return the name of this cache disabled entry
	 */
	@Override
	public String getName() {
		return _cacheDisabledEntry.getName();
	}

	/**
	 * Returns the primary key of this cache disabled entry.
	 *
	 * @return the primary key of this cache disabled entry
	 */
	@Override
	public long getPrimaryKey() {
		return _cacheDisabledEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cacheDisabledEntry.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _cacheDisabledEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cacheDisabledEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cacheDisabledEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cacheDisabledEntry.isNew();
	}

	@Override
	public void persist() {
		_cacheDisabledEntry.persist();
	}

	/**
	 * Sets the cache disabled entry ID of this cache disabled entry.
	 *
	 * @param cacheDisabledEntryId the cache disabled entry ID of this cache disabled entry
	 */
	@Override
	public void setCacheDisabledEntryId(long cacheDisabledEntryId) {
		_cacheDisabledEntry.setCacheDisabledEntryId(cacheDisabledEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cacheDisabledEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_cacheDisabledEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cacheDisabledEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cacheDisabledEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the name of this cache disabled entry.
	 *
	 * @param name the name of this cache disabled entry
	 */
	@Override
	public void setName(String name) {
		_cacheDisabledEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_cacheDisabledEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this cache disabled entry.
	 *
	 * @param primaryKey the primary key of this cache disabled entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cacheDisabledEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cacheDisabledEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CacheDisabledEntry>
		toCacheModel() {

		return _cacheDisabledEntry.toCacheModel();
	}

	@Override
	public CacheDisabledEntry toEscapedModel() {
		return new CacheDisabledEntryWrapper(
			_cacheDisabledEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cacheDisabledEntry.toString();
	}

	@Override
	public CacheDisabledEntry toUnescapedModel() {
		return new CacheDisabledEntryWrapper(
			_cacheDisabledEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cacheDisabledEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CacheDisabledEntryWrapper)) {
			return false;
		}

		CacheDisabledEntryWrapper cacheDisabledEntryWrapper =
			(CacheDisabledEntryWrapper)object;

		if (Objects.equals(
				_cacheDisabledEntry,
				cacheDisabledEntryWrapper._cacheDisabledEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public CacheDisabledEntry getWrappedModel() {
		return _cacheDisabledEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cacheDisabledEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cacheDisabledEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cacheDisabledEntry.resetOriginalValues();
	}

	private final CacheDisabledEntry _cacheDisabledEntry;

}
// SB-Hash:-2127936882