/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.web.internal.info.collection.provider;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mahmoud Azzam
 * @author Alessio Antonio Rendina
 */
@Component(service = RelatedInfoItemCollectionProvider.class)
public class CSDiagramEntriesRelatedInfoItemCollectionProvider
	implements RelatedInfoItemCollectionProvider<CPDefinition, CSDiagramEntry> {

	@Override
	public InfoPage<CSDiagramEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

		Pagination pagination = collectionQuery.getPagination();

		if (!(relatedItem instanceof CPDefinition)) {
			return InfoPage.of(Collections.emptyList(), pagination, 0);
		}

		CPDefinition cpDefinition = (CPDefinition)relatedItem;

		try {
			List<CSDiagramEntry> csDiagramEntries =
				_csDiagramEntryLocalService.
					getCPDefinitionRelatedCSDiagramEntries(
						cpDefinition.getCPDefinitionId());

			if (!csDiagramEntries.isEmpty()) {
				return InfoPage.of(
					ListUtil.subList(
						csDiagramEntries, pagination.getStart(),
						pagination.getEnd()),
					pagination, csDiagramEntries.size());
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return InfoPage.of(Collections.emptyList(), pagination, 0);
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "related-diagrams");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSDiagramEntriesRelatedInfoItemCollectionProvider.class);

	@Reference
	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

	@Reference
	private Language _language;

}