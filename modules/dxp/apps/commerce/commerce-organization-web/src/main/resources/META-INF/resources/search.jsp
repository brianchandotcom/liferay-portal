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
CommerceSearchOrganizationsDisplayContext commerceSearchOrganizationsDisplayContext = (CommerceSearchOrganizationsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="users-container" id="<portlet:namespace />entriesContainer">
	<liferay-ui:search-container
		id="organizations"
		searchContainer="<%= commerceSearchOrganizationsDisplayContext.getSearchContainer(locale) %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="result"
		>

			<%
			Organization curOrganization = null;
			User user2 = null;
			CommerceOrder commerceOrder = null;

			if (result instanceof Organization) {
				curOrganization = (Organization)result;
			}
			else if (result instanceof User) {
				user2 = (User)result;
			}
			else if (result instanceof CommerceOrder) {
				commerceOrder = (CommerceOrder)result;
			}
			%>

			<c:choose>
				<c:when test="<%= commerceOrder != null %>">
					<liferay-ui:search-container-column-text
						name="name"
						value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="type"
						value="Order"
					/>
				</c:when>
				<c:when test="<%= user2 != null %>">
					<liferay-ui:search-container-column-text
						name="name"
						value="<%= user2.getFullName() %>"
					/>

					<liferay-ui:search-container-column-text
						name="type"
						value="User"
					/>
				</c:when>
				<c:when test="<%= curOrganization != null %>">
					<liferay-ui:search-container-column-text
						name="name"
						value="<%= curOrganization.getName() %>"
					/>

					<liferay-ui:search-container-column-text
						name="type"
						value="Organization"
					/>
				</c:when>
			</c:choose>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/search_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= commerceSearchOrganizationsDisplayContext.getSearchContainer(locale) %>" />
	</liferay-ui:search-container>
</div>