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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.constants.CommerceActionKeys" %><%@
page import="com.liferay.commerce.constants.CommercePortletKeys" %><%@
page import="com.liferay.commerce.exception.CommerceShipmentItemQuantityException" %><%@
page import="com.liferay.commerce.exception.NoSuchShipmentException" %><%@
page import="com.liferay.commerce.exception.NoSuchShipmentItemException" %><%@
page import="com.liferay.commerce.model.CommerceAddress" %><%@
page import="com.liferay.commerce.model.CommerceRegion" %><%@
page import="com.liferay.commerce.model.CommerceShipment" %><%@
page import="com.liferay.commerce.model.CommerceShipmentItem" %><%@
page import="com.liferay.commerce.model.CommerceShippingMethod" %><%@
page import="com.liferay.commerce.model.CommerceWarehouse" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.commerce.product.model.CPInstance" %><%@
page import="com.liferay.commerce.service.permission.CommerceShipmentPermission" %><%@
page import="com.liferay.commerce.shipment.web.internal.display.context.CommerceShipmentDisplayContext" %><%@
page import="com.liferay.commerce.shipment.web.internal.display.context.CommerceShipmentItemDisplayContext" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String lifecycle = (String)request.getAttribute(liferayPortletRequest.LIFECYCLE_PHASE);

PortletURL shipmentsURLObj = PortalUtil.getControlPanelPortletURL(request, CommercePortletKeys.COMMERCE_SHIPMENT, lifecycle);

String shipmentsURL = shipmentsURLObj.toString();

String languageId = LanguageUtil.getLanguageId(locale);

boolean hasManageCommerceShipmentsPermission = CommerceShipmentPermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);
%>