<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");

long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewTemplatesURL">
		<portlet:param name="struts_action" value="/dynamic_data_mapping/view_template" />
		<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
		<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewTemplatesURL %>"><liferay-ui:message key="view-all" /></a>
	</span>

	<%
	String message = "add";
	%>

	<c:choose>
		<c:when test="<%= classNameId == PortalUtil.getClassNameId(DDMStructure.class) %>">
			<c:if test="<%= DDMPermission.contains(permissionChecker, scopeGroupId, ddmResource, ActionKeys.ADD_TEMPLATE) && (Validator.isNull(templateTypeValue) || templateTypeValue.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM)) %>">
				<portlet:renderURL var="addTemplateURL">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= viewTemplatesURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
					<portlet:param name="structureAvailableFields" value='<%= renderResponse.getNamespace() + "structureAvailableFields" %>' />
				</portlet:renderURL>

				<%
				if (Validator.isNull(templateTypeValue)) {
					message = "add-form-template";
				}
				%>

				<span class="lfr-toolbar-button add-template <%= toolbarItem.equals("add-form-template") ? "current" : StringPool.BLANK %>">
					<a href="<%= addTemplateURL %>"><liferay-ui:message key="<%= message %>" /></a>
				</span>
			</c:if>

			<c:if test="<%= DDMPermission.contains(permissionChecker, scopeGroupId, ddmResource, ActionKeys.ADD_TEMPLATE) && (Validator.isNull(templateTypeValue) || templateTypeValue.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) %>">
				<portlet:renderURL var="addTemplateURL">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= viewTemplatesURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
					<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
				</portlet:renderURL>

				<%
				if (Validator.isNull(templateTypeValue)) {
					message = "add-display-template";
				}
				%>

				<span class="lfr-toolbar-button add-template <%= toolbarItem.equals("add-display-template") ? "current" : StringPool.BLANK %>">
					<a href="<%= addTemplateURL %>"><liferay-ui:message key="<%= message %>" /></a>
				</span>
			</c:if>
		</c:when>
		<c:otherwise>

			<%
			List<PortletDisplayTemplateHandler> portletDisplayTemplateHandlers = new ArrayList<PortletDisplayTemplateHandler>();

			if (classNameId > 0) {
				portletDisplayTemplateHandlers.add(PortletDisplayTemplateHandlerRegistryUtil.getPortletDisplayTemplateHandler(classNameId));
			}
			else {
				portletDisplayTemplateHandlers.addAll(getPortletDisplayTemplateHandlers(permissionChecker, scopeGroupId));
			}

			if (!portletDisplayTemplateHandlers.isEmpty()) {
			%>

				<liferay-ui:icon-menu align="left" cssClass='<%= "lfr-toolbar-button add-button " + (toolbarItem.equals("add") ? "current" : StringPool.BLANK) %>' direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>' message="add" showWhenSingleIcon="<%= true %>">

					<liferay-portlet:renderURL varImpl="addPortletDisplayTemplateURL">
						<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
						<portlet:param name="redirect" value="<%= viewTemplatesURL %>" />
						<portlet:param name="backURL" value="<%= viewTemplatesURL %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
						<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
					</liferay-portlet:renderURL>

					<%
					for (PortletDisplayTemplateHandler portletDisplayTemplateHandler : portletDisplayTemplateHandlers) {
						addPortletDisplayTemplateURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(portletDisplayTemplateHandler.getClassName())));
						addPortletDisplayTemplateURL.setParameter("classPK", String.valueOf(0));
						addPortletDisplayTemplateURL.setParameter("ddmResource", portletDisplayTemplateHandler.getResourceName());
					%>

						<liferay-ui:icon
							image="add_portlet_display_template"
							message="<%= portletDisplayTemplateHandler.getName(locale) %>"
							method="get"
							url="<%= addPortletDisplayTemplateURL.toString() %>"
						/>

					<%
					}
					%>

				</liferay-ui:icon-menu>

				<%
				}
				%>

		</c:otherwise>
	</c:choose>
</div>

<%!
public List<PortletDisplayTemplateHandler> getPortletDisplayTemplateHandlers(PermissionChecker permissionChecker, long scopeGroupId) {
	List<PortletDisplayTemplateHandler> portletDisplayTemplateHandlers = PortletDisplayTemplateHandlerRegistryUtil.getPortletDisplayTemplateHandlers();

	List<PortletDisplayTemplateHandler> allowedPortletDisplayTemplateHandlers = new ArrayList<PortletDisplayTemplateHandler>();

	for (PortletDisplayTemplateHandler portletDisplayTemplateHandler : portletDisplayTemplateHandlers) {
		if (DDMPermission.contains(permissionChecker, scopeGroupId, portletDisplayTemplateHandler.getResourceName(), ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE)) {
			allowedPortletDisplayTemplateHandlers.add(portletDisplayTemplateHandler);
		}
	}

	return allowedPortletDisplayTemplateHandlers;
}
%>