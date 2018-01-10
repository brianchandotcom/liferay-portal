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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/edit_fragment_entry"
	},
	service = MVCActionCommand.class
)
public class EditFragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		String name = ParamUtil.getString(actionRequest, "name");
		String css = ParamUtil.getString(actionRequest, "cssContent");
		String js = ParamUtil.getString(actionRequest, "jsContent");
		String html = ParamUtil.getString(actionRequest, "htmlContent");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			_fragmentEntryService.updateFragmentEntry(
				fragmentEntryId, name, css, html, js, serviceContext);
		}
		catch (FragmentEntryContentException fece) {
			hideDefaultErrorMessage(actionRequest);

			actionResponse.setRenderParameter(
				"mvcRenderCommandName", "/fragment/edit_fragment_entry");
			actionResponse.setRenderParameter(
				"fragmentEntryId", String.valueOf(fragmentEntryId));
			actionResponse.setRenderParameter("cssContent", css);
			actionResponse.setRenderParameter("jsContent", js);
			actionResponse.setRenderParameter("htmlContent", html);

			SessionErrors.add(actionRequest, fece.getClass(), fece);
		}
	}

	@Reference
	private FragmentEntryService _fragmentEntryService;

}