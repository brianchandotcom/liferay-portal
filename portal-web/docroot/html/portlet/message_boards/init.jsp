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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.comment.Comment" %><%@
page import="com.liferay.portal.kernel.comment.CommentConstants" %><%@
page import="com.liferay.portal.kernel.comment.CommentManagerUtil" %><%@
page import="com.liferay.portal.kernel.comment.WorkflowableComment" %><%@
page import="com.liferay.portal.kernel.util.MimeTypesUtil" %><%@
page import="com.liferay.portlet.messageboards.BannedUserException" %><%@
page import="com.liferay.portlet.messageboards.CategoryNameException" %><%@
page import="com.liferay.portlet.messageboards.DiscussionMaxCommentsException" %><%@
page import="com.liferay.portlet.messageboards.LockedThreadException" %><%@
page import="com.liferay.portlet.messageboards.MBGroupServiceSettings" %><%@
page import="com.liferay.portlet.messageboards.MailingListEmailAddressException" %><%@
page import="com.liferay.portlet.messageboards.MailingListInServerNameException" %><%@
page import="com.liferay.portlet.messageboards.MailingListInUserNameException" %><%@
page import="com.liferay.portlet.messageboards.MailingListOutEmailAddressException" %><%@
page import="com.liferay.portlet.messageboards.MailingListOutServerNameException" %><%@
page import="com.liferay.portlet.messageboards.MailingListOutUserNameException" %><%@
page import="com.liferay.portlet.messageboards.MessageBodyException" %><%@
page import="com.liferay.portlet.messageboards.MessageSubjectException" %><%@
page import="com.liferay.portlet.messageboards.NoSuchCategoryException" %><%@
page import="com.liferay.portlet.messageboards.NoSuchMessageException" %><%@
page import="com.liferay.portlet.messageboards.RequiredMessageException" %><%@
page import="com.liferay.portlet.messageboards.SplitThreadException" %><%@
page import="com.liferay.portlet.messageboards.model.MBBan" %><%@
page import="com.liferay.portlet.messageboards.model.MBCategory" %><%@
page import="com.liferay.portlet.messageboards.model.MBCategoryConstants" %><%@
page import="com.liferay.portlet.messageboards.model.MBCategoryDisplay" %><%@
page import="com.liferay.portlet.messageboards.model.MBMailingList" %><%@
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
page import="com.liferay.portlet.messageboards.service.MBMessageServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.MBThreadServiceUtil" %><%@
page import="com.liferay.portlet.messageboards.service.permission.MBCategoryPermission" %><%@
page import="com.liferay.portlet.messageboards.service.permission.MBMessagePermission" %><%@
page import="com.liferay.portlet.messageboards.service.permission.MBPermission" %><%@
page import="com.liferay.portlet.messageboards.util.MBConstants" %><%@
page import="com.liferay.portlet.messageboards.util.MBMessageAttachmentsUtil" %><%@
page import="com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator" %><%@
page import="com.liferay.portlet.ratings.model.RatingsStats" %><%@
page import="com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil" %><%@
page import="com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil" %>

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

ResourceURL rssURL = liferayPortletResponse.createResourceURL();

rssURL.setParameter("struts_action", "/message_boards/rss");
rssURL.setParameter("p_l_id", String.valueOf(plid));
rssURL.setParameter("mbCategoryId", String.valueOf(scopeGroupId));

boolean categoriesPanelCollapsible = true;
boolean categoriesPanelExtended = true;
boolean threadsPanelCollapsible = true;
boolean threadsPanelExtended = true;

boolean childrenMessagesTaggable = true;
boolean includeFormTag = true;
boolean showSearch = true;

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
%>

<%@ include file="/html/portlet/message_boards/init-ext.jsp" %>