<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "all");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Country country = (Country)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.MANAGE_COUNTRIES) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/address/edit_country" />
			<portlet:param name="backURL" value='<%= ParamUtil.getString(request, "backURL") %>' />
			<portlet:param name="countryId" value="<%= String.valueOf(country.getCountryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<c:choose>
			<c:when test="<%= country.isActive() %>">
				<portlet:actionURL name="/address/update_country_status" var="deactivateCountryURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DEACTIVATE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="navigation" value="<%= navigation %>" />
					<portlet:param name="countryIds" value="<%= String.valueOf(country.getCountryId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-deactivate
					url="<%= deactivateCountryURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL name="/address/update_country_status" var="activateCountryURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="navigation" value="<%= navigation %>" />
					<portlet:param name="countryIds" value="<%= String.valueOf(country.getCountryId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="activate"
					url="<%= activateCountryURL %>"
				/>
			</c:otherwise>
		</c:choose>

		<portlet:actionURL name="/address/delete_country" var="deleteCountryURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="countryIds" value="<%= String.valueOf(country.getCountryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteCountryURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>