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
DDMFormInstanceReport ddmFormInstanceReport = ddmFormReportDisplayContext.getDDMFormInstanceReport();

String ddmFormInstanceReportData = StringPool.BLANK;

if (ddmFormInstanceReport != null) {
	ddmFormInstanceReportData = ddmFormInstanceReport.getData();
}
%>

<div id="<portlet:namespace />report">
	<react:component
		module="js/index.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"data", ddmFormInstanceReportData
			).put(
				"fields", ddmFormReportDisplayContext.getFieldsJSONArray()
			).put(
				"formReportRecordsFieldValuesURL", ddmFormReportDisplayContext.getFormReportRecordsFieldValuesURL()
			).put(
				"portletNamespace", PortalUtil.getPortletNamespace(DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_REPORT)
			).build()
		%>'
	/>
</div>