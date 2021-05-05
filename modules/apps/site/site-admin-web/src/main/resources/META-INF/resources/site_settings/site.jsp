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

<%@ include file="/init.jsp" %>

<%
String viewOrganizationsRedirect = ParamUtil.getString(request, "viewOrganizationsRedirect");

String redirect = ParamUtil.getString(request, "redirect", viewOrganizationsRedirect);

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

String backURL = ParamUtil.getString(request, "backURL", redirect);

Group group = (Group)request.getAttribute("site.group");
%>

<liferay-ui:success key='<%= ConfigurationAdminPortletKeys.SITE_SETTINGS + "requestProcessed" %>' message="site-was-added" />

<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
<aui:input name="groupId" type="hidden" value="<%= group.getGroupId() %>" />
<aui:input name="liveGroupId" type="hidden" value='<%= (long)request.getAttribute("site.liveGroupId") %>' />
<aui:input name="stagingGroupId" type="hidden" value='<%= (long)request.getAttribute("site.stagingGroupId") %>' />

<liferay-frontend:form-navigator
	backURL="<%= backURL %>"
	formModelBean="<%= group %>"
	id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES %>"
	showButtons="<%= false %>"
/>