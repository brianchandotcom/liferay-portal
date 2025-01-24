/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSEOEntryCustomMetaTag}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryCustomMetaTag
 * @generated
 */
public class LayoutSEOEntryCustomMetaTagWrapper
	extends BaseModelWrapper<LayoutSEOEntryCustomMetaTag>
	implements LayoutSEOEntryCustomMetaTag,
			   ModelWrapper<LayoutSEOEntryCustomMetaTag> {

	public LayoutSEOEntryCustomMetaTagWrapper(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		super(layoutSEOEntryCustomMetaTag);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"layoutSEOEntryCustomMetaTagId",
			getLayoutSEOEntryCustomMetaTagId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("layoutSEOEntryId", getLayoutSEOEntryId());
		attributes.put("property", getProperty());
		attributes.put("content", getContent());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long layoutSEOEntryCustomMetaTagId = (Long)attributes.get(
			"layoutSEOEntryCustomMetaTagId");

		if (layoutSEOEntryCustomMetaTagId != null) {
			setLayoutSEOEntryCustomMetaTagId(layoutSEOEntryCustomMetaTagId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long layoutSEOEntryId = (Long)attributes.get("layoutSEOEntryId");

		if (layoutSEOEntryId != null) {
			setLayoutSEOEntryId(layoutSEOEntryId);
		}

		String property = (String)attributes.get("property");

		if (property != null) {
			setProperty(property);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}
	}

	@Override
	public LayoutSEOEntryCustomMetaTag cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this layout seo entry custom meta tag.
	 *
	 * @return the company ID of this layout seo entry custom meta tag
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this layout seo entry custom meta tag.
	 *
	 * @return the content of this layout seo entry custom meta tag
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the localized content of this layout seo entry custom meta tag in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized content of this layout seo entry custom meta tag
	 */
	@Override
	public String getContent(java.util.Locale locale) {
		return model.getContent(locale);
	}

	/**
	 * Returns the localized content of this layout seo entry custom meta tag in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized content of this layout seo entry custom meta tag. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getContent(java.util.Locale locale, boolean useDefault) {
		return model.getContent(locale, useDefault);
	}

	/**
	 * Returns the localized content of this layout seo entry custom meta tag in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized content of this layout seo entry custom meta tag
	 */
	@Override
	public String getContent(String languageId) {
		return model.getContent(languageId);
	}

	/**
	 * Returns the localized content of this layout seo entry custom meta tag in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized content of this layout seo entry custom meta tag
	 */
	@Override
	public String getContent(String languageId, boolean useDefault) {
		return model.getContent(languageId, useDefault);
	}

	@Override
	public String getContentCurrentLanguageId() {
		return model.getContentCurrentLanguageId();
	}

	@Override
	public String getContentCurrentValue() {
		return model.getContentCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized contents of this layout seo entry custom meta tag.
	 *
	 * @return the locales and localized contents of this layout seo entry custom meta tag
	 */
	@Override
	public Map<java.util.Locale, String> getContentMap() {
		return model.getContentMap();
	}

	/**
	 * Returns the ct collection ID of this layout seo entry custom meta tag.
	 *
	 * @return the ct collection ID of this layout seo entry custom meta tag
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the group ID of this layout seo entry custom meta tag.
	 *
	 * @return the group ID of this layout seo entry custom meta tag
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout seo entry custom meta tag ID of this layout seo entry custom meta tag.
	 *
	 * @return the layout seo entry custom meta tag ID of this layout seo entry custom meta tag
	 */
	@Override
	public long getLayoutSEOEntryCustomMetaTagId() {
		return model.getLayoutSEOEntryCustomMetaTagId();
	}

	/**
	 * Returns the layout seo entry ID of this layout seo entry custom meta tag.
	 *
	 * @return the layout seo entry ID of this layout seo entry custom meta tag
	 */
	@Override
	public long getLayoutSEOEntryId() {
		return model.getLayoutSEOEntryId();
	}

	/**
	 * Returns the mvcc version of this layout seo entry custom meta tag.
	 *
	 * @return the mvcc version of this layout seo entry custom meta tag
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this layout seo entry custom meta tag.
	 *
	 * @return the primary key of this layout seo entry custom meta tag
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the property of this layout seo entry custom meta tag.
	 *
	 * @return the property of this layout seo entry custom meta tag
	 */
	@Override
	public String getProperty() {
		return model.getProperty();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets the company ID of this layout seo entry custom meta tag.
	 *
	 * @param companyId the company ID of this layout seo entry custom meta tag
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this layout seo entry custom meta tag.
	 *
	 * @param content the content of this layout seo entry custom meta tag
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the localized content of this layout seo entry custom meta tag in the language.
	 *
	 * @param content the localized content of this layout seo entry custom meta tag
	 * @param locale the locale of the language
	 */
	@Override
	public void setContent(String content, java.util.Locale locale) {
		model.setContent(content, locale);
	}

	/**
	 * Sets the localized content of this layout seo entry custom meta tag in the language, and sets the default locale.
	 *
	 * @param content the localized content of this layout seo entry custom meta tag
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setContent(
		String content, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setContent(content, locale, defaultLocale);
	}

	@Override
	public void setContentCurrentLanguageId(String languageId) {
		model.setContentCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized contents of this layout seo entry custom meta tag from the map of locales and localized contents.
	 *
	 * @param contentMap the locales and localized contents of this layout seo entry custom meta tag
	 */
	@Override
	public void setContentMap(Map<java.util.Locale, String> contentMap) {
		model.setContentMap(contentMap);
	}

	/**
	 * Sets the localized contents of this layout seo entry custom meta tag from the map of locales and localized contents, and sets the default locale.
	 *
	 * @param contentMap the locales and localized contents of this layout seo entry custom meta tag
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setContentMap(
		Map<java.util.Locale, String> contentMap,
		java.util.Locale defaultLocale) {

		model.setContentMap(contentMap, defaultLocale);
	}

	/**
	 * Sets the ct collection ID of this layout seo entry custom meta tag.
	 *
	 * @param ctCollectionId the ct collection ID of this layout seo entry custom meta tag
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this layout seo entry custom meta tag.
	 *
	 * @param groupId the group ID of this layout seo entry custom meta tag
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout seo entry custom meta tag ID of this layout seo entry custom meta tag.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the layout seo entry custom meta tag ID of this layout seo entry custom meta tag
	 */
	@Override
	public void setLayoutSEOEntryCustomMetaTagId(
		long layoutSEOEntryCustomMetaTagId) {

		model.setLayoutSEOEntryCustomMetaTagId(layoutSEOEntryCustomMetaTagId);
	}

	/**
	 * Sets the layout seo entry ID of this layout seo entry custom meta tag.
	 *
	 * @param layoutSEOEntryId the layout seo entry ID of this layout seo entry custom meta tag
	 */
	@Override
	public void setLayoutSEOEntryId(long layoutSEOEntryId) {
		model.setLayoutSEOEntryId(layoutSEOEntryId);
	}

	/**
	 * Sets the mvcc version of this layout seo entry custom meta tag.
	 *
	 * @param mvccVersion the mvcc version of this layout seo entry custom meta tag
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this layout seo entry custom meta tag.
	 *
	 * @param primaryKey the primary key of this layout seo entry custom meta tag
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the property of this layout seo entry custom meta tag.
	 *
	 * @param property the property of this layout seo entry custom meta tag
	 */
	@Override
	public void setProperty(String property) {
		model.setProperty(property);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<LayoutSEOEntryCustomMetaTag, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<LayoutSEOEntryCustomMetaTag, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected LayoutSEOEntryCustomMetaTagWrapper wrap(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		return new LayoutSEOEntryCustomMetaTagWrapper(
			layoutSEOEntryCustomMetaTag);
	}

}