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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/staging" prefix="liferay-staging" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.DateRange" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.JavaConstants" %><%@
page import="com.liferay.portal.kernel.util.MapUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.model.LayoutSetBranch" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.model.StagedGroupedModel" %><%@
page import="com.liferay.portal.model.StagedModel" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.service.LayoutSetBranchLocalServiceUtil" %><%@
page import="com.liferay.portal.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PortletKeys" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.ExportImportDateUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.ExportImportHelperUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.ManifestSummary" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataContext" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataContextFactoryUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandler" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerChoice" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerControl" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys" %><%@
page import="com.liferay.portlet.exportimport.lar.StagedModelType" %><%@
page import="com.liferay.portlet.exportimport.model.ExportImportConfiguration" %><%@
page import="com.liferay.portlet.exportimport.staging.StagingUtil" %>

<%@ page import="java.util.Calendar" %><%@
page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletResponse" %><%@
page import="javax.portlet.PortletURL" %>

<portlet:defineObjects/>

<liferay-theme:defineObjects/>

<liferay-staging:defineObjects />

<%
PortletRequest portletRequest = (PortletRequest)request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

PortletResponse portletResponse = (PortletResponse)request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

String currentURL = null;

if ((portletRequest != null) && (portletResponse != null)) {
	PortletURL currentURLObj = PortletURLUtil.getCurrent(PortalUtil.getLiferayPortletRequest(portletRequest), PortalUtil.getLiferayPortletResponse(portletResponse));

	currentURL = currentURLObj.toString();
}
else {
	currentURL = PortalUtil.getCurrentURL(request);
}
%>