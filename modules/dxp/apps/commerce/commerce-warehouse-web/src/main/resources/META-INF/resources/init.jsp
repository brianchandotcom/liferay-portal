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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.admin.constants.CommerceAdminWebKeys" %><%@
page import="com.liferay.commerce.constants.CommerceActionKeys" %><%@
page import="com.liferay.commerce.exception.CommerceGeocoderException" %><%@
page import="com.liferay.commerce.exception.CommerceWarehouseActiveException" %><%@
page import="com.liferay.commerce.exception.CommerceWarehouseCommerceRegionIdException" %><%@
page import="com.liferay.commerce.exception.CommerceWarehouseNameException" %><%@
page import="com.liferay.commerce.model.CommerceWarehouse" %><%@
page import="com.liferay.commerce.model.CommerceWarehouseItem" %><%@
page import="com.liferay.commerce.product.constants.CPPortletKeys" %><%@
page import="com.liferay.commerce.product.model.CPInstance" %><%@
page import="com.liferay.commerce.service.permission.CommercePermission" %><%@
page import="com.liferay.commerce.warehouse.web.internal.admin.WarehousesCommerceAdminModule" %><%@
page import="com.liferay.commerce.warehouse.web.internal.display.context.CommerceWarehouseItemsDisplayContext" %><%@
page import="com.liferay.commerce.warehouse.web.internal.display.context.CommerceWarehousesDisplayContext" %><%@
page import="com.liferay.commerce.warehouse.web.internal.servlet.taglib.ui.CommerceWarehouseFormNavigatorConstants" %><%@
page import="com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String commerceAdminModuleKey = WarehousesCommerceAdminModule.KEY;

String lifecycle = (String)request.getAttribute(liferayPortletRequest.LIFECYCLE_PHASE);

PortletURL catalogURLObj = PortalUtil.getControlPanelPortletURL(request, CPPortletKeys.CP_DEFINITIONS, lifecycle);

String catalogURL = catalogURLObj.toString();
%>