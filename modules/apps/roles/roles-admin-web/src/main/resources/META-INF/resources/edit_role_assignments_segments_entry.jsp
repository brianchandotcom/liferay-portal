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
SearchContainer searchContainer = (SearchContainer)request.getAttribute("edit_role_assignments.jsp-searchContainer");
%>

<liferay-ui:search-container
	id="assigneesSearch"
	searchContainer="<%= searchContainer %>"
	var="segmentsEntrySearchContainer"
>
	<liferay-ui:search-container-row
		className="com.liferay.segments.model.SegmentsEntry"
		keyProperty="segmentsEntryId"
		modelVar="segmentsEntry"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-title"
			name="name"
			value="<%= HtmlUtil.escape(segmentsEntry.getName(locale)) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-smallest table-cell-minw-150"
			name="active"
			value='<%= segmentsEntry.getActive() ? "yes" : "no" %>'
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-smallest table-cell-minw-150"
			name="source"
			value="<%= segmentsEntry.getSource() %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-smallest table-cell-minw-150"
			name="scope"
			value="<%= HtmlUtil.escape(SegmentsEntryDisplayContext.getGroupDescriptiveName(segmentsEntry, locale)) %>"
		/>

		<liferay-ui:search-container-column-date
			cssClass="table-cell-expand-smallest table-cell-minw-150 table-cell-ws-nowrap"
			name="create-date"
			value="<%= segmentsEntry.getCreateDate() %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-smallest table-cell-minw-150"
			name="members"
			value="<%= SegmentsEntryDisplayContext.getMembersCount(segmentsEntry.getSegmentsEntryId()) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>