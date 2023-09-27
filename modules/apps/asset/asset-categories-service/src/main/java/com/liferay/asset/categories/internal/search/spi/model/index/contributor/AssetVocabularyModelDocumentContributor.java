/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@Component(
	property = "indexer.class.name=com.liferay.asset.kernel.model.AssetVocabulary",
	service = ModelDocumentContributor.class
)
public class AssetVocabularyModelDocumentContributor
	implements ModelDocumentContributor<AssetVocabulary> {

	@Override
	public void contribute(Document document, AssetVocabulary assetVocabulary) {
		document.addKeyword(
			Field.ASSET_VOCABULARY_ID, assetVocabulary.getVocabularyId());

		Locale siteDefaultLocale = _getSiteDefaultLocale(
			assetVocabulary.getGroupId());

		_searchLocalizationHelper.addLocalizedField(
			document, Field.DESCRIPTION, siteDefaultLocale,
			assetVocabulary.getDescriptionMap());

		document.addText(Field.NAME, assetVocabulary.getName());

		_searchLocalizationHelper.addLocalizedField(
			document, Field.TITLE, siteDefaultLocale,
			assetVocabulary.getTitleMap());

		document.addNumber(
			Field.VISIBILITY_TYPE, assetVocabulary.getVisibilityType());
		document.addLocalizedKeyword(
			"localized_title",
			_localization.populateLocalizationMap(
				assetVocabulary.getTitleMap(),
				assetVocabulary.getDefaultLanguageId(),
				assetVocabulary.getGroupId()),
			true, true);
	}

	private Locale _getSiteDefaultLocale(long groupId) {
		try {
			return _portal.getSiteDefaultLocale(groupId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

}