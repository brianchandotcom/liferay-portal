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
String toolbarItem = ParamUtil.getString(request, "toolbar-item", "view-all");
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewApplicationsURL">
		<portlet:param name="mvcPath" value="/admin/view.jsp" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<aui:a href="<%= viewApplicationsURL %>" label='<%= permissionChecker.isCompanyAdmin() ? "view-all" : "my-applications" %>' />
	</span>

	<c:if test="<%= OAuthPermission.contains(permissionChecker, OAuthActionKeys.ADD_APPLICATION) %>">
		<portlet:renderURL var="addApplicationURL">
			<portlet:param name="mvcPath" value="/admin/edit_application.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add") ? "current" : StringPool.BLANK %>">
			<aui:a href="<%= addApplicationURL %>" label="add" />
		</span>
	</c:if>
</div>