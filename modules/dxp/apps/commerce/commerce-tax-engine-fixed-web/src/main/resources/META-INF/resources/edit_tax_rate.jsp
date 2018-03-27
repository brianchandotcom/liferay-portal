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
long commerceTaxMethodId = ParamUtil.getLong(request, "commerceTaxMethodId");

CommerceTaxFixedRatesDisplayContext commerceTaxFixedRatesDisplayContext = (CommerceTaxFixedRatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxFixedRate commerceTaxFixedRate = commerceTaxFixedRatesDisplayContext.getCommerceTaxFixedRate();

long commerceTaxFixedRateId = 0;

if (commerceTaxFixedRate != null) {
	commerceTaxFixedRateId = commerceTaxFixedRate.getCommerceTaxFixedRateId();
}
%>

<portlet:actionURL name="editCommerceTaxFixedRate" var="editCommerceTaxFixedRateActionURL" />

<aui:form action="<%= editCommerceTaxFixedRateActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceTaxFixedRate == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="commerceTaxFixedOptionId" type="hidden" value="<%= commerceTaxFixedRateId %>" />
	<aui:input name="commerceTaxMethodId" type="hidden" value="<%= commerceTaxMethodId %>" />

	<div class="lfr-form-content sheet">
		<aui:model-context bean="<%= commerceTaxFixedRate %>" model="<%= CommerceTaxFixedRate.class %>" />

		<c:if test="<%= commerceTaxFixedRate == null %>">
			<aui:select disabled="<%= commerceTaxFixedRate != null %>" label="tax-category" name="commerceTaxCategoryId">

				<%
				List<CommerceTaxCategory> commerceTaxCategories = commerceTaxFixedRatesDisplayContext.getAvailableCommerceTaxCategories();

				for (CommerceTaxCategory commerceTaxCategory : commerceTaxCategories) {
				%>

					<aui:option
						label="<%= commerceTaxCategory.getName(locale) %>"
						value="<%= commerceTaxCategory.getCommerceTaxCategoryId() %>"
					/>

				<%
				}
				%>

			</aui:select>

			<liferay-portlet:renderURL var="taxCategoriesURL">
				<portlet:param name="commerceAdminModuleKey" value="<%= CommerceConstants.TAXES_COMMERCE_ADMIN_MODULE_KEY %>" />
				<portlet:param name="screenNavigationEntryKey" value="tax-categories" />
			</liferay-portlet:renderURL>

			<a data-senna-off target="_parent" href="<%= taxCategoriesURL %>"><liferay-ui:message key="manage-tax-categories" /></a>
		</c:if>

		<aui:input name="rate" suffix="<%= commerceTaxFixedRatesDisplayContext.getCommerceCurrencyCode() %>" />
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="saveButton" primary="<%= true %>" value="save" />

		<aui:button cssClass="btn-lg" name="cancelButton" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,aui-io-request">
	A.one('#<portlet:namespace/>saveButton').on(
		'click',
		function(event) {
			var A = AUI();

			var url = '<%= editCommerceTaxFixedRateActionURL.toString() %>';

			A.io.request(
				url,
				{
					form: {
						id: '<portlet:namespace/>fm'
					},
					method: 'POST',
					on: {
						success: function() {
							Liferay.Util.getOpener().refreshPortlet();
							Liferay.Util.getOpener().closePopup('editTaxFixedRateDialog');
						}
					}
				}
			);
		}
	);

	A.one('#<portlet:namespace/>cancelButton').on(
		'click',
		function(event) {
			Liferay.Util.getOpener().closePopup('editTaxFixedRateDialog');
		}
	);
</aui:script>