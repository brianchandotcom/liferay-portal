<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.item.selector.web.internal.display.context.CommerceCountryItemSelectorViewDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.CommerceCountryItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.CommerceInventoryWarehouseItemSelectorViewDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.CommerceInventoryWarehouseManagementToolbarDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.CommercePriceListItemSelectorViewDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.CommercePriceListItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.CommercePricingClassItemSelectorViewDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.CommercePricingClassItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.CommerceProductInstanceItemSelectorViewDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.CommerceProductInstanceItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.SimpleSiteItemSelectorViewDisplayContext" %><%@
page import="com.liferay.commerce.item.selector.web.internal.display.context.SimpleSiteItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />