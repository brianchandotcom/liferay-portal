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

package com.liferay.document.library.web.internal.webdav;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.document.library.kernel.util.DL;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.InvalidLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.NoSuchLockException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.Status;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.webdav.LockException;
import com.liferay.portlet.documentlibrary.webdav.DLFileEntryResourceImpl;
import com.liferay.portlet.documentlibrary.webdav.DLWebDAVUtil;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"webdav.storage.token=document_library"
	},
	service = WebDAVStorage.class
)
public class DLWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	public static final String MS_OFFICE_2010_TEXT_XML_UTF8 =
		"text/xml; charset=\"utf-8\"";

	@Override
	public int copyCollectionResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite, long depth)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			long parentFolderId = getParentFolderId(
				webDAVRequest.getCompanyId(), destinationArray);

			Folder folder = (Folder)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(
				webDAVRequest.getCompanyId(), destination);
			String name = WebDAVUtil.getResourceName(destinationArray);
			String description = folder.getDescription();

			ServiceContext serviceContext = _getServiceContext(
				DLFolder.class.getName(), webDAVRequest);

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite &&
				deleteResource(
					groupId, parentFolderId, name,
					webDAVRequest.getLockUuid())) {

				status = HttpServletResponse.SC_NO_CONTENT;
			}

			if (depth == 0) {
				_dlAppService.addFolder(
					groupId, parentFolderId, name, description, serviceContext);
			}
			else {
				_dlAppService.copyFolder(
					groupId, folder.getFolderId(), parentFolderId, name,
					description, serviceContext);
			}

			return status;
		}
		catch (DuplicateFolderNameException dfne) {
			if (_log.isDebugEnabled()) {
				_log.debug(dfne, dfne);
			}

			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			return HttpServletResponse.SC_CONFLICT;
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public int copySimpleResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		File file = null;

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			long parentFolderId = getParentFolderId(
				webDAVRequest.getCompanyId(), destinationArray);

			long groupId = WebDAVUtil.getGroupId(
				webDAVRequest.getCompanyId(), destination);
			String title = getTitle(destinationArray);

			FileEntry fileEntry = (FileEntry)resource.getModel();

			InputStream is = fileEntry.getContentStream();

			file = FileUtil.createTempFile(is);

			ServiceContext serviceContext = _getServiceContext(
				DLFileEntry.class.getName(), webDAVRequest);

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite &&
				deleteResource(
					groupId, parentFolderId, title,
					webDAVRequest.getLockUuid())) {

				status = HttpServletResponse.SC_NO_CONTENT;
			}

			_dlAppService.addFileEntry(
				groupId, parentFolderId, title, fileEntry.getMimeType(), title,
				fileEntry.getDescription(), StringPool.BLANK, file,
				serviceContext);

			return status;
		}
		catch (DuplicateFileEntryException | DuplicateFolderNameException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (LockException le) {
			if (_log.isDebugEnabled()) {
				_log.debug(le, le);
			}

			return WebDAVUtil.SC_LOCKED;
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			return HttpServletResponse.SC_CONFLICT;
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public int deleteResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		try {
			Resource resource = getResource(webDAVRequest);

			if (resource == null) {
				if (webDAVRequest.isAppleDoubleRequest()) {
					return HttpServletResponse.SC_NO_CONTENT;
				}

				return HttpServletResponse.SC_NOT_FOUND;
			}

			Object model = resource.getModel();

			if (model instanceof Folder) {
				Folder folder = (Folder)model;

				if (folder.isRepositoryCapabilityProvided(
						TrashCapability.class) &&
					_dlTrashUtil.isTrashEnabled(
						folder.getGroupId(), folder.getRepositoryId())) {

					_dlTrashService.moveFolderToTrash(folder.getFolderId());
				}
				else {
					_dlAppService.deleteFolder(folder.getFolderId());
				}
			}
			else {
				FileEntry fileEntry = (FileEntry)model;

				if (!hasLock(fileEntry, webDAVRequest.getLockUuid()) &&
					(fileEntry.getLock() != null)) {

					return WebDAVUtil.SC_LOCKED;
				}

				if (fileEntry.isRepositoryCapabilityProvided(
						TrashCapability.class) &&
					_dlTrashUtil.isTrashEnabled(
						fileEntry.getGroupId(), fileEntry.getRepositoryId())) {

					_dlTrashService.moveFileEntryToTrash(
						fileEntry.getFileEntryId());
				}
				else {
					_dlAppService.deleteFileEntry(fileEntry.getFileEntryId());
				}
			}

			return HttpServletResponse.SC_NO_CONTENT;
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public Resource getResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDAVRequest.getPathArray();

			long parentFolderId = getParentFolderId(
				webDAVRequest.getCompanyId(), pathArray);

			String name = WebDAVUtil.getResourceName(pathArray);

			if (Validator.isNull(name)) {
				String path = getRootPath() + webDAVRequest.getPath();

				return new BaseResourceImpl(path, StringPool.BLANK, getToken());
			}

			try {
				Folder folder = _dlAppService.getFolder(
					webDAVRequest.getGroupId(), parentFolderId, name);

				if ((folder.getParentFolderId() != parentFolderId) ||
					(webDAVRequest.getGroupId() != folder.getRepositoryId())) {

					StringBundler sb = new StringBundler(6);

					sb.append("No DLFolder exists with the key ");
					sb.append("{parendFolderId=");
					sb.append(parentFolderId);
					sb.append(", repositoryId=");
					sb.append(webDAVRequest.getGroupId());
					sb.append(StringPool.CLOSE_CURLY_BRACE);

					throw new NoSuchFolderException(sb.toString());
				}

				return toResource(webDAVRequest, folder, false);
			}
			catch (NoSuchFolderException nsfe) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsfe, nsfe);
				}

				try {
					FileEntry fileEntry = _dlAppService.getFileEntry(
						webDAVRequest.getGroupId(), parentFolderId,
						getTitle(pathArray));

					return toResource(webDAVRequest, fileEntry, false);
				}
				catch (NoSuchFileEntryException nsfee) {
					if (_log.isDebugEnabled()) {
						_log.debug(nsfee, nsfee);
					}

					return null;
				}
			}
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public List<Resource> getResources(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		try {
			long folderId = getFolderId(
				webDAVRequest.getCompanyId(), webDAVRequest.getPathArray());

			List<Resource> folders = getFolders(webDAVRequest, folderId);
			List<Resource> fileEntries = getFileEntries(
				webDAVRequest, folderId);

			List<Resource> resources = new ArrayList<>(
				folders.size() + fileEntries.size());

			resources.addAll(folders);
			resources.addAll(fileEntries);

			return resources;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public boolean isSupportsClassTwo() {
		return true;
	}

	@Override
	public Status lockResource(
			WebDAVRequest webDAVRequest, String owner, long timeout)
		throws WebDAVException {

		Resource resource = getResource(webDAVRequest);

		Lock lock = null;
		int status = HttpServletResponse.SC_OK;

		try {
			if (resource == null) {
				status = HttpServletResponse.SC_CREATED;

				String[] pathArray = webDAVRequest.getPathArray();

				long parentFolderId = getParentFolderId(
					webDAVRequest.getCompanyId(), pathArray);

				String title = getTitle(pathArray);

				String contentType = getContentType(
					webDAVRequest.getHttpServletRequest(), null, title);

				File file = FileUtil.createTempFile(
					FileUtil.getExtension(title));

				file.createNewFile();

				ServiceContext serviceContext = _getServiceContext(
					DLFileEntry.class.getName(), webDAVRequest);

				FileEntry fileEntry = _dlAppService.addFileEntry(
					webDAVRequest.getGroupId(), parentFolderId, title,
					contentType, title, StringPool.BLANK, StringPool.BLANK,
					file, serviceContext);

				resource = toResource(webDAVRequest, fileEntry, false);
			}

			if (!resource.isCollection()) {
				FileEntry fileEntry = (FileEntry)resource.getModel();

				ServiceContext serviceContext = _getServiceContext(
					DLFileEntry.class.getName(), webDAVRequest);

				serviceContext.setAttribute(
					DL.MANUAL_CHECK_IN_REQUIRED,
					webDAVRequest.isManualCheckInRequired());

				populateServiceContext(serviceContext, fileEntry);

				_dlAppService.checkOutFileEntry(
					fileEntry.getFileEntryId(), owner, timeout, serviceContext);

				lock = fileEntry.getLock();
			}
			else {
				boolean inheritable = false;

				long depth = WebDAVUtil.getDepth(
					webDAVRequest.getHttpServletRequest());

				if (depth != 0) {
					inheritable = true;
				}

				Folder folder = (Folder)resource.getModel();

				lock = _dlAppService.lockFolder(
					folder.getRepositoryId(), folder.getFolderId(), owner,
					inheritable, timeout);
			}
		}
		catch (Exception e) {

			// DuplicateLock is 423 not 501

			if (!(e instanceof DuplicateLockException)) {
				throw new WebDAVException(e);
			}

			status = WebDAVUtil.SC_LOCKED;
		}

		return new Status(lock, status);
	}

	@Override
	public Status makeCollection(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		try {
			HttpServletRequest httpServletRequest =
				webDAVRequest.getHttpServletRequest();

			if (httpServletRequest.getContentLength() > 0) {
				return new Status(
					HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			}

			String[] pathArray = webDAVRequest.getPathArray();

			long parentFolderId = getParentFolderId(
				webDAVRequest.getCompanyId(), pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);

			ServiceContext serviceContext = _getServiceContext(
				DLFolder.class.getName(), webDAVRequest);

			_dlAppService.addFolder(
				webDAVRequest.getGroupId(), parentFolderId, name,
				StringPool.BLANK, serviceContext);

			String location = StringUtil.merge(pathArray, StringPool.SLASH);

			return new Status(location, HttpServletResponse.SC_CREATED);
		}
		catch (DuplicateFileEntryException | DuplicateFolderNameException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return new Status(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			return new Status(HttpServletResponse.SC_CONFLICT);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return new Status(HttpServletResponse.SC_FORBIDDEN);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public int moveCollectionResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			Folder folder = (Folder)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(
				webDAVRequest.getCompanyId(), destinationArray);

			long parentFolderId = getParentFolderId(
				webDAVRequest.getCompanyId(), destinationArray);
			String name = WebDAVUtil.getResourceName(destinationArray);

			ServiceContext serviceContext = _getServiceContext(
				DLFolder.class.getName(), webDAVRequest);

			serviceContext.setUserId(webDAVRequest.getUserId());

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite &&
				deleteResource(
					groupId, parentFolderId, name,
					webDAVRequest.getLockUuid())) {

				status = HttpServletResponse.SC_NO_CONTENT;
			}

			if (parentFolderId != folder.getParentFolderId()) {
				_dlAppService.moveFolder(
					folder.getFolderId(), parentFolderId, serviceContext);
			}

			if (!name.equals(folder.getName())) {
				_dlAppService.updateFolder(
					folder.getFolderId(), name, folder.getDescription(),
					serviceContext);
			}

			return status;
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (DuplicateFolderNameException dfne) {
			if (_log.isDebugEnabled()) {
				_log.debug(dfne, dfne);
			}

			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public int moveSimpleResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		File file = null;

		try {
			FileEntry fileEntry = (FileEntry)resource.getModel();

			if (!hasLock(fileEntry, webDAVRequest.getLockUuid()) &&
				(fileEntry.getLock() != null)) {

				return WebDAVUtil.SC_LOCKED;
			}

			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			long groupId = WebDAVUtil.getGroupId(
				webDAVRequest.getCompanyId(), destinationArray);
			long newParentFolderId = getParentFolderId(
				webDAVRequest.getCompanyId(), destinationArray);
			String title = getTitle(destinationArray);

			ServiceContext serviceContext = _getServiceContext(
				DLFileEntry.class.getName(), webDAVRequest);

			populateServiceContext(serviceContext, fileEntry);

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite &&
				deleteResource(
					groupId, newParentFolderId, title,
					webDAVRequest.getLockUuid())) {

				status = HttpServletResponse.SC_NO_CONTENT;
			}

			// LPS-5415

			if (webDAVRequest.isMac()) {
				try {
					FileEntry destFileEntry = _dlAppService.getFileEntry(
						groupId, newParentFolderId, title);

					InputStream is = fileEntry.getContentStream();

					file = FileUtil.createTempFile(is);

					populateServiceContext(serviceContext, destFileEntry);

					_dlAppService.updateFileEntry(
						destFileEntry.getFileEntryId(),
						destFileEntry.getTitle(), destFileEntry.getMimeType(),
						destFileEntry.getTitle(),
						destFileEntry.getDescription(), StringPool.BLANK,
						DLVersionNumberIncrease.MINOR, file, serviceContext);

					_dlAppService.deleteFileEntry(fileEntry.getFileEntryId());

					return status;
				}
				catch (NoSuchFileEntryException nsfee) {
					if (_log.isDebugEnabled()) {
						_log.debug(nsfee, nsfee);
					}
				}
			}

			populateServiceContext(serviceContext, fileEntry);

			_dlAppService.updateFileEntry(
				fileEntry.getFileEntryId(), title, fileEntry.getMimeType(),
				title, fileEntry.getDescription(), StringPool.BLANK,
				DLVersionNumberIncrease.MINOR, file, serviceContext);

			if (fileEntry.getFolderId() != newParentFolderId) {
				_dlAppService.moveFileEntry(
					fileEntry.getFileEntryId(), newParentFolderId,
					serviceContext);
			}

			return status;
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (DuplicateFileEntryException | DuplicateFolderNameException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (LockException le) {
			if (_log.isDebugEnabled()) {
				_log.debug(le, le);
			}

			return WebDAVUtil.SC_LOCKED;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public int putResource(WebDAVRequest webDAVRequest) throws WebDAVException {
		File file = null;

		try {
			HttpServletRequest httpServletRequest =
				webDAVRequest.getHttpServletRequest();

			String[] pathArray = webDAVRequest.getPathArray();

			long parentFolderId = getParentFolderId(
				webDAVRequest.getCompanyId(), pathArray);
			String title = getTitle(pathArray);

			ServiceContext serviceContext = _getServiceContext(
				DLFileEntry.class.getName(), webDAVRequest);

			file = FileUtil.createTempFile(FileUtil.getExtension(title));

			FileUtil.write(file, httpServletRequest.getInputStream());

			String contentType = getContentType(
				httpServletRequest, file, title);

			try {
				FileEntry fileEntry = _dlAppService.getFileEntry(
					webDAVRequest.getGroupId(), parentFolderId, title);

				if (!hasLock(fileEntry, webDAVRequest.getLockUuid()) &&
					(fileEntry.getLock() != null)) {

					return WebDAVUtil.SC_LOCKED;
				}

				populateServiceContext(serviceContext, fileEntry);

				serviceContext.setCommand(Constants.UPDATE_WEBDAV);

				_dlAppService.updateFileEntry(
					fileEntry.getFileEntryId(), title, contentType, title,
					fileEntry.getDescription(), StringPool.BLANK,
					DLVersionNumberIncrease.MINOR, file, serviceContext);
			}
			catch (NoSuchFileEntryException nsfee) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsfee, nsfee);
				}

				serviceContext.setCommand(Constants.ADD_WEBDAV);

				_dlAppService.addFileEntry(
					webDAVRequest.getGroupId(), parentFolderId, title,
					contentType, title, StringPool.BLANK, StringPool.BLANK,
					file, serviceContext);
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Added " + StringUtil.merge(pathArray, StringPool.SLASH));
			}

			return HttpServletResponse.SC_CREATED;
		}
		catch (FileSizeException fse) {
			if (_log.isDebugEnabled()) {
				_log.debug(fse, fse);
			}

			return HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE;
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			return HttpServletResponse.SC_CONFLICT;
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return HttpServletResponse.SC_CONFLICT;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public Lock refreshResourceLock(
			WebDAVRequest webDAVRequest, String uuid, long timeout)
		throws WebDAVException {

		try {
			Resource resource = getResource(webDAVRequest);

			if (!resource.isCollection()) {
				return _dlAppService.refreshFileEntryLock(
					uuid, webDAVRequest.getCompanyId(), timeout);
			}

			return _dlAppService.refreshFolderLock(
				uuid, webDAVRequest.getCompanyId(), timeout);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public boolean unlockResource(WebDAVRequest webDAVRequest, String token)
		throws WebDAVException {

		Resource resource = getResource(webDAVRequest);

		try {
			if (!resource.isCollection()) {
				FileEntry fileEntry = (FileEntry)resource.getModel();

				// Do not allow WebDAV to check in a file entry if it requires a
				// manual check in

				if (fileEntry.isManualCheckInRequired()) {
					return false;
				}

				ServiceContext serviceContext = _getServiceContext(
					DLFileEntry.class.getName(), webDAVRequest);

				serviceContext.setAttribute(
					DL.WEBDAV_CHECK_IN_MODE, Boolean.TRUE);

				_dlAppService.checkInFileEntry(
					fileEntry.getFileEntryId(), token, serviceContext);

				if (webDAVRequest.isAppleDoubleRequest()) {
					_dlAppService.deleteFileEntry(fileEntry.getFileEntryId());
				}
			}
			else {
				Folder folder = (Folder)resource.getModel();

				_dlAppService.unlockFolder(
					folder.getRepositoryId(), folder.getParentFolderId(),
					folder.getName(), token);
			}

			return true;
		}
		catch (InvalidLockException ile) {
			if (_log.isWarnEnabled()) {
				_log.warn(ile.getMessage());
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to unlock file entry", e);
			}
		}

		return false;
	}

	protected boolean deleteResource(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws Exception {

		try {
			Folder folder = _dlAppService.getFolder(
				groupId, parentFolderId, name);

			_dlAppService.deleteFolder(folder.getFolderId());

			return true;
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			try {
				FileEntry fileEntry = _dlAppService.getFileEntry(
					groupId, parentFolderId, name);

				if (!hasLock(fileEntry, lockUuid) &&
					(fileEntry.getLock() != null)) {

					StringBundler sb = new StringBundler(4);

					sb.append("Inconsistent file lock state for file entry ");
					sb.append(fileEntry.getPrimaryKey());
					sb.append(" and lock UUID ");
					sb.append(lockUuid);

					throw new LockException(sb.toString());
				}

				_dlAppService.deleteFileEntryByTitle(
					groupId, parentFolderId, name);

				return true;
			}
			catch (NoSuchFileEntryException nsfee) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsfee, nsfee);
				}
			}
		}

		return false;
	}

	protected String getContentType(
		HttpServletRequest httpServletRequest, File file, String title) {

		String contentType = GetterUtil.getString(
			httpServletRequest.getHeader(HttpHeaders.CONTENT_TYPE),
			ContentTypes.APPLICATION_OCTET_STREAM);

		if (contentType.equals(ContentTypes.APPLICATION_OCTET_STREAM) ||
			contentType.equals(MS_OFFICE_2010_TEXT_XML_UTF8)) {

			contentType = MimeTypesUtil.getContentType(file, title);
		}

		return contentType;
	}

	protected List<Resource> getFileEntries(
			WebDAVRequest webDAVRequest, long parentFolderId)
		throws Exception {

		List<Resource> resources = new ArrayList<>();

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			webDAVRequest.getGroupId(), parentFolderId);

		for (FileEntry fileEntry : fileEntries) {
			if (!DLWebDAVUtil.isRepresentableTitle(fileEntry.getTitle())) {
				_log.error(
					"Unrepresentable WebDAV title for file name " +
						fileEntry.getTitle());

				continue;
			}

			resources.add(toResource(webDAVRequest, fileEntry, true));
		}

		return resources;
	}

	protected long getFolderId(long companyId, String[] pathArray)
		throws Exception {

		return getFolderId(companyId, pathArray, false);
	}

	protected long getFolderId(
			long companyId, String[] pathArray, boolean parent)
		throws Exception {

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (pathArray.length <= 1) {
			return folderId;
		}

		long groupId = WebDAVUtil.getGroupId(companyId, pathArray);

		int x = pathArray.length;

		if (parent) {
			x--;
		}

		for (int i = 2; i < x; i++) {
			String name = pathArray[i];

			Folder folder = _dlAppService.getFolder(groupId, folderId, name);

			if (groupId == folder.getRepositoryId()) {
				folderId = folder.getFolderId();
			}
		}

		return folderId;
	}

	protected List<Resource> getFolders(
			WebDAVRequest webDAVRequest, long parentFolderId)
		throws Exception {

		List<Resource> resources = new ArrayList<>();

		List<Folder> folders = _dlAppService.getFolders(
			webDAVRequest.getGroupId(), parentFolderId, false);

		for (Folder folder : folders) {
			resources.add(toResource(webDAVRequest, folder, true));
		}

		return resources;
	}

	protected long getParentFolderId(long companyId, String[] pathArray)
		throws Exception {

		return getFolderId(companyId, pathArray, true);
	}

	protected String getTitle(String[] pathArray) {
		return DLWebDAVUtil.unescapeRawTitle(
			WebDAVUtil.getResourceName(pathArray));
	}

	protected boolean hasLock(FileEntry fileEntry, String lockUuid)
		throws Exception {

		if (Validator.isNull(lockUuid)) {

			// Client does not claim to know of a lock

			return fileEntry.hasLock();
		}

		// Client claims to know of a lock. Verify the lock UUID.

		try {
			return _dlAppService.verifyFileEntryLock(
				fileEntry.getRepositoryId(), fileEntry.getFileEntryId(),
				lockUuid);
		}
		catch (NoSuchLockException nsle) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsle, nsle);
			}

			return false;
		}
	}

	protected void populateServiceContext(
			ServiceContext serviceContext, FileEntry fileEntry)
		throws PortalException {

		serviceContext.setScopeGroupId(fileEntry.getGroupId());

		String className = DLFileEntryConstants.getClassName();

		long[] assetCategoryIds = _assetCategoryLocalService.getCategoryIds(
			className, fileEntry.getFileEntryId());

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			className, fileEntry.getFileEntryId());

		List<AssetLink> assetLinks = _assetLinkLocalService.getLinks(
			assetEntry.getEntryId());

		long[] assetLinkEntryIds = ListUtil.toLongArray(
			assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

		serviceContext.setAssetLinkEntryIds(assetLinkEntryIds);

		String[] assetTagNames = _assetTagLocalService.getTagNames(
			className, fileEntry.getFileEntryId());

		serviceContext.setAssetTagNames(assetTagNames);

		ExpandoBridge expandoBridge = fileEntry.getExpandoBridge();

		serviceContext.setExpandoBridgeAttributes(
			expandoBridge.getAttributes());

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		long fileEntryTypeId = dlFileEntry.getFileEntryTypeId();

		if (fileEntryTypeId > 0) {
			serviceContext.setAttribute(
				"fileEntryTypeId", dlFileEntry.getFileEntryTypeId());

			DLFileVersion dlFileVersion =
				_dlFileVersionLocalService.getLatestFileVersion(
					fileEntry.getFileEntryId(), !dlFileEntry.isCheckedOut());

			DLFileEntryType dlFileEntryType =
				_dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);

			List<DDMStructure> ddmStructures =
				dlFileEntryType.getDDMStructures();

			for (DDMStructure ddmStructure : ddmStructures) {
				DLFileEntryMetadata dlFileEntryMetadata =
					_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
						ddmStructure.getStructureId(),
						dlFileVersion.getFileVersionId());

				if (dlFileEntryMetadata == null) {
					continue;
				}

				DDMFormValues ddmFormValues =
					StorageEngineManagerUtil.getDDMFormValues(
						dlFileEntryMetadata.getDDMStorageId());

				serviceContext.setAttribute(
					DDMFormValues.class.getName() + StringPool.POUND +
						ddmStructure.getStructureId(),
					ddmFormValues);
			}
		}
	}

	protected Resource toResource(
		WebDAVRequest webDAVRequest, FileEntry fileEntry, boolean appendPath) {

		return new DLFileEntryResourceImpl(
			webDAVRequest, fileEntry, appendPath);
	}

	protected Resource toResource(
		WebDAVRequest webDAVRequest, Folder folder, boolean appendPath) {

		String parentPath = getRootPath() + webDAVRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = folder.getName();
		}

		Resource resource = new BaseResourceImpl(
			parentPath, name, folder.getName(), folder.getCreateDate(),
			folder.getModifiedDate());

		resource.setModel(folder);
		resource.setClassName(Folder.class.getName());
		resource.setPrimaryKey(folder.getPrimaryKey());

		return resource;
	}

	private ServiceContext _getServiceContext(
			String className, WebDAVRequest webDAVRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			className, webDAVRequest.getHttpServletRequest());

		serviceContext.setModelPermissions(null);

		serviceContext.deriveDefaultPermissions(
			webDAVRequest.getGroupId(), className);

		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLWebDAVStorageImpl.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference
	private DLTrashService _dlTrashService;

	@Reference
	private DLTrashUtil _dlTrashUtil;

}