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
String redirect = ParamUtil.getString(request, "redirect");

LayoutBranch layoutBranch = null;

long layoutBranchId = ParamUtil.getLong(request, "layoutBranchId");

if (layoutBranchId > 0) {
	layoutBranch = LayoutBranchLocalServiceUtil.getLayoutBranch(layoutBranchId);
}

long layoutRevisionId = ParamUtil.getLong(request, "layoutRevisionId");

if (layoutRevisionId <= 0) {
	layoutRevisionId = ParamUtil.getLong(request, "copyLayoutRevisionId");
}
%>

<liferay-ui:error exception="<%= LayoutBranchNameException.class %>">

	<%
	LayoutBranchNameException lbne = (LayoutBranchNameException)errorException;
	%>

	<c:if test="<%= lbne.getType() == LayoutBranchNameException.DUPLICATE %>">
		<liferay-ui:message key="a-page-variation-with-that-name-already-exists" />
	</c:if>

	<c:if test="<%= lbne.getType() == LayoutBranchNameException.TOO_LONG %>">
		<liferay-ui:message arguments="<%= new Object[] {4, 100} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
	</c:if>

	<c:if test="<%= lbne.getType() == LayoutBranchNameException.TOO_SHORT %>">
		<liferay-ui:message arguments="<%= new Object[] {4, 100} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
	</c:if>
</liferay-ui:error>

<%
String title = "add-page-variation";

if (layoutBranch != null) {
	title = "update-page-variation";
}
%>

<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>">
	<liferay-util:param name="navigationName" value="<%= title %>" />
</liferay-util:include>

<div class="container-fluid-1280" data-namespace="<portlet:namespace />" id="<portlet:namespace /><%= (layoutBranch != null) ? "updateBranch" : "addBranch" %>">
	<aui:model-context bean="<%= layoutBranch %>" model="<%= LayoutBranch.class %>" />

	<portlet:actionURL name="editLayoutBranch" var="editLayoutBranchURL">
		<portlet:param name="mvcRenderCommandName" value="editLayoutBranch" />
	</portlet:actionURL>

	<aui:form action="<%= editLayoutBranchURL %>" method="post" name="fm3">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= String.valueOf(scopeGroupId) %>" />
		<aui:input name="layoutBranchId" type="hidden" value="<%= layoutBranchId %>" />
		<aui:input name="copyLayoutRevisionId" type="hidden" value="<%= String.valueOf(layoutRevisionId) %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

		<aui:input name="name" />

		<aui:input name="description" />

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value='<%= (layoutBranch != null) ? "update" : "add" %>' />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>