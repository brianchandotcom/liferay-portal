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

<%@ include file="/html/portal/init.jsp" %>

<%
PortletCategory portletCategory = (PortletCategory)request.getAttribute(WebKeys.PORTLET_CATEGORY);

List<PortletCategory> portletCategories = ListUtil.fromCollection(portletCategory.getCategories());

portletCategories = ListUtil.sort(portletCategories, new PortletCategoryComparator(locale));

List<Portlet> portlets = new ArrayList<Portlet>();

for (String portletId : portletCategory.getPortletIds()) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(user.getCompanyId(), portletId);

	if ((portlet != null) && PortletPermissionUtil.contains(permissionChecker, layout, portlet, ActionKeys.ADD_TO_PAGE)) {
		portlets.add(portlet);
	}
}

String externalPortletCategory = null;

for (String portletId : PortletCategoryUtil.getFirstChildPortletIds(portletCategory)) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(user.getCompanyId(), portletId);

	if (portlet != null) {
		continue;
	}

	PortletApp portletApp = portlet.getPortletApp();

	if (portletApp.isWARFile() && Validator.isNull(externalPortletCategory)) {
		PortletConfig curPortletConfig = PortletConfigFactoryUtil.create(portlet, application);

		ResourceBundle portletResourceBundle = curPortletConfig.getResourceBundle(locale);

		externalPortletCategory = ResourceBundleUtil.getString(portletResourceBundle, portletCategory.getName());
	}
}

portlets = ListUtil.sort(portlets, new PortletTitleComparator(application, locale));

if (!portletCategories.isEmpty() || !portlets.isEmpty()) {
	String title = Validator.isNotNull(externalPortletCategory) ? externalPortletCategory : LanguageUtil.get(request, portletCategory.getName());
%>

	<liferay-ui:panel
		collapsible="<%= true %>"
		cssClass="lfr-content-category list-unstyled panel-page-category"
		extended="<%= true %>"
		title="<%= title %>"
	>
		<aui:nav cssClass="list-group">

			<%
			for (PortletCategory curPortletCategory : portletCategories) {
				request.setAttribute(WebKeys.PORTLET_CATEGORY, curPortletCategory);
			%>

				<liferay-util:include page="/html/portal/layout/view/view_category.jsp" />

			<%
			}

			for (Portlet portlet : portlets) {
			%>

				<c:if test="<%= !portlet.isInstanceable() %>">

					<%
					PortletURL portletURL = PortletURLFactoryUtil.create(request, portlet.getRootPortlet(), PortletRequest.ACTION_PHASE);

					portletURL.setPortletMode(PortletMode.VIEW);
					portletURL.setWindowState(WindowState.MAXIMIZED);
					%>

					<div>
						<a href="<%= portletURL %>"><%= PortalUtil.getPortletTitle(portlet, application, locale) %></a>
					</div>
				</c:if>

			<%
			}
			%>

		</aui:nav>
	</liferay-ui:panel>

<%
}
%>