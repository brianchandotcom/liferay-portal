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
 * This class is a wrapper for {@link LVEntryLocalizationVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationVersion
 * @generated
 */
public class LVEntryLocalizationVersionWrapper
	extends BaseModelWrapper<LVEntryLocalizationVersion>
	implements LVEntryLocalizationVersion,
			   ModelWrapper<LVEntryLocalizationVersion> {

	public LVEntryLocalizationVersionWrapper(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		super(lvEntryLocalizationVersion);
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

	/**
	 * Returns the company ID of this lv entry localization version.
	 *
	 * @return the company ID of this lv entry localization version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this lv entry localization version.
	 *
	 * @return the content of this lv entry localization version
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the language ID of this lv entry localization version.
	 *
	 * @return the language ID of this lv entry localization version
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the lv entry ID of this lv entry localization version.
	 *
	 * @return the lv entry ID of this lv entry localization version
	 */
	@Override
	public long getLvEntryId() {
		return model.getLvEntryId();
	}

	/**
	 * Returns the lv entry localization ID of this lv entry localization version.
	 *
	 * @return the lv entry localization ID of this lv entry localization version
	 */
	@Override
	public long getLvEntryLocalizationId() {
		return model.getLvEntryLocalizationId();
	}

	/**
	 * Returns the lv entry localization version ID of this lv entry localization version.
	 *
	 * @return the lv entry localization version ID of this lv entry localization version
	 */
	@Override
	public long getLvEntryLocalizationVersionId() {
		return model.getLvEntryLocalizationVersionId();
	}

	/**
	 * Returns the primary key of this lv entry localization version.
	 *
	 * @return the primary key of this lv entry localization version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the title of this lv entry localization version.
	 *
	 * @return the title of this lv entry localization version
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the version of this lv entry localization version.
	 *
	 * @return the version of this lv entry localization version
	 */
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	 * Sets the company ID of this lv entry localization version.
	 *
	 * @param companyId the company ID of this lv entry localization version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this lv entry localization version.
	 *
	 * @param content the content of this lv entry localization version
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the language ID of this lv entry localization version.
	 *
	 * @param languageId the language ID of this lv entry localization version
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the lv entry ID of this lv entry localization version.
	 *
	 * @param lvEntryId the lv entry ID of this lv entry localization version
	 */
	@Override
	public void setLvEntryId(long lvEntryId) {
		model.setLvEntryId(lvEntryId);
	}

	/**
	 * Sets the lv entry localization ID of this lv entry localization version.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID of this lv entry localization version
	 */
	@Override
	public void setLvEntryLocalizationId(long lvEntryLocalizationId) {
		model.setLvEntryLocalizationId(lvEntryLocalizationId);
	}

	/**
	 * Sets the lv entry localization version ID of this lv entry localization version.
	 *
	 * @param lvEntryLocalizationVersionId the lv entry localization version ID of this lv entry localization version
	 */
	@Override
	public void setLvEntryLocalizationVersionId(
		long lvEntryLocalizationVersionId) {

		model.setLvEntryLocalizationVersionId(lvEntryLocalizationVersionId);
	}

	/**
	 * Sets the primary key of this lv entry localization version.
	 *
	 * @param primaryKey the primary key of this lv entry localization version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the title of this lv entry localization version.
	 *
	 * @param title the title of this lv entry localization version
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the version of this lv entry localization version.
	 *
	 * @param version the version of this lv entry localization version
	 */
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	@Override
	public long getVersionedModelId() {
		return model.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		model.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(
		LVEntryLocalization lvEntryLocalization) {

		model.populateVersionedModel(lvEntryLocalization);
	}

	@Override
	public LVEntryLocalization toVersionedModel() {
		return model.toVersionedModel();
	}

	@Override
	protected LVEntryLocalizationVersionWrapper wrap(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		return new LVEntryLocalizationVersionWrapper(
			lvEntryLocalizationVersion);
	}

}