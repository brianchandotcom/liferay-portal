/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LocalizedEntryLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntryLocalization
 * @generated
 */
public class LocalizedEntryLocalizationWrapper
	extends BaseModelWrapper<LocalizedEntryLocalization>
	implements LocalizedEntryLocalization,
			   ModelWrapper<LocalizedEntryLocalization> {

	public LocalizedEntryLocalizationWrapper(
		LocalizedEntryLocalization localizedEntryLocalization) {

		super(localizedEntryLocalization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"localizedEntryLocalizationId", getLocalizedEntryLocalizationId());
		attributes.put("localizedEntryId", getLocalizedEntryId());
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

		Long localizedEntryLocalizationId = (Long)attributes.get(
			"localizedEntryLocalizationId");

		if (localizedEntryLocalizationId != null) {
			setLocalizedEntryLocalizationId(localizedEntryLocalizationId);
		}

		Long localizedEntryId = (Long)attributes.get("localizedEntryId");

		if (localizedEntryId != null) {
			setLocalizedEntryId(localizedEntryId);
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
	public LocalizedEntryLocalization cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the content of this localized entry localization.
	 *
	 * @return the content of this localized entry localization
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the language ID of this localized entry localization.
	 *
	 * @return the language ID of this localized entry localization
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the localized entry ID of this localized entry localization.
	 *
	 * @return the localized entry ID of this localized entry localization
	 */
	@Override
	public long getLocalizedEntryId() {
		return model.getLocalizedEntryId();
	}

	/**
	 * Returns the localized entry localization ID of this localized entry localization.
	 *
	 * @return the localized entry localization ID of this localized entry localization
	 */
	@Override
	public long getLocalizedEntryLocalizationId() {
		return model.getLocalizedEntryLocalizationId();
	}

	/**
	 * Returns the mvcc version of this localized entry localization.
	 *
	 * @return the mvcc version of this localized entry localization
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this localized entry localization.
	 *
	 * @return the primary key of this localized entry localization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the title of this localized entry localization.
	 *
	 * @return the title of this localized entry localization
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Sets the content of this localized entry localization.
	 *
	 * @param content the content of this localized entry localization
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the language ID of this localized entry localization.
	 *
	 * @param languageId the language ID of this localized entry localization
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the localized entry ID of this localized entry localization.
	 *
	 * @param localizedEntryId the localized entry ID of this localized entry localization
	 */
	@Override
	public void setLocalizedEntryId(long localizedEntryId) {
		model.setLocalizedEntryId(localizedEntryId);
	}

	/**
	 * Sets the localized entry localization ID of this localized entry localization.
	 *
	 * @param localizedEntryLocalizationId the localized entry localization ID of this localized entry localization
	 */
	@Override
	public void setLocalizedEntryLocalizationId(
		long localizedEntryLocalizationId) {

		model.setLocalizedEntryLocalizationId(localizedEntryLocalizationId);
	}

	/**
	 * Sets the mvcc version of this localized entry localization.
	 *
	 * @param mvccVersion the mvcc version of this localized entry localization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this localized entry localization.
	 *
	 * @param primaryKey the primary key of this localized entry localization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the title of this localized entry localization.
	 *
	 * @param title the title of this localized entry localization
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected LocalizedEntryLocalizationWrapper wrap(
		LocalizedEntryLocalization localizedEntryLocalization) {

		return new LocalizedEntryLocalizationWrapper(
			localizedEntryLocalization);
	}

}