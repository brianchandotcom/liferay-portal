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

<%@ include file="/init.jsp" %>

<%
CPInstanceCommercePriceEntryDisplayContext cpInstanceCommercePriceEntryDisplayContext = (CPInstanceCommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceEntry commercePriceEntry = cpInstanceCommercePriceEntryDisplayContext.getCommercePriceEntry();

CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

CPDefinition cpDefinition = cpInstanceCommercePriceEntryDisplayContext.getCPDefinition();

CPInstance cpInstance = cpInstanceCommercePriceEntryDisplayContext.getCPInstance();

long commercePriceEntryId = commercePriceEntry.getCommercePriceEntryId();
long cpDefinitionId = cpInstanceCommercePriceEntryDisplayContext.getCPDefinitionId();
long cpInstanceId = cpInstanceCommercePriceEntryDisplayContext.getCPInstanceId();

String instancePriceEntryToolbarItem = ParamUtil.getString(request, "instancePriceEntryToolbarItem", "view-price-entry-details");

PortletURL productSkusURL = cpInstanceCommercePriceEntryDisplayContext.getProductSkusURL();

PortletURL instancePriceListsURL = cpInstanceCommercePriceEntryDisplayContext.getInstancePriceListURL();

String title = commercePriceList.getName();

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "products"), catalogURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, cpDefinition.getName(languageId), String.valueOf(cpInstanceCommercePriceEntryDisplayContext.getEditProductDefinitionURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS), productSkusURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, cpInstance.getSku(), instancePriceListsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= CPNavigationItemRegistryUtil.getNavigationItems(renderRequest) %>"
/>

<%@ include file="/breadcrumb.jspf" %>
<%@ include file="/instance_price_entry_navbar.jspf" %>

<portlet:actionURL name="editCPInstanceCommercePriceEntry" var="editCommercePriceEntryActionURL" />

<aui:form action="<%= editCommercePriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= instancePriceListsURL.toString() %>"
			formModelBean="<%= commercePriceEntry %>"
			id="<%= CommercePriceEntryFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRICE_ENTRY %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>