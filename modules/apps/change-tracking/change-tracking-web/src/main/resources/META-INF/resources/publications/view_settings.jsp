<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<liferay-portlet:renderURL var="backURL" />

<%
PublicationsConfigurationDisplayContext publicationsConfigurationDisplayContext = (PublicationsConfigurationDisplayContext)request.getAttribute(CTWebKeys.PUBLICATIONS_CONFIGURATION_DISPLAY_CONTEXT);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));

if (publicationsConfigurationDisplayContext.isPublicationsEnabled()) {
	portletDisplay.setURLBack(backURL);
	portletDisplay.setShowBackIcon(true);
}
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<aui:form action="<%= publicationsConfigurationDisplayContext.getActionURL() %>" method="post" name="fm">
		<aui:input name="navigation" type="hidden" value="<%= publicationsConfigurationDisplayContext.getNavigation() %>" />

		<clay:sheet>
			<%@ include file="/publications/global_settings.jspf" %>
		</clay:sheet>
	</aui:form>
</clay:container-fluid>