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
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet portlet = null;
String portletResourceLabel = null;

if (Validator.isNotNull(portletResource)) {
	portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	String portletId = portlet.getPortletId();

	if (portletId.equals(PortletKeys.PORTAL)) {
		portletResourceLabel = LanguageUtil.get(request, "general-permissions");
	}
	else {
		portletResourceLabel = PortalUtil.getPortletLongTitle(portlet, application, locale);
	}
}

List<String> modelResources = null;

if (Validator.isNotNull(portletResource)) {
	modelResources = ResourceActionsUtil.getPortletModelResources(portletResource);
}
%>

<portlet:actionURL name="updateActions" var="editRolePermissionsURL">
	<portlet:param name="mvcPath" value="/edit_role_permissions_form.jsp" />
</portlet:actionURL>

<aui:form action="<%= editRolePermissionsURL %>" method="post" name="fm">
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="redirect" type="hidden" />
	<aui:input name="roleId" type="hidden" value="<%= role.getRoleId() %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="modelResources" type="hidden" value='<%= (modelResources == null) ? "" : StringUtil.merge(modelResources) %>' />
	<aui:input name="accountRoleGroupScope" type="hidden" value="<%= roleDisplayContext.isAccountRoleGroupScope() %>" />
	<aui:input name="selectedTargets" type="hidden" />
	<aui:input name="unselectedTargets" type="hidden" />

	<clay:sheet>
		<clay:sheet-header>
			<h3 class="sheet-title"><%= HtmlUtil.escape(portletResourceLabel) %></h3>
		</clay:sheet-header>

		<%
		request.setAttribute("edit_role_permissions.jsp-curPortletResource", portletResource);

		String applicationPermissionsLabel = "application-permissions";

		PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);

		if (portletResource.equals(PortletKeys.PORTAL)) {
			applicationPermissionsLabel = StringPool.BLANK;
		}
		else if ((portlet != null) && panelCategoryHelper.containsPortlet(portlet.getPortletId(), PanelCategoryKeys.ROOT)) {
			applicationPermissionsLabel = "general-permissions";
		}
		%>

		<clay:sheet-section>
			<c:if test="<%= Validator.isNotNull(applicationPermissionsLabel) %>">
				<h4 class="sheet-subtitle"><liferay-ui:message key="<%= applicationPermissionsLabel %>" /> <liferay-ui:icon-help message='<%= applicationPermissionsLabel + "-help" %>' /></h4>
			</c:if>

			<liferay-util:include page="/edit_role_permissions_resource.jsp" servletContext="<%= application %>" />
		</clay:sheet-section>

		<c:if test="<%= (modelResources != null) && !modelResources.isEmpty() %>">
			<clay:sheet-section>
				<h4 class="sheet-subtitle"><liferay-ui:message key="resource-permissions" /> <liferay-ui:icon-help message="resource-permissions-help" /></h4>

				<div class="permission-group">

					<%
					modelResources = ListUtil.sort(modelResources, new ModelResourceWeightComparator());

					for (int i = 0; i < modelResources.size(); i++) {
						String curModelResource = modelResources.get(i);

						String curModelResourceName = ResourceActionsUtil.getModelResource(request, curModelResource);
					%>

						<h5 class="sheet-tertiary-title" id="<%= roleDisplayContext.getResourceHtmlId(curModelResource) %>"><%= curModelResourceName %></h5>

						<%
						request.setAttribute("edit_role_permissions.jsp-curModelResource", curModelResource);
						request.setAttribute("edit_role_permissions.jsp-curModelResourceName", curModelResourceName);
						%>

						<liferay-util:include page="/edit_role_permissions_resource.jsp" servletContext="<%= application %>" />

					<%
					}
					%>

				</div>
			</clay:sheet-section>
		</c:if>

		<c:if test="<%= portletResource.equals(PortletKeys.PORTLET_DISPLAY_TEMPLATE) || portletResource.equals(TemplatePortletKeys.TEMPLATE) %>">
			<clay:sheet-section>
				<h4 class="sheet-subtitle"><liferay-ui:message key="related-application-permissions" /></h4>

				<div class="related-permissions">

					<%
					Set<String> relatedPortletResources = new HashSet<String>();

					List<String> headerNames = new ArrayList<String>();

					headerNames.add("permissions");
					headerNames.add("sites");

					SearchContainer<?> searchContainer = new SearchContainer(liferayPortletRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, liferayPortletResponse.createRenderURL(), headerNames, "there-are-no-applications-that-support-widget-templates");

					searchContainer.setRowChecker(new ResourceActionRowChecker(liferayPortletResponse));

					List<com.liferay.portal.kernel.dao.search.ResultRow> resultRows = searchContainer.getResultRows();

					List<TemplateHandler> templateHandlers = PortletDisplayTemplateUtil.getPortletDisplayTemplateHandlers();

					ListUtil.sort(templateHandlers, new TemplateHandlerComparator(locale));

					for (TemplateHandler templateHandler : templateHandlers) {
						String actionId = ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE;
						String resource = templateHandler.getResourceName();
						int scope = ResourceConstants.SCOPE_COMPANY;
						boolean supportsFilterByGroup = true;
						String target = resource + actionId;
						List<Group> groups = Collections.emptyList();

						String groupIds = ParamUtil.getString(request, "groupIds" + target, null);

						long[] groupIdsArray = StringUtil.split(groupIds, 0L);

						List<String> groupNames = new ArrayList<String>();

						Portlet curPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), resource);

						if (curPortlet.isSystem()) {
							continue;
						}

						if (roleDisplayContext.isAllowGroupScope()) {
							RolePermissions rolePermissions = new RolePermissions(resource, ResourceConstants.SCOPE_GROUP, actionId, role.getRoleId());

							groups = GroupLocalServiceUtil.search(
								company.getCompanyId(), GroupTypeContributorUtil.getClassNameIds(), null, null,
								LinkedHashMapBuilder.<String, Object>put(
									"rolePermissions", rolePermissions
								).build(),
								true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

							groupIdsArray = new long[groups.size()];

							for (int i = 0; i < groups.size(); i++) {
								Group group = groups.get(i);

								groupIdsArray[i] = group.getGroupId();

								groupNames.add(HtmlUtil.escape(group.getDescriptiveName(locale)));
							}

							if (!groups.isEmpty()) {
								scope = ResourceConstants.SCOPE_GROUP;
							}
						}
						else {
							scope = ResourceConstants.SCOPE_GROUP_TEMPLATE;
						}

						ResultRow row = new ResultRow(new Object[] {role, actionId, resource, target, scope, supportsFilterByGroup, groups, groupIdsArray, groupNames, curPortlet.getPortletId()}, target, relatedPortletResources.size());

						relatedPortletResources.add(curPortlet.getPortletId());

						row.addText(PortalUtil.getPortletLongTitle(curPortlet, application, locale) + ": " + roleDisplayContext.getActionLabel(resource, actionId));

						row.addJSP("/edit_role_permissions_resource_scope.jsp", application, request, response);

						resultRows.add(row);
					}

					searchContainer.setTotal(relatedPortletResources.size());
					%>

					<aui:input name="relatedPortletResources" type="hidden" value="<%= StringUtil.merge(relatedPortletResources) %>" />

					<liferay-ui:search-iterator
						paginate="<%= false %>"
						searchContainer="<%= searchContainer %>"
					/>
				</div>
			</clay:sheet-section>
		</c:if>

		<clay:sheet-footer>
			<aui:button cssClass="btn-primary" onClick='<%= liferayPortletResponse.getNamespace() + "updateActions();" %>' value="save" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>