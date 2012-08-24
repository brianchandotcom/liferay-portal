/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
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
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property property = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(property.eq(companyId));
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

	protected void reindexProductEntries(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SCProductEntry.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minProductEntryIdProjection = ProjectionFactoryUtil.min(
			"productEntryId");
		Projection maxProductEntryIdProjection = ProjectionFactoryUtil.max(
			"productEntryId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minProductEntryIdProjection);
		projectionList.add(maxProductEntryIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = SCProductEntryLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxProductEntryIds = results.get(0);

		if ((minAndMaxProductEntryIds[0] == null) ||
			(minAndMaxProductEntryIds[1] == null)) {

			return;
		}

		long minProductEntryId = (Long)minAndMaxProductEntryIds[0];
		long maxProductEntryId = (Long)minAndMaxProductEntryIds[1];

		long startProductEntryId = minProductEntryId;
		long endProductEntryId = startProductEntryId + DEFAULT_INTERVAL;

		while (startProductEntryId <= maxProductEntryId) {
			reindexProductEntries(
				companyId, startProductEntryId, endProductEntryId);

			startProductEntryId = endProductEntryId;
			endProductEntryId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexProductEntries(
			long companyId, long startProductEntryId, long endProductEntryId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SCProductEntry.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("productEntryId");

		dynamicQuery.add(property.ge(startProductEntryId));
		dynamicQuery.add(property.lt(endProductEntryId));

		addReindexCriteria(dynamicQuery, companyId);

		List<SCProductEntry> productEntries =
			SCProductEntryLocalServiceUtil.dynamicQuery(dynamicQuery);

		if (productEntries.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			productEntries.size());

		for (SCProductEntry productEntry : productEntries) {
			Document document = getDocument(productEntry);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}