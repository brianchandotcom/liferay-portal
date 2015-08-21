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
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.settings.ParameterMapSettingsLocator" %><%@
page import="com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator" %><%@
page import="com.liferay.portal.kernel.settings.SettingsFactory" %><%@
page import="com.liferay.portal.kernel.settings.SettingsFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.asset.model.AssetTag" %><%@
page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.portlet.social.model.SocialActivityCounter" %><%@
page import="com.liferay.portlet.social.model.SocialActivityCounterConstants" %><%@
page import="com.liferay.portlet.social.service.SocialActivityCounterLocalServiceUtil" %><%@
page import="com.liferay.portlet.social.util.SocialConfigurationUtil" %><%@
page import="com.liferay.portlet.social.util.SocialCounterPeriodUtil" %><%@
page import="com.liferay.portlet.social.util.comparator.SocialActivityCounterNameComparator" %><%@
page import="com.liferay.social.group.statistics.web.configuration.SocialGroupStatisticsPortletInstanceConfiguration" %><%@
page import="com.liferay.social.group.statistics.web.constants.SocialGroupStatisticsPortletKeys" %>

<%@ page import="java.text.DecimalFormat" %><%@
page import="java.text.Format" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

SettingsFactory settingsFactory = SettingsFactoryUtil.getSettingsFactory();

SocialGroupStatisticsPortletInstanceConfiguration groupStatisticsPortletInstanceConfiguration = settingsFactory.getSettings(SocialGroupStatisticsPortletInstanceConfiguration.class, new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getId()));
%>

<%@ include file="/init-ext.jsp" %>