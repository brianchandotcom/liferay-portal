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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
String tabs1 = "roles";
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Role role = (Role)request.getAttribute(WebKeys.ROLE);

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet portlet = null;
String portletResourceLabel = null;

if (Validator.isNotNull(portletResource)) {
	portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	String portletId = portlet.getPortletId();

	if (portletId.equals(PortletKeys.PORTAL)) {
		portletResourceLabel = LanguageUtil.get(pageContext, "general-permissions");
	}
	else {
		portletResourceLabel = PortalUtil.getPortletLongTitle(portlet, application, locale);
	}
}

List modelResources = null;

if (Validator.isNotNull(portletResource)) {
	modelResources = ResourceActionsUtil.getPortletModelResources(portletResource);
}
%>

<portlet:actionURL var="editRolePermissionsURL">
	<portlet:param name="struts_action" value="/roles_admin/edit_role_permissions" />
</portlet:actionURL>

<aui:form action="<%= editRolePermissionsURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" />
	<aui:input name="roleId" type="hidden" value="<%= role.getRoleId() %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="modelResources" type="hidden" value='<%= (modelResources == null) ? "" : StringUtil.merge(modelResources) %>' />
	<aui:input name="selectedTargets" type="hidden" />

	<h3><%= portletResourceLabel %></h3>

	<%
		request.setAttribute("edit_role_permissions.jsp-curPortletResource", portletResource);

		String applicationPermissionsLabel = "application-permissions";

		if (portletResource.equals(PortletKeys.PORTAL)) {
			applicationPermissionsLabel = StringPool.BLANK;
		}
		else if ((portlet != null) && Validator.isNotNull(portlet.getControlPanelEntryCategory())) {
			applicationPermissionsLabel = "general-permissions";
		}
	%>

	<c:if test="<%= Validator.isNotNull(applicationPermissionsLabel) %>">
		<h4><liferay-ui:message key="<%= applicationPermissionsLabel %>" /> <liferay-ui:icon-help message='<%= applicationPermissionsLabel + "-help" %>' /></h4>
	</c:if>

	<liferay-util:include page="/html/portlet/roles_admin/edit_role_permissions_resource.jsp" />

	<c:if test="<%= (modelResources != null) && !modelResources.isEmpty() %>">
		<h4><liferay-ui:message key="resource-permissions" /> <liferay-ui:icon-help message="resource-permissions-help" /></h4>

		<div class="permission-group">

			<%
				modelResources = ListUtil.sort(modelResources, new ModelResourceWeightComparator());

				for (int i = 0; i < modelResources.size(); i++) {
					String curModelResource = (String)modelResources.get(i);

					String curModelResourceName = ResourceActionsUtil.getModelResource(pageContext, curModelResource);
			%>

			<h5 id="<%= _getResourceHtmlId(curModelResource) %>"><%= curModelResourceName %></h5>

			<%
				request.setAttribute("edit_role_permissions.jsp-curModelResource", curModelResource);
				request.setAttribute("edit_role_permissions.jsp-curModelResourceName", curModelResourceName);
			%>

			<liferay-util:include page="/html/portlet/roles_admin/edit_role_permissions_resource.jsp" />

			<%
				}
			%>

		</div>
	</c:if>

	<aui:button-row>
		<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "updateActions();" %>' value="save" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%
PortletURL definePermissionsURL = liferayPortletResponse.createRenderURL();

definePermissionsURL.setParameter("struts_action", "/roles_admin/edit_role_permissions");
definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
definePermissionsURL.setParameter("redirect", backURL);
definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "define-permissions"), definePermissionsURL.toString());

if (Validator.isNotNull(portletResource)) {
	PortletURL resourceURL = liferayPortletResponse.createRenderURL();

	resourceURL.setParameter("struts_action", "/roles_admin/edit_role");
	resourceURL.setParameter(Constants.CMD, Constants.EDIT);
	resourceURL.setParameter("tabs1", tabs1);
	resourceURL.setParameter("portletResource", portletResource);

	PortalUtil.addPortletBreadcrumbEntry(request, portletResourceLabel, resourceURL.toString());
}
%>