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
import com.liferay.portal.kernel.util.ProxyFactory;
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
public class StagingUtil {

	public static String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId, boolean privateLayout) {

		return _staging.buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, privateLayout);
	}

	public static String buildRemoteURL(
		UnicodeProperties typeSettingsProperties) {

		return _staging.buildRemoteURL(typeSettingsProperties);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalServiceUtil#
	 *             checkDefaultLayoutSetBranches(long, Group, boolean, boolean,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	public static void checkDefaultLayoutSetBranches(
			long userId, Group liveGroup, boolean branchingPublic,
			boolean branchingPrivate, boolean remote,
			ServiceContext serviceContext)
		throws PortalException {

		_staging.checkDefaultLayoutSetBranches(
			userId, liveGroup, branchingPublic, branchingPrivate, remote,
			serviceContext);
	}

	public static void copyFromLive(PortletRequest PortletRequest)
		throws PortalException {

		_staging.copyFromLive(PortletRequest);
	}

	public static void copyFromLive(
			PortletRequest PortletRequest, Portlet portlet)
		throws PortalException {

		_staging.copyFromLive(PortletRequest, portlet);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishPortlet(long, long,
	 *             long, long, long, String, Map)}
	 */
	@Deprecated
	public static void copyPortlet(
			PortletRequest PortletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws PortalException {

		_staging.copyPortlet(
			PortletRequest, sourceGroupId, targetGroupId, sourcePlid,
			targetPlid, portletId);
	}

	public static void copyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		_staging.copyRemoteLayouts(exportImportConfiguration);
	}

	public static void copyRemoteLayouts(long exportImportConfigurationId)
		throws PortalException {

		_staging.copyRemoteLayouts(exportImportConfigurationId);
	}

	public static void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout)
		throws PortalException {

		_staging.copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #copyRemoteLayouts(long,
	 *             boolean, Map, Map, String, int, String, boolean, long,
	 *             boolean)}
	 */
	@Deprecated
	public static void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate)
		throws PortalException {

		_staging.copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout, startDate, endDate);
	}

	public static void deleteLastImportSettings(
			Group liveGroup, boolean privateLayout)
		throws PortalException {

		_staging.deleteLastImportSettings(liveGroup, privateLayout);
	}

	public static void deleteRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid) {

		_staging.deleteRecentLayoutRevisionId(request, layoutSetBranchId, plid);
	}

	public static void deleteRecentLayoutRevisionId(
		long userId, long layoutSetBranchId, long plid) {

		_staging.deleteRecentLayoutRevisionId(userId, layoutSetBranchId, plid);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #deleteRecentLayoutRevisionId(long, long, long)}
	 */
	@Deprecated
	public static void deleteRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid) {

		_staging.deleteRecentLayoutRevisionId(user, layoutSetBranchId, plid);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	public static void disableStaging(
			Group scopeGroup, Group liveGroup, ServiceContext serviceContext)
		throws Exception {

		_staging.disableStaging(scopeGroup, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	public static void disableStaging(
			Group liveGroup, ServiceContext serviceContext)
		throws Exception {

		_staging.disableStaging(liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	public static void disableStaging(
			PortletRequest portletRequest, Group scopeGroup, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception {

		_staging.disableStaging(
			portletRequest, scopeGroup, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	public static void disableStaging(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception {

		_staging.disableStaging(portletRequest, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#enableLocalStaging(
	 *             long, Group, boolean, boolean, ServiceContext)}
	 */
	@Deprecated
	public static void enableLocalStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			ServiceContext serviceContext)
		throws Exception {

		_staging.enableLocalStaging(
			userId, scopeGroup, liveGroup, branchingPublic, branchingPrivate,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.service.StagingLocalService#enableRemoteStaging(
	 *             long, Group, boolean, boolean, String, int, String, boolean,
	 *             long, ServiceContext)}
	 */
	@Deprecated
	public static void enableRemoteStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			ServiceContext serviceContext)
		throws Exception {

		_staging.enableRemoteStaging(
			userId, scopeGroup, liveGroup, branchingPublic, branchingPrivate,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, serviceContext);
	}

	public static JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		return _staging.getErrorMessagesJSONArray(locale, missingReferences);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getErrorMessagesJSONArray(Locale, Map<String,
	 *             MissingReference>)}
	 */
	@Deprecated
	public static JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return _staging.getErrorMessagesJSONArray(
			locale, missingReferences, contextMap);
	}

	public static JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e,
		ExportImportConfiguration exportImportConfiguration) {

		return _staging.getExceptionMessagesJSONObject(
			locale, e, exportImportConfiguration);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getExceptionMessagesJSONObject(Locale, Exception,
	 *             ExportImportConfiguration)}
	 */
	@Deprecated
	public static JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap) {

		return _staging.getExceptionMessagesJSONObject(locale, e, contextMap);
	}

	public static Group getLiveGroup(long groupId) {
		return _staging.getLiveGroup(groupId);
	}

	public static long getLiveGroupId(long groupId) {
		return _staging.getLiveGroupId(groupId);
	}

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportHelperUtil#getMissingParentLayouts(
	 *             Layout, long)}
	 */
	@Deprecated
	public static List<Layout> getMissingParentLayouts(
			Layout layout, long liveGroupId)
		throws Exception {

		return _staging.getMissingParentLayouts(layout, liveGroupId);
	}

	public static long getRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid)
		throws PortalException {

		return _staging.getRecentLayoutRevisionId(
			request, layoutSetBranchId, plid);
	}

	public static long getRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws PortalException {

		return _staging.getRecentLayoutRevisionId(
			user, layoutSetBranchId, plid);
	}

	public static long getRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId) {

		return _staging.getRecentLayoutSetBranchId(request, layoutSetId);
	}

	public static long getRecentLayoutSetBranchId(User user, long layoutSetId) {
		return _staging.getRecentLayoutSetBranchId(user, layoutSetId);
	}

	public static String getSchedulerGroupName(
		String destinationName, long groupId) {

		return _staging.getSchedulerGroupName(destinationName, groupId);
	}

	public static String getStagedPortletId(String portletId) {
		return _staging.getStagedPortletId(portletId);
	}

	public static long[] getStagingAndLiveGroupId(long groupId)
		throws PortalException {

		return _staging.getStagingAndLiveGroupId(groupId);
	}

	public static Group getStagingGroup(long groupId) {
		return _staging.getStagingGroup(groupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             )}
	 */
	@Deprecated
	public static Map<String, String[]> getStagingParameters() {
		return _staging.getStagingParameters();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             PortletRequest)}
	 */
	@Deprecated
	public static Map<String, String[]> getStagingParameters(
		PortletRequest PortletRequest) {

		return _staging.getStagingParameters(PortletRequest);
	}

	public static JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		return _staging.getWarningMessagesJSONArray(locale, missingReferences);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getWarningMessagesJSONArray(Locale, Map<String,
	 *             MissingReference>)}
	 */
	@Deprecated
	public static JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return _staging.getWarningMessagesJSONArray(
			locale, missingReferences, contextMap);
	}

	public static WorkflowTask getWorkflowTask(
			long userId, LayoutRevision layoutRevision)
		throws PortalException {

		return _staging.getWorkflowTask(userId, layoutRevision);
	}

	public static boolean hasWorkflowTask(
			long userId, LayoutRevision layoutRevision)
		throws PortalException {

		return _staging.hasWorkflowTask(userId, layoutRevision);
	}

	public static boolean isIncomplete(Layout layout, long layoutSetBranchId) {
		return _staging.isIncomplete(layout, layoutSetBranchId);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	public static void lockGroup(long userId, long groupId)
		throws PortalException {

		_staging.lockGroup(userId, groupId);
	}

	public static void publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws PortalException {

		_staging.publishLayout(userId, plid, liveGroupId, includeChildren);
	}

	public static void publishLayouts(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		_staging.publishLayouts(userId, exportImportConfiguration);
	}

	public static void publishLayouts(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		_staging.publishLayouts(userId, exportImportConfigurationId);
	}

	public static void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap)
		throws PortalException {

		_staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, long[], Map)}
	 */
	@Deprecated
	public static void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		_staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, long[], Map)}
	 */
	@Deprecated
	public static void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		_staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIdMap,
			parameterMap, startDate, endDate);
	}

	public static void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException {

		_staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, Map)}
	 */
	@Deprecated
	public static void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException {

		_staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	public static void publishPortlet(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		_staging.publishPortlet(userId, exportImportConfiguration);
	}

	public static void publishPortlet(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		_staging.publishPortlet(userId, exportImportConfigurationId);
	}

	public static void publishPortlet(
			long userId, long sourceGroupId, long targetGroupId,
			long sourcePlid, long targetPlid, String portletId,
			Map<String, String[]> parameterMap)
		throws PortalException {

		_staging.publishPortlet(
			userId, sourceGroupId, targetGroupId, sourcePlid, targetPlid,
			portletId, parameterMap);
	}

	public static void publishToLive(PortletRequest PortletRequest)
		throws PortalException {

		_staging.publishToLive(PortletRequest);
	}

	public static void publishToLive(
			PortletRequest PortletRequest, Portlet portlet)
		throws PortalException {

		_staging.publishToLive(PortletRequest, portlet);
	}

	public static void publishToRemote(PortletRequest PortletRequest)
		throws PortalException {

		_staging.publishToRemote(PortletRequest);
	}

	public static void scheduleCopyFromLive(PortletRequest PortletRequest)
		throws PortalException {

		_staging.scheduleCopyFromLive(PortletRequest);
	}

	public static void schedulePublishToLive(PortletRequest PortletRequest)
		throws PortalException {

		_staging.schedulePublishToLive(PortletRequest);
	}

	public static void schedulePublishToRemote(PortletRequest PortletRequest)
		throws PortalException {

		_staging.schedulePublishToRemote(PortletRequest);
	}

	public static void setRecentLayoutBranchId(
		HttpServletRequest request, long layoutSetBranchId, long plid,
		long layoutBranchId) {

		_staging.setRecentLayoutBranchId(
			request, layoutSetBranchId, plid, layoutBranchId);
	}

	public static void setRecentLayoutBranchId(
		User user, long layoutSetBranchId, long plid, long layoutBranchId) {

		_staging.setRecentLayoutBranchId(
			user, layoutSetBranchId, plid, layoutBranchId);
	}

	public static void setRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid,
		long layoutRevisionId) {

		_staging.setRecentLayoutRevisionId(
			request, layoutSetBranchId, plid, layoutRevisionId);
	}

	public static void setRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid, long layoutRevisionId) {

		_staging.setRecentLayoutRevisionId(
			user, layoutSetBranchId, plid, layoutRevisionId);
	}

	public static void setRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId, long layoutSetBranchId) {

		_staging.setRecentLayoutSetBranchId(
			request, layoutSetId, layoutSetBranchId);
	}

	public static void setRecentLayoutSetBranchId(
		User user, long layoutSetId, long layoutSetBranchId) {

		_staging.setRecentLayoutSetBranchId(
			user, layoutSetId, layoutSetBranchId);
	}

	public static String stripProtocolFromRemoteAddress(String remoteAddress) {
		return _staging.stripProtocolFromRemoteAddress(remoteAddress);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	public static void unlockGroup(long groupId) {
		_staging.unlockGroup(groupId);
	}

	public static void unscheduleCopyFromLive(PortletRequest PortletRequest)
		throws PortalException {

		_staging.unscheduleCopyFromLive(PortletRequest);
	}

	public static void unschedulePublishToLive(PortletRequest PortletRequest)
		throws PortalException {

		_staging.unschedulePublishToLive(PortletRequest);
	}

	public static void unschedulePublishToRemote(PortletRequest PortletRequest)
		throws PortalException {

		_staging.unschedulePublishToRemote(PortletRequest);
	}

	public static void updateLastImportSettings(
			Element layoutElement, Layout layout,
			PortletDataContext portletDataContext)
		throws PortalException {

		_staging.updateLastImportSettings(
			layoutElement, layout, portletDataContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportDateUtil#updateLastPublishDate(
	 *             long, boolean, com.liferay.portal.kernel.util.DateRange,
	 *             Date)}
	 */
	@Deprecated
	public static void updateLastPublishDate(
			long sourceGroupId, boolean privateLayout, Date lastPublishDate)
		throws PortalException {

		_staging.updateLastPublishDate(
			sourceGroupId, privateLayout, lastPublishDate);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportDateUtil#updateLastPublishDate(
	 *             String, PortletPreferences,
	 *             com.liferay.portal.kernel.util.DateRange, Date)}
	 */
	@Deprecated
	public static void updateLastPublishDate(
			String portletId, PortletPreferences portletPreferences,
			Date lastPublishDate)
		throws PortalException {

		_staging.updateLastPublishDate(
			portletId, portletPreferences, lastPublishDate);
	}

	public static void updateStaging(
			PortletRequest PortletRequest, Group liveGroup)
		throws PortalException {

		_staging.updateStaging(PortletRequest, liveGroup);
	}

	public static void validateRemote(
			long groupId, String remoteAddress, int remotePort,
			String remotePathContext, boolean secureConnection,
			long remoteGroupId)
		throws PortalException {

		_staging.validateRemote(
			groupId, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #validateRemote(long, String,
	 *             int, String, boolean, long)}
	 */
	@Deprecated
	public static void validateRemote(
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId)
		throws PortalException {

		_staging.validateRemote(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId);
	}

	private static final Staging _staging =
		ProxyFactory.newServiceTrackedInstance(Staging.class);

}