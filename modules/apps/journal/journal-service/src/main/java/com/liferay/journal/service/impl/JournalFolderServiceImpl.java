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

package com.liferay.journal.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.base.JournalFolderServiceBaseImpl;
import com.liferay.journal.service.persistence.JournalArticleFinder;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 */
@Component(
	property = {
		"json.web.service.context.name=journal",
		"json.web.service.context.path=JournalFolder"
	},
	service = AopService.class
)
public class JournalFolderServiceImpl extends JournalFolderServiceBaseImpl {

	@Override
	public JournalFolder addFolder(
			String externalReferenceCode, long groupId, long parentFolderId,
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_journalFolderModelResourcePermission, getPermissionChecker(),
			groupId, parentFolderId, ActionKeys.ADD_FOLDER);

		return journalFolderLocalService.addFolder(
			externalReferenceCode, getUserId(), groupId, parentFolderId, name,
			description, serviceContext);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		_journalFolderModelResourcePermission.check(
			getPermissionChecker(),
			journalFolderLocalService.getFolder(folderId), ActionKeys.DELETE);

		journalFolderLocalService.deleteFolder(folderId);
	}

	@Override
	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException {

		_journalFolderModelResourcePermission.check(
			getPermissionChecker(),
			journalFolderLocalService.getFolder(folderId), ActionKeys.DELETE);

		journalFolderLocalService.deleteFolder(folderId, includeTrashedEntries);
	}

	@Override
	public JournalFolder fetchFolder(long folderId) throws PortalException {
		JournalFolder folder = journalFolderLocalService.fetchFolder(folderId);

		if (folder != null) {
			_journalFolderModelResourcePermission.check(
				getPermissionChecker(), folder, ActionKeys.VIEW);
		}

		return folder;
	}

	@Override
	public List<DDMStructure> getDDMStructures(
			long[] groupIds, long folderId, int restrictionType)
		throws PortalException {

		return _filterStructures(
			journalFolderLocalService.getDDMStructures(
				groupIds, folderId, restrictionType));
	}

	@Override
	public List<DDMStructure> getDDMStructures(
			long[] groupIds, long folderId, int restrictionType,
			OrderByComparator<DDMStructure> orderByComparator)
		throws PortalException {

		return _filterStructures(
			journalFolderLocalService.getDDMStructures(
				groupIds, folderId, restrictionType, orderByComparator));
	}

	@Override
	public JournalFolder getFolder(long folderId) throws PortalException {
		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		_journalFolderModelResourcePermission.check(
			getPermissionChecker(), folder, ActionKeys.VIEW);

		return folder;
	}

	@Override
	public JournalFolder getFolderByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException {

		JournalFolder folder =
			journalFolderLocalService.getJournalFolderByExternalReferenceCode(
				externalReferenceCode, groupId);

		_journalFolderModelResourcePermission.check(
			getPermissionChecker(), folder, ActionKeys.VIEW);

		return folder;
	}

	@Override
	public List<Long> getFolderIds(long groupId, long folderId)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_journalFolderModelResourcePermission, getPermissionChecker(),
			groupId, folderId, ActionKeys.VIEW);

		List<Long> folderIds = getSubfolderIds(groupId, folderId, true);

		folderIds.add(0, folderId);

		return folderIds;
	}

	@Override
	public List<JournalFolder> getFolders(long groupId) {
		return journalFolderPersistence.filterFindByGroupId(groupId);
	}

	@Override
	public List<JournalFolder> getFolders(long groupId, long parentFolderId) {
		return getFolders(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<JournalFolder> getFolders(
		long groupId, long parentFolderId, int status) {

		return journalFolderPersistence.filterFindByG_P_S(
			groupId, parentFolderId, status);
	}

	@Override
	public List<JournalFolder> getFolders(
		long groupId, long parentFolderId, int start, int end) {

		return getFolders(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED, start,
			end);
	}

	@Override
	public List<JournalFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end) {

		return journalFolderPersistence.filterFindByG_P_S(
			groupId, parentFolderId, status, start, end);
	}

	@Override
	public List<Object> getFoldersAndArticles(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return getFoldersAndArticles(
			groupId, 0, folderId, status, start, end, orderByComparator);
	}

	@Override
	public List<Object> getFoldersAndArticles(
		long groupId, long folderId, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return getFoldersAndArticles(
			groupId, folderId, WorkflowConstants.STATUS_ANY, start, end,
			orderByComparator);
	}

	@Override
	public List<Object> getFoldersAndArticles(
		long groupId, long userId, long folderId, int status, int start,
		int end, OrderByComparator<?> orderByComparator) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, userId, true, start, end,
			(OrderByComparator<Object>)orderByComparator);

		return journalFolderFinder.filterFindF_A_ByG_F_DDMSI(
			groupId, folderId, 0, queryDefinition);
	}

	@Override
	public List<Object> getFoldersAndArticles(
		long groupId, long userId, long folderId, int status, Locale locale,
		int start, int end, OrderByComparator<?> orderByComparator) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, userId, true, start, end,
			(OrderByComparator<Object>)orderByComparator);

		return journalFolderFinder.filterFindF_A_ByG_F_DDMSI_L(
			groupId, folderId, 0, locale, queryDefinition);
	}

	@Override
	public List<Object> getFoldersAndArticles(
		long groupId, long userId, long folderId, long ddmStructureId,
		int status, Locale locale, int start, int end,
		OrderByComparator<?> orderByComparator) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, userId, true, start, end,
			(OrderByComparator<Object>)orderByComparator);

		return journalFolderFinder.filterFindF_A_ByG_F_DDMSI_L(
			groupId, folderId, ddmStructureId, locale, queryDefinition);
	}

	@Override
	public int getFoldersAndArticlesCount(
		long groupId, List<Long> folderIds, int status) {

		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			status);

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return _journalArticleFinder.filterCountByG_F(
				groupId, folderIds, queryDefinition);
		}

		int start = 0;
		int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

		int articlesCount = _journalArticleFinder.filterCountByG_F(
			groupId, folderIds.subList(start, end), queryDefinition);

		List<Long> sublist = folderIds.subList(start, end);

		sublist.clear();

		articlesCount += getFoldersAndArticlesCount(groupId, folderIds, status);

		return articlesCount;
	}

	@Override
	public int getFoldersAndArticlesCount(long groupId, long folderId) {
		return getFoldersAndArticlesCount(
			groupId, folderId, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public int getFoldersAndArticlesCount(
		long groupId, long folderId, int status) {

		return getFoldersAndArticlesCount(groupId, 0, folderId, status);
	}

	@Override
	public int getFoldersAndArticlesCount(
		long groupId, long userId, long folderId, int status) {

		QueryDefinition<Object> queryDefinition = new QueryDefinition<>(
			status, userId, true);

		return journalFolderFinder.filterCountF_A_ByG_F_DDMSI(
			groupId, folderId, 0, queryDefinition);
	}

	@Override
	public int getFoldersAndArticlesCount(
		long groupId, long userId, long folderId, long ddmStructureId,
		int status) {

		QueryDefinition<Object> queryDefinition = new QueryDefinition<>(
			status, userId, true);

		return journalFolderFinder.filterCountF_A_ByG_F_DDMSI(
			groupId, folderId, ddmStructureId, queryDefinition);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId) {
		return getFoldersCount(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return journalFolderPersistence.filterCountByG_P_NotS(
				groupId, parentFolderId, WorkflowConstants.STATUS_IN_TRASH);
		}

		return journalFolderPersistence.filterCountByG_P_S(
			groupId, parentFolderId, status);
	}

	@Override
	public void getSubfolderIds(
		List<Long> folderIds, long groupId, long folderId, boolean recurse) {

		List<JournalFolder> folders =
			journalFolderPersistence.filterFindByG_P_NotS(
				groupId, folderId, WorkflowConstants.STATUS_IN_TRASH);

		for (JournalFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			if (recurse) {
				getSubfolderIds(
					folderIds, folder.getGroupId(), folder.getFolderId(),
					recurse);
			}
		}
	}

	@Override
	public List<Long> getSubfolderIds(
		long groupId, long folderId, boolean recurse) {

		List<Long> folderIds = new ArrayList<>();

		getSubfolderIds(folderIds, groupId, folderId, recurse);

		return folderIds;
	}

	@Override
	public JournalFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException {

		_journalFolderModelResourcePermission.check(
			getPermissionChecker(),
			journalFolderLocalService.getFolder(folderId), ActionKeys.UPDATE);

		return journalFolderLocalService.moveFolder(
			folderId, parentFolderId, serviceContext);
	}

	@Override
	public JournalFolder moveFolderFromTrash(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException {

		_journalFolderModelResourcePermission.check(
			getPermissionChecker(),
			journalFolderLocalService.getFolder(folderId), ActionKeys.UPDATE);

		return journalFolderLocalService.moveFolderFromTrash(
			getUserId(), folderId, parentFolderId, serviceContext);
	}

	@Override
	public JournalFolder moveFolderToTrash(long folderId)
		throws PortalException {

		_journalFolderModelResourcePermission.check(
			getPermissionChecker(),
			journalFolderLocalService.getFolder(folderId), ActionKeys.DELETE);

		return journalFolderLocalService.moveFolderToTrash(
			getUserId(), folderId);
	}

	@Override
	public void restoreFolderFromTrash(long folderId) throws PortalException {
		_journalFolderModelResourcePermission.check(
			getPermissionChecker(),
			journalFolderLocalService.getFolder(folderId), ActionKeys.UPDATE);

		journalFolderLocalService.restoreFolderFromTrash(getUserId(), folderId);
	}

	@Override
	public List<DDMStructure> searchDDMStructures(
			long companyId, long[] groupIds, long folderId, int restrictionType,
			String keywords, int start, int end,
			OrderByComparator<DDMStructure> orderByComparator)
		throws PortalException {

		if (restrictionType ==
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW) {

			return _ddmStructureService.search(
				companyId, groupIds,
				_classNameLocalService.getClassNameId(JournalFolder.class),
				folderId, keywords, WorkflowConstants.STATUS_ANY, start, end,
				orderByComparator);
		}

		folderId = journalFolderLocalService.getOverridedDDMStructuresFolderId(
			folderId);

		if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return _ddmStructureService.search(
				companyId, groupIds,
				_classNameLocalService.getClassNameId(JournalFolder.class),
				folderId, keywords, WorkflowConstants.STATUS_ANY, start, end,
				orderByComparator);
		}

		return _ddmStructureService.search(
			companyId, groupIds,
			_classNameLocalService.getClassNameId(JournalArticle.class),
			keywords, WorkflowConstants.STATUS_ANY, start, end,
			orderByComparator);
	}

	@Override
	public int searchDDMStructuresCount(
			long companyId, long[] groupIds, long folderId, int restrictionType,
			String keywords)
		throws PortalException {

		if (restrictionType ==
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW) {

			return _ddmStructureService.searchCount(
				companyId, groupIds,
				_classNameLocalService.getClassNameId(JournalFolder.class),
				folderId, keywords, WorkflowConstants.STATUS_ANY);
		}

		folderId = journalFolderLocalService.getOverridedDDMStructuresFolderId(
			folderId);

		if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return _ddmStructureService.searchCount(
				companyId, groupIds,
				_classNameLocalService.getClassNameId(JournalFolder.class),
				folderId, keywords, WorkflowConstants.STATUS_ANY);
		}

		return _ddmStructureService.searchCount(
			companyId, groupIds,
			_classNameLocalService.getClassNameId(JournalArticle.class),
			keywords, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public void subscribe(long groupId, long folderId) throws PortalException {
		ModelResourcePermissionUtil.check(
			_journalFolderModelResourcePermission, getPermissionChecker(),
			groupId, folderId, ActionKeys.SUBSCRIBE);

		journalFolderLocalService.subscribe(getUserId(), groupId, folderId);
	}

	@Override
	public void unsubscribe(long groupId, long folderId)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_journalFolderModelResourcePermission, getPermissionChecker(),
			groupId, folderId, ActionKeys.SUBSCRIBE);

		journalFolderLocalService.unsubscribe(getUserId(), groupId, folderId);
	}

	@Override
	public JournalFolder updateFolder(
			long groupId, long folderId, long parentFolderId, String name,
			String description, boolean mergeWithParentFolder,
			ServiceContext serviceContext)
		throws PortalException {

		_journalFolderModelResourcePermission.check(
			getPermissionChecker(),
			journalFolderLocalService.getFolder(folderId), ActionKeys.UPDATE);

		return journalFolderLocalService.updateFolder(
			getUserId(), groupId, folderId, parentFolderId, name, description,
			mergeWithParentFolder, serviceContext);
	}

	@Override
	public JournalFolder updateFolder(
			long groupId, long folderId, long parentFolderId, String name,
			String description, long[] ddmStructureIds, int restrictionType,
			boolean mergeWithParentFolder, ServiceContext serviceContext)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_journalFolderModelResourcePermission, getPermissionChecker(),
			groupId, folderId, ActionKeys.UPDATE);

		return journalFolderLocalService.updateFolder(
			getUserId(), groupId, folderId, parentFolderId, name, description,
			ddmStructureIds, restrictionType, mergeWithParentFolder,
			serviceContext);
	}

	private List<DDMStructure> _filterStructures(
			List<DDMStructure> ddmStructures)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		ddmStructures = ListUtil.copy(ddmStructures);

		Iterator<DDMStructure> iterator = ddmStructures.iterator();

		while (iterator.hasNext()) {
			DDMStructure ddmStructure = iterator.next();

			if (!_ddmStructureModelResourcePermission.contains(
					permissionChecker, ddmStructure, ActionKeys.VIEW)) {

				iterator.remove();
			}
		}

		return ddmStructures;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure)"
	)
	private ModelResourcePermission<DDMStructure>
		_ddmStructureModelResourcePermission;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private JournalArticleFinder _journalArticleFinder;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFolder)"
	)
	private ModelResourcePermission<JournalFolder>
		_journalFolderModelResourcePermission;

}