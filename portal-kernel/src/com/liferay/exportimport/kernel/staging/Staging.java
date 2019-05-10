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

package com.liferay.exportimport.kernel.staging;

import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.xml.Element;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Raymond Augé
 */
@ProviderType
public interface Staging {

	public <T extends BaseModel> void addModelToChangesetCollection(T model)
		throws PortalException;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             StagingURLHelper#buildRemoteURL(ExportImportConfiguration)}
	 */
	@Deprecated
	public String buildRemoteURL(
		ExportImportConfiguration exportImportConfiguration);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             StagingURLHelper#buildRemoteURL(String, int, String,
	 *             boolean)}
	 */
	@Deprecated
	public String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getRemoteSiteURL(Group, boolean)}
	 */
	@Deprecated
	public String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId, boolean privateLayout);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             StagingURLHelper#buildRemoteURL(UnicodeProperties)}
	 */
	@Deprecated
	public String buildRemoteURL(UnicodeProperties typeSettingsProperties);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.service.StagingLocalServiceUtil#
	 *             checkDefaultLayoutSetBranches(long, Group, boolean, boolean,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	public void checkDefaultLayoutSetBranches(
			long userId, Group liveGroup, boolean branchingPublic,
			boolean branchingPrivate, boolean remote,
			ServiceContext serviceContext)
		throws PortalException;

	public long copyFromLive(PortletRequest portletRequest)
		throws PortalException;

	public long copyFromLive(PortletRequest portletRequest, Portlet portlet)
		throws PortalException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #publishPortlet(long, long, long, long, long, String, Map)}
	 */
	@Deprecated
	public long copyPortlet(
			PortletRequest portletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws PortalException;

	public long copyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long copyRemoteLayouts(long exportImportConfigurationId)
		throws PortalException;

	public long copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout)
		throws PortalException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #copyRemoteLayouts(long, boolean, Map, Map, String, int,
	 *             String, boolean, long, boolean)}
	 */
	@Deprecated
	public long copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate)
		throws PortalException;

	public long copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, String name,
			Map<String, String[]> parameterMap, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection,
			long remoteGroupId, boolean remotePrivateLayout)
		throws PortalException;

	public void deleteLastImportSettings(Group liveGroup, boolean privateLayout)
		throws PortalException;

	public void deleteRecentLayoutRevisionId(
		HttpServletRequest httpServletRequest, long layoutSetBranchId,
		long plid);

	public void deleteRecentLayoutRevisionId(
		long userId, long layoutSetBranchId, long plid);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #deleteRecentLayoutRevisionId(long, long, long)}
	 */
	@Deprecated
	public void deleteRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid);

	public JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
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
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getExceptionMessagesJSONObject(Locale, Exception,
	 *             ExportImportConfiguration)}
	 */
	@Deprecated
	public JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap);

	public Group getLiveGroup(Group group);

	public Group getLiveGroup(long groupId);

	public long getLiveGroupId(long groupId);

	/**
	 * @deprecated As of Wilberforce (7.0.x), moved to {@link
	 *             com.liferay.exportimport.kernel.lar.ExportImportHelperUtil#getMissingParentLayouts(
	 *             Layout, long)}
	 */
	@Deprecated
	public List<Layout> getMissingParentLayouts(Layout layout, long liveGroupId)
		throws PortalException;

	public Group getPermissionStagingGroup(Group group);

	public long getRecentLayoutRevisionId(
			HttpServletRequest httpServletRequest, long layoutSetBranchId,
			long plid)
		throws PortalException;

	public long getRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws PortalException;

	public long getRecentLayoutSetBranchId(
		HttpServletRequest httpServletRequest, long layoutSetId);

	public long getRecentLayoutSetBranchId(User user, long layoutSetId);

	public long getRemoteLayoutPlid(long userId, long stagingGroupId, long plid)
		throws PortalException;

	public String getRemoteSiteURL(Group stagingGroup, boolean privateLayout)
		throws PortalException;

	public String getSchedulerGroupName(String destinationName, long groupId);

	public String getStagedPortletId(String portletId);

	public long[] getStagingAndLiveGroupIds(long groupId)
		throws PortalException;

	public Group getStagingGroup(long groupId);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             )}
	 */
	@Deprecated
	public Map<String, String[]> getStagingParameters();

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             PortletRequest)}
	 */
	@Deprecated
	public Map<String, String[]> getStagingParameters(
		PortletRequest portletRequest);

	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
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

	public boolean isChangeTrackingEnabled(long companyId);

	public boolean isGroupAccessible(Group group, Group fromGroup);

	public boolean isGroupAccessible(long groupId, long fromGroupId)
		throws PortalException;

	public boolean isIncomplete(Layout layout);

	public boolean isIncomplete(Layout layout, long layoutSetBranchId);

	public boolean isRemoteLayoutHasPortletId(
		long userId, long stagingGroupId, long plid, String portletId);

	/**
	 * @deprecated As of Wilberforce (7.0.x), see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	public void lockGroup(long userId, long groupId) throws PortalException;

	public void populateLastPublishDateCounts(
			PortletDataContext portletDataContext,
			StagedModelType[] stagedModelTypes)
		throws PortalException;

	public void populateLastPublishDateCounts(
			PortletDataContext portletDataContext, String[] classNames)
		throws PortalException;

	public long publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws PortalException;

	public long publishLayouts(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long publishLayouts(long userId, long exportImportConfigurationId)
		throws PortalException;

	public long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap)
		throws PortalException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #publishLayouts(long, long, long, boolean, long[], Map)}
	 */
	@Deprecated
	public long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException;

	public long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds, String name,
			Map<String, String[]> parameterMap)
		throws PortalException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #publishLayouts(long, long, long, boolean, long[], Map)}
	 */
	@Deprecated
	public long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException;

	public long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #publishLayouts(long, long, long, boolean, Map)}
	 */
	@Deprecated
	public long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException;

	public long publishPortlet(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long publishPortlet(long userId, long exportImportConfigurationId)
		throws PortalException;

	public long publishPortlet(
			long userId, long sourceGroupId, long targetGroupId,
			long sourcePlid, long targetPlid, String portletId,
			Map<String, String[]> parameterMap)
		throws PortalException;

	public long publishToLive(PortletRequest portletRequest)
		throws PortalException;

	public long publishToLive(PortletRequest portletRequest, Portlet portlet)
		throws PortalException;

	public long publishToRemote(PortletRequest portletRequest)
		throws PortalException;

	public <T extends BaseModel> void removeModelFromChangesetCollection(
			T model)
		throws PortalException;

	public void scheduleCopyFromLive(PortletRequest portletRequest)
		throws PortalException;

	public void schedulePublishToLive(PortletRequest portletRequest)
		throws PortalException;

	public void schedulePublishToRemote(PortletRequest portletRequest)
		throws PortalException;

	public void setRecentLayoutBranchId(
			HttpServletRequest httpServletRequest, long layoutSetBranchId,
			long plid, long layoutBranchId)
		throws PortalException;

	public void setRecentLayoutBranchId(
			User user, long layoutSetBranchId, long plid, long layoutBranchId)
		throws PortalException;

	public void setRecentLayoutRevisionId(
			HttpServletRequest httpServletRequest, long layoutSetBranchId,
			long plid, long layoutRevisionId)
		throws PortalException;

	public void setRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid, long layoutRevisionId)
		throws PortalException;

	public void setRecentLayoutSetBranchId(
			HttpServletRequest httpServletRequest, long layoutSetId,
			long layoutSetBranchId)
		throws PortalException;

	public void setRecentLayoutSetBranchId(
			User user, long layoutSetId, long layoutSetBranchId)
		throws PortalException;

	public String stripProtocolFromRemoteAddress(String remoteAddress);

	public void transferFileToRemoteLive(
			File file, long stagingRequestId, HttpPrincipal httpPrincipal)
		throws Exception;

	/**
	 * @deprecated As of Wilberforce (7.0.x), see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	public void unlockGroup(long groupId);

	public void unscheduleCopyFromLive(PortletRequest portletRequest)
		throws PortalException;

	public void unschedulePublishToLive(PortletRequest portletRequest)
		throws PortalException;

	public void unschedulePublishToRemote(PortletRequest portletRequest)
		throws PortalException;

	public void updateLastImportSettings(
			Element layoutElement, Layout layout,
			PortletDataContext portletDataContext)
		throws PortalException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.lar.ExportImportDateUtil#updateLastPublishDate(
	 *             long, boolean, com.liferay.portal.kernel.util.DateRange,
	 *             Date)}
	 */
	@Deprecated
	public void updateLastPublishDate(
			long sourceGroupId, boolean privateLayout, Date lastPublishDate)
		throws PortalException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.lar.ExportImportDateUtil#updateLastPublishDate(
	 *             String, PortletPreferences,
	 *             com.liferay.portal.kernel.util.DateRange, Date)}
	 */
	@Deprecated
	public void updateLastPublishDate(
			String portletId, PortletPreferences portletPreferences,
			Date lastPublishDate)
		throws PortalException;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.staging.configuration.web.internal.portlet.StagingConfigurationPortlet#editStagingConfiguration(
	 *             javax.portlet.ActionRequest, javax.portlet.ActionResponse)}
	 */
	@Deprecated
	public void updateStaging(PortletRequest portletRequest, Group liveGroup)
		throws PortalException;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.portal.kernel.service.GroupLocalService#validateRemote(
	 *             long, String, int, String, boolean, long)}
	 */
	@Deprecated
	public void validateRemote(
			long groupId, String remoteAddress, int remotePort,
			String remotePathContext, boolean secureConnection,
			long remoteGroupId)
		throws PortalException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #validateRemote(long, String, int, String, boolean, long)}
	 */
	@Deprecated
	public void validateRemote(
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId)
		throws PortalException;

	public void validateRemoteGroupIsSame(
			long groupId, long remoteGroupId, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection)
		throws PortalException;

}