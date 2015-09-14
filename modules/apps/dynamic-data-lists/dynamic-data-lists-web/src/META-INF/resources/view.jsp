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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
%>
<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	
		<liferay-ui:search-container
			searchContainer="<%= new RecordSetSearch(renderRequest, portletURL) %>"
		>
	
			<%
			RecordSetDisplayTerms displayTerms = (RecordSetDisplayTerms)searchContainer.getDisplayTerms();
			RecordSetSearchTerms searchTerms = (RecordSetSearchTerms)searchContainer.getSearchTerms();
	
			request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
			%>
	
			<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />
	
			<liferay-ui:search-container-results>
				<%@ include file="/record_set_search_results.jspf" %>
			</liferay-ui:search-container-results>
	
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecordSet"
				escapedModel="<%= true %>"
				cssClass="entry-display-style"
				keyProperty="recordSetId"
				modelVar="recordSet"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcPath" value="/view_record_set.jsp" />
					<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
					<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
				</liferay-portlet:renderURL>
	
				<%
				if (!DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.VIEW)) {
					rowURL = null;
				}
				%>
	
				<%@ include file="/search_columns.jspf" %>
	
				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action"
					path="/record_set_action.jsp"
				/>
			</liferay-ui:search-container-row>
	
			<liferay-ui:search-iterator markupView="lexicon"/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= DDLPermission.contains(permissionChecker, scopeGroupId, DDLActionKeys.ADD_RECORD_SET) %>">
	<portlet:renderURL var="addRecordSetURL">
		<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addRecordSetURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<%@ include file="/export_record_set.jspf" %>