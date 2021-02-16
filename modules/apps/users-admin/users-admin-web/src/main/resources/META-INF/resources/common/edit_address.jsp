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
EditContactInformationDisplayContext editContactInformationDisplayContext = new EditContactInformationDisplayContext("address", request, renderResponse);

editContactInformationDisplayContext.setPortletDisplay(portletDisplay, portletName);

Address address = null;

long countryId = 0L;
long regionId = 0L;

if (editContactInformationDisplayContext.getPrimaryKey() > 0) {
	address = AddressServiceUtil.getAddress(editContactInformationDisplayContext.getPrimaryKey());

	countryId = address.getCountryId();
	regionId = address.getRegionId();
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "addresses"), editContactInformationDisplayContext.getRedirect());

PortalUtil.addPortletBreadcrumbEntry(request, editContactInformationDisplayContext.getSheetTitle(), null);
%>

<portlet:actionURL name="/users_admin/update_contact_information" var="actionURL" />

<aui:form action="<%= actionURL %>" method="post" name="fm">
	<aui:input name="errorMVCPath" type="hidden" value="/common/edit_address.jsp" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="redirect" type="hidden" value="<%= editContactInformationDisplayContext.getRedirect() %>" />
	<aui:input name="className" type="hidden" value="<%= editContactInformationDisplayContext.getClassName() %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(editContactInformationDisplayContext.getClassPK()) %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.ADDRESS %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= String.valueOf(editContactInformationDisplayContext.getPrimaryKey()) %>" />

	<clay:container-fluid>
		<div class="sheet-lg" id="breadcrumb">
			<liferay-ui:breadcrumb
				showCurrentGroup="<%= false %>"
				showGuestGroup="<%= false %>"
				showLayout="<%= false %>"
				showPortletBreadcrumb="<%= true %>"
			/>
		</div>

		<clay:sheet>
			<clay:sheet-header>
				<h2 class="sheet-title"><%= editContactInformationDisplayContext.getSheetTitle() %></h2>
			</clay:sheet-header>

			<clay:sheet-section>
				<aui:model-context bean="<%= address %>" model="<%= Address.class %>" />

				<aui:input checked="<%= (address != null)? address.isPrimary() : false %>" id="addressPrimary" label="make-primary" name="addressPrimary" type="checkbox" />

				<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + editContactInformationDisplayContext.getClassName() + ListTypeConstants.ADDRESS %>" message="please-select-a-type" />

				<aui:select label="type" listType="<%= editContactInformationDisplayContext.getClassName() + ListTypeConstants.ADDRESS %>" name="addressTypeId" />

				<liferay-ui:error exception="<%= AddressStreetException.class %>" message="please-enter-a-valid-street" />

				<aui:input fieldParam="addressStreet1" id="addressStreet1" name="street1" required="<%= true %>" />

				<aui:input fieldParam="addressStreet2" id="addressStreet2" name="street2" />

				<aui:input fieldParam="addressStreet3" id="addressStreet3" name="street3" />

				<liferay-ui:error exception="<%= AddressCityException.class %>" message="please-enter-a-valid-city" />

				<aui:input fieldParam="addressCity" id="addressCity" name="city" required="<%= true %>" />

				<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />

				<aui:select label="country" name="addressCountryId" />

				<liferay-ui:error exception="<%= NoSuchRegionException.class %>" message="please-select-a-region" />

				<aui:select label="region" name="addressRegionId" />

				<liferay-ui:error exception="<%= AddressZipException.class %>" message="please-enter-a-valid-postal-code" />

				<div class="form-group">
					<label class="control-label" for="<portlet:namespace />addressZip">
						<liferay-ui:message key="postal-code" />

						<span hidden id="<portlet:namespace />addressZipRequiredWrapper">
							<aui:icon cssClass="reference-mark text-warning" image="asterisk" markupView="lexicon" />

							<span class="hide-accessible"><liferay-ui:message key="required" /></span>
						</span>
					</label>

					<aui:input fieldParam="addressZip" id="addressZip" label="" name="zip" />
				</div>

				<aui:input cssClass="mailing-ctrl" fieldParam="addressMailing" id="addressMailing" name="mailing" />
			</clay:sheet-section>

			<clay:sheet-footer>
				<aui:button primary="<%= true %>" type="submit" />

				<aui:button href="<%= editContactInformationDisplayContext.getRedirect() %>" type="cancel" />
			</clay:sheet-footer>
		</clay:sheet>
	</clay:container-fluid>

	<script>
		new Liferay.DynamicSelect([
			{
				select: '<portlet:namespace />addressCountryId',
				selectData: Liferay.Address.getCountries,
				selectDesc: 'nameCurrentValue',
				selectId: 'countryId',
				selectSort: '<%= true %>',
				selectVal: '<%= countryId %>',
			},
			{
				select: '<portlet:namespace />addressRegionId',
				selectData: Liferay.Address.getRegions,
				selectDesc: 'name',
				selectId: 'regionId',
				selectVal: '<%= regionId %>',
			},
		]);
	</script>
</aui:form>

<aui:script use="liferay-form">
	var addressCountry = document.getElementById(
		'<portlet:namespace />addressCountryId'
	);

	function checkCountry(countryId) {
		Liferay.Service(
			'/country/get-country',
			{
				countryId: countryId,
			},
			(response, err) => {
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
		var value = Number(event.currentTarget.value);

		if (value > 0) {
			checkCountry(value);
		}
		else {
			updateAddressZipRequired(false);
		}
	}

	function updateAddressZipRequired(required) {
		var addressZipRequiredWrapper = document.getElementById(
			'<portlet:namespace />addressZipRequiredWrapper'
		);
		var formValidator = Liferay.Form.get('<portlet:namespace />fm')
			.formValidator;

		var rules = formValidator._getAttr('rules');

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