<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/menu/init.jsp" %>

<%
Menu menu = (Menu)request.getAttribute("liferay-ui:menu:menu");

List<MenuItem> menuItems = menu.getMenuItems();
%>

<liferay-ui:icon-menu
	cssClass="<%= menu.getCssClass() %>"
	data="<%= menu.getData() %>"
	direction="<%= menu.getDirection() %>"
	extended="<%= menu.isExtended() %>"
	icon="<%= menu.getIcon() %>"
	markupView="<%= menu.getMarkupView() %>"
	message="<%= menu.getMessage() %>"
	scroll="<%= menu.isScroll() %>"
	showArrow="<%= menu.isShowArrow() %>"
	showExpanded="<%= menu.isShowExpanded() %>"
	showWhenSingleIcon="<%= menu.isShowWhenSingleIcon() %>"
	triggerCssClass="<%= menu.getTriggerCssClass() %>"
>

	<%
	for (MenuItem menuItem : menuItems) {
	%>

		<liferay-ui:menu-item
			menuItem="<%= menuItem %>"
		/>

		<c:if test="<%= menuItem.hasSeparator() %>">
			<li aria-hidden="true" class="dropdown-divider" role="presentation"></li>
		</c:if>

	<%
	}
	%>

</liferay-ui:icon-menu>