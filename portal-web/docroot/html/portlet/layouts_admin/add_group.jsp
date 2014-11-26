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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
long parentGroupId = ParamUtil.getLong(request, "parentGroupId");

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();
%>

<portlet:renderURL var="backURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= backURL %>"
	title="add-new-group"
/>

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />

<portlet:actionURL var="addPageSetActionURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_layouts" />
</portlet:actionURL>

<aui:form action="<%= addPageSetActionURL %>" method="post">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="add_group" />
	<aui:input name="redirect" type="hidden" value="<%= redirectURL.toString() %>" />
	<aui:input name="parentGroupId" type="hidden" value="<%= parentGroupId %>" />

	<aui:input name="name" required="<%= true %>" type="text" />

	<aui:input name="description" type="textarea" />

	<aui:input name="friendlyURL" type="text" />

	<aui:button-row cssClass="lfr-add-page-toolbar">
		<aui:button type="submit" value="add" />

		<aui:button name="cancelAddOperation" value="cancel" />
	</aui:button-row>
</aui:form>