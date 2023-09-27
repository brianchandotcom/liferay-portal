<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/aui/nav_bar_search/init.jsp" %>

<div class="navbar-header navbar-header-right" data-toggle="collapsible-search" id="<%= id %>NavbarSearchCollapse">
	<c:if test="<%= Validator.isNotNull(file) %>">
		<liferay-ui:search-form
			page="<%= file %>"
			searchContainer="<%= searchContainer %>"
		/>
	</c:if>