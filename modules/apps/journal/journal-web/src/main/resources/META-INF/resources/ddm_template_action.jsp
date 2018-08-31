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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMTemplate ddmTemplate = (DDMTemplate)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= DDMTemplatePermission.contains(permissionChecker, ddmTemplate, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_ddm_template.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="ddmTemplateId" value="<%= String.valueOf(ddmTemplate.getTemplateId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMTemplatePermission.contains(permissionChecker, ddmTemplate, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMTemplatePermission.getTemplateModelResourceName(ddmTemplate.getResourceClassNameId()) %>"
			modelResourceDescription="<%= ddmTemplate.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(ddmTemplate.getTemplateId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<%
	Group scopeGroup = themeDisplay.getScopeGroup();
	%>

	<c:if test="<%= (!scopeGroup.hasLocalOrRemoteStagingGroup() || scopeGroup.isStagingGroup()) && DDMTemplatePermission.containsAddTemplatePermission(permissionChecker, scopeGroupId, ddmTemplate.getClassNameId(), ddmTemplate.getResourceClassNameId()) %>">
		<portlet:renderURL var="copyURL">
			<portlet:param name="mvcPath" value="/copy_ddm_template.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="ddmTemplateId" value="<%= String.valueOf(ddmTemplate.getTemplateId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="copy"
			url="<%= copyURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMTemplatePermission.contains(permissionChecker, ddmTemplate, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/journal/delete_ddm_template" var="deleteURL">
			<portlet:param name="mvcPath" value="/view_ddm_templates.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="ddmTemplateId" value="<%= String.valueOf(ddmTemplate.getTemplateId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>