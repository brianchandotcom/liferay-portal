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
CommerceTierPriceEntryDisplayContext commerceTierPriceEntryDisplayContext = (CommerceTierPriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTierPriceEntry commerceTierPriceEntry = commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntry();

long commercePriceEntryId = commerceTierPriceEntryDisplayContext.getCommercePriceEntryId();
long commercePriceListId = commerceTierPriceEntryDisplayContext.getCommercePriceListId();
long commerceTierPriceEntryId = commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryId();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-tier-price-entries");

PortletURL tierPriceEntriesURL = renderResponse.createRenderURL();

tierPriceEntriesURL.setParameter("mvcRenderCommandName", "viewCommerceTierPriceEntries");
tierPriceEntriesURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
tierPriceEntriesURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));
tierPriceEntriesURL.setParameter("toolbarItem", toolbarItem);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(tierPriceEntriesURL.toString());

renderResponse.setTitle(commerceTierPriceEntryDisplayContext.getContextTitle());
%>

<portlet:actionURL name="editCommerceTierPriceEntry" var="editCommerceTierPriceEntryActionURL" />

<aui:form action="<%= editCommerceTierPriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= tierPriceEntriesURL.toString() %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="commerceTierPriceEntryId" type="hidden" value="<%= commerceTierPriceEntryId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceTierPriceEntry %>" model="<%= CommerceTierPriceEntry.class %>" />

		<aui:fieldset>
			<aui:input name="price" />

			<aui:input name="minQuantity" />
		</aui:fieldset>

		<c:if test="<%= commerceTierPriceEntryDisplayContext.hasCustomAttributes() %>">
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

			<aui:button cssClass="btn-lg" href="<%= tierPriceEntriesURL.toString() %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>