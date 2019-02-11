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

package com.liferay.portlet.exportimport.service.impl;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.exception.ExportImportIOException;
import com.liferay.exportimport.kernel.exception.RemoteExportException;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.kernel.staging.StagingURLHelperUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetBranchConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.IndexStatusManagerThreadLocal;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.RemoteAuthException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.http.GroupServiceHttp;
import com.liferay.portlet.exportimport.service.base.StagingLocalServiceBaseImpl;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Michael C. Han
 * @author Máté Thurzó
 * @author Vilmos Papp
 */
public class StagingLocalServiceImpl extends StagingLocalServiceBaseImpl {

	@Override
	public void checkDefaultLayoutSetBranches(
			long userId, Group liveGroup, boolean branchingPublic,
			boolean branchingPrivate, boolean remote,
			ServiceContext serviceContext)
		throws PortalException {

		long targetGroupId = 0;

		if (remote) {
			targetGroupId = liveGroup.getGroupId();
		}
		else {
			Group stagingGroup = liveGroup.getStagingGroup();

			if (stagingGroup == null) {
				return;
			}

			targetGroupId = stagingGroup.getGroupId();
		}

		LayoutSetBranch layoutSetBranch =
			layoutSetBranchLocalService.fetchLayoutSetBranch(
				targetGroupId, false,
				LayoutSetBranchConstants.MASTER_BRANCH_NAME);

		if (branchingPublic && (layoutSetBranch == null)) {
			addDefaultLayoutSetBranch(
				userId, targetGroupId, liveGroup.getDescriptiveName(), false,
				serviceContext);
		}
		else if (!branchingPublic && (layoutSetBranch != null)) {
			deleteLayoutSetBranches(targetGroupId, false);
		}
		else if (layoutSetBranch != null) {
			ExportImportDateUtil.clearLastPublishDate(targetGroupId, false);
		}

		layoutSetBranch = layoutSetBranchLocalService.fetchLayoutSetBranch(
			targetGroupId, true, LayoutSetBranchConstants.MASTER_BRANCH_NAME);

		if (branchingPrivate && (layoutSetBranch == null)) {
			addDefaultLayoutSetBranch(
				userId, targetGroupId, liveGroup.getDescriptiveName(), true,
				serviceContext);
		}
		else if (!branchingPrivate && (layoutSetBranch != null)) {
			deleteLayoutSetBranches(targetGroupId, true);
		}
		else if (layoutSetBranch != null) {
			ExportImportDateUtil.clearLastPublishDate(targetGroupId, true);
		}
	}

	@Override
	public void cleanUpStagingRequest(long stagingRequestId)
		throws PortalException {

		boolean indexReadOnly = IndexStatusManagerThreadLocal.isIndexReadOnly();

		IndexStatusManagerThreadLocal.setIndexReadOnly(true);

		try {
			PortletFileRepositoryUtil.deletePortletFolder(stagingRequestId);
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to clean up staging request " + stagingRequestId,
					nsfe);
			}
		}
		finally {
			IndexStatusManagerThreadLocal.setIndexReadOnly(indexReadOnly);
		}
	}

	@Override
	public long createStagingRequest(long userId, long groupId, String checksum)
		throws PortalException {

		boolean indexReadOnly = IndexStatusManagerThreadLocal.isIndexReadOnly();

		IndexStatusManagerThreadLocal.setIndexReadOnly(true);

		try {
			ServiceContext serviceContext = new ServiceContext();

			Repository repository =
				PortletFileRepositoryUtil.addPortletRepository(
					groupId, _PORTLET_REPOSITORY_ID, serviceContext);

			Folder folder = PortletFileRepositoryUtil.addPortletFolder(
				userId, repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, checksum,
				serviceContext);

			return folder.getFolderId();
		}
		finally {
			IndexStatusManagerThreadLocal.setIndexReadOnly(indexReadOnly);
		}
	}

	@Override
	public void disableStaging(Group liveGroup, ServiceContext serviceContext)
		throws PortalException {

		disableStaging((PortletRequest)null, liveGroup, serviceContext);
	}

	@Override
	public void disableStaging(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws PortalException {

		if (liveGroup.isLayout()) {
			disableStaging(
				portletRequest, liveGroup.getParentGroup(), serviceContext);

			return;
		}

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		boolean stagedLocally = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("staged"));
		boolean stagedRemotely = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("stagedRemotely"));

		if (!stagedLocally && !stagedRemotely) {
			return;
		}

		if (stagedRemotely) {
			String remoteURL = StagingURLHelperUtil.buildRemoteURL(
				typeSettingsProperties);

			long remoteGroupId = GetterUtil.getLong(
				typeSettingsProperties.getProperty("remoteGroupId"));
			boolean forceDisable = GetterUtil.getBoolean(
				serviceContext.getAttribute("forceDisable"));

			disableRemoteStaging(remoteURL, remoteGroupId, forceDisable);
		}

		typeSettingsProperties.remove("branchingPrivate");
		typeSettingsProperties.remove("branchingPublic");
		typeSettingsProperties.remove("remoteAddress");
		typeSettingsProperties.remove("remoteGroupId");
		typeSettingsProperties.remove("remotePathContext");
		typeSettingsProperties.remove("remotePort");
		typeSettingsProperties.remove("secureConnection");
		typeSettingsProperties.remove("staged");
		typeSettingsProperties.remove("stagedRemotely");

		Set<String> keys = new HashSet<>();

		for (String key : typeSettingsProperties.keySet()) {
			if (key.startsWith(StagingConstants.STAGED_PORTLET)) {
				keys.add(key);
			}
		}

		for (String key : keys) {
			typeSettingsProperties.remove(key);
		}

		StagingUtil.deleteLastImportSettings(liveGroup, true);
		StagingUtil.deleteLastImportSettings(liveGroup, false);

		checkDefaultLayoutSetBranches(
			serviceContext.getUserId(), liveGroup, false, false, stagedRemotely,
			serviceContext);

		if (liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			groupLocalService.deleteGroup(stagingGroup.getGroupId());

			liveGroup.clearStagingGroup();
		}

		groupLocalService.updateGroup(
			liveGroup.getGroupId(), typeSettingsProperties.toString());
	}

	@Override
	public void enableLocalStaging(
			long userId, Group liveGroup, boolean branchingPublic,
			boolean branchingPrivate, ServiceContext serviceContext)
		throws PortalException {

		if (liveGroup.isLayout()) {
			enableLocalStaging(
				userId, liveGroup.getParentGroup(), branchingPublic,
				branchingPrivate, serviceContext);

			return;
		}

		if (liveGroup.isStagedRemotely()) {
			disableStaging(liveGroup, serviceContext);
		}

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		boolean hasStagingGroup = liveGroup.hasStagingGroup();

		if (!hasStagingGroup) {
			serviceContext.setAttribute("staging", Boolean.TRUE.toString());

			String languageId = typeSettingsProperties.getProperty(
				"languageId");

			if (Validator.isNotNull(languageId)) {
				serviceContext.setLanguageId(languageId);
			}

			addStagingGroup(userId, liveGroup, serviceContext);
		}

		checkDefaultLayoutSetBranches(
			userId, liveGroup, branchingPublic, branchingPrivate, false,
			serviceContext);

		typeSettingsProperties.setProperty(
			"branchingPrivate", String.valueOf(branchingPrivate));
		typeSettingsProperties.setProperty(
			"branchingPublic", String.valueOf(branchingPublic));

		if (!hasStagingGroup) {
			typeSettingsProperties.setProperty(
				"staged", Boolean.TRUE.toString());
			typeSettingsProperties.setProperty(
				"stagedRemotely", Boolean.FALSE.toString());

			setCommonStagingOptions(typeSettingsProperties, serviceContext);
		}

		groupLocalService.updateGroup(
			liveGroup.getGroupId(), typeSettingsProperties.toString());

		if (!hasStagingGroup) {
			Group stagingGroup = liveGroup.getStagingGroup();

			Map<String, String[]> parameterMap =
				ExportImportConfigurationParameterMapFactoryUtil.
					buildFullPublishParameterMap();

			if (liveGroup.hasPrivateLayouts()) {
				StagingUtil.publishLayouts(
					userId, liveGroup.getGroupId(), stagingGroup.getGroupId(),
					true, parameterMap);

				parameterMap =
					ExportImportConfigurationParameterMapFactoryUtil.
						buildParameterMap();
			}

			if (liveGroup.hasPublicLayouts() ||
				!liveGroup.hasPrivateLayouts()) {

				StagingUtil.publishLayouts(
					userId, liveGroup.getGroupId(), stagingGroup.getGroupId(),
					false, parameterMap);
			}
		}
	}

	@Override
	public void enableRemoteStaging(
			long userId, Group stagingGroup, boolean branchingPublic,
			boolean branchingPrivate, String remoteAddress, int remotePort,
			String remotePathContext, boolean secureConnection,
			long remoteGroupId, ServiceContext serviceContext)
		throws PortalException {

		groupLocalService.validateRemote(
			stagingGroup.getGroupId(), remoteAddress, remotePort,
			remotePathContext, secureConnection, remoteGroupId);

		if (stagingGroup.hasStagingGroup()) {
			disableStaging(stagingGroup, serviceContext);
		}

		boolean stagedRemotely = stagingGroup.isStagedRemotely();

		boolean oldStagedRemotely = stagedRemotely;

		UnicodeProperties typeSettingsProperties =
			stagingGroup.getTypeSettingsProperties();

		String remoteURL = StagingURLHelperUtil.buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection);

		if (stagedRemotely) {
			long oldRemoteGroupId = GetterUtil.getLong(
				typeSettingsProperties.getProperty("remoteGroupId"));

			String oldRemoteURL = StagingURLHelperUtil.buildRemoteURL(
				typeSettingsProperties);

			if (!remoteURL.equals(oldRemoteURL) ||
				(remoteGroupId != oldRemoteGroupId)) {

				disableRemoteStaging(oldRemoteURL, oldRemoteGroupId, false);

				stagedRemotely = false;
			}
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			remoteURL, user.getLogin(), user.getPassword(),
			user.isPasswordEncrypted());

		if (!stagedRemotely) {
			enableRemoteStaging(httpPrincipal, remoteGroupId);
		}

		Group remoteGroup = fetchRemoteGroup(httpPrincipal, remoteGroupId);

		checkDefaultLayoutSetBranches(
			userId, stagingGroup, branchingPublic, branchingPrivate, true,
			serviceContext);

		typeSettingsProperties.setProperty(
			"branchingPrivate", String.valueOf(branchingPrivate));
		typeSettingsProperties.setProperty(
			"branchingPublic", String.valueOf(branchingPublic));
		typeSettingsProperties.setProperty("remoteAddress", remoteAddress);
		typeSettingsProperties.setProperty(
			"remoteGroupId", String.valueOf(remoteGroupId));
		typeSettingsProperties.setProperty(
			"remoteGroupUUID", remoteGroup.getUuid());
		typeSettingsProperties.setProperty(
			"remotePathContext", remotePathContext);
		typeSettingsProperties.setProperty(
			"remotePort", String.valueOf(remotePort));
		typeSettingsProperties.setProperty(
			"secureConnection", String.valueOf(secureConnection));

		if (!oldStagedRemotely) {
			typeSettingsProperties.setProperty(
				"staged", Boolean.TRUE.toString());
			typeSettingsProperties.setProperty(
				"stagedRemotely", Boolean.TRUE.toString());

			setCommonStagingOptions(typeSettingsProperties, serviceContext);
		}

		groupLocalService.updateGroup(
			stagingGroup.getGroupId(), typeSettingsProperties.toString());

		updateStagedPortlets(remoteURL, remoteGroupId, typeSettingsProperties);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public MissingReferences publishStagingRequest(
			long userId, long stagingRequestId, boolean privateLayout,
			Map<String, String[]> parameterMap)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public MissingReferences publishStagingRequest(
			long userId, long stagingRequestId,
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		File file = null;

		Locale siteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			if (exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_PORTLET_REMOTE) {

				ExportImportThreadLocal.setPortletImportInProcess(true);
				ExportImportThreadLocal.setPortletStagingInProcess(true);
			}
			else {
				ExportImportThreadLocal.setLayoutImportInProcess(true);
				ExportImportThreadLocal.setLayoutStagingInProcess(true);
			}

			Folder folder = PortletFileRepositoryUtil.getPortletFolder(
				stagingRequestId);

			FileEntry stagingRequestFileEntry = getStagingRequestFileEntry(
				userId, stagingRequestId, folder);

			file = FileUtil.createTempFile("lar");

			FileUtil.write(file, stagingRequestFileEntry.getContentStream());

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			settingsMap.put("userId", userId);

			long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

			LocaleThreadLocal.setSiteDefaultLocale(
				PortalUtil.getSiteDefaultLocale(targetGroupId));

			MissingReferences missingReferences = null;

			if (exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_PORTLET_REMOTE) {

				exportImportLocalService.importPortletDataDeletions(
					exportImportConfiguration, file);

				missingReferences =
					exportImportLocalService.validateImportPortletInfo(
						exportImportConfiguration, file);

				exportImportLocalService.importPortletInfo(
					exportImportConfiguration, file);
			}
			else {
				exportImportLocalService.importLayoutsDataDeletions(
					exportImportConfiguration, file);

				missingReferences =
					exportImportLocalService.validateImportLayoutsFile(
						exportImportConfiguration, file);

				exportImportLocalService.importLayouts(
					exportImportConfiguration, file);
			}

			return missingReferences;
		}
		catch (IOException ioe) {
			ExportImportIOException eiioe = new ExportImportIOException(
				StagingLocalServiceImpl.class.getName(), ioe);

			eiioe.setStagingRequestId(stagingRequestId);
			eiioe.setType(ExportImportIOException.PUBLISH_STAGING_REQUEST);

			throw eiioe;
		}
		finally {
			if (exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_PORTLET_REMOTE) {

				ExportImportThreadLocal.setPortletImportInProcess(false);
				ExportImportThreadLocal.setPortletStagingInProcess(false);
			}
			else {
				ExportImportThreadLocal.setLayoutImportInProcess(false);
				ExportImportThreadLocal.setLayoutStagingInProcess(false);
			}

			LocaleThreadLocal.setSiteDefaultLocale(siteDefaultLocale);

			FileUtil.delete(file);
		}
	}

	@Override
	public void updateStagingRequest(
			long userId, long stagingRequestId, String fileName, byte[] bytes)
		throws PortalException {

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			stagingRequestId);

		boolean indexReadOnly = IndexStatusManagerThreadLocal.isIndexReadOnly();

		IndexStatusManagerThreadLocal.setIndexReadOnly(true);

		try {
			PortletFileRepositoryUtil.addPortletFileEntry(
				folder.getGroupId(), userId, Group.class.getName(),
				folder.getGroupId(), _PORTLET_REPOSITORY_ID,
				folder.getFolderId(), new UnsyncByteArrayInputStream(bytes),
				fileName, ContentTypes.APPLICATION_ZIP, false);
		}
		finally {
			IndexStatusManagerThreadLocal.setIndexReadOnly(indexReadOnly);
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #publishStagingRequest(long, long, boolean, Map)}
	 */
	@Deprecated
	@Override
	public MissingReferences validateStagingRequest(
		long userId, long stagingRequestId, boolean privateLayout,
		Map<String, String[]> parameterMap) {

		return new MissingReferences();
	}

	protected void addDefaultLayoutSetBranch(
			long userId, long groupId, String groupName, boolean privateLayout,
			ServiceContext serviceContext)
		throws PortalException {

		String masterBranchDescription =
			LayoutSetBranchConstants.MASTER_BRANCH_DESCRIPTION_PUBLIC;

		if (privateLayout) {
			masterBranchDescription =
				LayoutSetBranchConstants.MASTER_BRANCH_DESCRIPTION_PRIVATE;
		}

		String description = LanguageUtil.format(
			PortalUtil.getSiteDefaultLocale(groupId), masterBranchDescription,
			groupName, false);

		try {
			serviceContext.setWorkflowAction(WorkflowConstants.STATUS_APPROVED);

			LayoutSetBranch layoutSetBranch =
				layoutSetBranchLocalService.addLayoutSetBranch(
					userId, groupId, privateLayout,
					LayoutSetBranchConstants.MASTER_BRANCH_NAME, description,
					true, LayoutSetBranchConstants.ALL_BRANCHES,
					serviceContext);

			List<LayoutRevision> layoutRevisions =
				layoutRevisionLocalService.getLayoutRevisions(
					layoutSetBranch.getLayoutSetBranchId(), false);

			for (LayoutRevision layoutRevision : layoutRevisions) {
				layoutRevisionLocalService.updateStatus(
					userId, layoutRevision.getLayoutRevisionId(),
					WorkflowConstants.STATUS_APPROVED, serviceContext);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to create master branch for " +
						(privateLayout ? "private" : "public") + " layouts",
					pe);
			}
		}
	}

	protected Group addStagingGroup(
			long userId, Group liveGroup, ServiceContext serviceContext)
		throws PortalException {

		long parentGroupId = liveGroup.getParentGroupId();

		Group stagingGroup = groupLocalService.addGroup(
			userId, parentGroupId, liveGroup.getClassName(),
			liveGroup.getClassPK(), liveGroup.getGroupId(),
			liveGroup.getNameMap(), liveGroup.getDescriptionMap(),
			liveGroup.getType(), liveGroup.isManualMembership(),
			liveGroup.getMembershipRestriction(), liveGroup.getFriendlyURL(),
			false, true, serviceContext);

		if (LanguageUtil.isInheritLocales(liveGroup.getGroupId())) {
			return stagingGroup;
		}

		UnicodeProperties liveTypeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		UnicodeProperties stagingTypeSettingsProperties =
			stagingGroup.getTypeSettingsProperties();

		stagingTypeSettingsProperties.setProperty(
			GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES,
			Boolean.FALSE.toString());
		stagingTypeSettingsProperties.setProperty(
			PropsKeys.LOCALES,
			liveTypeSettingsProperties.getProperty(PropsKeys.LOCALES));
		stagingTypeSettingsProperties.setProperty(
			"languageId",
			liveTypeSettingsProperties.getProperty(
				"languageId",
				LocaleUtil.toLanguageId(LocaleUtil.getDefault())));

		return groupLocalService.updateGroup(
			stagingGroup.getGroupId(),
			stagingTypeSettingsProperties.toString());
	}

	protected void deleteLayoutSetBranches(long groupId, boolean privateLayout)
		throws PortalException {

		// Find the latest layout revision for all the layouts

		Map<Long, LayoutRevision> layoutRevisions = new HashMap<>();

		List<LayoutSetBranch> layoutSetBranches =
			layoutSetBranchLocalService.getLayoutSetBranches(
				groupId, privateLayout);

		boolean publishedToLive = false;

		for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
			String lastPublishDateString = layoutSetBranch.getSettingsProperty(
				"last-publish-date");

			if (Validator.isNotNull(lastPublishDateString)) {
				publishedToLive = true;

				break;
			}
		}

		for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
			String lastPublishDateString = layoutSetBranch.getSettingsProperty(
				"last-publish-date");

			if (Validator.isNull(lastPublishDateString) && publishedToLive) {
				continue;
			}

			Date lastPublishDate = null;

			if (Validator.isNotNull(lastPublishDateString)) {
				lastPublishDate = new Date(
					GetterUtil.getLong(lastPublishDateString));
			}

			List<LayoutRevision> headLayoutRevisions =
				layoutRevisionLocalService.getLayoutRevisions(
					layoutSetBranch.getLayoutSetBranchId(), true);

			for (LayoutRevision headLayoutRevision : headLayoutRevisions) {
				LayoutRevision layoutRevision = layoutRevisions.get(
					headLayoutRevision.getPlid());

				if (layoutRevision == null) {
					layoutRevisions.put(
						headLayoutRevision.getPlid(), headLayoutRevision);

					continue;
				}

				Date statusDate = headLayoutRevision.getStatusDate();

				if (statusDate.after(layoutRevision.getStatusDate()) &&
					((lastPublishDate == null) ||
					 lastPublishDate.after(statusDate))) {

					layoutRevisions.put(
						headLayoutRevision.getPlid(), headLayoutRevision);
				}
			}
		}

		// Update all layouts based on their latest revision

		for (LayoutRevision layoutRevision : layoutRevisions.values()) {
			updateLayoutWithLayoutRevision(layoutRevision);
		}

		layoutSetBranchLocalService.deleteLayoutSetBranches(
			groupId, privateLayout, true);
	}

	protected void disableRemoteStaging(
			String remoteURL, long remoteGroupId, boolean forceDisable)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			remoteURL, user.getLogin(), user.getPassword(),
			user.isPasswordEncrypted());

		try {
			GroupServiceHttp.disableStaging(httpPrincipal, remoteGroupId);
		}
		catch (NoSuchGroupException nsge) {
			if (_log.isWarnEnabled()) {
				_log.warn("Remote live group was already deleted", nsge);
			}
		}
		catch (PrincipalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_PERMISSIONS);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (RemoteAuthException rae) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(rae, rae);
			}

			rae.setURL(remoteURL);

			throw rae;
		}
		catch (SystemException se) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(se, se);
			}

			if (!forceDisable) {
				RemoteExportException ree = new RemoteExportException(
					RemoteExportException.BAD_CONNECTION);

				ree.setURL(remoteURL);

				throw ree;
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Forcibly disable remote staging");
			}
		}
	}

	protected void enableRemoteStaging(
			HttpPrincipal httpPrincipal, long remoteGroupId)
		throws PortalException {

		try {
			GroupServiceHttp.enableStaging(httpPrincipal, remoteGroupId);
		}
		catch (NoSuchGroupException nsge) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsge, nsge);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_GROUP);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (PrincipalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_PERMISSIONS);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (RemoteAuthException rae) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(rae, rae);
			}

			rae.setURL(httpPrincipal.getUrl());

			throw rae;
		}
		catch (SystemException se) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(se, se);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.BAD_CONNECTION);

			ree.setURL(httpPrincipal.getUrl());

			throw ree;
		}
	}

	protected Group fetchRemoteGroup(HttpPrincipal httpPrincipal, long groupId)
		throws PortalException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			return GroupServiceHttp.getGroup(httpPrincipal, groupId);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected FileEntry fetchStagingRequestFileEntry(
			long stagingRequestId, Folder folder)
		throws PortalException {

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				folder.getGroupId(), folder.getFolderId(),
				getAssembledFileName(stagingRequestId));
		}
		catch (NoSuchFileEntryException nsfee) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsfee, nsfee);
			}

			return null;
		}
	}

	protected String getAssembledFileName(long stagingRequestId) {
		return _ASSEMBLED_LAR_PREFIX + String.valueOf(stagingRequestId) +
			".lar";
	}

	protected FileEntry getStagingRequestFileEntry(
			long userId, long stagingRequestId, Folder folder)
		throws PortalException {

		FileEntry stagingRequestFileEntry = fetchStagingRequestFileEntry(
			stagingRequestId, folder);

		if (stagingRequestFileEntry != null) {
			return stagingRequestFileEntry;
		}

		boolean indexReadOnly = IndexStatusManagerThreadLocal.isIndexReadOnly();

		IndexStatusManagerThreadLocal.setIndexReadOnly(true);

		File tempFile = FileUtil.createTempFile("lar");

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				tempFile)) {

			List<FileEntry> fileEntries =
				PortletFileRepositoryUtil.getPortletFileEntries(
					folder.getGroupId(), folder.getFolderId(),
					new RepositoryModelTitleComparator<FileEntry>(true));

			for (FileEntry fileEntry : fileEntries) {
				try {
					StreamUtil.transfer(
						fileEntry.getContentStream(),
						StreamUtil.uncloseable(fileOutputStream));
				}
				finally {
					PortletFileRepositoryUtil.deletePortletFileEntry(
						fileEntry.getFileEntryId());
				}
			}

			String checksum = FileUtil.getMD5Checksum(tempFile);

			if (!checksum.equals(folder.getName())) {
				ExportImportIOException eiioe = new ExportImportIOException(
					StagingLocalServiceImpl.class.getName());

				eiioe.setChecksum(checksum);
				eiioe.setType(ExportImportIOException.STAGING_REQUEST_CHECKSUM);

				throw eiioe;
			}

			PortletFileRepositoryUtil.addPortletFileEntry(
				folder.getGroupId(), userId, Group.class.getName(),
				folder.getGroupId(), _PORTLET_REPOSITORY_ID,
				folder.getFolderId(), tempFile,
				getAssembledFileName(stagingRequestId),
				ContentTypes.APPLICATION_ZIP, false);

			stagingRequestFileEntry = fetchStagingRequestFileEntry(
				stagingRequestId, folder);

			if (stagingRequestFileEntry == null) {
				throw new IOException();
			}

			return stagingRequestFileEntry;
		}
		catch (IOException ioe) {
			ExportImportIOException eiioe = new ExportImportIOException(
				StagingLocalServiceImpl.class.getName(), ioe);

			eiioe.setStagingRequestId(stagingRequestId);
			eiioe.setType(
				ExportImportIOException.STAGING_REQUEST_REASSEMBLE_FILE);

			throw eiioe;
		}
		finally {
			IndexStatusManagerThreadLocal.setIndexReadOnly(indexReadOnly);

			FileUtil.delete(tempFile);
		}
	}

	protected void setCommonStagingOptions(
		UnicodeProperties typeSettingsProperties,
		ServiceContext serviceContext) {

		typeSettingsProperties.putAll(
			PropertiesParamUtil.getProperties(
				serviceContext, StagingConstants.STAGED_PREFIX));
	}

	protected Layout updateLayoutWithLayoutRevision(
			LayoutRevision layoutRevision)
		throws PortalException{

		// Suppress the usage of the advice to get the latest layout to prevent
		// a StaleObjectStateException

		Layout layout = null;

		boolean stagingAdvicesThreadLocalEnabled =
			StagingAdvicesThreadLocal.isEnabled();

		try {
			StagingAdvicesThreadLocal.setEnabled(false);

			layout = layoutLocalService.fetchLayout(layoutRevision.getPlid());
		}
		finally {
			StagingAdvicesThreadLocal.setEnabled(
				stagingAdvicesThreadLocalEnabled);
		}

		updatePortletPreferences(layoutRevision, layout);

		layout.setUserId(layoutRevision.getUserId());
		layout.setUserName(layoutRevision.getUserName());
		layout.setCreateDate(layoutRevision.getCreateDate());
		layout.setModifiedDate(layoutRevision.getModifiedDate());
		layout.setPrivateLayout(layoutRevision.isPrivateLayout());
		layout.setName(layoutRevision.getName());
		layout.setTitle(layoutRevision.getTitle());
		layout.setDescription(layoutRevision.getDescription());
		layout.setKeywords(layoutRevision.getKeywords());
		layout.setRobots(layoutRevision.getRobots());
		layout.setTypeSettings(layoutRevision.getTypeSettings());
		layout.setIconImageId(layoutRevision.getIconImageId());
		layout.setThemeId(layoutRevision.getThemeId());
		layout.setColorSchemeId(layoutRevision.getColorSchemeId());
		layout.setCss(layoutRevision.getCss());

		return layoutLocalService.updateLayout(layout);
	}

	protected void updatePortletPreferences(
		LayoutRevision layoutRevision, Layout layout) {

		portletPreferencesLocalService.deletePortletPreferencesByPlid(
			layout.getPlid());

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferencesByPlid(
				layoutRevision.getLayoutRevisionId());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			portletPreferencesLocalService.addPortletPreferences(
				layoutRevision.getCompanyId(), portletPreferences.getOwnerId(),
				portletPreferences.getOwnerType(), layout.getPlid(),
				portletPreferences.getPortletId(), null,
				portletPreferences.getPreferences());
		}
	}

	protected void updateStagedPortlets(
			String remoteURL, long remoteGroupId,
			UnicodeProperties typeSettingsProperties)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			remoteURL, user.getLogin(), user.getPassword(),
			user.isPasswordEncrypted());

		Map<String, String> stagedPortletIds = new HashMap<>();

		for (String key : typeSettingsProperties.keySet()) {
			if (key.startsWith(StagingConstants.STAGED_PORTLET)) {
				stagedPortletIds.put(
					key, typeSettingsProperties.getProperty(key));
			}
		}

		try {
			GroupServiceHttp.updateStagedPortlets(
				httpPrincipal, remoteGroupId, stagedPortletIds);
		}
		catch (NoSuchGroupException nsge) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsge, nsge);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_GROUP);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (PrincipalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_PERMISSIONS);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (RemoteAuthException rae) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(rae, rae);
			}

			rae.setURL(remoteURL);

			throw rae;
		}
		catch (SystemException se) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(se, se);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.BAD_CONNECTION);

			ree.setURL(remoteURL);

			throw ree;
		}
	}

	private static final String _ASSEMBLED_LAR_PREFIX = "assembled_";

	private static final String _PORTLET_REPOSITORY_ID = "134";

	private static final Log _log = LogFactoryUtil.getLog(
		StagingLocalServiceImpl.class);

}