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
 * This class is a wrapper for {@link LVEntryLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalization
 * @generated
 */
public class LVEntryLocalizationWrapper
	extends BaseModelWrapper<LVEntryLocalization>
	implements LVEntryLocalization, ModelWrapper<LVEntryLocalization> {

	public LVEntryLocalizationWrapper(LVEntryLocalization lvEntryLocalization) {
		super(lvEntryLocalization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("headId", getHeadId());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long headId = (Long)attributes.get("headId");

		if (headId != null) {
			setHeadId(headId);
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
	 * Returns the company ID of this lv entry localization.
	 *
	 * @return the company ID of this lv entry localization
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this lv entry localization.
	 *
	 * @return the content of this lv entry localization
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the head ID of this lv entry localization.
	 *
	 * @return the head ID of this lv entry localization
	 */
	@Override
	public long getHeadId() {
		return model.getHeadId();
	}

	/**
	 * Returns the language ID of this lv entry localization.
	 *
	 * @return the language ID of this lv entry localization
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the lv entry ID of this lv entry localization.
	 *
	 * @return the lv entry ID of this lv entry localization
	 */
	@Override
	public long getLvEntryId() {
		return model.getLvEntryId();
	}

	/**
	 * Returns the lv entry localization ID of this lv entry localization.
	 *
	 * @return the lv entry localization ID of this lv entry localization
	 */
	@Override
	public long getLvEntryLocalizationId() {
		return model.getLvEntryLocalizationId();
	}

	/**
	 * Returns the mvcc version of this lv entry localization.
	 *
	 * @return the mvcc version of this lv entry localization
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this lv entry localization.
	 *
	 * @return the primary key of this lv entry localization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the title of this lv entry localization.
	 *
	 * @return the title of this lv entry localization
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Sets the company ID of this lv entry localization.
	 *
	 * @param companyId the company ID of this lv entry localization
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this lv entry localization.
	 *
	 * @param content the content of this lv entry localization
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the head ID of this lv entry localization.
	 *
	 * @param headId the head ID of this lv entry localization
	 */
	@Override
	public void setHeadId(long headId) {
		model.setHeadId(headId);
	}

	/**
	 * Sets the language ID of this lv entry localization.
	 *
	 * @param languageId the language ID of this lv entry localization
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the lv entry ID of this lv entry localization.
	 *
	 * @param lvEntryId the lv entry ID of this lv entry localization
	 */
	@Override
	public void setLvEntryId(long lvEntryId) {
		model.setLvEntryId(lvEntryId);
	}

	/**
	 * Sets the lv entry localization ID of this lv entry localization.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID of this lv entry localization
	 */
	@Override
	public void setLvEntryLocalizationId(long lvEntryLocalizationId) {
		model.setLvEntryLocalizationId(lvEntryLocalizationId);
	}

	/**
	 * Sets the mvcc version of this lv entry localization.
	 *
	 * @param mvccVersion the mvcc version of this lv entry localization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this lv entry localization.
	 *
	 * @param primaryKey the primary key of this lv entry localization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the title of this lv entry localization.
	 *
	 * @param title the title of this lv entry localization
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	@Override
	public boolean isHead() {
		return model.isHead();
	}

	@Override
	public void populateVersionModel(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		model.populateVersionModel(lvEntryLocalizationVersion);
	}

	@Override
	protected LVEntryLocalizationWrapper wrap(
		LVEntryLocalization lvEntryLocalization) {

		return new LVEntryLocalizationWrapper(lvEntryLocalization);
	}

}