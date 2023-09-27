<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset/init.jsp" %>

<%
DDLRecord ddlRecord = (DDLRecord)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

DDLRecordVersion ddlRecordVersion = (DDLRecordVersion)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_VERSION);

KaleoProcessLink kaleoProcessLink = (KaleoProcessLink)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS_LINK);
%>

<clay:container-fluid
	cssClass="main-content-body mt-4"
>
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
</clay:container-fluid>