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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link LocalizedEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntry
 * @generated
 */
public class LocalizedEntryWrapper
	implements LocalizedEntry, ModelWrapper<LocalizedEntry> {

	public LocalizedEntryWrapper(LocalizedEntry localizedEntry) {
		_localizedEntry = localizedEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return LocalizedEntry.class;
	}

	@Override
	public String getModelClassName() {
		return LocalizedEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("defaultLanguageId", getDefaultLanguageId());
		attributes.put("localizedEntryId", getLocalizedEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
		}

		Long localizedEntryId = (Long)attributes.get("localizedEntryId");

		if (localizedEntryId != null) {
			setLocalizedEntryId(localizedEntryId);
		}
	}

	@Override
	public Object clone() {
		return new LocalizedEntryWrapper(
			(LocalizedEntry)_localizedEntry.clone());
	}

	@Override
	public int compareTo(LocalizedEntry localizedEntry) {
		return _localizedEntry.compareTo(localizedEntry);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _localizedEntry.getAvailableLanguageIds();
	}

	@Override
	public String getContent() {
		return _localizedEntry.getContent();
	}

	@Override
	public String getContent(String languageId) {
		return _localizedEntry.getContent(languageId);
	}

	@Override
	public String getContent(String languageId, boolean useDefault) {
		return _localizedEntry.getContent(languageId, useDefault);
	}

	@Override
	public String getContentMapAsXML() {
		return _localizedEntry.getContentMapAsXML();
	}

	/**
	 * Returns the default language ID of this localized entry.
	 *
	 * @return the default language ID of this localized entry
	 */
	@Override
	public String getDefaultLanguageId() {
		return _localizedEntry.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _localizedEntry.getExpandoBridge();
	}

	@Override
	public Map<String, String> getLanguageIdToContentMap() {
		return _localizedEntry.getLanguageIdToContentMap();
	}

	@Override
	public Map<String, String> getLanguageIdToTitleMap() {
		return _localizedEntry.getLanguageIdToTitleMap();
	}

	/**
	 * Returns the localized entry ID of this localized entry.
	 *
	 * @return the localized entry ID of this localized entry
	 */
	@Override
	public long getLocalizedEntryId() {
		return _localizedEntry.getLocalizedEntryId();
	}

	/**
	 * Returns the primary key of this localized entry.
	 *
	 * @return the primary key of this localized entry
	 */
	@Override
	public long getPrimaryKey() {
		return _localizedEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _localizedEntry.getPrimaryKeyObj();
	}

	@Override
	public String getTitle() {
		return _localizedEntry.getTitle();
	}

	@Override
	public String getTitle(String languageId) {
		return _localizedEntry.getTitle(languageId);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return _localizedEntry.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleMapAsXML() {
		return _localizedEntry.getTitleMapAsXML();
	}

	@Override
	public int hashCode() {
		return _localizedEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _localizedEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _localizedEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _localizedEntry.isNew();
	}

	@Override
	public void persist() {
		_localizedEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_localizedEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the default language ID of this localized entry.
	 *
	 * @param defaultLanguageId the default language ID of this localized entry
	 */
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		_localizedEntry.setDefaultLanguageId(defaultLanguageId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_localizedEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_localizedEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_localizedEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the localized entry ID of this localized entry.
	 *
	 * @param localizedEntryId the localized entry ID of this localized entry
	 */
	@Override
	public void setLocalizedEntryId(long localizedEntryId) {
		_localizedEntry.setLocalizedEntryId(localizedEntryId);
	}

	@Override
	public void setNew(boolean n) {
		_localizedEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this localized entry.
	 *
	 * @param primaryKey the primary key of this localized entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_localizedEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_localizedEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LocalizedEntry>
		toCacheModel() {

		return _localizedEntry.toCacheModel();
	}

	@Override
	public LocalizedEntry toEscapedModel() {
		return new LocalizedEntryWrapper(_localizedEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _localizedEntry.toString();
	}

	@Override
	public LocalizedEntry toUnescapedModel() {
		return new LocalizedEntryWrapper(_localizedEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _localizedEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LocalizedEntryWrapper)) {
			return false;
		}

		LocalizedEntryWrapper localizedEntryWrapper =
			(LocalizedEntryWrapper)obj;

		if (Objects.equals(
				_localizedEntry, localizedEntryWrapper._localizedEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public LocalizedEntry getWrappedModel() {
		return _localizedEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _localizedEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _localizedEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_localizedEntry.resetOriginalValues();
	}

	private final LocalizedEntry _localizedEntry;

}