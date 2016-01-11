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

package com.liferay.journal.trash;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.journal.service.permission.JournalFolderPermission;
import com.liferay.journal.util.impl.JournalUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.trash.RestoreEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashEntryConstants;

import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements trash handling for the journal article entity.
 *
 * @author Levente Hudák
 * @author Sergio González
 * @author Zsolt Berentey
 */
@Component(
	property = {"model.class.name=com.liferay.journal.model.JournalArticle"},
	service = TrashHandler.class
)
public class JournalArticleTrashHandler extends JournalBaseTrashHandler {

	@Override
	public void checkRestorableEntry(
			long classPK, long containerModelId, String newName)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		checkRestorableEntry(
			classPK, 0, containerModelId, article.getArticleId(), newName);
	}

	@Override
	public void checkRestorableEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException {

		checkRestorableEntry(
			trashEntry.getClassPK(), trashEntry.getEntryId(), containerModelId,
			trashEntry.getTypeSettingsProperty("title"), newName);
	}

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		_journalArticleLocalService.deleteArticle(
			article.getGroupId(), article.getArticleId(), null);
	}

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public Filter getExcludeFilter(SearchContext searchContext) {
		BooleanFilter excludeBooleanFilter = new BooleanFilter();

		excludeBooleanFilter.addRequiredTerm(
			Field.ENTRY_CLASS_NAME, JournalArticle.class.getName());
		excludeBooleanFilter.addRequiredTerm("head", false);

		return excludeBooleanFilter;
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		long parentFolderId = article.getFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public ContainerModel getParentContainerModel(TrashedModel trashedModel)
		throws PortalException {

		JournalArticle article = (JournalArticle)trashedModel;

		return getContainerModel(article.getFolderId());
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		return JournalUtil.getJournalControlPanelLink(
			portletRequest, article.getFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		return JournalUtil.getAbsolutePath(
			portletRequest, article.getFolderId());
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		return article.getTrashEntry();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		return (TrashRenderer)assetRendererFactory.getAssetRenderer(
			article.getId());
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return JournalFolderPermission.contains(
				permissionChecker, groupId, classPK, ActionKeys.ADD_ARTICLE);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		return article.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK) throws PortalException {
		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		return article.isInTrashContainer();
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		if ((article.getFolderId() > 0) &&
			(_journalFolderLocalService.fetchFolder(
				article.getFolderId()) == null)) {

			return false;
		}

		return !article.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		_journalArticleLocalService.moveArticle(
			article.getGroupId(), article.getArticleId(), containerModelId,
			serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerId,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		_journalArticleLocalService.moveArticleFromTrash(
			userId, article.getGroupId(), article, containerId, serviceContext);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		_journalArticleLocalService.restoreArticleFromTrash(userId, article);
	}

	@Override
	public void updateTitle(long classPK, String name) throws PortalException {
		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		article.setArticleId(name);

		_journalArticleLocalService.updateJournalArticle(article);

		JournalArticleResource articleResource =
			_journalArticleResourceLocalService.getArticleResource(
				article.getResourcePrimKey());

		articleResource.setArticleId(name);

		_journalArticleResourceLocalService.updateJournalArticleResource(
			articleResource);
	}

	protected void checkDuplicateEntry(
			long classPK, long trashEntryId, String originalTitle,
			String newName)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		JournalArticleResource journalArticleResource =
			article.getArticleResource();

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		JournalArticleResource originalArticleResource =
			_journalArticleResourceLocalService.fetchArticleResource(
				article.getGroupId(), originalTitle);

		if ((originalArticleResource != null) &&
			(journalArticleResource.getPrimaryKey() !=
				originalArticleResource.getPrimaryKey())) {

			RestoreEntryException ree = new RestoreEntryException(
				RestoreEntryException.DUPLICATE);

			JournalArticle duplicateArticle =
				_journalArticleLocalService.getArticle(
					originalArticleResource.getGroupId(), originalTitle);

			ree.setDuplicateEntryId(duplicateArticle.getResourcePrimKey());
			ree.setOldName(duplicateArticle.getArticleId());
			ree.setTrashEntryId(trashEntryId);

			throw ree;
		}
	}

	protected void checkRestorableEntry(
			long classPK, long trashEntryId, long containerModelId,
			String originalTitle, String newName)
		throws PortalException {

		checkValidContainer(classPK, containerModelId);

		checkDuplicateEntry(classPK, trashEntryId, originalTitle, newName);
	}

	protected void checkValidContainer(long classPK, long containerModelId)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			PortalUtil.getSiteGroupId(article.getGroupId()),
			PortalUtil.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		if (containerModelId == TrashEntryConstants.DEFAULT_CONTAINER_ID) {
			containerModelId = article.getFolderId();
		}

		int restrictionType = JournalUtil.getRestrictionType(containerModelId);

		List<DDMStructure> folderDDMStructures =
			_journalFolderLocalService.getDDMStructures(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					article.getGroupId()),
				containerModelId, restrictionType);

		for (DDMStructure folderDDMStructure : folderDDMStructures) {
			if (folderDDMStructure.getStructureId() ==
					ddmStructure.getStructureId()) {

				return;
			}
		}

		throw new RestoreEntryException(
			RestoreEntryException.INVALID_CONTAINER);
	}

	@Override
	protected long getGroupId(long classPK) throws PortalException {
		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			classPK);

		return article.getGroupId();
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return JournalArticlePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private DDMStructureLocalService _ddmStructureLocalService;
	private JournalArticleLocalService _journalArticleLocalService;
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;
	private JournalFolderLocalService _journalFolderLocalService;

}