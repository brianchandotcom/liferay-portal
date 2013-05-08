<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

LayoutBranch layoutBranch = null;

long layoutBranchId = ParamUtil.getLong(request, "layoutBranchId");

if (layoutBranchId > 0) {
	layoutBranch = LayoutBranchLocalServiceUtil.getLayoutBranch(layoutBranchId);
}

long layoutRevisionId = ParamUtil.getLong(request, "layoutRevisionId");
%>

<div class='<%= (layoutBranch != null) ? StringPool.BLANK : "hide" %>' data-namespace="<portlet:namespace />" id="<portlet:namespace /><%= layoutBranch != null ? "updateBranch" : "addBranch" %>">
	<aui:model-context bean="<%= layoutBranch %>" model="<%= LayoutBranch.class %>" />

	<portlet:actionURL var="editLayoutBranchURL">
		<portlet:param name="struts_action" value="/staging_bar/edit_layout_branch" />
	</portlet:actionURL>

	<aui:form action="<%= editLayoutBranchURL %>" method="post" name="fm3">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= layoutBranch != null ? Constants.UPDATE : Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden"  value="<%= String.valueOf(scopeGroupId) %>" />
		<aui:input name="layoutBranchId" type="hidden" value="<%= layoutBranchId %>" />
		<aui:input name="copyLayoutRevisionId" type="hidden" value="<%= String.valueOf(layoutRevisionId) %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

		<aui:input name="name" />

		<aui:input name="description" />

		<aui:button-row>
			<aui:button type="submit" value='<%= (layoutBranch != null) ? "update" : "add" %>' />
		</aui:button-row>
	</aui:form>
</div>