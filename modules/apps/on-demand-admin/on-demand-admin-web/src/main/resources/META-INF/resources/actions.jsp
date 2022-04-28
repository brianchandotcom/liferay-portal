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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Company rowObjectCompany = (Company)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= (rowObjectCompany.getCompanyId() != PortalUtil.getDefaultCompanyId()) && PortletPermissionUtil.contains(permissionChecker, 0, 0, OnDemandAdminPortletKeys.ON_DEMAND_ADMIN, OnDemandAdminActionKeys.REQUEST_ADMINISTRATOR_ACCESS, true) %>">
		<portlet:renderURL var="dialogURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/justification.jsp" />
			<portlet:param name="companyId" value="<%= String.valueOf(rowObjectCompany.getCompanyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			id="requestAdminAccessLink"
			message="request-administrator-access"
			onClick='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "openModal(event);" %>'
			url="<%= dialogURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>

<aui:script>
	function <portlet:namespace />openModal(event) {
		Liferay.Util.openModal({
			disableAutoClose: true,
			height: '60vh',
			id: '<portlet:namespace />requestAdminAccessDialog',
			iframeBodyCssClass: '',
			size: 'md',
			title: '<liferay-ui:message key="request-administrator-access" />',
			url: event.currentTarget.href,
		});
	}
</aui:script>