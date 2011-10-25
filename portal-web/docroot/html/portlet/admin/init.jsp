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

<%@ page import="com.liferay.portal.CompanyMxException" %><%@
page import="com.liferay.portal.CompanyVirtualHostException" %><%@
page import="com.liferay.portal.CompanyWebIdException" %><%@
page import="com.liferay.portal.captcha.recaptcha.ReCaptchaImpl" %><%@
page import="com.liferay.portal.convert.ConvertProcess" %><%@
page import="com.liferay.portal.dao.shard.ManualShardSelector" %><%@
page import="com.liferay.portal.kernel.dao.shard.ShardUtil" %><%@
page import="com.liferay.portal.kernel.plugin.PluginPackage" %><%@
page import="com.liferay.portal.kernel.scripting.ScriptingUtil" %><%@
page import="com.liferay.portal.kernel.util.InstancePool" %><%@
page import="com.liferay.portal.kernel.xml.Document" %><%@
page import="com.liferay.portal.kernel.xml.Element" %><%@
page import="com.liferay.portal.kernel.xml.SAXReaderUtil" %><%@
page import="com.liferay.portal.util.PortalInstances" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntry" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileVersion" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.PDFProcessor" %><%@
page import="com.liferay.portlet.expando.model.ExpandoBridge" %><%@
page import="com.liferay.portlet.expando.model.ExpandoColumnConstants" %>

<%@ page import="java.awt.BasicStroke" %><%@
page import="java.awt.Color" %><%@
page import="java.awt.Font" %>

<%@ page import="org.apache.commons.collections.map.MultiValueMap" %><%@
page import="org.apache.log4j.Level" %><%@
page import="org.apache.log4j.LogManager" %><%@
page import="org.apache.log4j.Logger" %>

<%@ page import="org.jfree.chart.JFreeChart" %><%@
page import="org.jfree.chart.plot.DialShape" %><%@
page import="org.jfree.chart.plot.MeterInterval" %><%@
page import="org.jfree.chart.plot.MeterPlot" %><%@
page import="org.jfree.chart.servlet.ServletUtilities" %><%@
page import="org.jfree.data.Range" %><%@
page import="org.jfree.data.general.DefaultValueDataset" %>

<%
boolean showShardSelector = false;

if (PropsValues.SHARD_SELECTOR.equals(ManualShardSelector.class.getName()) && ShardUtil.getAvailableShardNames().length > 1) {
	showShardSelector = true;
}
%>