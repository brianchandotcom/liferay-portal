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

String displayStyle = ddlDisplayContext.getDDLRecordSetDisplayStyle();

RecordSetSearch recordSetSearch = new RecordSetSearch(renderRequest, portletURL);

String orderByCol = ParamUtil.getString(request, "orderByCol", "modified-date");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

OrderByComparator<DDLRecordSet> orderByComparator = DDLPortletUtil.getDDLRecordSetOrderByComparator(orderByCol, orderByType);

recordSetSearch.setOrderByCol(orderByCol);
recordSetSearch.setOrderByComparator(orderByComparator);
recordSetSearch.setOrderByType(orderByType);
%>

<liferay-util:include page="/search_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

		<liferay-ui:search-container
			searchContainer="<%= recordSetSearch %>"
		>

			<%
			RecordSetDisplayTerms displayTerms = (RecordSetDisplayTerms)searchContainer.getDisplayTerms();
			RecordSetSearchTerms searchTerms = (RecordSetSearchTerms)searchContainer.getSearchTerms();

			request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
			%>

			<liferay-ui:search-container-results>
				<%@ include file="/record_set_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecordSet"
				cssClass="entry-display-style"
				escapedModel="<%= true %>"
				keyProperty="recordSetId"
				modelVar="recordSet"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcPath" value="/view_record_set.jsp" />
					<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
					<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
					<portlet:param name="displayStyle" value="<%= displayStyle %>" />
				</liferay-portlet:renderURL>

				<%
				if (!DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.VIEW)) {
					rowURL = null;
				}
				%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>

						<liferay-ui:search-container-column-image
							src='<%= themeDisplay.getPathThemeImages() + "/file_system/large/article.png" %>'
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="2"
							href="<%= rowURL %>"
							path="/view_record_set_descriptive.jsp"
						/>

						<liferay-ui:search-container-column-jsp
							path="/record_set_action.jsp"
						/>
					</c:when>
					<c:otherwise>

						<%@ include file="/search_columns.jspf" %>

						<liferay-ui:search-container-column-jsp
							align="right"
							cssClass="entry-action"
							path="/record_set_action.jsp"
						/>
					</c:otherwise>
				</c:choose>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
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