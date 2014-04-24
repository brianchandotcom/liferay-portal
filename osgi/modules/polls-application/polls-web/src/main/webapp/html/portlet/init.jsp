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
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>

<%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.polls.DuplicateVoteException" %><%@
page import="com.liferay.portlet.polls.NoSuchChoiceException" %><%@
page import="com.liferay.portlet.polls.NoSuchQuestionException" %><%@
page import="com.liferay.portlet.polls.QuestionChoiceException" %><%@
page import="com.liferay.portlet.polls.QuestionDescriptionException" %><%@
page import="com.liferay.portlet.polls.QuestionExpirationDateException" %><%@
page import="com.liferay.portlet.polls.QuestionTitleException" %><%@
page import="com.liferay.portlet.polls.action.EditQuestionAction" %><%@
page import="com.liferay.portlet.polls.constants.PollsPortletKeys" %><%@
page import="com.liferay.portlet.polls.model.PollsChoice" %><%@
page import="com.liferay.portlet.polls.model.PollsQuestion" %><%@
page import="com.liferay.portlet.polls.model.PollsVote" %><%@
page import="com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil" %><%@
page import="com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil" %><%@
page import="com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil" %><%@
page import="com.liferay.portlet.polls.service.permission.PollsPermission" %><%@
page import="com.liferay.portlet.polls.service.permission.PollsQuestionPermission" %><%@
page import="com.liferay.portlet.polls.util.PollsUtil" %><%@
page import="com.liferay.portlet.polls.util.PollsWebKeys" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchEntry" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.security.auth.PrincipalException" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.WebKeys" %>

<%@
page import="java.text.Format" %><%@
page import="java.text.NumberFormat" %>

<%@
page import="java.util.List" %><%@
page import="java.util.ArrayList" %>

<%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>


<portlet:defineObjects />
<liferay-theme:defineObjects/>

<%
	PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

	String currentURL = currentURLObj.toString();

	Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

	WindowState windowState = liferayPortletRequest.getWindowState();
%>