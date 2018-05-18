<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAdminModuleRegistry commerceAdminModuleRegistry = (CommerceAdminModuleRegistry)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_MODULE_REGISTRY);

NavigableMap<String, CommerceAdminModule> commerceAdminModules = commerceAdminModuleRegistry.getCommerceAdminModules();

String selectedCommerceAdminModuleKey = ParamUtil.getString(request, "commerceAdminModuleKey", commerceAdminModules.firstKey());

List<NavigationItem> navigationItems = new ArrayList<>();

for (Map.Entry<String, CommerceAdminModule> entry : commerceAdminModules.entrySet()) {
	String commerceAdminModuleKey = entry.getKey();
	CommerceAdminModule commerceAdminModule = entry.getValue();

	if (!commerceAdminModule.isVisible(scopeGroupId)) {
		continue;
	}

	PortletURL commerceAdminModuleURL = renderResponse.createRenderURL();

	commerceAdminModuleURL.setParameter("commerceAdminModuleKey", commerceAdminModuleKey);

	NavigationItem navigationItem = new NavigationItem();

	navigationItem.setActive(commerceAdminModuleKey.equals(selectedCommerceAdminModuleKey));
	navigationItem.setHref(commerceAdminModuleURL.toString());
	navigationItem.setLabel(commerceAdminModule.getLabel(locale));

	navigationItems.add(navigationItem);
}
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= navigationItems %>"
/>