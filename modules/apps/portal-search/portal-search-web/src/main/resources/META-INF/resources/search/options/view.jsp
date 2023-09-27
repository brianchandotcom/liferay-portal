<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<liferay-theme:defineObjects />

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>

<div class="alert alert-info text-center">
	<clay:link
		href="javascript:void(0);"
		label='<%= LanguageUtil.get(request, "search-options-help") %>'
		onClick="<%= portletDisplay.getURLConfigurationJS() %>"
	/>
</div>