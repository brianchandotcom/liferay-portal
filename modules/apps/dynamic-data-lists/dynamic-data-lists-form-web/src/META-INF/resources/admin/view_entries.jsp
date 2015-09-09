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
DDLFormViewEntriesDisplayContext ddlFormViewEntriesDisplayContext = new DDLFormViewEntriesDisplayContext(liferayPortletRequest, liferayPortletResponse, ddlFormAdminDisplayContext.getDDMStructure(), ddlFormAdminDisplayContext.getRecordSet());

DisplayTerms displayTerms = ddlFormViewEntriesDisplayContext.getRecordSearchContainer().getDisplayTerms();
%>

<aui:form action="<%= currentURL.toString() %>" method="post" name="fm">
	<aui:nav-bar cssClass="collapse-basic-search" view="lexicon">
		<aui:nav-bar-search>
			<liferay-ui:input-search autoFocus="<%= true %>" view="lexicon" />
		</aui:nav-bar-search>
	</aui:nav-bar>
</aui:form>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-util:include page="/admin/view_entries_sort_button.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />viewEntriesContainer">
	<liferay-ui:search-container searchContainer="<%= ddlFormViewEntriesDisplayContext.getRecordSearchContainer() %>">

		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.lists.model.DDLRecord"
			cssClass="entry-display-style selectable"
			modelVar="ddlRecord">

			<%
			DDMFormValues ddmFormValues = ddlFormViewEntriesDisplayContext.getDDMFormValues(ddlRecord);

			for (int i = 0; i < ddlFormViewEntriesDisplayContext.getTotalColumns(); i++) {
			%>

				<liferay-ui:search-container-column-text
					name="<%= ddlFormViewEntriesDisplayContext.getColumnName(i, ddmFormValues) %>"
					value="<%= ddlFormViewEntriesDisplayContext.getColumnValue(i, ddmFormValues) %>"
				/>

			<%
			}
			%>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= ddlRecord.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/admin/view_entries_action.jsp"
			/>

		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= ddlFormViewEntriesDisplayContext.getDisplayStyle() %>" paginate="<%= false %>" searchContainer="<%= ddlFormViewEntriesDisplayContext.getRecordSearchContainer() %>" view="lexicon" />

	</liferay-ui:search-container>
</div>

<div class="container-fluid-1280">
	<liferay-ui:search-paginator searchContainer="<%= ddlFormViewEntriesDisplayContext.getRecordSearchContainer() %>" />
</div>