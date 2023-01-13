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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/save_variant_segments_experience"
	},
	service = {AopService.class, MVCActionCommand.class}
)
public class SaveVariantSegmentsExperienceMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout draftLayout = _layoutLocalService.getLayout(
			themeDisplay.getPlid());

		if (!draftLayout.isDraftLayout()) {
			sendRedirect(actionRequest, actionResponse);

			return;
		}

		Layout layout = _layoutLocalService.getLayout(draftLayout.getClassPK());

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			themeDisplay.getPermissionChecker(), draftLayout);

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			themeDisplay.getPermissionChecker(), layout);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		_layoutCopyHelper.copyLayout(
			new long[] {segmentsExperienceId}, draftLayout, layout);

		hideDefaultSuccessMessage(actionRequest);

		MultiSessionMessages.add(actionRequest, "variantSaved");

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}