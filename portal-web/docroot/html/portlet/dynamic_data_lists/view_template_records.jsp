<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
DDLRecordSet recordSet = null;

if (Validator.isNotNull(recordSetId)) {
	recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(recordSetId);
}
%>

<portlet:actionURL var="editRecordSetURL">
	<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
</portlet:actionURL>

<aui:form action="<%= editRecordSetURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveRecordSet();" %>'>
	<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), ActionKeys.ADD_RECORD) && editable %>">
		<aui:button onClick='<%= renderResponse.getNamespace() + "addRecord();" %>' value="add-record" />

		<div class="separator"><!-- --></div>
	</c:if>

	<%= DDLUtil.getTemplateContent(displayDDMTemplateId, recordSet, themeDisplay, renderRequest, renderResponse) %>

</aui:form>

<aui:script>
	function <portlet:namespace />addRecord() {
		submitForm(document.<portlet:namespace />fm, '<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="backURL" value="<%= currentURL %>" /><portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" /><portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" /></liferay-portlet:renderURL>');
	}
</aui:script>