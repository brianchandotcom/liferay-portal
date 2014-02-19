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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

String p_u_i_d = ParamUtil.getString(request, "p_u_i_d");
long groupId = ParamUtil.getLong(request, "groupId");
boolean includeCompany = ParamUtil.getBoolean(request, "includeCompany");
boolean includeUserPersonalSite = ParamUtil.getBoolean(request, "includeUserPersonalSite");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectGroup");
String target = ParamUtil.getString(request, "target");

User selUser = PortalUtil.getSelectedUser(request);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/sites_admin/select_site");

if (selUser != null) {
	portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
}

portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("includeCompany", String.valueOf(includeCompany));
portletURL.setParameter("includeUserPersonalSite", String.valueOf(includeUserPersonalSite));
portletURL.setParameter("eventName", eventName);
portletURL.setParameter("target", target);
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectGroupFm">
	<liferay-ui:header
		title="sites"
	/>

	<liferay-ui:search-container
		searchContainer="<%= new GroupSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-form
			page="/html/portlet/users_admin/group_search.jsp"
		/>

		<%
		GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();
		%>

		<liferay-ui:search-container-results>

			<%
			results.clear();

			List<Long> excludedGroupIds = new ArrayList<Long>();

			Group companyGroup = company.getGroup();

			excludedGroupIds.add(companyGroup.getGroupId());

			if (groupId > 0) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				if (group.isStagingGroup()) {
					excludedGroupIds.add(group.getLiveGroupId());
				}
				else {
					excludedGroupIds.add(groupId);
				}
			}

			groupParams.put("excludedGroupIds", excludedGroupIds);

			if (strutsAction.equals("/users_admin/select_site")) {
				groupParams.put("manualMembership", Boolean.TRUE);
			}

			groupParams.put("site", Boolean.TRUE);

			if (filterManageableGroups) {
				groupParams.put("usersGroups", user.getUserId());
			}

			int additionalSites = 0;

			if (includeCompany) {
				if (searchContainer.getStart() == 0) {
					results.add(companyGroup);
				}

				additionalSites++;
			}

			if (includeUserPersonalSite) {
				if (searchContainer.getStart() == 0) {
					Group userPersonalSite = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.USER_PERSONAL_SITE);

					results.add(userPersonalSite);
				}

				additionalSites++;
			}

			if (searchTerms.isAdvancedSearch()) {
				total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), null, searchTerms.getName(), searchTerms.getDescription(), groupParams, searchTerms.isAndOperator());
			}
			else {
				total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), null, searchTerms.getKeywords(), groupParams);
			}

			total += additionalSites;

			searchContainer.setTotal(total);

			int start = searchContainer.getStart();

			if (searchContainer.getStart() > additionalSites) {
				start = searchContainer.getStart() - additionalSites;
			}

			int end = searchContainer.getEnd() - additionalSites;

			List<Group> sites = null;

			if (searchTerms.isAdvancedSearch()) {
				sites = GroupLocalServiceUtil.search(company.getCompanyId(), null, searchTerms.getName(), searchTerms.getDescription(), groupParams, searchTerms.isAndOperator(), start, end, searchContainer.getOrderByComparator());
			}
			else {
				sites = GroupLocalServiceUtil.search(company.getCompanyId(), null, searchTerms.getKeywords(), groupParams, start, end, searchContainer.getOrderByComparator());
			}

			results.addAll(sites);

			searchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Group"
			escapedModel="<%= true %>"
			keyProperty="groupId"
			modelVar="group"
			rowIdProperty="friendlyURL"
		>

			<liferay-ui:search-container-column-text
				name="name"
				value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= LanguageUtil.get(pageContext, group.getTypeLabel()) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= (Validator.isNull(p_u_i_d) || SiteMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, group.getGroupId())) %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("groupdescriptivename", group.getDescriptiveName(locale));
					data.put("groupid", group.getGroupId());
					data.put("grouptarget", target);
					data.put("grouptype", LanguageUtil.get(pageContext, group.getTypeLabel()));

					boolean disabled = false;

					if (selUser != null) {
						for (Group curGroup : selUser.getGroups()) {
							if (curGroup.getGroupId() == group.getGroupId()) {
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
		'<portlet:namespace />enableRemovedSites',
		{
			selectors: A.all('.selector-button:disabled')
		}
	);

	Util.selectEntityHandler('#<portlet:namespace />selectGroupFm', '<%= HtmlUtil.escapeJS(eventName) %>', <%= selUser != null %>);
</aui:script>