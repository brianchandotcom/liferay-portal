/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link MappingEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MappingEntry
 * @generated
 */
public class MappingEntryWrapper
	implements MappingEntry, ModelWrapper<MappingEntry> {

	public MappingEntryWrapper(MappingEntry mappingEntry) {
		_mappingEntry = mappingEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return MappingEntry.class;
	}

	@Override
	public String getModelClassName() {
		return MappingEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mappingEntryId", getMappingEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mappingEntryId = (Long)attributes.get("mappingEntryId");

		if (mappingEntryId != null) {
			setMappingEntryId(mappingEntryId);
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
		return new MappingEntryWrapper((MappingEntry)_mappingEntry.clone());
	}

	@Override
	public int compareTo(MappingEntry mappingEntry) {
		return _mappingEntry.compareTo(mappingEntry);
	}

	/**
	 * Returns the company ID of this mapping entry.
	 *
	 * @return the company ID of this mapping entry
	 */
	@Override
	public long getCompanyId() {
		return _mappingEntry.getCompanyId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _mappingEntry.getExpandoBridge();
	}

	/**
	 * Returns the mapping entry ID of this mapping entry.
	 *
	 * @return the mapping entry ID of this mapping entry
	 */
	@Override
	public long getMappingEntryId() {
		return _mappingEntry.getMappingEntryId();
	}

	/**
	 * Returns the name of this mapping entry.
	 *
	 * @return the name of this mapping entry
	 */
	@Override
	public String getName() {
		return _mappingEntry.getName();
	}

	/**
	 * Returns the primary key of this mapping entry.
	 *
	 * @return the primary key of this mapping entry
	 */
	@Override
	public long getPrimaryKey() {
		return _mappingEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _mappingEntry.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _mappingEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _mappingEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _mappingEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _mappingEntry.isNew();
	}

	@Override
	public void persist() {
		_mappingEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mappingEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this mapping entry.
	 *
	 * @param companyId the company ID of this mapping entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_mappingEntry.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_mappingEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_mappingEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_mappingEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the mapping entry ID of this mapping entry.
	 *
	 * @param mappingEntryId the mapping entry ID of this mapping entry
	 */
	@Override
	public void setMappingEntryId(long mappingEntryId) {
		_mappingEntry.setMappingEntryId(mappingEntryId);
	}

	/**
	 * Sets the name of this mapping entry.
	 *
	 * @param name the name of this mapping entry
	 */
	@Override
	public void setName(String name) {
		_mappingEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_mappingEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this mapping entry.
	 *
	 * @param primaryKey the primary key of this mapping entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mappingEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_mappingEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<MappingEntry>
		toCacheModel() {

		return _mappingEntry.toCacheModel();
	}

	@Override
	public MappingEntry toEscapedModel() {
		return new MappingEntryWrapper(_mappingEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _mappingEntry.toString();
	}

	@Override
	public MappingEntry toUnescapedModel() {
		return new MappingEntryWrapper(_mappingEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _mappingEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MappingEntryWrapper)) {
			return false;
		}

		MappingEntryWrapper mappingEntryWrapper = (MappingEntryWrapper)object;

		if (Objects.equals(_mappingEntry, mappingEntryWrapper._mappingEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public MappingEntry getWrappedModel() {
		return _mappingEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _mappingEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _mappingEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_mappingEntry.resetOriginalValues();
	}

	private final MappingEntry _mappingEntry;

}
// LIFERAY-SERVICE-BUILDER-HASH:-38218913