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

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.portlet.social.model.SocialActivityCounter" %><%@
page import="com.liferay.portlet.social.model.SocialActivityCounterConstants" %><%@
page import="com.liferay.portlet.social.service.SocialActivityCounterLocalServiceUtil" %><%@
page import="com.liferay.portlet.social.util.SocialConfigurationUtil" %><%@
page import="com.liferay.portlet.social.util.SocialCounterPeriodUtil" %><%@
page import="com.liferay.portlet.social.util.comparator.SocialActivityCounterNameComparator" %><%@
page import="com.liferay.social.group.statistics.web.configuration.GroupStatisticsPortletInstanceConfiguration" %><%@
page import="com.liferay.social.group.statistics.web.constants.GroupStatisticsPortletKeys" %>

<%
SettingsFactory settingsFactory = SettingsFactoryUtil.getSettingsFactory();

GroupStatisticsPortletInstanceConfiguration groupStatisticsPortletInstanceConfiguration = settingsFactory.getSettings(GroupStatisticsPortletInstanceConfiguration.class, new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getId()));
%>