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
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.DateRange" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.MapUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.ManifestSummary" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataContext" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataContextFactoryUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandler" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerChoice" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerControl" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys" %><%@
page import="com.liferay.portlet.exportimport.staging.StagingUtil" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<liferay-staging:defineObjects />

<liferay-theme:defineObjects />

<%
DateRange dateRange = (DateRange)GetterUtil.getObject(request.getAttribute("liferay-staging:portlet-list:dateRange"));
boolean disableInputs = GetterUtil.getBoolean(request.getAttribute("liferay-staging:portlet-list:disableInputs"));
Map<String, String[]> parameterMap = (Map<String, String[]>)GetterUtil.getObject(request.getAttribute("liferay-staging:content:parameterMap"), Collections.emptyMap());
List<Portlet> portlets = (List<Portlet>)GetterUtil.getObject(request.getAttribute("liferay-staging:portlet-list:portlets"), Collections.emptyList());
String type = GetterUtil.getString(request.getAttribute("liferay-staging:portlet-list:type"));

Set<String> displayedControls = new HashSet<String>();
Set<String> portletDataHandlerClasses = new HashSet<String>();

long exportGroupId = groupId;

if (type.equals(Constants.EXPORT) && (liveGroupId > 0)) {
	exportGroupId = liveGroupId;
}
else if (stagingGroupId > 0) {
	exportGroupId = stagingGroupId;
}

Date startDate = dateRange.getStartDate();
Date endDate = dateRange.getEndDate();

PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(company.getCompanyId(), exportGroupId, startDate, endDate);

ManifestSummary manifestSummary = portletDataContext.getManifestSummary();
%>