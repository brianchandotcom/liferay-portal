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
taglib uri="http://liferay.com/tld/flags" prefix="liferay-flags" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/trash" prefix="liferay-trash" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.asset.kernel.model.AssetRendererFactory" %><%@
page import="com.liferay.asset.kernel.model.AssetTag" %><%@
page import="com.liferay.asset.kernel.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.persistence.AssetEntryQuery" %><%@
page import="com.liferay.document.library.kernel.antivirus.AntivirusScannerException" %><%@
page import="com.liferay.document.library.kernel.exception.DuplicateFileEntryException" %><%@
page import="com.liferay.document.library.kernel.exception.FileExtensionException" %><%@
page import="com.liferay.document.library.kernel.exception.FileNameException" %><%@
page import="com.liferay.document.library.kernel.exception.FileSizeException" %><%@
page import="com.liferay.document.library.kernel.model.DLFileEntry" %><%@
page import="com.liferay.message.boards.display.context.MBHomeDisplayContext" %><%@
page import="com.liferay.message.boards.display.context.MBListDisplayContext" %><%@
page import="com.liferay.message.boards.kernel.constants.MBConstants" %><%@
page import="com.liferay.message.boards.kernel.exception.BannedUserException" %><%@
page import="com.liferay.message.boards.kernel.exception.CategoryNameException" %><%@
page import="com.liferay.message.boards.kernel.exception.LockedThreadException" %><%@
page import="com.liferay.message.boards.kernel.exception.MailingListEmailAddressException" %><%@
page import="com.liferay.message.boards.kernel.exception.MailingListInServerNameException" %><%@
page import="com.liferay.message.boards.kernel.exception.MailingListInUserNameException" %><%@
page import="com.liferay.message.boards.kernel.exception.MailingListOutEmailAddressException" %><%@
page import="com.liferay.message.boards.kernel.exception.MailingListOutServerNameException" %><%@
page import="com.liferay.message.boards.kernel.exception.MailingListOutUserNameException" %><%@
page import="com.liferay.message.boards.kernel.exception.MessageBodyException" %><%@
page import="com.liferay.message.boards.kernel.exception.MessageSubjectException" %><%@
page import="com.liferay.message.boards.kernel.exception.NoSuchCategoryException" %><%@
page import="com.liferay.message.boards.kernel.exception.NoSuchMessageException" %><%@
page import="com.liferay.message.boards.kernel.exception.RequiredMessageException" %><%@
page import="com.liferay.message.boards.kernel.exception.SplitThreadException" %><%@
page import="com.liferay.message.boards.kernel.model.MBBan" %><%@
page import="com.liferay.message.boards.kernel.model.MBCategory" %><%@
page import="com.liferay.message.boards.kernel.model.MBCategoryConstants" %><%@
page import="com.liferay.message.boards.kernel.model.MBCategoryDisplay" %><%@
page import="com.liferay.message.boards.kernel.model.MBMailingList" %><%@
page import="com.liferay.message.boards.kernel.model.MBMessage" %><%@
page import="com.liferay.message.boards.kernel.model.MBMessageConstants" %><%@
page import="com.liferay.message.boards.kernel.model.MBMessageDisplay" %><%@
page import="com.liferay.message.boards.kernel.model.MBMessageIterator" %><%@
page import="com.liferay.message.boards.kernel.model.MBStatsUser" %><%@
page import="com.liferay.message.boards.kernel.model.MBThread" %><%@
page import="com.liferay.message.boards.kernel.model.MBThreadFlag" %><%@
page import="com.liferay.message.boards.kernel.model.MBTreeWalker" %><%@
page import="com.liferay.message.boards.kernel.service.MBBanLocalServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.service.MBCategoryLocalServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.service.MBCategoryServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.service.MBMailingListLocalServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.service.MBMessageServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.service.MBStatsUserLocalServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.service.MBThreadFlagLocalServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.service.MBThreadLocalServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.service.MBThreadServiceUtil" %><%@
page import="com.liferay.message.boards.kernel.util.comparator.MessageCreateDateComparator" %><%@
page import="com.liferay.message.boards.web.constants.MBPortletKeys" %><%@
page import="com.liferay.message.boards.web.internal.dao.search.MBResultRowSplitter" %><%@
page import="com.liferay.message.boards.web.internal.display.context.MBDisplayContextProvider" %><%@
page import="com.liferay.message.boards.web.internal.display.context.util.MBRequestHelper" %><%@
page import="com.liferay.message.boards.web.internal.search.EntriesChecker" %><%@
page import="com.liferay.message.boards.web.internal.util.MBBreadcrumbUtil" %><%@
page import="com.liferay.message.boards.web.internal.util.MBWebComponentProvider" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.captcha.CaptchaConfigurationException" %><%@
page import="com.liferay.portal.kernel.captcha.CaptchaTextException" %><%@
page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchUserException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.model.ModelHintsConstants" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortalPreferences" %><%@
page import="com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.search.SearchResult" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.service.ServiceContext" %><%@
page import="com.liferay.portal.kernel.service.SubscriptionLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.theme.ThemeDisplay" %><%@
page import="com.liferay.portal.kernel.upload.LiferayFileItemException" %><%@
page import="com.liferay.portal.kernel.upload.UploadRequestSizeException" %><%@
page import="com.liferay.portal.kernel.util.CharPool" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.MimeTypesUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.Portal" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
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
page import="com.liferay.portal.upload.LiferayFileItem" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %><%@
page import="com.liferay.portlet.messageboards.MBGroupServiceSettings" %><%@
page import="com.liferay.portlet.messageboards.model.impl.MBCategoryDisplayImpl" %><%@
page import="com.liferay.portlet.messageboards.model.impl.MBMessageImpl" %><%@
page import="com.liferay.portlet.messageboards.model.impl.MBMessageIteratorImpl" %><%@
page import="com.liferay.portlet.messageboards.service.permission.MBCategoryPermission" %><%@
page import="com.liferay.portlet.messageboards.service.permission.MBMessagePermission" %><%@
page import="com.liferay.portlet.messageboards.service.permission.MBPermission" %><%@
page import="com.liferay.portlet.messageboards.util.MBMessageAttachmentsUtil" %><%@
page import="com.liferay.portlet.messageboards.util.MBUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.trash.kernel.model.TrashEntry" %><%@
page import="com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil" %><%@
page import="com.liferay.trash.kernel.util.TrashUtil" %>

<%@ page import="java.text.Format" %><%@
page import="java.text.NumberFormat" %>

<%@ page import="java.util.ArrayList" %><%@
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
boolean subscribeByDefault = mbGroupServiceSettings.isSubscribeByDefault();
boolean threadAsQuestionByDefault = mbGroupServiceSettings.isThreadAsQuestionByDefault();

boolean enableRSS = mbGroupServiceSettings.isEnableRSS();
int rssDelta = mbGroupServiceSettings.getRSSDelta();
String rssDisplayStyle = mbGroupServiceSettings.getRSSDisplayStyle();
String rssFeedType = mbGroupServiceSettings.getRSSFeedType();

boolean childrenMessagesTaggable = true;
boolean showSearch = true;

MBWebComponentProvider mbWebComponentProvider = MBWebComponentProvider.getMBWebComponentProvider();

MBDisplayContextProvider mbDisplayContextProvider = mbWebComponentProvider.getMBDisplayContextProvider();

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
%>

<%@ include file="/message_boards/init-ext.jsp" %>