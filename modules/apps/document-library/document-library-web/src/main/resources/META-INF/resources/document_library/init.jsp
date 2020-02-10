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

<%@ include file="/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/data-engine" prefix="liferay-data-engine" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %>

<%@ page import="com.liferay.bulk.selection.BulkSelectionRunner" %><%@
page import="com.liferay.document.library.configuration.DLConfiguration" %><%@
page import="com.liferay.document.library.kernel.model.DLVersionNumberIncrease" %><%@
page import="com.liferay.document.library.web.internal.bulk.selection.BulkSelectionRunnerUtil" %><%@
page import="com.liferay.document.library.web.internal.display.context.DLEditFileEntryTypeDisplayContext" %><%@
page import="com.liferay.document.library.web.internal.display.context.DLEditFileShortcutDisplayContext" %><%@
page import="com.liferay.document.library.web.internal.display.context.FolderActionDisplayContext" %><%@
page import="com.liferay.document.library.web.internal.search.DDMStructureRowChecker" %><%@
page import="com.liferay.document.library.web.internal.util.DLAssetHelperUtil" %><%@
page import="com.liferay.document.library.web.internal.util.RepositoryClassDefinitionUtil" %><%@
page import="com.liferay.dynamic.data.mapping.exception.RequiredStructureException" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList" %><%@
page import="com.liferay.portal.kernel.lock.Lock" %><%@
page import="com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.util.RepositoryUtil" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.Objects" %>

<%
DLRequestHelper dlRequestHelper = new DLRequestHelper(request);

String portletId = dlRequestHelper.getResourcePortletId();

portletName = dlRequestHelper.getResourcePortletName();

DLAdminDisplayContext dlAdminDisplayContext = dlAdminDisplayContextProvider.getDLAdminDisplayContext(request, response);

DLAdminManagementToolbarDisplayContext dlAdminManagementToolbarDisplayContext = dlAdminDisplayContextProvider.getDLAdminManagementToolbarDisplayContext(request, response);

DLConfiguration dlConfiguration = ConfigurationProviderUtil.getSystemConfiguration(DLConfiguration.class);
DLGroupServiceSettings dlGroupServiceSettings = dlRequestHelper.getDLGroupServiceSettings();
DLPortletInstanceSettings dlPortletInstanceSettings = dlRequestHelper.getDLPortletInstanceSettings();

long rootFolderId = dlAdminDisplayContext.getRootFolderId();
String rootFolderName = dlAdminDisplayContext.getRootFolderName();

boolean showComments = ParamUtil.getBoolean(request, "showComments", true);
boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);
%>

<%@ include file="/document_library/init-ext.jsp" %>