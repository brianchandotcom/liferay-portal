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

<%@ include file="/html/portlet/dynamic_data_list_display/init.jsp" %>

<%
DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

DDMStructure ddmStructure = recordSet.getDDMStructure();
%>

<portlet:actionURL var="editRecordSetURL">
	<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
</portlet:actionURL>

<aui:form action="<%= editRecordSetURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveRecordSet();" %>'>
		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), ActionKeys.ADD_RECORD) && editable %>">
					<portlet:renderURL var="addRecordURL">
						<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
						<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
					</portlet:renderURL>

					<aui:nav-item href="<%= addRecordURL %>" iconCssClass="icon-plus" label='<%= LanguageUtil.format(request, "add-x", HtmlUtil.escape(ddmStructure.getName(locale)), false) %>' />
				</c:if>

				<portlet:resourceURL var="exportRecordSetURL">
					<portlet:param name="struts_action" value="/dynamic_data_lists/export" />
					<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
				</portlet:resourceURL>

				<%
				StringBundler sb = new StringBundler(6);

				sb.append("javascript:");
				sb.append(renderResponse.getNamespace());
				sb.append("exportRecordSet");
				sb.append("('");
				sb.append(exportRecordSetURL);
				sb.append("');");
				%>

				<aui:nav-item href="<%= sb.toString() %>" iconCssClass="icon-arrow-down" label="export" />
			</aui:nav>
		</aui:nav-bar>

	<%= DDLUtil.getTemplateContent(displayDDMTemplateId, recordSet, themeDisplay, renderRequest, renderResponse) %>
</aui:form>