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
String tabs1 = ParamUtil.getString(request, "tabs1", "my-sites");

if (!tabs1.equals("my-sites") && !tabs1.equals("available-sites")) {
	tabs1 = "my-sites";
}

String displayStyle = ParamUtil.getString(request, "displayStyle", "descriptive");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);

request.setAttribute("view.jsp-tabs1", tabs1);

GroupSearch groupSearch = new GroupSearch(renderRequest, PortletURLUtil.clone(portletURL, renderResponse));

GroupSearchTerms searchTerms = (GroupSearchTerms)groupSearch.getSearchTerms();

LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

groupParams.put("site", Boolean.TRUE);

if (tabs1.equals("my-sites")) {
	groupParams.put("usersGroups", Long.valueOf(user.getUserId()));
	groupParams.put("active", Boolean.TRUE);
}
else {
	List types = new ArrayList();

	types.add(Integer.valueOf(GroupConstants.TYPE_SITE_OPEN));
	types.add(Integer.valueOf(GroupConstants.TYPE_SITE_RESTRICTED));

	groupParams.put("types", types);
	groupParams.put("active", Boolean.TRUE);
}

int groupsCount = GroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), groupParams);

groupSearch.setTotal(groupsCount);

List<Group> groups = GroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), groupParams, groupSearch.getStart(), groupSearch.getEnd(), groupSearch.getOrderByComparator());

groupSearch.setResults(groups);

long[] groupIds = ListUtil.toLongArray(groups, Group.GROUP_ID_ACCESSOR);

Map<Long, Integer> groupUsersCounts = UserLocalServiceUtil.searchCounts(company.getCompanyId(), WorkflowConstants.STATUS_APPROVED, groupIds);
%>

<liferay-ui:success key="membershipRequestSent" message="your-request-was-sent-you-will-receive-a-reply-by-email" />

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL mySitesURL = PortletURLUtil.clone(portletURL, renderResponse);

		mySitesURL.setParameter("tabs1", "my-sites");
		%>

		<aui:nav-item href="<%= mySitesURL.toString() %>" id="mySites" label="my-sites" selected='<%= tabs1.equals("my-sites") %>' />

		<%
		PortletURL availableSitesURL = PortletURLUtil.clone(portletURL, renderResponse);

		availableSitesURL.setParameter("tabs1", "available-sites");
		%>

		<aui:nav-item href="<%= availableSitesURL.toString() %>" id="availableSites" label="available-sites" selected='<%= tabs1.equals("available-sites") %>' />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL %>" name="searchFm">
			<liferay-portlet:renderURLParams varImpl="portletURL" />

			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= groupSearch.getOrderByCol() %>"
			orderByType="<%= groupSearch.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="get" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= groupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Group"
			keyProperty="groupId"
			modelVar="group"
			rowIdProperty="friendlyURL"
		>

			<%
			String rowURL = StringPool.BLANK;

			if (group.getPublicLayoutsPageCount() > 0) {
				rowURL = group.getDisplayURL(themeDisplay, false);
			}
			else if (tabs1.equals("my-sites") && (group.getPrivateLayoutsPageCount() > 0)) {
				rowURL = group.getDisplayURL(themeDisplay, true);
			}
			%>

			<liferay-ui:search-container-column-text
				name="name"
				orderable="<%= true %>"
			>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(rowURL) %>">
						<a href="<%= rowURL %>" target="_blank">
							<strong><%= HtmlUtil.escape(group.getDescriptiveName(locale)) %></strong>
						</a>
					</c:when>
					<c:otherwise>
						<strong><%= HtmlUtil.escape(group.getDescriptiveName(locale)) %></strong>
					</c:otherwise>
				</c:choose>

				<c:if test='<%= !tabs1.equals("my-sites") && Validator.isNotNull(group.getDescription(locale)) %>'>
					<br />

					<em><%= HtmlUtil.escape(group.getDescription(locale)) %></em>
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="members"
				value="<%= String.valueOf(groupUsersCounts.get(group.getGroupId())) %>"
			/>

			<c:if test='<%= tabs1.equals("my-sites") && PropsValues.LIVE_USERS_ENABLED %>'>
				<liferay-ui:search-container-column-text
					name="online-now"
					value="<%= String.valueOf(LiveUsers.getGroupUsersCount(company.getCompanyId(), group.getGroupId())) %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-text
				name="tags"
			>
				<liferay-ui:asset-tags-summary
					className="<%= Group.class.getName() %>"
					classPK="<%= group.getGroupId() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				path="/site_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>