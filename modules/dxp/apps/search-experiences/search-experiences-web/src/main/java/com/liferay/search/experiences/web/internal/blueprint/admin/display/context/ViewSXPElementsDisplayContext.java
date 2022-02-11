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

package com.liferay.search.experiences.web.internal.blueprint.admin.display.context;

import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.web.internal.display.context.helper.SXPRequestHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 */
public class ViewSXPElementsDisplayContext {

	public ViewSXPElementsDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<SXPElement> sxpElementModelResourcePermission) {

		_sxpElementModelResourcePermission = sxpElementModelResourcePermission;

		_sxpRequestHelper = new SXPRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/search-experiences-rest/v1.0/sxp-elements";
	}

	public boolean hasAddSXPElementPermission() {
		PortletResourcePermission portletResourcePermission =
			_sxpElementModelResourcePermission.getPortletResourcePermission();

		return portletResourcePermission.contains(
			_sxpRequestHelper.getPermissionChecker(), null,
			SXPActionKeys.ADD_SXP_ELEMENT);
	}

	private final ModelResourcePermission<SXPElement>
		_sxpElementModelResourcePermission;
	private final SXPRequestHelper _sxpRequestHelper;

}