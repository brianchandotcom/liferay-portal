<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<liferay-ui:message arguments="<%= new Object[] {4, 75} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
	</c:if>

	<c:if test="<%= lsbne.getType() == LayoutSetBranchNameException.TOO_SHORT %>">
		<liferay-ui:message arguments="<%= new Object[] {4, 75} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
	</c:if>
</liferay-ui:error>

<%
String title = "add-site-pages-variation";

if (layoutSetBranch != null) {
	title = "update-site-pages-variation";
}
%>

<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>">
	<liferay-util:param name="navigationName" value="<%= title %>" />
</liferay-util:include>

<clay:container-fluid
	cssClass="container-view"
	id='<%= liferayPortletResponse.getNamespace() + ((layoutSetBranch != null) ? "updateBranch" : "addBranch") %>'
>
	<aui:model-context bean="<%= layoutSetBranch %>" model="<%= LayoutSetBranch.class %>" />

	<portlet:actionURL name="/staging_bar/edit_layout_set_branch" var="editLayoutSetBranchURL">
		<portlet:param name="mvcRenderCommandName" value="/staging_bar/edit_layout_set_branch" />
	</portlet:actionURL>

	<aui:form action="<%= editLayoutSetBranchURL %>" enctype="multipart/form-data" method="post" name="fm3">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= stagingGroup.getGroupId() %>" />
		<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
		<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutSetBranchId %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

		<aui:fieldset>
			<aui:input ignoreRequestValue="<%= true %>" name="name" type="text" value="<%= (layoutSetBranch != null) ? HtmlUtil.escape(layoutSetBranchDisplayContext.getLayoutSetBranchDisplayName(layoutSetBranch)) : StringPool.BLANK %>" />

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

						<aui:option label="<%= HtmlUtil.escape(layoutSetBranchDisplayContext.getLayoutSetBranchDisplayName(curLayoutSetBranch)) %>" localizeLabel="<%= false %>" value="<%= curLayoutSetBranch.getLayoutSetBranchId() %>" />

					<%
					}
					%>

				</aui:select>
			</c:if>
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" value='<%= (layoutSetBranch != null) ? "update" : "add" %>' />

			<aui:button href="<%= redirect %>" value="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>