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
CPInstanceCommerceTierPriceEntryDisplayContext cpInstanceCommerceTierPriceEntryDisplayContext = (CPInstanceCommerceTierPriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTierPriceEntry commerceTierPriceEntry = cpInstanceCommerceTierPriceEntryDisplayContext.getCommerceTierPriceEntry();

CommercePriceEntry commercePriceEntry = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntry();

CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

CommerceCurrency commerceCurrency = commercePriceList.getCommerceCurrency();

CPDefinition cpDefinition = cpInstanceCommerceTierPriceEntryDisplayContext.getCPDefinition();

CPInstance cpInstance = cpInstanceCommerceTierPriceEntryDisplayContext.getCPInstance();

long commercePriceEntryId = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntryId();
long commerceTierPriceEntryId = cpInstanceCommerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryId();

PortletURL productSkusURL = cpInstanceCommerceTierPriceEntryDisplayContext.getProductSkusURL();

PortletURL instancePriceListsURL = cpInstanceCommerceTierPriceEntryDisplayContext.getInstancePriceListURL();

PortletURL instanceTierPriceEntriesURL = cpInstanceCommerceTierPriceEntryDisplayContext.getInstanceTierPriceEntriesURL();

String title = cpInstanceCommerceTierPriceEntryDisplayContext.getContextTitle();

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "products"), catalogURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, cpDefinition.getName(languageId), String.valueOf(cpInstanceCommerceTierPriceEntryDisplayContext.getEditProductDefinitionURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS), productSkusURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, cpInstance.getSku(), instancePriceListsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, commercePriceList.getName(), instanceTierPriceEntriesURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= CPNavigationItemRegistryUtil.getNavigationItems(renderRequest) %>"
/>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCPInstanceCommerceTierPriceEntry" var="editCommerceTierPriceEntryActionURL" />

<aui:form action="<%= editCommerceTierPriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= instanceTierPriceEntriesURL.toString() %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="commerceTierPriceEntryId" type="hidden" value="<%= commerceTierPriceEntryId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceTierPriceEntry %>" model="<%= CommerceTierPriceEntry.class %>" />

		<aui:fieldset-group>
			<aui:fieldset>
				<aui:input name="price" suffix="<%= commerceCurrency.getCode() %>" type="text" value="<%= (commerceTierPriceEntry == null) ? cpInstanceCommerceTierPriceEntryDisplayContext.format(BigDecimal.ZERO) : cpInstanceCommerceTierPriceEntryDisplayContext.format(commerceTierPriceEntry.getPrice()) %>" />

				<aui:input name="promoPrice" suffix="<%= commerceCurrency.getCode() %>" type="text" value="<%= (commerceTierPriceEntry == null) ? cpInstanceCommerceTierPriceEntryDisplayContext.format(BigDecimal.ZERO) : cpInstanceCommerceTierPriceEntryDisplayContext.format(commerceTierPriceEntry.getPromoPrice()) %>" />

				<aui:input name="minQuantity" />
			</aui:fieldset>
		</aui:fieldset-group>

		<c:if test="<%= cpInstanceCommerceTierPriceEntryDisplayContext.hasCustomAttributes() %>">
			<aui:fieldset>
				<liferay-expando:custom-attribute-list
					className="<%= CommerceTierPriceEntry.class.getName() %>"
					classPK="<%= (commerceTierPriceEntry != null) ? commerceTierPriceEntry.getCommerceTierPriceEntryId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</aui:fieldset>
		</c:if>

		<aui:button-row cssClass="tier-price-entry-button-row">
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= instanceTierPriceEntriesURL.toString() %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>