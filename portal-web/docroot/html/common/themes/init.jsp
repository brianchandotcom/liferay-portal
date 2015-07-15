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

<%@ include file="/html/common/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.servlet.taglib.util.OutputData" %><%@
page import="com.liferay.portal.kernel.util.PredicateFilter" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.security.ldap.LDAPSettingsUtil" %><%@
page import="com.liferay.portal.servlet.ComboServletStaticURLGenerator" %><%@
page import="com.liferay.portlet.JavaScriptPortletResourcePredicateFilter" %><%@
page import="com.liferay.portlet.PortletResourceAccessor" %><%@
page import="com.liferay.portlet.PortletResourceStaticURLGenerator" %><%@
page import="com.liferay.taglib.aui.ScriptTag" %>

<%@ page import="org.apache.struts.tiles.ComponentContext" %><%@
page import="org.apache.struts.tiles.taglib.ComponentConstants" %>