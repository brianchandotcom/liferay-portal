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

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.FolderIndexer;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author     Alexander Chow
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.document.library.internal.search.DLFolderIndexer}
 */
@Deprecated
public class DLFolderIndexer
	extends BaseIndexer<DLFolder> implements FolderIndexer {

	public static final String CLASS_NAME = DLFolder.class.getName();

	public DLFolderIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.DESCRIPTION, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String[] getFolderClassNames() {
		return new String[] {CLASS_NAME};
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return _dlFolderModelResourcePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		addStatus(contextBooleanFilter, searchContext);

		contextBooleanFilter.addRequiredTerm(Field.HIDDEN, false);
	}

	@Override
	protected void doDelete(DLFolder dlFolder) throws Exception {
		deleteDocument(dlFolder.getCompanyId(), dlFolder.getFolderId());
	}

	@Override
	protected Document doGetDocument(DLFolder dlFolder) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing folder " + dlFolder);
		}

		Document document = getBaseModelDocument(CLASS_NAME, dlFolder);

		document.addText(Field.DESCRIPTION, dlFolder.getDescription());
		document.addKeyword(Field.FOLDER_ID, dlFolder.getParentFolderId());
		document.addKeyword(
			Field.HIDDEN, dlFolder.isHidden() || dlFolder.isInHiddenFolder());

		String title = dlFolder.getName();

		if (dlFolder.isInTrash()) {
			title = TrashUtil.getOriginalTitle(title);
		}

		document.addText(Field.TITLE, title);

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
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(DLFolder dlFolder) throws Exception {
		if (!dlFolder.isApproved() && !dlFolder.isInTrash()) {
			return;
		}

		IndexWriterHelperUtil.updateDocument(
			getSearchEngineId(), dlFolder.getCompanyId(), getDocument(dlFolder),
			isCommitImmediately());
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

	protected void reindexFolders(final long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			DLFolderLocalServiceUtil.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("mountPoint");

				dynamicQuery.add(property.eq(false));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(DLFolder dlFolder) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(dlFolder));
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index document library folder " +
								dlFolder.getFolderId(),
							pe);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFolderIndexer.class);

	private static volatile ModelResourcePermission<DLFolder>
		_dlFolderModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, DLFolderIndexer.class,
				"_dlFolderModelResourcePermission",
				"(model.class.name=" + DLFolder.class.getName() + ")", true);

}