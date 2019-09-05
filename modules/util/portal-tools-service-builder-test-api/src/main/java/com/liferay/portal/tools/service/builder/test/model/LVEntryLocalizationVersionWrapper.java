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
 * This class is a wrapper for {@link LVEntryLocalizationVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationVersion
 * @generated
 */
public class LVEntryLocalizationVersionWrapper
	implements LVEntryLocalizationVersion,
			   ModelWrapper<LVEntryLocalizationVersion> {

	public LVEntryLocalizationVersionWrapper(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		_lvEntryLocalizationVersion = lvEntryLocalizationVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return LVEntryLocalizationVersion.class;
	}

	@Override
	public String getModelClassName() {
		return LVEntryLocalizationVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"lvEntryLocalizationVersionId", getLvEntryLocalizationVersionId());
		attributes.put("version", getVersion());
		attributes.put("lvEntryLocalizationId", getLvEntryLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("lvEntryId", getLvEntryId());
		attributes.put("languageId", getLanguageId());
		attributes.put("title", getTitle());
		attributes.put("content", getContent());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long lvEntryLocalizationVersionId = (Long)attributes.get(
			"lvEntryLocalizationVersionId");

		if (lvEntryLocalizationVersionId != null) {
			setLvEntryLocalizationVersionId(lvEntryLocalizationVersionId);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Long lvEntryLocalizationId = (Long)attributes.get(
			"lvEntryLocalizationId");

		if (lvEntryLocalizationId != null) {
			setLvEntryLocalizationId(lvEntryLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long lvEntryId = (Long)attributes.get("lvEntryId");

		if (lvEntryId != null) {
			setLvEntryId(lvEntryId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}
	}

	@Override
	public Object clone() {
		return new LVEntryLocalizationVersionWrapper(
			(LVEntryLocalizationVersion)_lvEntryLocalizationVersion.clone());
	}

	@Override
	public int compareTo(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		return _lvEntryLocalizationVersion.compareTo(
			lvEntryLocalizationVersion);
	}

	/**
	 * Returns the company ID of this lv entry localization version.
	 *
	 * @return the company ID of this lv entry localization version
	 */
	@Override
	public long getCompanyId() {
		return _lvEntryLocalizationVersion.getCompanyId();
	}

	/**
	 * Returns the content of this lv entry localization version.
	 *
	 * @return the content of this lv entry localization version
	 */
	@Override
	public String getContent() {
		return _lvEntryLocalizationVersion.getContent();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _lvEntryLocalizationVersion.getExpandoBridge();
	}

	/**
	 * Returns the language ID of this lv entry localization version.
	 *
	 * @return the language ID of this lv entry localization version
	 */
	@Override
	public String getLanguageId() {
		return _lvEntryLocalizationVersion.getLanguageId();
	}

	/**
	 * Returns the lv entry ID of this lv entry localization version.
	 *
	 * @return the lv entry ID of this lv entry localization version
	 */
	@Override
	public long getLvEntryId() {
		return _lvEntryLocalizationVersion.getLvEntryId();
	}

	/**
	 * Returns the lv entry localization ID of this lv entry localization version.
	 *
	 * @return the lv entry localization ID of this lv entry localization version
	 */
	@Override
	public long getLvEntryLocalizationId() {
		return _lvEntryLocalizationVersion.getLvEntryLocalizationId();
	}

	/**
	 * Returns the lv entry localization version ID of this lv entry localization version.
	 *
	 * @return the lv entry localization version ID of this lv entry localization version
	 */
	@Override
	public long getLvEntryLocalizationVersionId() {
		return _lvEntryLocalizationVersion.getLvEntryLocalizationVersionId();
	}

	/**
	 * Returns the primary key of this lv entry localization version.
	 *
	 * @return the primary key of this lv entry localization version
	 */
	@Override
	public long getPrimaryKey() {
		return _lvEntryLocalizationVersion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _lvEntryLocalizationVersion.getPrimaryKeyObj();
	}

	/**
	 * Returns the title of this lv entry localization version.
	 *
	 * @return the title of this lv entry localization version
	 */
	@Override
	public String getTitle() {
		return _lvEntryLocalizationVersion.getTitle();
	}

	/**
	 * Returns the version of this lv entry localization version.
	 *
	 * @return the version of this lv entry localization version
	 */
	@Override
	public int getVersion() {
		return _lvEntryLocalizationVersion.getVersion();
	}

	@Override
	public int hashCode() {
		return _lvEntryLocalizationVersion.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _lvEntryLocalizationVersion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _lvEntryLocalizationVersion.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _lvEntryLocalizationVersion.isNew();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_lvEntryLocalizationVersion.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this lv entry localization version.
	 *
	 * @param companyId the company ID of this lv entry localization version
	 */
	@Override
	public void setCompanyId(long companyId) {
		_lvEntryLocalizationVersion.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this lv entry localization version.
	 *
	 * @param content the content of this lv entry localization version
	 */
	@Override
	public void setContent(String content) {
		_lvEntryLocalizationVersion.setContent(content);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_lvEntryLocalizationVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_lvEntryLocalizationVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_lvEntryLocalizationVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the language ID of this lv entry localization version.
	 *
	 * @param languageId the language ID of this lv entry localization version
	 */
	@Override
	public void setLanguageId(String languageId) {
		_lvEntryLocalizationVersion.setLanguageId(languageId);
	}

	/**
	 * Sets the lv entry ID of this lv entry localization version.
	 *
	 * @param lvEntryId the lv entry ID of this lv entry localization version
	 */
	@Override
	public void setLvEntryId(long lvEntryId) {
		_lvEntryLocalizationVersion.setLvEntryId(lvEntryId);
	}

	/**
	 * Sets the lv entry localization ID of this lv entry localization version.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID of this lv entry localization version
	 */
	@Override
	public void setLvEntryLocalizationId(long lvEntryLocalizationId) {
		_lvEntryLocalizationVersion.setLvEntryLocalizationId(
			lvEntryLocalizationId);
	}

	/**
	 * Sets the lv entry localization version ID of this lv entry localization version.
	 *
	 * @param lvEntryLocalizationVersionId the lv entry localization version ID of this lv entry localization version
	 */
	@Override
	public void setLvEntryLocalizationVersionId(
		long lvEntryLocalizationVersionId) {

		_lvEntryLocalizationVersion.setLvEntryLocalizationVersionId(
			lvEntryLocalizationVersionId);
	}

	@Override
	public void setNew(boolean n) {
		_lvEntryLocalizationVersion.setNew(n);
	}

	/**
	 * Sets the primary key of this lv entry localization version.
	 *
	 * @param primaryKey the primary key of this lv entry localization version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_lvEntryLocalizationVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_lvEntryLocalizationVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the title of this lv entry localization version.
	 *
	 * @param title the title of this lv entry localization version
	 */
	@Override
	public void setTitle(String title) {
		_lvEntryLocalizationVersion.setTitle(title);
	}

	/**
	 * Sets the version of this lv entry localization version.
	 *
	 * @param version the version of this lv entry localization version
	 */
	@Override
	public void setVersion(int version) {
		_lvEntryLocalizationVersion.setVersion(version);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<LVEntryLocalizationVersion> toCacheModel() {

		return _lvEntryLocalizationVersion.toCacheModel();
	}

	@Override
	public LVEntryLocalizationVersion toEscapedModel() {
		return new LVEntryLocalizationVersionWrapper(
			_lvEntryLocalizationVersion.toEscapedModel());
	}

	@Override
	public String toString() {
		return _lvEntryLocalizationVersion.toString();
	}

	@Override
	public LVEntryLocalizationVersion toUnescapedModel() {
		return new LVEntryLocalizationVersionWrapper(
			_lvEntryLocalizationVersion.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _lvEntryLocalizationVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LVEntryLocalizationVersionWrapper)) {
			return false;
		}

		LVEntryLocalizationVersionWrapper lvEntryLocalizationVersionWrapper =
			(LVEntryLocalizationVersionWrapper)obj;

		if (Objects.equals(
				_lvEntryLocalizationVersion,
				lvEntryLocalizationVersionWrapper.
					_lvEntryLocalizationVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public long getVersionedModelId() {
		return _lvEntryLocalizationVersion.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		_lvEntryLocalizationVersion.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(
		LVEntryLocalization lvEntryLocalization) {

		_lvEntryLocalizationVersion.populateVersionedModel(lvEntryLocalization);
	}

	@Override
	public LVEntryLocalization toVersionedModel() {
		return _lvEntryLocalizationVersion.toVersionedModel();
	}

	@Override
	public LVEntryLocalizationVersion getWrappedModel() {
		return _lvEntryLocalizationVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _lvEntryLocalizationVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _lvEntryLocalizationVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_lvEntryLocalizationVersion.resetOriginalValues();
	}

	private final LVEntryLocalizationVersion _lvEntryLocalizationVersion;

}