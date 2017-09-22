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

CPDefinition cpDefinition = cpInstanceCommerceTierPriceEntryDisplayContext.getCPDefinition();

CPInstance cpInstance = cpInstanceCommerceTierPriceEntryDisplayContext.getCPInstance();

long commercePriceEntryId = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntryId();
long commerceTierPriceEntryId = cpInstanceCommerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryId();
long cpDefinitionId = cpInstanceCommerceTierPriceEntryDisplayContext.getCPDefinitionId();
long cpInstanceId = cpInstanceCommerceTierPriceEntryDisplayContext.getCPInstanceId();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-tier-price-entries");

PortletURL instanceTierPriceEntriesURL = renderResponse.createRenderURL();

instanceTierPriceEntriesURL.setParameter("mvcRenderCommandName", "viewCPInstanceCommerceTierPriceEntries");
instanceTierPriceEntriesURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
instanceTierPriceEntriesURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionId));
instanceTierPriceEntriesURL.setParameter("cpInstanceId", String.valueOf(cpInstanceId));
instanceTierPriceEntriesURL.setParameter("toolbarItem", toolbarItem);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(instanceTierPriceEntriesURL.toString());

renderResponse.setTitle(cpInstanceCommerceTierPriceEntryDisplayContext.getContextTitle());
%>

<portlet:actionURL name="editCPInstanceCommerceTierPriceEntry" var="editCommerceTierPriceEntryActionURL" />

<aui:form action="<%= editCommerceTierPriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= instanceTierPriceEntriesURL.toString() %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="commerceTierPriceEntryId" type="hidden" value="<%= commerceTierPriceEntryId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceTierPriceEntry %>" model="<%= CommerceTierPriceEntry.class %>" />

		<aui:fieldset>
			<aui:input name="price" />

			<aui:input name="minQuantity" />
		</aui:fieldset>

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