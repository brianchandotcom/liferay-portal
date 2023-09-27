/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.criteria.extension.sample.internal.odata.retriever;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.segments.criteria.extension.sample.internal.odata.entity.KBArticleEntityModel;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.odata.search.ODataSearchAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.knowledge.base.model.KBArticle",
	service = ODataRetriever.class
)
public class KBArticleODataRetriever implements ODataRetriever<KBArticle> {

	@Override
	public List<KBArticle> getResults(
			long companyId, String filterString, Locale locale, int start,
			int end)
		throws PortalException {

		Hits hits = _oDataSearchAdapter.search(
			companyId, _getFilterParser(), filterString,
			KBArticle.class.getName(), _entityModel, locale, start, end);

		return _getKBArticles(hits);
	}

	@Override
	public int getResultsCount(
			long companyId, String filterString, Locale locale)
		throws PortalException {

		return _oDataSearchAdapter.searchCount(
			companyId, _getFilterParser(), filterString,
			KBArticle.class.getName(), _entityModel, locale);
	}

	private FilterParser _getFilterParser() {
		return _filterParserProvider.provide(_entityModel);
	}

	private KBArticle _getKBArticle(Document document) throws PortalException {
		long resourcePrimKey = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		return _kbArticleLocalService.getLatestKBArticle(resourcePrimKey, 0);
	}

	private List<KBArticle> _getKBArticles(Hits hits) throws PortalException {
		Document[] documents = hits.getDocs();

		List<KBArticle> kbArticles = new ArrayList<>(documents.length);

		for (Document document : documents) {
			kbArticles.add(_getKBArticle(document));
		}

		return kbArticles;
	}

	private static final EntityModel _entityModel = new KBArticleEntityModel();

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference
	private ODataSearchAdapter _oDataSearchAdapter;

}