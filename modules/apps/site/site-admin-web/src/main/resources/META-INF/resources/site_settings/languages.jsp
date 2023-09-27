<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DisplaySettingsDisplayContext displaySettingsDisplayContext = new DisplaySettingsDisplayContext(request, liferayPortletResponse);
%>

<div id="<portlet:namespace />siteLanguageConfiguration">
	<div class="inline-item my-5 p-5 w-100">
		<span aria-hidden="true" class="loading-animation"></span>
	</div>

	<react:component
		module="js/SiteLanguageConfiguration"
		props="<%= displaySettingsDisplayContext.getPropsMap() %>"
	/>
</div>