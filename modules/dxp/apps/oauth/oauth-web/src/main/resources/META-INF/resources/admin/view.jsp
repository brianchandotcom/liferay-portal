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

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="mvcPath" value="/admin/view.jsp" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />

	<liferay-util:include page="/admin/toolbar.jsp" servletContext="<%= application %>">
		<liferay-util:param name="toolbar-item" value="view-all" />
	</liferay-util:include>

	<liferay-ui:search-container
		searchContainer="<%= new OAuthApplicationSearch(renderRequest, currentURLObj) %>"
	>
		<liferay-ui:search-form
			page="/admin/search_application.jsp"
			servletContext="<%= application %>"
		/>

		<%
		OAuthApplicationDisplayTerms displayTerms = (OAuthApplicationDisplayTerms)searchContainer.getDisplayTerms();

		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		if (!permissionChecker.isCompanyAdmin()) {
			params.put("userId", Long.valueOf(themeDisplay.getUserId()));
		}

		searchContainer.setTotal(OAuthApplicationLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), displayTerms.getName(), params));
		searchContainer.setResults(OAuthApplicationLocalServiceUtil.search(themeDisplay.getCompanyId(), displayTerms.getName(), params, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()));
		%>

		<liferay-ui:search-container-row
			className="com.liferay.oauth.model.OAuthApplication"
			modelVar="oAuthApplication"
		>
			<liferay-portlet:renderURL var="rowHREF">
				<portlet:param name="mvcPath" value='<%= permissionChecker.isCompanyAdmin() ? "/admin/view_application.jsp" : "/admin/edit_application.jsp" %>' />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="oAuthApplicationId" value="<%= String.valueOf(oAuthApplication.getOAuthApplicationId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="id"
				orderable="<%= true %>"
				value="<%= String.valueOf(oAuthApplication.getOAuthApplicationId()) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				orderable="<%= true %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="website-url"
				property="websiteURL"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="access-type"
				translate="<%= true %>"
				value="<%= oAuthApplication.getAccessLevelLabel() %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="number-of-authorizations"
				value="<%= String.valueOf(OAuthUserLocalServiceUtil.getOAuthApplicationOAuthUsersCount(oAuthApplication.getOAuthApplicationId())) %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				cssClass="entry-action"
				path="/admin/application_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>