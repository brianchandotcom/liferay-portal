/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.product.model.CPConfigurationEntry",
	service = ModelDocumentContributor.class
)
public class CPConfigurationEntryModelDocumentContributor
	implements ModelDocumentContributor<CPConfigurationEntry> {

	@Override
	public void contribute(
		Document document, CPConfigurationEntry cpConfigurationEntry) {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Indexing commerce product configuration entry " +
						cpConfigurationEntry);
			}

			document.addKeyword(
				CPField.ASSET_CATEGORY_NAMES,
				_toLowerCaseStringArray(
					_assetCategoryLocalService.getCategoryNames(
						CPDefinition.class.getName(),
						cpConfigurationEntry.getClassPK())));
			document.addNumber(CPField.DEPTH, cpConfigurationEntry.getDepth());
			document.addKeyword(
				CPField.EXTERNAL_REFERENCE_CODE,
				cpConfigurationEntry.getExternalReferenceCode());
			document.addKeyword(
				CPField.CP_CONFIGURATION_LIST_ID,
				cpConfigurationEntry.getCPConfigurationListId());
			document.addNumber(
				CPField.HEIGHT, cpConfigurationEntry.getHeight());
			document.addKeyword(
				Field.HIDDEN, !cpConfigurationEntry.isVisible());

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Commerce product definition " + cpConfigurationEntry +
						" indexed successfully");
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index commerce product definition" +
						cpConfigurationEntry,
					exception);
			}
		}
	}

	private String[] _toLowerCaseStringArray(String[] categoryNames) {
		for (int i = 0; i < categoryNames.length; i++) {
			categoryNames[i] = categoryNames[i].toLowerCase();
		}

		return categoryNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPConfigurationEntryModelDocumentContributor.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}