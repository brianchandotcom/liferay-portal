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
		<portlet:actionURL name="revertStructure" var="revertURL">
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