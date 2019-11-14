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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<LocalizedEntry>
	implements LocalizedEntry, ModelWrapper<LocalizedEntry> {

	public LocalizedEntryWrapper(LocalizedEntry localizedEntry) {
		super(localizedEntry);
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
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	@Override
	public String getContent() {
		return model.getContent();
	}

	@Override
	public String getContent(String languageId) {
		return model.getContent(languageId);
	}

	@Override
	public String getContent(String languageId, boolean useDefault) {
		return model.getContent(languageId, useDefault);
	}

	@Override
	public String getContentMapAsXML() {
		return model.getContentMapAsXML();
	}

	/**
	 * Returns the default language ID of this localized entry.
	 *
	 * @return the default language ID of this localized entry
	 */
	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	@Override
	public Map<String, String> getLanguageIdToContentMap() {
		return model.getLanguageIdToContentMap();
	}

	@Override
	public Map<String, String> getLanguageIdToTitleMap() {
		return model.getLanguageIdToTitleMap();
	}

	/**
	 * Returns the localized entry ID of this localized entry.
	 *
	 * @return the localized entry ID of this localized entry
	 */
	@Override
	public long getLocalizedEntryId() {
		return model.getLocalizedEntryId();
	}

	/**
	 * Returns the primary key of this localized entry.
	 *
	 * @return the primary key of this localized entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public String getTitle() {
		return model.getTitle();
	}

	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return model.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleMapAsXML() {
		return model.getTitleMapAsXML();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a localized entry model instance should use the <code>LocalizedEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the default language ID of this localized entry.
	 *
	 * @param defaultLanguageId the default language ID of this localized entry
	 */
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		model.setDefaultLanguageId(defaultLanguageId);
	}

	/**
	 * Sets the localized entry ID of this localized entry.
	 *
	 * @param localizedEntryId the localized entry ID of this localized entry
	 */
	@Override
	public void setLocalizedEntryId(long localizedEntryId) {
		model.setLocalizedEntryId(localizedEntryId);
	}

	/**
	 * Sets the primary key of this localized entry.
	 *
	 * @param primaryKey the primary key of this localized entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected LocalizedEntryWrapper wrap(LocalizedEntry localizedEntry) {
		return new LocalizedEntryWrapper(localizedEntry);
	}

}