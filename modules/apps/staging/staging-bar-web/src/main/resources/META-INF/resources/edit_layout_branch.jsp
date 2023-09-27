<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<liferay-ui:message arguments="<%= new Object[] {4, 75} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
	</c:if>

	<c:if test="<%= lbne.getType() == LayoutBranchNameException.TOO_SHORT %>">
		<liferay-ui:message arguments="<%= new Object[] {4, 75} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
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

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + ((layoutBranch != null) ? "updateBranch" : "addBranch") %>'
>
	<aui:model-context bean="<%= layoutBranch %>" model="<%= LayoutBranch.class %>" />

	<portlet:actionURL name="/staging_bar/edit_layout_branch" var="editLayoutBranchURL">
		<portlet:param name="mvcRenderCommandName" value="/staging_bar/edit_layout_branch" />
	</portlet:actionURL>

	<aui:form action="<%= editLayoutBranchURL %>" method="post" name="fm3">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= String.valueOf(scopeGroupId) %>" />
		<aui:input name="layoutBranchId" type="hidden" value="<%= layoutBranchId %>" />
		<aui:input name="copyLayoutRevisionId" type="hidden" value="<%= String.valueOf(layoutRevisionId) %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

		<aui:input ignoreRequestValue="<%= true %>" name="name" type="text" value="<%= (layoutBranch != null) ? HtmlUtil.escape(layoutBranchDisplayContext.getLayoutBranchDisplayName(layoutBranch)) : StringPool.BLANK %>" />

		<aui:input name="description" />

		<aui:button-row>
			<aui:button type="submit" value='<%= (layoutBranch != null) ? "update" : "add" %>' />

			<aui:button href="<%= redirect %>" value="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>