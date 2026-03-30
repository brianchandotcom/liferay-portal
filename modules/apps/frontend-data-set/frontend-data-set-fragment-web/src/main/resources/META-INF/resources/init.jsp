<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.frontend.data.set.fragment.web.internal.constants.FDSFragmentWebKeys" %><%@
page import="com.liferay.frontend.data.set.fragment.web.internal.display.context.FDSFragmentItemSelectorDisplayContext" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
FDSFragmentItemSelectorDisplayContext fdsFragmentItemSelectorDisplayContext = (FDSFragmentItemSelectorDisplayContext)request.getAttribute(FDSFragmentWebKeys.FDS_FRAGMENT_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>