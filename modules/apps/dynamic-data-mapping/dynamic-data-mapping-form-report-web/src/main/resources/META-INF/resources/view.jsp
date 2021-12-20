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

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.dynamic.data.mapping.constants.DDMPortletKeys" %><%@
page import="com.liferay.dynamic.data.mapping.form.report.web.internal.display.context.DDMFormReportDisplayContext" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
DDMFormReportDisplayContext ddmFormReportDisplayContext = (DDMFormReportDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String ddmFormInstanceReportData = StringPool.BLANK;

DDMFormInstanceReport ddmFormInstanceReport = ddmFormReportDisplayContext.getDDMFormInstanceReport();

if (ddmFormInstanceReport != null) {
	ddmFormInstanceReportData = ddmFormInstanceReport.getData();
}
%>

<react:component
	module="js/index"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"data", ddmFormInstanceReportData
		).put(
			"fields", ddmFormReportDisplayContext.getFieldsJSONArray()
		).put(
			"formReportRecordsFieldValuesURL", ddmFormReportDisplayContext.getFormReportRecordsFieldValuesURL()
		).put(
			"lastModifiedDate", ddmFormReportDisplayContext.getLastModifiedDate()
		).put(
			"portletNamespace", PortalUtil.getPortletNamespace(DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_REPORT)
		).put(
			"totalItems", ddmFormReportDisplayContext.getTotalItems()
		).build()
	%>'
/>