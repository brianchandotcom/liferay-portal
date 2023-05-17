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