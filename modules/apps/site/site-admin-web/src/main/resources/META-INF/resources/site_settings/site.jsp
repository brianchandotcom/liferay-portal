<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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