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

package com.liferay.portlet.softwarecatalog.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
@OSGiBeanProperties
public class SCIndexer extends BaseIndexer<SCProductEntry> {

	public static final String CLASS_NAME = SCProductEntry.class.getName();

	public SCIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.CONTENT, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.TITLE, Field.UID);
		setStagingAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	protected void doDelete(SCProductEntry scProductEntry) throws Exception {
		deleteDocument(
			scProductEntry.getCompanyId(), scProductEntry.getProductEntryId());
	}

	@Override
	protected Document doGetDocument(SCProductEntry scProductEntry)
		throws Exception {

		Document document = getBaseModelDocument(CLASS_NAME, scProductEntry);

		StringBundler sb = new StringBundler(15);

		String longDescription = HtmlUtil.extractText(
			scProductEntry.getLongDescription());

		sb.append(longDescription);

		sb.append(StringPool.SPACE);
		sb.append(scProductEntry.getPageURL());
		sb.append(StringPool.SPACE);
		sb.append(scProductEntry.getRepoArtifactId());
		sb.append(StringPool.SPACE);
		sb.append(scProductEntry.getRepoGroupId());
		sb.append(StringPool.SPACE);

		String shortDescription = HtmlUtil.extractText(
			scProductEntry.getShortDescription());

		sb.append(shortDescription);

		sb.append(StringPool.SPACE);
		sb.append(scProductEntry.getType());
		sb.append(StringPool.SPACE);
		sb.append(scProductEntry.getUserId());
		sb.append(StringPool.SPACE);

		String userName = PortalUtil.getUserName(
			scProductEntry.getUserId(), scProductEntry.getUserName());

		sb.append(userName);

		document.addText(Field.CONTENT, sb.toString());

		document.addText(Field.TITLE, scProductEntry.getName());
		document.addKeyword(Field.TYPE, scProductEntry.getType());

		String version = StringPool.BLANK;

		SCProductVersion latestProductVersion =
			scProductEntry.getLatestVersion();

		if (latestProductVersion != null) {
			version = latestProductVersion.getVersion();
		}

		document.addKeyword(Field.VERSION, version);

		document.addText("longDescription", longDescription);
		document.addText("pageURL", scProductEntry.getPageURL());
		document.addKeyword(
			"repoArtifactId", scProductEntry.getRepoArtifactId());
		document.addKeyword("repoGroupId", scProductEntry.getRepoGroupId());
		document.addText("shortDescription", shortDescription);

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, Field.TITLE, Field.CONTENT);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(SCProductEntry scProductEntry) throws Exception {
		Document document = getDocument(scProductEntry);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), scProductEntry.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		SCProductEntry productEntry =
			SCProductEntryLocalServiceUtil.getProductEntry(classPK);

		doReindex(productEntry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexProductEntries(companyId);
	}

	@Override
	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {

		BooleanFilter booleanFilter = fullQuery.getPreBooleanFilter();

		if (booleanFilter == null) {
			booleanFilter = new BooleanFilter();
		}

		String type = (String)searchContext.getAttribute("type");

		if (Validator.isNotNull(type)) {
			booleanFilter.addRequiredTerm("type", type);
		}

		if (booleanFilter.hasClauses()) {
			fullQuery.setPreBooleanFilter(booleanFilter);
		}
	}

	protected void reindexProductEntries(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			SCProductEntryLocalServiceUtil.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SCProductEntry>() {

				@Override
				public void performAction(SCProductEntry productEntry) {
					try {
						Document document = getDocument(productEntry);

						indexableActionableDynamicQuery.addDocument(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to software catalog product entry " +
									productEntry.getProductEntryId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(SCIndexer.class);

}