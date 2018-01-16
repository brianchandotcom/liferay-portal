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

<%@ include file="/designer/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

KaleoDefinitionVersion currentKaleoDefinitionVersion = (KaleoDefinitionVersion)request.getAttribute(KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)row.getObject();
%>

<portlet:renderURL var="viewURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/designer/view_kaleo_definition_version.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
	<portlet:param name="draftVersion" value="<%= kaleoDefinitionVersion.getVersion() %>" />
</portlet:renderURL>

<liferay-portlet:actionURL name="revertKaleoDefinitionVersion" var="revertURL">
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
	<portlet:param name="draftVersion" value="<%= kaleoDefinitionVersion.getVersion() %>" />
</liferay-portlet:actionURL>

<c:if test="<%= !kaleoDefinitionVersion.getVersion().equals(currentKaleoDefinitionVersion.getVersion()) %>">
	<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" id='<%= "iconMenu_" + kaleoDefinitionVersion.getVersion() %>' markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
		<liferay-ui:icon
			id='<%= "previewBeforeRevert" + kaleoDefinitionVersion.getVersion() %>'
			message="preview"
			url="javascript:;"
		/>

		<liferay-ui:icon
			message="restore"
			url="<%= revertURL %>"
		/>
	</liferay-ui:icon-menu>
</c:if>

<aui:script use="liferay-kaleo-designer-utils">
	var title = '<liferay-ui:message key="preview"/>';

	var previewBeforeRevert = A.rbind('previewBeforeRevert', Liferay.KaleoDesignerUtils, '<%= viewURL %>', '<%= revertURL %>', title);

	Liferay.delegateClick('<portlet:namespace />previewBeforeRevert<%= kaleoDefinitionVersion.getVersion() %>', previewBeforeRevert);
</aui:script>