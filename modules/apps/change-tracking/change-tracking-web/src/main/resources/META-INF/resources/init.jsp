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
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.change.tracking.conflict.ConflictInfo" %><%@
page import="com.liferay.change.tracking.constants.CTActionKeys" %><%@
page import="com.liferay.change.tracking.constants.CTConstants" %><%@
page import="com.liferay.change.tracking.exception.CTCollectionDescriptionException" %><%@
page import="com.liferay.change.tracking.exception.CTCollectionNameException" %><%@
page import="com.liferay.change.tracking.model.CTCollection" %><%@
page import="com.liferay.change.tracking.model.CTEntry" %><%@
page import="com.liferay.change.tracking.model.CTProcess" %><%@
page import="com.liferay.change.tracking.service.CTEntryLocalServiceUtil" %><%@
page import="com.liferay.change.tracking.web.internal.constants.CTWebKeys" %><%@
page import="com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry" %><%@
page import="com.liferay.change.tracking.web.internal.display.CTEntryDiffDisplay" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ChangeListsConfigurationDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ChangeListsDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ChangeListsManagementToolbarDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.SelectChangeListManagementToolbarDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ViewChangesDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ViewDiscardDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ViewEntryDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ViewHistoryDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ViewHistoryManagementToolbarDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.security.permission.resource.CTCollectionPermission" %><%@
page import="com.liferay.petra.string.StringBundler" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.ModelHintsUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.Date" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.ResourceBundle" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
portletDisplay.setShowStagingIcon(false);
%>