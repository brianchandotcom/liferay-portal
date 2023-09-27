<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructure ddmStructure = (DDMStructure)row.getObject();
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
		<portlet:param name="closeRedirect" value='<%= (String)row.getParameter("backURL") %>' />
		<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
		<portlet:param name="refererPortletName" value="<%= KaleoFormsPortletKeys.KALEO_FORMS_ADMIN %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
		<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
		<portlet:param name="classPK" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "editStructure('" + LanguageUtil.format(request, "edit-x", LanguageUtil.get(request, "field-set"), false) + "','" + editURL + "');" %>'
		url="javascript:void(0);"
	/>

	<liferay-ui:icon
		message="choose"
		onClick='<%= "Liferay.fire('" + liferayPortletResponse.getNamespace() + "chooseDefinition', {ddmStructureId: " + ddmStructure.getStructureId() + ", name: '" + HtmlUtil.escapeJS(ddmStructure.getName(locale)) + "', node: this});" %>'
		url="javascript:void(0);"
	/>
</liferay-ui:icon-menu>