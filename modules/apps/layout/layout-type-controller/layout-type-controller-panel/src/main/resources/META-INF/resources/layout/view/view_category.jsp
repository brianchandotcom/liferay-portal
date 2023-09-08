<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout/init.jsp" %>

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
%>

<c:if test="<%= !portletCategories.isEmpty() || !portlets.isEmpty() %>">
	<clay:panel
		collapseClassNames="panel-page-category"
		displayTitle="<%= Validator.isNotNull(externalPortletCategory) ? externalPortletCategory : LanguageUtil.get(request, portletCategory.getName()) %>"
		expanded="<%= true %>"
	>
		<div class="panel-body">

			<%
			for (PortletCategory curPortletCategory : portletCategories) {
				request.setAttribute(WebKeys.PORTLET_CATEGORY, curPortletCategory);
			%>

				<liferay-util:include page="/layout/view/view_category.jsp" servletContext="<%= application %>" />

			<%
			}

			for (Portlet portlet : portlets) {
			%>

				<c:if test="<%= !portlet.isInstanceable() %>">
					<div>
						<a
							href="<%=
								PortletURLBuilder.create(
									PortletURLFactoryUtil.create(request, portlet.getRootPortlet(), PortletRequest.ACTION_PHASE)
								).setPortletMode(
									PortletMode.VIEW
								).setWindowState(
									WindowState.MAXIMIZED
								).buildPortletURL()
							%>"
						>
							<%= PortalUtil.getPortletTitle(portlet, application, locale) %>
						</a>
					</div>
				</c:if>

			<%
			}
			%>

		</div>
	</clay:panel>
</c:if>