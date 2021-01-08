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

DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion = ddmFormAdminDisplayContext.getDDMFormInstanceRecordVersion();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "view-form"));
%>

<clay:container-fluid>
	<c:if test="<%= ddmFormInstanceRecordVersion != null %>">
		<aui:model-context bean="<%= ddmFormInstanceRecordVersion %>" model="<%= DDMFormInstanceRecordVersion.class %>" />

		<div class="panel text-center">
			<aui:workflow-status markupView="lexicon" model="<%= DDMFormInstanceRecord.class %>" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= ddmFormInstanceRecordVersion.getStatus() %>" version="<%= ddmFormInstanceRecordVersion.getVersion() %>" />
		</div>
	</c:if>
</clay:container-fluid>

<clay:container-fluid
	cssClass="editing-form-entry-admin"
>
	<portlet:actionURL name="/dynamic_data_mapping_form/add_form_instance_record" var="editFormInstanceRecordActionURL" />

	<aui:form action="<%= editFormInstanceRecordActionURL %>" data-DDMFormInstanceId="<%= ddmFormInstanceRecordVersion.getFormInstanceId() %>" data-senna-off="true" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="formInstanceRecordId" type="hidden" value="<%= ddmFormInstanceRecordVersion.getFormInstanceRecordId() %>" />
		<aui:input name="formInstanceId" type="hidden" value="<%= ddmFormInstanceRecordVersion.getFormInstanceId() %>" />

		<%
		DDMFormValues ddmFormValues = ddmFormInstanceRecordVersion.getDDMFormValues();
		%>

		<aui:input name="defaultLanguageId" type="hidden" value="<%= LocaleUtil.toLanguageId(ddmFormValues.getDefaultLocale()) %>" />

		<div class="ddm-form-basic-info">
			<h1 class="ddm-form-name"><%= ddmFormAdminDisplayContext.getFormName() %></h1>

			<%
			String description = ddmFormAdminDisplayContext.getFormDescription();
			%>

			<c:if test="<%= Validator.isNotNull(description) %>">
				<h5 class="ddm-form-description"><%= description %></h5>
			</c:if>
		</div>

		<react:component
			module="admin/js/index.es"
			props="<%= ddmFormAdminDisplayContext.getDDMFormReactData(renderRequest, false) %>"
		/>
	</aui:form>
</clay:container-fluid>