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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/enable_layout"
	},
	service = MVCActionCommand.class
)
public class EnableLayoutMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long incompleteLayoutRevisionId = ParamUtil.getLong(
			actionRequest, "incompleteLayoutRevisionId");

		LayoutRevision incompleteLayoutRevision =
			_layoutRevisionLocalService.getLayoutRevision(
				incompleteLayoutRevisionId);

		long layoutBranchId = ParamUtil.getLong(
			actionRequest, "layoutBranchId",
			incompleteLayoutRevision.getLayoutBranchId());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		_layoutRevisionLocalService.updateLayoutRevision(
			serviceContext.getUserId(),
			incompleteLayoutRevision.getLayoutRevisionId(), layoutBranchId,
			incompleteLayoutRevision.getName(),
			incompleteLayoutRevision.getTitle(),
			incompleteLayoutRevision.getDescription(),
			incompleteLayoutRevision.getKeywords(),
			incompleteLayoutRevision.getRobots(),
			incompleteLayoutRevision.getTypeSettings(),
			incompleteLayoutRevision.getIconImage(),
			incompleteLayoutRevision.getIconImageId(),
			incompleteLayoutRevision.getThemeId(),
			incompleteLayoutRevision.getColorSchemeId(),
			incompleteLayoutRevision.getCss(), serviceContext);
	}

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

}