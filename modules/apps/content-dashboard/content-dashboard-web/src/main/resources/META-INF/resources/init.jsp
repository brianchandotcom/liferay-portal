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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.asset.kernel.model.AssetVocabulary" %><%@
page import="com.liferay.content.dashboard.item.action.ContentDashboardItemAction" %><%@
page import="com.liferay.content.dashboard.web.internal.constants.ContentDashboardWebKeys" %><%@
page import="com.liferay.content.dashboard.web.internal.display.context.ContentDashboardAdminConfigurationDisplayContext" %><%@
page import="com.liferay.content.dashboard.web.internal.display.context.ContentDashboardAdminDisplayContext" %><%@
page import="com.liferay.content.dashboard.web.internal.display.context.ContentDashboardAdminManagementToolbarDisplayContext" %><%@
page import="com.liferay.content.dashboard.web.internal.display.context.ContentDashboardItemTypeItemSelectorViewDisplayContext" %><%@
page import="com.liferay.content.dashboard.web.internal.display.context.ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.content.dashboard.web.internal.item.ContentDashboardItem" %><%@
page import="com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType" %><%@
page import="com.liferay.info.item.InfoItemReference" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.json.JSONUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.List" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%@ include file="/init-ext.jsp" %>