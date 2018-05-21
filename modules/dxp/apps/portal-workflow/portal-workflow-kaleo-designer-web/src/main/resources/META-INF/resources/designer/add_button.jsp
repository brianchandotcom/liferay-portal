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

<%@ include file="/designer/init.jsp" %>

<c:if test="<%= kaleoDesignerDisplayContext.isSaveKaleoDefinitionVersionButtonVisible(permissionChecker, null) %>">
	<liferay-portlet:renderURL portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="editKaleoDefinitionVersionURL">
		<portlet:param name="mvcPath" value='<%= "/designer/edit_kaleo_definition_version.jsp" %>' />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="clearSessionMessage" value="true" />
	</liferay-portlet:renderURL>

	<liferay-frontend:add-menu
		inline="<%= true %>"
	>
		<liferay-frontend:add-menu-item
			title='<%= LanguageUtil.get(request, "new-workflow") %>'
			url="<%= editKaleoDefinitionVersionURL.toString() %>"
		/>
	</liferay-frontend:add-menu>
</c:if>