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
String className = ParamUtil.getString(request, "className");
Long classPK = ParamUtil.getLong(request, "classPK");
long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId");
long primaryKey = ParamUtil.getLong(request, "primaryKey", 0L);
String redirect = ParamUtil.getString(request, "redirect");

Address address = null;

long countryId = 0L;
long regionId = 0L;

if (primaryKey > 0L) {
	address = AddressServiceUtil.getAddress(primaryKey);

	countryId = address.getCountryId();
	regionId = address.getRegionId();
}

EditContactInformationDisplayContext editContactInformationDisplayContext = new EditContactInformationDisplayContext(className, classPK, parentOrganizationId, renderResponse, request);

if (!portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(editContactInformationDisplayContext.getBackURL());

	renderResponse.setTitle(editContactInformationDisplayContext.getPortletTitle());
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "contact-information"), redirect);

String sheetTitle;

if (primaryKey > 0) {
	sheetTitle = LanguageUtil.get(request, "edit-address");
}
else {
	sheetTitle = LanguageUtil.get(request, "add-address");
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, sheetTitle), null);
%>

<portlet:actionURL name="/users_admin/update_contact_information" var="actionURL" />

<aui:form action="<%= actionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="className" type="hidden" value="<%= className %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="errorMVCPath" type="hidden" value="/common/edit_address.jsp" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.ADDRESS %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= String.valueOf(primaryKey) %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<div class="container-fluid container-fluid-max-xl">
		<div class="sheet-lg" id="breadcrumb">
			<liferay-ui:breadcrumb
				showCurrentGroup="<%= false %>"
				showGuestGroup="<%= false %>"
				showLayout="<%= false %>"
				showPortletBreadcrumb="<%= true %>"
			/>
		</div>

		<div class="sheet sheet-lg">
			<div class="sheet-header">
				<h2 class="sheet-title"><liferay-ui:message key="<%= sheetTitle %>" /></h2>
			</div>

			<div class="sheet-section">
				<aui:model-context bean="<%= address %>" model="<%= Address.class %>" />

				<aui:input checked="<%= (address != null)? address.isPrimary() : false %>" id="addressPrimary" label="make-primary" name="addressPrimary" type="checkbox" />

				<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.ADDRESS %>" message="please-select-a-type" />

				<aui:select label="type" listType="<%= className + ListTypeConstants.ADDRESS %>" name='<%= "addressTypeId" %>' />

				<liferay-ui:error exception="<%= AddressStreetException.class %>" message="please-enter-a-valid-street" />

				<aui:input fieldParam='<%= "addressStreet1" %>' id='<%= "addressStreet1" %>' name="street1" required="<%= true %>" />

				<aui:input fieldParam='<%= "addressStreet2" %>' id='<%= "addressStreet2" %>' name="street2" />

				<aui:input fieldParam='<%= "addressStreet3" %>' id='<%= "addressStreet3" %>' name="street3" />

				<liferay-ui:error exception="<%= AddressCityException.class %>" message="please-enter-a-valid-city" />

				<aui:input fieldParam='<%= "addressCity" %>' id='<%= "addressCity" %>' name="city" required="<%= true %>" />

				<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />

				<aui:select label="country" name='<%= "addressCountryId" %>' />

				<liferay-ui:error exception="<%= NoSuchRegionException.class %>" message="please-select-a-region" />

				<aui:select label="region" name='<%= "addressRegionId" %>' />

				<liferay-ui:error exception="<%= AddressZipException.class %>" message="please-enter-a-valid-postal-code" />

				<div class="form-group">
					<label class="control-label" for="<portlet:namespace />addressZip">
						<liferay-ui:message key="postal-code" />

						<span hidden id="<portlet:namespace />addressZipRequiredWrapper">
							<aui:icon cssClass="reference-mark text-warning" image="asterisk" markupView="lexicon" />

							<span class="hide-accessible"><liferay-ui:message key="required" /></span>
						</span>
					</label>

					<aui:input fieldParam='<%= "addressZip" %>' id='<%= "addressZip" %>' label="" name="zip" />
				</div>

				<aui:input cssClass="mailing-ctrl" fieldParam='<%= "addressMailing" %>' id='<%= "addressMailing" %>' name="mailing" />
			</div>

			<div class="sheet-footer">
				<aui:button primary="<%= true %>" type="submit" />

				<aui:button href="<%= redirect %>" type="cancel" />
			</div>
		</div>
	</div>

	<aui:script use="liferay-address,liferay-dynamic-select">
		new Liferay.DynamicSelect(
			[
				{
					select: '<portlet:namespace />addressCountryId',
					selectData: Liferay.Address.getCountries,
					selectDesc: 'nameCurrentValue',
					selectId: 'countryId',
					selectSort: '<%= true %>',
					selectVal: '<%= countryId %>'
				},
				{
					select: '<portlet:namespace />addressRegionId',
					selectData: Liferay.Address.getRegions,
					selectDesc: 'name',
					selectId: 'regionId',
					selectVal: '<%= regionId %>'
				}
			]
		);
	</aui:script>
</aui:form>

<aui:script use="liferay-form">
	const addressCountry = document.getElementById('<portlet:namespace />addressCountryId');

	function checkCountry(countryId) {
		Liferay.Service(
			'/country/get-country',
			{
				countryId: countryId
			},
			function(response, err) {
				if (err) {
					console.error(err);
				}
				else {
					updateAddressZipRequired(response.zipRequired);
				}
			}
		);
	}

	function handleSelectChange(event) {
		const value = Number(event.currentTarget.value);

		if (value > 0) {
			checkCountry(value);
		}
		else {
			updateAddressZipRequired(false);
		}
	}

	function updateAddressZipRequired(required) {
		const addressZipRequiredWrapper = document.getElementById('<portlet:namespace />addressZipRequiredWrapper');
		const formValidator = Liferay.Form.get('<portlet:namespace />fm').formValidator;

		const rules = formValidator._getAttr('rules');

		if (required) {
			addressZipRequiredWrapper.removeAttribute('hidden');
		}
		else {
			addressZipRequiredWrapper.setAttribute('hidden', true);
		}

		rules.<portlet:namespace />addressZip = {required: required};
	}

	if (addressCountry) {
		addressCountry.addEventListener('change', handleSelectChange);

		<c:if test="<%= countryId > 0 %>">
			checkCountry(<%= countryId %>);
		</c:if>
	}
</aui:script>