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

<%@ page import="com.liferay.portal.layoutconfiguration.util.RuntimePageUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DocumentConversionUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructure" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission" %><%@
page import="com.liferay.portlet.journal.NoSuchArticleException" %><%@
page import="com.liferay.portlet.journal.action.EditArticleAction" %><%@
page import="com.liferay.portlet.journal.model.JournalArticle" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleConstants" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleDisplay" %><%@
page import="com.liferay.portlet.journal.search.ArticleSearch" %><%@
page import="com.liferay.portlet.journal.search.ArticleSearchTerms" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalArticlePermission" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalPermission" %><%@
page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

long groupId = ParamUtil.getLong(renderRequest, "groupId");

if (groupId <= 0) {
	groupId = GetterUtil.getLong(portletPreferences.getValue("groupId", scopeGroupId.toString()));
}

String articleId = ParamUtil.getString(renderRequest, "articleId");
String ddmTemplateKey = ParamUtil.getString(renderRequest, "ddmTemplateKey");

if (Validator.isNull(articleId)) {
	articleId = GetterUtil.getString(portletPreferences.getValue("articleId", StringPool.BLANK));
	ddmTemplateKey = GetterUtil.getString(portletPreferences.getValue("ddmTemplateKey", StringPool.BLANK));
}

boolean showAvailableLocales = GetterUtil.getBoolean(portletPreferences.getValue("showAvailableLocales", StringPool.BLANK));
String[] extensions = portletPreferences.getValues("extensions", null);
boolean enablePrint = GetterUtil.getBoolean(portletPreferences.getValue("enablePrint", null));
boolean enableRelatedAssets = GetterUtil.getBoolean(portletPreferences.getValue("enableRelatedAssets", null), true);
boolean enableRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableRatings", null));
boolean enableComments = PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED && GetterUtil.getBoolean(portletPreferences.getValue("enableComments", null));
boolean enableCommentRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableCommentRatings", null));
boolean enableViewCountIncrement = GetterUtil.getBoolean(portletPreferences.getValue("enableViewCountIncrement", null), PropsValues.ASSET_ENTRY_BUFFERED_INCREMENT_ENABLED);

String[] conversions = DocumentConversionUtil.getConversions("html");

boolean openOfficeServerEnabled = PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED);
boolean enableConversions = openOfficeServerEnabled && (extensions != null) && (extensions.length > 0);

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/journal_content/init-ext.jsp" %>