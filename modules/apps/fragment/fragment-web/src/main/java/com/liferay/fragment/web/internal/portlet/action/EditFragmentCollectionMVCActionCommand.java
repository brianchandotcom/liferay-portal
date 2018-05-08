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
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/edit_fragment_collection"
	},
	service = MVCActionCommand.class
)
public class EditFragmentCollectionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentCollectionId = ParamUtil.getLong(
			actionRequest, "fragmentCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		FragmentCollection fragmentCollection = null;

		if (fragmentCollectionId <= 0) {

			// Add fragment collection

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			fragmentCollection =
				_fragmentCollectionService.addFragmentCollection(
					serviceContext.getScopeGroupId(), name, description,
					serviceContext);
		}
		else {

			// Update fragment collection

			fragmentCollection =
				_fragmentCollectionService.updateFragmentCollection(
					fragmentCollectionId, name, description);
		}

		String redirect = getRedirectURL(actionResponse, fragmentCollection);

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected String getRedirectURL(
		ActionResponse actionResponse, FragmentCollection fragmentCollection) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/fragment/view");
		portletURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(fragmentCollection.getFragmentCollectionId()));

		return portletURL.toString();
	}

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private Portal _portal;

}