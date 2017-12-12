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
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

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
		"mvc.command.name=deleteFragmentEntries"
	},
	service = MVCActionCommand.class
)
public class DeleteFragmentEntriesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteFragmentEntryIds = null;

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		if (fragmentEntryId > 0) {
			deleteFragmentEntryIds = new long[] {fragmentEntryId};
		}
		else {
			deleteFragmentEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		List<FragmentEntry> fragmentEntryList =
			_fragmentEntryService.deleteFragmentEntries(deleteFragmentEntryIds);

		if (ListUtil.isNotEmpty(fragmentEntryList)) {
			SessionErrors.add(actionRequest, "deleteFragmentEntriesInvalid");

			sendRedirect(actionRequest, actionResponse);
		}
	}

	@Reference
	private FragmentEntryService _fragmentEntryService;

}