/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutRevisionConstants;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutRevisionLocalServiceBaseImpl;
import com.liferay.portal.util.comparator.LayoutRevisionCreateDateComparator;

import java.util.Date;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutRevisionLocalServiceImpl
	extends LayoutRevisionLocalServiceBaseImpl {

	public LayoutRevision addLayoutRevision(
			long userId, long layoutSetBranchId, long layoutBranchId,
			long parentLayoutRevisionId, boolean head,  long plid,
			boolean privateLayout, String name, String title,
			String description, String keywords, String robots,
			String typeSettings, boolean iconImage, long iconImageId,
			String themeId, String colorSchemeId, String wapThemeId,
			String wapColorSchemeId, String css, ServiceContext serviceContext)
		throws PortalException, SystemException {

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
		layoutRevision.setCreateDate(serviceContext.getCreateDate(now));
		layoutRevision.setModifiedDate(serviceContext.getModifiedDate(now));
		layoutRevision.setLayoutSetBranchId(layoutSetBranchId);
		layoutRevision.setLayoutBranchId(layoutBranchId);
		layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
		layoutRevision.setHead(head);

		long mergeLayoutRevisionId = ParamUtil.getLong(
			serviceContext, "mergeLayoutRevisionId");

		if (mergeLayoutRevisionId > 0) {
			layoutRevision.setMajor(true);
		}

		layoutRevision.setPlid(plid);
		layoutRevision.setPrivateLayout(privateLayout);
		layoutRevision.setName(name);
		layoutRevision.setTitle(title);
		layoutRevision.setDescription(description);
		layoutRevision.setKeywords(keywords);
		layoutRevision.setRobots(robots);
		layoutRevision.setTypeSettings(typeSettings);

		if (iconImage) {
			layoutRevision.setIconImage(iconImage);
			layoutRevision.setIconImageId(iconImageId);
		}

		layoutRevision.setThemeId(themeId);
		layoutRevision.setColorSchemeId(colorSchemeId);
		layoutRevision.setWapThemeId(wapThemeId);
		layoutRevision.setWapColorSchemeId(wapColorSchemeId);
		layoutRevision.setCss(css);
		layoutRevision.setStatus(WorkflowConstants.STATUS_DRAFT);
		layoutRevision.setStatusDate(serviceContext.getModifiedDate(now));

		layoutRevisionPersistence.update(layoutRevision, false);

		// Portlet preferences

		if (parentLayoutRevisionId ==
				LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) {

			parentLayoutRevisionId = layoutRevision.getPlid();
		}

		long sourceParentLayoutRevisionId = parentLayoutRevisionId;

		if (mergeLayoutRevisionId > 0) {
			sourceParentLayoutRevisionId = mergeLayoutRevisionId;
		}

		copyPortletPreferences(
			layoutRevision, sourceParentLayoutRevisionId, serviceContext);

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), layoutRevision.getGroupId(), user.getUserId(),
			LayoutRevision.class.getName(),
			layoutRevision.getLayoutRevisionId(), layoutRevision,
			serviceContext);

		return layoutRevision;
	}

	public void deleteLayoutLayoutRevisions(long plid)
		throws PortalException, SystemException {

		for (LayoutRevision layoutRevision : getLayoutRevisions(plid)) {
			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	@Override
	public void deleteLayoutRevision(LayoutRevision layoutRevision)
		throws PortalException, SystemException {

		if (layoutRevision.hasChildren()) {
			for (LayoutRevision curLayoutRevision :
					layoutRevision.getChildren()) {

				curLayoutRevision.setParentLayoutRevisionId(
					layoutRevision.getParentLayoutRevisionId());

				layoutRevisionPersistence.update(curLayoutRevision, false);
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

		layoutRevisionPersistence.remove(layoutRevision);
	}

	@Override
	public void deleteLayoutRevision(long layoutRevisionId)
		throws PortalException, SystemException {

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
	}

	public void deleteLayoutRevisions(long layoutSetBranchId, long plid)
		throws PortalException, SystemException {

		for (LayoutRevision layoutRevision : getLayoutRevisions(
				layoutSetBranchId, plid)) {

			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	public void deleteLayoutRevisions(
			long layoutSetBranchId, long layoutBranchId, long plid)
		throws PortalException, SystemException {

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByL_L_P(
				layoutSetBranchId, layoutBranchId, plid);

		for (LayoutRevision layoutRevision : layoutRevisions) {
			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	public void deleteLayoutSetBranchLayoutRevisions(long layoutSetBranchId)
		throws PortalException, SystemException {

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByLayoutSetBranchId(
				layoutSetBranchId);

		for (LayoutRevision layoutRevision : layoutRevisions) {
			layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);
		}
	}

	public List<LayoutRevision> getChildLayoutRevisions(
			long layoutSetBranchId, long parentLayoutRevisionId, long plid)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P_P(
			layoutSetBranchId, parentLayoutRevisionId, plid);
	}

	public List<LayoutRevision> getChildLayoutRevisions(
			long layoutSetBranchId, long parentLayoutRevision, long plid,
			int start, int end, OrderByComparator orderByComparator)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P_P(
			layoutSetBranchId, parentLayoutRevision, plid, start, end,
			orderByComparator);
	}

	public int getChildLayoutRevisionsCount(
			long layoutSetBranchId, long parentLayoutRevision, long plid)
		throws SystemException {

		return layoutRevisionPersistence.countByL_P_P(
			layoutSetBranchId, parentLayoutRevision, plid);
	}

	@Override
	public LayoutRevision getLayoutRevision(long layoutRevisionId)
		throws PortalException, SystemException {

		return layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);
	}

	public LayoutRevision getLayoutRevision(
			long layoutSetBranchId, long plid, boolean head)
		throws PortalException, SystemException {

		return layoutRevisionPersistence.findByL_H_P(
			layoutSetBranchId, head, plid);
	}

	public LayoutRevision getLayoutRevision(
			long layoutSetBranchId, long layoutBranchId, long plid)
		throws PortalException, SystemException {

		List<LayoutRevision> layoutRevisions =
			layoutRevisionPersistence.findByL_L_P(
				layoutSetBranchId, layoutBranchId, plid, 0, 1,
				new LayoutRevisionCreateDateComparator(false));

		if (!layoutRevisions.isEmpty()) {
			return layoutRevisions.get(0);
		}

		throw new NoSuchLayoutRevisionException();
	}

	public List<LayoutRevision> getLayoutRevisions(long plid)
		throws SystemException {

		return layoutRevisionPersistence.findByPlid(plid);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutSetBranchId, boolean head)
		throws SystemException {

		return layoutRevisionPersistence.findByL_H(layoutSetBranchId, head);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutSetBranchId, int status)
		throws SystemException {

		return layoutRevisionPersistence.findByL_S(layoutSetBranchId, status);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutSetBranchId, long plid)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P(layoutSetBranchId, plid);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutSetBranchId, long plid, int status)
		throws SystemException {

		return layoutRevisionPersistence.findByL_P_S(
			layoutSetBranchId, plid, status);
	}

	public List<LayoutRevision> getLayoutRevisions(
			long layoutSetBranchId, long layoutBranchId, long plid,
			int start, int end, OrderByComparator orderByComparator)
		throws SystemException {

		return layoutRevisionPersistence.findByL_L_P(
			layoutSetBranchId, layoutBranchId, plid, start, end,
			orderByComparator);
	}

	public int getLayoutRevisionsCount(
			long layoutSetBranchId, long layoutBranchId, long plid)
		throws SystemException {

		return layoutRevisionPersistence.countByL_L_P(
			layoutSetBranchId, layoutBranchId, plid);
	}

	public LayoutRevision updateLayoutRevision(
			long userId, long layoutRevisionId, long layoutBranchId,
			String name, String title, String description, String keywords,
			String robots, String typeSettings, boolean iconImage,
			long iconImageId, String themeId, String colorSchemeId,
			String wapThemeId, String wapColorSchemeId, String css,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout revision

		User user = userPersistence.findByPrimaryKey(userId);
		LayoutRevision oldLayoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);
		Date now = new Date();

		LayoutRevision layoutRevision = null;

		int workflowAction = serviceContext.getWorkflowAction();

		if (workflowAction != WorkflowConstants.ACTION_PUBLISH) {
			long newLayoutRevisionId = counterLocalService.increment();

			layoutRevision = layoutRevisionPersistence.create(
				newLayoutRevisionId);

			layoutRevision.setGroupId(oldLayoutRevision.getGroupId());
			layoutRevision.setCompanyId(oldLayoutRevision.getCompanyId());
			layoutRevision.setUserId(user.getUserId());
			layoutRevision.setUserName(user.getFullName());
			layoutRevision.setCreateDate(serviceContext.getCreateDate(now));
			layoutRevision.setModifiedDate(serviceContext.getModifiedDate(now));
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

			if (iconImage) {
				layoutRevision.setIconImage(iconImage);
				layoutRevision.setIconImageId(iconImageId);
			}

			layoutRevision.setThemeId(themeId);
			layoutRevision.setColorSchemeId(colorSchemeId);
			layoutRevision.setWapThemeId(wapThemeId);
			layoutRevision.setWapColorSchemeId(wapColorSchemeId);
			layoutRevision.setCss(css);
			layoutRevision.setStatus(WorkflowConstants.STATUS_DRAFT);
			layoutRevision.setStatusDate(serviceContext.getModifiedDate(now));

			layoutRevisionPersistence.update(layoutRevision, false);

			// Portlet preferences

			copyPortletPreferences(
				layoutRevision, layoutRevision.getParentLayoutRevisionId(),
				serviceContext);

			StagingUtil.deleteRecentLayoutRevisionId(
				user, layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid());

			StagingUtil.setRecentLayoutBranchId(
				user, layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid(), layoutRevision.getLayoutBranchId());
		}
		else {
			layoutRevision = oldLayoutRevision;
		}

		boolean major = ParamUtil.getBoolean(serviceContext, "major");

		if (major) {
			updateMajor(layoutRevision);
		}

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			layoutRevision.getCompanyId(), layoutRevision.getGroupId(),
			userId, LayoutRevision.class.getName(),
			layoutRevision.getLayoutRevisionId(), layoutRevision,
			serviceContext);

		return layoutRevision;
	}

	public LayoutRevision updateStatus(
			long userId, long layoutRevisionId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		layoutRevision.setStatus(status);
		layoutRevision.setStatusByUserId(user.getUserId());
		layoutRevision.setStatusByUserName(user.getFullName());
		layoutRevision.setStatusDate(new Date());

		if (status == WorkflowConstants.STATUS_APPROVED) {
			layoutRevision.setHead(true);

			List<LayoutRevision> layoutRevisions =
				layoutRevisionPersistence.findByL_P(
					layoutRevision.getLayoutSetBranchId(),
					layoutRevision.getPlid());

			for (LayoutRevision curLayoutRevision : layoutRevisions) {
				if (curLayoutRevision.getLayoutRevisionId() !=
						layoutRevision.getLayoutRevisionId()) {

					curLayoutRevision.setHead(false);

					layoutRevisionPersistence.update(curLayoutRevision, false);
				}
			}
		}
		else {
			layoutRevision.setHead(false);

			List<LayoutRevision> layoutRevisions =
				layoutRevisionPersistence.findByL_P_S(
					layoutRevision.getLayoutSetBranchId(),
					layoutRevision.getPlid(),
					WorkflowConstants.STATUS_APPROVED);

			for (LayoutRevision curLayoutRevision : layoutRevisions) {
				if (curLayoutRevision.getLayoutRevisionId() !=
						layoutRevision.getLayoutRevisionId()) {

					curLayoutRevision.setHead(true);

					layoutRevisionPersistence.update(curLayoutRevision, false);

					break;
				}
			}
		}

		layoutRevisionPersistence.update(layoutRevision, false);

		return layoutRevision;
	}

	protected void copyPortletPreferences(
			LayoutRevision layoutRevision, long parentLayoutRevisionId,
			ServiceContext serviceContext)
		throws SystemException {

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
			long layoutSetBranchId, long parentLayoutRevisionId, long plid)
		throws SystemException {

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

	protected LayoutRevision updateMajor(LayoutRevision layoutRevision)
		throws PortalException, SystemException {

		long parentLayoutRevisionId =
			layoutRevision.getParentLayoutRevisionId();

		boolean fork = false;

		while (parentLayoutRevisionId !=
					LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) {

			LayoutRevision parentLayoutRevision =
				layoutRevisionPersistence.findByPrimaryKey(
					parentLayoutRevisionId);

			if (parentLayoutRevision.isMajor()) {
				break;
			}

			parentLayoutRevisionId =
				parentLayoutRevision.getParentLayoutRevisionId();

			if (parentLayoutRevision.getChildren().size() > 1) {
				fork = true;
			}

			if (!fork) {
				layoutRevisionLocalService.deleteLayoutRevision(
					parentLayoutRevision);
			}
		}

		layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
		layoutRevision.setMajor(true);

		layoutRevisionPersistence.update(layoutRevision, false);

		return layoutRevision;
	}

}