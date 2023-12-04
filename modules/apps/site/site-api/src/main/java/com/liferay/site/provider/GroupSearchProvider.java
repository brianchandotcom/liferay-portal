/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.site.search.GroupSearch;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 */
public interface GroupSearchProvider {

	public GroupSearch getGroupSearch(
			PortletRequest portletRequest, PortletURL portletURL)
		throws PortalException;

	public void setResultsAndTotal(
			GroupSearch groupSearch, PortletRequest portletRequest)
		throws PortalException;

}