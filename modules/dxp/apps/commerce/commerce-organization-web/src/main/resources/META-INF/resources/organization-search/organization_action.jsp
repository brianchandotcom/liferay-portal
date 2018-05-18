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

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Organization organization = (Organization)row.getObject();
%>

<portlet:actionURL name="setCurrentOrganization" var="setCurrentOrganizationURL">
	<portlet:param name="currentOrganizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
</portlet:actionURL>

<aui:button cssClass="btn-secondary" href="<%= setCurrentOrganizationURL %>" value='<%= LanguageUtil.get(resourceBundle, "select") %>' />