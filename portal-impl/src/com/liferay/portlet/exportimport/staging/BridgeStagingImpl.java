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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.Serializable;

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
public class BridgeStagingImpl implements Staging {

	public BridgeStagingImpl() {
		this(new DummyStagingImpl());
	}

	public BridgeStagingImpl(Staging defaultStaging) {
		_defaultStaging = defaultStaging;

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(Staging.class);

		_serviceTracker.open();
	}

	@Override
	public String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId, boolean privateLayout) {

		return getStaging().buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, privateLayout);
	}

	@Override
	public String buildRemoteURL(UnicodeProperties typeSettingsProperties) {
		return getStaging().buildRemoteURL(typeSettingsProperties);
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
			ServiceContext serviceContext)
		throws PortalException {

		getStaging().checkDefaultLayoutSetBranches(
			userId, liveGroup, branchingPublic, branchingPrivate, remote,
			serviceContext);
	}

	@Override
	public void copyFromLive(PortletRequest portletRequest)
		throws PortalException {

		getStaging().copyFromLive(portletRequest);
	}

	@Override
	public void copyFromLive(PortletRequest portletRequest, Portlet portlet)
		throws PortalException {

		getStaging().copyFromLive(portletRequest, portlet);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishPortlet(long, long,
	 *             long, long, long, String, Map)}
	 */
	@Deprecated
	@Override
	public void copyPortlet(
			PortletRequest portletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws PortalException {

		getStaging().copyPortlet(
			portletRequest, sourceGroupId, targetGroupId, sourcePlid,
			targetPlid, portletId);
	}

	@Override
	public void copyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		getStaging().copyRemoteLayouts(exportImportConfiguration);
	}

	@Override
	public void copyRemoteLayouts(long exportImportConfigurationId)
		throws PortalException {

		getStaging().copyRemoteLayouts(exportImportConfigurationId);
	}

	@Override
	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout)
		throws PortalException {

		getStaging().copyRemoteLayouts(
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
	@Override
	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate)
		throws PortalException {

		getStaging().copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout, startDate, endDate);
	}

	@Override
	public void deleteLastImportSettings(Group liveGroup, boolean privateLayout)
		throws PortalException {

		getStaging().deleteLastImportSettings(liveGroup, privateLayout);
	}

	@Override
	public void deleteRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid) {

		getStaging().deleteRecentLayoutRevisionId(
			request, layoutSetBranchId, plid);
	}

	@Override
	public void deleteRecentLayoutRevisionId(
		long userId, long layoutSetBranchId, long plid) {

		getStaging().deleteRecentLayoutRevisionId(
			userId, layoutSetBranchId, plid);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #deleteRecentLayoutRevisionId(long, long, long)}
	 */
	@Deprecated
	@Override
	public void deleteRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid) {

		getStaging().deleteRecentLayoutRevisionId(
			user, layoutSetBranchId, plid);
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

		getStaging().disableStaging(scopeGroup, liveGroup, serviceContext);
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

		getStaging().disableStaging(liveGroup, serviceContext);
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

		getStaging().disableStaging(
			portletRequest, scopeGroup, liveGroup, serviceContext);
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

		getStaging().disableStaging(portletRequest, liveGroup, serviceContext);
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

		getStaging().enableLocalStaging(
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
	@Override
	public void enableRemoteStaging(
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

	@Override
	public JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return getStaging().getErrorMessagesJSONArray(
			locale, missingReferences, contextMap);
	}

	@Override
	public JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap) {

		return getStaging().getExceptionMessagesJSONObject(
			locale, e, contextMap);
	}

	@Override
	public Group getLiveGroup(long groupId) {
		return getStaging().getLiveGroup(groupId);
	}

	@Override
	public long getLiveGroupId(long groupId) {
		return getStaging().getLiveGroupId(groupId);
	}

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             com.liferay.portlet.exportimport.lar.ExportImportHelperUtil#getMissingParentLayouts(
	 *             Layout, long)}
	 */
	@Deprecated
	@Override
	public List<Layout> getMissingParentLayouts(Layout layout, long liveGroupId)
		throws PortalException {

		return getStaging().getMissingParentLayouts(layout, liveGroupId);
	}

	@Override
	public long getRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid)
		throws PortalException {

		return getStaging().getRecentLayoutRevisionId(
			request, layoutSetBranchId, plid);
	}

	@Override
	public long getRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws PortalException {

		return getStaging().getRecentLayoutRevisionId(
			user, layoutSetBranchId, plid);
	}

	@Override
	public long getRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId) {

		return getStaging().getRecentLayoutSetBranchId(request, layoutSetId);
	}

	@Override
	public long getRecentLayoutSetBranchId(User user, long layoutSetId) {
		return getStaging().getRecentLayoutSetBranchId(user, layoutSetId);
	}

	@Override
	public String getSchedulerGroupName(String destinationName, long groupId) {
		return getStaging().getSchedulerGroupName(destinationName, groupId);
	}

	@Override
	public String getStagedPortletId(String portletId) {
		return getStaging().getStagedPortletId(portletId);
	}

	@Override
	public Group getStagingGroup(long groupId) {
		return getStaging().getStagingGroup(groupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             )}
	 */
	@Deprecated
	@Override
	public Map<String, String[]> getStagingParameters() {
		return getStaging().getStagingParameters();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             PortletRequest)}
	 */
	@Deprecated
	@Override
	public Map<String, String[]> getStagingParameters(
		PortletRequest portletRequest) {

		return getStaging().getStagingParameters(portletRequest);
	}

	@Override
	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return getStaging().getWarningMessagesJSONArray(
			locale, missingReferences, contextMap);
	}

	@Override
	public WorkflowTask getWorkflowTask(
			long userId, LayoutRevision layoutRevision)
		throws PortalException {

		return getStaging().getWorkflowTask(userId, layoutRevision);
	}

	@Override
	public boolean hasWorkflowTask(long userId, LayoutRevision layoutRevision)
		throws PortalException {

		return getStaging().hasWorkflowTask(userId, layoutRevision);
	}

	@Override
	public boolean isIncomplete(Layout layout, long layoutSetBranchId) {
		return getStaging().isIncomplete(layout, layoutSetBranchId);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	@Override
	public void lockGroup(long userId, long groupId) throws PortalException {
		getStaging().lockGroup(userId, groupId);
	}

	@Override
	public void publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws PortalException {

		getStaging().publishLayout(userId, plid, liveGroupId, includeChildren);
	}

	@Override
	public void publishLayouts(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		getStaging().publishLayouts(userId, exportImportConfiguration);
	}

	@Override
	public void publishLayouts(long userId, long exportImportConfigurationId)
		throws PortalException {

		getStaging().publishLayouts(userId, exportImportConfigurationId);
	}

	@Override
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap)
		throws PortalException {

		getStaging().publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap);
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
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		getStaging().publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap,
			startDate, endDate);
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
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		getStaging().publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	@Override
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException {

		getStaging().publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap);
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
			Date startDate, Date endDate)
		throws PortalException {

		getStaging().publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	@Override
	public void publishPortlet(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		getStaging().publishPortlet(userId, exportImportConfiguration);
	}

	@Override
	public void publishPortlet(long userId, long exportImportConfigurationId)
		throws PortalException {

		getStaging().publishPortlet(userId, exportImportConfigurationId);
	}

	@Override
	public void publishPortlet(
			long userId, long sourceGroupId, long targetGroupId,
			long sourcePlid, long targetPlid, String portletId,
			Map<String, String[]> parameterMap)
		throws PortalException {

		getStaging().publishPortlet(
			userId, sourceGroupId, targetGroupId, sourcePlid, targetPlid,
			portletId, parameterMap);
	}

	@Override
	public void publishToLive(PortletRequest portletRequest)
		throws PortalException {

		getStaging().publishToLive(portletRequest);
	}

	@Override
	public void publishToLive(PortletRequest portletRequest, Portlet portlet)
		throws PortalException {

		getStaging().publishToLive(portletRequest, portlet);
	}

	@Override
	public void publishToRemote(PortletRequest portletRequest)
		throws PortalException {

		getStaging().publishToRemote(portletRequest);
	}

	@Override
	public void scheduleCopyFromLive(PortletRequest portletRequest)
		throws PortalException {

		getStaging().scheduleCopyFromLive(portletRequest);
	}

	@Override
	public void schedulePublishToLive(PortletRequest portletRequest)
		throws PortalException {

		getStaging().scheduleCopyFromLive(portletRequest);
	}

	@Override
	public void schedulePublishToRemote(PortletRequest portletRequest)
		throws PortalException {

		getStaging().scheduleCopyFromLive(portletRequest);
	}

	@Override
	public void setRecentLayoutBranchId(
		HttpServletRequest request, long layoutSetBranchId, long plid,
		long layoutBranchId) {

		getStaging().setRecentLayoutBranchId(
			request, layoutSetBranchId, plid, layoutBranchId);
	}

	@Override
	public void setRecentLayoutBranchId(
		User user, long layoutSetBranchId, long plid, long layoutBranchId) {

		getStaging().setRecentLayoutBranchId(
			user, layoutSetBranchId, plid, layoutBranchId);
	}

	@Override
	public void setRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid,
		long layoutRevisionId) {

		getStaging().setRecentLayoutRevisionId(
			request, layoutSetBranchId, plid, layoutRevisionId);
	}

	@Override
	public void setRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid, long layoutRevisionId) {

		getStaging().setRecentLayoutRevisionId(
			user, layoutSetBranchId, plid, layoutRevisionId);
	}

	@Override
	public void setRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId, long layoutSetBranchId) {

		getStaging().setRecentLayoutSetBranchId(
			request, layoutSetId, layoutSetBranchId);
	}

	@Override
	public void setRecentLayoutSetBranchId(
		User user, long layoutSetId, long layoutSetBranchId) {

		getStaging().setRecentLayoutSetBranchId(
			user, layoutSetId, layoutSetBranchId);
	}

	@Override
	public String stripProtocolFromRemoteAddress(String remoteAddress) {
		return getStaging().stripProtocolFromRemoteAddress(remoteAddress);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	@Override
	public void unlockGroup(long groupId) {
		getStaging().unlockGroup(groupId);
	}

	@Override
	public void unscheduleCopyFromLive(PortletRequest portletRequest)
		throws PortalException {

		getStaging().unscheduleCopyFromLive(portletRequest);
	}

	@Override
	public void unschedulePublishToLive(PortletRequest portletRequest)
		throws PortalException {

		getStaging().unschedulePublishToLive(portletRequest);
	}

	@Override
	public void unschedulePublishToRemote(PortletRequest portletRequest)
		throws PortalException {

		getStaging().unschedulePublishToRemote(portletRequest);
	}

	@Override
	public void updateLastImportSettings(
			Element layoutElement, Layout layout,
			PortletDataContext portletDataContext)
		throws PortalException {

		getStaging().updateLastImportSettings(
			layoutElement, layout, portletDataContext);
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
			long sourceGroupId, boolean privateLayout, Date lastPublishDate)
		throws PortalException {

		getStaging().updateLastPublishDate(
			sourceGroupId, privateLayout, lastPublishDate);
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
			Date lastPublishDate)
		throws PortalException {

		getStaging().updateLastPublishDate(
			portletId, portletPreferences, lastPublishDate);
	}

	@Override
	public void updateStaging(PortletRequest portletRequest, Group liveGroup)
		throws PortalException {

		getStaging().updateStaging(portletRequest, liveGroup);
	}

	@Override
	public void validateRemote(
			long groupId, String remoteAddress, int remotePort,
			String remotePathContext, boolean secureConnection,
			long remoteGroupId)
		throws PortalException {

		getStaging().validateRemote(
			groupId, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #validateRemote(long, String,
	 *             int, String, boolean, long)}
	 */
	@Deprecated
	@Override
	public void validateRemote(
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId)
		throws PortalException {

		getStaging().validateRemote(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId);
	}

	protected Staging getStaging() {
		if (_serviceTracker.isEmpty()) {
			return _defaultStaging;
		}

		return _serviceTracker.getService();
	}

	private final Staging _defaultStaging;
	private final ServiceTracker<Staging, Staging> _serviceTracker;

}