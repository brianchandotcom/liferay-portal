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
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/layout" prefix="liferay-layout" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/staging" prefix="liferay-staging" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants" %><%@
page import="com.liferay.exportimport.kernel.lar.ExportImportDateUtil" %><%@
page import="com.liferay.exportimport.kernel.lar.ExportImportHelperUtil" %><%@
page import="com.liferay.exportimport.kernel.lar.ManifestSummary" %><%@
page import="com.liferay.exportimport.kernel.lar.PortletDataContext" %><%@
page import="com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil" %><%@
page import="com.liferay.exportimport.kernel.lar.PortletDataHandler" %><%@
page import="com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean" %><%@
page import="com.liferay.exportimport.kernel.lar.PortletDataHandlerChoice" %><%@
page import="com.liferay.exportimport.kernel.lar.PortletDataHandlerControl" %><%@
page import="com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys" %><%@
page import="com.liferay.exportimport.kernel.lar.StagedModelType" %><%@
page import="com.liferay.exportimport.kernel.model.ExportImportConfiguration" %><%@
page import="com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil" %><%@
page import="com.liferay.exportimport.kernel.staging.LayoutStagingUtil" %><%@
page import="com.liferay.exportimport.kernel.staging.StagingUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.LayoutConstants" %><%@
page import="com.liferay.portal.kernel.model.LayoutRevision" %><%@
page import="com.liferay.portal.kernel.model.LayoutRevisionConstants" %><%@
page import="com.liferay.portal.kernel.model.LayoutSetBranch" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.model.StagedGroupedModel" %><%@
page import="com.liferay.portal.kernel.model.StagedModel" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletBag" %><%@
page import="com.liferay.portal.kernel.portlet.PortletBagPool" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.util.AggregateResourceBundle" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.DateRange" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.JavaConstants" %><%@
page import="com.liferay.portal.kernel.util.MapUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PortletKeys" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.staging.constants.StagingProcessesWebKeys" %><%@
page import="com.liferay.taglib.ui.util.SessionTreeJSClicks" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Calendar" %><%@
page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.ResourceBundle" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletResponse" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-staging:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
PortletRequest portletRequest = (PortletRequest)request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

PortletResponse portletResponse = (PortletResponse)request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

if ((portletRequest == null) || (portletResponse == null)) {
	currentURL = PortalUtil.getCurrentURL(request);
}
%>