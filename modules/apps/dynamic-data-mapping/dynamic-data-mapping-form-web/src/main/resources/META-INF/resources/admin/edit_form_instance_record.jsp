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
String redirect = ParamUtil.getString(request, "redirect");

DDMFormInstanceRecordVersion formInstanceRecordVersion = ddmFormAdminDisplayContext.getDDMFormInstanceRecordVersion();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

String title = ParamUtil.getString(request, "title");

renderResponse.setTitle(GetterUtil.get(title, LanguageUtil.get(request, "view-form")));
%>

<div class="portlet-forms">
	<div class="ddm-form-basic-info ddm-form-success-page">
		<div class="container-fluid-1280">
			<h1 class="ddm-form-name"><%= ddmFormAdminDisplayContext.getFormName() %></h1>

			<h5 class="ddm-form-description"><%= ddmFormAdminDisplayContext.getFormDescription() %></h5>
		</div>
	</div>
</div>

<div class="container-fluid-1280 ddm-form-builder-app editing-form-entry">
	<portlet:actionURL name="addFormInstanceRecord" var="editFormInstanceRecordActionURL" />

	<aui:form action="<%= editFormInstanceRecordActionURL %>" data-DDMFormInstanceId="<%= formInstanceRecordVersion.getFormInstanceId() %>" data-senna-off="true" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="formInstanceId" type="hidden" value="<%= formInstanceRecordVersion.getFormInstanceId() %>" />
		<aui:input name="formInstanceRecordId" type="hidden" value="<%= formInstanceRecordVersion.getFormInstanceRecordId() %>" />
		<%= ddmFormAdminDisplayContext.getDDMFormHTML(renderRequest) %>
	</aui:form>
</div>