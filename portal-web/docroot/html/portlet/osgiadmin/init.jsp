<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="aQute.libg.header.OSGiHeader" %>

<%@ page import="com.liferay.portal.util.PropsValues" %>
<%@ page import="com.liferay.portlet.osgiadmin.OSGiException" %>
<%@ page import="com.liferay.portlet.osgiadmin.service.OSGiServiceUtil" %>

<%@ page import="org.osgi.framework.Bundle" %>
<%@ page import="org.osgi.framework.BundleContext" %>
<%@ page import="org.osgi.framework.BundleException" %>
<%@ page import="org.osgi.framework.ServiceReference" %>
<%@ page import="org.osgi.framework.launch.Framework" %>
<%@ page import="org.osgi.framework.startlevel.BundleStartLevel" %>

<%@ page import="java.net.MalformedURLException" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.text.Format" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Dictionary" %>
<%@ page import="java.util.List" %>

<%
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>