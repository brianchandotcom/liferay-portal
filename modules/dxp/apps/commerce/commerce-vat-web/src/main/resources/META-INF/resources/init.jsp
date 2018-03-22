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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.vat.constants.CommerceVatActionKeys" %><%@
page import="com.liferay.commerce.vat.constants.CommerceVatPortletKeys" %><%@
page import="com.liferay.commerce.vat.constants.CommerceVatWebKeys" %><%@
page import="com.liferay.commerce.vat.exception.NoSuchVatNumberException" %><%@
page import="com.liferay.commerce.vat.model.CommerceVatNumber" %><%@
page import="com.liferay.commerce.vat.web.internal.display.context.CommerceVatNumberDisplayContext" %><%@
page import="com.liferay.commerce.vat.web.internal.security.permission.resource.CommerceVatPermission" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Organization" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String lifecycle = (String)request.getAttribute(liferayPortletRequest.LIFECYCLE_PHASE);

PortletURL vatNumbersURLObj = PortalUtil.getControlPanelPortletURL(request, CommerceVatPortletKeys.COMMERCE_VAT_NUMBER, lifecycle);

String vatNumbersURL = vatNumbersURLObj.toString();

boolean hasManageCommerceVatNumbersPermission = CommerceVatPermission.contains(permissionChecker, scopeGroupId, CommerceVatActionKeys.MANAGE_COMMERCE_VAT_NUMBERS);
%>