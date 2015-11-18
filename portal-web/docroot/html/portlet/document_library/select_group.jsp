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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectGroup");
%>

<aui:form method="post" name="selectGroupFm">
	<liferay-ui:header
		title="sites"
	/>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/document_library/select_group");
	%>

	<liferay-ui:search-container
		searchContainer="<%= new GroupSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-form
			page="/html/portlet/users_admin/group_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<div class="separator"><!-- --></div>

		<%
		GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

		groupParams.put("active", true);
		groupParams.put("usersGroups", user.getUserId());
		%>

		<liferay-ui:search-container-results>

			<%
			int additionalSites = 0;

			if (!searchTerms.hasSearchTerms() && PortalUtil.isCompanyControlPanelPortlet(PortletKeys.DOCUMENT_LIBRARY, themeDisplay)) {
				if (searchContainer.getStart() == 0) {
					results.add(company.getGroup());
				}

				additionalSites++;

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
				sites = GroupServiceUtil.search(company.getCompanyId(), null, searchTerms.getName(), searchTerms.getDescription(), groupParams, searchTerms.isAndOperator(), start, end, searchContainer.getOrderByComparator());
			}
			else {
				sites = GroupServiceUtil.search(company.getCompanyId(), null, searchTerms.getKeywords(), groupParams, start, end, searchContainer.getOrderByComparator());
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

			<%
			String groupDescriptiveName = HtmlUtil.escape(group.getDescriptiveName(locale));

			if (group.isUser()) {
				groupDescriptiveName = LanguageUtil.get(pageContext, "my-site");
			}
			%>

			<liferay-ui:search-container-column-text
				name="name"
				value="<%= groupDescriptiveName %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= LanguageUtil.get(pageContext, group.getTypeLabel()) %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("groupdescriptivename", groupDescriptiveName);
				data.put("groupid", group.getGroupId());
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectGroupFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>