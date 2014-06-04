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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class AssetVocabularyImpl extends AssetVocabularyBaseImpl {

	public AssetVocabularyImpl() {
	}

	@Override
	public List<AssetCategory> getCategories() throws SystemException {
		return AssetCategoryLocalServiceUtil.getVocabularyCategories(
			getVocabularyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public long[] getRequiredClassNameIds() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper();

		return vocabularySettingsHelper.getRequiredClassNameIds();
	}

	@Override
	public long[] getSelectedClassNameIds() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper();

		return vocabularySettingsHelper.getClassNameIds();
	}

	@Override
	public String getSettings() {
		if (_vocabularySettingsHelper == null) {
			return super.getSettings();
		}
		else {
			return _vocabularySettingsHelper.toString();
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public UnicodeProperties getSettingsProperties() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper();

		UnicodeProperties settingsProperties = new UnicodeProperties(true);

		settingsProperties.fastLoad(vocabularySettingsHelper.toString());

		return settingsProperties;
	}

	@Override
	public String getTitle(String languageId) {
		String value = super.getTitle(languageId);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		String value = super.getTitle(languageId, useDefault);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	@Override
	public String getUnambiguousTitle(
			List<AssetVocabulary> vocabularies, long groupId,
			final Locale locale)
		throws PortalException, SystemException {

		if (getGroupId() == groupId ) {
			return getTitle(locale);
		}

		boolean hasAmbiguousTitle = ListUtil.exists(
			vocabularies,
			new PredicateFilter<AssetVocabulary>() {

				@Override
				public boolean filter(AssetVocabulary vocabulary) {
					String title = vocabulary.getTitle(locale);

					if (title.equals(getTitle(locale)) &&
						(vocabulary.getVocabularyId() != getVocabularyId())) {

						return true;
					}

					return false;
				}

			});

		if (hasAmbiguousTitle) {
			Group group = GroupLocalServiceUtil.getGroup(getGroupId());

			return group.getUnambiguousName(getTitle(locale), locale);
		}

		return getTitle(locale);
	}

	@Override
	public boolean hasMoreThanOneCategorySelected(final long[] categoryIds)
		throws SystemException {

		PredicateFilter<AssetCategory> predicateFilter =
			new PredicateFilter<AssetCategory>() {

				@Override
				public boolean filter(AssetCategory assetCategory) {
					return ArrayUtil.contains(
						categoryIds, assetCategory.getCategoryId());
				}

			};

		if (ListUtil.count(getCategories(), predicateFilter) > 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isAssociatedToClassNameId(long classNameId) {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper();

		return vocabularySettingsHelper.hasClassNameId(classNameId);
	}

	@Override
	public boolean isMissingRequiredCategory(
			long classNameId, final long[] categoryIds)
		throws SystemException {

		if (!isRequired(classNameId)) {
			return false;
		}

		PredicateFilter<AssetCategory> predicateFilter =
			new PredicateFilter<AssetCategory>() {

				@Override
				public boolean filter(AssetCategory assetCategory) {
					return ArrayUtil.contains(
						categoryIds, assetCategory.getCategoryId());
				}

			};

		return !ListUtil.exists(getCategories(), predicateFilter);
	}

	@Override
	public boolean isMultiValued() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper();

		return vocabularySettingsHelper.isMultiValued();
	}

	@Override
	public boolean isRequired(long classNameId) {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper();

		return vocabularySettingsHelper.isClassNameIdRequired(classNameId);
	}

	@Override
	public void setSettings(String settings) {
		_vocabularySettingsHelper = null;

		super.setSettings(settings);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void setSettingsProperties(UnicodeProperties settingsProperties) {
		super.setSettings(settingsProperties.toString());

		_vocabularySettingsHelper = getVocabularySettingsHelper();
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper() {
		if (_vocabularySettingsHelper == null) {
			_vocabularySettingsHelper = new AssetVocabularySettingsHelper(
				super.getSettings());
		}

		return _vocabularySettingsHelper;
	}

	private AssetVocabularySettingsHelper _vocabularySettingsHelper;

}