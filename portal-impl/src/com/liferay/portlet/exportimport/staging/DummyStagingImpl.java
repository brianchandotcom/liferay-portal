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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Daniel Kocsis
 */
public class DummyStagingImpl implements Staging {

	@Override
	public String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId, boolean privateLayout) {

		return StringPool.BLANK;
	}

	@Override
	public String buildRemoteURL(UnicodeProperties typeSettingsProperties) {
		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalServiceUtil#
	 *             checkDefaultLayoutSetBranches(long, Group, boolean, boolean,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void checkDefaultLayoutSetBranches(
		long userId, Group liveGroup, boolean branchingPublic,
		boolean branchingPrivate, boolean remote,
		ServiceContext serviceContext) {
	}

	@Override
	public void copyFromLive(PortletRequest PortletRequest) {
	}

	@Override
	public void copyFromLive(PortletRequest PortletRequest, Portlet portlet) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishPortlet(long, long,
	 *             long, long, long, String, Map)}
	 */
	@Deprecated
	@Override
	public void copyPortlet(
		PortletRequest PortletRequest, long sourceGroupId, long targetGroupId,
		long sourcePlid, long targetPlid, String portletId) {
	}

	@Override
	public void copyRemoteLayouts(
		ExportImportConfiguration exportImportConfiguration) {
	}

	@Override
	public void copyRemoteLayouts(long exportImportConfigurationId) {
	}

	@Override
	public void copyRemoteLayouts(
		long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #copyRemoteLayouts(long,
	 *             boolean, Map, Map, String, int, String, boolean, long,
	 *             boolean)}
	 */
	@Deprecated
	@Override
	public void copyRemoteLayouts(
		long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout, Date startDate, Date endDate) {
	}

	@Override
	public void deleteLastImportSettings(
		Group liveGroup, boolean privateLayout) {
	}

	@Override
	public void deleteRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid) {
	}

	@Override
	public void deleteRecentLayoutRevisionId(
		long userId, long layoutSetBranchId, long plid) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #deleteRecentLayoutRevisionId(long, long, long)}
	 */
	@Deprecated
	@Override
	public void deleteRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid) {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void disableStaging(
			Group scopeGroup, Group liveGroup, ServiceContext serviceContext)
		throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void disableStaging(Group liveGroup, ServiceContext serviceContext)
		throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void disableStaging(
			PortletRequest portletRequest, Group scopeGroup, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void disableStaging(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#enableLocalStaging(
	 *             long, Group, boolean, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void enableLocalStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			ServiceContext serviceContext)
		throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#enableRemoteStaging(
	 *             long, Group, boolean, boolean, String, int, String, boolean,
	 *             long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void enableRemoteStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			ServiceContext serviceContext)
		throws Exception {
	}

	@Override
	public JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return JSONFactoryUtil.createJSONArray();
	}

	@Override
	public JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap) {

		return JSONFactoryUtil.createJSONObject();
	}

	@Override
	public Group getLiveGroup(long groupId) {
		return null;
	}

	@Override
	public long getLiveGroupId(long groupId) {
		return 0L;
	}

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportHelperUtil#getMissingParentLayouts(
	 *             Layout, long)}
	 */
	@Deprecated
	@Override
	public List<Layout> getMissingParentLayouts(
		Layout layout, long liveGroupId) {

		return Collections.<Layout>emptyList();
	}

	@Override
	public long getRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid) {

		return 0L;
	}

	@Override
	public long getRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid) {

		return 0L;
	}

	@Override
	public long getRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId) {

		return 0L;
	}

	@Override
	public long getRecentLayoutSetBranchId(User user, long layoutSetId) {
		return 0L;
	}

	@Override
	public String getSchedulerGroupName(String destinationName, long groupId) {
		return StringPool.BLANK;
	}

	@Override
	public String getStagedPortletId(String portletId) {
		return StringPool.BLANK;
	}

	@Override
	public Group getStagingGroup(long groupId) {
		return null;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             )}
	 */
	@Deprecated
	@Override
	public Map<String, String[]> getStagingParameters() {
		return Collections.<String, String[]>emptyMap();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             PortletRequest)}
	 */
	@Deprecated
	@Override
	public Map<String, String[]> getStagingParameters(
		PortletRequest PortletRequest) {

		return Collections.<String, String[]>emptyMap();
	}

	@Override
	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return JSONFactoryUtil.createJSONArray();
	}

	@Override
	public WorkflowTask getWorkflowTask(
		long userId, LayoutRevision layoutRevision) {

		return null;
	}

	@Override
	public boolean hasWorkflowTask(long userId, LayoutRevision layoutRevision) {
		return false;
	}

	@Override
	public boolean isIncomplete(Layout layout, long layoutSetBranchId) {
		return false;
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	@Override
	public void lockGroup(long userId, long groupId) {
	}

	@Override
	public void publishLayout(
		long userId, long plid, long liveGroupId, boolean includeChildren) {
	}

	@Override
	public void publishLayouts(
		long userId, ExportImportConfiguration exportImportConfiguration) {
	}

	@Override
	public void publishLayouts(long userId, long exportImportConfigurationId) {
	}

	@Override
	public void publishLayouts(
		long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, long[], Map)}
	 */
	@Deprecated
	@Override
	public void publishLayouts(
		long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap, Date startDate, Date endDate) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, long[], Map)}
	 */
	@Deprecated
	@Override
	public void publishLayouts(
		long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, Map<Long, Boolean> layoutIdMap,
		Map<String, String[]> parameterMap, Date startDate, Date endDate) {
	}

	@Override
	public void publishLayouts(
		long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, Map<String, String[]> parameterMap) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, Map)}
	 */
	@Deprecated
	@Override
	public void publishLayouts(
		long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, Map<String, String[]> parameterMap,
		Date startDate, Date endDate) {
	}

	@Override
	public void publishPortlet(
		long userId, ExportImportConfiguration exportImportConfiguration) {
	}

	@Override
	public void publishPortlet(long userId, long exportImportConfigurationId) {
	}

	@Override
	public void publishPortlet(
		long userId, long sourceGroupId, long targetGroupId, long sourcePlid,
		long targetPlid, String portletId,
		Map<String, String[]> parameterMap) {
	}

	@Override
	public void publishToLive(PortletRequest PortletRequest) {
	}

	@Override
	public void publishToLive(PortletRequest PortletRequest, Portlet portlet) {
	}

	@Override
	public void publishToRemote(PortletRequest PortletRequest) {
	}

	@Override
	public void scheduleCopyFromLive(PortletRequest PortletRequest) {
	}

	@Override
	public void schedulePublishToLive(PortletRequest PortletRequest) {
	}

	@Override
	public void schedulePublishToRemote(PortletRequest PortletRequest) {
	}

	@Override
	public void setRecentLayoutBranchId(
		HttpServletRequest request, long layoutSetBranchId, long plid,
		long layoutBranchId) {
	}

	@Override
	public void setRecentLayoutBranchId(
		User user, long layoutSetBranchId, long plid, long layoutBranchId) {
	}

	@Override
	public void setRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid,
		long layoutRevisionId) {
	}

	@Override
	public void setRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid, long layoutRevisionId) {
	}

	@Override
	public void setRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId, long layoutSetBranchId) {
	}

	@Override
	public void setRecentLayoutSetBranchId(
		User user, long layoutSetId, long layoutSetBranchId) {
	}

	@Override
	public String stripProtocolFromRemoteAddress(String remoteAddress) {
		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	@Override
	public void unlockGroup(long groupId) {
	}

	@Override
	public void unscheduleCopyFromLive(PortletRequest PortletRequest) {
	}

	@Override
	public void unschedulePublishToLive(PortletRequest PortletRequest) {
	}

	@Override
	public void unschedulePublishToRemote(PortletRequest PortletRequest) {
	}

	@Override
	public void updateLastImportSettings(
		Element layoutElement, Layout layout,
		PortletDataContext portletDataContext) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportDateUtil#updateLastPublishDate(
	 *             long, boolean, com.liferay.portal.kernel.util.DateRange,
	 *             Date)}
	 */
	@Deprecated
	@Override
	public void updateLastPublishDate(
		long sourceGroupId, boolean privateLayout, Date lastPublishDate) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportDateUtil#updateLastPublishDate(
	 *             String, PortletPreferences,
	 *             com.liferay.portal.kernel.util.DateRange, Date)}
	 */
	@Deprecated
	@Override
	public void updateLastPublishDate(
		String portletId, PortletPreferences portletPreferences,
		Date lastPublishDate) {
	}

	@Override
	public void updateStaging(PortletRequest PortletRequest, Group liveGroup) {
	}

	@Override
	public void validateRemote(
		long groupId, String remoteAddress, int remotePort,
		String remotePathContext, boolean secureConnection,
		long remoteGroupId) {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #validateRemote(long, String,
	 *             int, String, boolean, long)}
	 */
	@Deprecated
	@Override
	public void validateRemote(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId) {
	}

}