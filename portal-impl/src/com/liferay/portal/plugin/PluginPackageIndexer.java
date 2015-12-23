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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
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

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
@OSGiBeanProperties
public class PluginPackageIndexer extends BaseIndexer<PluginPackage> {

	public static final String CLASS_NAME = PluginPackage.class.getName();

	public PluginPackageIndexer() {
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
	protected void doDelete(PluginPackage pluginPackage) throws Exception {
		deleteDocument(CompanyConstants.SYSTEM, pluginPackage.getModuleId());
	}

	@Override
	protected Document doGetDocument(PluginPackage pluginPackage)
		throws Exception {

		Document document = new DocumentImpl();

		document.addUID(CLASS_NAME, pluginPackage.getModuleId());

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
			ListUtil.toArray(licenses, License.NAME_ACCESSOR));

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
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		return new Summary(title, content);
	}

	@Override
	protected void doReindex(PluginPackage pluginPackage) throws Exception {
		Document document = getDocument(pluginPackage);

		IndexWriterHelperUtil.updateDocument(
			getSearchEngineId(), CompanyConstants.SYSTEM, document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		IndexWriterHelperUtil.deleteEntityDocuments(
			getSearchEngineId(), CompanyConstants.SYSTEM, CLASS_NAME,
			isCommitImmediately());

		Collection<Document> documents = new ArrayList<>();

		List<PluginPackage> pluginPackages =
			PluginPackageUtil.getAllAvailablePluginPackages();

		int total = pluginPackages.size();

		ReindexStatusMessageSenderUtil.sendStatusMessage(
			getClassName(), 0, total);

		for (PluginPackage pluginPackage : pluginPackages) {
			Document document = getDocument(pluginPackage);

			documents.add(document);
		}

		IndexWriterHelperUtil.updateDocuments(
			getSearchEngineId(), CompanyConstants.SYSTEM, documents,
			isCommitImmediately());

		ReindexStatusMessageSenderUtil.sendStatusMessage(
			getClassName(), total, total);
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

		String tag = (String)searchContext.getAttribute("tag");

		if (Validator.isNotNull(tag)) {
			booleanFilter.addRequiredTerm("tag", tag);
		}

		String repositoryURL = (String)searchContext.getAttribute(
			"repositoryURL");

		if (Validator.isNotNull(repositoryURL)) {
			booleanFilter.addRequiredTerm("repositoryURL", repositoryURL);
		}

		String license = (String)searchContext.getAttribute("license");

		if (Validator.isNotNull(license)) {
			booleanFilter.addRequiredTerm("license", license);
		}

		String status = (String)searchContext.getAttribute(Field.STATUS);

		if (Validator.isNull(status) || status.equals("all")) {
			return;
		}

		if (status.equals(
				PluginPackageImpl.
					STATUS_NOT_INSTALLED_OR_OLDER_VERSION_INSTALLED)) {

			BooleanFilter statusBooleanFilter = new BooleanFilter();

			statusBooleanFilter.addTerm(
				Field.STATUS, PluginPackageImpl.STATUS_NOT_INSTALLED);
			statusBooleanFilter.addTerm(
				Field.STATUS, PluginPackageImpl.STATUS_OLDER_VERSION_INSTALLED);

			booleanFilter.add(statusBooleanFilter, BooleanClauseOccur.MUST);
		}
		else {
			booleanFilter.addRequiredTerm(Field.STATUS, status);
		}

		if (booleanFilter.hasClauses()) {
			fullQuery.setPreBooleanFilter(booleanFilter);
		}
	}

}