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

List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(groupId, privateLayout);

long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

if (layoutSetBranches.contains(layoutSetBranch)) {
	layoutSetBranches = ListUtil.copy(layoutSetBranches);

	layoutSetBranches.remove(layoutSetBranch);
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= true %>"
	showBackURL="<%= true %>"
	title="merge-site-pages-variation"
/>

<div id="<portlet:namespace />mergeLayoutSetBranch">
	<portlet:actionURL name="mergeLayoutSetBranch" var="mergeLayoutSetBranchURL">
		<portlet:param name="mvcRenderCommandName" value="viewLayoutSetBranches" />
	</portlet:actionURL>

	<aui:form action="<%= mergeLayoutSetBranchURL %>" enctype="multipart/form-data" method="post" name="fm4">
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

					<%
					long curLayoutSetBranchId = curLayoutSetBranch.getLayoutSetBranchId();
					%>

					<a class="layout-set-branch" data-layoutSetBranchId="<%= curLayoutSetBranchId %>" data-layoutSetBranchMessage="<%= HtmlUtil.escapeAttribute(LanguageUtil.format(request, "are-you-sure-you-want-to-merge-changes-from-x", curLayoutSetBranch.getName(), false)) %>" data-layoutSetBranchName="<%= HtmlUtil.escapeAttribute(curLayoutSetBranch.getName()) %>" href="#" id="<portlet:namespace /><%= curLayoutSetBranchId %>" onClick="<portlet:namespace />selectLayoutSetBranch('<%= curLayoutSetBranchId %>');">
						<liferay-ui:message key="select" />
					</a>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" searchContainer="<%= searchContainer %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script >
	function <portlet:namespace />selectLayoutSetBranch(layoutSetBranchId) {
		var layoutSetBranch = AUI.$('#<portlet:namespace />' + layoutSetBranchId);

		var mergeLayoutSetBranchId = layoutSetBranch.attr('data-layoutSetBranchId');
		var mergeLayoutSetBranchName = layoutSetBranch.attr('data-layoutSetBranchName');
		var mergeLayoutSetBranchMessage = layoutSetBranch.attr('data-layoutSetBranchMessage');

		if (confirm(mergeLayoutSetBranchMessage)) {
			var form = document.<portlet:namespace />fm4;

			alert(form.<portlet:namespace />mergeLayoutSetBranchId.value);

			form.<portlet:namespace />mergeLayoutSetBranchId.value = mergeLayoutSetBranchId;

			alert(form.<portlet:namespace />mergeLayoutSetBranchId.value);

			submitForm(form);
		}
	}
</aui:script>