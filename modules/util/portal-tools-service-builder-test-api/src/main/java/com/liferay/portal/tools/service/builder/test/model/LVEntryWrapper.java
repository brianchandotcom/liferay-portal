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
 * This class is a wrapper for {@link LVEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntry
 * @generated
 */
public class LVEntryWrapper
	extends BaseModelWrapper<LVEntry>
	implements LVEntry, ModelWrapper<LVEntry> {

	public LVEntryWrapper(LVEntry lvEntry) {
		super(lvEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("headId", getHeadId());
		attributes.put("defaultLanguageId", getDefaultLanguageId());
		attributes.put("lvEntryId", getLvEntryId());
		attributes.put("companyId", getCompanyId());
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

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this lv entry.
	 *
	 * @return the company ID of this lv entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
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
	 * Returns the default language ID of this lv entry.
	 *
	 * @return the default language ID of this lv entry
	 */
	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the group ID of this lv entry.
	 *
	 * @return the group ID of this lv entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the head ID of this lv entry.
	 *
	 * @return the head ID of this lv entry
	 */
	@Override
	public long getHeadId() {
		return model.getHeadId();
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
	 * Returns the lv entry ID of this lv entry.
	 *
	 * @return the lv entry ID of this lv entry
	 */
	@Override
	public long getLvEntryId() {
		return model.getLvEntryId();
	}

	/**
	 * Returns the mvcc version of this lv entry.
	 *
	 * @return the mvcc version of this lv entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this lv entry.
	 *
	 * @return the primary key of this lv entry
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
	 * Returns the unique group key of this lv entry.
	 *
	 * @return the unique group key of this lv entry
	 */
	@Override
	public String getUniqueGroupKey() {
		return model.getUniqueGroupKey();
	}

	/**
	 * Returns the uuid of this lv entry.
	 *
	 * @return the uuid of this lv entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a lv entry model instance should use the <code>LVEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this lv entry.
	 *
	 * @param companyId the company ID of this lv entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the default language ID of this lv entry.
	 *
	 * @param defaultLanguageId the default language ID of this lv entry
	 */
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		model.setDefaultLanguageId(defaultLanguageId);
	}

	/**
	 * Sets the group ID of this lv entry.
	 *
	 * @param groupId the group ID of this lv entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the head ID of this lv entry.
	 *
	 * @param headId the head ID of this lv entry
	 */
	@Override
	public void setHeadId(long headId) {
		model.setHeadId(headId);
	}

	/**
	 * Sets the lv entry ID of this lv entry.
	 *
	 * @param lvEntryId the lv entry ID of this lv entry
	 */
	@Override
	public void setLvEntryId(long lvEntryId) {
		model.setLvEntryId(lvEntryId);
	}

	/**
	 * Sets the mvcc version of this lv entry.
	 *
	 * @param mvccVersion the mvcc version of this lv entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this lv entry.
	 *
	 * @param primaryKey the primary key of this lv entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the unique group key of this lv entry.
	 *
	 * @param uniqueGroupKey the unique group key of this lv entry
	 */
	@Override
	public void setUniqueGroupKey(String uniqueGroupKey) {
		model.setUniqueGroupKey(uniqueGroupKey);
	}

	/**
	 * Sets the uuid of this lv entry.
	 *
	 * @param uuid the uuid of this lv entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public boolean isHead() {
		return model.isHead();
	}

	@Override
	public void populateVersionModel(LVEntryVersion lvEntryVersion) {
		model.populateVersionModel(lvEntryVersion);
	}

	@Override
	protected LVEntryWrapper wrap(LVEntry lvEntry) {
		return new LVEntryWrapper(lvEntry);
	}

}