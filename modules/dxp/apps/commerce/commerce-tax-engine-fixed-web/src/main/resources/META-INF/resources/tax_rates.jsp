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
CommerceTaxFixedRatesDisplayContext commerceTaxFixedRatesDisplayContext = (CommerceTaxFixedRatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceTaxCategory> commerceTaxCategorySearchContainer = commerceTaxFixedRatesDisplayContext.getSearchContainer();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
	searchContainerId="commerceTaxCategories"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceTaxFixedRatesDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceTaxFixedRatesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceTaxFixedRatesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= commerceTaxFixedRatesDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceTaxFixedRatesDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="editCommerceTaxFixedRate" var="editCommerceTaxFixedRateActionURL" />

<aui:form action="<%= editCommerceTaxFixedRateActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceTaxCategoryId" type="hidden" />
	<aui:input name="commerceTaxMethodId" type="hidden" value="<%= commerceTaxFixedRatesDisplayContext.getCommerceTaxMethodId() %>" />
	<aui:input name="rate" type="hidden" />

	<liferay-ui:error exception="<%= DuplicateCommerceTaxFixedRateException.class %>" message="please-select-a-valid-tax-category" />
	<liferay-ui:error exception="<%= NoSuchTaxCategoryException.class %>" message="the-tax-category-could-not-be-found" />
	<liferay-ui:error exception="<%= NoSuchTaxFixedRateException.class %>" message="the-tax-fixed-rate-could-not-be-found" />

	<liferay-ui:search-container
		id="commerceTaxCategories"
		searchContainer="<%= commerceTaxCategorySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceTaxCategory"
			keyProperty="commerceTaxCategoryId"
			modelVar="commerceTaxCategory"
		>

			<%
			CommerceTaxFixedRate commerceTaxFixedRate = commerceTaxFixedRatesDisplayContext.getCommerceTaxFixedRate(commerceTaxCategory.getCommerceTaxCategoryId());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>
				<%= HtmlUtil.escape(commerceTaxCategory.getName(languageId)) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content tax-fixed-rate"
				name="rate"
			>
				<aui:model-context bean="<%= commerceTaxFixedRate %>" model="<%= CommerceTaxFixedRate.class %>" />

				<div class="form-group">
					<div class="input-group m-0">
						<aui:input id='<%= "rate" + index %>' label="" name="rate" suffix="<%= commerceTaxFixedRatesDisplayContext.getCommerceCurrencyCode() %>" />

						<div class="input-group-item input-group-item-shrink">
							<aui:button cssClass="btn btn-primary" name='<%= "saveButton" + index %>' onClick='<%= "javascript:"+ renderResponse.getNamespace() +"updateCommerceTaxFixedRate(" + commerceTaxCategory.getCommerceTaxCategoryId() + "," + index + ")" %>' primary="<%= true %>" value="save" />
						</div>
					</div>
				</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace/>updateCommerceTaxFixedRate(commerceTaxCategoryId, index) {
		var form = $(document.<portlet:namespace />fm);

		form.fm('commerceTaxCategoryId').val(commerceTaxCategoryId);

		var rateInput = $('#<portlet:namespace />rate' + index);

		form.fm('rate').val(rateInput.val());

		submitForm(form);
	}
</aui:script>

<style>
	.tax-fixed-rate .form-group{
		margin-bottom: 0;
	}
</style>