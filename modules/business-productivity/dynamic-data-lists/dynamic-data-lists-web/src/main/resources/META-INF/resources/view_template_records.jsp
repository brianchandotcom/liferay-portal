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
DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long displayDDMTemplateId = ParamUtil.getLong(request, "displayDDMTemplateId");
long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

DDMStructure ddmStructure = recordSet.getDDMStructure();

boolean showAddRecordButton = false;

if (DDLUtil.isEditable(request, portletDisplay.getId(), themeDisplay.getScopeGroupId())) {
	showAddRecordButton = DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), DDLActionKeys.ADD_RECORD);
}

DDLDisplayTemplateTransformer ddlDisplayTemplateTransformer = new DDLDisplayTemplateTransformer(displayDDMTemplateId, recordSet, themeDisplay, renderRequest);
%>

<portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="addRecord" var="addRecordURL">
	<portlet:param name="mvcPath" value="/edit_record.jsp" />
</portlet:actionURL>

<aui:form action="<%= addRecordURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveRecordSet();" %>'>
	<c:if test="<%= showAddRecordButton %>">
		<aui:nav-bar>
			<aui:nav>
				<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="editRecordURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
					<portlet:param name="mvcPath" value="/edit_record.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
					<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
				</portlet:renderURL>

				<aui:nav-item href="<%= editRecordURL %>" iconCssClass="icon-plus" label='<%= LanguageUtil.format(request, "add-x", HtmlUtil.escape(ddmStructure.getName(locale))) %>' />
			</aui:nav>
		</aui:nav-bar>
	</c:if>

	<%= ddlDisplayTemplateTransformer.transform() %>
</aui:form>