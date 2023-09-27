<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPInstanceCommercePriceEntryDisplayContext cpInstanceCommercePriceEntryDisplayContext = (CPInstanceCommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceEntry commercePriceEntry = cpInstanceCommercePriceEntryDisplayContext.getCommercePriceEntry();

long commercePriceEntryId = commercePriceEntry.getCommercePriceEntryId();

CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

CommerceCurrency commerceCurrency = commercePriceList.getCommerceCurrency();
%>

<portlet:actionURL name="/cp_definitions/edit_cp_instance_commerce_price_entry" var="editCommercePriceEntryActionURL" />

<aui:form action="<%= editCommercePriceEntryActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceCommercePriceEntryDisplayContext.getCPInstanceId() %>" />

	<aui:model-context bean="<%= commercePriceEntry %>" model="<%= CommercePriceEntry.class %>" />

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:input name="price" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= commerceCurrency.round(commercePriceEntry.getPrice()) %>">
					<aui:validator name="min">0</aui:validator>
					<aui:validator name="number" />
				</aui:input>
			</commerce-ui:panel>

			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "tier-price-settings") %>'
			>
				<aui:fieldset collapsible="<%= false %>" cssClass="price-entry-price-settings">
					<div class="row">
						<div class="col-12">
							<aui:input checked="<%= commercePriceEntry.isBulkPricing() %>" label="bulk-pricing" name="bulkPricing" type="radio" value="<%= true %>" />
						</div>

						<div class="col-12">
							<aui:input checked="<%= !commercePriceEntry.isBulkPricing() %>" label="tiered-pricing" name="bulkPricing" type="radio" value="<%= false %>" />
						</div>
					</div>
				</aui:fieldset>
			</commerce-ui:panel>

			<c:if test="<%= CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), CommercePriceEntry.class.getName(), commercePriceEntryId, null) %>">
				<commerce-ui:panel
					title='<%= LanguageUtil.get(request, "custom-attribute") %>'
				>
					<liferay-expando:custom-attribute-list
						className="<%= CommercePriceEntry.class.getName() %>"
						classPK="<%= (commercePriceEntry != null) ? commercePriceEntry.getCommercePriceEntryId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</commerce-ui:panel>
			</c:if>
		</div>
	</div>

	<aui:button-row cssClass="price-entry-button-row">
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>