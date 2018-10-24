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

package com.liferay.layout.internal.search;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.batch.BatchIndexingHelper;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = Indexer.class)
public class LayoutIndexer extends BaseIndexer<Layout> {

	@Override
	public String getClassName() {
		return Layout.class.getName();
	}

	@Override
	protected void doDelete(Layout object) throws Exception {
	}

	@Override
	protected Document doGetDocument(Layout object) throws Exception {
		return null;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		return null;
	}

	@Override
	protected void doReindex(Layout layout) throws Exception {
		Document document = getDocument(layout);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), layout.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		Layout layout = _layoutLocalService.getLayout(classPK);

		doReindex(layout);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexLayouts(companyId);
	}

	private void _reindexLayouts(long companyId) throws PortalException {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_layoutLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setInterval(
			_batchIndexingHelper.getBulkSize(Layout.class.getName()));
		indexableActionableDynamicQuery.setPerformActionMethod(
			(Layout layout) -> {
				try {
					Document document = getDocument(layout);

					indexableActionableDynamicQuery.addDocuments(document);
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index layout " + layout.getPlid(), pe);
					}
				}
			});

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(LayoutIndexer.class);

	@Reference
	private BatchIndexingHelper _batchIndexingHelper;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

}