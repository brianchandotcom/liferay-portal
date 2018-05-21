<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
PortletURL portletURL = kaleoFormsAdminDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="kaleoProcess"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews="<%= kaleoFormsAdminDisplayContext.getDisplayViews() %>"
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= kaleoFormsAdminDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= kaleoFormsAdminDisplayContext.getOrderByCol() %>"
			orderByType="<%= kaleoFormsAdminDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "modified-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteKaleoProcess();" %>'
			icon="trash"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteKaleoProcess() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			var searchContainer = AUI.$('#<portlet:namespace />kaleoProcess', form);

			form.attr('method', 'post');
			form.fm('kaleoProcessIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteKaleoProcess"><portlet:param name="mvcPath" value="/admin/view.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}
</aui:script>