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