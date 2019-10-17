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

<%@ include file="/document_library/init.jsp" %>

<%
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectGroup");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/document_library/select_group.jsp");
%>

<div class="container-fluid-1280">
	<clay:management-toolbar
		clearResultsURL="<%= portletURL.toString() %>"
		searchActionURL="<%= portletURL.toString() %>"
		selectable="<%= false %>"
	/>

	<aui:form method="post" name="selectGroupFm">
		<liferay-ui:search-container
			searchContainer="<%= new GroupSearch(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse)) %>"
		>

			<%
			GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

			LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

			groupParams.put("active", true);
			groupParams.put("usersGroups", user.getUserId());
			%>

			<liferay-ui:search-container-results>

				<%
				int additionalSites = 0;

				if (!searchTerms.hasSearchTerms() && PortletPermissionUtil.hasControlPanelAccessPermission(permissionChecker, themeDisplay.getCompanyGroupId(), DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
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

				total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), null, searchTerms.getKeywords(), groupParams);

				total += additionalSites;

				searchContainer.setTotal(total);

				int start = searchContainer.getStart();

				if (searchContainer.getStart() > additionalSites) {
					start = searchContainer.getStart() - additionalSites;
				}

				int end = searchContainer.getEnd() - additionalSites;

				List<Group> sites = GroupServiceUtil.search(company.getCompanyId(), null, searchTerms.getKeywords(), groupParams, start, end, searchContainer.getOrderByComparator());

				results.addAll(sites);

				searchContainer.setResults(results);
				%>

			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Group"
				escapedModel="<%= true %>"
				keyProperty="groupId"
				modelVar="group"
				rowIdProperty="friendlyURL"
			>

				<%
				if (group.hasStagingGroup()) {
					group = group.getStagingGroup();
				}

				String groupDescriptiveName = group.getDescriptiveName(locale);

				if (group.isUser()) {
					groupDescriptiveName = LanguageUtil.get(request, "my-site");
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand table-cell-minw-200 table-title"
					name="name"
					value="<%= HtmlUtil.escape(groupDescriptiveName) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smaller table-cell-minw-150"
					name="type"
					value="<%= LanguageUtil.get(request, group.getTypeLabel()) %>"
				/>

				<liferay-ui:search-container-column-text>

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("groupdescriptivename", groupDescriptiveName);
					data.put("groupid", group.getGroupId());
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="select" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectGroupFm',
		'<%= HtmlUtil.escapeJS(eventName) %>'
	);
</aui:script>