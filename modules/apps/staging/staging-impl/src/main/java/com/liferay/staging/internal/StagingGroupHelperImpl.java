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

package com.liferay.staging.internal;

import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.staging.StagingURLHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.service.http.GroupServiceHttp;
import com.liferay.staging.StagingGroupHelper;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = StagingGroupHelper.class)
public class StagingGroupHelperImpl implements StagingGroupHelper {

	@Override
	public Group fetchLiveGroup(Group group) {
		if (isLocalStagingGroup(group)) {
			return fetchLocalLiveGroup(group);
		}

		if (isRemoteStagingGroup(group)) {
			return fetchRemoteLiveGroup(group);
		}

		return null;
	}

	@Override
	public Group fetchLiveGroup(long groupId) {
		return fetchLiveGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public Group fetchLocalLiveGroup(Group group) {
		if (!isLocalStagingGroup(group)) {
			return null;
		}

		group = _getParentGroupForScopeGroup(group);

		return _groupLocalService.fetchGroup(group.getLiveGroupId());
	}

	@Override
	public Group fetchLocalLiveGroup(long groupId) {
		return fetchLocalLiveGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public Group fetchLocalStagingGroup(Group group) {
		if (!isLocalLiveGroup(group)) {
			return null;
		}

		group = _getParentGroupForScopeGroup(group);

		return _groupLocalService.fetchStagingGroup(group.getGroupId());
	}

	@Override
	public Group fetchLocalStagingGroup(long groupId) {
		return fetchLocalStagingGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public Group fetchRemoteLiveGroup(Group group) {
		if (!isRemoteStagingGroup(group)) {
			return null;
		}

		group = _getParentGroupForScopeGroup(group);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			User user = permissionChecker.getUser();

			String remoteURL = _stagingURLHelper.buildRemoteURL(
				group.getTypeSettingsProperties());

			HttpPrincipal httpPrincipal = new HttpPrincipal(
				remoteURL, user.getLogin(), user.getPassword(),
				user.isPasswordEncrypted());

			long remoteGroupId = GetterUtil.getLong(
				_getTypeSettingsProperty(group, "remoteGroupId"));

			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			return GroupServiceHttp.getGroup(httpPrincipal, remoteGroupId);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get remote live group: " + pe.getMessage());
			}

			return null;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public Group fetchRemoteLiveGroup(long groupId) {
		return fetchRemoteLiveGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public boolean isLiveGroup(Group group) {
		if (isLocalLiveGroup(group) || isRemoteLiveGroup(group)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLiveGroup(long groupId) {
		return isLiveGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public boolean isLocalLiveGroup(Group group) {
		if (group == null) {
			return false;
		}

		group = _getParentGroupForScopeGroup(group);

		Group stagingGroup = _groupLocalService.fetchStagingGroup(
			group.getGroupId());

		if (stagingGroup == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isLocalLiveGroup(long groupId) {
		return isLocalLiveGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public boolean isLocalStagingGroup(Group group) {
		if (group == null) {
			return false;
		}

		group = _getParentGroupForScopeGroup(group);

		if (group.getLiveGroupId() == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isLocalStagingGroup(long groupId) {
		return isLocalStagingGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public boolean isLocalStagingOrLocalLiveGroup(Group group) {
		if (isLocalStagingGroup(group) || isLocalLiveGroup(group)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLocalStagingOrLocalLiveGroup(long groupId) {
		return isLocalStagingOrLocalLiveGroup(
			_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public boolean isRemoteLiveGroup(Group group) {
		if (group == null) {
			return false;
		}

		group = _getParentGroupForScopeGroup(group);

		if (group.getRemoteStagingGroupCount() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRemoteLiveGroup(long groupId) {
		return isRemoteLiveGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public boolean isRemoteStagingGroup(Group group) {
		if (group == null) {
			return false;
		}

		group = _getParentGroupForScopeGroup(group);

		return GetterUtil.getBoolean(
			_getTypeSettingsProperty(group, "stagedRemotely"));
	}

	@Override
	public boolean isRemoteStagingGroup(long groupId) {
		return isRemoteStagingGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public boolean isRemoteStagingOrRemoteLiveGroup(Group group) {
		if (isRemoteStagingGroup(group) || isRemoteLiveGroup(group)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRemoteStagingOrRemoteLiveGroup(long groupId) {
		return isRemoteStagingOrRemoteLiveGroup(
			_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public boolean isStagedPortlet(Group group, String portletId) {
		if (!isStagingGroup(group) && !isLiveGroup(group)) {
			return false;
		}

		return group.isStagedPortlet(portletId);
	}

	@Override
	public boolean isStagedPortlet(long groupId, String portletId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		return isStagedPortlet(group, portletId);
	}

	@Override
	public boolean isStagedPortletData(long groupId, String className) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return true;
		}

		List<Portlet> dataSiteLevelPortlets = Collections.emptyList();

		try {
			dataSiteLevelPortlets =
				_exportImportHelper.getDataSiteLevelPortlets(
					group.getCompanyId(), true);
		}
		catch (Exception e) {
			return true;
		}

		for (Portlet dataSiteLevelPortlet : dataSiteLevelPortlets) {
			PortletDataHandler portletDataHandler =
				dataSiteLevelPortlet.getPortletDataHandlerInstance();

			if (ArrayUtil.contains(
					portletDataHandler.getClassNames(), className)) {

				return isStagedPortlet(
					groupId, dataSiteLevelPortlet.getRootPortletId());
			}
		}

		return true;
	}

	@Override
	public boolean isStagingGroup(Group group) {
		if (isLocalStagingGroup(group) || isRemoteStagingGroup(group)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isStagingGroup(long groupId) {
		return isStagingGroup(_groupLocalService.fetchGroup(groupId));
	}

	@Override
	public boolean isStagingOrLiveGroup(Group group) {
		if (isStagingGroup(group) || isLiveGroup(group)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isStagingOrLiveGroup(long groupId) {
		return isStagingOrLiveGroup(_groupLocalService.fetchGroup(groupId));
	}

	private Group _getParentGroupForScopeGroup(Group group) {
		if (group.isLayout()) {
			return group.getParentGroup();
		}

		return group;
	}

	private String _getTypeSettingsProperty(Group group, String key) {
		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingGroupHelperImpl.class);

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private StagingURLHelper _stagingURLHelper;

}