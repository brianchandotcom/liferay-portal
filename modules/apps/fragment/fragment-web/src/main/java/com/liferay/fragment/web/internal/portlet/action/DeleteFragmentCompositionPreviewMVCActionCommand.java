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
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/delete_fragment_composition_preview"
	},
	service = MVCActionCommand.class
)
public class DeleteFragmentCompositionPreviewMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentCompositionId = ParamUtil.getLong(
			actionRequest, "fragmentCompositionId");

		FragmentComposition fragmentComposition =
			_fragmentCompositionLocalService.fetchFragmentComposition(
				fragmentCompositionId);

		if (fragmentComposition != null) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				fragmentComposition.getPreviewFileEntryId());

			_fragmentCompositionService.updateFragmentComposition(
				fragmentCompositionId, 0);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	@Reference
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

	@Reference
	private FragmentCompositionService _fragmentCompositionService;

}