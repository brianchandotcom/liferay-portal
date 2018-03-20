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
String p_u_i_d = ParamUtil.getString(request, "p_u_i_d");
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectOrganization");
String target = ParamUtil.getString(request, "target");

User selUser = PortalUtil.getSelectedUser(request);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_organization.jsp");

if (selUser != null) {
	portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
}

portletURL.setParameter("eventName", eventName);

if (Validator.isNotNull(target)) {
	portletURL.setParameter("target", target);
}

renderResponse.setTitle(LanguageUtil.get(request, "organizations"));
%>

<clay:navigation-bar
	items='<%= userDisplayContext.getNavigationItems("organizations") %>'
/>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>

			<li>
				<aui:form action="<%= portletURL.toString() %>" name="searchFm">
					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectOrganizationFm">
	<liferay-ui:search-container
		searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
		var="organizationSearchContainer"
	>

		<%
		long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

		LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

		if (filterManageableOrganizations) {
			organizationParams.put("organizationsTree", user.getOrganizations());
		}
		%>

		<liferay-ui:organization-search-container-results
			organizationParams="<%= organizationParams %>"
			parentOrganizationId="<%= parentOrganizationId %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				orderable="<%= true %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="parent-organization"
				value="<%= HtmlUtil.escape(organization.getParentOrganizationName()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				orderable="<%= true %>"
				value="<%= LanguageUtil.get(request, organization.getType()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="city"
				property="address.city"
			/>

			<liferay-ui:search-container-column-text
				name="region"
				property="address.region.name"
			/>

			<liferay-ui:search-container-column-text
				name="country"
				value="<%= UsersAdmin.ORGANIZATION_COUNTRY_NAME_ACCESSOR.get(organization) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= Validator.isNull(p_u_i_d) || OrganizationMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, organization.getOrganizationId()) %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("entityid", organization.getOrganizationId());
					data.put("entityname", organization.getName());
					data.put("groupid", organization.getGroupId());
					data.put("type", LanguageUtil.get(request, organization.getType()));

					boolean disabled = false;

					if (selUser != null) {
						for (long curOrganizationId : selUser.getOrganizationIds()) {
							if (curOrganizationId == organization.getOrganizationId()) {
								disabled = true;

								break;
							}
						}
					}
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= disabled %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	var openingLiferay = Util.getOpener().Liferay;

	openingLiferay.fire(
		'<portlet:namespace />enableRemovedOrganizations',
		{
			selectors: A.all('.selector-button:disabled')
		}
	);

	Util.selectEntityHandler('#<portlet:namespace />selectOrganizationFm', '<%= HtmlUtil.escapeJS(eventName) %>', <%= selUser != null %>);
</aui:script>