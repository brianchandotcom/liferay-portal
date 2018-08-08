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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/update_fragment_entry_links"
	},
	service = MVCActionCommand.class
)
public class UpdateFragmentEntryLinksMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Callable<Void> updateFragmentEntryLinksCallable =
			new UpdateFragmentEntryLinksCallable(actionRequest);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig, updateFragmentEntryLinksCallable);
		}
		catch (Throwable t) {
			_log.error(t, t);

			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	protected void updateFragmentEntryLinks(ActionRequest actionRequest)
		throws PortalException {

		long fragmentEntryLinkId1 = ParamUtil.getLong(
			actionRequest, "fragmentEntryLinkId1");

		long fragmentEntryLinkId2 = ParamUtil.getLong(
			actionRequest, "fragmentEntryLinkId2");

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String data = ParamUtil.getString(actionRequest, "data");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		FragmentEntryLink fragmentEntryLink1 =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLinkId1);

		FragmentEntryLink fragmentEntryLink2 =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLinkId2);

		if ((fragmentEntryLink1 != null) && (fragmentEntryLink2 != null)) {
			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLinkId1, fragmentEntryLink2.getPosition());

			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLinkId2, fragmentEntryLink1.getPosition());

			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructure(
					serviceContext.getScopeGroupId(), classNameId, classPK,
					data);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateFragmentEntryLinksMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	private class UpdateFragmentEntryLinksCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			updateFragmentEntryLinks(_actionRequest);

			return null;
		}

		private UpdateFragmentEntryLinksCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}