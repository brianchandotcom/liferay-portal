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

<%@ include file="/display/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDMFormInstance formInstance = ddmFormDisplayContext.getFormInstance();

DDMFormInstanceRecord formInstanceRecord = ddmFormDisplayContext.getFormInstanceRecord();

DDMFormInstanceRecordVersion formInstanceRecordVersion = null;

if (formInstanceRecord != null) {
	formInstanceRecordVersion = formInstanceRecord.getLatestFormInstanceRecordVersion();
}

portletDisplay.setURLBack(redirect);
portletDisplay.setShowBackIcon(true);

String title = ParamUtil.getString(request, "title");

renderResponse.setTitle(GetterUtil.get(title, LanguageUtil.get(request, "view-form")));
%>

<clay:container-fluid>
	<c:if test="<%= formInstanceRecordVersion != null %>">
		<aui:model-context bean="<%= formInstanceRecordVersion %>" model="<%= DDMFormInstanceRecordVersion.class %>" />

		<div class="panel text-center">
			<aui:workflow-status markupView="lexicon" model="<%= DDMFormInstanceRecord.class %>" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= formInstanceRecordVersion.getStatus() %>" version="<%= formInstanceRecordVersion.getVersion() %>" />
		</div>
	</c:if>
</clay:container-fluid>

<clay:container-fluid
	cssClass="ddm-form-builder-app editing-form-entry"
>
	<portlet:actionURL name="/dynamic_data_mapping_form/add_form_instance_record" var="editFormInstanceRecordActionURL" />

	<aui:form action="<%= editFormInstanceRecordActionURL %>" data-DDMFormInstanceId="<%= ddmFormDisplayContext.getFormInstanceId() %>" data-senna-off="true" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="formInstanceRecordId" type="hidden" value="<%= ddmFormDisplayContext.getFormInstanceRecordId() %>" />
		<aui:input name="formInstanceId" type="hidden" value="<%= ddmFormDisplayContext.getFormInstanceId() %>" />
		<aui:input name="defaultLanguageId" type="hidden" value='<%= ParamUtil.getString(request, "defaultLanguageId") %>' />

		<div class="ddm-form-basic-info">

			<%
			String languageId = ddmFormDisplayContext.getDefaultLanguageId();

			Locale displayLocale = LocaleUtil.fromLanguageId(languageId);
			%>

			<h1 class="ddm-form-name"><%= HtmlUtil.escape(formInstance.getName(displayLocale)) %></h1>

			<%
			String description = HtmlUtil.escape(formInstance.getDescription(displayLocale));
			%>

			<c:if test="<%= Validator.isNotNull(description) %>">
				<h5 class="ddm-form-description"><%= description %></h5>
			</c:if>
		</div>

		<react:component
			module="admin/js/index.es"
			props="<%= ddmFormDisplayContext.getDDMFormReactData() %>"
		/>
	</aui:form>
</clay:container-fluid>