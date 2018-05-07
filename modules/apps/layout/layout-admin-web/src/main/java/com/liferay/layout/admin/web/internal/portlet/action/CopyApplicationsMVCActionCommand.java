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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.sites.action.ActionUtil;
import com.liferay.sites.kernel.util.SitesUtil;

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
		"mvc.command.name=/layout/copy_applications"
	},
	service = MVCActionCommand.class
)
public class CopyApplicationsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

		Layout layout = _layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		String layoutType = layout.getType();

		if (!layoutType.equals(LayoutConstants.TYPE_PORTLET)) {
			return;
		}

		long copyLayoutId = ParamUtil.getLong(actionRequest, "copyLayoutId");

		if ((copyLayoutId == 0) || (copyLayoutId == layout.getLayoutId())) {
			return;
		}

		Layout copyLayout = _layoutLocalService.fetchLayout(
			groupId, privateLayout, copyLayoutId);

		if ((copyLayout == null) || !copyLayout.isTypePortlet()) {
			return;
		}

		UnicodeProperties sourceLayoutTypeSettingsProperties =
			copyLayout.getTypeSettingsProperties();

		ActionUtil.removePortletIds(actionRequest, layout);

		ActionUtil.copyPreferences(actionRequest, layout, copyLayout);

		SitesUtil.copyLookAndFeel(layout, copyLayout);

		_layoutService.updateLayout(
			groupId, privateLayout, layoutId,
			sourceLayoutTypeSettingsProperties.toString());
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

}