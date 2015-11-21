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
String tabs1 = (String)request.getAttribute("edit_site_assignments.jsp-tabs1");
String tabs2 = (String)request.getAttribute("edit_site_assignments.jsp-tabs2");

int cur = (Integer)request.getAttribute("edit_site_assignments.jsp-cur");

Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

PortletURL viewOrganizationsURL = renderResponse.createRenderURL();

viewOrganizationsURL.setParameter("mvcPath", "/view.jsp");
viewOrganizationsURL.setParameter("tabs1", "organizations");
viewOrganizationsURL.setParameter("tabs2", tabs2);
viewOrganizationsURL.setParameter("redirect", currentURL);
viewOrganizationsURL.setParameter("groupId", String.valueOf(group.getGroupId()));

OrganizationGroupChecker organizationGroupChecker = null;

if (!tabs1.equals("summary") && !tabs2.equals("current")) {
	organizationGroupChecker = new OrganizationGroupChecker(renderResponse, group);
}

String emptyResultsMessage = OrganizationSearch.EMPTY_RESULTS_MESSAGE;

if (tabs2.equals("current")) {
	emptyResultsMessage ="no-organization-was-found-that-is-a-member-of-this-site";
}

SearchContainer searchContainer = new OrganizationSearch(renderRequest, viewOrganizationsURL);

searchContainer.setEmptyResultsMessage(emptyResultsMessage);
%>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "submit();" %>'>
	<aui:input name="tabs1" type="hidden" value="organizations" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="addOrganizationIds" type="hidden" />
	<aui:input name="removeOrganizationIds" type="hidden" />

	<liferay-ui:search-container
		rowChecker="<%= organizationGroupChecker %>"
		searchContainer="<%= searchContainer %>"
		var="organizationSearchContainer"
	>

		<%
		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

		long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

		LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

		if (tabs1.equals("summary") || tabs2.equals("current")) {
			organizationParams.put("groupOrganization", Long.valueOf(group.getGroupId()));
			organizationParams.put("organizationsGroups", Long.valueOf(group.getGroupId()));
		}
		%>

		<liferay-ui:search-container-results>

			<%
			total = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), parentOrganizationId, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams);

			organizationSearchContainer.setTotal(total);

			results = OrganizationLocalServiceUtil.search(company.getCompanyId(), parentOrganizationId, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, organizationSearchContainer.getStart(), organizationSearchContainer.getEnd(), organizationSearchContainer.getOrderByComparator());

			organizationSearchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Organization"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>

			<%@ include file="/organization_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-util:buffer var="formButton">
			<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
				<c:choose>
					<c:when test='<%= tabs2.equals("current") %>'>

						<%
						viewOrganizationsURL.setParameter("tabs2", "available");
						%>

						<liferay-frontend:add-menu>
							<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "assign-organizations") %>' url="<%= viewOrganizationsURL.toString() %>" />
						</liferay-frontend:add-menu>

						<%
						viewOrganizationsURL.setParameter("tabs2", "current");
						%>

					</c:when>
					<c:otherwise>

						<%
						portletURL.setParameter("tabs2", "current");
						portletURL.setParameter("cur", String.valueOf(cur));

						String taglibOnClick = renderResponse.getNamespace() + "updateGroupOrganizations('" + portletURL.toString() + "');";
						%>

						<aui:button-row>
							<aui:button onClick="<%= taglibOnClick %>" primary="<%= true %>" value="save" />
						</aui:button-row>
					</c:otherwise>
				</c:choose>
			</c:if>
		</liferay-util:buffer>

		<c:choose>
			<c:when test='<%= tabs1.equals("summary") && (total > 0) %>'>
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" persistState="<%= true %>" title='<%= LanguageUtil.format(request, (total > 1) ? "x-organizations" : "x-organization", total, false) %>'>
					<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />

					<c:if test="<%= total > searchContainer.getDelta() %>">
						<a href="<%= viewOrganizationsURL %>"><liferay-ui:message key="view-more" /> &raquo;</a>
					</c:if>
				</liferay-ui:panel>
			</c:when>
			<c:when test='<%= !tabs1.equals("summary") %>'>
				<liferay-ui:search-iterator markupView="lexicon" />

				<%= formButton %>
			</c:when>
		</c:choose>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />updateGroupOrganizations(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addOrganizationIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeOrganizationIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editGroupOrganizations" />');
	}
</aui:script>