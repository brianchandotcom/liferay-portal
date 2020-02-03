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
taglib uri="http://liferay.com/tld/item-selector" prefix="liferay-item-selector" %><%@
taglib uri="http://liferay.com/tld/site-navigation" prefix="liferay-site-navigation" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem" %><%@
page import="com.liferay.item.selector.ItemSelectorViewDescriptor" %><%@
page import="com.liferay.item.selector.ItemSelectorViewRenderer" %><%@
page import="com.liferay.item.selector.web.internal.display.context.ItemSelectorViewDescriptorRendererDisplayContext" %><%@
page import="com.liferay.item.selector.web.internal.display.context.ItemSelectorViewDescriptorRendererManagementToolbarDisplayContext" %><%@
page import="com.liferay.item.selector.web.internal.portlet.LocalizedItemSelectorRendering" %><%@
page import="com.liferay.item.selector.web.internal.servlet.taglib.clay.ItemDescriptorHorizontalCard" %><%@
page import="com.liferay.item.selector.web.internal.servlet.taglib.clay.ItemDescriptorVerticalCard" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.model.UserConstants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.util.Date" %><%@
page import="java.util.List" %><%@
page import="java.util.Objects" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />