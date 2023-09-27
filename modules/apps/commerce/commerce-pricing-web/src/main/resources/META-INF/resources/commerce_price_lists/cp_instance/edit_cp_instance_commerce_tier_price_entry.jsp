<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPInstanceCommerceTierPriceEntryDisplayContext cpInstanceCommerceTierPriceEntryDisplayContext = (CPInstanceCommerceTierPriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTierPriceEntry commerceTierPriceEntry = cpInstanceCommerceTierPriceEntryDisplayContext.getCommerceTierPriceEntry();
CommercePriceEntry commercePriceEntry = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntry();
CPDefinition cpDefinition = cpInstanceCommerceTierPriceEntryDisplayContext.getCPDefinition();
CPInstance cpInstance = cpInstanceCommerceTierPriceEntryDisplayContext.getCPInstance();
%>

<commerce-ui:modal-content
	title="<%= cpInstanceCommerceTierPriceEntryDisplayContext.getContextTitle() %>"
>
	<portlet:actionURL name="/cp_definitions/edit_cp_instance_commerce_tier_price_entry" var="editCommerceTierPriceEntryActionURL" />

	<aui:form action="<%= editCommerceTierPriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commercePriceEntryId" type="hidden" value="<%= cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntryId() %>" />
		<aui:input name="commerceTierPriceEntryId" type="hidden" value="<%= cpInstanceCommerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryId() %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
		<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstance.getCPInstanceId() %>" />

		<liferay-ui:error exception="<%= DuplicateCommerceTierPriceEntryException.class %>" message="there-is-already-a-tier-price-entry-with-the-same-minimum-quantity" />

		<div class="row">
			<div class="col-12">

				<%
				CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

				CommerceCurrency commerceCurrency = commercePriceList.getCommerceCurrency();

				BigDecimal price = BigDecimal.ZERO;

				if ((commerceTierPriceEntry != null) && (commerceTierPriceEntry.getPrice() != null)) {
					price = commerceCurrency.round(commerceTierPriceEntry.getPrice());
				}
				%>

				<aui:input name="price" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= price %>">
					<aui:validator name="min">0</aui:validator>
					<aui:validator name="number" />
				</aui:input>

				<%
				BigDecimal minQuantity = BigDecimal.ZERO;

				if ((commerceTierPriceEntry != null) && (commerceTierPriceEntry.getMinQuantity() != null)) {
					minQuantity = commerceTierPriceEntry.getMinQuantity();
				}
				%>

				<aui:input name="minQuantity" value="<%= minQuantity.intValue() %>">
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