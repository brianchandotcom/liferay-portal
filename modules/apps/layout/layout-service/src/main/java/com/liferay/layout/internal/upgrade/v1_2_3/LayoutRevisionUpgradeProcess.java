/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_2_3;

import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevisionConstants;
import com.liferay.portal.kernel.model.LayoutSetBranchTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.LayoutBranchLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.util.List;

/**
 * @author Vendel Toreki
 */
public class LayoutRevisionUpgradeProcess extends UpgradeProcess {

	public LayoutRevisionUpgradeProcess(
		LayoutBranchLocalService layoutBranchLocalService,
		LayoutLocalService layoutLocalService,
		LayoutRevisionLocalService layoutRevisionLocalService,
		LayoutSetBranchLocalService layoutSetBranchLocalService) {

		_layoutBranchLocalService = layoutBranchLocalService;
		_layoutLocalService = layoutLocalService;
		_layoutRevisionLocalService = layoutRevisionLocalService;
		_layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	@Override
	protected void doUpgrade() throws PortalException {
		boolean stagingAdvicesThreadLocalEnabled =
			StagingAdvicesThreadLocal.isEnabled();

		try {
			StagingAdvicesThreadLocal.setEnabled(false);

			for (Layout layout :
					(List<Layout>)_layoutLocalService.dslQuery(
						DSLQueryFactoryUtil.select(
							LayoutTable.INSTANCE
						).from(
							LayoutTable.INSTANCE
						).where(
							LayoutTable.INSTANCE.groupId.in(
								_getBranchingEnabledStagingGroupIdsDSLQuery()
							).and(
								LayoutTable.INSTANCE.hidden.eq(false)
							).and(
								LayoutTable.INSTANCE.system.eq(false)
							).and(
								LayoutTable.INSTANCE.type.eq(
									LayoutConstants.TYPE_CONTENT)
							)
						))) {

				_createInitialContentLayoutRevisions(layout);
			}
		}
		finally {
			StagingAdvicesThreadLocal.setEnabled(
				stagingAdvicesThreadLocalEnabled);
		}
	}

	private void _createInitialContentLayoutRevisions(Layout layout)
		throws PortalException {

		int layoutRevisionsCount =
			_layoutRevisionLocalService.getLayoutRevisionsCount(
				layout.getPlid());

		if (layoutRevisionsCount > 0) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(layout.getUserId());

		for (long layoutSetBranchId :
				(List<Long>)_layoutSetBranchLocalService.dslQuery(
					DSLQueryFactoryUtil.select(
						LayoutSetBranchTable.INSTANCE.layoutSetBranchId
					).from(
						LayoutSetBranchTable.INSTANCE
					).where(
						LayoutSetBranchTable.INSTANCE.groupId.eq(
							layout.getGroupId()
						).and(
							LayoutSetBranchTable.INSTANCE.privateLayout.eq(
								layout.isPrivateLayout())
						)
					))) {

			LayoutBranch layoutBranch =
				_layoutBranchLocalService.getMasterLayoutBranch(
					layoutSetBranchId, layout.getPlid(), serviceContext);

			_layoutRevisionLocalService.addLayoutRevision(
				layout.getUserId(), layoutSetBranchId,
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

	private DSLQuery _getBranchingEnabledStagingGroupIdsDSLQuery() {
		return DSLQueryFactoryUtil.select(
			GroupTable.INSTANCE.groupId
		).from(
			GroupTable.INSTANCE
		).where(
			Predicate.withParentheses(
				GroupTable.INSTANCE.typeSettings.like(
					"%stagedRemotely=true%"
				).and(
					Predicate.withParentheses(
						GroupTable.INSTANCE.typeSettings.like(
							"%branchingPrivate=true%"
						).or(
							GroupTable.INSTANCE.typeSettings.like(
								"%branchingPublic=true%")
						))
				)
			).or(
				GroupTable.INSTANCE.liveGroupId.gt(
					0L
				).and(
					GroupTable.INSTANCE.liveGroupId.in(
						DSLQueryFactoryUtil.select(
							GroupTable.INSTANCE.groupId
						).from(
							GroupTable.INSTANCE
						).where(
							GroupTable.INSTANCE.typeSettings.like(
								"%branchingPrivate=true%"
							).or(
								GroupTable.INSTANCE.typeSettings.like(
									"%branchingPublic=true%")
							)
						))
				)
			)
		);
	}

	private final LayoutBranchLocalService _layoutBranchLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutRevisionLocalService _layoutRevisionLocalService;
	private final LayoutSetBranchLocalService _layoutSetBranchLocalService;

}