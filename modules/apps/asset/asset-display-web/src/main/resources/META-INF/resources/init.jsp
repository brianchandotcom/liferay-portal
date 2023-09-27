<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.asset.display.web.internal.display.context.AssetDisplayDisplayContext" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
AssetDisplayDisplayContext assetDisplayDisplayContext = new AssetDisplayDisplayContext(request);
%>

<%@ include file="/init-ext.jsp" %>