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
import com.liferay.portal.kernel.util.ServiceProxyFactory;
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

/**
 * @author Raymond Augé
 */
public class StagingUtil {

	public static <T extends BaseModel> void addModelToChangesetCollection(
			T model)
		throws PortalException {

		_staging.addModelToChangesetCollection(model);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             StagingURLHelper#buildRemoteURL(ExportImportConfiguration)}
	 */
	@Deprecated
	public static String buildRemoteURL(
		ExportImportConfiguration exportImportConfiguration) {

		return _staging.buildRemoteURL(exportImportConfiguration);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             StagingURLHelper#buildRemoteURL(String, int, String,
	 *             boolean)}
	 */
	@Deprecated
	public static String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection) {

		return _staging.buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getRemoteSiteURL(Group, boolean)}
	 */
	@Deprecated
	public static String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId, boolean privateLayout) {

		return _staging.buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, privateLayout);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             StagingURLHelper#buildRemoteURL(UnicodeProperties)}
	 */
	@Deprecated
	public static String buildRemoteURL(
		UnicodeProperties typeSettingsProperties) {

		return _staging.buildRemoteURL(typeSettingsProperties);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.service.StagingLocalServiceUtil#
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

	public static long copyFromLive(PortletRequest portletRequest)
		throws PortalException {

		return _staging.copyFromLive(portletRequest);
	}

	public static long copyFromLive(
			PortletRequest portletRequest, Portlet portlet)
		throws PortalException {

		return _staging.copyFromLive(portletRequest, portlet);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #publishPortlet(long, long, long, long, long, String, Map)}
	 */
	@Deprecated
	public static long copyPortlet(
			PortletRequest portletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws PortalException {

		return _staging.copyPortlet(
			portletRequest, sourceGroupId, targetGroupId, sourcePlid,
			targetPlid, portletId);
	}

	public static long copyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		return _staging.copyRemoteLayouts(exportImportConfiguration);
	}

	public static long copyRemoteLayouts(long exportImportConfigurationId)
		throws PortalException {

		return _staging.copyRemoteLayouts(exportImportConfigurationId);
	}

	public static long copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout)
		throws PortalException {

		return _staging.copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #copyRemoteLayouts(long, boolean, Map, Map, String, int,
	 *             String, boolean, long, boolean)}
	 */
	@Deprecated
	public static long copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate)
		throws PortalException {

		return _staging.copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout, startDate, endDate);
	}

	public static long copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, String name,
			Map<String, String[]> parameterMap, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection,
			long remoteGroupId, boolean remotePrivateLayout)
		throws PortalException {

		return _staging.copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, name, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout);
	}

	public static void deleteLastImportSettings(
			Group liveGroup, boolean privateLayout)
		throws PortalException {

		_staging.deleteLastImportSettings(liveGroup, privateLayout);
	}

	public static void deleteRecentLayoutRevisionId(
		HttpServletRequest httpServletRequest, long layoutSetBranchId,
		long plid) {

		_staging.deleteRecentLayoutRevisionId(
			httpServletRequest, layoutSetBranchId, plid);
	}

	public static void deleteRecentLayoutRevisionId(
		long userId, long layoutSetBranchId, long plid) {

		_staging.deleteRecentLayoutRevisionId(userId, layoutSetBranchId, plid);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #deleteRecentLayoutRevisionId(long, long, long)}
	 */
	@Deprecated
	public static void deleteRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid) {

		_staging.deleteRecentLayoutRevisionId(user, layoutSetBranchId, plid);
	}

	public static JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		return _staging.getErrorMessagesJSONArray(locale, missingReferences);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
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
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getExceptionMessagesJSONObject(Locale, Exception,
	 *             ExportImportConfiguration)}
	 */
	@Deprecated
	public static JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap) {

		return _staging.getExceptionMessagesJSONObject(locale, e, contextMap);
	}

	public static Group getLiveGroup(Group group) {
		return _staging.getLiveGroup(group);
	}

	public static Group getLiveGroup(long groupId) {
		return _staging.getLiveGroup(groupId);
	}

	public static long getLiveGroupId(long groupId) {
		return _staging.getLiveGroupId(groupId);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), moved to {@link
	 *             com.liferay.exportimport.kernel.lar.ExportImportHelperUtil#getMissingParentLayouts(
	 *             Layout, long)}
	 */
	@Deprecated
	public static List<Layout> getMissingParentLayouts(
			Layout layout, long liveGroupId)
		throws Exception {

		return _staging.getMissingParentLayouts(layout, liveGroupId);
	}

	public static Group getPermissionStagingGroup(Group group) {
		return _staging.getPermissionStagingGroup(group);
	}

	public static long getRecentLayoutRevisionId(
			HttpServletRequest httpServletRequest, long layoutSetBranchId,
			long plid)
		throws PortalException {

		return _staging.getRecentLayoutRevisionId(
			httpServletRequest, layoutSetBranchId, plid);
	}

	public static long getRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws PortalException {

		return _staging.getRecentLayoutRevisionId(
			user, layoutSetBranchId, plid);
	}

	public static long getRecentLayoutSetBranchId(
		HttpServletRequest httpServletRequest, long layoutSetId) {

		return _staging.getRecentLayoutSetBranchId(
			httpServletRequest, layoutSetId);
	}

	public static long getRecentLayoutSetBranchId(User user, long layoutSetId) {
		return _staging.getRecentLayoutSetBranchId(user, layoutSetId);
	}

	public static long getRemoteLayoutPlid(
			long userId, long stagingGroupId, long plid)
		throws PortalException {

		return _staging.getRemoteLayoutPlid(userId, stagingGroupId, plid);
	}

	public static String getRemoteSiteURL(
			Group stagingGroup, boolean privateLayout)
		throws PortalException {

		return _staging.getRemoteSiteURL(stagingGroup, privateLayout);
	}

	public static String getSchedulerGroupName(
		String destinationName, long groupId) {

		return _staging.getSchedulerGroupName(destinationName, groupId);
	}

	public static String getStagedPortletId(String portletId) {
		return _staging.getStagedPortletId(portletId);
	}

	public static long[] getStagingAndLiveGroupIds(long groupId)
		throws PortalException {

		return _staging.getStagingAndLiveGroupIds(groupId);
	}

	public static Group getStagingGroup(long groupId) {
		return _staging.getStagingGroup(groupId);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             )}
	 */
	@Deprecated
	public static Map<String, String[]> getStagingParameters() {
		return _staging.getStagingParameters();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             PortletRequest)}
	 */
	@Deprecated
	public static Map<String, String[]> getStagingParameters(
		PortletRequest portletRequest) {

		return _staging.getStagingParameters(portletRequest);
	}

	public static JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		return _staging.getWarningMessagesJSONArray(locale, missingReferences);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
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

	public static boolean isChangeTrackingEnabled(long companyId) {
		return _staging.isChangeTrackingEnabled(companyId);
	}

	public static boolean isGroupAccessible(Group group, Group fromGroup) {
		return _staging.isGroupAccessible(group, fromGroup);
	}

	public static boolean isGroupAccessible(long groupId, long fromGroupId)
		throws PortalException {

		return _staging.isGroupAccessible(groupId, fromGroupId);
	}

	public static boolean isIncomplete(Layout layout) {
		return _staging.isIncomplete(layout);
	}

	public static boolean isIncomplete(Layout layout, long layoutSetBranchId) {
		return _staging.isIncomplete(layout, layoutSetBranchId);
	}

	public static boolean isRemoteLayoutHasPortletId(
		long userId, long stagingGroupId, long plid, String portletId) {

		return _staging.isRemoteLayoutHasPortletId(
			userId, stagingGroupId, plid, portletId);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	public static void lockGroup(long userId, long groupId)
		throws PortalException {

		_staging.lockGroup(userId, groupId);
	}

	public static void populateLastPublishDateCounts(
			PortletDataContext portletDataContext,
			StagedModelType[] stagedModelTypes)
		throws PortalException {

		_staging.populateLastPublishDateCounts(
			portletDataContext, stagedModelTypes);
	}

	public static void populateLastPublishDateCounts(
			PortletDataContext portletDataContext, String[] classNames)
		throws PortalException {

		_staging.populateLastPublishDateCounts(portletDataContext, classNames);
	}

	public static long publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws PortalException {

		return _staging.publishLayout(
			userId, plid, liveGroupId, includeChildren);
	}

	public static long publishLayouts(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		return _staging.publishLayouts(userId, exportImportConfiguration);
	}

	public static long publishLayouts(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		return _staging.publishLayouts(userId, exportImportConfigurationId);
	}

	public static long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap)
		throws PortalException {

		return _staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #publishLayouts(long, long, long, boolean, long[], Map)}
	 */
	@Deprecated
	public static long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		return _staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public static long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds, String name,
			Map<String, String[]> parameterMap)
		throws PortalException {

		return _staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIds,
			name, parameterMap);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #publishLayouts(long, long, long, boolean, long[], Map)}
	 */
	@Deprecated
	public static long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		return _staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIdMap,
			parameterMap, startDate, endDate);
	}

	public static long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException {

		return _staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #publishLayouts(long, long, long, boolean, Map)}
	 */
	@Deprecated
	public static long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException {

		return _staging.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	public static long publishPortlet(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		return _staging.publishPortlet(userId, exportImportConfiguration);
	}

	public static long publishPortlet(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		return _staging.publishPortlet(userId, exportImportConfigurationId);
	}

	public static long publishPortlet(
			long userId, long sourceGroupId, long targetGroupId,
			long sourcePlid, long targetPlid, String portletId,
			Map<String, String[]> parameterMap)
		throws PortalException {

		return _staging.publishPortlet(
			userId, sourceGroupId, targetGroupId, sourcePlid, targetPlid,
			portletId, parameterMap);
	}

	public static long publishToLive(PortletRequest portletRequest)
		throws PortalException {

		return _staging.publishToLive(portletRequest);
	}

	public static long publishToLive(
			PortletRequest portletRequest, Portlet portlet)
		throws PortalException {

		return _staging.publishToLive(portletRequest, portlet);
	}

	public static long publishToRemote(PortletRequest portletRequest)
		throws PortalException {

		return _staging.publishToRemote(portletRequest);
	}

	public static <T extends BaseModel> void removeModelFromChangesetCollection(
			T model)
		throws PortalException {

		_staging.removeModelFromChangesetCollection(model);
	}

	public static void scheduleCopyFromLive(PortletRequest portletRequest)
		throws PortalException {

		_staging.scheduleCopyFromLive(portletRequest);
	}

	public static void schedulePublishToLive(PortletRequest portletRequest)
		throws PortalException {

		_staging.schedulePublishToLive(portletRequest);
	}

	public static void schedulePublishToRemote(PortletRequest portletRequest)
		throws PortalException {

		_staging.schedulePublishToRemote(portletRequest);
	}

	public static void setRecentLayoutBranchId(
			HttpServletRequest httpServletRequest, long layoutSetBranchId,
			long plid, long layoutBranchId)
		throws PortalException {

		_staging.setRecentLayoutBranchId(
			httpServletRequest, layoutSetBranchId, plid, layoutBranchId);
	}

	public static void setRecentLayoutBranchId(
			User user, long layoutSetBranchId, long plid, long layoutBranchId)
		throws PortalException {

		_staging.setRecentLayoutBranchId(
			user, layoutSetBranchId, plid, layoutBranchId);
	}

	public static void setRecentLayoutRevisionId(
			HttpServletRequest httpServletRequest, long layoutSetBranchId,
			long plid, long layoutRevisionId)
		throws PortalException {

		_staging.setRecentLayoutRevisionId(
			httpServletRequest, layoutSetBranchId, plid, layoutRevisionId);
	}

	public static void setRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid, long layoutRevisionId)
		throws PortalException {

		_staging.setRecentLayoutRevisionId(
			user, layoutSetBranchId, plid, layoutRevisionId);
	}

	public static void setRecentLayoutSetBranchId(
			HttpServletRequest httpServletRequest, long layoutSetId,
			long layoutSetBranchId)
		throws PortalException {

		_staging.setRecentLayoutSetBranchId(
			httpServletRequest, layoutSetId, layoutSetBranchId);
	}

	public static void setRecentLayoutSetBranchId(
			User user, long layoutSetId, long layoutSetBranchId)
		throws PortalException {

		_staging.setRecentLayoutSetBranchId(
			user, layoutSetId, layoutSetBranchId);
	}

	public static String stripProtocolFromRemoteAddress(String remoteAddress) {
		return _staging.stripProtocolFromRemoteAddress(remoteAddress);
	}

	public static void transferFileToRemoteLive(
			File file, long stagingRequestId, HttpPrincipal httpPrincipal)
		throws Exception {

		_staging.transferFileToRemoteLive(
			file, stagingRequestId, httpPrincipal);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	public static void unlockGroup(long groupId) {
		_staging.unlockGroup(groupId);
	}

	public static void unscheduleCopyFromLive(PortletRequest portletRequest)
		throws PortalException {

		_staging.unscheduleCopyFromLive(portletRequest);
	}

	public static void unschedulePublishToLive(PortletRequest portletRequest)
		throws PortalException {

		_staging.unschedulePublishToLive(portletRequest);
	}

	public static void unschedulePublishToRemote(PortletRequest portletRequest)
		throws PortalException {

		_staging.unschedulePublishToRemote(portletRequest);
	}

	public static void updateLastImportSettings(
			Element layoutElement, Layout layout,
			PortletDataContext portletDataContext)
		throws PortalException {

		_staging.updateLastImportSettings(
			layoutElement, layout, portletDataContext);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.lar.ExportImportDateUtil#updateLastPublishDate(
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
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.exportimport.kernel.lar.ExportImportDateUtil#updateLastPublishDate(
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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.staging.configuration.web.internal.portlet.StagingConfigurationPortlet#editStagingConfiguration(
	 *             javax.portlet.ActionRequest, javax.portlet.ActionResponse)}
	 */
	@Deprecated
	public static void updateStaging(
			PortletRequest portletRequest, Group liveGroup)
		throws PortalException {

		_staging.updateStaging(portletRequest, liveGroup);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.portal.kernel.service.GroupLocalService#validateRemote(
	 *             long, String, int, String, boolean, long)}
	 */
	@Deprecated
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
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #validateRemote(long, String, int, String, boolean, long)}
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

	public static void validateRemoteGroupIsSame(
			long groupId, long remoteGroupId, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection)
		throws PortalException {

		_staging.validateRemoteGroupIsSame(
			groupId, remoteGroupId, remoteAddress, remotePort,
			remotePathContext, secureConnection);
	}

	private static volatile Staging _staging =
		ServiceProxyFactory.newServiceTrackedInstance(
			Staging.class, StagingUtil.class, "_staging", false);

}