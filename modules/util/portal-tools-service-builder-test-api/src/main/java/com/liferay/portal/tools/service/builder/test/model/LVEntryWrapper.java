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

package com.liferay.portal.tools.service.builder.test.model;

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
 * This class is a wrapper for {@link LVEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntry
 * @generated
 */
@ProviderType
public class LVEntryWrapper implements LVEntry, ModelWrapper<LVEntry> {
	public LVEntryWrapper(LVEntry lvEntry) {
		_lvEntry = lvEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return LVEntry.class;
	}

	@Override
	public String getModelClassName() {
		return LVEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("headId", getHeadId());
		attributes.put("defaultLanguageId", getDefaultLanguageId());
		attributes.put("lvEntryId", getLvEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("uniqueGroupKey", getUniqueGroupKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long headId = (Long)attributes.get("headId");

		if (headId != null) {
			setHeadId(headId);
		}

		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
		}

		Long lvEntryId = (Long)attributes.get("lvEntryId");

		if (lvEntryId != null) {
			setLvEntryId(lvEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String uniqueGroupKey = (String)attributes.get("uniqueGroupKey");

		if (uniqueGroupKey != null) {
			setUniqueGroupKey(uniqueGroupKey);
		}
	}

	@Override
	public Object clone() {
		return new LVEntryWrapper((LVEntry)_lvEntry.clone());
	}

	@Override
	public int compareTo(LVEntry lvEntry) {
		return _lvEntry.compareTo(lvEntry);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _lvEntry.getAvailableLanguageIds();
	}

	@Override
	public String getContent() {
		return _lvEntry.getContent();
	}

	@Override
	public String getContent(String languageId) {
		return _lvEntry.getContent(languageId);
	}

	@Override
	public String getContent(String languageId, boolean useDefault) {
		return _lvEntry.getContent(languageId, useDefault);
	}

	@Override
	public String getContentMapAsXML() {
		return _lvEntry.getContentMapAsXML();
	}

	/**
	* Returns the default language ID of this lv entry.
	*
	* @return the default language ID of this lv entry
	*/
	@Override
	public String getDefaultLanguageId() {
		return _lvEntry.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _lvEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this lv entry.
	*
	* @return the group ID of this lv entry
	*/
	@Override
	public long getGroupId() {
		return _lvEntry.getGroupId();
	}

	/**
	* Returns the head ID of this lv entry.
	*
	* @return the head ID of this lv entry
	*/
	@Override
	public long getHeadId() {
		return _lvEntry.getHeadId();
	}

	@Override
	public Map<String, String> getLanguageIdToContentMap() {
		return _lvEntry.getLanguageIdToContentMap();
	}

	@Override
	public Map<String, String> getLanguageIdToTitleMap() {
		return _lvEntry.getLanguageIdToTitleMap();
	}

	/**
	* Returns the lv entry ID of this lv entry.
	*
	* @return the lv entry ID of this lv entry
	*/
	@Override
	public long getLvEntryId() {
		return _lvEntry.getLvEntryId();
	}

	/**
	* Returns the mvcc version of this lv entry.
	*
	* @return the mvcc version of this lv entry
	*/
	@Override
	public long getMvccVersion() {
		return _lvEntry.getMvccVersion();
	}

	/**
	* Returns the primary key of this lv entry.
	*
	* @return the primary key of this lv entry
	*/
	@Override
	public long getPrimaryKey() {
		return _lvEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _lvEntry.getPrimaryKeyObj();
	}

	@Override
	public String getTitle() {
		return _lvEntry.getTitle();
	}

	@Override
	public String getTitle(String languageId) {
		return _lvEntry.getTitle(languageId);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return _lvEntry.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleMapAsXML() {
		return _lvEntry.getTitleMapAsXML();
	}

	/**
	* Returns the unique group key of this lv entry.
	*
	* @return the unique group key of this lv entry
	*/
	@Override
	public String getUniqueGroupKey() {
		return _lvEntry.getUniqueGroupKey();
	}

	/**
	* Returns the uuid of this lv entry.
	*
	* @return the uuid of this lv entry
	*/
	@Override
	public String getUuid() {
		return _lvEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _lvEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _lvEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _lvEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _lvEntry.isNew();
	}

	@Override
	public void persist() {
		_lvEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_lvEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the default language ID of this lv entry.
	*
	* @param defaultLanguageId the default language ID of this lv entry
	*/
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		_lvEntry.setDefaultLanguageId(defaultLanguageId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_lvEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_lvEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_lvEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this lv entry.
	*
	* @param groupId the group ID of this lv entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_lvEntry.setGroupId(groupId);
	}

	/**
	* Sets the head ID of this lv entry.
	*
	* @param headId the head ID of this lv entry
	*/
	@Override
	public void setHeadId(long headId) {
		_lvEntry.setHeadId(headId);
	}

	/**
	* Sets the lv entry ID of this lv entry.
	*
	* @param lvEntryId the lv entry ID of this lv entry
	*/
	@Override
	public void setLvEntryId(long lvEntryId) {
		_lvEntry.setLvEntryId(lvEntryId);
	}

	/**
	* Sets the mvcc version of this lv entry.
	*
	* @param mvccVersion the mvcc version of this lv entry
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_lvEntry.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_lvEntry.setNew(n);
	}

	/**
	* Sets the primary key of this lv entry.
	*
	* @param primaryKey the primary key of this lv entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_lvEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_lvEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the unique group key of this lv entry.
	*
	* @param uniqueGroupKey the unique group key of this lv entry
	*/
	@Override
	public void setUniqueGroupKey(String uniqueGroupKey) {
		_lvEntry.setUniqueGroupKey(uniqueGroupKey);
	}

	/**
	* Sets the uuid of this lv entry.
	*
	* @param uuid the uuid of this lv entry
	*/
	@Override
	public void setUuid(String uuid) {
		_lvEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LVEntry> toCacheModel() {
		return _lvEntry.toCacheModel();
	}

	@Override
	public LVEntry toEscapedModel() {
		return new LVEntryWrapper(_lvEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _lvEntry.toString();
	}

	@Override
	public LVEntry toUnescapedModel() {
		return new LVEntryWrapper(_lvEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _lvEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LVEntryWrapper)) {
			return false;
		}

		LVEntryWrapper lvEntryWrapper = (LVEntryWrapper)obj;

		if (Objects.equals(_lvEntry, lvEntryWrapper._lvEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isHead() {
		return _lvEntry.isHead();
	}

	@Override
	public void populateVersionModel(LVEntryVersion lvEntryVersion) {
		_lvEntry.populateVersionModel(lvEntryVersion);
	}

	@Override
	public LVEntry getWrappedModel() {
		return _lvEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _lvEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _lvEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_lvEntry.resetOriginalValues();
	}

	private final LVEntry _lvEntry;
}