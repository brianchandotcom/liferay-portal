<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:renderURL var="manageUserViewsURL">
	<portlet:param name="mvcRenderCommandName" value="/frontend_data_set_admin/manage_user_views" />
	<portlet:param name="backURL" value="<%= themeDisplay.getURLCurrent() %>" />
</portlet:renderURL>

<%
String manageUserViewsLabel = LanguageUtil.get(request, "manage-user-views");
final String manageUserViewsURLString = manageUserViewsURL;
%>

<li class="control-menu-nav-item">
	<clay:dropdown-menu
		aria-label='<%= LanguageUtil.get(themeDisplay.getLocale(), "options") %>'
		cssClass="control-menu-nav-link lfr-portal-tooltip"
		displayType="unstyled"
		dropdownItems='<%=
			new JSPDropdownItemList(pageContext) {
				{
					add(
						dropdownItem -> {
							dropdownItem.setHref(manageUserViewsURLString);
							dropdownItem.setIcon("users");
							dropdownItem.setLabel(manageUserViewsLabel);
						});
				}
			}
		%>'
		icon="ellipsis-v"
		monospaced="<%= true %>"
		small="<%= true %>"
	/>
</li>