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

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/discard_draft_layout"
	},
	service = {AopService.class, MVCActionCommand.class}
)
public class DiscardDraftLayoutMVCActionCommand
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

		long selPlid = ParamUtil.getLong(actionRequest, "selPlid");

		try {
			LayoutPermissionUtil.check(
				themeDisplay.getPermissionChecker(), selPlid,
				ActionKeys.UPDATE);
		}
		catch (PrincipalException principalException) {
			if (!LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), selPlid,
					ActionKeys.UPDATE_LAYOUT_CONTENT)) {

				throw principalException;
			}
		}

		Layout draftLayout = _layoutLocalService.getLayout(selPlid);

		if (!draftLayout.isDraftLayout()) {
			sendRedirect(actionRequest, actionResponse);

			return;
		}

		Layout layout = _layoutLocalService.getLayout(draftLayout.getClassPK());

		int fragmentEntryLinksCount =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksCountByPlid(
				layout.getGroupId(), layout.getPlid());

		if ((fragmentEntryLinksCount == 0) &&
			(layout.getClassNameId() == _portal.getClassNameId(
				LayoutPageTemplateEntry.class))) {

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(layout.getClassPK());

			if (layoutPageTemplateEntry != null) {
				layout = _layoutLocalService.getLayout(
					layoutPageTemplateEntry.getPlid());
			}
		}

		LayoutPermissionUtil.check(
			themeDisplay.getPermissionChecker(), layout.getPlid(),
			ActionKeys.VIEW);

		UnicodeProperties typeSettingsUnicodeProperties =
			draftLayout.getTypeSettingsProperties();

		boolean published = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.getProperty("published"));

		draftLayout = _layoutCopyHelper.copyLayout(layout, draftLayout);

		draftLayout.setStatus(WorkflowConstants.STATUS_APPROVED);

		typeSettingsUnicodeProperties = draftLayout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put(
			"published", String.valueOf(published));

		draftLayout.setTypeSettingsProperties(typeSettingsUnicodeProperties);

		_layoutLocalService.updateLayout(draftLayout);

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private Portal _portal;

}