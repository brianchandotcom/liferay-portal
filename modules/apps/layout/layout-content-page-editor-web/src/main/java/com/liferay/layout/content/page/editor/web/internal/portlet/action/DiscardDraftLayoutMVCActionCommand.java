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

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/discard_draft_layout"
	},
	service = MVCActionCommand.class
)
public class DiscardDraftLayoutMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DiscardDraftLayoutCallable discardDraftLayoutCallable =
			new DiscardDraftLayoutCallable(actionRequest);

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig, discardDraftLayoutCallable);
		}
		catch (Throwable t) {
			throw new Exception(t);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

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

	private class DiscardDraftLayoutCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_actionRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			long plid = ParamUtil.getLong(_actionRequest, "classPK");

			LayoutPermissionUtil.check(
				themeDisplay.getPermissionChecker(), plid, ActionKeys.UPDATE);

			Layout draftLayout = _layoutLocalService.getLayout(plid);

			if ((draftLayout.getClassPK() == 0) ||
				(_portal.getClassNameId(Layout.class) !=
					draftLayout.getClassNameId())) {

				return null;
			}

			Layout layout = _layoutLocalService.getLayout(
				draftLayout.getClassPK());

			int fragmentEntryLinksCount =
				_fragmentEntryLinkLocalService.
					getClassedModelFragmentEntryLinksCount(
						layout.getGroupId(),
						_portal.getClassNameId(Layout.class), layout.getPlid());

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

			draftLayout = _layoutCopyHelper.copyLayout(layout, draftLayout);

			draftLayout.setModifiedDate(layout.getPublishDate());

			_layoutLocalService.updateLayout(draftLayout);

			return null;
		}

		private DiscardDraftLayoutCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}