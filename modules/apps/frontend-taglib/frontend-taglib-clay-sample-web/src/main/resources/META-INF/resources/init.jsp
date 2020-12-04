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

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.clay.sample.web.constants.ClaySamplePortletKeys" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.CardsDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleFileCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleHorizontalCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleImageCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleNavigationCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleUserCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleVerticalCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.DropdownsDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ManagementToolbarsDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.MultiSelectDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.NavigationBarsDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.List" %>

<liferay-theme:defineObjects />

<%@ include file="/init-ext.jsp" %>

<%
CardsDisplayContext cardsDisplayContext = (CardsDisplayContext)request.getAttribute(ClaySamplePortletKeys.CARDS_DISPLAY_CONTEXT);
MultiSelectDisplayContext multiSelectDisplayContext = (MultiSelectDisplayContext)request.getAttribute(ClaySamplePortletKeys.MULTI_SELECT_DISPLAY_CONTEXT);
DropdownsDisplayContext dropdownsDisplayContext = (DropdownsDisplayContext)request.getAttribute(ClaySamplePortletKeys.DROPDOWNS_DISPLAY_CONTEXT);
ManagementToolbarsDisplayContext managementToolbarsDisplayContext = (ManagementToolbarsDisplayContext)request.getAttribute(ClaySamplePortletKeys.MANAGEMENT_TOOLBARS_DISPLAY_CONTEXT);
NavigationBarsDisplayContext navigationBarsDisplayContext = (NavigationBarsDisplayContext)request.getAttribute(ClaySamplePortletKeys.NAVIGATION_BARS_DISPLAY_CONTEXT);
%>