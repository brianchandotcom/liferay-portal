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

package liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.web.internal.constants.UserAssociatedDataWebKeys;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.Collection;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/user_associated_data/manage_user_associated_data_summary"
	},
	service = MVCRenderCommand.class
)
public class ManageUserAssociatedDataSummaryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selUser = PortalUtil.getSelectedUser(renderRequest);

			renderRequest.setAttribute(
				UserAssociatedDataWebKeys.
					MANAGE_USER_ASSOCIATED_DATA_SUMMARY_STEP,
				_determineStep(selUser));
		}
		catch (Exception pe) {
			throw new PortletException(pe);
		}

		return "/manage_user_associated_data_summary.jsp";
	}

	private int _determineStep(User selUser) throws Exception {
		if (selUser.isActive()) {
			return 1;
		}

		int selUserPageCount =
			selUser.getPrivateLayoutsPageCount() +
				selUser.getPublicLayoutsPageCount();

		if (selUserPageCount > 0) {
			return 2;
		}

		Collection<UADEntityAggregator> uadEntityAggregators =
			_uadRegistry.getUADEntityAggregators();

		int selUserEntityCount = 0;

		for (UADEntityAggregator uadEntityAggregator : uadEntityAggregators) {
			selUserEntityCount += uadEntityAggregator.count(
				selUser.getUserId());
		}

		if (selUserEntityCount > 0) {
			return 3;
		}

		return 5;
	}

	@Reference
	private UADRegistry _uadRegistry;

}