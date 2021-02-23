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
CommerceCountriesDisplayContext commerceCountriesDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Country country = commerceCountriesDisplayContext.getCountry();
long countryId = commerceCountriesDisplayContext.getCountryId();
CommerceRegionsStarter commerceRegionsStarter = commerceCountriesDisplayContext.getCommerceRegionsStarter();
%>

<portlet:actionURL name="/commerce_country/edit_commerce_country" var="editCommerceCountryActionURL" />

<aui:form action="<%= editCommerceCountryActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCommerceCountry();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (country == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= redirect %>" />
	<aui:input name="countryId" type="hidden" value="<%= String.valueOf(countryId) %>" />

	<liferay-ui:error exception="<%= CountryA2Exception.class %>" message="please-enter-a-valid-two-letter-iso-code" />
	<liferay-ui:error exception="<%= CountryA3Exception.class %>" message="please-enter-a-valid-three-letter-iso-code" />
	<liferay-ui:error exception="<%= CountryNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= DuplicateCountryException.class %>" message="the-two-letter-iso-code-is-already-used" />

	<aui:model-context bean="<%= country %>" model="<%= Country.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:fieldset>
				<liferay-ui:input-localized
					autoFocus="<%= true %>"
					cssClass="form-group"
					name="name"
					xml="<%= (country == null) ? StringPool.BLANK : country.getTitleMapAsXML() %>"
				/>

				<aui:input checked="<%= (country == null) ? false : country.getBillingAllowed() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="billingAllowed" type="toggle-switch" />

				<aui:input checked="<%= (country == null) ? false : country.getShippingAllowed() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="shippingAllowed" type="toggle-switch" />

				<aui:input id="twoLettersISOCode" label="two-letter-iso-code" name="a2" />

				<aui:input id="threeLettersISOCode" label="three-letter-iso-code" name="a3" />

				<aui:input id="numericISOCode" name="number" />

				<aui:input checked="<%= (country == null) ? false : country.getSubjectToVAT() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="subjectToVAT" type="toggle-switch" />

				<aui:input id="priority" name="position" />

				<aui:input checked="<%= (country == null) ? false : country.isActive() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="active" type="toggle-switch" />

				<c:if test="<%= commerceRegionsStarter != null %>">
					<aui:input name="key" type="hidden" value="<%= commerceRegionsStarter.getKey() %>" />

					<aui:button cssClass="btn-lg" disabled="<%= commerceCountriesDisplayContext.hasRegions(country) %>" name="importCommerceRegionsButton" value='<%= LanguageUtil.get(request, "import-regions") %>' />
				</c:if>
			</aui:fieldset>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceCountry() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<c:if test="<%= commerceRegionsStarter != null %>">
	<aui:script use="aui-io-request,aui-parse-content,liferay-notification">
		A.one('#<portlet:namespace />importCommerceRegionsButton').on(
			'click',
			function (event) {
				var data = {
					<portlet:namespace />key: '<%= commerceRegionsStarter.getKey() %>',
				};

				this.attr('disabled', true);

				A.io.request(
					'<liferay-portlet:actionURL name="/commerce_country/import_commerce_regions" portletName="<%= portletDisplay.getPortletName() %>" />',
					{
						data: data,
						on: {
							success: function (event, id, obj) {
								var response = JSON.parse(obj.response);

								if (!response.success) {
									A.one(
										'#<portlet:namespace />importCommerceRegionsButton'
									).attr('disabled', false);

									new Liferay.Notification({
										closeable: true,
										delay: {
											hide: 5000,
											show: 0,
										},
										duration: 500,
										message:
											'<liferay-ui:message key="an-unexpected-error-occurred" />',
										render: true,
										title: '<liferay-ui:message key="danger" />',
										type: 'danger',
									});
								}
							},
						},
					}
				);
			}
		);
	</aui:script>
</c:if>