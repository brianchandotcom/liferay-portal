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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminIndexUtil;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import javax.portlet.PortletException;

/**
 * @author Kevin Tan
 * @author Petteri Karttunen
 */
public class SelectBlueprintDisplayContext
	extends ViewEntriesDisplayContext<Blueprint> {

	public SelectBlueprintDisplayContext(
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

	public SearchContainer<Blueprint> getSearchContainer()
		throws PortalException, PortletException {

		SearchContainer<Blueprint> searchContainer = super.getSearchContainer();

		BlueprintsAdminIndexUtil.populateBlueprintResults(
			liferayPortletRequest, themeDisplay.getCompanyGroupId(),
			WorkflowConstants.STATUS_APPROVED, searchContainer, getOrderByCol(),
			getOrderByType());

		return searchContainer;
	}

	private String _eventName;

}