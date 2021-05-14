<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationDisplayContext commerceOrganizationDisplayContext = (CommerceOrganizationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationDisplayContext.getOrganization();

PortletURL portletURL = commerceOrganizationDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "/commerce_organization/view_commerce_organization");
%>

<portlet:renderURL var="editCommerceOrganizationURL">
	<portlet:param name="mvcRenderCommandName" value="/commerce_organization/edit_commerce_organization" />
	<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
	<portlet:param name='<%= PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL" %>' value="<%= portletURL.toString() %>" />
</portlet:renderURL>

<div class="account-management">
	<div class="row">
		<div class="col-auto">
			<img alt="avatar" class="account-management__thumbnail img-fluid rounded-circle" src="<%= commerceOrganizationDisplayContext.getLogo(organization) %>" />
		</div>

		<div class="col d-flex flex-col justify-content-center">
			<span class="account-management__name">
				<%= HtmlUtil.escape(organization.getName()) %>
			</span>
		</div>

		<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organization.getOrganizationId(), ActionKeys.UPDATE) %>">
			<div class="align-items-center col-auto d-flex">
				<div class="account-management__action">
					<aui:button cssClass="btn-lg btn-secondary" href="<%= editCommerceOrganizationURL %>" value='<%= LanguageUtil.get(request, "edit-organization") %>' />
				</div>
			</div>
		</c:if>
	</div>
</div>

<liferay-frontend:screen-navigation
	context="<%= organization %>"
	key="<%= CommerceOrganizationScreenNavigationConstants.SCREEN_NAVIGATION_KEY %>"
	portletURL="<%= portletURL %>"
/>