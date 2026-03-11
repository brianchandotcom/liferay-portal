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
 * This class is a wrapper for {@link MvccEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MvccEntry
 * @generated
 */
public class MvccEntryWrapper implements ModelWrapper<MvccEntry>, MvccEntry {

	public MvccEntryWrapper(MvccEntry mvccEntry) {
		_mvccEntry = mvccEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return MvccEntry.class;
	}

	@Override
	public String getModelClassName() {
		return MvccEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("mvccEntryId", getMvccEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long mvccEntryId = (Long)attributes.get("mvccEntryId");

		if (mvccEntryId != null) {
			setMvccEntryId(mvccEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public Object clone() {
		return new MvccEntryWrapper((MvccEntry)_mvccEntry.clone());
	}

	@Override
	public int compareTo(MvccEntry mvccEntry) {
		return _mvccEntry.compareTo(mvccEntry);
	}

	/**
	 * Returns the company ID of this mvcc entry.
	 *
	 * @return the company ID of this mvcc entry
	 */
	@Override
	public long getCompanyId() {
		return _mvccEntry.getCompanyId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _mvccEntry.getExpandoBridge();
	}

	/**
	 * Returns the mvcc entry ID of this mvcc entry.
	 *
	 * @return the mvcc entry ID of this mvcc entry
	 */
	@Override
	public long getMvccEntryId() {
		return _mvccEntry.getMvccEntryId();
	}

	/**
	 * Returns the mvcc version of this mvcc entry.
	 *
	 * @return the mvcc version of this mvcc entry
	 */
	@Override
	public long getMvccVersion() {
		return _mvccEntry.getMvccVersion();
	}

	/**
	 * Returns the name of this mvcc entry.
	 *
	 * @return the name of this mvcc entry
	 */
	@Override
	public String getName() {
		return _mvccEntry.getName();
	}

	/**
	 * Returns the primary key of this mvcc entry.
	 *
	 * @return the primary key of this mvcc entry
	 */
	@Override
	public long getPrimaryKey() {
		return _mvccEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _mvccEntry.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _mvccEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _mvccEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _mvccEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _mvccEntry.isNew();
	}

	@Override
	public void persist() {
		_mvccEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mvccEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this mvcc entry.
	 *
	 * @param companyId the company ID of this mvcc entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_mvccEntry.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_mvccEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_mvccEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_mvccEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the mvcc entry ID of this mvcc entry.
	 *
	 * @param mvccEntryId the mvcc entry ID of this mvcc entry
	 */
	@Override
	public void setMvccEntryId(long mvccEntryId) {
		_mvccEntry.setMvccEntryId(mvccEntryId);
	}

	/**
	 * Sets the mvcc version of this mvcc entry.
	 *
	 * @param mvccVersion the mvcc version of this mvcc entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccEntry.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this mvcc entry.
	 *
	 * @param name the name of this mvcc entry
	 */
	@Override
	public void setName(String name) {
		_mvccEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_mvccEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this mvcc entry.
	 *
	 * @param primaryKey the primary key of this mvcc entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mvccEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_mvccEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<MvccEntry>
		toCacheModel() {

		return _mvccEntry.toCacheModel();
	}

	@Override
	public MvccEntry toEscapedModel() {
		return new MvccEntryWrapper(_mvccEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _mvccEntry.toString();
	}

	@Override
	public MvccEntry toUnescapedModel() {
		return new MvccEntryWrapper(_mvccEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _mvccEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MvccEntryWrapper)) {
			return false;
		}

		MvccEntryWrapper mvccEntryWrapper = (MvccEntryWrapper)object;

		if (Objects.equals(_mvccEntry, mvccEntryWrapper._mvccEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public MvccEntry getWrappedModel() {
		return _mvccEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _mvccEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _mvccEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_mvccEntry.resetOriginalValues();
	}

	private final MvccEntry _mvccEntry;

}