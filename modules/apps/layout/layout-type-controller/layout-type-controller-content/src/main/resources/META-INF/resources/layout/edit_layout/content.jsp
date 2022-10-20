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