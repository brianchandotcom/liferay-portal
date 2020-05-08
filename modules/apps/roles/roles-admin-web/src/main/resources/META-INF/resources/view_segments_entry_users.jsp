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
long segmentsEntryId = ParamUtil.getLong(request, "segmentsEntryId");
%>

<clay:container
	size="sm"
>
	<liferay-ui:search-container
		emptyResultsMessage="no-users-have-been-assigned-to-this-segment"
		iteratorURL="<%= currentURLObj %>"
		total="<%= SegmentsEntryDisplayContext.getSegmentsEntryUsersCount(segmentsEntryId) %>"
		var="segmentsEntryUsersSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= SegmentsEntryDisplayContext.getSegmentsEntryUsers(segmentsEntryId, segmentsEntryUsersSearchContainer.getStart(), segmentsEntryUsersSearchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
		>
			<liferay-ui:search-container-column-text>
				<liferay-ui:user-portrait
					userId="<%= user2.getUserId() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
				cssClass="justify-content-center"
				value="<%= user2.getFullName() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container>