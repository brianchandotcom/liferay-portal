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

package com.liferay.portal.kernel.staging;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
@ProviderType
public interface Staging {

	public String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId, boolean privateLayout);

	public String buildRemoteURL(UnicodeProperties typeSettingsProperties);

	public void checkDefaultLayoutSetBranches(
			long userId, Group liveGroup, boolean branchingPublic,
			boolean branchingPrivate, boolean remote,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public void copyFromLive(PortletRequest PortletRequest)
		throws PortalException, SystemException;

	public void copyFromLive(PortletRequest PortletRequest, Portlet portlet)
		throws PortalException, SystemException;

	public void copyPortlet(
			PortletRequest PortletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws PortalException, SystemException;

	public void copyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException, SystemException;

	public void copyRemoteLayouts(long exportImportConfigurationId)
		throws PortalException, SystemException;

	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate)
		throws PortalException, SystemException;

	public void deleteLastImportSettings(Group liveGroup, boolean privateLayout)
		throws PortalException, SystemException;

	public void deleteRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid)
		throws SystemException;

	public void deleteRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	public void disableStaging(
			Group scopeGroup, Group liveGroup, ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	public void disableStaging(Group liveGroup, ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	public void disableStaging(
			PortletRequest portletRequest, Group scopeGroup, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	public void disableStaging(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#enableLocalStaging(
	 *             long, Group, boolean, boolean, ServiceContext)}
	 */
	@Deprecated
	public void enableLocalStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#enableRemoteStaging(
	 *             long, Group, boolean, boolean, String, int, String, boolean,
	 *             long, ServiceContext)}
	 */
	@Deprecated
	public void enableRemoteStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			ServiceContext serviceContext)
		throws Exception;

	public JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap);

	public JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap);

	public Group getLiveGroup(long groupId)
		throws PortalException, SystemException;

	public long getLiveGroupId(long groupId)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             com.liferay.portal.kernel.lar.ExportImportHelperUtil#getMissingParentLayouts(
	 *             Layout, long)}
	 */
	@Deprecated
	public List<Layout> getMissingParentLayouts(Layout layout, long liveGroupId)
		throws PortalException, SystemException;

	public long getRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid)
		throws PortalException, SystemException;

	public long getRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws PortalException, SystemException;

	public long getRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId);

	public long getRecentLayoutSetBranchId(User user, long layoutSetId)
		throws SystemException;

	public String getSchedulerGroupName(String destinationName, long groupId);

	public String getStagedPortletId(String portletId);

	public Map<String, String[]> getStagingParameters();

	public Map<String, String[]> getStagingParameters(
		PortletRequest PortletRequest);

	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap);

	public WorkflowTask getWorkflowTask(
			long userId, LayoutRevision layoutRevision)
		throws PortalException, SystemException;

	public boolean hasWorkflowTask(long userId, LayoutRevision layoutRevision)
		throws PortalException, SystemException;

	public boolean isIncomplete(Layout layout, long layoutSetBranchId);

	public void lockGroup(long userId, long groupId)
		throws PortalException, SystemException;

	public void publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws PortalException, SystemException;

	public void publishLayouts(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException, SystemException;

	public void publishLayouts(long userId, long exportImportConfigurationId)
		throws PortalException, SystemException;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, long[], Map, Date, Date)}
	 */
	@Deprecated
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException, SystemException;

	public void publishToLive(PortletRequest PortletRequest)
		throws PortalException, SystemException;

	public void publishToLive(PortletRequest PortletRequest, Portlet portlet)
		throws PortalException, SystemException;

	public void publishToRemote(PortletRequest PortletRequest)
		throws PortalException, SystemException;

	public void scheduleCopyFromLive(PortletRequest PortletRequest)
		throws PortalException, SystemException;

	public void schedulePublishToLive(PortletRequest PortletRequest)
		throws PortalException, SystemException;

	public void schedulePublishToRemote(PortletRequest PortletRequest)
		throws PortalException, SystemException;

	public void setRecentLayoutBranchId(
			HttpServletRequest request, long layoutSetBranchId, long plid,
			long layoutBranchId)
		throws SystemException;

	public void setRecentLayoutBranchId(
			User user, long layoutSetBranchId, long plid, long layoutBranchId)
		throws SystemException;

	public void setRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid,
			long layoutRevisionId)
		throws SystemException;

	public void setRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid, long layoutRevisionId)
		throws SystemException;

	public void setRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId, long layoutSetBranchId);

	public void setRecentLayoutSetBranchId(
			User user, long layoutSetId, long layoutSetBranchId)
		throws SystemException;

	public String stripProtocolFromRemoteAddress(String remoteAddress);

	public void unlockGroup(long groupId) throws SystemException;

	public void unscheduleCopyFromLive(PortletRequest PortletRequest)
		throws PortalException, SystemException;

	public void unschedulePublishToLive(PortletRequest PortletRequest)
		throws PortalException, SystemException;

	public void unschedulePublishToRemote(PortletRequest PortletRequest)
		throws PortalException, SystemException;

	public void updateLastImportSettings(
			Element layoutElement, Layout layout,
			PortletDataContext portletDataContext)
		throws PortalException, SystemException;

	public void updateLastPublishDate(
			long sourceGroupId, boolean privateLayout, Date lastPublishDate)
		throws PortalException, SystemException;

	public void updateLastPublishDate(
			String portletId, PortletPreferences portletPreferences,
			Date lastPublishDate)
		throws PortalException, SystemException;

	public void updateStaging(PortletRequest PortletRequest, Group liveGroup)
		throws PortalException, SystemException;

	public void validateRemote(
			long groupId, String remoteAddress, int remotePort,
			String remotePathContext, boolean secureConnection,
			long remoteGroupId)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #validateRemote(long, String,
	 *             int, String, boolean, long)}
	 */
	@Deprecated
	public void validateRemote(
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId)
		throws PortalException;

}