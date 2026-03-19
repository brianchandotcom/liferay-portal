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
 * This class is a wrapper for {@link WhereClauseEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WhereClauseEntry
 * @generated
 */
public class WhereClauseEntryWrapper
	implements ModelWrapper<WhereClauseEntry>, WhereClauseEntry {

	public WhereClauseEntryWrapper(WhereClauseEntry whereClauseEntry) {
		_whereClauseEntry = whereClauseEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return WhereClauseEntry.class;
	}

	@Override
	public String getModelClassName() {
		return WhereClauseEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("whereClauseEntryId", getWhereClauseEntryId());
		attributes.put("name", getName());
		attributes.put("nickname", getNickname());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long whereClauseEntryId = (Long)attributes.get("whereClauseEntryId");

		if (whereClauseEntryId != null) {
			setWhereClauseEntryId(whereClauseEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String nickname = (String)attributes.get("nickname");

		if (nickname != null) {
			setNickname(nickname);
		}
	}

	@Override
	public Object clone() {
		return new WhereClauseEntryWrapper(
			(WhereClauseEntry)_whereClauseEntry.clone());
	}

	@Override
	public int compareTo(WhereClauseEntry whereClauseEntry) {
		return _whereClauseEntry.compareTo(whereClauseEntry);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _whereClauseEntry.getExpandoBridge();
	}

	/**
	 * Returns the name of this where clause entry.
	 *
	 * @return the name of this where clause entry
	 */
	@Override
	public String getName() {
		return _whereClauseEntry.getName();
	}

	/**
	 * Returns the nickname of this where clause entry.
	 *
	 * @return the nickname of this where clause entry
	 */
	@Override
	public String getNickname() {
		return _whereClauseEntry.getNickname();
	}

	/**
	 * Returns the primary key of this where clause entry.
	 *
	 * @return the primary key of this where clause entry
	 */
	@Override
	public long getPrimaryKey() {
		return _whereClauseEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _whereClauseEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the where clause entry ID of this where clause entry.
	 *
	 * @return the where clause entry ID of this where clause entry
	 */
	@Override
	public long getWhereClauseEntryId() {
		return _whereClauseEntry.getWhereClauseEntryId();
	}

	@Override
	public int hashCode() {
		return _whereClauseEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _whereClauseEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _whereClauseEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _whereClauseEntry.isNew();
	}

	@Override
	public void persist() {
		_whereClauseEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_whereClauseEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_whereClauseEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_whereClauseEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_whereClauseEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the name of this where clause entry.
	 *
	 * @param name the name of this where clause entry
	 */
	@Override
	public void setName(String name) {
		_whereClauseEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_whereClauseEntry.setNew(n);
	}

	/**
	 * Sets the nickname of this where clause entry.
	 *
	 * @param nickname the nickname of this where clause entry
	 */
	@Override
	public void setNickname(String nickname) {
		_whereClauseEntry.setNickname(nickname);
	}

	/**
	 * Sets the primary key of this where clause entry.
	 *
	 * @param primaryKey the primary key of this where clause entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_whereClauseEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_whereClauseEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the where clause entry ID of this where clause entry.
	 *
	 * @param whereClauseEntryId the where clause entry ID of this where clause entry
	 */
	@Override
	public void setWhereClauseEntryId(long whereClauseEntryId) {
		_whereClauseEntry.setWhereClauseEntryId(whereClauseEntryId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<WhereClauseEntry>
		toCacheModel() {

		return _whereClauseEntry.toCacheModel();
	}

	@Override
	public WhereClauseEntry toEscapedModel() {
		return new WhereClauseEntryWrapper(_whereClauseEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _whereClauseEntry.toString();
	}

	@Override
	public WhereClauseEntry toUnescapedModel() {
		return new WhereClauseEntryWrapper(
			_whereClauseEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _whereClauseEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WhereClauseEntryWrapper)) {
			return false;
		}

		WhereClauseEntryWrapper whereClauseEntryWrapper =
			(WhereClauseEntryWrapper)object;

		if (Objects.equals(
				_whereClauseEntry, whereClauseEntryWrapper._whereClauseEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public WhereClauseEntry getWrappedModel() {
		return _whereClauseEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _whereClauseEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _whereClauseEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_whereClauseEntry.resetOriginalValues();
	}

	private final WhereClauseEntry _whereClauseEntry;

}
// LIFERAY-SERVICE-BUILDER-HASH:378418801