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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.blogs.kernel.model.BlogsEntry" %><%@
page import="com.liferay.blogs.service.BlogsStatsUserLocalService" %><%@
page import="com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil" %><%@
page import="com.liferay.ip.geocoder.IPGeocoder" %><%@
page import="com.liferay.ip.geocoder.IPInfo" %><%@
page import="com.liferay.message.boards.kernel.model.MBMessage" %><%@
page import="com.liferay.message.boards.kernel.service.MBStatsUserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchUserException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Contact" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.GroupConstants" %><%@
page import="com.liferay.portal.kernel.model.LayoutConstants" %><%@
page import="com.liferay.portal.kernel.model.Organization" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.OrganizationLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.UserPermissionUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.RSSUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.util.comparator.UserLoginDateComparator" %><%@
page import="com.liferay.portal.kernel.webserver.WebServerServletTokenUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.social.kernel.model.SocialActivity" %><%@
page import="com.liferay.social.kernel.model.SocialRelationConstants" %><%@
page import="com.liferay.social.kernel.model.SocialRequestConstants" %><%@
page import="com.liferay.social.kernel.service.SocialActivityLocalServiceUtil" %><%@
page import="com.liferay.social.kernel.service.SocialRelationLocalServiceUtil" %><%@
page import="com.liferay.social.kernel.service.SocialRequestLocalServiceUtil" %><%@
page import="com.liferay.social.networking.constants.SocialNetworkingPortletKeys" %><%@
page import="com.liferay.social.networking.exception.NoSuchMeetupsEntryException" %><%@
page import="com.liferay.social.networking.exception.NoSuchMeetupsRegistrationException" %><%@
page import="com.liferay.social.networking.friends.social.FriendsRequestKeys" %><%@
page import="com.liferay.social.networking.members.social.MembersRequestKeys" %><%@
page import="com.liferay.social.networking.model.MeetupsEntry" %><%@
page import="com.liferay.social.networking.model.MeetupsRegistration" %><%@
page import="com.liferay.social.networking.model.WallEntry" %><%@
page import="com.liferay.social.networking.service.MeetupsEntryLocalServiceUtil" %><%@
page import="com.liferay.social.networking.service.MeetupsRegistrationLocalServiceUtil" %><%@
page import="com.liferay.social.networking.service.WallEntryLocalServiceUtil" %><%@
page import="com.liferay.social.networking.web.internal.constants.SocialNetworkingWebKeys" %><%@
page import="com.liferay.social.networking.web.internal.meetups.util.MeetupsConstants" %><%@
page import="com.liferay.social.networking.web.internal.util.WallUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Calendar" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletPreferences" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

Organization organization = null;
User user2 = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());
}
else if (group.isUser()) {
	user2 = UserLocalServiceUtil.getUserById(group.getClassPK());
}

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>