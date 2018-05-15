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
PortletURL portletURL = ddlDisplayContext.getPortletURL();

String displayStyle = ddlDisplayContext.getDisplayStyle();
%>

<liferay-util:include page="/navigation_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/management_bar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="recordSetIds" type="hidden" />

		<liferay-ui:search-container
			id="ddlRecordSet"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= ddlDisplayContext.getSearch() %>"
		>

			<%
			request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
			%>

			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecordSet"
				cssClass="entry-display-style"
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
						<liferay-ui:search-container-column-text>
							<liferay-ui:user-portrait
								userId="<%= recordSet.getUserId() %>"
							/>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							colspan="<%= 2 %>"
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
							path="/record_set_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<%@ include file="/export_record_set.jspf" %>