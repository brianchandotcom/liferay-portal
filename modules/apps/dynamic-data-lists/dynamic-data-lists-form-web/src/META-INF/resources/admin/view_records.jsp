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

<%@ include file="/admin/init.jsp" %>

<%
DDLFormViewRecordsDisplayContext ddlFormViewRecordsDisplayContext = new DDLFormViewRecordsDisplayContext(liferayPortletRequest, liferayPortletResponse, ddlFormAdminDisplayContext.getRecordSet());
%>

<aui:form action="<%= currentURL.toString() %>" method="post" name="fm">
	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<aui:nav-bar-search>
			<liferay-ui:input-search autoFocus="<%= true %>" markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:nav-bar>
</aui:form>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-util:include page="/admin/view_records_sort_button.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />viewEntriesContainer">
	<liferay-ui:search-container searchContainer="<%= ddlFormViewRecordsDisplayContext.getRecordSearchContainer() %>">

		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.lists.model.DDLRecord"
			cssClass="entry-display-style selectable"
			modelVar="record"
		>

			<%
			DDMFormValues ddmFormValues = ddlFormViewRecordsDisplayContext.getDDMFormValues(record);

			for (int i = 0; i < ddlFormViewRecordsDisplayContext.getTotalColumns(); i++) {
			%>

				<liferay-ui:search-container-column-text
					name="<%= ddlFormViewRecordsDisplayContext.getColumnName(i, ddmFormValues) %>"
					value="<%= ddlFormViewRecordsDisplayContext.getColumnValue(i, ddmFormValues) %>"
				/>

			<%
			}
			%>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= record.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/admin/record_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= ddlFormViewRecordsDisplayContext.getDisplayStyle() %>" markupView="lexicon" paginate="<%= false %>" searchContainer="<%= ddlFormViewRecordsDisplayContext.getRecordSearchContainer() %>" />

	</liferay-ui:search-container>
</div>

<div class="container-fluid-1280">
	<liferay-ui:search-paginator searchContainer="<%= ddlFormViewRecordsDisplayContext.getRecordSearchContainer() %>" />
</div>