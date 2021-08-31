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
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commercePriceListDisplayContext.getCommercePriceList();
long commercePriceListId = commercePriceListDisplayContext.getCommercePriceListId();
List<CommerceCatalog> commerceCatalogs = commercePriceListDisplayContext.getCommerceCatalogs();

CommercePriceList parentCommercePriceList = commercePriceList.fetchParentCommercePriceList();

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((commercePriceList != null) && (commercePriceList.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<c:if test="<%= (commercePriceList != null) && commercePriceList.isPending() %>">
	<div class="alert alert-info">
		<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
	</div>
</c:if>

<portlet:actionURL name="/commerce_price_list/edit_commerce_price_list" var="editCommercePriceListActionURL" />

<aui:form action="<%= editCommercePriceListActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="addCommerceAccountIds" type="hidden" value="" />
	<aui:input name="addCommerceAccountGroupIds" type="hidden" value="" />
	<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListId %>" />
	<aui:input name="deleteCommercePriceListAccountRelIds" type="hidden" value="" />
	<aui:input name="deleteCommercePriceListCommerceAccountGroupRelIds" type="hidden" value="" />
	<aui:input name="parentCommercePriceListId" type="hidden" value="<%= commercePriceListDisplayContext.getParentCommercePriceListId() %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<aui:model-context bean="<%= commercePriceList %>" model="<%= CommercePriceList.class %>" />

	<liferay-ui:error exception="<%= CommercePriceListCurrencyException.class %>" message="please-select-a-valid-store-currency" />
	<liferay-ui:error exception="<%= CommercePriceListParentPriceListGroupIdException.class %>" message="please-select-a-valid-parent-price-list-for-the-selected-catalog" />
	<liferay-ui:error exception="<%= NoSuchCatalogException.class %>" message="please-select-a-valid-catalog" />

	<div class="row">
		<div class="col-8">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:input name="name" required="<%= true %>" />

				<aui:select disabled="<%= commercePriceList != null %>" label="catalog" name="commerceCatalogGroupId" required="<%= true %>" showEmptyOption="<%= true %>">

					<%
					for (CommerceCatalog commerceCatalog : commerceCatalogs) {
					%>

						<aui:option label="<%= HtmlUtil.escape(commerceCatalog.getName()) %>" selected="<%= (commercePriceList == null) ? (commerceCatalogs.size() == 1) : commercePriceListDisplayContext.isSelectedCatalog(commerceCatalog) %>" value="<%= commerceCatalog.getGroupId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="currency" name="commerceCurrencyId" required="<%= true %>" showEmptyOption="<%= true %>">

					<%
					for (CommerceCurrency commerceCurrency : commercePriceListDisplayContext.getCommerceCurrencies()) {
					%>

						<aui:option label="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" selected="<%= (commercePriceList != null) && (commercePriceList.getCommerceCurrencyId() == commerceCurrency.getCommerceCurrencyId()) %>" value="<%= commerceCurrency.getCommerceCurrencyId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input name="priority">
					<aui:validator name="number" />
				</aui:input>

				<label class="control-label" for="parentCommercePriceListId"><%= LanguageUtil.get(request, "parent-price-list") %></label>

				<div class="mb-4" id="autocomplete-root"></div>

				<aui:script require="commerce-frontend-js/components/autocomplete/entry as autocomplete, commerce-frontend-js/utilities/eventsDefinitions as events">
					autocomplete.default('autocomplete', 'autocomplete-root', {
						apiUrl:
							'<%= commercePriceListDisplayContext.getPriceListsApiUrl(portletName) %>',
						initialLabel:
							'<%= (parentCommercePriceList == null) ? StringPool.BLANK : HtmlUtil.escapeJS(parentCommercePriceList.getName()) %>',
						initialValue:
							'<%= (parentCommercePriceList == null) ? 0 : parentCommercePriceList.getCommercePriceListId() %>',
						inputId: 'parentCommercePriceListId',
						inputName:
							'<%= liferayPortletResponse.getNamespace() %>parentCommercePriceListId',
						itemsKey: 'id',
						itemsLabel: 'name',
						onValueUpdated: function (value, priceListData) {
							if (value) {
								window.document.querySelector(
									'#<portlet:namespace />parentCommercePriceListId'
								).value = priceListData.id;
							}
							else {
								window.document.querySelector(
									'#<portlet:namespace />parentCommercePriceListId'
								).value = 0;
							}
						},
					});
				</aui:script>

				<aui:select label="price-type" name="netPrice">

					<%
					boolean isNetPrice = true;

					if (commercePriceList != null) {
						isNetPrice = commercePriceList.isNetPrice();
					}
					%>

					<aui:option label="net-price" selected="<%= isNetPrice %>" value="true" />
					<aui:option label="gross-price" selected="<%= !isNetPrice %>" value="false" />
				</aui:select>
			</commerce-ui:panel>
		</div>

		<div class="col-4">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "schedule") %>'
			>
				<liferay-ui:error exception="<%= CommercePriceListExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />

				<aui:fieldset>
					<aui:input formName="fm" label="publish-date" name="displayDate" />

					<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
				</aui:fieldset>
			</commerce-ui:panel>

			<c:if test="<%= commercePriceListDisplayContext.hasCustomAttributesAvailable(CommercePriceList.class.getName(), commercePriceListId) %>">
				<commerce-ui:panel
					title='<%= LanguageUtil.get(request, "custom-attributes") %>'
				>
					<liferay-expando:custom-attribute-list
						className="<%= CommercePriceList.class.getName() %>"
						classPK="<%= commercePriceListId %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</commerce-ui:panel>
			</c:if>
		</div>
	</div>
</aui:form>