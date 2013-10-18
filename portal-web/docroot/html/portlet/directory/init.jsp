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

<%@ page import="com.liferay.portal.NoSuchOrganizationException" %><%@
page import="com.liferay.portal.NoSuchUserGroupException" %><%@
page import="com.liferay.portlet.social.model.SocialRelationConstants" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationDisplayTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.UserDisplayTerms" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "users");

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale);
%>

<%@ include file="/html/portlet/directory/init-ext.jsp" %>