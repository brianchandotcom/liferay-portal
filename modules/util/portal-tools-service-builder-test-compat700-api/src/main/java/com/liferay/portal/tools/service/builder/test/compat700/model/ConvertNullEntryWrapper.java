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
 * This class is a wrapper for {@link ConvertNullEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ConvertNullEntry
 * @generated
 */
public class ConvertNullEntryWrapper
	implements ConvertNullEntry, ModelWrapper<ConvertNullEntry> {

	public ConvertNullEntryWrapper(ConvertNullEntry convertNullEntry) {
		_convertNullEntry = convertNullEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ConvertNullEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ConvertNullEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("convertNullEntryId", getConvertNullEntryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long convertNullEntryId = (Long)attributes.get("convertNullEntryId");

		if (convertNullEntryId != null) {
			setConvertNullEntryId(convertNullEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public Object clone() {
		return new ConvertNullEntryWrapper(
			(ConvertNullEntry)_convertNullEntry.clone());
	}

	@Override
	public int compareTo(ConvertNullEntry convertNullEntry) {
		return _convertNullEntry.compareTo(convertNullEntry);
	}

	/**
	 * Returns the convert null entry ID of this convert null entry.
	 *
	 * @return the convert null entry ID of this convert null entry
	 */
	@Override
	public long getConvertNullEntryId() {
		return _convertNullEntry.getConvertNullEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _convertNullEntry.getExpandoBridge();
	}

	/**
	 * Returns the name of this convert null entry.
	 *
	 * @return the name of this convert null entry
	 */
	@Override
	public String getName() {
		return _convertNullEntry.getName();
	}

	/**
	 * Returns the primary key of this convert null entry.
	 *
	 * @return the primary key of this convert null entry
	 */
	@Override
	public long getPrimaryKey() {
		return _convertNullEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _convertNullEntry.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _convertNullEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _convertNullEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _convertNullEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _convertNullEntry.isNew();
	}

	@Override
	public void persist() {
		_convertNullEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_convertNullEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the convert null entry ID of this convert null entry.
	 *
	 * @param convertNullEntryId the convert null entry ID of this convert null entry
	 */
	@Override
	public void setConvertNullEntryId(long convertNullEntryId) {
		_convertNullEntry.setConvertNullEntryId(convertNullEntryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_convertNullEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_convertNullEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_convertNullEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the name of this convert null entry.
	 *
	 * @param name the name of this convert null entry
	 */
	@Override
	public void setName(String name) {
		_convertNullEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_convertNullEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this convert null entry.
	 *
	 * @param primaryKey the primary key of this convert null entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_convertNullEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_convertNullEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ConvertNullEntry>
		toCacheModel() {

		return _convertNullEntry.toCacheModel();
	}

	@Override
	public ConvertNullEntry toEscapedModel() {
		return new ConvertNullEntryWrapper(_convertNullEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _convertNullEntry.toString();
	}

	@Override
	public ConvertNullEntry toUnescapedModel() {
		return new ConvertNullEntryWrapper(
			_convertNullEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _convertNullEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ConvertNullEntryWrapper)) {
			return false;
		}

		ConvertNullEntryWrapper convertNullEntryWrapper =
			(ConvertNullEntryWrapper)object;

		if (Objects.equals(
				_convertNullEntry, convertNullEntryWrapper._convertNullEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public ConvertNullEntry getWrappedModel() {
		return _convertNullEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _convertNullEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _convertNullEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_convertNullEntry.resetOriginalValues();
	}

	private final ConvertNullEntry _convertNullEntry;

}
// LIFERAY-SERVICE-BUILDER-HASH:1808058812