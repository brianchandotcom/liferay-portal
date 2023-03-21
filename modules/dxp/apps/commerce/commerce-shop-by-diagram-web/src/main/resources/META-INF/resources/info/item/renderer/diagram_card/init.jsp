<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ taglib uri="http://liferay.com/tld/adaptive-media-image" prefix="liferay-adaptive-media" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.constants.CommerceWebKeys" %><%@
page import="com.liferay.commerce.context.CommerceContext" %><%@
page import="com.liferay.commerce.product.catalog.CPCatalogEntry" %><%@
page import="com.liferay.commerce.product.constants.CPWebKeys" %><%@
page import="com.liferay.commerce.product.content.constants.CPContentWebKeys" %><%@
page import="com.liferay.commerce.product.content.util.CPContentHelper" %><%@
page import="com.liferay.commerce.shop.by.diagram.constants.CSDiagramWebKeys" %><%@
page import="com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting" %><%@
page import="com.liferay.commerce.shop.by.diagram.util.CSDiagramCPTypeHelper" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);
CPCatalogEntry cpCatalogEntry = (CPCatalogEntry)request.getAttribute(CPWebKeys.CP_CATALOG_ENTRY);
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);
CSDiagramCPTypeHelper csDiagramCPTypeHelper = (CSDiagramCPTypeHelper)request.getAttribute(CSDiagramWebKeys.CS_DIAGRAM_CP_TYPE_HELPER);
%>