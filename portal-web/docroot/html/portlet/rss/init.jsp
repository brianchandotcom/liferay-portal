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

<%@ page import="com.liferay.portal.kernel.sanitizer.Sanitizer" %><%@
page import="com.liferay.portal.kernel.sanitizer.SanitizerException" %><%@
page import="com.liferay.portal.kernel.sanitizer.SanitizerUtil" %><%@
page import="com.liferay.portlet.journal.action.EditArticleAction" %><%@
page import="com.liferay.portlet.journal.model.JournalArticle" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleConstants" %><%@
page import="com.liferay.portlet.journal.search.ArticleSearch" %><%@
page import="com.liferay.portlet.journal.search.ArticleSearchTerms" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleServiceUtil" %><%@
page import="com.liferay.portlet.journal.util.JournalUtil" %><%@
page import="com.liferay.portlet.rss.util.RSSUtil" %>

<%@ page import="com.sun.syndication.feed.synd.SyndContent" %><%@
page import="com.sun.syndication.feed.synd.SyndEnclosure" %><%@
page import="com.sun.syndication.feed.synd.SyndEntry" %><%@
page import="com.sun.syndication.feed.synd.SyndFeed" %><%@
page import="com.sun.syndication.feed.synd.SyndImage" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

String[] urls = preferences.getValues("urls", new String[0]);
String[] titles = preferences.getValues("titles", new String[0]);
int entriesPerFeed = GetterUtil.getInteger(preferences.getValue("entriesPerFeed", "8"));
int expandedEntriesPerFeed = GetterUtil.getInteger(preferences.getValue("expandedEntriesPerFeed", "1"));
boolean showFeedTitle = GetterUtil.getBoolean(preferences.getValue("showFeedTitle", Boolean.TRUE.toString()));
boolean showFeedPublishedDate = GetterUtil.getBoolean(preferences.getValue("showFeedPublishedDate", Boolean.TRUE.toString()));
boolean showFeedDescription = GetterUtil.getBoolean(preferences.getValue("showFeedDescription", Boolean.TRUE.toString()));
boolean showFeedImage = GetterUtil.getBoolean(preferences.getValue("showFeedImage", Boolean.TRUE.toString()));
String feedImageAlignment = preferences.getValue("feedImageAlignment", "right");
boolean showFeedItemAuthor = GetterUtil.getBoolean(preferences.getValue("showFeedItemAuthor", Boolean.TRUE.toString()));

String[] headerArticleValues = preferences.getValues("headerArticleValues", new String[] {"0", ""});

long headerArticleGroupId = GetterUtil.getLong(headerArticleValues[0]);
String headerArticleId = headerArticleValues[1];

String[] footerArticleValues = preferences.getValues("footerArticleValues", new String[] {"0", ""});

long footerArticleGroupId = GetterUtil.getLong(footerArticleValues[0]);
String footerArticleId = footerArticleValues[1];

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/rss/init-ext.jsp" %>