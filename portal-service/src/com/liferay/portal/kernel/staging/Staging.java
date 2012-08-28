/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
public interface Staging {

	public String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId, boolean privateLayout);

	public void copyFromLive(PortletRequest PortletRequest) throws Exception;

	public void copyFromLive(PortletRequest PortletRequest, Portlet portlet)
		throws Exception;

	public void copyPortlet(
			PortletRequest PortletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws Exception;

	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate)
		throws Exception;

	public void deleteLastImportSettings(Group liveGroup, boolean privateLayout)
		throws Exception;

	public void deleteRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid)
		throws SystemException;

	public void deleteRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws SystemException;

	/**
	 * @deprecated {@link #disableStaging(Group, ServiceContext)}
	 */
	public void disableStaging(
			Group scopeGroup, Group liveGroup, ServiceContext serviceContext)
		throws Exception;

	public void disableStaging(Group liveGroup, ServiceContext serviceContext)
		throws Exception;

	/**
	 * @deprecated {@link #disableStaging(PortletRequest, Group,
	 *             ServiceContext)}
	 */
	public void disableStaging(
			PortletRequest portletRequest, Group scopeGroup, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception;

	public void disableStaging(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception;

	public void enableLocalStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			ServiceContext serviceContext)
		throws Exception;

	public void enableRemoteStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			ServiceContext serviceContext)
		throws Exception;

	public Group getLiveGroup(long groupId)
		throws PortalException, SystemException;

	public long getLiveGroupId(long groupId)
		throws PortalException, SystemException;

	public List<Layout> getMissingParentLayouts(Layout layout, long liveGroupId)
		throws Exception;

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

	public Map<String, String[]> getStagingParameters();

	public Map<String, String[]> getStagingParameters(
		PortletRequest PortletRequest);

	public WorkflowTask getWorkflowTask(
			long userId, LayoutRevision layoutRevision)
		throws PortalException, SystemException;

	public boolean hasWorkflowTask(long userId, LayoutRevision layoutRevision)
		throws PortalException, SystemException;

	public boolean isIncomplete(Layout layout, long layoutSetBranchId);

	public void publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws Exception;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception;

	public void publishToLive(PortletRequest PortletRequest) throws Exception;

	public void publishToLive(PortletRequest PortletRequest, Portlet portlet)
		throws Exception;

	public void publishToRemote(PortletRequest PortletRequest) throws Exception;

	public void scheduleCopyFromLive(PortletRequest PortletRequest)
		throws Exception;

	public void schedulePublishToLive(PortletRequest PortletRequest)
		throws Exception;

	public void schedulePublishToRemote(PortletRequest PortletRequest)
		throws Exception;

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

	public void unscheduleCopyFromLive(PortletRequest PortletRequest)
		throws Exception;

	public void unschedulePublishToLive(PortletRequest PortletRequest)
		throws Exception;

	public void unschedulePublishToRemote(PortletRequest PortletRequest)
		throws Exception;

	public void updateLastImportSettings(
			Element layoutElement, Layout layout,
			PortletDataContext portletDataContext)
		throws Exception;

	public void updateStaging(PortletRequest PortletRequest, Group liveGroup)
		throws Exception;

}