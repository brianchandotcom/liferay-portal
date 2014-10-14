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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.FolderIndexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Alexander Chow
 */
public class DLFolderIndexer extends BaseIndexer implements FolderIndexer {

	public static final String[] CLASS_NAMES = {DLFolder.class.getName()};

	public static final String PORTLET_ID = PortletKeys.DOCUMENT_LIBRARY;

	public DLFolderIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.DESCRIPTION, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String[] getFolderClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(entryClassPK);

		return DLFolderPermission.contains(
			permissionChecker, dlFolder, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		addStatus(contextQuery, searchContext);

		contextQuery.addRequiredTerm(Field.HIDDEN, false);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		DLFolder dlFolder = (DLFolder)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, dlFolder.getFolderId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), dlFolder.getCompanyId(),
			document.get(Field.UID), isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		DLFolder dlFolder = (DLFolder)obj;

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing folder " + dlFolder);
		}

		Document document = getBaseModelDocument(PORTLET_ID, dlFolder);

		document.addText(Field.DESCRIPTION, dlFolder.getDescription());
		document.addKeyword(Field.FOLDER_ID, dlFolder.getParentFolderId());
		document.addKeyword(
			Field.HIDDEN, (dlFolder.isHidden() || dlFolder.isInHiddenFolder()));
		document.addText(Field.TITLE, dlFolder.getName());
		document.addKeyword(Field.TREE_PATH, dlFolder.getTreePath());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(dlFolder.getTreePath(), CharPool.SLASH));

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + dlFolder + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet, PortletURL portletURL,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		liferayPortletURL.setLifecycle(PortletRequest.ACTION_PHASE);

		try {
			liferayPortletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException wse) {
		}

		String folderId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/document_library/view");
		portletURL.setParameter("folderId", folderId);

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		DLFolder dlFolder = (DLFolder)obj;

		if (!dlFolder.isApproved() && !dlFolder.isInTrash()) {
			return;
		}

		Document document = getDocument(dlFolder);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), dlFolder.getCompanyId(), document,
				isCommitImmediately());
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(classPK);

		doReindex(dlFolder);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFolders(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexFolders(final long companyId) throws PortalException {
		final ActionableDynamicQuery actionableDynamicQuery =
			DLFolderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName(
						"mountPoint");

					dynamicQuery.add(property.eq(false));
				}

			});
		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DLFolder dlFolder = (DLFolder)object;

					Document document = getDocument(dlFolder);

					if (document != null) {
						actionableDynamicQuery.addDocument(document);
					}
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFolderIndexer.class);

}