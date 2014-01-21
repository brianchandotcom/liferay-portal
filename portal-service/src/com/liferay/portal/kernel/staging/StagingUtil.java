/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.xml.Element;
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
public class StagingUtil {

	public static String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId, boolean privateLayout) {

		return getStaging().buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, privateLayout);
	}

	public static String buildRemoteURL(
		UnicodeProperties typeSettingsProperties) {

		return getStaging().buildRemoteURL(typeSettingsProperties);
	}

	public static void checkDefaultLayoutSetBranches(
			long userId, Group liveGroup, boolean branchingPublic,
			boolean branchingPrivate, boolean remote,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		getStaging().checkDefaultLayoutSetBranches(
			userId, liveGroup, branchingPublic, branchingPrivate, remote,
			serviceContext);
	}

	public static void copyFromLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().copyFromLive(PortletRequest);
	}

	public static void copyFromLive(
			PortletRequest PortletRequest, Portlet portlet)
		throws Exception {

		getStaging().copyFromLive(PortletRequest, portlet);
	}

	public static void copyPortlet(
			PortletRequest PortletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws Exception {

		getStaging().copyPortlet(
			PortletRequest, sourceGroupId, targetGroupId, sourcePlid,
			targetPlid, portletId);
	}

	public static void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate)
		throws Exception {

		getStaging().copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout, startDate, endDate);
	}

	public static void deleteLastImportSettings(
			Group liveGroup, boolean privateLayout)
		throws PortalException, SystemException {

		getStaging().deleteLastImportSettings(liveGroup, privateLayout);
	}

	public static void deleteRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid)
		throws SystemException {

		getStaging().deleteRecentLayoutRevisionId(
			request, layoutSetBranchId, plid);
	}

	public static void deleteRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws SystemException {

		getStaging().deleteRecentLayoutRevisionId(
			user, layoutSetBranchId, plid);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	public static void disableStaging(
			Group scopeGroup, Group liveGroup, ServiceContext serviceContext)
		throws Exception {

		getStaging().disableStaging(scopeGroup, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#disableStaging(
	 *             Group, ServiceContext)}
	 */
	@Deprecated
	public static void disableStaging(
			Group liveGroup, ServiceContext serviceContext)
		throws Exception {

		getStaging().disableStaging(liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	public static void disableStaging(
			PortletRequest portletRequest, Group scopeGroup, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception {

		getStaging().disableStaging(
			portletRequest, scopeGroup, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#disableStaging(
	 *             PortletRequest, Group, ServiceContext)}
	 */
	@Deprecated
	public static void disableStaging(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception {

		getStaging().disableStaging(portletRequest, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#enableLocalStaging(
	 *             long, Group, boolean, boolean, ServiceContext)}
	 */
	@Deprecated
	public static void enableLocalStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			ServiceContext serviceContext)
		throws Exception {

		getStaging().enableLocalStaging(
			userId, scopeGroup, liveGroup, branchingPublic, branchingPrivate,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.StagingLocalService#enableRemoteStaging(
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

		getStaging().enableRemoteStaging(
			userId, scopeGroup, liveGroup, branchingPublic, branchingPrivate,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, serviceContext);
	}

	public static JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return getStaging().getErrorMessagesJSONArray(
			locale, missingReferences, contextMap);
	}

	public static JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap) {

		return getStaging().getExceptionMessagesJSONObject(
			locale, e, contextMap);
	}

	public static Group getLiveGroup(long groupId)
		throws PortalException, SystemException {

		return getStaging().getLiveGroup(groupId);
	}

	public static long getLiveGroupId(long groupId)
		throws PortalException, SystemException {

		return getStaging().getLiveGroupId(groupId);
	}

	public static List<Layout> getMissingParentLayouts(
			Layout layout, long liveGroupId)
		throws Exception {

		return getStaging().getMissingParentLayouts(layout, liveGroupId);
	}

	public static long getRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid)
		throws PortalException, SystemException {

		return getStaging().getRecentLayoutRevisionId(
			request, layoutSetBranchId, plid);
	}

	public static long getRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws PortalException, SystemException {

		return getStaging().getRecentLayoutRevisionId(
			user, layoutSetBranchId, plid);
	}

	public static long getRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId) {

		return getStaging().getRecentLayoutSetBranchId(request, layoutSetId);
	}

	public static long getRecentLayoutSetBranchId(User user, long layoutSetId)
		throws SystemException {

		return getStaging().getRecentLayoutSetBranchId(user, layoutSetId);
	}

	public static String getSchedulerGroupName(
		String destinationName, long groupId) {

		return getStaging().getSchedulerGroupName(destinationName, groupId);
	}

	public static String getStagedPortletId(String portletId) {
		return getStaging().getStagedPortletId(portletId);
	}

	public static Staging getStaging() {
		PortalRuntimePermission.checkGetBeanProperty(StagingUtil.class);

		return _staging;
	}

	public static Map<String, String[]> getStagingParameters() {
		return getStaging().getStagingParameters();
	}

	public static Map<String, String[]> getStagingParameters(
		PortletRequest PortletRequest) {

		return getStaging().getStagingParameters(PortletRequest);
	}

	public static JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return getStaging().getWarningMessagesJSONArray(
			locale, missingReferences, contextMap);
	}

	public static WorkflowTask getWorkflowTask(
			long userId, LayoutRevision layoutRevision)
		throws PortalException, SystemException {

		return getStaging().getWorkflowTask(userId, layoutRevision);
	}

	public static boolean hasWorkflowTask(
			long userId, LayoutRevision layoutRevision)
		throws PortalException, SystemException {

		return getStaging().hasWorkflowTask(userId, layoutRevision);
	}

	public static boolean isIncomplete(Layout layout, long layoutSetBranchId) {
		return getStaging().isIncomplete(layout, layoutSetBranchId);
	}

	public static void lockGroup(long userId, long groupId) throws Exception {
		getStaging().lockGroup(userId, groupId);
	}

	public static void publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws Exception {

		getStaging().publishLayout(userId, plid, liveGroupId, includeChildren);
	}

	public static void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		getStaging().publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public static void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		getStaging().publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIdMap,
			parameterMap, startDate, endDate);
	}

	public static void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException, SystemException {

		getStaging().publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	public static void publishToLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().publishToLive(PortletRequest);
	}

	public static void publishToLive(
			PortletRequest PortletRequest, Portlet portlet)
		throws Exception {

		getStaging().publishToLive(PortletRequest, portlet);
	}

	public static void publishToRemote(PortletRequest PortletRequest)
		throws Exception {

		getStaging().publishToRemote(PortletRequest);
	}

	public static void scheduleCopyFromLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().scheduleCopyFromLive(PortletRequest);
	}

	public static void schedulePublishToLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().schedulePublishToLive(PortletRequest);
	}

	public static void schedulePublishToRemote(PortletRequest PortletRequest)
		throws Exception {

		getStaging().schedulePublishToRemote(PortletRequest);
	}

	public static void setRecentLayoutBranchId(
			HttpServletRequest request, long layoutSetBranchId, long plid,
			long layoutBranchId)
		throws SystemException {

		getStaging().setRecentLayoutBranchId(
			request, layoutSetBranchId, plid, layoutBranchId);
	}

	public static void setRecentLayoutBranchId(
			User user, long layoutSetBranchId, long plid, long layoutBranchId)
		throws SystemException {

		getStaging().setRecentLayoutBranchId(
			user, layoutSetBranchId, plid, layoutBranchId);
	}

	public static void setRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid,
			long layoutRevisionId)
		throws SystemException {

		getStaging().setRecentLayoutRevisionId(
			request, layoutSetBranchId, plid, layoutRevisionId);
	}

	public static void setRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid, long layoutRevisionId)
		throws SystemException {

		getStaging().setRecentLayoutRevisionId(
			user, layoutSetBranchId, plid, layoutRevisionId);
	}

	public static void setRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId, long layoutSetBranchId) {

		getStaging().setRecentLayoutSetBranchId(
			request, layoutSetId, layoutSetBranchId);
	}

	public static void setRecentLayoutSetBranchId(
			User user, long layoutSetId, long layoutSetBranchId)
		throws SystemException {

		getStaging().setRecentLayoutSetBranchId(
			user, layoutSetId, layoutSetBranchId);
	}

	public static void unlockGroup(long groupId) throws SystemException {
		getStaging().unlockGroup(groupId);
	}

	public static void unscheduleCopyFromLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().unscheduleCopyFromLive(PortletRequest);
	}

	public static void unschedulePublishToLive(PortletRequest PortletRequest)
		throws Exception {

		getStaging().unschedulePublishToLive(PortletRequest);
	}

	public static void unschedulePublishToRemote(PortletRequest PortletRequest)
		throws Exception {

		getStaging().unschedulePublishToRemote(PortletRequest);
	}

	public static void updateLastImportSettings(
			Element layoutElement, Layout layout,
			PortletDataContext portletDataContext)
		throws Exception {

		getStaging().updateLastImportSettings(
			layoutElement, layout, portletDataContext);
	}

	public static void updateLastPublishDate(
			long sourceGroupId, boolean privateLayout, Date lastPublishDate)
		throws Exception {

		getStaging().updateLastPublishDate(
			sourceGroupId, privateLayout, lastPublishDate);
	}

	public static void updateLastPublishDate(
			String portletId, PortletPreferences portletPreferences,
			Date lastPublishDate)
		throws Exception {

		getStaging().updateLastPublishDate(
			portletId, portletPreferences, lastPublishDate);
	}

	public static void updateStaging(
			PortletRequest PortletRequest, Group liveGroup)
		throws Exception {

		getStaging().updateStaging(PortletRequest, liveGroup);
	}

	public static void validateRemote(
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId)
		throws PortalException {

		getStaging().validateRemote(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId);
	}

	public void setStaging(Staging staging) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_staging = staging;
	}

	private static Staging _staging;

}