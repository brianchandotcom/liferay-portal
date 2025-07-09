<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/osb_patcher/views/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long patcherFixComponentId = ParamUtil.getLong(request, "patcherFixComponentId");

PatcherFixComponent patcherFixComponent = PatcherFixComponentLocalServiceUtil.fetchPatcherFixComponent(patcherFixComponentId);
%>

<liferay-util:include page="/osb_patcher/views/header.jsp" servletContext="<%= application %>">
	<liferay-util:param name="title" value="<%= patcherFixComponent.getName() %>" />
</liferay-util:include>

<aui:model-context bean="<%= patcherFixComponent %>" model="<%= PatcherFixComponent.class %>" />

<portlet:actionURL name="/patcher/update_fix_components" var="updatePatcherFixComponentURL" />

<aui:form action="<%= updatePatcherFixComponentURL %>" method="post">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="patcherFixComponentId" type="hidden" value="<%= patcherFixComponent.getPatcherFixComponentId() %>" />

	<aui:input name="name" type="text" value="<%= patcherFixComponent.getName() %>" />

	<aui:button-row>
		<aui:button type="submit" value="update" />

		<aui:button href="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>