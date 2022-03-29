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

package com.liferay.layout.internal.upgrade.v1_2_3;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutRevisionConstants;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutBranchLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.util.List;

/**
 * @author Vendel Toreki
 */
public class LayoutRevisionUpgradeProcess extends UpgradeProcess {

	public LayoutRevisionUpgradeProcess(
		GroupLocalService groupLocalService,
		LayoutLocalService layoutLocalService,
		LayoutBranchLocalService layoutBranchLocalService,
		LayoutRevisionLocalService layoutRevisionLocalService,
		LayoutSetBranchLocalService layoutSetBranchLocalService) {

		_groupLocalService = groupLocalService;
		_layoutLocalService = layoutLocalService;
		_layoutBranchLocalService = layoutBranchLocalService;
		_layoutRevisionLocalService = layoutRevisionLocalService;
		_layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	@Override
	protected void doUpgrade() throws PortalException {
		boolean stagingAdvicesThreadLocalEnabled =
			StagingAdvicesThreadLocal.isEnabled();

		try {
			StagingAdvicesThreadLocal.setEnabled(false);

			for (Group group : _getStagingGroups()) {
				if (!_isBranchingEnabledInStagingGroup(group)) {
					continue;
				}

				_upgradeContentLayouts(group);
			}
		}
		finally {
			StagingAdvicesThreadLocal.setEnabled(
				stagingAdvicesThreadLocalEnabled);
		}
	}

	private void _createInitialContentLayoutRevisions(Layout layout)
		throws PortalException {

		List<LayoutRevision> layoutRevisions =
			_layoutRevisionLocalService.getLayoutRevisions(layout.getPlid());

		if (!layoutRevisions.isEmpty()) {
			return;
		}

		List<LayoutSetBranch> layoutSetBranches =
			_layoutSetBranchLocalService.getLayoutSetBranches(
				layout.getGroupId(), layout.isPrivateLayout());

		for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setUserId(layout.getUserId());

			LayoutBranch layoutBranch =
				_layoutBranchLocalService.getMasterLayoutBranch(
					layoutSetBranch.getLayoutSetBranchId(), layout.getPlid(),
					serviceContext);

			_layoutRevisionLocalService.addLayoutRevision(
				layout.getUserId(), layoutSetBranch.getLayoutSetBranchId(),
				layoutBranch.getLayoutBranchId(),
				LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, true,
				layout.getPlid(), LayoutConstants.DEFAULT_PLID,
				layout.isPrivateLayout(), layout.getName(), layout.getTitle(),
				layout.getDescription(), layout.getKeywords(),
				layout.getRobots(), layout.getTypeSettings(),
				layout.getIconImage(), layout.getIconImageId(),
				layout.getThemeId(), layout.getColorSchemeId(), layout.getCss(),
				serviceContext);
		}
	}

	private List<Group> _getStagingGroups() {
		DynamicQuery dynamicQuery = _groupLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.or(
				RestrictionsFactoryUtil.gt("liveGroupId", 0L),
				RestrictionsFactoryUtil.like(
					"typeSettings", "%stagedRemotely=true%")));

		return _groupLocalService.dynamicQuery(dynamicQuery);
	}

	private boolean _isBranchingEnabledInStagingGroup(Group group) {
		if (!group.isStagedRemotely()) {
			group = group.getLiveGroup();
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		if (GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty(
					"branchingPrivate")) ||
			GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty("branchingPublic"))) {

			return true;
		}

		return false;
	}

	private void _upgradeContentLayouts(Group group) throws PortalException {
		DynamicQuery dynamicQuery = _layoutLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("groupId", group.getGroupId()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("type", LayoutConstants.TYPE_CONTENT));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("hidden", false));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("system", false));

		List<Layout> layouts = _layoutLocalService.dynamicQuery(dynamicQuery);

		for (Layout layout : layouts) {
			_createInitialContentLayoutRevisions(layout);
		}
	}

	private final GroupLocalService _groupLocalService;
	private final LayoutBranchLocalService _layoutBranchLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutRevisionLocalService _layoutRevisionLocalService;
	private final LayoutSetBranchLocalService _layoutSetBranchLocalService;

}