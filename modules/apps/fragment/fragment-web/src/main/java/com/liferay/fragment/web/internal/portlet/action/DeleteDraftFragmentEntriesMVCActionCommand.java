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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/delete_draft_fragment_entries"
	},
	service = MVCActionCommand.class
)
public class DeleteDraftFragmentEntriesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		FragmentEntry fragmentEntry = _fragmentEntryService.fetchFragmentEntry(
			fragmentEntryId);

		if (fragmentEntry == null) {
			return;
		}

		if (fragmentEntry.isDraft()) {
			_fragmentEntryService.deleteFragmentEntry(
				fragmentEntry.getFragmentEntryId());
		}
		else {
			FragmentEntry draftFragmentEntry =
				fragmentEntry.fetchDraftFragmentEntry();

			if (draftFragmentEntry != null) {
				_fragmentEntryService.deleteFragmentEntry(
					draftFragmentEntry.getFragmentEntryId());
			}
		}
	}

	@Reference
	private FragmentEntryService _fragmentEntryService;

}