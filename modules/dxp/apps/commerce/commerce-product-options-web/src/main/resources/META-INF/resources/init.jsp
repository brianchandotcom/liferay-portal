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
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/expando" prefix="liferay-expando" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.product.constants.CPPortletKeys" %><%@
page import="com.liferay.commerce.product.constants.CPWebKeys" %><%@
page import="com.liferay.commerce.product.exception.CPOptionCategoryKeyException" %><%@
page import="com.liferay.commerce.product.exception.CPOptionKeyException" %><%@
page import="com.liferay.commerce.product.exception.CPOptionValueKeyException" %><%@
page import="com.liferay.commerce.product.exception.CPSpecificationOptionKeyException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPOptionException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPOptionValueException" %><%@
page import="com.liferay.commerce.product.model.CPOption" %><%@
page import="com.liferay.commerce.product.model.CPOptionCategory" %><%@
page import="com.liferay.commerce.product.model.CPOptionValue" %><%@
page import="com.liferay.commerce.product.model.CPSpecificationOption" %><%@
page import="com.liferay.commerce.product.options.web.internal.display.context.CPOptionCategoryDisplayContext" %><%@
page import="com.liferay.commerce.product.options.web.internal.display.context.CPOptionDisplayContext" %><%@
page import="com.liferay.commerce.product.options.web.internal.display.context.CPSpecificationOptionDisplayContext" %><%@
page import="com.liferay.commerce.product.options.web.internal.servlet.taglib.ui.CPOptionCategoryFormNavigatorConstants" %><%@
page import="com.liferay.commerce.product.options.web.internal.servlet.taglib.ui.CPSpecificationOptionFormNavigatorConstants" %><%@
page import="com.liferay.commerce.product.util.CPNavigationItemRegistryUtil" %><%@
page import="com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.util.CustomAttributesUtil" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Collections" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String lifecycle = (String)request.getAttribute(liferayPortletRequest.LIFECYCLE_PHASE);

PortletURL optionsURLObj = PortalUtil.getControlPanelPortletURL(request, CPPortletKeys.CP_OPTIONS, lifecycle);
PortletURL optionCategoriesURLObj = PortalUtil.getControlPanelPortletURL(request, CPPortletKeys.CP_OPTION_CATEGORIES, lifecycle);
PortletURL specificationOptionsURLObj = PortalUtil.getControlPanelPortletURL(request, CPPortletKeys.CP_SPECIFICATION_OPTIONS, lifecycle);

String optionsURL = optionsURLObj.toString();
String optionCategoriesURL = optionCategoriesURLObj.toString();
String specificationOptionsURL = specificationOptionsURLObj.toString();
%>