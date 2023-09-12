/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.layout.helper.LayoutCopyHelper;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
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
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template_admin/copy_layout_page_template_entry"
	},
	service = MVCActionCommand.class
)
public class CopyLayoutPageTemplateEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Callable<LayoutPageTemplateEntry> copyLayoutPageTemplateEntryCallable =
			new CopyLayoutPageTemplateEntryCallable(actionRequest);

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig, copyLayoutPageTemplateEntryCallable);
		}
		catch (Throwable throwable) {
			if (_log.isDebugEnabled()) {
				_log.debug(throwable, throwable);
			}

			SessionErrors.add(actionRequest, PortalException.class);
		}

		if (!SessionErrors.isEmpty(actionRequest)) {
			hideDefaultErrorMessage(actionRequest);

			sendRedirect(actionRequest, actionResponse);
		}
	}

	private LayoutPageTemplateEntry _copyLayoutPageTemplateEntry(
			ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long layoutPageTemplateCollectionId = ParamUtil.getLong(
			actionRequest, "layoutPageTemplateCollectionId");
		long layoutPageTemplateEntryId = ParamUtil.getLong(
			actionRequest, "layoutPageTemplateEntryId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.copyLayoutPageTemplateEntry(
				themeDisplay.getScopeGroupId(), layoutPageTemplateCollectionId,
				layoutPageTemplateEntryId, serviceContext);

		LayoutPageTemplateEntry sourceLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
				layoutPageTemplateEntryId);

		Layout sourceLayout = _layoutLocalService.getLayout(
			sourceLayoutPageTemplateEntry.getPlid());

		Layout targetLayout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		_layoutCopyHelper.copyLayoutContent(
			sourceLayout, targetLayout.fetchDraftLayout());

		_layoutCopyHelper.copyLayoutContent(sourceLayout, targetLayout);

		return layoutPageTemplateEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyLayoutPageTemplateEntryMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

	private class CopyLayoutPageTemplateEntryCallable
		implements Callable<LayoutPageTemplateEntry> {

		@Override
		public LayoutPageTemplateEntry call() throws Exception {
			return _copyLayoutPageTemplateEntry(_actionRequest);
		}

		private CopyLayoutPageTemplateEntryCallable(
			ActionRequest actionRequest) {

			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}