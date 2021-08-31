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

<%@ include file="/init.jsp" %>

<%
CPInstanceCommerceTierPriceEntryDisplayContext cpInstanceCommerceTierPriceEntryDisplayContext = (CPInstanceCommerceTierPriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTierPriceEntry commerceTierPriceEntry = cpInstanceCommerceTierPriceEntryDisplayContext.getCommerceTierPriceEntry();
CommercePriceEntry commercePriceEntry = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntry();
CPDefinition cpDefinition = cpInstanceCommerceTierPriceEntryDisplayContext.getCPDefinition();
CPInstance cpInstance = cpInstanceCommerceTierPriceEntryDisplayContext.getCPInstance();
long commercePriceEntryId = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntryId();
long commerceTierPriceEntryId = cpInstanceCommerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryId();
String title = cpInstanceCommerceTierPriceEntryDisplayContext.getContextTitle();

CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

CommerceCurrency commerceCurrency = commercePriceList.getCommerceCurrency();

BigDecimal price = BigDecimal.ZERO;

if ((commerceTierPriceEntry != null) && (commerceTierPriceEntry.getPrice() != null)) {
	price = commerceCurrency.round(commerceTierPriceEntry.getPrice());
}
%>

<commerce-ui:modal-content
	title="<%= title %>"
>
	<portlet:actionURL name="/cp_definitions/edit_cp_instance_commerce_tier_price_entry" var="editCommerceTierPriceEntryActionURL" />

	<aui:form action="<%= editCommerceTierPriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
		<aui:input name="commerceTierPriceEntryId" type="hidden" value="<%= commerceTierPriceEntryId %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
		<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstance.getCPInstanceId() %>" />

		<liferay-ui:error exception="<%= DuplicateCommerceTierPriceEntryException.class %>" message="there-is-already-a-tier-price-entry-with-the-same-minimum-quantity" />

		<div class="row">
			<div class="col-12">
				<aui:input name="price" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= price %>">
					<aui:validator name="min">0</aui:validator>
					<aui:validator name="number" />
				</aui:input>

				<aui:input bean="<%= commerceTierPriceEntry %>" model="<%= CommerceTierPriceEntry.class %>" name="minQuantity">
					<aui:validator name="min">0</aui:validator>
				</aui:input>

				<c:if test="<%= cpInstanceCommerceTierPriceEntryDisplayContext.hasCustomAttributes() %>">
					<liferay-expando:custom-attribute-list
						className="<%= CommerceTierPriceEntry.class.getName() %>"
						classPK="<%= (commerceTierPriceEntry != null) ? commerceTierPriceEntry.getCommerceTierPriceEntryId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</c:if>
			</div>
		</div>
	</aui:form>
</commerce-ui:modal-content>