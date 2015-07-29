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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/staging" prefix="liferay-staging" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.DateRange" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.MapUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portlet.exportimport.lar.ExportImportDateUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.ExportImportHelperUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys" %>

<%@ page import="java.util.Calendar" %><%@
page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletRequest" %>

<liferay-staging:defineObjects />

<liferay-theme:defineObjects />

<%
String cmd = GetterUtil.getString(request.getAttribute("liferay-staging:content:cmd"));
boolean disableInputs = GetterUtil.getBoolean(request.getAttribute("liferay-staging:content:disableInputs"));
PortletRequest renderRequest = (PortletRequest)request.getAttribute("liferay-staging:content:renderRequest");
Map<String, String[]> parameterMap = (Map<String, String[]>)GetterUtil.getObject(request.getAttribute("liferay-staging:content:parameterMap"), Collections.emptyMap());
String type = GetterUtil.getString(request.getAttribute("liferay-staging:content:type"));

DateRange dateRange = null;
String defaultRange = null;
long exportGroupId = groupId;

if (type.equals(Constants.EXPORT)) {
	defaultRange = ExportImportDateUtil.RANGE_ALL;

	if (liveGroupId > 0) {
		exportGroupId = liveGroupId;
	}
}
else {
	defaultRange = ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE;

	if (stagingGroupId > 0) {
		exportGroupId = stagingGroupId;
	}
}

dateRange = ExportImportDateUtil.getDateRange(renderRequest, exportGroupId, privateLayout, 0, null, defaultRange);

Date startDate = dateRange.getStartDate();
Date endDate = dateRange.getEndDate();

List<Portlet> dataSiteLevelPortlets = ExportImportHelperUtil.getDataSiteLevelPortlets(company.getCompanyId(), false);
%>