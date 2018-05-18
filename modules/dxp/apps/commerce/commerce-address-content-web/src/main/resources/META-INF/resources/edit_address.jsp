<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

String languageId = LanguageUtil.getLanguageId(locale);

CommerceAddressDisplayContext commerceAddressDisplayContext = (CommerceAddressDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAddress commerceAddress = commerceAddressDisplayContext.getCommerceAddress();

long commerceAddressId = commerceAddressDisplayContext.getCommerceAddressId();
long commerceCountryId = commerceAddressDisplayContext.getCommerceCountryId();
long commerceRegionId = commerceAddressDisplayContext.getCommerceRegionId();
%>

<portlet:actionURL name="editCommerceAddress" var="editCommerceAddressActionURL" />

<aui:form action="<%= editCommerceAddressActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceAddress == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="commerceAddressId" type="hidden" value="<%= commerceAddressId %>" />
	<aui:input name="addressUserId" type="hidden" value="<%= (commerceAddress == null) ? themeDisplay.getUserId() : commerceAddress.getClassPK() %>" />

	<aui:model-context bean="<%= commerceAddress %>" model="<%= CommerceAddress.class %>" />

	<liferay-ui:error exception="<%= CommerceAddressCityException.class %>" message="please-enter-a-valid-city" />
	<liferay-ui:error exception="<%= CommerceAddressCountryException.class %>" message="please-select-a-country" />
	<liferay-ui:error exception="<%= CommerceAddressStreetException.class %>" message="please-enter-a-valid-street" />

	<div class="lfr-form-content">
		<aui:fieldset cssClass="addresses">
			<div class="col-md-6">
				<aui:input name="name" />

				<aui:input name="description" />

				<aui:input name="street1" />

				<aui:input name="street2" />

				<aui:input name="street3" />
			</div>

			<div class="col-md-6">
				<aui:input name="city" />

				<aui:input label="postal-code" name="zip" />

				<aui:select label="country" name="commerceCountryId" showEmptyOption="<%= true %>">

					<%
					List<CommerceCountry> commerceCountries = commerceAddressDisplayContext.getCommerceCountries();

					for (CommerceCountry commerceCountry : commerceCountries) {
					%>

						<aui:option label="<%= commerceCountry.getName(languageId) %>" selected="<%= (commerceAddress != null) && (commerceAddress.getCommerceCountryId() == commerceCountry.getCommerceCountryId()) %>" value="<%= commerceCountry.getCommerceCountryId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="region" name="commerceRegionId" showEmptyOption="<%= true %>">

					<%
					List<CommerceRegion> commerceRegions = commerceAddressDisplayContext.getCommerceRegions();

					for (CommerceRegion commerceRegion : commerceRegions) {
					%>

						<aui:option label="<%= commerceRegion.getName() %>" selected="<%= (commerceAddress != null) && (commerceAddress.getCommerceRegionId() == commerceRegion.getCommerceRegionId()) %>" value="<%= commerceRegion.getCommerceRegionId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input name="phoneNumber" />

				<aui:input name="defaultBilling" />

				<aui:input name="defaultShipping" />
			</div>
		</aui:fieldset>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace />commerceCountryId',
				selectData: function(callback) {
					Liferay.Service(
						'/commerce.commercecountry/get-billing-commerce-countries',
						{
							groupId: <%= scopeGroupId %>,
							billingAllowed: true,
							active: true
						},
						callback
					);
				},
				selectDesc: 'nameCurrentValue',
				selectId: 'commerceCountryId',
				selectSort: '<%= true %>',
				selectVal: '<%= commerceCountryId %>'
			},
			{
				select: '<portlet:namespace />commerceRegionId',
				selectData: function(callback, selectKey) {
					Liferay.Service(
					'/commerce.commerceregion/get-commerce-regions',
						{
							commerceCountryId: Number(selectKey),
							active: true
						},
						callback
					);
				},
				selectDesc: 'name',
				selectId: 'commerceRegionId',
				selectVal: '<%= commerceRegionId %>'
			}
		]
	);
</aui:script>