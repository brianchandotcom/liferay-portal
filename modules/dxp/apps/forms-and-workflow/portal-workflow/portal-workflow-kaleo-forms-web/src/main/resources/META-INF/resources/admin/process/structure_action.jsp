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

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructure ddmStructure = (DDMStructure)row.getObject();

String backURL = (String)row.getParameter("backURL");
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-portlet:renderURL portletName="<%= PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.EDIT) %>" var="editURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/edit_structure.jsp" />
		<portlet:param name="navigationStartsOn" value="<%= DDMNavigationHelper.EDIT_STRUCTURE %>" />
		<portlet:param name="closeRedirect" value="<%= backURL %>" />
		<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
		<portlet:param name="refererPortletName" value="<%= KaleoFormsPortletKeys.KALEO_FORMS_ADMIN %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
		<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
		<portlet:param name="classPK" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		onClick='<%= "javascript:" + renderResponse.getNamespace() + "editStructure('" + LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "field-set"), false) + "','" + editURL + "');" %>'
		url="javascript:;"
	/>

	<liferay-ui:icon
		message="choose"
		onClick='<%= "Liferay.fire('" + renderResponse.getNamespace() + "chooseDefinition', {ddmStructureId: " + ddmStructure.getStructureId() + ", name: '" + HtmlUtil.escapeJS(ddmStructure.getName(locale)) + "', node: this});" %>'
		url="javascript:;"
	/>
</liferay-ui:icon-menu>