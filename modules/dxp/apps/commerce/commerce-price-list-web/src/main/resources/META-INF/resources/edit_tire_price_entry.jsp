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
CommerceTirePriceEntryDisplayContext commerceTirePriceEntryDisplayContext = (CommerceTirePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTirePriceEntry commerceTirePriceEntry = commerceTirePriceEntryDisplayContext.getCommerceTirePriceEntry();

long commercePriceEntryId = commerceTirePriceEntryDisplayContext.getCommercePriceEntryId();
long commercePriceListId = commerceTirePriceEntryDisplayContext.getCommercePriceListId();
long commerceTirePriceEntryId = commerceTirePriceEntryDisplayContext.getCommerceTirePriceEntryId();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-tire-price-entries");

PortletURL tirePriceEntriesURL = renderResponse.createRenderURL();

tirePriceEntriesURL.setParameter("mvcRenderCommandName", "viewCommerceTirePriceEntries");
tirePriceEntriesURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
tirePriceEntriesURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));
tirePriceEntriesURL.setParameter("toolbarItem", toolbarItem);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(tirePriceEntriesURL.toString());

renderResponse.setTitle(commerceTirePriceEntryDisplayContext.getContextTitle());
%>

<portlet:actionURL name="editCommerceTirePriceEntry" var="editCommerceTirePriceEntryActionURL" />

<aui:form action="<%= editCommerceTirePriceEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= tirePriceEntriesURL.toString() %>" />
	<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
	<aui:input name="commerceTirePriceEntryId" type="hidden" value="<%= commerceTirePriceEntryId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceTirePriceEntry %>" model="<%= CommerceTirePriceEntry.class %>" />

		<aui:fieldset>
			<aui:input name="price" />

			<aui:input name="minQuantity" />
		</aui:fieldset>

		<c:if test="<%= commerceTirePriceEntryDisplayContext.hasCustomAttributesAvailable() %>">
			<aui:fieldset>
				<liferay-expando:custom-attribute-list
					className="<%= CommerceTirePriceEntry.class.getName() %>"
					classPK="<%= (commerceTirePriceEntry != null) ? commerceTirePriceEntry.getCommerceTirePriceEntryId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</aui:fieldset>
		</c:if>

		<aui:button-row cssClass="tire-price-entry-button-row">
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= tirePriceEntriesURL.toString() %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>