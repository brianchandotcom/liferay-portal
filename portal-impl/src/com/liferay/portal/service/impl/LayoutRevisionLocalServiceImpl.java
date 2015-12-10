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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchLayoutRevisionException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutRevisionConstants;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutRevisionLocalServiceBaseImpl;
import com.liferay.portal.util.comparator.LayoutRevisionCreateDateComparator;
import com.liferay.portlet.exportimport.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutRevisionLocalServiceImpl
	extends LayoutRevisionLocalServiceBaseImpl {

	@Override
	public LayoutRevision addLayoutRevision(
			long userId, long layoutSetBranchId, long layoutBranchId,
			long parentLayoutRevisionId, boolean head, long plid,
			long portletPreferencesPlid, boolean privateLayout, String name,
			String title, String description, String keywords, String robots,
			String typeSettings, boolean iconImage, long iconImageId,
			String themeId, String colorSchemeId, String wapThemeId,
			String wapColorSchemeId, String css, ServiceContext serviceContext)
		throws PortalException {

		// Layout revision

		User user = userPersistence.findByPrimaryKey(userId);
		LayoutSetBranch layoutSetBranch =
			layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);
		parentLayoutRevisionId = getParentLayoutRevisionId(
			layoutSetBranchId, parentLayoutRevisionId, plid);
		Date now = new Date();

		long layoutRevisionId = counterLocalService.increment();

		LayoutRevision layoutRevision = layoutRevisionPersistence.create(
			layoutRevisionId);

		layoutRevision.setGroupId(layoutSetBranch.getGroupId());
		layoutRevision.setCompanyId(user.getCompanyId());
		layoutRevision.setUserId(user.getUserId());
		layoutRevision.setUserName(user.getFullName());
		layoutRevision.setLayoutSetBranchId(layoutSetBranchId);
		layoutRevision.setLayoutBranchId(layoutBranchId);
		layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
		layoutRevision.setHead(head);
		layoutRevision.setPlid(plid);
		layoutRevision.setPrivateLayout(privateLayout);
		layoutRevision.setName(name);
		layoutRevision.setTitle(title);
		layoutRevision.setDescription(description);
		layoutRevision.setKeywords(keywords);
		layoutRevision.setRobots(robots);
		layoutRevision.setTypeSettings(typeSettings);
		layoutRevision.setIconImageId(iconImageId);
		layoutRevision.setThemeId(themeId);
		layoutRevision.setColorSchemeId(colorSchemeId);
		layoutRevision.setWapThemeId(wapThemeId);
		layoutRevision.setWapColorSchemeId(wapColorSchemeId);
		layoutRevision.setCss(css);
		layoutRevision.setStatus(WorkflowConstants.STATUS_DRAFT);
		layoutRevision.setStatusDate(serviceContext.getModifiedDate(now));

		layoutRevisionPersistence.update(layoutRevision);

		_layoutRevisionId.set(layoutRevision.getLayoutRevisionId());

		// Portlet preferences

		if (portletPreferencesPlid == LayoutConstants.DEFAULT_PLID) {
			portletPreferencesPlid = plid;
		}

		copyPortletPreferences(layoutRevision, portletPreferencesPlid);

		// Workflow

		if (isWorkflowEnabled(plid)) {
			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), layoutRevision.getGroupId(),
				user.getUserId(), LayoutRevision.class.getName(),
				layoutRevision.getLayoutRevisionId(), layoutRevision,
				serviceContext);
		}
		else {
			updateMajor(layoutRevision);

			updateStatus(
				userId, layoutRevisionId, WorkflowConstants.STATUS_APPROVED,
				serviceContext);
		}

		StagingUtil.setRecentLayoutRevisionId(
			user, layoutSetBranchId, plid,
			layoutRevision.getLayoutRevisionId());

		return layoutRevision;
	}

	@Override
	public void deleteLayoutLayoutRevisions(long plid) throws PortalException {
		for (LayoutRevision layoutRevision : getLayoutRevisions(plid)) {
			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	@Override
	public LayoutRevision deleteLayoutRevision(LayoutRevision layoutRevision)
		throws PortalException {

		if (layoutRevision.hasChildren()) {
			for (LayoutRevision curLayoutRevision :
					layoutRevision.getChildren()) {

				curLayoutRevision.setParentLayoutRevisionId(
					layoutRevision.getParentLayoutRevisionId());

				layoutRevisionPersistence.update(curLayoutRevision);
			}
		}

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferencesByPlid(
				layoutRevision.getLayoutRevisionId());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			try {
				portletPreferencesLocalService.deletePortletPreferences(
					portletPreferences.getPortletPreferencesId());
			}
			catch (NoSuchPortletPreferencesException nsppe) {
			}
		}

		recentLayoutRevisionLocalService.deleteRecentLayoutRevisions(
			layoutRevision);

		if (layoutRevision.isPending()) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				layoutRevision.getCompanyId(), layoutRevision.getGroupId(),
				LayoutRevision.class.getName(),
				layoutRevision.getLayoutRevisionId());
		}

		return layoutRevisionPersistence.remove(layoutRevision);
	}

	@Override
	public LayoutRevision deleteLayoutRevision(long layoutRevisionId)
		throws PortalException {

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		return deleteLayoutRevision(layoutRevision);
	}

	@Override
	public void deleteLayoutRevisions(long layoutSetBranchId, long plid)
		throws PortalException {

		for (LayoutRevision layoutRevision : getLayoutRevisions(
				layoutSetBranchId, plid)) {

			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	@Override
	public void deleteLayoutRevisions(
			long layoutSetBranchId, long layoutBranchId, long plid)
		throws PortalException {

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByL_L_P(
				layoutSetBranchId, layoutBranchId, plid);

		for (LayoutRevision layoutRevision : layoutRevisions) {
			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	@Override
	public void deleteLayoutSetBranchLayoutRevisions(long layoutSetBranchId)
		throws PortalException {

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByLayoutSetBranchId(
				layoutSetBranchId);

		for (LayoutRevision layoutRevision : layoutRevisions) {
			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	@Override
	public LayoutRevision fetchLastLayoutRevision(long plid, boolean head) {
		try {
			return layoutRevisionPersistence.findByH_P_Last(
				head, plid, new LayoutRevisionCreateDateComparator(true));
		}
		catch (NoSuchLayoutRevisionException nslre) {
			return null;
		}
	}

	@Override
	public LayoutRevision fetchLatestLayoutRevision(
		long layoutSetBranchId, long plid) {

		return layoutRevisionPersistence.fetchByL_P_First(
			layoutSetBranchId, plid,
			new LayoutRevisionCreateDateComparator(false));
	}

	@Override
	public LayoutRevision fetchLayoutRevision(
		long layoutSetBranchId, boolean head, long plid) {

		return layoutRevisionPersistence.fetchByL_H_P(
			layoutSetBranchId, head, plid);
	}

	@Override
	public List<LayoutRevision> getChildLayoutRevisions(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid) {

		return layoutRevisionPersistence.findByL_P_P(
			layoutSetBranchId, parentLayoutRevisionId, plid);
	}

	@Override
	public List<LayoutRevision> getChildLayoutRevisions(
		long layoutSetBranchId, long parentLayoutRevision, long plid, int start,
		int end, OrderByComparator<LayoutRevision> orderByComparator) {

		return layoutRevisionPersistence.findByL_P_P(
			layoutSetBranchId, parentLayoutRevision, plid, start, end,
			orderByComparator);
	}

	@Override
	public int getChildLayoutRevisionsCount(
		long layoutSetBranchId, long parentLayoutRevision, long plid) {

		return layoutRevisionPersistence.countByL_P_P(
			layoutSetBranchId, parentLayoutRevision, plid);
	}

	@Override
	public LayoutRevision getLayoutRevision(
			long layoutSetBranchId, long plid, boolean head)
		throws PortalException {

		return layoutRevisionPersistence.findByL_H_P(
			layoutSetBranchId, head, plid);
	}

	@Override
	public LayoutRevision getLayoutRevision(
			long layoutSetBranchId, long layoutBranchId, long plid)
		throws PortalException {

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByL_L_P(
				layoutSetBranchId, layoutBranchId, plid, 0, 1,
				new LayoutRevisionCreateDateComparator(false));

		if (!layoutRevisions.isEmpty()) {
			return layoutRevisions.get(0);
		}

		StringBundler sb = new StringBundler(7);

		sb.append("{layoutSetBranchId=");
		sb.append(layoutSetBranchId);
		sb.append(", layoutBranchId=");
		sb.append(layoutBranchId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append("}");

		throw new NoSuchLayoutRevisionException(sb.toString());
	}

	@Override
	public List<LayoutRevision> getLayoutRevisions(long plid) {
		return layoutRevisionPersistence.findByPlid(plid);
	}

	@Override
	public List<LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, boolean head) {

		return layoutRevisionPersistence.findByL_H(layoutSetBranchId, head);
	}

	@Override
	public List<LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, int status) {

		return layoutRevisionPersistence.findByL_S(layoutSetBranchId, status);
	}

	@Override
	public List<LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid) {

		return layoutRevisionPersistence.findByL_P(layoutSetBranchId, plid);
	}

	@Override
	public List<LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, int status) {

		return layoutRevisionPersistence.findByL_P_S(
			layoutSetBranchId, plid, status);
	}

	@Override
	public List<LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return layoutRevisionPersistence.findByL_P(
			layoutSetBranchId, plid, start, end, orderByComparator);
	}

	@Override
	public List<LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long layoutBranchId, long plid, int start,
		int end, OrderByComparator<LayoutRevision> orderByComparator) {

		return layoutRevisionPersistence.findByL_L_P(
			layoutSetBranchId, layoutBranchId, plid, start, end,
			orderByComparator);
	}

	@Override
	public int getLayoutRevisionsCount(
		long layoutSetBranchId, long layoutBranchId, long plid) {

		return layoutRevisionPersistence.countByL_L_P(
			layoutSetBranchId, layoutBranchId, plid);
	}

	@Override
	public LayoutRevision updateLayoutRevision(
			long userId, long layoutRevisionId, long layoutBranchId,
			String name, String title, String description, String keywords,
			String robots, String typeSettings, boolean iconImage,
			long iconImageId, String themeId, String colorSchemeId,
			String wapThemeId, String wapColorSchemeId, String css,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout revision

		User user = userPersistence.findByPrimaryKey(userId);
		LayoutRevision oldLayoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);
		Date now = new Date();

		LayoutRevision layoutRevision = null;

		int workflowAction = serviceContext.getWorkflowAction();

		boolean revisionInProgress = ParamUtil.getBoolean(
			serviceContext, "revisionInProgress");

		if (!MergeLayoutPrototypesThreadLocal.isInProgress() &&
			(workflowAction != WorkflowConstants.ACTION_PUBLISH) &&
			(_layoutRevisionId.get() <= 0) && !revisionInProgress) {

			long newLayoutRevisionId = counterLocalService.increment();

			layoutRevision = layoutRevisionPersistence.create(
				newLayoutRevisionId);

			layoutRevision.setGroupId(oldLayoutRevision.getGroupId());
			layoutRevision.setCompanyId(oldLayoutRevision.getCompanyId());
			layoutRevision.setUserId(user.getUserId());
			layoutRevision.setUserName(user.getFullName());
			layoutRevision.setLayoutSetBranchId(
				oldLayoutRevision.getLayoutSetBranchId());
			layoutRevision.setParentLayoutRevisionId(
				oldLayoutRevision.getLayoutRevisionId());
			layoutRevision.setHead(false);
			layoutRevision.setLayoutBranchId(layoutBranchId);
			layoutRevision.setPlid(oldLayoutRevision.getPlid());
			layoutRevision.setPrivateLayout(
				oldLayoutRevision.isPrivateLayout());
			layoutRevision.setName(name);
			layoutRevision.setTitle(title);
			layoutRevision.setDescription(description);
			layoutRevision.setKeywords(keywords);
			layoutRevision.setRobots(robots);
			layoutRevision.setTypeSettings(typeSettings);
			layoutRevision.setIconImageId(iconImageId);
			layoutRevision.setThemeId(themeId);
			layoutRevision.setColorSchemeId(colorSchemeId);
			layoutRevision.setWapThemeId(wapThemeId);
			layoutRevision.setWapColorSchemeId(wapColorSchemeId);
			layoutRevision.setCss(css);
			layoutRevision.setStatus(WorkflowConstants.STATUS_DRAFT);
			layoutRevision.setStatusDate(serviceContext.getModifiedDate(now));

			layoutRevisionPersistence.update(layoutRevision);

			_layoutRevisionId.set(layoutRevision.getLayoutRevisionId());

			// Portlet preferences

			copyPortletPreferences(
				layoutRevision, layoutRevision.getParentLayoutRevisionId());

			StagingUtil.setRecentLayoutBranchId(
				user, layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid(), layoutRevision.getLayoutBranchId());

			StagingUtil.setRecentLayoutRevisionId(
				user, layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid(), layoutRevision.getLayoutRevisionId());
		}
		else {
			if (_layoutRevisionId.get() > 0) {
				layoutRevision = layoutRevisionPersistence.findByPrimaryKey(
					_layoutRevisionId.get());
			}
			else {
				layoutRevision = oldLayoutRevision;
			}

			layoutRevision.setName(name);
			layoutRevision.setTitle(title);
			layoutRevision.setDescription(description);
			layoutRevision.setKeywords(keywords);
			layoutRevision.setRobots(robots);
			layoutRevision.setTypeSettings(typeSettings);
			layoutRevision.setIconImageId(iconImageId);
			layoutRevision.setThemeId(themeId);
			layoutRevision.setColorSchemeId(colorSchemeId);
			layoutRevision.setWapThemeId(wapThemeId);
			layoutRevision.setWapColorSchemeId(wapColorSchemeId);
			layoutRevision.setCss(css);

			layoutRevisionPersistence.update(layoutRevision);

			_layoutRevisionId.set(layoutRevision.getLayoutRevisionId());
		}

		boolean major = ParamUtil.getBoolean(serviceContext, "major");

		if (major || !isWorkflowEnabled(layoutRevision.getPlid())) {
			updateMajor(layoutRevision);
		}

		// Workflow

		if (isWorkflowEnabled(layoutRevision.getPlid())) {
			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				layoutRevision.getCompanyId(), layoutRevision.getGroupId(),
				userId, LayoutRevision.class.getName(),
				layoutRevision.getLayoutRevisionId(), layoutRevision,
				serviceContext);
		}
		else {
			updateStatus(
				userId, layoutRevision.getLayoutRevisionId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}

		return layoutRevision;
	}

	@Override
	public LayoutRevision updateStatus(
			long userId, long layoutRevisionId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		layoutRevision.setStatus(status);
		layoutRevision.setStatusByUserId(user.getUserId());
		layoutRevision.setStatusByUserName(user.getFullName());
		layoutRevision.setStatusDate(new Date());

		if (status == WorkflowConstants.STATUS_APPROVED) {
			layoutRevision.setHead(true);
		}
		else {
			layoutRevision.setHead(false);
			layoutRevision.setMajor(false);
		}

		layoutRevisionPersistence.update(layoutRevision);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			List<LayoutRevision> layoutRevisions =
				layoutRevisionPersistence.findByL_P(
					layoutRevision.getLayoutSetBranchId(),
					layoutRevision.getPlid());

			for (LayoutRevision curLayoutRevision : layoutRevisions) {
				if (curLayoutRevision.getLayoutRevisionId() !=
						layoutRevision.getLayoutRevisionId()) {

					curLayoutRevision.setHead(false);

					layoutRevisionPersistence.update(curLayoutRevision);
				}
			}
		}
		else {
			List<LayoutRevision> layoutRevisions =
				layoutRevisionPersistence.findByL_P_S(
					layoutRevision.getLayoutSetBranchId(),
					layoutRevision.getPlid(),
					WorkflowConstants.STATUS_APPROVED);

			for (LayoutRevision curLayoutRevision : layoutRevisions) {
				if (curLayoutRevision.getLayoutRevisionId() !=
						layoutRevision.getLayoutRevisionId()) {

					curLayoutRevision.setHead(true);

					layoutRevisionPersistence.update(curLayoutRevision);

					break;
				}
			}
		}

		return layoutRevision;
	}

	protected void copyPortletPreferences(
		LayoutRevision layoutRevision, long parentLayoutRevisionId) {

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferencesByPlid(
				parentLayoutRevisionId);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			portletPreferencesLocalService.addPortletPreferences(
				layoutRevision.getCompanyId(), portletPreferences.getOwnerId(),
				portletPreferences.getOwnerType(),
				layoutRevision.getLayoutRevisionId(),
				portletPreferences.getPortletId(), null,
				portletPreferences.getPreferences());
		}
	}

	protected long getParentLayoutRevisionId(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid) {

		LayoutRevision parentLayoutRevision = null;

		if (parentLayoutRevisionId > 0) {
			parentLayoutRevision = layoutRevisionPersistence.fetchByPrimaryKey(
				parentLayoutRevisionId);

			if (parentLayoutRevision == null) {
				List<LayoutRevision> layoutRevisions =
					layoutRevisionPersistence.findByL_P(
						layoutSetBranchId, plid, 0, 1);

				if (!layoutRevisions.isEmpty()) {
					parentLayoutRevision = layoutRevisions.get(0);
				}
			}
		}

		if (parentLayoutRevision != null) {
			return parentLayoutRevision.getLayoutRevisionId();
		}

		return LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID;
	}

	protected boolean isWorkflowEnabled(long plid) throws PortalException {
		Layout layout = layoutLocalService.getLayout(plid);

		if (layout.isTypeLinkToLayout() || layout.isTypeURL()) {
			return false;
		}

		return true;
	}

	protected LayoutRevision updateMajor(LayoutRevision layoutRevision)
		throws PortalException {

		List<LayoutRevision> parentLayoutRevisions = new ArrayList<>();

		long parentLayoutRevisionId =
			layoutRevision.getParentLayoutRevisionId();

		while (parentLayoutRevisionId !=
					LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) {

			LayoutRevision parentLayoutRevision =
				layoutRevisionPersistence.findByPrimaryKey(
					parentLayoutRevisionId);

			if (parentLayoutRevision.isMajor()) {
				break;
			}

			parentLayoutRevisions.add(parentLayoutRevision);

			parentLayoutRevisionId =
				parentLayoutRevision.getParentLayoutRevisionId();
		}

		layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
		layoutRevision.setMajor(true);

		layoutRevisionPersistence.update(layoutRevision);

		for (LayoutRevision parentLayoutRevision : parentLayoutRevisions) {
			List<LayoutRevision> childrenLayoutRevisions =
				parentLayoutRevision.getChildren();

			if (!childrenLayoutRevisions.isEmpty()) {
				break;
			}

			layoutRevisionLocalService.deleteLayoutRevision(
				parentLayoutRevision);
		}

		return layoutRevision;
	}

	private static final ThreadLocal<Long> _layoutRevisionId =
		new AutoResetThreadLocal<>(
			LayoutRevisionLocalServiceImpl.class + "._layoutRevisionId", 0L);

}