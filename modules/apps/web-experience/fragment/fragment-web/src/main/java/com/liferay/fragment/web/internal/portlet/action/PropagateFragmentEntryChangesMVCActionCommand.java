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
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
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
		"mvc.command.name=/fragment/propagate_fragment_entry_changes"
	},
	service = MVCActionCommand.class
)
public class PropagateFragmentEntryChangesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] fragmentEntryLinkIds = null;

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		long fragmentEntryLinkId = ParamUtil.getLong(
			actionRequest, "fragmentEntryLinkId");

		if (fragmentEntryLinkId > 0) {
			fragmentEntryLinkIds = new long[] {fragmentEntryLinkId};
		}
		else {
			fragmentEntryLinkIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			fragmentEntryLinkIds, fragmentEntryId, serviceContext);
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

}