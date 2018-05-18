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
CommercePriceEntryDisplayContext commercePriceEntryDisplayContext = (CommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceEntry commercePriceEntry = commercePriceEntryDisplayContext.getCommercePriceEntry();

CPInstance cpInstance = commercePriceEntry.getCPInstance();
CPDefinition cpDefinition = cpInstance.getCPDefinition();

CommercePriceList commercePriceList = commercePriceEntryDisplayContext.getCommercePriceList();

long commercePriceEntryId = commercePriceEntryDisplayContext.getCommercePriceEntryId();
long commercePriceListId = commercePriceEntryDisplayContext.getCommercePriceListId();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-price-entry-details");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "editCommercePriceEntry");
portletURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
portletURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));
portletURL.setParameter("toolbarItem", toolbarItem);

PortletURL editPriceListURL = renderResponse.createRenderURL();

editPriceListURL.setParameter("mvcRenderCommandName", "editCommercePriceList");
editPriceListURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));

String title = cpDefinition.getName(languageId);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "price-lists"), priceListsURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, commercePriceList.getName(), editPriceListURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, portletURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "details"), StringPool.BLANK, data);

editPriceListURL.setParameter("screenNavigationCategoryKey", String.valueOf(CommercePriceListScreenNavigationConstants.CATEGORY_KEY_ENTRIES));

renderResponse.setTitle(LanguageUtil.get(request, "price-lists"));
%>

<%@ include file="/price_list_navbar.jspf" %>

<%@ include file="/breadcrumb.jspf" %>
<%@ include file="/price_entry_navbar.jspf" %>

<portlet:actionURL name="editCommercePriceEntry" var="editCommercePriceEntryActionURL" />

<aui:form action="<%= editCommercePriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListId %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= editPriceListURL.toString() %>"
			formModelBean="<%= commercePriceEntry %>"
			id="<%= CommercePriceEntryFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRICE_ENTRY %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>