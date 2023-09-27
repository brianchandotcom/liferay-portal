<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.frontend.data.set.views.web.internal.constants.FDSViewsWebKeys" %><%@
page import="com.liferay.frontend.data.set.views.web.internal.display.context.FDSViewItemSelectorDisplayContext" %><%@
page import="com.liferay.frontend.data.set.views.web.internal.display.context.FDSViewsDisplayContext" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
FDSViewItemSelectorDisplayContext fdsViewItemSelectorDisplayContext = (FDSViewItemSelectorDisplayContext)request.getAttribute(FDSViewsWebKeys.FDS_VIEW_ITEM_SELECTOR_DISPLAY_CONTEXT);
FDSViewsDisplayContext fdsViewsDisplayContext = (FDSViewsDisplayContext)request.getAttribute(FDSViewsWebKeys.FDS_VIEWS_DISPLAY_CONTEXT);
%>