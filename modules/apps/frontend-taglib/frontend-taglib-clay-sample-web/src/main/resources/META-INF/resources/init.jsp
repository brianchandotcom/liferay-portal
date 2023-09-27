<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.clay.sample.web.constants.ClaySamplePortletKeys" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.CardsDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleFileCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleHorizontalCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleImageCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleManagementToolbarsDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleNavigationCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleUserCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ClaySampleVerticalCard" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.DropdownsDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.MultiselectDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.NavigationBarsDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.TabsDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.sample.web.internal.display.context.VerticalNavDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.PaginationBarDelta" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.PaginationBarLabels" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.List" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%@ include file="/init-ext.jsp" %>

<%
CardsDisplayContext cardsDisplayContext = (CardsDisplayContext)request.getAttribute(ClaySamplePortletKeys.CARDS_DISPLAY_CONTEXT);
ClaySampleDisplayContext claySampleDisplayContext = (ClaySampleDisplayContext)request.getAttribute(ClaySamplePortletKeys.CLAY_SAMPLE_DISPLAY_CONTEXT);
DropdownsDisplayContext dropdownsDisplayContext = (DropdownsDisplayContext)request.getAttribute(ClaySamplePortletKeys.DROPDOWNS_DISPLAY_CONTEXT);
MultiselectDisplayContext multiselectDisplayContext = (MultiselectDisplayContext)request.getAttribute(ClaySamplePortletKeys.MULTISELECT_DISPLAY_CONTEXT);
NavigationBarsDisplayContext navigationBarsDisplayContext = (NavigationBarsDisplayContext)request.getAttribute(ClaySamplePortletKeys.NAVIGATION_BARS_DISPLAY_CONTEXT);
TabsDisplayContext tabsDisplayContext = (TabsDisplayContext)request.getAttribute(ClaySamplePortletKeys.TABS_DISPLAY_CONTEXT);
VerticalNavDisplayContext verticalNavDisplayContext = (VerticalNavDisplayContext)request.getAttribute(ClaySamplePortletKeys.VERTICAL_NAV_DISPLAY_CONTEXT);
%>