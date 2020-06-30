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
DDLRecordSet recordSet = ddlDisplayContext.getRecordSet();

if (recordSet != null) {
	renderResponse.setTitle(recordSet.getName(locale));
}
%>

<c:choose>
	<c:when test="<%= recordSet == null %>">
		<div class="alert alert-info">
			<liferay-ui:message key="select-an-existing-list-or-add-a-list-to-be-displayed-in-this-application" />
		</div>
	</c:when>
	<c:when test="<%= ddlDisplayContext.isFormView() %>">
		<liferay-util:include page="/edit_record.jsp" servletContext="<%= application %>">
			<liferay-util:param name="redirect" value="<%= currentURL %>" />
			<liferay-util:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
			<liferay-util:param name="formDDMTemplateId" value="<%= String.valueOf(ddlDisplayContext.getFormDDMTemplateId()) %>" />
		</liferay-util:include>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/view_record_set.jsp" servletContext="<%= application %>">
			<liferay-util:param name="mvcPath" value="/view_selected_record_set.jsp" />
			<liferay-util:param name="displayDDMTemplateId" value="<%= String.valueOf(ddlDisplayContext.getDisplayDDMTemplateId()) %>" />
			<liferay-util:param name="formDDMTemplateId" value="<%= String.valueOf(ddlDisplayContext.getFormDDMTemplateId()) %>" />
			<liferay-util:param name="editable" value="<%= String.valueOf(ddlDisplayContext.isEditable()) %>" />
			<liferay-util:param name="spreadsheet" value="<%= String.valueOf(ddlDisplayContext.isSpreadsheet()) %>" />
		</liferay-util:include>
	</c:otherwise>
</c:choose>

<%@ include file="/view_selected_record_set_options.jspf" %>