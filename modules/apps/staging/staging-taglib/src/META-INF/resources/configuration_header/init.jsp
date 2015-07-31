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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portlet.exportimport.model.ExportImportConfiguration" %>

<liferay-theme:defineObjects />

<%
ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)request.getAttribute("liferay-staging:configuration-header:exportImportConfiguration");
String label = GetterUtil.getString((String)request.getAttribute("liferay-staging:configuration-header:label"));
LiferayPortletRequest liferayPortletRequest = (LiferayPortletRequest)request.getAttribute("liferay-staging:configuration-header:liferayPortletRequest");
%>