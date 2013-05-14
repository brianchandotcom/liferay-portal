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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.asset.DLFileEntryAssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderActionableDynamicQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Alexander Chow
 */
public class DLFolderIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {DLFolder.class.getName()};

	public static final String PORTLET_ID = PortletKeys.DOCUMENT_LIBRARY;

	public DLFolderIndexer() {
		setFilterSearch(true);
		setPermissionAware(true);
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

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

		contextQuery.addRequiredTerm(Field.HIDDEN, false);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		DLFolder dlFolder = (DLFolder)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, dlFolder.getFolderId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), dlFolder.getCompanyId(),
			document.get(Field.UID));
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

		if (!dlFolder.isInTrash() && dlFolder.isInTrashContainer()) {
			DLFolder trashedFolder = dlFolder.getTrashContainer();

			if (trashedFolder != null) {
				addTrashFields(
					document, DLFolder.class.getName(),
					trashedFolder.getFolderId(), null, null,
					DLFileEntryAssetRendererFactory.TYPE);

				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_NAME, DLFolder.class.getName());
				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_PK, trashedFolder.getFolderId());
				document.addKeyword(
					Field.STATUS, WorkflowConstants.STATUS_IN_TRASH);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + dlFolder + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

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
				getSearchEngineId(), dlFolder.getCompanyId(), document);
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

	protected void reindexFolders(final long companyId)
		throws PortalException, SystemException {

		final Collection<Document> documents = new ArrayList<Document>();

		ActionableDynamicQuery actionableDynamicQuery =
			new DLFolderActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property property = PropertyFactoryUtil.forName("mountPoint");

				dynamicQuery.add(property.eq(false));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				DLFolder dlFolder = (DLFolder)object;

				Document document = getDocument(dlFolder);

				if (document != null) {
					documents.add(document);
				}
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	private static Log _log = LogFactoryUtil.getLog(DLFolderIndexer.class);

}