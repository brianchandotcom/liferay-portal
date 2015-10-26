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
long groupId = ParamUtil.getLong(request, "groupId");
long[] selectedGroupIds = StringUtil.split(ParamUtil.getString(request, "selectedGroupIds"), 0L);

Boolean privateLayout = null;

String privateLayoutString = request.getParameter("privateLayout");

if (Validator.isNotNull(privateLayoutString)) {
	privateLayout = GetterUtil.getBoolean(privateLayoutString);
}

String type = ParamUtil.getString(request, "type");
String[] types = ParamUtil.getParameterValues(request, "types");

if (Validator.isNull(type)) {
	if (types.length > 0) {
		type = types[0];
	}
	else {
		type = "sites-that-i-administer";
	}
}

if (types.length == 0) {
	types = new String[] {type};
}

String p_u_i_d = ParamUtil.getString(request, "p_u_i_d");
String filter = ParamUtil.getString(request, "filter");
boolean includeCompany = ParamUtil.getBoolean(request, "includeCompany");
boolean includeCurrentGroup = ParamUtil.getBoolean(request, "includeCurrentGroup", true);
boolean includeUserPersonalSite = ParamUtil.getBoolean(request, "includeUserPersonalSite");
boolean manualMembership = ParamUtil.getBoolean(request, "manualMembership");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectSite");
String target = ParamUtil.getString(request, "target");

PortletURL portletURL = renderResponse.createRenderURL();

User selUser = PortalUtil.getSelectedUser(request);

if (selUser != null) {
	portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
}

portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("selectedGroupIds", StringUtil.merge(selectedGroupIds));
portletURL.setParameter("type", type);
portletURL.setParameter("types", types);
portletURL.setParameter("filter", filter);
portletURL.setParameter("includeCompany", String.valueOf(includeCompany));
portletURL.setParameter("includeUserPersonalSite", String.valueOf(includeUserPersonalSite));
portletURL.setParameter("manualMembership", String.valueOf(manualMembership));
portletURL.setParameter("eventName", eventName);
portletURL.setParameter("target", target);
%>

<c:if test='<%= !type.equals("parent-sites") || (types.length > 1) %>'>
	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">
			<aui:nav-item cssClass='<%= (types.length > 1) ? StringPool.BLANK : "active" %>' label="sites" />

			<c:if test="<%= types.length > 1 %>">

				<%
				for (String curType : types) {
					portletURL.setParameter("type", curType);
				%>

					<aui:nav-item href="<%= portletURL.toString() %>" label="<%= curType %>" selected="<%= curType.equals(type) %>" />

				<%
				}
				%>

			</c:if>
		</aui:nav>

		<c:if test='<%= !type.equals("parent-sites") %>'>
			<aui:nav-bar-search>
				<aui:form action="<%= portletURL.toString() %>" name="searchFm">
					<liferay-ui:input-search markupView="lexicon" />
				</aui:form>
			</aui:nav-bar-search>
		</c:if>
	</aui:nav-bar>
</c:if>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectGroupFm">
	<liferay-ui:search-container
		searchContainer="<%= new GroupSearch(renderRequest, portletURL) %>"
	>

		<%
		GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();
		%>

		<liferay-ui:search-container-results>

			<%
			results.clear();

			if (manualMembership) {
				groupParams.put("manualMembership", Boolean.TRUE);
			}

			if (type.equals("child-sites")) {
				Group parentGroup = GroupLocalServiceUtil.getGroup(groupId);

				List<Group> parentGroups = new ArrayList<Group>();

				parentGroups.add(parentGroup);

				groupParams.put("groupsTree", parentGroups);
			}
			else if (filterManageableGroups) {
				groupParams.put("usersGroups", user.getUserId());
			}

			groupParams.put("site", Boolean.TRUE);

			int additionalSites = 0;

			if (includeCompany) {
				if (searchContainer.getStart() == 0) {
					results.add(company.getGroup());
				}

				additionalSites++;
			}

			if (!includeCurrentGroup && (groupId > 0)) {
				List<Long> excludedGroupIds = new ArrayList<Long>();

				Group group = GroupLocalServiceUtil.getGroup(groupId);

				if (group.isStagingGroup()) {
					excludedGroupIds.add(group.getLiveGroupId());
				}
				else {
					excludedGroupIds.add(groupId);
				}

				groupParams.put("excludedGroupIds", excludedGroupIds);
			}

			if (includeUserPersonalSite) {
				if (searchContainer.getStart() == 0) {
					Group userPersonalSite = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.USER_PERSONAL_SITE);

					results.add(userPersonalSite);
				}

				additionalSites++;
			}

			if (type.equals("layoutScopes")) {
				total = GroupLocalServiceUtil.getGroupsCount(company.getCompanyId(), Layout.class.getName(), groupId);
			}
			else if (type.equals("parent-sites")) {
			}
			else if (searchTerms.isAdvancedSearch()) {
				total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), null, searchTerms.getName(), searchTerms.getDescription(), groupParams, searchTerms.isAndOperator());
			}
			else {
				total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), null, searchTerms.getKeywords(), groupParams, searchTerms.isAndOperator());
			}

			total += additionalSites;

			searchContainer.setTotal(total);

			int start = searchContainer.getStart();

			if (searchContainer.getStart() > additionalSites) {
				start = searchContainer.getStart() - additionalSites;
			}

			int end = searchContainer.getEnd() - additionalSites;

			List<Group> groups = null;

			if (type.equals("layoutScopes")) {
				groups = GroupLocalServiceUtil.getGroups(company.getCompanyId(), Layout.class.getName(), groupId, start, end);

				groups = _filterLayoutGroups(groups, privateLayout);
			}
			else if (type.equals("parent-sites")) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				groups = group.getAncestors();

				if (Validator.isNotNull(filter)) {
					groups = _filterGroups(groups, filter);
				}

				total = groups.size();

				total += additionalSites;

				searchContainer.setTotal(total);
			}
			else if (searchTerms.isAdvancedSearch()) {
				groups = GroupLocalServiceUtil.search(company.getCompanyId(), null, searchTerms.getName(), searchTerms.getDescription(), groupParams, searchTerms.isAndOperator(), start, end, searchContainer.getOrderByComparator());
			}
			else {
				groups = GroupLocalServiceUtil.search(company.getCompanyId(), null, searchTerms.getKeywords(), groupParams, start, end, searchContainer.getOrderByComparator());
			}

			results.addAll(groups);

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
				value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="checkbox-cell"
			>
				<c:if test="<%= Validator.isNull(p_u_i_d) || SiteMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, group.getGroupId()) %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("groupdescriptivename", group.getDescriptiveName(locale));
					data.put("groupid", group.getGroupId());
					data.put("grouptarget", target);
					data.put("grouptype", LanguageUtil.get(request, group.getTypeLabel()));
					data.put("url", group.getDisplayURL(themeDisplay));

					boolean disabled = false;

					if (selUser != null) {
						for (long curGroupId : selUser.getGroupIds()) {
							if (curGroupId == group.getGroupId()) {
								disabled = true;

								break;
							}
						}
					}
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= ArrayUtil.contains(selectedGroupIds, group.getGroupId()) || disabled %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>

		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<%!
private List<Group> _filterLayoutGroups(List<Group> groups, Boolean privateLayout) throws Exception {
	List<Group> filteredGroups = new ArrayList();

	if (privateLayout == null) {
		return groups;
	}

	for (Group group : groups) {
		if (!group.isLayout()) {
			continue;
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(group.getClassPK());

		if (layout.isPrivateLayout() == privateLayout) {
			filteredGroups.add(group);
		}
	}

	return filteredGroups;
}

private List<Group> _filterGroups(List<Group> groups, String filter) throws Exception {
	List<Group> filteredGroups = new ArrayList();

	for (Group group : groups) {
		if (filter.equals("contentSharingWithChildrenEnabled") && SitesUtil.isContentSharingWithChildrenEnabled(group)) {
			filteredGroups.add(group);
		}
	}

	return filteredGroups;
}
%>

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