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

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/expando" prefix="liferay-expando" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/item-selector" prefix="liferay-item-selector" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/soy" prefix="soy" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/trash" prefix="liferay-trash" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.commerce.product.constants.CPConstants" %><%@
page import="com.liferay.commerce.product.constants.CPPortletKeys" %><%@
page import="com.liferay.commerce.product.constants.CPWebKeys" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPAttachmentFileEntriesDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPDefinitionLinkDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPDefinitionOptionRelDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPDefinitionOptionValueRelDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPDefinitionShippingInfoDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPDefinitionSpecificationOptionValueDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPDefinitionTaxCategoryDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPDefinitionsDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPInstanceDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPInstancePricingInfoDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.display.context.CPInstanceShippingInfoDisplayContext" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.servlet.taglib.ui.CPAttachmentFileEntryFormNavigatorConstants" %><%@
page import="com.liferay.commerce.product.definitions.web.internal.servlet.taglib.ui.CPDefinitionSpecificationOptionValueFormNavigatorConstants" %><%@
page import="com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants" %><%@
page import="com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPInstanceScreenNavigationConstants" %><%@
page import="com.liferay.commerce.product.exception.CPAttachmentFileEntryExpirationDateException" %><%@
page import="com.liferay.commerce.product.exception.CPAttachmentFileEntryFileEntryIdException" %><%@
page import="com.liferay.commerce.product.exception.CPDefinitionIgnoreSKUCombinationsException" %><%@
page import="com.liferay.commerce.product.exception.CPDefinitionMetaDescriptionException" %><%@
page import="com.liferay.commerce.product.exception.CPDefinitionMetaKeywordsException" %><%@
page import="com.liferay.commerce.product.exception.CPDefinitionMetaTitleException" %><%@
page import="com.liferay.commerce.product.exception.CPDefinitionOptionValueRelKeyException" %><%@
page import="com.liferay.commerce.product.exception.CPDefinitionProductTypeNameException" %><%@
page import="com.liferay.commerce.product.exception.CPFriendlyURLEntryException" %><%@
page import="com.liferay.commerce.product.exception.CPInstanceDDMContentException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPDefinitionException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPInstanceException" %><%@
page import="com.liferay.commerce.product.exception.NoSuchSkuContributorCPDefinitionOptionRelException" %><%@
page import="com.liferay.commerce.product.model.CPAttachmentFileEntry" %><%@
page import="com.liferay.commerce.product.model.CPAttachmentFileEntryConstants" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.commerce.product.model.CPDefinitionLink" %><%@
page import="com.liferay.commerce.product.model.CPDefinitionOptionRel" %><%@
page import="com.liferay.commerce.product.model.CPDefinitionOptionValueRel" %><%@
page import="com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue" %><%@
page import="com.liferay.commerce.product.model.CPInstance" %><%@
page import="com.liferay.commerce.product.model.CPMeasurementUnitConstants" %><%@
page import="com.liferay.commerce.product.model.CPOptionCategory" %><%@
page import="com.liferay.commerce.product.model.CPSpecificationOption" %><%@
page import="com.liferay.commerce.product.model.CPTaxCategory" %><%@
page import="com.liferay.commerce.product.type.CPType" %><%@
page import="com.liferay.commerce.product.util.CPNavigationItemRegistryUtil" %><%@
page import="com.liferay.document.library.kernel.util.DLUtil" %><%@
page import="com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Layout" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.trash.kernel.util.TrashUtil" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Collections" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.StringJoiner" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String lifecycle = (String)request.getAttribute(liferayPortletRequest.LIFECYCLE_PHASE);

PortletURL catalogURLObj = PortalUtil.getControlPanelPortletURL(request, CPPortletKeys.CP_DEFINITIONS, lifecycle);

String catalogURL = catalogURLObj.toString();

String languageId = LanguageUtil.getLanguageId(locale);
%>