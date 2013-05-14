/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryActionableDynamicQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class SCIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {SCProductEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.SOFTWARE_CATALOG;

	public SCIndexer() {
		setStagingAware(false);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		SCProductEntry productEntry = (SCProductEntry)obj;

		deleteDocument(
			productEntry.getCompanyId(), productEntry.getProductEntryId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		SCProductEntry productEntry = (SCProductEntry)obj;

		Document document = getBaseModelDocument(PORTLET_ID, productEntry);

		StringBundler sb = new StringBundler(15);

		String longDescription = HtmlUtil.extractText(
			productEntry.getLongDescription());

		sb.append(longDescription);

		sb.append(StringPool.SPACE);
		sb.append(productEntry.getPageURL());
		sb.append(StringPool.SPACE);
		sb.append(productEntry.getRepoArtifactId());
		sb.append(StringPool.SPACE);
		sb.append(productEntry.getRepoGroupId());
		sb.append(StringPool.SPACE);

		String shortDescription = HtmlUtil.extractText(
			productEntry.getShortDescription());

		sb.append(shortDescription);

		sb.append(StringPool.SPACE);
		sb.append(productEntry.getType());
		sb.append(StringPool.SPACE);
		sb.append(productEntry.getUserId());
		sb.append(StringPool.SPACE);

		String userName = PortalUtil.getUserName(
			productEntry.getUserId(), productEntry.getUserName());

		sb.append(userName);

		document.addText(Field.CONTENT, sb.toString());

		document.addText(Field.TITLE, productEntry.getName());
		document.addKeyword(Field.TYPE, productEntry.getType());

		String version = StringPool.BLANK;

		SCProductVersion latestProductVersion = productEntry.getLatestVersion();

		if (latestProductVersion != null) {
			version = latestProductVersion.getVersion();
		}

		document.addKeyword(Field.VERSION, version);

		document.addText("longDescription", longDescription);
		document.addText("pageURL", productEntry.getPageURL());
		document.addKeyword("repoArtifactId", productEntry.getRepoArtifactId());
		document.addKeyword("repoGroupId", productEntry.getRepoGroupId());
		document.addText("shortDescription", shortDescription);

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String productEntryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"struts_action", "/software_catalog/view_product_entry");
		portletURL.setParameter("productEntryId", productEntryId);

		Summary summary = createSummary(document, Field.TITLE, Field.CONTENT);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		SCProductEntry productEntry = (SCProductEntry)obj;

		Document document = getDocument(productEntry);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), productEntry.getCompanyId(), document);
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
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	@Override
	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {

		String type = (String)searchContext.getAttribute("type");

		if (Validator.isNotNull(type)) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			searchQuery.addRequiredTerm("type", type);

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void reindexProductEntries(long companyId)
		throws PortalException, SystemException {

		final Collection<Document> documents = new ArrayList<Document>();

		ActionableDynamicQuery actionableDynamicQuery =
			new SCProductEntryActionableDynamicQuery() {

			@Override
			protected void performAction(Object object) throws PortalException {
				SCProductEntry productEntry = (SCProductEntry)object;

				Document document = getDocument(productEntry);

				documents.add(document);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}