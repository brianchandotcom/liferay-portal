<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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