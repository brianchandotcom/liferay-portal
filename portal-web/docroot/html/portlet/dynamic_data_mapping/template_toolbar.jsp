<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String toolbarItem = ParamUtil.getString(request, "toolbarItem");

String redirect = ParamUtil.getString(request, "redirect");

long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");
%>

<aui:nav-bar>
	<aui:nav>

		<%
		String message = "add";
		%>

		<c:choose>
			<c:when test="<%= classNameId == PortalUtil.getClassNameId(DDMStructure.class) %>">
				<c:if test="<%= DDMPermission.contains(permissionChecker, scopeGroupId, ddmDisplay.getResourceName(), ddmDisplay.getAddTemplateActionId()) && (Validator.isNull(templateTypeValue) || templateTypeValue.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM)) %>">
					<portlet:renderURL var="addTemplateURL">
						<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
						<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
						<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
						<portlet:param name="structureAvailableFields" value='<%= renderResponse.getNamespace() + "getAvailableFields" %>' />
					</portlet:renderURL>

					<%
					if (Validator.isNull(templateTypeValue)) {
						message = "add-form-template";
					}
					%>

					<aui:nav-item href="<%= addTemplateURL %>" iconCssClass="icon-plus" label="<%= message %>" selected='<%= toolbarItem.equals("add-form-template") %>' />
				</c:if>

				<c:if test="<%= DDMPermission.contains(permissionChecker, scopeGroupId, ddmDisplay.getResourceName(), ddmDisplay.getAddTemplateActionId()) && (Validator.isNull(templateTypeValue) || templateTypeValue.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) %>">
					<portlet:renderURL var="addTemplateURL">
						<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
						<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
						<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
						<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
					</portlet:renderURL>

					<%
					if (Validator.isNull(templateTypeValue)) {
						message = "add-display-template";
					}
					%>

					<aui:nav-item href="<%= addTemplateURL %>" iconCssClass="icon-plus" label="<%= message %>" selected='<%= toolbarItem.equals("add-display-template") %>' />
				</c:if>
			</c:when>
			<c:otherwise>

				<%
				List<TemplateHandler> templateHandlers = new ArrayList<TemplateHandler>();

				if (classNameId > 0) {
					TemplateHandler templateHandler = TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

					if (DDMPermission.contains(permissionChecker, scopeGroupId, templateHandler.getResourceName(), ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE)) {
						templateHandlers.add(templateHandler);
					}
				}
				else {
					templateHandlers = PortletDisplayTemplateUtil.getPortletDisplayTemplateHandlers();

					Iterator<TemplateHandler> itr = templateHandlers.iterator();

					while (itr.hasNext()) {
						TemplateHandler templateHandler = itr.next();

						if (!DDMPermission.contains(permissionChecker, scopeGroupId, templateHandler.getResourceName(), ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE)) {
							itr.remove();
						}
					}
				}

				if (!templateHandlers.isEmpty()) {
					ListUtil.sort(templateHandlers, new TemplateHandlerComparator(locale));
				%>

					<aui:nav-item dropdown="<%= true %>" iconCssClass="icon-plus" label="add">
						<liferay-portlet:renderURL varImpl="addPortletDisplayTemplateURL">
							<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
							<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
						</liferay-portlet:renderURL>

						<%
						for (TemplateHandler templateHandler : templateHandlers) {
							addPortletDisplayTemplateURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(templateHandler.getClassName())));
							addPortletDisplayTemplateURL.setParameter("classPK", String.valueOf(0));
						%>

							<aui:nav-item
								href="<%= addPortletDisplayTemplateURL.toString() %>"
								iconCssClass="icon-list-alt"
								label="<%= templateHandler.getName(locale) %>"
							/>

						<%
						}
						%>

					</aui:nav-item>

					<%
					}
					%>

			</c:otherwise>
		</c:choose>
	</aui:nav>

	<aui:nav-bar-search cssClass="pull-right" file="/html/portlet/dynamic_data_mapping/template_search.jsp" />
</aui:nav-bar>