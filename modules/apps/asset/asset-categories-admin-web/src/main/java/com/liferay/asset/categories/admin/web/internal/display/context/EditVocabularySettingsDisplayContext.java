/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class EditVocabularySettingsDisplayContext {

	public EditVocabularySettingsDisplayContext(
		HttpServletRequest httpServletRequest, AssetVocabulary vocabulary) {

		_vocabulary = vocabulary;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public boolean classTypePKExists(long classNameId, long classTypePK) {
		if ((classNameId <= 0) ||
			(classTypePK == AssetCategoryConstants.ALL_CLASS_TYPE_PK)) {

			return true;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(
			new long[] {
				_themeDisplay.getCompanyGroupId(),
				_themeDisplay.getScopeGroupId()
			},
			_themeDisplay.getLocale());

		if (!classTypes.isEmpty()) {
			if (ListUtil.exists(
					classTypes,
					classType -> classType.getClassTypeId() == classTypePK)) {

				return true;
			}

			return false;
		}

		return true;
	}

	public List<AssetRendererFactory<?>> getAvailableAssetRendererFactories() {
		return ListUtil.filter(
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				_themeDisplay.getCompanyId()),
			AssetRendererFactory::isCategorizable);
	}

	public List<SelectOption> getClassNameIdOptions(long selectedClassNameId) {
		List<SelectOption> options = new ArrayList<>();

		options.add(
			new SelectOption(
				LanguageUtil.get(_themeDisplay.getLocale(), "all-asset-types"),
				String.valueOf(AssetCategoryConstants.ALL_CLASS_NAME_ID),
				selectedClassNameId ==
					AssetCategoryConstants.ALL_CLASS_NAME_ID));

		List<AssetRendererFactory<?>> availableAssetRendererFactories =
			getAvailableAssetRendererFactories();

		for (AssetRendererFactory<?> availableAssetRendererFactory :
				availableAssetRendererFactories) {

			options.add(
				new SelectOption(
					ResourceActionsUtil.getModelResource(
						_themeDisplay.getLocale(),
						availableAssetRendererFactory.getClassName()),
					String.valueOf(
						availableAssetRendererFactory.getClassNameId()),
					selectedClassNameId ==
						availableAssetRendererFactory.getClassNameId()));
		}

		return options;
	}

	public List<AssetRendererFactory<?>> getClassTypedAssetRenderFactories() {
		return ListUtil.filter(
			getAvailableAssetRendererFactories(),
			AssetRendererFactory::isSupportsClassTypes);
	}

	public List<SelectOption> getClassTypePKOptions(
		AssetRendererFactory<?> assetRendererFactory, long selectedClassNameId,
		long selectedClassTypePK) {

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(
			new long[] {
				_themeDisplay.getCompanyGroupId(),
				_themeDisplay.getScopeGroupId()
			},
			_themeDisplay.getLocale());

		if (classTypes.isEmpty()) {
			return Collections.emptyList();
		}

		List<SelectOption> options = new ArrayList<>();

		boolean exists = classTypePKExists(
			selectedClassNameId, selectedClassTypePK);

		if (!exists) {
			options.add(
				new SelectOption(
					LanguageUtil.get(_themeDisplay.getLocale(), "none"),
					String.valueOf(selectedClassTypePK), true));
		}

		for (ClassType classType : classTypes) {
			options.add(
				new SelectOption(
					HtmlUtil.escape(classType.getName()),
					String.valueOf(classType.getClassTypeId()),
					(selectedClassNameId ==
						assetRendererFactory.getClassNameId()) &&
					(selectedClassTypePK == classType.getClassTypeId())));
		}

		return options;
	}

	public long[] getSelectedClassNameIds() {
		if (_vocabulary != null) {
			return _vocabulary.getSelectedClassNameIds();
		}

		return AssetVocabularySettingsHelper.DEFAULT_SELECTED_CLASS_NAME_IDS;
	}

	public long[] getSelectedClassTypePKs() {
		if (_vocabulary != null) {
			return _vocabulary.getSelectedClassTypePKs();
		}

		return AssetVocabularySettingsHelper.DEFAULT_SELECTED_CLASS_TYPE_PKS;
	}

	private final ThemeDisplay _themeDisplay;
	private final AssetVocabulary _vocabulary;

}