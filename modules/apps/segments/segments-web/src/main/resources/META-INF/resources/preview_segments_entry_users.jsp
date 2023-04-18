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
PreviewSegmentsEntryUsersDisplayContext previewSegmentsEntryUsersDisplayContext = (PreviewSegmentsEntryUsersDisplayContext)request.getAttribute(PreviewSegmentsEntryUsersDisplayContext.class.getName());

SearchContainer<User> userSearchContainer = previewSegmentsEntryUsersDisplayContext.getSearchContainer();
%>

<clay:container-fluid>
	<c:choose>
		<c:when test="<%= userSearchContainer.getTotal() > 0 %>">
			<liferay-ui:search-container
				searchContainer="<%= userSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.User"
					escapedModel="<%= true %>"
					keyProperty="userId"
					modelVar="user2"
					rowIdProperty="screenName"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200 table-title"
						name="name"
						orderable="<%= true %>"
						property="fullName"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200"
						name="email-address"
						orderable="<%= true %>"
						property="emailAddress"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</c:when>
		<c:otherwise>
			<clay:alert
				cssClass="c-mt-5"
				displayType="info"
				message="<%= userSearchContainer.getEmptyResultsMessage() %>"
			/>
		</c:otherwise>
	</c:choose>
</clay:container-fluid>