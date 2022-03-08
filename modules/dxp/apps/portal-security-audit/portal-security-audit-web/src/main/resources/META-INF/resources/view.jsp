<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:renderURL varImpl="searchURL" />

<clay:container-fluid
	cssClass="container-view"
>

	<%
	AuditDisplayContext auditDisplayContext = new AuditDisplayContext(request, liferayPortletRequest, liferayPortletResponse, renderRequest, timeZone);
	%>

	<aui:form action="<%= searchURL %>" method="get" name="fm">
		<liferay-portlet:renderURLParams varImpl="searchURL" />

		<liferay-ui:search-container
			searchContainer="<%= auditDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-form
				page="/event_search.jsp"
				servletContext="<%= application %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.security.audit.AuditEvent"
				escapedModel="<%= true %>"
				keyProperty="auditEventId"
				modelVar="event"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcPath" value="/view_audit_event.jsp" />
					<portlet:param name="auditEventId" value="<%= String.valueOf(event.getAuditEventId()) %>" />
				</liferay-portlet:renderURL>

				<%@ include file="/search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<div class="separator"><!-- --></div>

			<liferay-ui:search-iterator
				searchContainer="<%= auditDisplayContext.getSearchContainer() %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>