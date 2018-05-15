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
PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="ddlRecordSet"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-util:include page="/display_style_buttons.jsp" servletContext="<%= application %>" />

		<c:if test="<%= DDLPermission.contains(permissionChecker, scopeGroupId, DDLActionKeys.ADD_RECORD_SET) %>">
			<portlet:renderURL var="addRecordSetURL">
				<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add") %>'
					url="<%= addRecordSetURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= ddlDisplayContext.getOrderByCol() %>"
			orderByType="<%= ddlDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "modified-date", "name"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
				<liferay-util:include page="/record_set_search.jsp" servletContext="<%= application %>" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteRecordSets();" %>'
			icon="trash"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteRecordSets() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			var searchContainer = AUI.$('#<portlet:namespace />ddlRecordSet', form);

			form.attr('method', 'post');
			form.fm('recordSetIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteRecordSet"><portlet:param name="mvcPath" value="/view.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}
</aui:script>