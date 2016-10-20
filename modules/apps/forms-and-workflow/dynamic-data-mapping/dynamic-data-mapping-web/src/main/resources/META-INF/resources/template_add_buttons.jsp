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
SearchContainer searchContainer = (SearchContainer)request.getAttribute(WebKeys.SEARCH_CONTAINER);

String toolbarItem = ParamUtil.getString(request, "toolbarItem");

long groupId = ParamUtil.getLong(request, "groupId", PortalUtil.getScopeGroupId(request, refererPortletName));
long classNameId = ParamUtil.getLong(request, "classNameId");
long classPK = ParamUtil.getLong(request, "classPK");
long resourceClassNameId = ParamUtil.getLong(request, "resourceClassNameId");

String message = "add";
%>

<liferay-frontend:add-menu>
	<c:choose>
		<c:when test="<%= classNameId == PortalUtil.getClassNameId(DDMStructure.class) %>">
			<c:if test="<%= DDMTemplatePermission.containsAddTemplatePermission(permissionChecker, groupId, classNameId, scopeClassNameId) && (Validator.isNull(templateTypeValue) || templateTypeValue.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM)) %>">

				<%
				if (Validator.isNull(templateTypeValue)) {
					message = "add-form-template";
				}
				%>

				<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="addTemplateURL">
					<portlet:param name="mvcPath" value="/edit_template.jsp" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
					<portlet:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
					<portlet:param name="structureAvailableFields" value='<%= renderResponse.getNamespace() + "getAvailableFields" %>' />
				</portlet:renderURL>

				<liferay-frontend:add-menu-item
					title="<%= LanguageUtil.get(request, message) %>"
					url="<%= addTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= DDMTemplatePermission.containsAddTemplatePermission(permissionChecker, groupId, classNameId, scopeClassNameId) && (Validator.isNull(templateTypeValue) || templateTypeValue.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) %>">

				<%
				if (Validator.isNull(templateTypeValue)) {
					message = "add-display-template";
				}
				%>

				<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="addTemplateURL">
					<portlet:param name="mvcPath" value="/edit_template.jsp" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
					<portlet:param name="resourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
					<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
				</portlet:renderURL>

				<liferay-frontend:add-menu-item
					title="<%= LanguageUtil.get(request, message) %>"
					url="<%= addTemplateURL %>"
				/>
			</c:if>
		</c:when>
		<c:otherwise>

			<%
			List<TemplateHandler> templateHandlers = new ArrayList<TemplateHandler>();

			if (classNameId > 0) {
				TemplateHandler templateHandler = TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

				if (PortletPermissionUtil.contains(permissionChecker, layout, templateHandler.getResourceName(), ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE)) {
					templateHandlers.add(templateHandler);
				}
			}
			else {
				templateHandlers = PortletDisplayTemplateUtil.getPortletDisplayTemplateHandlers();

				Iterator<TemplateHandler> itr = templateHandlers.iterator();

				while (itr.hasNext()) {
					TemplateHandler templateHandler = itr.next();

					if (!PortletPermissionUtil.contains(permissionChecker, layout, templateHandler.getResourceName(), ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE)) {
						itr.remove();
					}
				}
			}

			if (!templateHandlers.isEmpty()) {
				ListUtil.sort(templateHandlers, new TemplateHandlerComparator(locale));
			%>

				<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="addPortletDisplayTemplateURL">
					<portlet:param name="mvcPath" value="/edit_template.jsp" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
				</liferay-portlet:renderURL>

				<%
				for (TemplateHandler templateHandler : templateHandlers) {
					addPortletDisplayTemplateURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(templateHandler.getClassName())));
					addPortletDisplayTemplateURL.setParameter("classPK", String.valueOf(0));
					addPortletDisplayTemplateURL.setParameter("resourceClassNameId", String.valueOf(resourceClassNameId));
				%>

					<liferay-frontend:add-menu-item
						title="<%= templateHandler.getName(locale) %>"
						url="<%= addPortletDisplayTemplateURL.toString() %>"
					/>

			<%
				}
			}
			%>

		</c:otherwise>
	</c:choose>
</liferay-frontend:add-menu>