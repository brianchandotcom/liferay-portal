<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portlet.journal.NoSuchArticleException" %><%@
page import="com.liferay.portlet.journal.util.JournalUtil" %>

<%
long groupId = GetterUtil.getLong(portletPreferences.getValue("groupId", String.valueOf(themeDisplay.getScopeGroupId())));
String ddmStructureKey = portletPreferences.getValue("ddmStructureKey", StringPool.BLANK);
String type = portletPreferences.getValue("type", StringPool.BLANK);
String pageUrl = portletPreferences.getValue("pageUrl", "maximized");
int pageDelta = GetterUtil.getInteger(portletPreferences.getValue("pageDelta", StringPool.BLANK));
String orderByCol = portletPreferences.getValue("orderByCol", StringPool.BLANK);
String orderByType = portletPreferences.getValue("orderByType", StringPool.BLANK);

OrderByComparator orderByComparator = JournalUtil.getArticleOrderByComparator(orderByCol, orderByType);

DDMStructure ddmStructure = null;

if (Validator.isNotNull(ddmStructureKey)) {
	ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(groupId, PortalUtil.getClassNameId(JournalArticle.class), ddmStructureKey);
}

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/journal_articles/init-ext.jsp" %>