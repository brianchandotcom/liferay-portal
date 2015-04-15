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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(groupId);

long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

if (layoutSetBranches.contains(layoutSetBranch)) {
	layoutSetBranches = ListUtil.copy(layoutSetBranches);

	layoutSetBranches.remove(layoutSetBranch);
}
%>

<div id="<portlet:namespace />mergeLayoutSetBranch">
	<portlet:actionURL var="mergeLayoutSetBranchURL">
		<portlet:param name="struts_action" value="/staging_bar/edit_layout_set_branch" />
	</portlet:actionURL>

	<aui:form action="<%= mergeLayoutSetBranchURL %>" enctype="multipart/form-data" method="post" name="fm4">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="merge_layout_set_branch" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutSetBranchId %>" />
		<aui:input name="mergeLayoutSetBranchId" type="hidden" />

		<liferay-ui:search-container id="layoutSetBranchesSearchContainer">
			<liferay-ui:search-container-results
				results="<%= layoutSetBranches %>"
				total="<%= layoutSetBranches.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.LayoutSetBranch"
				escapedModel="<%= true %>"
				keyProperty="layoutSetBranchId"
				modelVar="curLayoutSetBranch"
			>

				<liferay-ui:search-container-column-text
					name="branch"
					value="<%= LanguageUtil.get(request, curLayoutSetBranch.getName()) %>"
				/>

				<liferay-ui:search-container-column-text>
					<a class="layout-set-branch" data-layoutSetBranchId="<%= curLayoutSetBranch.getLayoutSetBranchId() %>" data-layoutSetBranchMessage="<%= HtmlUtil.escapeAttribute(LanguageUtil.format(request, "are-you-sure-you-want-to-merge-changes-from-x", curLayoutSetBranch.getName(), false)) %>" data-layoutSetBranchName="<%= HtmlUtil.escapeAttribute(curLayoutSetBranch.getName()) %>" href="#">
						<liferay-ui:message key="select" />
					</a>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>