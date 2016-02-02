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

package com.liferay.exportimport.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.messaging.LayoutsLocalPublisherRequest;
import com.liferay.portal.messaging.LayoutsRemotePublisherRequest;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalService;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class UpgradePublisherRequest extends UpgradeProcess {

	public UpgradePublisherRequest(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService,
		GroupLocalService groupLocalService,
		SchedulerEngineHelper schedulerEngineHelper,
		UserLocalService userLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
		_groupLocalService = groupLocalService;
		_schedulerEngineHelper = schedulerEngineHelper;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (Group group : getGroups()) {
			updateScheduledPublications(group);
		}
	}

	protected List<Group> getGroups() {
		List<Group> groups = _groupLocalService.getGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<Group> filteredGroups = new ArrayList<>(groups.size());

		for (Group group : groups) {
			if (!group.isStaged() && !group.hasLocalOrRemoteStagingGroup()) {
				continue;
			}

			filteredGroups.add(group);
		}

		return filteredGroups;
	}

	protected String getSchedulerGroupName(long groupId, boolean localStaging)
		throws PortalException {

		String destinationName = DestinationNames.LAYOUTS_LOCAL_PUBLISHER;

		if (!localStaging) {
			destinationName = DestinationNames.LAYOUTS_REMOTE_PUBLISHER;
		}

		return StagingUtil.getSchedulerGroupName(destinationName, groupId);
	}

	protected void updateScheduledLocalPublication(
			SchedulerResponse schedulerResponse)
		throws PortalException {

		Message message = schedulerResponse.getMessage();

		LayoutsLocalPublisherRequest publisherRequest =
			(LayoutsLocalPublisherRequest)message.getPayload();

		User user = _userLocalService.getUser(publisherRequest.getUserId());

		Map<String, Serializable> publishLayoutLocalSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildPublishLayoutLocalSettingsMap(
					user, publisherRequest.getSourceGroupId(),
					publisherRequest.getTargetGroupId(),
					publisherRequest.isPrivateLayout(),
					ExportImportHelperUtil.getLayoutIds(
						publisherRequest.getLayoutIdMap()),
					publisherRequest.getParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					user.getUserId(), schedulerResponse.getDescription(),
					ExportImportConfigurationConstants.
						TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL,
					publishLayoutLocalSettingsMap);

		_schedulerEngineHelper.schedule(
			schedulerResponse.getTrigger(), StorageType.PERSISTED,
			schedulerResponse.getDescription(),
			DestinationNames.LAYOUTS_LOCAL_PUBLISHER,
			exportImportConfiguration.getExportImportConfigurationId(), 0);
	}

	protected void updateScheduledPublications(Group group)
		throws PortalException {

		boolean localStaging = true;

		if (group.isStagedRemotely() || group.hasRemoteStagingGroup()) {
			localStaging = false;
		}

		List<SchedulerResponse> scheduledJobs =
			_schedulerEngineHelper.getScheduledJobs(
				getSchedulerGroupName(group.getGroupId(), localStaging),
				StorageType.PERSISTED);

		for (SchedulerResponse schedulerResponse : scheduledJobs) {
			if (localStaging) {
				updateScheduledLocalPublication(schedulerResponse);
			}
			else {
				updateScheduleRemotePublication(schedulerResponse);
			}
		}
	}

	protected void updateScheduleRemotePublication(
			SchedulerResponse schedulerResponse)
		throws PortalException {

		Message message = schedulerResponse.getMessage();

		LayoutsRemotePublisherRequest publisherRequest =
			(LayoutsRemotePublisherRequest)message.getPayload();

		User user = _userLocalService.getUser(publisherRequest.getUserId());

		Map<String, Serializable> publishLayoutRemoteSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildPublishLayoutRemoteSettingsMap(
					user, publisherRequest.getSourceGroupId(),
					publisherRequest.isPrivateLayout(),
					publisherRequest.getLayoutIdMap(),
					publisherRequest.getParameterMap(),
					publisherRequest.getRemoteAddress(),
					publisherRequest.getRemotePort(),
					publisherRequest.getRemotePathContext(),
					publisherRequest.isSecureConnection(),
					publisherRequest.getRemoteGroupId(),
					publisherRequest.isRemotePrivateLayout());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					user.getUserId(), schedulerResponse.getDescription(),
					ExportImportConfigurationConstants.
						TYPE_SCHEDULED_PUBLISH_LAYOUT_REMOTE,
					publishLayoutRemoteSettingsMap);

		_schedulerEngineHelper.schedule(
			schedulerResponse.getTrigger(), StorageType.PERSISTED,
			schedulerResponse.getDescription(),
			DestinationNames.LAYOUTS_REMOTE_PUBLISHER,
			exportImportConfiguration.getExportImportConfigurationId(), 0);
	}

	private final ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;
	private final GroupLocalService _groupLocalService;
	private final SchedulerEngineHelper _schedulerEngineHelper;
	private final UserLocalService _userLocalService;

}