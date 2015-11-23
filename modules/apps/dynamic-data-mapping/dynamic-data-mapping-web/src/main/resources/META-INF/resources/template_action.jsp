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

DDMTemplate template = (DDMTemplate)row.getObject();
%>

<liferay-ui:icon-menu direction="down" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">
	<c:if test="<%= DDMTemplatePermission.contains(permissionChecker, scopeGroupId, template, refererPortletName, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_template.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(template.getGroupId()) %>" />
			<portlet:param name="templateId" value="<%= String.valueOf(template.getTemplateId()) %>" />
			<portlet:param name="type" value="<%= template.getType() %>" />
			<portlet:param name="structureAvailableFields" value='<%= renderResponse.getNamespace() + "getAvailableFields" %>' />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMTemplatePermission.contains(permissionChecker, scopeGroupId, template, refererPortletName, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMTemplatePermission.getTemplateModelResourceName(template.getResourceClassNameId()) %>"
			modelResourceDescription="<%= template.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(template.getTemplateId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			message="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMTemplatePermission.containsAddTemplatePermission(permissionChecker, scopeGroupId, template.getClassNameId(), template.getResourceClassNameId()) %>">
		<portlet:renderURL var="copyURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/copy_template.jsp" />
			<portlet:param name="templateId" value="<%= String.valueOf(template.getTemplateId()) %>" />
			<portlet:param name="classNameId" value="<%= String.valueOf(template.getClassNameId()) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(template.getClassPK()) %>" />
			<portlet:param name="resourceClassNameId" value="<%= String.valueOf(template.getResourceClassNameId()) %>" />
		</portlet:renderURL>

		<%
		StringBundler sb = new StringBundler(6);

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append("copyTemplate");
		sb.append("('");
		sb.append(copyURL);
		sb.append("');");
		%>

		<liferay-ui:icon
			message="copy"
			url="<%= sb.toString() %>"
		/>
	</c:if>

	<c:if test="<%= DDMTemplatePermission.contains(permissionChecker, scopeGroupId, template, refererPortletName, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteTemplate" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="templateId" value="<%= String.valueOf(template.getTemplateId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>