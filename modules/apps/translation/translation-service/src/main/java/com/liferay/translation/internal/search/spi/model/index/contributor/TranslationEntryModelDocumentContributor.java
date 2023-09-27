/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.translation.model.TranslationEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "indexer.class.name=com.liferay.translation.model.TranslationEntry",
	service = ModelDocumentContributor.class
)
public class TranslationEntryModelDocumentContributor
	implements ModelDocumentContributor<TranslationEntry> {

	@Override
	public void contribute(
		Document document, TranslationEntry translationEntry) {

		document.addText(Field.CONTENT, translationEntry.getContent());
		document.addDate(
			Field.MODIFIED_DATE, translationEntry.getModifiedDate());
		document.addKeyword(Field.STATUS, translationEntry.getStatus());
		document.addText(Field.TITLE, _getTitle(translationEntry));
	}

	private Locale _getLocale(TranslationEntry translationEntry)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return _portal.getSiteDefaultLocale(translationEntry.getGroupId());
		}

		return serviceContext.getLocale();
	}

	private String _getTitle(TranslationEntry translationEntry) {
		try {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						TranslationEntry.class.getName());

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					translationEntry.getTranslationEntryId());

			return assetRenderer.getTitle(_getLocale(translationEntry));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TranslationEntryModelDocumentContributor.class);

	@Reference
	private Portal _portal;

}