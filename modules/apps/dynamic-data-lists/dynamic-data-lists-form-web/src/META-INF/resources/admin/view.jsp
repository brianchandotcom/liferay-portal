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
PortletURL portletURL = ddlFormAdminDisplayContext.getPortletURL();
%>

<liferay-util:include page="/admin/search_bar.jsp" servletContext="<%= application %>" />

<div id="<portlet:namespace />formContainer" class="container-fluid-1280">
	
	<aui:form action="<%= portletURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteStructureIds" type="hidden" />
	
		<liferay-ui:search-container
			emptyResultsMessage="no-forms-were-found"
			id="searchContainer"
			searchContainer="<%= new RecordSetSearch(renderRequest, portletURL) %>"
		>
	
			<%
			request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
			%>
	
			<liferay-ui:search-container-results
				results="<%= ddlFormAdminDisplayContext.getSearchContainerResults(searchContainer) %>"
				total="<%= ddlFormAdminDisplayContext.getSearchContainerTotal(searchContainer) %>"
			/>
	
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecordSet"
				cssClass="entry-display-style"
				escapedModel="<%= true %>"
				keyProperty="recordSetId"
				modelVar="recordSet"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
				</portlet:renderURL>
	
				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="name"
					value="<%= recordSet.getName(locale) %>"
				/>
	
				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="description"
					value="<%= StringUtil.shorten(recordSet.getDescription(locale), 100) %>"
				/>
	
				<liferay-ui:search-container-column-date
					href="<%= rowURL %>"
					name="modified-date"
					value="<%= recordSet.getModifiedDate() %>"
				/>
	
				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="checkbox-cell entry-action"
					path="/admin/record_set_action.jsp"
				/>
			</liferay-ui:search-container-row>
	
			<liferay-ui:search-iterator view="lexicon"/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= ddlFormAdminDisplayContext.isShowAddRecordSetButton() %>">
	<portlet:renderURL var="addRecordSetURL">
		<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "new-form") %>' url="<%= addRecordSetURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>
