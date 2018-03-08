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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.constants.CommercePortletKeys" %><%@
page import="com.liferay.commerce.model.CommercePriceList" %><%@
page import="com.liferay.commerce.model.CommercePriceListQualificationTypeRel" %><%@
page import="com.liferay.commerce.price.list.qualification.type.web.internal.display.context.OrganizationPriceListQualificationTypeDisplayContext" %><%@
page import="com.liferay.commerce.price.list.qualification.type.web.internal.display.context.RolePriceListQualificationTypeDisplayContext" %><%@
page import="com.liferay.commerce.price.list.qualification.type.web.internal.display.context.UserGroupPriceListQualificationTypeDisplayContext" %><%@
page import="com.liferay.commerce.price.list.qualification.type.web.internal.display.context.UserPriceListQualificationTypeDisplayContext" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Organization" %><%@
page import="com.liferay.portal.kernel.model.Role" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.model.UserGroup" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String lifecycle = (String)request.getAttribute(liferayPortletRequest.LIFECYCLE_PHASE);

PortletURL priceListsURLObj = PortalUtil.getControlPanelPortletURL(request, CommercePortletKeys.COMMERCE_PRICE_LIST, lifecycle);

String priceListsURL = priceListsURLObj.toString();
%>