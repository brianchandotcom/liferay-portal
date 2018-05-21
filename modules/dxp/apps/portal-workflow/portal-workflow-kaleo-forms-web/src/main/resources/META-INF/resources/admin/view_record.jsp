<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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