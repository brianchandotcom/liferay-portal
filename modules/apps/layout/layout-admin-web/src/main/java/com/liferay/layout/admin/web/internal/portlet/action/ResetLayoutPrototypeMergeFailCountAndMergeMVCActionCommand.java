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
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.sites.kernel.util.SitesUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Resets the number of failed merge attempts for the page template, which is
 * accessed from the action request's <code>layoutPrototypeId</code> param. Once
 * the counter is reset, the modified page template is merged back into its
 * linked page, which is accessed from the action request's <code>selPlid</code>
 * param.
 *
 * <p>
 * If the number of failed merge attempts is not equal to zero after the merge,
 * an error key is submitted into the {@link SessionErrors}.
 * </p>
 *
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_prototype/reset_merge_fail_count_and_merge"
	},
	service = MVCActionCommand.class
)
public class ResetLayoutPrototypeMergeFailCountAndMergeMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutPrototypeId");

		SitesUtil.setMergeFailCount(
			_layoutPrototypeLocalService.getLayoutPrototype(layoutPrototypeId),
			0);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference
	private LayoutPrototypeService _layoutPrototypeService;

}