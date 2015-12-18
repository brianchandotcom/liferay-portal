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

package com.liferay.portlet.exportimport.staging;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.MissingReference;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;

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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalServiceUtil#
	 *             checkDefaultLayoutSetBranches(long, Group, boolean, boolean,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	public void checkDefaultLayoutSetBranches(
			long userId, Group liveGroup, boolean branchingPublic,
			boolean branchingPrivate, boolean remote,
			ServiceContext serviceContext)
		throws PortalException;

	public void copyFromLive(PortletRequest PortletRequest)
		throws PortalException;

	public void copyFromLive(PortletRequest PortletRequest, Portlet portlet)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishPortlet(long, long,
	 *             long, long, long, String, Map)}
	 */
	@Deprecated
	public void copyPortlet(
			PortletRequest PortletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws PortalException;

	public void copyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public void copyRemoteLayouts(long exportImportConfigurationId)
		throws PortalException;

	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #copyRemoteLayouts(long,
	 *             boolean, Map, Map, String, int, String, boolean, long,
	 *             boolean)}
	 */
	@Deprecated
	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate)
		throws PortalException;

	public void deleteLastImportSettings(Group liveGroup, boolean privateLayout)
		throws PortalException;

	public void deleteRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid);

	public void deleteRecentLayoutRevisionId(
		long userId, long layoutSetBranchId, long plid);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #deleteRecentLayoutRevisionId(long, long, long)}
	 */
	@Deprecated
	public void deleteRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	public void disableStaging(
			Group scopeGroup, Group liveGroup, ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	public void disableStaging(Group liveGroup, ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	public void disableStaging(
			PortletRequest portletRequest, Group scopeGroup, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	public void disableStaging(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#enableLocalStaging(
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
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#enableRemoteStaging(
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
		Locale locale, Map<String, MissingReference> missingReferences);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getErrorMessagesJSONArray(Locale, Map<String,
	 *             MissingReference>)}
	 */
	@Deprecated
	public JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap);

	public JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e,
		ExportImportConfiguration exportImportConfiguration);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getExceptionMessagesJSONObject(Locale, Exception,
	 *             ExportImportConfiguration)}
	 */
	@Deprecated
	public JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap);

	public Group getLiveGroup(long groupId);

	public long getLiveGroupId(long groupId);

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportHelperUtil#getMissingParentLayouts(
	 *             Layout, long)}
	 */
	@Deprecated
	public List<Layout> getMissingParentLayouts(Layout layout, long liveGroupId)
		throws PortalException;

	public long getRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid)
		throws PortalException;

	public long getRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws PortalException;

	public long getRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId);

	public long getRecentLayoutSetBranchId(User user, long layoutSetId);

	public String getSchedulerGroupName(String destinationName, long groupId);

	public String getStagedPortletId(String portletId);

	public long[] getStagingAndLiveGroupIds(long groupId)
		throws PortalException;

	public Group getStagingGroup(long groupId);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             )}
	 */
	@Deprecated
	public Map<String, String[]> getStagingParameters();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             PortletRequest)}
	 */
	@Deprecated
	public Map<String, String[]> getStagingParameters(
		PortletRequest PortletRequest);

	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getWarningMessagesJSONArray(Locale, Map<String,
	 *             MissingReference>)}
	 */
	@Deprecated
	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap);

	public WorkflowTask getWorkflowTask(
			long userId, LayoutRevision layoutRevision)
		throws PortalException;

	public boolean hasWorkflowTask(long userId, LayoutRevision layoutRevision)
		throws PortalException;

	public boolean isIncomplete(Layout layout, long layoutSetBranchId);

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	public void lockGroup(long userId, long groupId) throws PortalException;

	public void publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws PortalException;

	public void publishLayouts(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public void publishLayouts(long userId, long exportImportConfigurationId)
		throws PortalException;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, long[], Map)}
	 */
	@Deprecated
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, long[], Map)}
	 */
	@Deprecated
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, Map)}
	 */
	@Deprecated
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException;

	public void publishPortlet(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public void publishPortlet(long userId, long exportImportConfigurationId)
		throws PortalException;

	public void publishPortlet(
			long userId, long sourceGroupId, long targetGroupId,
			long sourcePlid, long targetPlid, String portletId,
			Map<String, String[]> parameterMap)
		throws PortalException;

	public void publishToLive(PortletRequest PortletRequest)
		throws PortalException;

	public void publishToLive(PortletRequest PortletRequest, Portlet portlet)
		throws PortalException;

	public void publishToRemote(PortletRequest PortletRequest)
		throws PortalException;

	public void scheduleCopyFromLive(PortletRequest PortletRequest)
		throws PortalException;

	public void schedulePublishToLive(PortletRequest PortletRequest)
		throws PortalException;

	public void schedulePublishToRemote(PortletRequest PortletRequest)
		throws PortalException;

	public void setRecentLayoutBranchId(
			HttpServletRequest request, long layoutSetBranchId, long plid,
			long layoutBranchId)
		throws PortalException;

	public void setRecentLayoutBranchId(
			User user, long layoutSetBranchId, long plid, long layoutBranchId)
		throws PortalException;

	public void setRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid,
			long layoutRevisionId)
		throws PortalException;

	public void setRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid, long layoutRevisionId)
		throws PortalException;

	public void setRecentLayoutSetBranchId(
			HttpServletRequest request, long layoutSetId,
			long layoutSetBranchId)
		throws PortalException;

	public void setRecentLayoutSetBranchId(
			User user, long layoutSetId, long layoutSetBranchId)
		throws PortalException;

	public String stripProtocolFromRemoteAddress(String remoteAddress);

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	public void unlockGroup(long groupId);

	public void unscheduleCopyFromLive(PortletRequest PortletRequest)
		throws PortalException;

	public void unschedulePublishToLive(PortletRequest PortletRequest)
		throws PortalException;

	public void unschedulePublishToRemote(PortletRequest PortletRequest)
		throws PortalException;

	public void updateLastImportSettings(
			Element layoutElement, Layout layout,
			PortletDataContext portletDataContext)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportDateUtil#updateLastPublishDate(
	 *             long, boolean, com.liferay.portal.kernel.util.DateRange,
	 *             Date)}
	 */
	@Deprecated
	public void updateLastPublishDate(
			long sourceGroupId, boolean privateLayout, Date lastPublishDate)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportDateUtil#updateLastPublishDate(
	 *             String, PortletPreferences,
	 *             com.liferay.portal.kernel.util.DateRange, Date)}
	 */
	@Deprecated
	public void updateLastPublishDate(
			String portletId, PortletPreferences portletPreferences,
			Date lastPublishDate)
		throws PortalException;

	public void updateStaging(PortletRequest PortletRequest, Group liveGroup)
		throws PortalException;

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