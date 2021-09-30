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

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.exportimport.service.base.StagingServiceBaseImpl;

import java.io.Serializable;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class StagingServiceImpl extends StagingServiceBaseImpl {

	@Override
	public void cleanUpStagingRequest(long stagingRequestId)
		throws PortalException {

		boolean stagingInProcessOnLive =
			ExportImportThreadLocal.isStagingInProcessOnRemoteLive();

		ExportImportThreadLocal.setStagingInProcessOnRemoteLive(true);

		try {
			checkPermission(stagingRequestId);

			stagingLocalService.cleanUpStagingRequest(stagingRequestId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"StagingServiceImpl#cleanUpStagingRequest(" +
						stagingRequestId + ")",
					portalException);
			}

			throw portalException;
		}
		finally {
			ExportImportThreadLocal.setStagingInProcessOnRemoteLive(
				stagingInProcessOnLive);
		}
	}

	@Override
	public long createStagingRequest(long groupId, String checksum)
		throws PortalException {

		boolean stagingInProcessOnLive =
			ExportImportThreadLocal.isStagingInProcessOnRemoteLive();

		ExportImportThreadLocal.setStagingInProcessOnRemoteLive(true);

		try {
			GroupPermissionUtil.check(
				getPermissionChecker(), groupId,
				ActionKeys.EXPORT_IMPORT_LAYOUTS);

			return stagingLocalService.createStagingRequest(
				getUserId(), groupId, checksum);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"StagingServiceImpl#createStagingRequest(", groupId,
						", ", checksum, ")"),
					portalException);
			}

			throw portalException;
		}
		finally {
			ExportImportThreadLocal.setStagingInProcessOnRemoteLive(
				stagingInProcessOnLive);
		}
	}

	@Override
	public void enableLocalStaging(
			long groupId, boolean branchingPublic, boolean branchingPrivate,
			ServiceContext serviceContext)
		throws PortalException {

		Group liveGroup = _groupLocalService.getGroup(groupId);

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_STAGING);

		stagingLocalService.enableLocalStaging(
			getUserId(), liveGroup, branchingPublic, branchingPrivate,
			serviceContext);
	}

	@Override
	public void enableRemoteStaging(
			long groupId, boolean branchingPublic, boolean branchingPrivate,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			ServiceContext serviceContext)
		throws PortalException {

		Group stagingGroup = _groupLocalService.getGroup(groupId);

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_STAGING);

		stagingLocalService.enableRemoteStaging(
			getUserId(), stagingGroup, branchingPublic, branchingPrivate,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, serviceContext);
	}

	@Override
	public boolean hasRemoteLayout(
			String uuid, long groupId, boolean privateLayout)
		throws PortalException {

		try {
			GroupPermissionUtil.check(
				getPermissionChecker(), groupId,
				ActionKeys.EXPORT_IMPORT_LAYOUTS);

			return _layoutLocalService.hasLayout(uuid, groupId, privateLayout);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"StagingServiceImpl#hasRemoteLayout(", uuid, ", ",
						groupId, ", ", privateLayout, ")"),
					portalException);
			}

			throw portalException;
		}
	}

	@Override
	public void propagateExportImportLifecycleEvent(
			int code, int processFlag, String processId,
			List<Serializable> arguments)
		throws PortalException {

		try {
			Serializable serializable = arguments.get(0);

			long groupId = GroupConstants.DEFAULT_LIVE_GROUP_ID;

			if (serializable instanceof PortletDataContext) {
				PortletDataContext portletDataContext =
					(PortletDataContext)serializable;

				groupId = portletDataContext.getGroupId();
			}
			else if (serializable instanceof ExportImportConfiguration) {
				ExportImportConfiguration exportImportConfiguration =
					(ExportImportConfiguration)serializable;

				groupId = MapUtil.getLong(
					exportImportConfiguration.getSettingsMap(),
					"targetGroupId");
			}

			GroupPermissionUtil.check(
				getPermissionChecker(), groupId,
				ActionKeys.EXPORT_IMPORT_LAYOUTS);

			ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
				code, processFlag, processId,
				arguments.toArray(new Serializable[0]));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"StagingServiceImpl#",
						"propagateExportImportLifecycleEvent(", code,
						StringPool.COMMA_AND_SPACE, processFlag,
						StringPool.COMMA_AND_SPACE, processId,
						StringPool.COMMA_AND_SPACE, arguments,
						StringPool.CLOSE_PARENTHESIS),
					portalException);
			}

			throw portalException;
		}
	}

	@Override
	public MissingReferences publishStagingRequest(
			long stagingRequestId,
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		boolean stagingInProcessOnLive =
			ExportImportThreadLocal.isStagingInProcessOnRemoteLive();

		ExportImportThreadLocal.setStagingInProcessOnRemoteLive(true);

		try {
			checkPermission(stagingRequestId);

			return stagingLocalService.publishStagingRequest(
				getUserId(), stagingRequestId, exportImportConfiguration);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"StagingServiceImpl#publishStagingRequest(",
						stagingRequestId, ", ", exportImportConfiguration, ")"),
					portalException);
			}

			throw portalException;
		}
		finally {
			ExportImportThreadLocal.setStagingInProcessOnRemoteLive(
				stagingInProcessOnLive);
		}
	}

	@Override
	public void updateStagingRequest(
			long stagingRequestId, String fileName, byte[] bytes)
		throws PortalException {

		boolean stagingInProcessOnLive =
			ExportImportThreadLocal.isStagingInProcessOnRemoteLive();

		ExportImportThreadLocal.setStagingInProcessOnRemoteLive(true);

		try {
			checkPermission(stagingRequestId);

			stagingLocalService.updateStagingRequest(
				getUserId(), stagingRequestId, fileName, bytes);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"StagingServiceImpl#updateStagingRequest(",
						stagingRequestId, ", ", fileName, ", ", bytes.length,
						"bytes)"),
					portalException);
			}

			throw portalException;
		}
		finally {
			ExportImportThreadLocal.setStagingInProcessOnRemoteLive(
				stagingInProcessOnLive);
		}
	}

	protected void checkPermission(long stagingRequestId)
		throws PortalException {

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			stagingRequestId);

		GroupPermissionUtil.check(
			getPermissionChecker(), folder.getGroupId(),
			ActionKeys.EXPORT_IMPORT_LAYOUTS);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingServiceImpl.class);

	@BeanReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

	@BeanReference(type = LayoutLocalService.class)
	private LayoutLocalService _layoutLocalService;

}