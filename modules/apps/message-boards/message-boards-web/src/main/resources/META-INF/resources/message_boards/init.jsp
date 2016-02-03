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
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/trash" prefix="liferay-trash" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.servlet.taglib.AddMenuItem" %><%@
page import="com.liferay.message.boards.display.context.MBHomeDisplayContext" %><%@
page import="com.liferay.message.boards.web.constants.MBPortletKeys" %><%@
page import="com.liferay.message.boards.web.constants.MBWebKeys" %><%@
page import="com.liferay.message.boards.web.dao.search.MBResultRowSplitter" %><%@
page import="com.liferay.message.boards.web.display.context.MBDisplayContextProvider" %><%@
page import="com.liferay.message.boards.web.display.context.util.MBRequestHelper" %><%@
page import="com.liferay.message.boards.web.portlet.toolbar.contributor.MBPortletToolbarContributor" %><%@
page import="com.liferay.message.boards.web.search.EntriesChecker" %><%@
page import="com.liferay.message.boards.web.util.MBBreadcrumbUtil" %><%@
page import="com.liferay.message.boards.web.util.MBWebComponentProvider" %><%@
page import="com.liferay.portal.exception.NoSuchUserException" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.captcha.CaptchaConfigurationException" %><%@
page import="com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException" %><%@
page import="com.liferay.portal.kernel.captcha.CaptchaTextException" %><%@
page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.search.SearchResultUtil" %><%@
page import="com.liferay.portal.kernel.search.Summary" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.Menu" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem" %><%@
page import="com.liferay.portal.kernel.upload.LiferayFileItemException" %><%@
page import="com.liferay.portal.kernel.upload.UploadRequestSizeException" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.MathUtil" %><%@
page import="com.liferay.portal.kernel.util.MimeTypesUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.PropsUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.model.ModelHintsConstants" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.service.ServiceContext" %><%@
page import="com.liferay.portal.service.SubscriptionLocalServiceUtil" %><%@
page import="com.liferay.portal.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.theme.ThemeDisplay" %><%@
page import="com.liferay.portal.upload.LiferayFileItem" %><%@
page import="com.liferay.portal.util.Portal" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portlet.PortalPreferences" %><%@
page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %><%@
page import="com.liferay.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetRenderer" %><%@
page import="com.liferay.portlet.asset.model.AssetRendererFactory" %><%@
page import="com.liferay.portlet.asset.model.AssetTag" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %><%@
page import="com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException" %><%@
page import="com.liferay.portlet.documentlibrary.exception.DuplicateFileEntryException" %><%@
page import="com.liferay.portlet.documentlibrary.exception.FileExtensionException" %><%@
page import="com.liferay.portlet.documentlibrary.exception.FileNameException" %><%@
page import="com.liferay.portlet.documentlibrary.exception.FileSizeException" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntry" %><%@
page import="com.liferay.portlet.messageboards.MBGroupServiceSettings" %><%@
page import="com.liferay.portlet.messageboards.constants.MBConstants" %><%@
page import="com.liferay.portlet.messageboards.exception.BannedUserException" %><%@
page import="com.liferay.portlet.messageboards.exception.CategoryNameException" %><%@
page import="com.liferay.portlet.messageboards.exception.LockedThreadException" %><%@
page import="com.liferay.portlet.messageboards.exception.MailingListEmailAddressException" %><%@
page import="com.liferay.portlet.messageboards.exception.MailingListInServerNameException" %><%@
page import="com.liferay.portlet.messageboards.exception.MailingListInUserNameException" %><%@
page import="com.liferay.portlet.messageboards.exception.MailingListOutEmailAddressException" %><%@
page import="com.liferay.portlet.messageboards.exception.MailingListOutServerNameException" %><%@
page import="com.liferay.portlet.messageboards.exception.MailingListOutUserNameException" %><%@
page import="com.liferay.portlet.messageboards.exception.MessageBodyException" %><%@
page import="com.liferay.portlet.messageboards.exception.MessageSubjectException" %><%@
page import="com.liferay.portlet.messageboards.exception.NoSuchCategoryException" %><%@
page import="com.liferay.portlet.messageboards.exception.NoSuchMessageException" %><%@
page import="com.liferay.portlet.messageboards.exception.RequiredMessageException" %><%@
page import="com.liferay.portlet.messageboards.exception.SplitThreadException" %><%@
page import="com.liferay.portlet.messageboards.model.MBBan" %><%@
page import="com.liferay.portlet.messageboards.model.MBCategory" %><%@
page import="com.liferay.portlet.messageboards.model.MBCategoryConstants" %><%@
page import="com.liferay.portlet.messageboards.model.MBCategoryDisplay" %><%@
page import="com.liferay.portlet.messageboards.model.MBMailingList" %><%@
page import="com.liferay.portlet.messageboards.model.MBMessage" %><%@
page import="com.liferay.portlet.messageboards.model.MBMessageConstants" %><%@
page import="com.liferay.portlet.messageboards.model.MBMessageDisplay" %><%@
page import="com.liferay.portlet.messageboards.model.MBStatsUser" %><%@
page import="com.liferay.portlet.messageboards.model.MBThread" %><%@
page import="com.liferay.portlet.messageboards.model.MBThreadConstants" %><%@
page import="com.liferay.portlet.messageboards.model.MBThreadFlag" %><%@
page import="com.liferay.portlet.messageboards.model.MBTreeWalker" %><%@
page import="com.liferay.portlet.messageboards.model.impl.MBCategoryDisplayImpl" %><%@
page import="com.liferay.portlet.messageboards.model.impl.MBMessageImpl" %><%@
page import="com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBCategoryServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBMailingListLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBMessageServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBThreadServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.permission.MBCategoryPermission" %><%@
page import="com.liferay.portlet.messageboards.service.permission.MBMessagePermission" %><%@
page import="com.liferay.portlet.messageboards.service.permission.MBPermission" %><%@
page import="com.liferay.portlet.messageboards.util.MBMessageAttachmentsUtil" %><%@
page import="com.liferay.portlet.messageboards.util.MBUtil" %><%@
page import="com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.trash.kernel.model.TrashEntry" %><%@
page import="com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil" %><%@
page import="com.liferay.trash.kernel.util.TrashUtil" %>

<%@ page import="java.text.Format" %><%@
page import="java.text.NumberFormat" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Calendar" %><%@
page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String currentLanguageId = LanguageUtil.getLanguageId(request);
Locale currentLocale = LocaleUtil.fromLanguageId(currentLanguageId);
Locale defaultLocale = themeDisplay.getSiteDefaultLocale();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

MBGroupServiceSettings mbGroupServiceSettings = MBGroupServiceSettings.getInstance(themeDisplay.getSiteGroupId());

String[] priorities = mbGroupServiceSettings.getPriorities(currentLanguageId);

boolean allowAnonymousPosting = mbGroupServiceSettings.isAllowAnonymousPosting();
boolean enableFlags = mbGroupServiceSettings.isEnableFlags();
boolean enableRatings = mbGroupServiceSettings.isEnableRatings();
String messageFormat = mbGroupServiceSettings.getMessageFormat();
String recentPostsDateOffset = mbGroupServiceSettings.getRecentPostsDateOffset();
boolean subscribeByDefault = mbGroupServiceSettings.isSubscribeByDefault();
boolean threadAsQuestionByDefault = mbGroupServiceSettings.isThreadAsQuestionByDefault();

boolean enableRSS = mbGroupServiceSettings.isEnableRSS();
int rssDelta = mbGroupServiceSettings.getRSSDelta();
String rssDisplayStyle = mbGroupServiceSettings.getRSSDisplayStyle();
String rssFeedType = mbGroupServiceSettings.getRSSFeedType();

boolean childrenMessagesTaggable = true;
boolean includeFormTag = true;
boolean showSearch = true;

MBRequestHelper mbRequestHelper = new MBRequestHelper(request);

MBWebComponentProvider mbWebComponentProvider = MBWebComponentProvider.getMBWebComponentProvider();

MBDisplayContextProvider mbDisplayContextProvider = mbWebComponentProvider.getMBDisplayContextProvider();

MBHomeDisplayContext mbHomeDisplayContext = mbDisplayContextProvider.getMBHomeDisplayContext(request, response);

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
%>

<%@ include file="/message_boards/init-ext.jsp" %>