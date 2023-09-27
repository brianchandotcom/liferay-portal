<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout/edit_layout/init.jsp" %>

<liferay-ui:success key="layoutAdded" message="the-page-was-created-successfully" />

<liferay-ui:success key="layoutPageTemplateAdded" message="the-page-template-was-created-successfully" />

<liferay-ui:success key="layoutPublished" message="the-page-was-published-successfully" />

<liferay-ui:success key="layoutUtilityPageEntryAdded" message="the-utility-page-was-created-successfully" />

<%
String portletResource = ParamUtil.getString(request, "portletResource");
%>

<c:if test="<%= Validator.isNotNull(portletResource) %>">
	<liferay-ui:success key='<%= portletResource + "requestProcessed" %>' message="your-request-completed-successfully" />
</c:if>

<div class="layout-content portlet-layout" id="main-content" role="main">
	<liferay-portlet:runtime
		portletName="<%= ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET %>"
	/>
</div>

<liferay-layout:layout-common />