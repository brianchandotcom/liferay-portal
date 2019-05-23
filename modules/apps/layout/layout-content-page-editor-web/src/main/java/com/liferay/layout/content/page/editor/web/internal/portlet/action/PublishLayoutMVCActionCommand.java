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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
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
		"mvc.command.name=/content_layout/publish_layout"
	},
	service = MVCActionCommand.class
)
public class PublishLayoutMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PublishLayoutCallable publishLayoutCallable = new PublishLayoutCallable(
			actionRequest);

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig, publishLayoutCallable);
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
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	private class PublishLayoutCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			long plid = ParamUtil.getLong(_actionRequest, "classPK");

			Layout draftLayout = _layoutLocalService.getLayout(plid);

			if ((draftLayout.getClassPK() == 0) ||
				(_portal.getClassNameId(Layout.class) !=
					draftLayout.getClassNameId())) {

				return null;
			}

			Layout layout = _layoutLocalService.getLayout(
				draftLayout.getClassPK());

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_actionRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			LayoutPermissionUtil.check(
				themeDisplay.getPermissionChecker(), draftLayout,
				ActionKeys.UPDATE);

			LayoutPermissionUtil.check(
				themeDisplay.getPermissionChecker(), layout, ActionKeys.UPDATE);

			layout = _layoutCopyHelper.copyLayout(draftLayout, layout);

			UnicodeProperties typeSettingsProperties =
				draftLayout.getTypeSettingsProperties();

			typeSettingsProperties.setProperty("published", "true");

			_layoutLocalService.updateLayout(
				draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
				draftLayout.getLayoutId(), typeSettingsProperties.toString());

			_layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), new Date());

			String portletId = _portal.getPortletId(_actionRequest);

			if (SessionMessages.contains(
					_actionRequest,
					portletId.concat(
						SessionMessages.
							KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE))) {

				SessionMessages.clear(_actionRequest);
			}

			MultiSessionMessages.add(_actionRequest, "layoutPublished");

			return null;
		}

		private PublishLayoutCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}