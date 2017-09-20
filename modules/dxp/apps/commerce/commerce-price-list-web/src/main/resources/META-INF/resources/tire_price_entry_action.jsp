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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceTirePriceEntry commerceTirePriceEntry = null;

if (row != null) {
	commerceTirePriceEntry = (CommerceTirePriceEntry)row.getObject();
}
else {
	commerceTirePriceEntry = (CommerceTirePriceEntry)request.getAttribute("info_panel.jsp-entry");
}

CommercePriceEntry commercePriceEntry = commerceTirePriceEntry.getCommercePriceEntry();

long commercePriceListId = 0;

if (commercePriceEntry != null) {
	commercePriceListId = commercePriceEntry.getCommercePriceListId();
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="editCommerceTirePriceEntry" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commercePriceEntryId" value="<%= String.valueOf(commerceTirePriceEntry.getCommercePriceEntryId()) %>" />
		<portlet:param name="commercePriceListId" value="<%= String.valueOf(commercePriceListId) %>" />
		<portlet:param name="commerceTirePriceEntryId" value="<%= String.valueOf(commerceTirePriceEntry.getCommerceTirePriceEntryId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="editCommerceTirePriceEntry" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commercePriceEntryId" value="<%= String.valueOf(commerceTirePriceEntry.getCommercePriceEntryId()) %>" />
		<portlet:param name="commerceTirePriceEntryId" value="<%= String.valueOf(commerceTirePriceEntry.getCommerceTirePriceEntryId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>