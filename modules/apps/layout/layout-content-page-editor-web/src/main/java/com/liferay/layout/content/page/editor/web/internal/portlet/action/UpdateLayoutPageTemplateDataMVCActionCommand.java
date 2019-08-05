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
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;

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
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/update_layout_page_template_data"
	},
	service = MVCActionCommand.class
)
public class UpdateLayoutPageTemplateDataMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Callable<Void> callable =
			new UpdateLayoutPageTemplateStructuresCallable(actionRequest);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			TransactionInvokerUtil.invoke(_transactionConfig, callable);
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

	protected void updateLayoutPageTemplateData(ActionRequest actionRequest)
		throws PortalException {

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId",
			SegmentsExperienceConstants.ID_DEFAULT);
		String data = ParamUtil.getString(actionRequest, "data");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_layoutPageTemplateStructureService.updateLayoutPageTemplateStructure(
			serviceContext.getScopeGroupId(), classNameId, classPK,
			segmentsExperienceId, data);

		String fragmentEntryLinkIdsString = ParamUtil.getString(
			actionRequest, "fragmentEntryLinkIds");

		if (Validator.isNotNull(fragmentEntryLinkIdsString)) {
			long[] toFragmentEntryLinkIds = JSONUtil.toLongArray(
				JSONFactoryUtil.createJSONArray(fragmentEntryLinkIdsString));

			_fragmentEntryLinkLocalService.deleteFragmentEntryLinks(
				toFragmentEntryLinkIds);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateLayoutPageTemplateDataMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplateStructureService
		_layoutPageTemplateStructureService;

	private class UpdateLayoutPageTemplateStructuresCallable
		implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			updateLayoutPageTemplateData(_actionRequest);

			return null;
		}

		private UpdateLayoutPageTemplateStructuresCallable(
			ActionRequest actionRequest) {

			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}