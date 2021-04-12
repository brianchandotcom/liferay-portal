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
taglib uri="http://liferay.com/tld/commerce-ui" prefix="commerce-ui" %><%@
taglib uri="http://liferay.com/tld/expando" prefix="liferay-expando" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.currency.model.CommerceCurrency" %><%@
page import="com.liferay.commerce.discount.constants.CommerceDiscountConstants" %><%@
page import="com.liferay.commerce.discount.exception.CommerceDiscountCouponCodeException" %><%@
page import="com.liferay.commerce.discount.exception.CommerceDiscountExpirationDateException" %><%@
page import="com.liferay.commerce.discount.exception.DuplicateCommerceDiscountException" %><%@
page import="com.liferay.commerce.discount.model.CommerceDiscount" %><%@
page import="com.liferay.commerce.discount.model.CommerceDiscountRule" %><%@
page import="com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType" %><%@
page import="com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributor" %><%@
page import="com.liferay.commerce.discount.target.CommerceDiscountTarget" %><%@
page import="com.liferay.commerce.price.list.constants.CommercePriceListConstants" %><%@
page import="com.liferay.commerce.price.list.exception.CommercePriceListCurrencyException" %><%@
page import="com.liferay.commerce.price.list.exception.CommercePriceListExpirationDateException" %><%@
page import="com.liferay.commerce.price.list.exception.CommercePriceListParentPriceListGroupIdException" %><%@
page import="com.liferay.commerce.price.list.exception.DuplicateCommerceTierPriceEntryException" %><%@
page import="com.liferay.commerce.price.list.model.CommercePriceEntry" %><%@
page import="com.liferay.commerce.price.list.model.CommercePriceList" %><%@
page import="com.liferay.commerce.price.list.model.CommerceTierPriceEntry" %><%@
page import="com.liferay.commerce.pricing.constants.CommercePriceModifierConstants" %><%@
page import="com.liferay.commerce.pricing.exception.CommercePriceModifierExpirationDateException" %><%@
page import="com.liferay.commerce.pricing.exception.NoSuchPricingClassException" %><%@
page import="com.liferay.commerce.pricing.model.CommercePriceModifier" %><%@
page import="com.liferay.commerce.pricing.model.CommercePricingClass" %><%@
page import="com.liferay.commerce.pricing.type.CommercePriceModifierType" %><%@
page import="com.liferay.commerce.pricing.web.internal.constants.CommerceDiscountScreenNavigationConstants" %><%@
page import="com.liferay.commerce.pricing.web.internal.constants.CommercePricingClassScreenNavigationConstants" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.AddedAllCommerceDiscountRuleDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.AddedAnyCommerceDiscountRuleDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CPDefinitionPricingClassDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CartTotalCommerceDiscountRuleDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommerceDiscountDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommerceDiscountQualifiersDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommerceDiscountRuleDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePriceEntryDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePriceListDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePriceListQualifiersDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePricingClassCPDefinitionDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePricingClassDiscountDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePricingClassDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommercePricingClassPriceListDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.display.context.CommerceTierCommercePriceEntryDisplayContext" %><%@
page import="com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants" %><%@
page import="com.liferay.commerce.pricing.web.internal.servlet.taglib.ui.constants.CommercePriceListScreenNavigationConstants" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCatalogException" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.commerce.product.model.CProduct" %><%@
page import="com.liferay.commerce.product.model.CommerceCatalog" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.taglib.servlet.PipingServletResponse" %><%@
page import="com.liferay.taglib.util.CustomAttributesUtil" %>

<%@ page import="java.math.BigDecimal" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Objects" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);
%>