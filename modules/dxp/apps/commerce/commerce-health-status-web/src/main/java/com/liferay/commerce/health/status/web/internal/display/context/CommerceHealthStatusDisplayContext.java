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

package com.liferay.commerce.health.status.web.internal.display.context;

import com.liferay.commerce.health.status.CommerceHealthStatus;
import com.liferay.commerce.health.status.web.internal.admin.HealthStatusCommerceAdminModule;
import com.liferay.commerce.health.status.web.internal.util.CommerceHealthStatusRegistry;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceHealthStatusDisplayContext {

	public CommerceHealthStatusDisplayContext(
		CommerceHealthStatusRegistry commerceHealthStatusRegistry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commerceHealthStatusRegistry = commerceHealthStatusRegistry;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public List<CommerceHealthStatus> getCommerceHealthStatuses() {
		return _commerceHealthStatusRegistry.getCommerceHealthStatuses();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey", HealthStatusCommerceAdminModule.KEY);

		return portletURL;
	}

	public SearchContainer<CommerceHealthStatus> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, "there-are-no-results");

		List<CommerceHealthStatus> results = getCommerceHealthStatuses();

		_searchContainer.setResults(results);
		_searchContainer.setTotal(results.size());

		return _searchContainer;
	}

	private final CommerceHealthStatusRegistry _commerceHealthStatusRegistry;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CommerceHealthStatus> _searchContainer;

}