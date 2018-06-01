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

<c:choose>
	<c:when test="<%= hasAddSourcePermission && reportsEngineDisplayContext.isSourcesTabSelected() %>">
		<liferay-ui:search-form
			page="/admin/data_source/source_search.jsp"
			servletContext="<%= application %>"
		/>
	</c:when>
	<c:when test="<%= hasAddDefinitionPermission && reportsEngineDisplayContext.isDefinitionsTabSelected() %>">
		<liferay-ui:search-form
			page="/admin/definition/definition_search.jsp"
			servletContext="<%= application %>"
		/>
	</c:when>
	<c:otherwise>
		<liferay-ui:search-form
			page="/admin/report/entry_search.jsp"
			servletContext="<%= application %>"
		/>
	</c:otherwise>
</c:choose>