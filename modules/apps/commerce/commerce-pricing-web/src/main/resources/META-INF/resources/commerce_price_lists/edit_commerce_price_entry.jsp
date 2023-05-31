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
CommercePriceEntryDisplayContext commercePriceEntryDisplayContext = (CommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceEntry commercePriceEntry = commercePriceEntryDisplayContext.getCommercePriceEntry();

long commercePriceEntryId = commercePriceEntryDisplayContext.getCommercePriceEntryId();
%>

<portlet:actionURL name="/commerce_price_list/edit_commerce_price_entry" var="editCommercePriceEntryActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "edit-price") %>'
>
	<aui:form action="<%= editCommercePriceEntryActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commercePriceEntryId" type="hidden" value="<%= commercePriceEntryId %>" />
		<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceEntryDisplayContext.getCommercePriceListId() %>" />

		<aui:model-context bean="<%= commercePriceEntry %>" model="<%= CommercePriceEntry.class %>" />

		<div class="row">
			<div class="col-12">
				<%@ include file="/commerce_price_lists/commerce_price_entry/details.jspf" %>
			</div>

			<div class="col-12">
				<%@ include file="/commerce_price_lists/commerce_price_entry/custom_fields.jspf" %>
			</div>
		</div>

		<aui:button-row cssClass="price-entry-button-row">
			<aui:button cssClass="btn-lg" type="submit" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>