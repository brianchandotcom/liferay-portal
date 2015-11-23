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
boolean includeCheckBox = ParamUtil.getBoolean(request, "includeCheckBox", true);
String searchContainerId = ParamUtil.getString(request, "searchContainerId");
%>

<liferay-util:include page="/template_search_bar.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar
	includeCheckBox="<%= includeCheckBox && !user.isDefaultUser() %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-filters>
		<liferay-util:include page="/template_sort_button.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-filters>

	<c:if test="<%= includeCheckBox %>">
		<liferay-frontend:management-bar-action-buttons>

			<%
			String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteTemplates();";
			%>

			<liferay-frontend:management-bar-button href="<%= taglibURL %>" iconCssClass="icon-trash" />
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteTemplates() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			var searchContainer = AUI.$('#<portlet:namespace />entriesContainer', form);

			form.attr('method', 'post');
			form.fm('deleteTemplateIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteTemplate"><portlet:param name="mvcPath" value="/view_template.jsp" /></portlet:actionURL>');
		}
	}
</aui:script>