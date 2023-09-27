<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPInstanceCommercePriceEntryDisplayContext cpInstanceCommercePriceEntryDisplayContext = (CPInstanceCommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPInstance cpInstance = cpInstanceCommercePriceEntryDisplayContext.getCPInstance();
long cpInstanceId = cpInstanceCommercePriceEntryDisplayContext.getCPInstanceId();
%>

<portlet:actionURL name="/cp_definitions/edit_cp_instance_commerce_price_entry" var="addCommercePriceEntryURL" />

<aui:form action="<%= addCommercePriceEntryURL %>" cssClass="hide" name="addCommercePriceEntryFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />
	<aui:input name="unitOfMeasureKey" type="hidden" value="" />
	<aui:input name="commercePriceListIds" type="hidden" value="" />
</aui:form>

<div id="<portlet:namespace />entriesContainer">
	<aui:form action="<%= cpInstanceCommercePriceEntryDisplayContext.getPortletURL() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommercePriceEntryIds" type="hidden" />

		<liferay-ui:error exception="<%= DuplicateCommercePriceEntryException.class %>" message="one-or-more-selected-entries-already-exist" />

		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId())
				).put(
					"cpInstanceId", String.valueOf(cpInstanceId)
				).build()
			%>'
			creationMenu="<%= cpInstanceCommercePriceEntryDisplayContext.getCreationMenu() %>"
			dataProviderKey="<%= CommercePricingFDSNames.INSTANCE_PRICE_ENTRIES %>"
			formName="fm"
			id="<%= CommercePricingFDSNames.INSTANCE_PRICE_ENTRIES %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</aui:form>
</div>

<liferay-frontend:component
	context="<%= cpInstanceCommercePriceEntryDisplayContext.getJSContext() %>"
	module="commerce_price_lists/cp_instance/js/cp_instance_commerce_price_lists"
/>