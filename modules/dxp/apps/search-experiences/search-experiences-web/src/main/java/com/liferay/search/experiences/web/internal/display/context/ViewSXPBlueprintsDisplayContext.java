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

package com.liferay.search.experiences.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.web.internal.security.permission.resource.SXPBlueprintEntryPermission;
import com.liferay.search.experiences.web.internal.util.SXPBlueprintUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletException;

/**
 * @author Petteri Karttunen
 */
public class ViewSXPBlueprintsDisplayContext
	extends BaseDisplayContext<SXPBlueprint> {

	public ViewSXPBlueprintsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	public List<String> getAvailableActions(SXPBlueprint sxpBlueprint)
		throws PortalException {

		if (SXPBlueprintEntryPermission.contains(
				themeDisplay.getPermissionChecker(), sxpBlueprint,
				ActionKeys.DELETE)) {

			return Collections.singletonList("deleteEntries");
		}

		return Collections.emptyList();
	}

	public SearchContainer<SXPBlueprint> getSearchContainer()
		throws PortalException, PortletException {

		SearchContainer<SXPBlueprint> searchContainer =
			super.getSearchContainer();

		SXPBlueprintUtil.populateSXPBlueprintSearchContainer(
			liferayPortletRequest, themeDisplay.getCompanyGroupId(),
			WorkflowConstants.STATUS_APPROVED, searchContainer, getOrderByCol(),
			getOrderByType());

		return searchContainer;
	}

}