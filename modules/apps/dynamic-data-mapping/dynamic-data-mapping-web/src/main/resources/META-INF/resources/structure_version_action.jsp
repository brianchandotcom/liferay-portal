<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructureVersion structureVersion = (DDMStructureVersion)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-portlet:renderURL portletName="<%= DDMPortletKeys.DYNAMIC_DATA_MAPPING %>" var="viewStructureVersionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		<portlet:param name="mvcPath" value="/view_structure.jsp" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="structureVersionId" value="<%= String.valueOf(structureVersion.getStructureVersionId()) %>" />
		<portlet:param name="formBuilderReadOnly" value="<%= String.valueOf(Boolean.TRUE) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="view[action]"
		url="<%= viewStructureVersionURL %>"
	/>

	<c:if test="<%= structureVersion.isApproved() %>">
		<portlet:actionURL name="/dynamic_data_mapping/revert_structure" var="revertURL">
			<portlet:param name="mvcPath" value="/edit_structure.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="structureId" value="<%= String.valueOf(structureVersion.getStructureId()) %>" />
			<portlet:param name="version" value="<%= structureVersion.getVersion() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="revert"
			url="<%= revertURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>