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
taglib uri="http://liferay.com/tld/commerce-ui" prefix="commerce-ui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.model.CommerceOrder" %><%@
page import="com.liferay.commerce.product.catalog.CPCatalogEntry" %><%@
page import="com.liferay.commerce.product.catalog.CPMedia" %><%@
page import="com.liferay.commerce.product.catalog.CPSku" %><%@
page import="com.liferay.commerce.product.constants.CPPortletKeys" %><%@
page import="com.liferay.commerce.product.content.constants.CPContentWebKeys" %><%@
page import="com.liferay.commerce.product.content.util.CPContentHelper" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue" %><%@
page import="com.liferay.commerce.product.model.CPInstance" %><%@
page import="com.liferay.commerce.product.model.CPOptionCategory" %><%@
page import="com.liferay.commerce.product.model.CPSpecificationOption" %><%@
page import="com.liferay.commerce.product.model.CPSubscriptionInfo" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingException" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingFileEntryIdException" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleException" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleFileEntryIdException" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingSampleUrlException" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseArticleResourcePKException" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseContentException" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingTermsOfUseException" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingUrlException" %><%@
page import="com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException" %><%@
page import="com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting" %><%@
page import="com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemException" %><%@
page import="com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemFileEntryIdException" %><%@
page import="com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemUrlException" %><%@
page import="com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem" %><%@
page import="com.liferay.commerce.product.type.virtual.util.VirtualCPTypeHelper" %><%@
page import="com.liferay.commerce.product.type.virtual.web.internal.display.context.CPDefinitionVirtualSettingDisplayContext" %><%@
page import="com.liferay.commerce.product.type.virtual.web.internal.display.context.CommerceVirtualOrderItemEditDisplayContext" %><%@
page import="com.liferay.commerce.product.type.virtual.web.internal.servlet.taglib.ui.constants.CPDefinitionVirtualSettingFormNavigatorConstants" %><%@
page import="com.liferay.journal.model.JournalArticle" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.security.auth.AuthTokenUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Time" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.List" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String lifecycle = (String)request.getAttribute(LiferayPortletRequest.LIFECYCLE_PHASE);

String catalogURL = String.valueOf(PortalUtil.getControlPanelPortletURL(request, CPPortletKeys.CP_DEFINITIONS, lifecycle));

String languageId = LanguageUtil.getLanguageId(locale);
%>