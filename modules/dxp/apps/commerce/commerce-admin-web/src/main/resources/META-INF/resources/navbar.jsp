<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAdminModuleRegistry commerceAdminModuleRegistry = (CommerceAdminModuleRegistry)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_MODULE_REGISTRY);

NavigableMap<String, CommerceAdminModule> commerceAdminModules = commerceAdminModuleRegistry.getCommerceAdminModules();

String selectedCommerceAdminModuleKey = ParamUtil.getString(request, "commerceAdminModuleKey", commerceAdminModules.firstKey());

CommerceAdminModule selectedCommerceAdminModule = commerceAdminModules.get(selectedCommerceAdminModuleKey);

PortletURL searchURL = selectedCommerceAdminModule.getSearchURL(renderRequest, renderResponse);

String navbarCssClass = "navbar-inverse";

if (searchURL != null) {
	navbarCssClass += " collapse-basic-search";
}
%>

<aui:nav-bar cssClass="<%= navbarCssClass %>" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		for (Map.Entry<String, CommerceAdminModule> entry : commerceAdminModules.entrySet()) {
			String commerceAdminModuleKey = entry.getKey();
			CommerceAdminModule commerceAdminModule = entry.getValue();

			PortletURL commerceAdminModuleURL = renderResponse.createRenderURL();

			commerceAdminModuleURL.setParameter("commerceAdminModuleKey", commerceAdminModuleKey);
		%>

			<aui:nav-item
				href="<%= commerceAdminModuleURL.toString() %>"
				label="<%= commerceAdminModule.getLabel(locale) %>"
				selected="<%= commerceAdminModuleKey.equals(selectedCommerceAdminModuleKey) %>"
			/>

		<%
		}
		%>

	</aui:nav>

	<c:if test="<%= searchURL != null %>">
		<aui:nav-bar-search>
			<aui:form action="<%= searchURL %>" method="get" name="searchFm">
				<liferay-portlet:renderURLParams portletURL="<%= searchURL %>" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>