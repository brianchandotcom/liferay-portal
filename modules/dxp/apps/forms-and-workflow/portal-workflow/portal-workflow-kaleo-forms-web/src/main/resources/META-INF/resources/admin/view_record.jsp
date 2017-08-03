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

long ddlRecordId = ParamUtil.getLong(request, "ddlRecordId");

DDLRecord ddlRecord = DDLRecordServiceUtil.getRecord(ddlRecordId);

DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

KaleoProcess kaleoProcess = (KaleoProcess)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS);

long kaleoProcessId = BeanParamUtil.getLong(kaleoProcess, request, "kaleoProcessId");

String version = ParamUtil.getString(request, "version", DDLRecordConstants.VERSION_DEFAULT);

DDLRecordVersion ddlRecordVersion = ddlRecord.getRecordVersion(version);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.format(request, "view-x", kaleoProcess.getName(locale), false));
%>

<div class="container-fluid-1280">
	<c:if test="<%= ddlRecordVersion != null %>">
		<aui:model-context bean="<%= ddlRecordVersion %>" model="<%= DDLRecordVersion.class %>" />

		<div class="panel text-center">
			<aui:workflow-status markupView="lexicon" model="<%= DDLRecord.class %>" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= ddlRecordVersion.getStatus() %>" version="<%= ddlRecordVersion.getVersion() %>" />
		</div>
	</c:if>

	<aui:fieldset>

		<%
		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DDMFormValues ddmFormValues = null;

		if (ddlRecordVersion != null) {
			ddmFormValues = kaleoFormsAdminDisplayContext.getDDMFormValues(ddlRecordVersion.getDDMStorageId());
		}
		%>

		<div class="panel">
			<liferay-ddm:html
				classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
				classPK="<%= ddmStructure.getStructureId() %>"
				ddmFormValues="<%= ddmFormValues %>"
				readOnly="<%= true %>"
				requestedLocale="<%= locale %>"
			/>
		</div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancelButton" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</div>