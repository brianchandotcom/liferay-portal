/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.helper.LayoutCopyHelper;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.LockedLayoutException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/discard_draft_layout"
	},
	service = MVCActionCommand.class
)
public class DiscardDraftLayoutMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long selPlid = ParamUtil.getLong(actionRequest, "selPlid");

		LayoutPermissionUtil.checkLayoutUpdatePermission(
			themeDisplay.getPermissionChecker(), selPlid);

		Layout draftLayout = _layoutLocalService.getLayout(selPlid);

		if (!draftLayout.isDraftLayout()) {
			sendRedirect(actionRequest, actionResponse);

			return;
		}

		if (!draftLayout.isUnlocked(Constants.EDIT, themeDisplay.getUserId())) {
			_redirectToLockedLayout(actionRequest, actionResponse);

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

		try {
			boolean published = layout.isPublished();

			draftLayout = _layoutCopyHelper.copyLayoutContent(
				layout, draftLayout);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Layout.class.getName(), actionRequest);

			serviceContext.setAttribute("published", published);

			_layoutLocalService.updateStatus(
				themeDisplay.getUserId(), draftLayout.getPlid(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception exception) {
			if (!(exception instanceof LockedLayoutException) &&
				!(exception.getCause() instanceof LockedLayoutException)) {

				throw exception;
			}

			_redirectToLockedLayout(actionRequest, actionResponse);
		}
	}

	private void _redirectToLockedLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		SessionErrors.add(actionRequest, LockedLayoutException.class);

		hideDefaultSuccessMessage(actionRequest);

		sendRedirect(
			actionRequest, actionResponse,
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					actionRequest, LayoutAdminPortletKeys.GROUP_PAGES,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/layout_admin/locked_layout"
			).setBackURL(
				() -> {
					String backURL = ParamUtil.getString(
						actionRequest, "backURL");

					if (Validator.isNotNull(backURL)) {
						return backURL;
					}

					HttpServletRequest httpServletRequest =
						_portal.getHttpServletRequest(actionRequest);

					backURL = ParamUtil.getString(
						httpServletRequest, "p_l_back_url");

					if (Validator.isNotNull(backURL)) {
						return backURL;
					}

					return ParamUtil.getString(httpServletRequest, "redirect");
				}
			).buildString());
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