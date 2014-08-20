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
<%@ taglib uri="/META-INF/c.tld" prefix="c" %>
<%@ taglib uri="/META-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/META-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/META-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/META-INF/x.tld" prefix="x" %>
<%@ taglib uri="/META-INF/liferay-portlet_2_0.tld" prefix="portlet" %>
<%@ taglib uri="/META-INF/aui.tld" prefix="aui" %>
<%@ taglib uri="/META-INF/liferay-ddm.tld" prefix="liferay-ddm" %>
<%@ taglib uri="/META-INF/liferay-portlet-ext.tld" prefix="liferay-portlet" %>
<%@ taglib uri="/META-INF/liferay-security.tld" prefix="liferay-security" %>
<%@ taglib uri="/META-INF/liferay-staging.tld" prefix="liferay-staging" %>
<%@ taglib uri="/META-INF/liferay-theme.tld" prefix="liferay-theme" %>
<%@ taglib uri="/META-INF/liferay-ui.tld" prefix="liferay-ui" %>
<%@ taglib uri="/META-INF/liferay-util.tld" prefix="liferay-util" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.CharPool" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.amazon.rankings.model.AmazonRankings" %><%@
page import="com.liferay.amazon.rankings.util.AmazonRankingsUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.asset.NoSuchTagException" %><%@
page import="com.liferay.portlet.asset.NoSuchTagPropertyException" %><%@
page import="com.liferay.portlet.asset.model.AssetTag" %><%@
page import="com.liferay.portlet.asset.model.AssetTagProperty" %><%@
page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil" %>

<%@ page import="java.net.URL" %><%@ 
page import="java.text.NumberFormat" %><%@
page import="java.util.Enumeration" %><%@  
page import="java.util.Set" %><%@ 
page import="java.util.TreeSet" %>

<%@ page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.ValidatorException" %><%@
page import="javax.portlet.WindowState" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
PortletMode portletMode = liferayPortletRequest.getPortletMode();
WindowState windowState = liferayPortletRequest.getWindowState();
PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);
String currentURL = currentURLObj.toString();

String[] isbns = portletPreferences.getValues("isbns", new String[0]);
String accessKeyID = portletPreferences.getValue(AmazonRankings.AMAZON_ACCESS_KEY_ID, "");
String associateTag = portletPreferences.getValue(AmazonRankings.AMAZON_ASSOCIATE_TAG , "");
String secretAccessKey = portletPreferences.getValue(AmazonRankings.AMAZON_SECRET_ACCESS_KEY, "");

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
%>

<%@ include file="/init-ext.jsp" %>