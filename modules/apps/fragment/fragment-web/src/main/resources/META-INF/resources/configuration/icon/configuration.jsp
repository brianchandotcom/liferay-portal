<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())));

renderResponse.setTitle(LanguageUtil.get(request, "fragment-configuration"));

ConfigurationDisplayContext configurationDisplayContext = new ConfigurationDisplayContext(request, liferayPortletResponse);
%>

<div>
	<span aria-hidden="true" class="loading-animation"></span>

	<react:component
		data="<%= configurationDisplayContext.getData() %>"
		module="js/FormFragmentsConfiguration"
	/>
</div>