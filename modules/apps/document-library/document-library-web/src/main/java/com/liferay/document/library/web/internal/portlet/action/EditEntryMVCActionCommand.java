/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.FileEntryLockException;
import com.liferay.document.library.kernel.exception.InvalidFolderException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.SourceFileNameException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.service.TrashEntryService;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.WindowState;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 * @author Manuel de la Peña
 * @author Levente Hudák
 */
@Component(
	property = {
		"jakarta.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"jakarta.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"jakarta.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_entry",
		"mvc.command.name=/document_library/move_entry"
	},
	service = MVCActionCommand.class
)
public class EditEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.CANCEL_CHECKOUT)) {
				_cancelCheckedOutEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKIN)) {
				_checkInEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKOUT)) {
				_checkOutEntries(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteEntries(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE)) {
				_moveEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				_deleteEntries(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				_restoreTrashEntries(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (windowState.equals(LiferayWindowState.POP_UP)) {
				String redirect = _portal.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
			}
		}
		catch (DuplicateLockException duplicateLockException) {
			SessionErrors.add(
				actionRequest, duplicateLockException.getClass(),
				duplicateLockException.getLock());

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
		catch (NoSuchFileEntryException | NoSuchFolderException |
			   PrincipalException exception) {

			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
		catch (DuplicateFileEntryException duplicateFileEntryException) {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(
				ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION);

			SessionErrors.add(
				actionRequest, duplicateFileEntryException.getClass(),
				duplicateFileEntryException);
		}
		catch (AssetCategoryException | AssetTagException |
			   DuplicateFolderNameException | FileEntryLockException |
			   InvalidFolderException | SourceFileNameException exception) {

			SessionErrors.add(actionRequest, exception.getClass(), exception);
		}
	}

	private SearchContext _getSearchContext(
			ActionRequest actionRequest, String keywords,
			long searchRepositoryId, long searchFolderId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContext searchContext = SearchContextFactory.getInstance(
			new long[0], new String[0], new HashMap<>(),
			themeDisplay.getCompanyId(), keywords, themeDisplay.getLayout(),
			themeDisplay.getLocale(), themeDisplay.getScopeGroupId(),
			themeDisplay.getTimeZone(), themeDisplay.getUserId());

		searchContext.setAttribute("paginationType", "regular");
		searchContext.setAttribute("searchRepositoryId", searchRepositoryId);
		searchContext.setAttribute("status", WorkflowConstants.STATUS_APPROVED);
		searchContext.setEnd(QueryUtil.ALL_POS);
		searchContext.setFolderIds(new long[] {searchFolderId});
		searchContext.setStart(QueryUtil.ALL_POS);

		Group group = _groupLocalService.fetchGroup(searchRepositoryId);

		if ((group != null) &&
			GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), group, ActionKeys.VIEW)) {

			searchContext.setGroupIds(new long[] {searchRepositoryId});
		}

		searchContext.setIncludeDiscussions(true);
		searchContext.setIncludeInternalAssetCategories(true);
		searchContext.setLocale(themeDisplay.getSiteDefaultLocale());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSearchSubfolders(true);

		return searchContext;
	}

	private void _cancelCheckedOutEntries(ActionRequest actionRequest)
		throws PortalException {

		long[] fileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.cancelCheckOut(fileEntryId);
		}
	}

	private void _checkInEntries(ActionRequest actionRequest)
		throws PortalException {

		BulkSelection<FileShortcut> fileShortcutBulkSelection =
			_fileShortcutBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		DLVersionNumberIncrease dlVersionNumberIncrease =
			DLVersionNumberIncrease.valueOf(
				actionRequest.getParameter("versionIncrease"),
				DLVersionNumberIncrease.MINOR);
		String changeLog = ParamUtil.getString(actionRequest, "changeLog");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		fileShortcutBulkSelection.forEach(
			fileShortcut -> _dlAppService.checkInFileEntry(
				fileShortcut.getToFileEntryId(), dlVersionNumberIncrease,
				changeLog, serviceContext));

		BulkSelection<FileEntry> fileEntryBulkSelection =
			_fileEntryBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		fileEntryBulkSelection.forEach(
			fileEntry -> _dlAppService.checkInFileEntry(
				fileEntry.getFileEntryId(), dlVersionNumberIncrease, changeLog,
				serviceContext));
	}

	private void _checkOutEntries(ActionRequest actionRequest)
		throws PortalException {

		BulkSelection<FileShortcut> fileShortcutBulkSelection =
			_fileShortcutBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		fileShortcutBulkSelection.forEach(
			fileShortcut -> _dlAppService.checkOutFileEntry(
				fileShortcut.getToFileEntryId(), serviceContext));

		BulkSelection<FileEntry> fileEntryBulkSelection =
			_fileEntryBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		fileEntryBulkSelection.forEach(
			fileEntry -> _dlAppService.checkOutFileEntry(
				fileEntry.getFileEntryId(), serviceContext));
	}

	private void _deleteEntries(
			ActionRequest actionRequest, boolean moveToTrash)
		throws PortalException {

		String keywords = ParamUtil.getString(actionRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			_deleteFileEntries(actionRequest, keywords, moveToTrash);
		}
		else {
			List<TrashedModel> trashedModels = new ArrayList<>();
			BulkSelection<Folder> folderBulkSelection =
				_folderBulkSelectionFactory.create(
					actionRequest.getParameterMap());

			folderBulkSelection.forEach(
				folder -> _deleteFolder(folder, moveToTrash, trashedModels));

			BulkSelection<FileShortcut> fileShortcutBulkSelection =
				_fileShortcutBulkSelectionFactory.create(
					actionRequest.getParameterMap());

			fileShortcutBulkSelection.forEach(
				fileShortcut -> _deleteFileShortcut(
					fileShortcut, moveToTrash, trashedModels));

			BulkSelection<FileEntry> fileEntryBulkSelection =
				_fileEntryBulkSelectionFactory.create(
					actionRequest.getParameterMap());

			fileEntryBulkSelection.forEach(
				fileEntry -> _deleteFileEntry(
					fileEntry, moveToTrash, trashedModels));

			if (moveToTrash && !trashedModels.isEmpty()) {
				addDeleteSuccessData(
					actionRequest,
					HashMapBuilder.<String, Object>put(
						"trashedModels", trashedModels
					).build());
			}
		}
	}

	private void _deleteFileEntry(
		FileEntry fileEntry, boolean moveToTrash,
		List<TrashedModel> trashedModels) {

		try {
			if (moveToTrash) {
				_dlTrashService.moveFileEntryToTrash(
					fileEntry.getFileEntryId());

				if (fileEntry.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)fileEntry.getModel());
				}
			}
			else {
				_dlAppService.deleteFileEntry(fileEntry.getFileEntryId());
			}
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}
	}

	private void _deleteFileShortcut(
		FileShortcut fileShortcut, boolean moveToTrash,
		List<TrashedModel> trashedModels) {

		try {
			if (moveToTrash) {
				fileShortcut = _dlTrashService.moveFileShortcutToTrash(
					fileShortcut.getFileShortcutId());

				if (fileShortcut.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)fileShortcut.getModel());
				}
			}
			else {
				_dlAppService.deleteFileShortcut(
					fileShortcut.getFileShortcutId());
			}
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}
	}

	private void _deleteFileEntries(
			ActionRequest actionRequest, String keywords, boolean moveToTrash)
		throws PortalException {

		Map<String, List<Long>> selectedMap = _getFilteredEntries(
			actionRequest, keywords);

		List<Long> folderIds = selectedMap.get("folderIds");
		List<Long> fileEntryIds = selectedMap.get("fileEntryIds");

		List<TrashedModel> trashedModels = new ArrayList<>();

		if (!folderIds.isEmpty()) {
			for (Long folderId : folderIds) {
				try{
					_deleteFolder(
						_dlAppService.getFolder(folderId), moveToTrash,
						trashedModels);
				}
				catch (Exception exception) {
					continue;
				}
			}
		}

		if (!fileEntryIds.isEmpty()) {
			for (Long fileEntryId : fileEntryIds) {
				try {
					_deleteFileEntry(
						_dlAppService.getFileEntry(fileEntryId), moveToTrash,
						trashedModels);
				}
				catch (Exception exception) {
					continue;
				}
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			addDeleteSuccessData(
				actionRequest,
				HashMapBuilder.<String, Object>put(
					"trashedModels", trashedModels
				).build());
		}
	}

	private void _deleteFolder(
		Folder folder, boolean moveToTrash, List<TrashedModel> trashedModels) {

		try {
			if (moveToTrash) {
				if (folder.isMountPoint()) {
					return;
				}

				folder = _dlTrashService.moveFolderToTrash(
					folder.getFolderId());

				if (folder.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)folder.getModel());
				}
			}
			else {
				_dlAppService.deleteFolder(folder.getFolderId());
			}
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}
	}

	private Map<String, List<Long>> _getFilteredEntries(
			ActionRequest actionRequest, String keywords)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest httpServletRequest =
			PortalUtil.getOriginalServletRequest(
				PortalUtil.getHttpServletRequest(actionRequest));

		long searchRepositoryId = ParamUtil.getLong(
			httpServletRequest, "searchRepositoryId",
			ParamUtil.getLong(
				httpServletRequest, "repositoryId",
				themeDisplay.getScopeGroupId()));

		long searchFolderId = _getSearchFolderId(httpServletRequest);

		SearchContext searchContext = _getSearchContext(
			actionRequest, keywords, searchRepositoryId, searchFolderId);

		Hits hits = _dlAppService.search(searchRepositoryId, searchContext);

		List<Long> fileEntryIds = new ArrayList<>();
		List<Long> folderIds = new ArrayList<>();

		if ((hits != null) && (hits.getLength() > 0)) {
			for (Document doc : hits.getDocs()) {
				String className = doc.get(Field.ENTRY_CLASS_NAME);
				long classPK = GetterUtil.getLong(
					doc.get(Field.ENTRY_CLASS_PK));

				if (Objects.equals(DLFileEntry.class.getName(), className)) {
					fileEntryIds.add(classPK);
				}
				else if (Objects.equals(DLFolder.class.getName(), className)) {
					folderIds.add(classPK);
				}
			}
		}

		return HashMapBuilder.<String, List<Long>>put(
			"fileEntryIds", fileEntryIds
		).put(
			"folderIds", folderIds
		).build();
	}

	private void _moveEntries(ActionRequest actionRequest)
		throws PortalException {

		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		BulkSelection<Folder> folderBulkSelection =
			_folderBulkSelectionFactory.create(actionRequest.getParameterMap());

		folderBulkSelection.forEach(
			folder -> _dlAppService.moveFolder(
				folder.getFolderId(), newFolderId, serviceContext));

		BulkSelection<FileEntry> fileEntryBulkSelection =
			_fileEntryBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		fileEntryBulkSelection.forEach(
			fileEntry -> _dlAppService.moveFileEntry(
				fileEntry.getFileEntryId(), newFolderId, serviceContext));

		BulkSelection<FileShortcut> fileShortcutBulkSelection =
			_fileShortcutBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		fileShortcutBulkSelection.forEach(
			fileShortcut -> _dlAppService.updateFileShortcut(
				fileShortcut.getFileShortcutId(), newFolderId,
				fileShortcut.getToFileEntryId(), serviceContext));
	}

	private long _getSearchFolderId(
			HttpServletRequest httpServletRequest) {

		long searchFolderId = ParamUtil.getLong(
			httpServletRequest, "searchFolderId",
			ParamUtil.getLong(
				httpServletRequest, "folderId",
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));

		long rootFolderId = ParamUtil.getLong(
			httpServletRequest, "rootFolderId",
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		if ((rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(searchFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			searchFolderId = rootFolderId;
		}

		return searchFolderId;
	}

	private void _restoreTrashEntries(ActionRequest actionRequest)
		throws PortalException {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLTrashService _dlTrashService;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileEntry)"
	)
	private BulkSelectionFactory<FileEntry> _fileEntryBulkSelectionFactory;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileShortcut)"
	)
	private BulkSelectionFactory<FileShortcut>
		_fileShortcutBulkSelectionFactory;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFolder)"
	)
	private BulkSelectionFactory<Folder> _folderBulkSelectionFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TrashEntryService _trashEntryService;

}