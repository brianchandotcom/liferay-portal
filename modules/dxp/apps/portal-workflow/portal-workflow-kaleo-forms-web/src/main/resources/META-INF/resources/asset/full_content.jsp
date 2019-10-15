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

<%@ include file="/asset/init.jsp" %>

<%
DDLRecord ddlRecord = (DDLRecord)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

DDLRecordVersion ddlRecordVersion = (DDLRecordVersion)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_VERSION);

KaleoProcessLink kaleoProcessLink = (KaleoProcessLink)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS_LINK);
%>

<div class="container-fluid-1280 main-content-body">
	<aui:fieldset>

		<%
		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);
		long classPK = ddlRecordSet.getDDMStructureId();

		if (kaleoProcessLink != null) {
			classNameId = PortalUtil.getClassNameId(DDMTemplate.class);
			classPK = kaleoProcessLink.getDDMTemplateId();
		}

		DDMFormValues ddmFormValues = null;

		if (ddlRecordVersion != null) {
			ddmFormValues = ddlRecordVersion.getDDMFormValues();
		}
		%>

		<liferay-ddm:html
			classNameId="<%= classNameId %>"
			classPK="<%= classPK %>"
			ddmFormValues="<%= ddmFormValues %>"
			readOnly="<%= true %>"
			requestedLocale="<%= locale %>"
		/>
	</aui:fieldset>
</div>