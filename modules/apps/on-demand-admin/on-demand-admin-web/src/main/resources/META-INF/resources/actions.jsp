<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<c:if test="<%= rowObjectCompany.isActive() && (rowObjectCompany.getCompanyId() != PortalUtil.getDefaultCompanyId()) && PortletPermissionUtil.contains(permissionChecker, 0, 0, OnDemandAdminPortletKeys.ON_DEMAND_ADMIN, OnDemandAdminActionKeys.REQUEST_ADMINISTRATOR_ACCESS, true) %>">
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