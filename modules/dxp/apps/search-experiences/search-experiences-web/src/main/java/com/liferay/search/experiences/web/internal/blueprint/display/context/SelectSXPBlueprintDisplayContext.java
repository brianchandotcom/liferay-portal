/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.blueprint.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintUtil;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

/**
 * @author Kevin Tan
 * @author Petteri Karttunen
 */
public class SelectSXPBlueprintDisplayContext
	extends ViewEntriesDisplayContext<SXPBlueprint> {

	public SelectSXPBlueprintDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			httpServletRequest, "eventName",
			liferayPortletResponse.getNamespace() + "selectBlueprint");

		return _eventName;
	}

	public SearchContainer<SXPBlueprint> getSearchContainer()
		throws PortalException, PortletException {

		SearchContainer<SXPBlueprint> searchContainer =
			super.getSearchContainer();

		SXPBlueprintUtil.populateSXPBlueprintSearchContainer(
			liferayPortletRequest, themeDisplay.getCompanyGroupId(),
			WorkflowConstants.STATUS_APPROVED, searchContainer, getOrderByCol(),
			getOrderByType());

		searchContainer.setRowChecker(null);

		return searchContainer;
	}

	protected PortletURL getIteratorURL() {
		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			SXPBlueprintMVCCommandNames.SELECT_SXP_BLUEPRINT
		).buildPortletURL();
	}

	private String _eventName;

}