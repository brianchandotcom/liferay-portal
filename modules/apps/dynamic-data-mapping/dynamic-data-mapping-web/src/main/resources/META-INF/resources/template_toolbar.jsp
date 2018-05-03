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
String mvcPath = ParamUtil.getString(request, "mvcPath", "/view_template.jsp");

String tabs1 = ParamUtil.getString(request, "tabs1", "templates");

long templateId = ParamUtil.getLong(request, "templateId");

long groupId = ParamUtil.getLong(request, "groupId", PortalUtil.getScopeGroupId(request, refererPortletName, true));
long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");

long resourceClassNameId = ParamUtil.getLong(request, "resourceClassNameId");

if (resourceClassNameId == 0) {
	resourceClassNameId = PortalUtil.getClassNameId(PortletDisplayTemplate.class);
}

String mode = ParamUtil.getString(request, "mode", DDMTemplateConstants.TEMPLATE_MODE_CREATE);

String eventName = ParamUtil.getString(request, "eventName", "selectTemplate");
boolean includeCheckBox = ParamUtil.getBoolean(request, "includeCheckBox", true);
String keywords = ParamUtil.getString(request, "keywords");
String orderByCol = ParamUtil.getString(request, "orderByCol", "modified-date");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");
String searchContainerId = ParamUtil.getString(request, "searchContainerId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", mvcPath);
portletURL.setParameter("templateId", String.valueOf(templateId));
portletURL.setParameter("classNameId", String.valueOf(classNameId));
portletURL.setParameter("classPK", String.valueOf(classPK));
portletURL.setParameter("resourceClassNameId", String.valueOf(resourceClassNameId));
portletURL.setParameter("eventName", eventName);
portletURL.setParameter("keywords", keywords);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= includeCheckBox && !user.isDefaultUser() %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"modified-date", "id"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<portlet:renderURL var="searchURL">
				<portlet:param name="mvcPath" value="<%= mvcPath %>" />
				<portlet:param name="tabs1" value="<%= tabs1 %>" />
				<portlet:param name="templateId" value="<%= String.valueOf(templateId) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
				<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
				<portlet:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
				<portlet:param name="eventName" value="<%= eventName %>" />
			</portlet:renderURL>

			<aui:form action="<%= searchURL.toString() %>" method="post" name="searchForm">
				<liferay-util:include page="/template_search.jsp" servletContext="<%= application %>" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<c:if test="<%= includeCheckBox %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteTemplates();" %>'
				icon="trash"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</c:if>

	<liferay-frontend:management-bar-buttons>
		<liferay-util:include page="/template_add_buttons.jsp" servletContext="<%= application %>">
			<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			<liferay-util:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
			<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
			<liferay-util:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
			<liferay-util:param name="mode" value="<%= mode %>" />
		</liferay-util:include>
	</liferay-frontend:management-bar-buttons>
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