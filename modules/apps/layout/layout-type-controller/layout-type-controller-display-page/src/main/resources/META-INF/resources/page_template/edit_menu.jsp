<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/page_template/init.jsp" %>

<%
EditDisplayPageMenuDisplayContext editDisplayPageMenuDisplayContext = (EditDisplayPageMenuDisplayContext)request.getAttribute(DisplayPageLayoutTypeControllerWebKeys.EDIT_DISPLAY_PAGE_MENU_DISPLAY_CONTEXT);
%>

<li class="control-menu-nav-item">
	<clay:dropdown-menu
		aria-label='<%= LanguageUtil.get(request, "edit") %>'
		borderless="<%= true %>"
		cssClass="control-menu-nav-link"
		displayType="unstyled"
		dropdownItems="<%= editDisplayPageMenuDisplayContext.getDropdownItems() %>"
		icon="pencil"
		monospaced="<%= true %>"
		small="<%= true %>"
	/>
</li>