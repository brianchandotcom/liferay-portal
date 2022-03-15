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

<%@ include file="/html/taglib/ui/menu/init.jsp" %>

<%
Menu menu = (Menu)request.getAttribute("liferay-ui:menu:menu");

List<MenuItem> menuItems = menu.getMenuItems();

Optional<MenuItem> menuItemOptional = menuItems.stream(
).filter(
	menuItem -> menuItem.getIcon() != null
).findFirst();
%>

<liferay-ui:icon-menu
	cssClass="<%= menu.getCssClass() %>"
	data="<%= menu.getData() %>"
	direction="<%= menu.getDirection() %>"
	dropdownCssClass='<%= menuItemOptional.isPresent() ? "dropdown-menu-indicator-start" : StringPool.BLANK %>'
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