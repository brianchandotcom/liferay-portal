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
CommerceAccountDisplayContext commerceAccountDisplayContext = (CommerceAccountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAccount commerceAccount = commerceAccountDisplayContext.getCurrentCommerceAccount();
CommerceAddress commerceAddress = commerceAccountDisplayContext.getDefaultBillingCommerceAddress();

long countryId = 0;
long regionId = 0;

if (commerceAddress != null) {
	countryId = commerceAddress.getCountryId();
	regionId = commerceAddress.getRegionId();
}
%>

<portlet:actionURL name="/commerce_account/edit_commerce_account" var="editCommerceAccountActionURL" />

<div class="account-management">
	<aui:form action="<%= editCommerceAccountActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceAccount == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceAccountId" type="hidden" value="<%= (commerceAccount == null) ? 0 : commerceAccount.getCommerceAccountId() %>" />
		<aui:input name="commerceAddressId" type="hidden" value="<%= (commerceAddress == null) ? 0 : commerceAddress.getCommerceAddressId() %>" />

		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="details"
		/>

		<aui:model-context bean="<%= commerceAccount %>" model="<%= CommerceAccount.class %>" />

		<section class="panel panel-secondary">
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-4 account-management__thumbnail-container">
						<aui:fieldset>
							<c:if test="<%= commerceAccount != null %>">

								<%
								long logoId = commerceAccount.getLogoId();

								UserFileUploadsConfiguration userFileUploadsConfiguration = commerceAccountDisplayContext.getUserFileUploadsConfiguration();
								%>

								<liferay-ui:logo-selector
									currentLogoURL='<%= themeDisplay.getPathImage() + "/organization_logo?img_id=" + logoId + "&t=" + WebServerServletTokenUtil.getToken(logoId) %>'
									defaultLogo="<%= logoId == 0 %>"
									defaultLogoURL='<%= themeDisplay.getPathImage() + "/organization_logo?img_id=0" %>'
									logoDisplaySelector=".organization-logo"
									maxFileSize="<%= userFileUploadsConfiguration.imageMaxSize() %>"
									tempImageFileName="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>"
								/>
							</c:if>
						</aui:fieldset>
					</div>

					<div class="col-lg-4">
						<aui:input name="name" />
						<aui:input name="email" />
					</div>

					<div class="col-lg-4">
						<aui:input label="vat-number" name="taxId" />
					</div>
				</div>
			</div>
		</section>

		<section class="panel panel-secondary">
			<div class="panel-body">
				<div class="row">
					<aui:select label="default-billing" name="defaultBillingAddressId" showEmptyOption="<%= true %>" wrapperCssClass="col-md-6">

						<%
						for (CommerceAddress billingAddress : commerceAccountDisplayContext.getBillingCommerceAddresses()) {
						%>

							<aui:option label="<%= billingAddress.getName() %>" selected="<%= billingAddress.getCommerceAddressId() == commerceAccount.getDefaultBillingAddressId() %>" value="<%= billingAddress.getCommerceAddressId() %>" />

						<%
						}
						%>

					</aui:select>

					<aui:select label="default-shipping" name="defaultShippingAddressId" showEmptyOption="<%= true %>" wrapperCssClass="col-md-6">

						<%
						for (CommerceAddress shippingAddress : commerceAccountDisplayContext.getShippingCommerceAddresses()) {
						%>

							<aui:option label="<%= shippingAddress.getName() %>" selected="<%= shippingAddress.getCommerceAddressId() == commerceAccount.getDefaultShippingAddressId() %>" value="<%= shippingAddress.getCommerceAddressId() %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</div>
		</section>

		<div class="commerce-cta is-visible">
			<c:if test="<%= Validator.isNotNull(backURL) %>">
				<aui:button cssClass="btn-lg btn-secondary" href="<%= backURL %>" value="cancel" />
			</c:if>

			<aui:button cssClass="btn-lg" primary="<%= true %>" type="submit" />
		</div>
	</aui:form>
</div>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />countryId',
			selectData: function (callback) {
				Liferay.Service(
					'/country/get-company-countries',
					{
						active: true,
						companyId: <%= company.getCompanyId() %>,
					},
					callback
				);
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'countryId',
			selectSort: true,
			selectVal: '<%= countryId %>',
		},
		{
			select: '<portlet:namespace />regionId',
			selectData: function (callback, selectKey) {
				Liferay.Service(
					'/region/get-regions',
					{
						active: true,
						countryId: Number(selectKey),
					},
					callback
				);
			},
			selectDesc: 'name',
			selectId: 'regionId',
			selectVal: '<%= regionId %>',
		},
	]);
</aui:script>