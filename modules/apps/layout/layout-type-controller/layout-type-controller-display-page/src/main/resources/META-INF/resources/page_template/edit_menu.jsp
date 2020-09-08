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

<%@ include file="/page_template/init.jsp" %>

<%
EditDisplayPageMenuDisplayContext editDisplayPageMenuDisplayContext = (EditDisplayPageMenuDisplayContext)request.getAttribute(DisplayPageLayoutTypeControllerWebKeys.EDIT_DISPLAY_PAGE_MENU_DISPLAY_CONTEXT);
%>

<li class="control-menu-nav-item">
	<clay:dropdown-menu
		borderless="<%= true %>"
		displayType="unstyled"
		dropdownItems="<%= editDisplayPageMenuDisplayContext.getDropdownItems() %>"
		icon="pencil"
		monospaced="<%= true %>"
		small="<%= true %>"
	/>
</li>