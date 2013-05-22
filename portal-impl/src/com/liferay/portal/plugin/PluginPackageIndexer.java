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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class PluginPackageIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {PluginPackage.class.getName()};

	public static final String PORTLET_ID = "PluginPackageIndexer";

	public PluginPackageIndexer() {
		setStagingAware(false);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		PluginPackage pluginPackage = (PluginPackage)obj;

		deleteDocument(CompanyConstants.SYSTEM, pluginPackage.getModuleId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		PluginPackage pluginPackage = (PluginPackage)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, pluginPackage.getModuleId());

		document.addKeyword(Field.COMPANY_ID, CompanyConstants.SYSTEM);

		StringBundler sb = new StringBundler(7);

		sb.append(pluginPackage.getAuthor());
		sb.append(StringPool.SPACE);

		String longDescription = HtmlUtil.extractText(
			pluginPackage.getLongDescription());

		sb.append(longDescription);

		sb.append(StringPool.SPACE);
		sb.append(pluginPackage.getName());
		sb.append(StringPool.SPACE);

		String shortDescription = HtmlUtil.extractText(
			pluginPackage.getShortDescription());

		sb.append(shortDescription);

		document.addText(Field.CONTENT, sb.toString());

		document.addKeyword(
			Field.ENTRY_CLASS_NAME, PluginPackage.class.getName());

		ModuleId moduleIdObj = ModuleId.getInstance(
			pluginPackage.getModuleId());

		document.addKeyword(Field.GROUP_ID, moduleIdObj.getGroupId());

		document.addDate(Field.MODIFIED_DATE, pluginPackage.getModifiedDate());
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);

		String[] statusAndInstalledVersion =
			PluginPackageUtil.getStatusAndInstalledVersion(pluginPackage);

		document.addKeyword(Field.STATUS, statusAndInstalledVersion[0]);

		document.addText(Field.TITLE, pluginPackage.getName());

		document.addKeyword("artifactId", moduleIdObj.getArtifactId());
		document.addText("author", pluginPackage.getAuthor());
		document.addText("changeLog", pluginPackage.getChangeLog());
		document.addKeyword("installedVersion", statusAndInstalledVersion[1]);

		List<License> licenses = pluginPackage.getLicenses();

		document.addKeyword(
			"license",
			StringUtil.split(
				ListUtil.toString(licenses, License.NAME_ACCESSOR)));

		document.addText("longDescription", longDescription);
		document.addKeyword("moduleId", pluginPackage.getModuleId());

		boolean osiLicense = false;

		for (int i = 0; i < licenses.size(); i++) {
			License license = licenses.get(i);

			if (license.isOsiApproved()) {
				osiLicense = true;

				break;
			}
		}

		document.addKeyword("osi-approved-license", osiLicense);
		document.addText("pageURL", pluginPackage.getPageURL());
		document.addKeyword("repositoryURL", pluginPackage.getRepositoryURL());
		document.addText("shortDescription", shortDescription);

		List<String> tags = pluginPackage.getTags();

		document.addKeyword("tag", tags.toArray(new String[tags.size()]));

		List<String> types = pluginPackage.getTypes();

		document.addKeyword("type", types.toArray(new String[types.size()]));

		document.addKeyword("version", pluginPackage.getVersion());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String moduleId = document.get("moduleId");
		String repositoryURL = document.get("repositoryURL");

		portletURL.setParameter("struts_action", "/admin/view");
		portletURL.setParameter("tabs2", "repositories");
		portletURL.setParameter("moduleId", moduleId);
		portletURL.setParameter("repositoryURL", repositoryURL);

		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		PluginPackage pluginPackage = (PluginPackage)obj;

		Document document = getDocument(pluginPackage);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), CompanyConstants.SYSTEM, document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		SearchEngineUtil.deletePortletDocuments(
			getSearchEngineId(), CompanyConstants.SYSTEM, PORTLET_ID);

		Collection<Document> documents = new ArrayList<Document>();

		for (PluginPackage pluginPackage :
				PluginPackageUtil.getAllAvailablePluginPackages()) {

			Document document = getDocument(pluginPackage);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), CompanyConstants.SYSTEM, documents);
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

		String tag = (String)searchContext.getAttribute("tag");

		if (Validator.isNotNull(tag)) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			searchQuery.addExactTerm("tag", tag);

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		String repositoryURL = (String)searchContext.getAttribute(
			"repositoryURL");

		if (Validator.isNotNull(repositoryURL)) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			Query query = TermQueryFactoryUtil.create(
				searchContext, "repositoryURL", repositoryURL);

			searchQuery.add(query, BooleanClauseOccur.SHOULD);

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		String license = (String)searchContext.getAttribute("license");

		if (Validator.isNotNull(license)) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			searchQuery.addExactTerm("license", license);

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		String status = (String)searchContext.getAttribute(Field.STATUS);

		if (Validator.isNull(status) || status.equals("all")) {
			return;
		}

		BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		if (status.equals(
				PluginPackageImpl.
					STATUS_NOT_INSTALLED_OR_OLDER_VERSION_INSTALLED)) {

			searchQuery.addExactTerm(
				Field.STATUS, PluginPackageImpl.STATUS_NOT_INSTALLED);
			searchQuery.addExactTerm(
				Field.STATUS, PluginPackageImpl.STATUS_OLDER_VERSION_INSTALLED);
		}
		else {
			searchQuery.addExactTerm(Field.STATUS, status);
		}

		fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
	}

}