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
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/staging" prefix="liferay-staging" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration" %><%@
page import="com.liferay.depot.application.DepotApplication" %><%@
page import="com.liferay.depot.exception.DepotEntryNameException" %><%@
page import="com.liferay.depot.model.DepotEntry" %><%@
page import="com.liferay.depot.model.DepotEntryGroupRel" %><%@
page import="com.liferay.depot.web.internal.constants.DepotAdminWebKeys" %><%@
page import="com.liferay.depot.web.internal.constants.DepotPortletKeys" %><%@
page import="com.liferay.depot.web.internal.constants.DepotScreenNavigationEntryConstants" %><%@
page import="com.liferay.depot.web.internal.constants.SharingWebKeys" %><%@
page import="com.liferay.depot.web.internal.display.context.DepotAdminDetailsDisplayContext" %><%@
page import="com.liferay.depot.web.internal.display.context.DepotAdminDisplayContext" %><%@
page import="com.liferay.depot.web.internal.display.context.DepotAdminManagementToolbarDisplayContext" %><%@
page import="com.liferay.depot.web.internal.display.context.DepotAdminMembershipsDisplayContext" %><%@
page import="com.liferay.depot.web.internal.display.context.DepotAdminRolesDisplayContext" %><%@
page import="com.liferay.depot.web.internal.display.context.DepotAdminSitesDisplayContext" %><%@
page import="com.liferay.depot.web.internal.display.context.DepotApplicationDisplayContext" %><%@
page import="com.liferay.depot.web.internal.util.DepotLanguageUtil" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.exception.DuplicateGroupException" %><%@
page import="com.liferay.portal.kernel.exception.GroupKeyException" %><%@
page import="com.liferay.portal.kernel.exception.LocaleException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.GroupConstants" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropertiesParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.roles.admin.kernel.util.RolesAdminUtil" %><%@
page import="com.liferay.sharing.configuration.SharingConfiguration" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />