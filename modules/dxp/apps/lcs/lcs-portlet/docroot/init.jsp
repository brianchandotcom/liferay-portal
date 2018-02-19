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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.lcs.exception.LCSExceptionHandler" %><%@
page import="com.liferay.lcs.rest.LCSClusterEntryClientUtil" %><%@
page import="com.liferay.lcs.rest.LCSClusterNodeClientUtil" %><%@
page import="com.liferay.lcs.rest.LCSProjectClientUtil" %><%@
page import="com.liferay.lcs.rest.client.LCSClusterEntry" %><%@
page import="com.liferay.lcs.rest.client.LCSClusterNode" %><%@
page import="com.liferay.lcs.rest.client.LCSProject" %><%@
page import="com.liferay.lcs.util.ClusterNodeUtil" %><%@
page import="com.liferay.lcs.util.LCSAlert" %><%@
page import="com.liferay.lcs.util.LCSConnectionManagerUtil" %><%@
page import="com.liferay.lcs.util.LCSConstants" %><%@
page import="com.liferay.lcs.util.LCSPortletPreferencesUtil" %><%@
page import="com.liferay.lcs.util.LCSUtil" %><%@
page import="com.liferay.lcs.util.PortletPropsValues" %><%@
page import="com.liferay.portal.kernel.cluster.ClusterExecutorUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionErrors" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatConstants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.Date" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %><%@
page import="java.util.TimeZone" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

Format dateFormatDate = FastDateFormatFactoryUtil.getDateTime(FastDateFormatConstants.MEDIUM, FastDateFormatConstants.MEDIUM, locale, timeZone);
Format intervalDateFormatDate = FastDateFormatFactoryUtil.getSimpleDateFormat("HH:mm:ss", TimeZone.getTimeZone(StringPool.UTC));
%>