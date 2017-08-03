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