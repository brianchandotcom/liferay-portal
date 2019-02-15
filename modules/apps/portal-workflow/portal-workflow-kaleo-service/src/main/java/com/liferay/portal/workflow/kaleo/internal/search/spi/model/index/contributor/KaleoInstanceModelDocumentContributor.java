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

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.workflow.kaleo.internal.search.KaleoInstanceField;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalServiceUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstance",
	service = ModelDocumentContributor.class
)
public class KaleoInstanceModelDocumentContributor
	implements ModelDocumentContributor<KaleoInstance> {

	@Override
	public void contribute(Document document, KaleoInstance kaleoInstance) {
		String status = getStatus(kaleoInstance);

		if (status.equals("created")) {
			return;
		}

		document.addKeyword(
			KaleoInstanceField.CLASS_NAME, kaleoInstance.getClassName());
		document.addKeyword(Field.CLASS_PK, kaleoInstance.getClassPK());
		document.addKeyword(
			KaleoInstanceField.COMPLETED, kaleoInstance.isCompleted());
		document.addDate(
			KaleoInstanceField.COMPLETION_DATE,
			kaleoInstance.getCompletionDate());
		document.addKeyword(
			KaleoInstanceField.KALEO_DEFINITION_NAME,
			kaleoInstance.getKaleoDefinitionName());
		document.addKeyword(
			KaleoInstanceField.KALEO_INSTANCE_ID,
			kaleoInstance.getKaleoInstanceId());
		document.addKeyword(KaleoInstanceField.STATUS, status);

		AssetEntry assetEntry = getAssetEntry(kaleoInstance);

		if (assetEntry == null) {
			return;
		}

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String siteDefaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String[] titleLanguageIds = getLanguageIds(
			siteDefaultLanguageId, assetEntry.getTitle());

		for (String titleLanguageId : titleLanguageIds) {
			document.addText(
				LocalizationUtil.getLocalizedName(
					KaleoInstanceField.ASSET_TITLE, titleLanguageId),
				assetEntry.getTitle(titleLanguageId));
		}

		String[] descriptionLanguageIds = getLanguageIds(
			siteDefaultLanguageId, assetEntry.getDescription());

		for (String descriptionLanguageId : descriptionLanguageIds) {
			document.addText(
				LocalizationUtil.getLocalizedName(
					KaleoInstanceField.ASSET_DESCRIPTION,
					descriptionLanguageId),
				assetEntry.getDescription(descriptionLanguageId));
		}
	}

	protected AssetEntry getAssetEntry(KaleoInstance kaleoInstance) {
		try {
			AssetRendererFactory<?> assetRendererFactory =
				getAssetRendererFactory(kaleoInstance.getClassName());

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					kaleoInstance.getClassPK());

			return assetEntryLocalService.getEntry(
				assetRenderer.getClassName(), assetRenderer.getClassPK());
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return null;
	}

	protected AssetRendererFactory<?> getAssetRendererFactory(
		String className) {

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(className);
	}

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	protected String getStatus(KaleoInstance kaleoInstance) {
		String status = StringPool.BLANK;

		DynamicQuery dynamicQuery =
			KaleoInstanceTokenLocalServiceUtil.dynamicQuery();

		Property kaleoInstanceIdProperty = PropertyFactoryUtil.forName(
			"kaleoInstanceId");

		dynamicQuery.add(
			kaleoInstanceIdProperty.eq(kaleoInstance.getKaleoInstanceId()));

		List<KaleoInstanceToken> kaleoInstanceTokenList =
			KaleoInstanceTokenLocalServiceUtil.dynamicQuery(dynamicQuery);

		KaleoInstanceToken kaleoInstanceToken = kaleoInstanceTokenList.get(0);

		status = kaleoInstanceToken.getCurrentKaleoNodeName();

		return status;
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoInstanceModelDocumentContributor.class);

}