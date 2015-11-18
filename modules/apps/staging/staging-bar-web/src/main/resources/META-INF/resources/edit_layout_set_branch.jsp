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

LayoutSetBranch layoutSetBranch = null;

long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

if (layoutSetBranchId > 0) {
	layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);
}
%>

<liferay-ui:error exception="<%= LayoutSetBranchNameException.class %>">

	<%
	LayoutSetBranchNameException lsbne = (LayoutSetBranchNameException)errorException;
	%>

	<c:if test="<%= lsbne.getType() == LayoutSetBranchNameException.DUPLICATE %>">
		<liferay-ui:message key="a-site-pages-variation-with-that-name-already-exists" />
	</c:if>

	<c:if test="<%= lsbne.getType() == LayoutSetBranchNameException.MASTER %>">
		<liferay-ui:message key="only-one-site-pages-variation-can-be-the-main-one" />
	</c:if>

	<c:if test="<%= lsbne.getType() == LayoutSetBranchNameException.TOO_LONG %>">
		<liferay-ui:message arguments="<%= new Object[] {4, 100} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
	</c:if>

	<c:if test="<%= lsbne.getType() == LayoutSetBranchNameException.TOO_SHORT %>">
		<liferay-ui:message arguments="<%= new Object[] {4, 100} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
	</c:if>
</liferay-ui:error>

<%
String title = "add-site-pages-variation";

if (layoutSetBranch != null) {
	title = "update-site-pages-variation";
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= true %>"
	showBackURL="<%= true %>"
	title="<%= title %>"
/>

<div data-namespace="<portlet:namespace />" id="<portlet:namespace /><%= layoutSetBranch != null ? "updateBranch" : "addBranch" %>">
	<aui:model-context bean="<%= layoutSetBranch %>" model="<%= LayoutSetBranch.class %>" />

	<portlet:actionURL name="editLayoutSetBranch" var="editLayoutSetBranchURL">
		<portlet:param name="mvcRenderCommandName" value="editLayoutSetBranch" />
	</portlet:actionURL>

	<aui:form action="<%= editLayoutSetBranchURL %>" enctype="multipart/form-data" method="post" name="fm3">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= stagingGroup.getGroupId() %>" />
		<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
		<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutSetBranchId %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

		<aui:fieldset>
			<aui:input name="name" />

			<aui:input name="description" />

			<c:if test="<%= layoutSetBranch == null %>">

				<%
				List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), privateLayout);
				%>

				<aui:select helpMessage="copy-pages-from-site-pages-variation-help" label="copy-pages-from-site-pages-variation" name="copyLayoutSetBranchId">
					<aui:option label="all-site-pages-variations" selected="<%= true %>" value="<%= LayoutSetBranchConstants.ALL_BRANCHES %>" />
					<aui:option label="none-empty-site-pages-variation" value="<%= LayoutSetBranchConstants.NO_BRANCHES %>" />

					<%
					for (LayoutSetBranch curLayoutSetBranch : layoutSetBranches) {
					%>

						<aui:option label="<%= HtmlUtil.escape(curLayoutSetBranch.getName()) %>" value="<%= curLayoutSetBranch.getLayoutSetBranchId() %>" />

					<%
					}
					%>

				</aui:select>
			</c:if>
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" value='<%= (layoutSetBranch != null) ? "update" : "add" %>' />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>