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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.util.UADApplicationSummaryHelper;

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
		"mvc.command.name=/view_uad_summary"
	},
	service = MVCRenderCommand.class
)
public class ViewUADSummaryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selectedUser = _portal.getSelectedUser(renderRequest);

			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_SUMMARY_STEP, _determineStep(selectedUser));
		}
		catch (Exception pe) {
			throw new PortletException(pe);
		}

		return "/view_uad_summary.jsp";
	}

	private int _determineStep(User selectedUser) throws Exception {
		if (selectedUser.isActive()) {
			return 1;
		}

		int selectedUserPageCount =
			selectedUser.getPrivateLayoutsPageCount() +
				selectedUser.getPublicLayoutsPageCount();

		if (selectedUserPageCount > 0) {
			return 2;
		}

		int reviewableUADEntitiesCount =
			_uadApplicationSummaryHelper.getReviewableUADEntitiesCount(
				_uadRegistry.getUADEntityDisplayStream(),
				selectedUser.getUserId());

		if (reviewableUADEntitiesCount > 0) {
			return 3;
		}

		Collection<UADAggregator> uadAggregators =
			_uadRegistry.getUADAggregators();

		int selectedUserEntityCount = 0;

		for (UADAggregator uadAggregator : uadAggregators) {
			selectedUserEntityCount += uadAggregator.count(
				selectedUser.getUserId());
		}

		if (selectedUserEntityCount > 0) {
			return 4;
		}

		return 5;
	}

	@Reference
	private Portal _portal;

	@Reference
	private UADApplicationSummaryHelper _uadApplicationSummaryHelper;

	@Reference
	private UADRegistry _uadRegistry;

}