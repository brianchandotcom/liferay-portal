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

package com.liferay.sync.service.impl;

import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.sync.model.DLSyncEvent;
import com.liferay.document.library.sync.service.DLSyncEventLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.jsonwebservice.NoSuchJSONWebServiceException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.sync.constants.SyncConstants;
import com.liferay.sync.constants.SyncDLObjectConstants;
import com.liferay.sync.constants.SyncDeviceConstants;
import com.liferay.sync.internal.configuration.SyncServiceConfigurationValues;
import com.liferay.sync.internal.util.JSONWebServiceActionParametersMap;
import com.liferay.sync.internal.util.SyncContext;
import com.liferay.sync.internal.util.SyncDLObjectUpdate;
import com.liferay.sync.internal.util.SyncDeviceThreadLocal;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.model.SyncDevice;
import com.liferay.sync.service.SyncDLFileVersionDiffLocalService;
import com.liferay.sync.service.base.SyncDLObjectServiceBaseImpl;
import com.liferay.sync.service.configuration.SyncServiceConfigurationKeys;
import com.liferay.sync.util.SyncHelper;
import com.liferay.sync.util.comparator.SyncDLObjectModifiedTimeComparator;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jodd.bean.BeanUtil;

import jodd.util.NameValue;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Young
 * @author Dennis Ju
 */
@Component(
	property = {
		"json.web.service.context.name=sync",
		"json.web.service.context.path=SyncDLObject"
	},
	service = AopService.class
)
public class SyncDLObjectServiceImpl extends SyncDLObjectServiceBaseImpl {

	@Override
	public SyncDLObject addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			File file, String checksum, ServiceContext serviceContext)
		throws PortalException {

		try {
			Group group = groupLocalService.getGroup(repositoryId);

			_syncHelper.checkSyncEnabled(group.getGroupId());

			checkFolder(folderId);

			if (!group.isUser()) {
				ModelPermissions modelPermissions =
					serviceContext.getModelPermissions();

				if ((modelPermissions == null) ||
					ArrayUtil.isEmpty(
						modelPermissions.getActionIds(
							RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE))) {

					_syncHelper.setFilePermissions(
						group, false, serviceContext);
				}
			}

			serviceContext.setCommand(Constants.ADD);

			populateServiceContext(serviceContext, group.getGroupId());

			FileEntry fileEntry = dlAppService.addFileEntry(
				repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, file, serviceContext);

			return toSyncDLObject(
				fileEntry, SyncDLObjectConstants.EVENT_ADD, checksum);
		}
		catch (PortalException pe) {
			if ((pe instanceof DuplicateFileEntryException) &&
				GetterUtil.getBoolean(
					serviceContext.getAttribute("overwrite"))) {

				FileEntry fileEntry = dlAppService.getFileEntry(
					repositoryId, folderId, title);

				return updateFileEntry(
					fileEntry.getFileEntryId(), sourceFileName, mimeType, title,
					description, changeLog, false, file, checksum,
					serviceContext);
			}

			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject addFolder(
			long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException {

		try {
			Group group = groupLocalService.getGroup(repositoryId);

			_syncHelper.checkSyncEnabled(group.getGroupId());

			checkFolder(parentFolderId);

			if (!group.isUser()) {
				ModelPermissions modelPermissions =
					serviceContext.getModelPermissions();

				if ((modelPermissions == null) ||
					ArrayUtil.isEmpty(
						modelPermissions.getActionIds(
							RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE))) {

					_syncHelper.setFilePermissions(group, true, serviceContext);
				}
			}

			Folder folder = dlAppService.addFolder(
				repositoryId, parentFolderId, name, description,
				serviceContext);

			return toSyncDLObject(folder, SyncDLObjectConstants.EVENT_ADD);
		}
		catch (PortalException pe) {
			if ((pe instanceof DuplicateFolderNameException) &&
				GetterUtil.getBoolean(
					serviceContext.getAttribute("overwrite"))) {

				Folder folder = dlAppService.getFolder(
					repositoryId, parentFolderId, name);

				return updateFolder(
					folder.getFolderId(), name, description, serviceContext);
			}

			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject cancelCheckOut(long fileEntryId)
		throws PortalException {

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			_syncHelper.checkSyncEnabled(fileEntry.getGroupId());

			checkFileEntry(fileEntry);

			dlAppService.cancelCheckOut(fileEntryId);

			fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			return toSyncDLObject(
				fileEntry, SyncDLObjectConstants.EVENT_CANCEL_CHECK_OUT);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			_syncHelper.checkSyncEnabled(fileEntry.getGroupId());

			checkFileEntry(fileEntry);

			dlAppService.checkInFileEntry(
				fileEntryId,
				DLVersionNumberIncrease.fromMajorVersion(majorVersion),
				changeLog, serviceContext);

			fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			return toSyncDLObject(
				fileEntry, SyncDLObjectConstants.EVENT_CHECK_IN);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			_syncHelper.checkSyncEnabled(fileEntry.getGroupId());

			checkFileEntry(fileEntry);

			dlAppService.checkOutFileEntry(fileEntryId, serviceContext);

			fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			return toSyncDLObject(
				fileEntry, SyncDLObjectConstants.EVENT_CHECK_OUT);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			_syncHelper.checkSyncEnabled(fileEntry.getGroupId());

			checkFileEntry(fileEntry);

			fileEntry = dlAppService.checkOutFileEntry(
				fileEntryId, owner, expirationTime, serviceContext);

			return toSyncDLObject(
				fileEntry, SyncDLObjectConstants.EVENT_CHECK_OUT);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject copyFileEntry(
			long sourceFileEntryId, long repositoryId, long folderId,
			String sourceFileName, String title, ServiceContext serviceContext)
		throws PortalException {

		try {
			Group group = groupLocalService.getGroup(repositoryId);

			_syncHelper.checkSyncEnabled(group.getGroupId());

			checkFolder(folderId);

			FileEntry sourceFileEntry = dlAppLocalService.getFileEntry(
				sourceFileEntryId);

			SyncDLObject sourceSyncDLObject =
				syncDLObjectLocalService.fetchSyncDLObject(
					SyncDLObjectConstants.TYPE_FILE, sourceFileEntryId);

			FileVersion fileVersion = sourceFileEntry.getLatestFileVersion();

			if (!group.isUser()) {
				ModelPermissions modelPermissions =
					serviceContext.getModelPermissions();

				if ((modelPermissions == null) ||
					ArrayUtil.isEmpty(
						modelPermissions.getActionIds(
							RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE))) {

					_syncHelper.setFilePermissions(
						group, false, serviceContext);
				}
			}

			serviceContext.setCommand(Constants.ADD);

			populateServiceContext(serviceContext, group.getGroupId());

			FileEntry fileEntry = dlAppService.addFileEntry(
				repositoryId, folderId, sourceFileName,
				sourceFileEntry.getMimeType(), title, null, null,
				fileVersion.getContentStream(false), sourceFileEntry.getSize(),
				serviceContext);

			return toSyncDLObject(
				fileEntry, SyncDLObjectConstants.EVENT_ADD,
				sourceSyncDLObject.getChecksum());
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public List<SyncDLObject> getAllFolderSyncDLObjects(long repositoryId)
		throws PortalException {

		try {
			_syncHelper.checkSyncEnabled(repositoryId);

			repositoryService.checkRepository(repositoryId);

			List<SyncDLObject> syncDLObjects =
				syncDLObjectFinder.findByModifiedTime(
					-1, repositoryId, 0, SyncDLObjectConstants.TYPE_FOLDER,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			return checkSyncDLObjects(syncDLObjects, repositoryId, 0);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject getFileEntrySyncDLObject(
			long repositoryId, long folderId, String title)
		throws PortalException {

		try {
			_syncHelper.checkSyncEnabled(repositoryId);

			FileEntry fileEntry = dlAppService.getFileEntry(
				repositoryId, folderId, title);

			return toSyncDLObject(fileEntry, SyncDLObjectConstants.EVENT_GET);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public List<SyncDLObject> getFileEntrySyncDLObjects(
			long repositoryId, long folderId)
		throws PortalException {

		try {
			_syncHelper.checkSyncEnabled(repositoryId);

			List<FileEntry> fileEntries = dlAppService.getFileEntries(
				repositoryId, folderId);

			List<SyncDLObject> syncDLObjects = new ArrayList<>(
				fileEntries.size());

			for (FileEntry fileEntry : fileEntries) {
				SyncDLObject syncDLObject = toSyncDLObject(
					fileEntry, SyncDLObjectConstants.EVENT_GET);

				syncDLObjects.add(syncDLObject);
			}

			return syncDLObjects;
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject getFolderSyncDLObject(long folderId)
		throws PortalException {

		try {
			Folder folder = dlAppLocalService.getFolder(folderId);

			_syncHelper.checkSyncEnabled(folder.getGroupId());

			folder = dlAppService.getFolder(folderId);

			if (!_syncHelper.isSupportedFolder(folder)) {
				return null;
			}

			return toSyncDLObject(folder, SyncDLObjectConstants.EVENT_GET);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject getFolderSyncDLObject(
			long repositoryId, long parentFolderId, String name)
		throws PortalException {

		try {
			_syncHelper.checkSyncEnabled(repositoryId);

			Folder folder = dlAppService.getFolder(
				repositoryId, parentFolderId, name);

			if (!_syncHelper.isSupportedFolder(folder)) {
				return null;
			}

			return toSyncDLObject(folder, SyncDLObjectConstants.EVENT_GET);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public List<SyncDLObject> getFolderSyncDLObjects(
			long repositoryId, long parentFolderId)
		throws PortalException {

		try {
			_syncHelper.checkSyncEnabled(repositoryId);

			List<Folder> folders = dlAppService.getFolders(
				repositoryId, parentFolderId);

			List<SyncDLObject> syncDLObjects = new ArrayList<>(folders.size());

			for (Folder folder : folders) {
				if (!_syncHelper.isSupportedFolder(folder)) {
					continue;
				}

				SyncDLObject syncDLObject = toSyncDLObject(
					folder, SyncDLObjectConstants.EVENT_GET);

				syncDLObjects.add(syncDLObject);
			}

			return syncDLObjects;
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public Group getGroup(long groupId) throws PortalException {
		try {
			_syncHelper.checkSyncEnabled(groupId);

			return groupService.getGroup(groupId);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public long getLatestModifiedTime() throws PortalException {
		try {
			_syncHelper.checkSyncEnabled(0);

			return syncDLObjectLocalService.getLatestModifiedTime();
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public Object getSyncContext() throws PortalException {
		try {
			_syncHelper.checkSyncEnabled(0);

			User user = getGuestOrUser();

			SyncContext syncContext = new SyncContext();

			String authType = PrefsPropsUtil.getString(
				CompanyThreadLocal.getCompanyId(),
				PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
				PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE));

			syncContext.setAuthType(authType);

			boolean oAuthEnabled = PrefsPropsUtil.getBoolean(
				user.getCompanyId(), SyncConstants.SYNC_OAUTH_ENABLED);

			if (oAuthEnabled) {
				String oAuthConsumerKey = PrefsPropsUtil.getString(
					user.getCompanyId(), SyncConstants.SYNC_OAUTH_CONSUMER_KEY);

				syncContext.setOAuthConsumerKey(oAuthConsumerKey);

				String oAuthConsumerSecret = PrefsPropsUtil.getString(
					user.getCompanyId(),
					SyncConstants.SYNC_OAUTH_CONSUMER_SECRET);

				syncContext.setOAuthConsumerSecret(oAuthConsumerSecret);

				syncContext.setOAuthEnabled(true);
			}

			syncContext.setPortletPreferencesMap(getPortletPreferencesMap());

			Bundle bundle = FrameworkUtil.getBundle(getClass());

			syncContext.setPluginVersion(String.valueOf(bundle.getVersion()));

			if (!user.isDefaultUser()) {
				boolean lanEnabled = PrefsPropsUtil.getBoolean(
					user.getCompanyId(),
					SyncServiceConfigurationKeys.SYNC_LAN_ENABLED,
					SyncServiceConfigurationValues.SYNC_LAN_ENABLED);

				if (lanEnabled) {
					String lanCertificate = PrefsPropsUtil.getString(
						user.getCompanyId(),
						SyncConstants.SYNC_LAN_CERTIFICATE);

					syncContext.setLanCertificate(lanCertificate);

					syncContext.setLanEnabled(true);

					String lanKey = PrefsPropsUtil.getString(
						user.getCompanyId(), SyncConstants.SYNC_LAN_KEY);

					syncContext.setLanKey(lanKey);

					String lanServerUuid = PrefsPropsUtil.getString(
						user.getCompanyId(),
						SyncConstants.SYNC_LAN_SERVER_UUID);

					syncContext.setLanServerUuid(lanServerUuid);
				}

				syncContext.setPortalBuildNumber(ReleaseInfo.getBuildNumber());
				syncContext.setUser(user);

				if (!syncDeviceSupports(SyncDeviceConstants.FEATURE_SET_1)) {
					syncContext.setUserSitesGroups(getUserSitesGroups());
				}
			}

			return syncContext;
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public String getSyncDLObjectUpdate(
			long repositoryId, long lastAccessTime, int max)
		throws PortalException {

		return getSyncDLObjectUpdate(repositoryId, lastAccessTime, max, true);
	}

	@Override
	public String getSyncDLObjectUpdate(
			long repositoryId, long lastAccessTime, int max,
			boolean retrieveFromCache)
		throws PortalException {

		try {
			_syncHelper.checkSyncEnabled(repositoryId);

			repositoryService.checkRepository(repositoryId);

			String[] events = null;

			if (retrieveFromCache) {
				events = new String[0];
			}
			else {
				events = new String[] {
					SyncDLObjectConstants.EVENT_DELETE,
					SyncDLObjectConstants.EVENT_TRASH
				};
			}

			int count = syncDLObjectPersistence.countByM_R_NotE(
				lastAccessTime, repositoryId, events);

			if (count == 0) {
				SyncDLObjectUpdate syncDLObjectUpdate = getSyncDLObjectUpdate(
					Collections.<SyncDLObject>emptyList(), 0, lastAccessTime,
					lastAccessTime);

				return syncDLObjectUpdate.toString();
			}

			int start = 0;
			int end = 0;

			if (max == QueryUtil.ALL_POS) {
				start = QueryUtil.ALL_POS;
				end = QueryUtil.ALL_POS;
			}
			else if (max == 0) {
				end = SyncServiceConfigurationValues.SYNC_PAGINATION_DELTA;
			}
			else {
				end = max;
			}

			List<SyncDLObject> syncDLObjects = null;

			if (retrieveFromCache) {
				syncDLObjects = syncDLObjectPersistence.findByM_R_NotE(
					lastAccessTime, repositoryId, events, start, end,
					new SyncDLObjectModifiedTimeComparator());
			}
			else {
				syncDLObjects = syncDLObjectFinder.findByModifiedTime(
					lastAccessTime, repositoryId, 0, null, start, end);
			}

			SyncDLObject syncDLObject = syncDLObjects.get(
				syncDLObjects.size() - 1);

			SyncDLObjectUpdate syncDLObjectUpdate = getSyncDLObjectUpdate(
				checkSyncDLObjects(syncDLObjects, repositoryId, lastAccessTime),
				count, syncDLObject.getModifiedTime(), lastAccessTime);

			return syncDLObjectUpdate.toString();
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public String getSyncDLObjectUpdate(
			long repositoryId, long parentFolderId, long lastAccessTime)
		throws PortalException {

		try {
			_syncHelper.checkSyncEnabled(repositoryId);

			repositoryService.checkRepository(repositoryId);

			List<SyncDLObject> syncDLObjects =
				syncDLObjectFinder.findByModifiedTime(
					lastAccessTime, repositoryId, parentFolderId, null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			SyncDLObject syncDLObject = syncDLObjects.get(
				syncDLObjects.size() - 1);

			SyncDLObjectUpdate syncDLObjectUpdate = getSyncDLObjectUpdate(
				checkSyncDLObjects(syncDLObjects, repositoryId, lastAccessTime),
				syncDLObjects.size(), syncDLObject.getModifiedTime(),
				lastAccessTime);

			return syncDLObjectUpdate.toString();
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public List<Group> getUserSitesGroups() throws PortalException {
		try {
			_syncHelper.checkSyncEnabled(0);

			User user = getUser();

			List<Group> groups = new ArrayList<>();

			LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

			groupParams.put("active", true);
			groupParams.put("usersGroups", user.getUserId());

			List<Group> userSiteGroups = groupLocalService.search(
				user.getCompanyId(), null, groupParams, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (Group userSiteGroup : userSiteGroups) {
				if (_syncHelper.isSyncEnabled(userSiteGroup)) {
					userSiteGroup.setName(userSiteGroup.getDescriptiveName());

					groups.add(userSiteGroup);
				}
			}

			List<Organization> organizations =
				organizationLocalService.getOrganizations(
					user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

			for (Organization organization : organizations) {
				Group userOrganizationGroup = organization.getGroup();

				if (_syncHelper.isSyncEnabled(userOrganizationGroup)) {
					userOrganizationGroup.setName(
						userOrganizationGroup.getDescriptiveName());

					groups.add(userOrganizationGroup);
				}

				if (!GetterUtil.getBoolean(
						PropsUtil.get(
							PropsKeys.ORGANIZATIONS_MEMBERSHIP_STRICT))) {

					for (Organization ancestorOrganization :
							organization.getAncestors()) {

						Group userAncestorOrganizationGroup =
							ancestorOrganization.getGroup();

						if (_syncHelper.isSyncEnabled(
								userAncestorOrganizationGroup)) {

							userAncestorOrganizationGroup.setName(
								userAncestorOrganizationGroup.
									getDescriptiveName());

							groups.add(userAncestorOrganizationGroup);
						}
					}
				}
			}

			if (PrefsPropsUtil.getBoolean(
					user.getCompanyId(),
					SyncServiceConfigurationKeys.SYNC_ALLOW_USER_PERSONAL_SITES,
					SyncServiceConfigurationValues.
						SYNC_ALLOW_USER_PERSONAL_SITES)) {

				Group userGroup = user.getGroup();

				userGroup.setName(user.getScreenName());

				groups.add(userGroup);
			}

			Group companyGroup = groupLocalService.getCompanyGroup(
				user.getCompanyId());

			if (_syncHelper.isSyncEnabled(companyGroup)) {
				companyGroup.setName(companyGroup.getDescriptiveName());

				groups.add(companyGroup);
			}

			Collections.sort(groups, new GroupNameComparator());

			return ListUtil.unique(groups);
		}
		catch (PortalException pe) {
			Class<?> clazz = pe.getClass();

			throw new PortalException(clazz.getName(), pe);
		}
	}

	@Override
	public SyncDLObject moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException {

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			_syncHelper.checkSyncEnabled(fileEntry.getGroupId());

			checkFileEntry(fileEntry);

			fileEntry = dlAppService.moveFileEntry(
				fileEntryId, newFolderId, serviceContext);

			return toSyncDLObject(fileEntry, SyncDLObjectConstants.EVENT_MOVE);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject moveFileEntryToTrash(long fileEntryId)
		throws PortalException {

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			_syncHelper.checkSyncEnabled(fileEntry.getGroupId());

			checkFileEntry(fileEntry);

			fileEntry = dlTrashService.moveFileEntryToTrash(fileEntryId);

			return toSyncDLObject(fileEntry, SyncDLObjectConstants.EVENT_TRASH);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException {

		try {
			Folder folder = dlAppLocalService.getFolder(folderId);

			_syncHelper.checkSyncEnabled(folder.getGroupId());

			checkFolder(folder);

			folder = dlAppService.moveFolder(
				folderId, parentFolderId, serviceContext);

			return toSyncDLObject(folder, SyncDLObjectConstants.EVENT_MOVE);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject moveFolderToTrash(long folderId)
		throws PortalException {

		try {
			Folder folder = dlAppLocalService.getFolder(folderId);

			_syncHelper.checkSyncEnabled(folder.getGroupId());

			checkFolder(folderId);

			folder = dlTrashService.moveFolderToTrash(folderId);

			return toSyncDLObject(folder, SyncDLObjectConstants.EVENT_TRASH);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject patchFileEntry(
			long fileEntryId, long sourceVersionId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File deltaFile, String checksum,
			ServiceContext serviceContext)
		throws PortalException {

		File sourceFile = null;

		File patchedFile = null;

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			_syncHelper.checkSyncEnabled(fileEntry.getGroupId());

			checkFileEntry(fileEntry);

			DLFileVersion dlFileVersion =
				dlFileVersionLocalService.getDLFileVersion(sourceVersionId);

			sourceFile = FileUtil.createTempFile(
				dlFileEntryLocalService.getFileAsStream(
					fileEntryId, dlFileVersion.getVersion(), false));

			patchedFile = FileUtil.createTempFile();

			_syncHelper.patchFile(sourceFile, deltaFile, patchedFile);

			SyncDLObject syncDLObject = updateFileEntry(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, patchedFile, checksum, serviceContext);

			if (SyncServiceConfigurationValues.SYNC_FILE_DIFF_CACHE_ENABLED &&
				(sourceVersionId != syncDLObject.getVersionId())) {

				DLFileVersion targetDLFileVersion =
					dlFileVersionLocalService.getFileVersion(
						syncDLObject.getVersionId());

				_syncDLFileVersionDiffLocalService.addSyncDLFileVersionDiff(
					fileEntryId, sourceVersionId,
					targetDLFileVersion.getFileVersionId(), deltaFile);
			}

			return syncDLObject;
		}
		catch (Exception e) {
			throw new PortalException(_syncHelper.buildExceptionMessage(e), e);
		}
		finally {
			FileUtil.delete(sourceFile);

			FileUtil.delete(patchedFile);
		}
	}

	@Override
	public SyncDLObject restoreFileEntryFromTrash(long fileEntryId)
		throws PortalException {

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			_syncHelper.checkSyncEnabled(fileEntry.getGroupId());

			dlTrashService.restoreFileEntryFromTrash(fileEntryId);

			fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			return toSyncDLObject(
				fileEntry, SyncDLObjectConstants.EVENT_RESTORE);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject restoreFolderFromTrash(long folderId)
		throws PortalException {

		try {
			Folder folder = dlAppLocalService.getFolder(folderId);

			_syncHelper.checkSyncEnabled(folder.getGroupId());

			dlTrashService.restoreFolderFromTrash(folderId);

			folder = dlAppLocalService.getFolder(folderId);

			return toSyncDLObject(folder, SyncDLObjectConstants.EVENT_RESTORE);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	@Transactional(enabled = false)
	public Map<String, Object> updateFileEntries(File zipFile)
		throws PortalException {

		Map<String, Object> responseMap = new HashMap<>();

		ZipReader zipReader = null;

		try {
			_syncHelper.checkSyncEnabled(0);

			zipReader = _zipReaderFactory.getZipReader(zipFile);

			String manifest = zipReader.getEntryAsString("/manifest.json");

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(manifest);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				JSONWebServiceActionParametersMap
					jsonWebServiceActionParametersMap =
						JSONFactoryUtil.looseDeserialize(
							jsonObject.toString(),
							JSONWebServiceActionParametersMap.class);

				String zipFileId = MapUtil.getString(
					jsonWebServiceActionParametersMap, "zipFileId");

				try {
					responseMap.put(
						zipFileId,
						updateFileEntries(
							zipReader, zipFileId,
							jsonWebServiceActionParametersMap));
				}
				catch (Exception e) {
					String message = e.getMessage();

					if (message == null) {
						_log.error(e, e);

						message = e.toString();
					}

					if (!message.startsWith(StringPool.QUOTE) &&
						!message.endsWith(StringPool.QUOTE)) {

						message = StringUtil.quote(message, StringPool.QUOTE);
					}

					String json = "{\"exception\": " + message + "}";

					responseMap.put(zipFileId, json);
				}
			}
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
		finally {
			if (zipReader != null) {
				zipReader.close();
			}
		}

		return responseMap;
	}

	@Override
	public SyncDLObject updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, String checksum,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			_syncHelper.checkSyncEnabled(fileEntry.getGroupId());

			checkFileEntry(fileEntry);

			serviceContext.setCommand(Constants.UPDATE);

			populateServiceContext(serviceContext, fileEntry.getGroupId());

			fileEntry = dlAppService.updateFileEntry(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog,
				DLVersionNumberIncrease.fromMajorVersion(majorVersion), file,
				serviceContext);

			return toSyncDLObject(
				fileEntry, SyncDLObjectConstants.EVENT_UPDATE, checksum);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	@Override
	public SyncDLObject updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			Folder folder = dlAppLocalService.getFolder(folderId);

			_syncHelper.checkSyncEnabled(folder.getGroupId());

			checkFolder(folder);

			folder = dlAppService.updateFolder(
				folderId, name, description, serviceContext);

			return toSyncDLObject(folder, SyncDLObjectConstants.EVENT_UPDATE);
		}
		catch (PortalException pe) {
			throw new PortalException(
				_syncHelper.buildExceptionMessage(pe), pe);
		}
	}

	protected static SyncDLObjectUpdate getSyncDLObjectUpdate(
		List<SyncDLObject> syncDLObjects, int resultsTotal, long lastAccessTime,
		long previousLastAccessTime) {

		Map<String, Long> settingsModifiedTimes = new HashMap<>();

		long syncContextModifiedTime = PrefsPropsUtil.getLong(
			CompanyThreadLocal.getCompanyId(),
			SyncConstants.SYNC_CONTEXT_MODIFIED_TIME);

		if ((syncContextModifiedTime != 0) &&
			(syncContextModifiedTime > previousLastAccessTime)) {

			settingsModifiedTimes.put(
				SyncConstants.SYNC_CONTEXT_MODIFIED_TIME,
				syncContextModifiedTime);
		}

		return new SyncDLObjectUpdate(
			syncDLObjects, resultsTotal, lastAccessTime, settingsModifiedTimes);
	}

	protected static boolean syncDeviceSupports(int featureSet) {
		SyncDevice syncDevice = SyncDeviceThreadLocal.getSyncDevice();

		if (syncDevice == null) {
			return false;
		}

		return syncDevice.supports(featureSet);
	}

	protected void checkFileEntry(FileEntry fileEntry) throws PortalException {

		// SYNC-1542

		if (fileEntry.isInTrash()) {
			throw new NoSuchFileEntryException();
		}
	}

	protected void checkFolder(Folder folder) throws PortalException {

		// SYNC-1542

		if (folder.getModel() instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)folder.getModel();

			if (dlFolder.isInTrash()) {
				throw new NoSuchFolderException();
			}

			return;
		}

		throw new PortalException("Folder must be an instance of DLFolder");
	}

	protected void checkFolder(long folderId) throws PortalException {
		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		checkFolder(dlAppService.getFolder(folderId));
	}

	protected SyncDLObject checkModifiedTime(
		SyncDLObject syncDLObject, long typePK) {

		DynamicQuery dynamicQuery = _dlSyncEventLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("typePK", typePK));

		List<DLSyncEvent> dlSyncEvents = _dlSyncEventLocalService.dynamicQuery(
			dynamicQuery);

		if (dlSyncEvents.isEmpty()) {
			return syncDLObject;
		}

		DLSyncEvent dlSyncEvent = dlSyncEvents.get(0);

		syncDLObject.setModifiedTime(dlSyncEvent.getModifiedTime());

		return syncDLObject;
	}

	protected List<SyncDLObject> checkSyncDLObjects(
			List<SyncDLObject> syncDLObjects, long repositoryId,
			long lastAccessTime)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isGroupAdmin(repositoryId)) {
			return syncDLObjects;
		}

		boolean hasFileModelPermission = hasModelPermission(
			repositoryId, DLFileEntryConstants.getClassName());
		boolean hasFolderModelPermission = hasModelPermission(
			repositoryId, DLFolderConstants.getClassName());

		if (hasFileModelPermission && hasFolderModelPermission) {
			return syncDLObjects;
		}

		Set<Long> typePKs = new HashSet<>();

		for (SyncDLObject syncDLObject : syncDLObjects) {
			typePKs.add(syncDLObject.getTypePK());

			if (!hasFolderModelPermission &&
				_PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

				long[] parentFolderIds = StringUtil.split(
					syncDLObject.getTreePath(), StringPool.SLASH, 0L);

				for (long parentFolderId : parentFolderIds) {
					if (parentFolderId > 0) {
						typePKs.add(parentFolderId);
					}
				}
			}
		}

		Set<Long> checkedTypePKs = SetUtil.fromList(
			checkTypePKs(
				repositoryId, permissionChecker.getUserId(),
				new ArrayList(typePKs)));

		List<SyncDLObject> checkedSyncDLObjects = new ArrayList<>();

		Date lastAccessDate = new Date(lastAccessTime);

		for (SyncDLObject syncDLObject : syncDLObjects) {
			String event = syncDLObject.getEvent();

			if (event.equals(SyncDLObjectConstants.EVENT_DELETE) ||
				event.equals(SyncDLObjectConstants.EVENT_TRASH) ||
				hasPermission(
					checkedTypePKs, syncDLObject, hasFileModelPermission,
					hasFolderModelPermission)) {

				checkedSyncDLObjects.add(syncDLObject);

				continue;
			}

			Date lastPermissionChangeDate =
				syncDLObject.getLastPermissionChangeDate();

			if ((lastPermissionChangeDate != null) &&
				lastPermissionChangeDate.after(lastAccessDate)) {

				syncDLObject.setEvent(SyncDLObjectConstants.EVENT_DELETE);

				checkedSyncDLObjects.add(syncDLObject);
			}
		}

		return checkedSyncDLObjects;
	}

	protected List<Long> checkTypePKs(
		long repositoryId, long userId, List<Long> typePKs) {

		if (typePKs.size() <= _SQL_DATA_MAX_PARAMETERS) {
			return syncDLObjectFinder.filterFindByR_U_T(
				repositoryId, userId, ArrayUtil.toLongArray(typePKs));
		}

		List<Long> sublistTypePKs = typePKs.subList(
			0, _SQL_DATA_MAX_PARAMETERS);

		List<Long> checkedTypePKs = syncDLObjectFinder.filterFindByR_U_T(
			repositoryId, userId, ArrayUtil.toLongArray(sublistTypePKs));

		sublistTypePKs.clear();

		checkedTypePKs.addAll(checkTypePKs(repositoryId, userId, typePKs));

		return checkedTypePKs;
	}

	protected Map<String, String> getPortletPreferencesMap()
		throws PortalException {

		User user = getUser();

		Map<String, String> portletPreferencesMap = HashMapBuilder.put(
			SyncServiceConfigurationKeys.
				SYNC_CLIENT_AUTHENTICATION_RETRY_INTERVAL,
			String.valueOf(
				SyncServiceConfigurationValues.
					SYNC_CLIENT_AUTHENTICATION_RETRY_INTERVAL)
		).build();

		int batchFileMaxSize = PrefsPropsUtil.getInteger(
			user.getCompanyId(),
			SyncServiceConfigurationKeys.SYNC_CLIENT_BATCH_FILE_MAX_SIZE,
			SyncServiceConfigurationValues.SYNC_CLIENT_BATCH_FILE_MAX_SIZE);

		portletPreferencesMap.put(
			SyncServiceConfigurationKeys.SYNC_CLIENT_BATCH_FILE_MAX_SIZE,
			String.valueOf(batchFileMaxSize));

		boolean forceSecurityMode = PrefsPropsUtil.getBoolean(
			user.getCompanyId(),
			SyncServiceConfigurationKeys.SYNC_CLIENT_FORCE_SECURITY_MODE,
			SyncServiceConfigurationValues.SYNC_CLIENT_FORCE_SECURITY_MODE);

		portletPreferencesMap.put(
			SyncServiceConfigurationKeys.SYNC_CLIENT_FORCE_SECURITY_MODE,
			String.valueOf(forceSecurityMode));

		int maxConnections = PrefsPropsUtil.getInteger(
			user.getCompanyId(),
			SyncServiceConfigurationKeys.SYNC_CLIENT_MAX_CONNECTIONS,
			SyncServiceConfigurationValues.SYNC_CLIENT_MAX_CONNECTIONS);

		portletPreferencesMap.put(
			SyncServiceConfigurationKeys.SYNC_CLIENT_MAX_CONNECTIONS,
			String.valueOf(maxConnections));

		int maxDownloadRate = PrefsPropsUtil.getInteger(
			user.getCompanyId(),
			SyncServiceConfigurationKeys.SYNC_CLIENT_MAX_DOWNLOAD_RATE,
			SyncServiceConfigurationValues.SYNC_CLIENT_MAX_DOWNLOAD_RATE);

		portletPreferencesMap.put(
			SyncServiceConfigurationKeys.SYNC_CLIENT_MAX_DOWNLOAD_RATE,
			String.valueOf(maxDownloadRate));

		int maxUploadRate = PrefsPropsUtil.getInteger(
			user.getCompanyId(),
			SyncServiceConfigurationKeys.SYNC_CLIENT_MAX_UPLOAD_RATE,
			SyncServiceConfigurationValues.SYNC_CLIENT_MAX_UPLOAD_RATE);

		portletPreferencesMap.put(
			SyncServiceConfigurationKeys.SYNC_CLIENT_MAX_UPLOAD_RATE,
			String.valueOf(maxUploadRate));

		int pollInterval = PrefsPropsUtil.getInteger(
			user.getCompanyId(),
			SyncServiceConfigurationKeys.SYNC_CLIENT_POLL_INTERVAL,
			SyncServiceConfigurationValues.SYNC_CLIENT_POLL_INTERVAL);

		portletPreferencesMap.put(
			SyncServiceConfigurationKeys.SYNC_CLIENT_POLL_INTERVAL,
			String.valueOf(pollInterval));

		return portletPreferencesMap;
	}

	protected boolean hasModelPermission(long groupId, String name)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		long[] roleIds = permissionChecker.getRoleIds(
			permissionChecker.getUserId(), groupId);

		if (roleIds.length == 0) {
			return false;
		}

		if (resourcePermissionLocalService.hasResourcePermission(
				permissionChecker.getCompanyId(), name,
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(permissionChecker.getCompanyId()), roleIds,
				ActionKeys.VIEW) ||
			resourcePermissionLocalService.hasResourcePermission(
				permissionChecker.getCompanyId(), name,
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID), roleIds,
				ActionKeys.VIEW) ||
			resourcePermissionLocalService.hasResourcePermission(
				permissionChecker.getCompanyId(), name,
				ResourceConstants.SCOPE_GROUP, String.valueOf(groupId), roleIds,
				ActionKeys.VIEW)) {

			return true;
		}

		return false;
	}

	protected boolean hasPermission(
		Set<Long> checkedTypePKs, SyncDLObject syncDLObject,
		boolean hasFileModelPermission, boolean hasFolderModelPermission) {

		String type = syncDLObject.getType();

		if ((!type.equals(SyncDLObjectConstants.TYPE_FILE) ||
			 !hasFileModelPermission) &&
			(!type.equals(SyncDLObjectConstants.TYPE_FOLDER) ||
			 !hasFolderModelPermission) &&
			!checkedTypePKs.contains(syncDLObject.getTypePK())) {

			return false;
		}

		if (!hasFolderModelPermission &&
			_PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			long[] parentFolderIds = StringUtil.split(
				syncDLObject.getTreePath(), StringPool.SLASH, 0L);

			for (long parentFolderId : parentFolderIds) {
				if ((parentFolderId > 0) &&
					!checkedTypePKs.contains(parentFolderId)) {

					return false;
				}
			}
		}

		return true;
	}

	protected void populateServiceContext(
			ServiceContext serviceContext, long groupId)
		throws PortalException {

		ServiceContext serviceContextThreadLocal =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest httpServletRequest =
			serviceContextThreadLocal.getRequest();

		if (httpServletRequest == null) {
			return;
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		serviceContext.setCompanyId(companyId);

		serviceContext.setPlid(LayoutConstants.DEFAULT_PLID);
		serviceContext.setRequest(httpServletRequest);

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

		themeDisplay.setCompany(_companyLocalService.getCompany(companyId));
		themeDisplay.setPermissionChecker(getPermissionChecker());
		themeDisplay.setPlid(_portal.getControlPanelPlid(companyId));
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(groupId);
		themeDisplay.setSiteGroupId(groupId);

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
	}

	protected SyncDLObject toSyncDLObject(FileEntry fileEntry, String event)
		throws PortalException {

		return toSyncDLObject(fileEntry, event, null);
	}

	protected SyncDLObject toSyncDLObject(
			FileEntry fileEntry, String event, String checksum)
		throws PortalException {

		SyncDLObject syncDLObject = _syncHelper.toSyncDLObject(
			fileEntry, event);

		checkModifiedTime(syncDLObject, fileEntry.getFileEntryId());

		if (Validator.isNotNull(checksum)) {
			_syncHelper.addChecksum(
				syncDLObject.getModifiedTime(), fileEntry.getFileEntryId(),
				checksum);
		}

		String lanTokenKey = _syncHelper.getLanTokenKey(
			syncDLObject.getModifiedTime(), fileEntry.getFileEntryId(), true);

		syncDLObject.setLanTokenKey(lanTokenKey);

		return syncDLObject;
	}

	protected SyncDLObject toSyncDLObject(Folder folder, String event)
		throws PortalException {

		SyncDLObject syncDLObject = _syncHelper.toSyncDLObject(folder, event);

		return checkModifiedTime(syncDLObject, folder.getFolderId());
	}

	protected SyncDLObject updateFileEntries(
			ZipReader zipReader, String zipFileId,
			JSONWebServiceActionParametersMap jsonWebServiceActionParametersMap)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		ServiceContext serviceContextThreadLocal =
			ServiceContextThreadLocal.getServiceContext();

		serviceContext.setRequest(serviceContextThreadLocal.getRequest());

		List<NameValue<String, Object>> innerParameters =
			jsonWebServiceActionParametersMap.getInnerParameters(
				"serviceContext");

		if (innerParameters != null) {
			for (NameValue<String, Object> innerParameter : innerParameters) {
				try {
					BeanUtil.setProperty(
						serviceContext, innerParameter.getName(),
						innerParameter.getValue());
				}
				catch (Exception e) {
					if (_log.isDebugEnabled()) {
						_log.debug(e.getMessage(), e);
					}
				}
			}
		}

		String urlPath = MapUtil.getString(
			jsonWebServiceActionParametersMap, "urlPath");

		if (urlPath.endsWith("/add-file-entry")) {
			long repositoryId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "repositoryId");
			long folderId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "folderId");
			String sourceFileName = MapUtil.getString(
				jsonWebServiceActionParametersMap, "sourceFileName");
			String mimeType = MapUtil.getString(
				jsonWebServiceActionParametersMap, "mimeType");
			String title = MapUtil.getString(
				jsonWebServiceActionParametersMap, "title");
			String description = MapUtil.getString(
				jsonWebServiceActionParametersMap, "description");
			String changeLog = MapUtil.getString(
				jsonWebServiceActionParametersMap, "changeLog");

			InputStream inputStream = zipReader.getEntryAsInputStream(
				zipFileId);

			File tempFile = null;

			try {
				tempFile = FileUtil.createTempFile(inputStream);

				String checksum = MapUtil.getString(
					jsonWebServiceActionParametersMap, "checksum");

				return syncDLObjectService.addFileEntry(
					repositoryId, folderId, sourceFileName, mimeType, title,
					description, changeLog, tempFile, checksum, serviceContext);
			}
			finally {
				FileUtil.delete(tempFile);
			}
		}
		else if (urlPath.endsWith("/add-folder")) {
			long repositoryId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "repositoryId");
			long parentFolderId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "parentFolderId");
			String name = MapUtil.getString(
				jsonWebServiceActionParametersMap, "name");
			String description = MapUtil.getString(
				jsonWebServiceActionParametersMap, "description");

			return syncDLObjectService.addFolder(
				repositoryId, parentFolderId, name, description,
				serviceContext);
		}
		else if (urlPath.endsWith("/copy-file-entry")) {
			long sourceFileEntryId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "sourceFileEntryId");
			long repositoryId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "repositoryId");
			long folderId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "folderId");
			String sourceFileName = MapUtil.getString(
				jsonWebServiceActionParametersMap, "sourceFileName");
			String title = MapUtil.getString(
				jsonWebServiceActionParametersMap, "title");

			return syncDLObjectService.copyFileEntry(
				sourceFileEntryId, repositoryId, folderId, sourceFileName,
				title, serviceContext);
		}
		else if (urlPath.endsWith("/move-file-entry")) {
			long fileEntryId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "fileEntryId");
			long newFolderId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "newFolderId");

			return syncDLObjectService.moveFileEntry(
				fileEntryId, newFolderId, serviceContext);
		}
		else if (urlPath.endsWith("/move-file-entry-to-trash")) {
			long fileEntryId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "fileEntryId");

			return syncDLObjectService.moveFileEntryToTrash(fileEntryId);
		}
		else if (urlPath.endsWith("/move-folder")) {
			long folderId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "folderId");
			long parentFolderId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "parentFolderId");

			return syncDLObjectService.moveFolder(
				folderId, parentFolderId, serviceContext);
		}
		else if (urlPath.endsWith("/move-folder-to-trash")) {
			long folderId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "folderId");

			return syncDLObjectService.moveFolderToTrash(folderId);
		}
		else if (urlPath.endsWith("/patch-file-entry")) {
			long fileEntryId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "fileEntryId");
			long sourceVersionId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "sourceVersionId");
			String sourceFileName = MapUtil.getString(
				jsonWebServiceActionParametersMap, "sourceFileName");
			String mimeType = MapUtil.getString(
				jsonWebServiceActionParametersMap, "mimeType");
			String title = MapUtil.getString(
				jsonWebServiceActionParametersMap, "title");
			String description = MapUtil.getString(
				jsonWebServiceActionParametersMap, "description");
			String changeLog = MapUtil.getString(
				jsonWebServiceActionParametersMap, "changeLog");
			boolean majorVersion = MapUtil.getBoolean(
				jsonWebServiceActionParametersMap, "majorVersion");

			InputStream inputStream = zipReader.getEntryAsInputStream(
				zipFileId);

			File tempFile = null;

			try {
				tempFile = FileUtil.createTempFile(inputStream);

				String checksum = MapUtil.getString(
					jsonWebServiceActionParametersMap, "checksum");

				return syncDLObjectService.patchFileEntry(
					fileEntryId, sourceVersionId, sourceFileName, mimeType,
					title, description, changeLog, majorVersion, tempFile,
					checksum, serviceContext);
			}
			finally {
				FileUtil.delete(tempFile);
			}
		}
		else if (urlPath.endsWith("/update-file-entry")) {
			long fileEntryId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "fileEntryId");
			String sourceFileName = MapUtil.getString(
				jsonWebServiceActionParametersMap, "sourceFileName");
			String mimeType = MapUtil.getString(
				jsonWebServiceActionParametersMap, "mimeType");
			String title = MapUtil.getString(
				jsonWebServiceActionParametersMap, "title");
			String description = MapUtil.getString(
				jsonWebServiceActionParametersMap, "description");
			String changeLog = MapUtil.getString(
				jsonWebServiceActionParametersMap, "changeLog");
			boolean majorVersion = MapUtil.getBoolean(
				jsonWebServiceActionParametersMap, "majorVersion");

			File tempFile = null;

			try {
				InputStream inputStream = zipReader.getEntryAsInputStream(
					zipFileId);

				if (inputStream != null) {
					tempFile = FileUtil.createTempFile(inputStream);
				}

				String checksum = MapUtil.getString(
					jsonWebServiceActionParametersMap, "checksum");

				return syncDLObjectService.updateFileEntry(
					fileEntryId, sourceFileName, mimeType, title, description,
					changeLog, majorVersion, tempFile, checksum,
					serviceContext);
			}
			finally {
				FileUtil.delete(tempFile);
			}
		}
		else if (urlPath.endsWith("/update-folder")) {
			long folderId = MapUtil.getLong(
				jsonWebServiceActionParametersMap, "folderId");
			String name = MapUtil.getString(
				jsonWebServiceActionParametersMap, "name");
			String description = MapUtil.getString(
				jsonWebServiceActionParametersMap, "description");

			return syncDLObjectService.updateFolder(
				folderId, name, description, serviceContext);
		}
		else {
			throw new NoSuchJSONWebServiceException(
				"No JSON web service action with path " + urlPath);
		}
	}

	private static final boolean _PERMISSIONS_VIEW_DYNAMIC_INHERITANCE =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE));

	private static final int _SQL_DATA_MAX_PARAMETERS = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.SQL_DATA_MAX_PARAMETERS));

	private static final Log _log = LogFactoryUtil.getLog(
		SyncDLObjectServiceImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLSyncEventLocalService _dlSyncEventLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SyncDLFileVersionDiffLocalService
		_syncDLFileVersionDiffLocalService;

	@Reference
	private SyncHelper _syncHelper;

	@Reference
	private ZipReaderFactory _zipReaderFactory;

}