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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
String p_u_i_d = ParamUtil.getString(request, "p_u_i_d");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectOrganization");
String target = ParamUtil.getString(request, "target");

User selUser = PortalUtil.getSelectedUser(request);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/users_admin/select_organization");

if (selUser != null) {
	portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
}

portletURL.setParameter("eventName", eventName);

if (Validator.isNotNull(target)) {
	portletURL.setParameter("target", target);
}
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectOrganizationFm">
	<liferay-ui:header
		title="organizations"
	/>

	<liferay-ui:search-container
		searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
		var="organizationSearchContainer"
	>
		<liferay-ui:search-form
			page="/html/portlet/users_admin/organization_search.jsp"
		/>

		<%
		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

		long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

		LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

		if (filterManageableOrganizations) {
			organizationParams.put("organizationsTree", user.getOrganizations());
		}
		%>

		<liferay-ui:search-container-results>
			<c:choose>
				<c:when test="<%= PropsValues.ORGANIZATIONS_INDEXER_ENABLED && PropsValues.ORGANIZATIONS_SEARCH_WITH_INDEX %>">
					<%@ include file="/html/portlet/users_admin/organization_search_results_index.jspf" %>
				</c:when>
				<c:otherwise>
					<%@ include file="/html/portlet/users_admin/organization_search_results_database.jspf" %>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Organization"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>
			<liferay-ui:search-container-column-text
				name="name"
				orderable="<%= true %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				buffer="buffer"
				name="parent-organization"
			>

				<%
				String parentOrganizationName = StringPool.BLANK;

				if (organization.getParentOrganizationId() > 0) {
					try {
						Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

						parentOrganizationName = parentOrganization.getName();
					}
					catch (Exception e) {
					}
				}

				buffer.append(HtmlUtil.escape(parentOrganizationName));
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="type"
				orderable="<%= true %>"
				value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
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
				property="address.country.name"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= (Validator.isNull(p_u_i_d) || OrganizationMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, organization.getOrganizationId())) %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("groupid", organization.getGroupId());
					data.put("name", organization.getName());
					data.put("organizationid", organization.getOrganizationId());
					data.put("type", LanguageUtil.get(pageContext, organization.getType()));

					boolean disabled = false;

					if (selUser != null) {
						for (Organization curOrganization : selUser.getOrganizations()) {
							if (curOrganization.getOrganizationId() == organization.getOrganizationId()) {
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

		<liferay-ui:search-iterator />
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