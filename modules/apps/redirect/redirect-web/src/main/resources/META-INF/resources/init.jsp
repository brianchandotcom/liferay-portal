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
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPNavigationItemList" %><%@
page import="com.liferay.frontend.taglib.servlet.taglib.util.EmptyResultMessageKeys" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.DateUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.redirect.exception.CircularRedirectEntryException" %><%@
page import="com.liferay.redirect.exception.DuplicateRedirectEntrySourceURLException" %><%@
page import="com.liferay.redirect.exception.RequiredRedirectEntryDestinationURLException" %><%@
page import="com.liferay.redirect.exception.RequiredRedirectEntrySourceURLException" %><%@
page import="com.liferay.redirect.model.RedirectEntry" %><%@
page import="com.liferay.redirect.model.RedirectNotFoundEntry" %><%@
page import="com.liferay.redirect.service.RedirectEntryLocalServiceUtil" %><%@
page import="com.liferay.redirect.web.internal.constants.RedirectWebKeys" %><%@
page import="com.liferay.redirect.web.internal.display.context.RedirectDisplayContext" %><%@
page import="com.liferay.redirect.web.internal.display.context.RedirectManagementToolbarDisplayContext" %><%@
page import="com.liferay.redirect.web.internal.display.context.RedirectNotFoundEntriesDisplayContext" %><%@
page import="com.liferay.redirect.web.internal.display.context.RedirectNotFoundEntriesManagementToolbarDisplayContext" %><%@
page import="com.liferay.redirect.web.internal.util.RedirectUtil" %>

<%@ page import="java.text.DateFormat" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />