<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewOnPageDisplayContext viewOnPageDisplayContext = (ViewOnPageDisplayContext)request.getAttribute(ViewOnPageDisplayContext.class.getName());
%>

<div class="seo-studio-on-page">
	<react:component
		module="{OnPage} from seo-studio-web"
		props="<%= viewOnPageDisplayContext.getReactData() %>"
	/>
</div>