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

<%@ include file="/control/menu/init.jsp" %>

<%
LayoutActionsDisplayContext layoutActionsDisplayContext = (LayoutActionsDisplayContext)request.getAttribute(LayoutAdminWebKeys.LAYOUT_ACTIONS_DISPLAY_CONTEXT);
%>

<li class="control-menu-nav-item">
	<clay:dropdown-menu
		aria-label='<%= LanguageUtil.get(resourceBundle, "options") %>'
		borderless="<%= true %>"
		displayType="unstyled"
		dropdownItems="<%= layoutActionsDisplayContext.getDropdownItems() %>"
		icon="ellipsis-v"
		menuProps='<%=
			HashMapBuilder.put(
				"className", "cadmin"
			).build()
		%>'
		monospaced="<%= true %>"
		propsTransformer="js/LayoutActionDropdownPropsTransformer"
		small="<%= true %>"
	/>
</li>