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
taglib uri="http://liferay.com/tld/soy" prefix="soy" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.fragment.constants.FragmentActionKeys" %><%@
page import="com.liferay.fragment.contributor.FragmentCollectionContributor" %><%@
page import="com.liferay.fragment.exception.DuplicateFragmentCollectionException" %><%@
page import="com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException" %><%@
page import="com.liferay.fragment.exception.DuplicateFragmentEntryKeyException" %><%@
page import="com.liferay.fragment.exception.FragmentCollectionNameException" %><%@
page import="com.liferay.fragment.exception.InvalidFileException" %><%@
page import="com.liferay.fragment.exception.RequiredFragmentEntryException" %><%@
page import="com.liferay.fragment.model.FragmentCollection" %><%@
page import="com.liferay.fragment.model.FragmentEntry" %><%@
page import="com.liferay.fragment.renderer.FragmentRendererController" %><%@
page import="com.liferay.fragment.service.FragmentCollectionLocalServiceUtil" %><%@
page import="com.liferay.fragment.web.internal.constants.FragmentWebKeys" %><%@
page import="com.liferay.fragment.web.internal.dao.search.FragmentEntryResultRowSplitter" %><%@
page import="com.liferay.fragment.web.internal.display.context.ContributedFragmentManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.EditFragmentEntryDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentCollectionResourcesDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentCollectionResourcesManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentCollectionsDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentCollectionsManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentEntryLinkDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentEntryUsageManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentManagementToolbarDisplayContextFactory" %><%@
page import="com.liferay.fragment.web.internal.display.context.GroupFragmentEntryLinkDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.GroupFragmentEntryUsageManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.RenderFragmentEntryDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.SelectFragmentCollectionDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.display.context.SelectFragmentCollectionManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission" %><%@
page import="com.liferay.fragment.web.internal.servlet.taglib.clay.ContributedFragmentEntryVerticalCard" %><%@
page import="com.liferay.fragment.web.internal.servlet.taglib.clay.FragmentCollectionHorizontalCard" %><%@
page import="com.liferay.fragment.web.internal.servlet.taglib.clay.FragmentCollectionResourceVerticalCard" %><%@
page import="com.liferay.fragment.web.internal.servlet.taglib.clay.FragmentEntryVerticalCardFactory" %><%@
page import="com.liferay.frontend.taglib.servlet.taglib.util.EmptyResultMessageKeys" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.MapUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
FragmentDisplayContext fragmentDisplayContext = new FragmentDisplayContext(renderRequest, renderResponse, request);
%>

<%@ include file="/init-ext.jsp" %>