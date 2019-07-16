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

package com.liferay.portlet.asset.util;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author     Pavel Savinov
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.asset.tags.internal.search.AssetTagIndexer}
 */
@Deprecated
public class AssetTagIndexer extends BaseIndexer<AssetTag> {

	public static final String CLASS_NAME = AssetTag.class.getName();

	public AssetTagIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.GROUP_ID, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		String name = (String)searchContext.getAttribute(Field.NAME);

		if (Validator.isNotNull(name)) {
			BooleanQuery nameQuery = new BooleanQueryImpl();

			addSearchTerm(nameQuery, searchContext, Field.NAME, true);

			searchQuery.add(nameQuery, BooleanClauseOccur.SHOULD);
		}
	}

	@Override
	protected void doDelete(AssetTag assetTag) throws Exception {
		deleteDocument(assetTag.getCompanyId(), assetTag.getTagId());
	}

	@Override
	protected Document doGetDocument(AssetTag assetTag) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing asset tag " + assetTag);
		}

		Document document = getBaseModelDocument(CLASS_NAME, assetTag);

		document.addTextSortable(Field.NAME, assetTag.getName());
		document.addNumberSortable("assetCount", assetTag.getAssetCount());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + assetTag + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	protected void doReindex(AssetTag assetTag) throws Exception {
		IndexWriterHelperUtil.updateDocument(
			getSearchEngineId(), assetTag.getCompanyId(), getDocument(assetTag),
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(AssetTagLocalServiceUtil.getTag(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexTags(companyId);
	}

	protected void reindexTags(final long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			AssetTagLocalServiceUtil.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(AssetTag tag) -> {
				try {
					Document document = getDocument(tag);

					if (document != null) {
						indexableActionableDynamicQuery.addDocuments(document);
					}
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index asset tag " + tag.getTagId(), pe);
					}
				}
			});

		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagIndexer.class);

}