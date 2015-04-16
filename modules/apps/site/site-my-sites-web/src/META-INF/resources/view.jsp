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

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);

pageContext.setAttribute("portletURL", portletURL);

request.setAttribute("view.jsp-tabs1", tabs1);
%>

<liferay-ui:success key="membershipRequestSent" message="your-request-was-sent-you-will-receive-a-reply-by-email" />

<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<liferay-ui:tabs
		names="my-sites,available-sites"
		portletURL="<%= portletURL %>"
	/>

	<liferay-ui:search-container
		searchContainer="<%= new GroupSearch(renderRequest, portletURL) %>"
	>

		<%
		GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

		groupParams.put("site", Boolean.TRUE);

		if (tabs1.equals("my-sites")) {
			groupParams.put("usersGroups", new Long(user.getUserId()));
			groupParams.put("active", Boolean.TRUE);
		}
		else {
			List types = new ArrayList();

			types.add(new Integer(GroupConstants.TYPE_SITE_OPEN));
			types.add(new Integer(GroupConstants.TYPE_SITE_RESTRICTED));

			groupParams.put("types", types);
			groupParams.put("active", Boolean.TRUE);
		}
		%>

		<liferay-ui:search-container-results>

			<%
			if (searchTerms.isAdvancedSearch()) {
				total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams, searchTerms.isAndOperator());

				searchContainer.setTotal(total);

				results = GroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
			}
			else {
				total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), groupParams);

				searchContainer.setTotal(total);

				results = GroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), groupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
			}

			searchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<aui:nav-bar>
			<aui:nav-bar-search>
				<%@ include file="/search.jspf" %>
			</aui:nav-bar-search>
		</aui:nav-bar>

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

				<c:if test='<%= !tabs1.equals("my-sites") && Validator.isNotNull(group.getDescription()) %>'>
					<br />

					<em><%= HtmlUtil.escape(group.getDescription()) %></em>
				</c:if>
			</liferay-ui:search-container-column-text>

			<%
			LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

			userParams.put("inherit", Boolean.TRUE);
			userParams.put("usersGroups", new Long(group.getGroupId()));
			%>

			<liferay-ui:search-container-column-text
				name="members"
				value="<%= String.valueOf(UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams)) %>"
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
				align="right"
				cssClass="entry-action"
				path="/site_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>