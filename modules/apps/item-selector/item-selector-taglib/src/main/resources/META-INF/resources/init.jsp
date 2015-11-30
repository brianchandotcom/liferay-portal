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
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.item.selector.ItemSelector" %><%@
page import="com.liferay.item.selector.ItemSelectorCriterion" %><%@
page import="com.liferay.item.selector.ItemSelectorReturnType" %><%@
page import="com.liferay.item.selector.taglib.ItemSelectorRepositoryEntryBrowserReturnTypeUtil" %><%@
page import="com.liferay.item.selector.taglib.dao.search.RepositoryEntryResultRowSplitter" %><%@
page import="com.liferay.item.selector.taglib.servlet.ServletContextUtil" %><%@
page import="com.liferay.item.selector.taglib.servlet.taglib.RepositoryEntryBrowserTag" %><%@
page import="com.liferay.item.selector.taglib.util.ItemSelectorRepositoryEntryBrowserUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.repository.model.FileShortcut" %><%@
page import="com.liferay.portal.kernel.repository.model.FileVersion" %><%@
page import="com.liferay.portal.kernel.repository.model.Folder" %><%@
page import="com.liferay.portal.kernel.util.ClassUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.RequestBackedPortletURLFactory" %><%@
page import="com.liferay.portlet.RequestBackedPortletURLFactoryUtil" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntry" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFolderConstants" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLAppServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DLUtil" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupSearch" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<portlet:defineObjects />

<liferay-theme:defineObjects/>