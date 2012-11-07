<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DocumentConversionUtil" %><%@
page import="com.liferay.portlet.journal.NoSuchArticleException" %><%@
page import="com.liferay.portlet.journal.NoSuchStructureException" %><%@
page import="com.liferay.portlet.journal.action.EditArticleAction" %><%@
page import="com.liferay.portlet.journal.model.JournalArticle" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleConstants" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleDisplay" %><%@
page import="com.liferay.portlet.journal.model.JournalStructure" %><%@
page import="com.liferay.portlet.journal.model.JournalTemplate" %><%@
page import="com.liferay.portlet.journal.search.ArticleSearch" %><%@
page import="com.liferay.portlet.journal.search.ArticleSearchTerms" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalArticlePermission" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalPermission" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalTemplatePermission" %><%@
page import="com.liferay.portlet.journal.util.JournalUtil" %><%@
page import="com.liferay.portlet.layoutconfiguration.util.RuntimePageUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

long groupId = ParamUtil.getLong(renderRequest, "groupId");

if (groupId <= 0) {
	groupId = GetterUtil.getLong(preferences.getValue("groupId", scopeGroupId.toString()));
}

String articleId = ParamUtil.getString(renderRequest, "articleId");
String templateId = ParamUtil.getString(renderRequest, "templateId");

if (Validator.isNull(articleId)) {
	articleId = GetterUtil.getString(preferences.getValue("articleId", StringPool.BLANK));
	templateId = GetterUtil.getString(preferences.getValue("templateId", StringPool.BLANK));
}

boolean showAvailableLocales = GetterUtil.getBoolean(preferences.getValue("showAvailableLocales", StringPool.BLANK));
String[] extensions = preferences.getValues("extensions", null);
boolean enablePrint = GetterUtil.getBoolean(preferences.getValue("enablePrint", null));
boolean enableRelatedAssets = GetterUtil.getBoolean(preferences.getValue("enableRelatedAssets", null), true);
boolean enableRatings = GetterUtil.getBoolean(preferences.getValue("enableRatings", null));
boolean enableComments = PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED && GetterUtil.getBoolean(preferences.getValue("enableComments", null));
boolean enableCommentRatings = GetterUtil.getBoolean(preferences.getValue("enableCommentRatings", null));
boolean enableViewCountIncrement = GetterUtil.getBoolean(preferences.getValue("enableViewCountIncrement", null), PropsValues.ASSET_ENTRY_INCREMENT_VIEW_COUNTER_ENABLED);

String[] conversions = DocumentConversionUtil.getConversions("html");

boolean openOfficeServerEnabled = PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED);
boolean enableConversions = openOfficeServerEnabled && (extensions != null) && (extensions.length > 0);

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/journal_content/init-ext.jsp" %>