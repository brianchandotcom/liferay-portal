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
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

long organizationId = organizationScreenNavigationDisplayContext.getOrganizationId();

List<Address> addresses = AddressServiceUtil.getAddresses(Organization.class.getName(), organizationId);
%>

<div class="sheet-title">
	<span class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<h2 class="sheet-title">
				<%= organizationScreenNavigationDisplayContext.getFormLabel() %>
			</h2>
		</span>
		<span class="autofit-col">
			<liferay-ui:icon
				cssClass="modify-address-link"
				data="<%=
					new HashMap<String, Object>() {
						{
							put("title", LanguageUtil.get(request, "add-address"));
						}
					}
				%>"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message="add"
				url="javascript:;"
			/>
		</span>
	</span>
</div>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="services"
/>

<liferay-ui:error exception="<%= AddressCityException.class %>" message="please-enter-a-valid-city" />
<liferay-ui:error exception="<%= AddressStreetException.class %>" message="please-enter-a-valid-street" />
<liferay-ui:error exception="<%= AddressZipException.class %>" message="please-enter-a-valid-postal-code" />
<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />
<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.ADDRESS %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= NoSuchRegionException.class %>" message="please-select-a-region" />

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	cssClass="addresses-search-container-wrapper lfr-search-container-wrapper"
	emptyResultsMessage="this-organization-does-not-have-any-addresses"
	id="addressesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= addresses.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= addresses.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Address"
		escapedModel="<%= true %>"
		keyProperty="addressId"
		modelVar="address"
	>
		<liferay-ui:search-container-column-icon
			icon="picture"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
		>
			<h4>
				<liferay-ui:message key="<%= address.getType().getName() %>" />
			</h4>

			<h8>
				<liferay-text-localizer:address-display
					address="<%= address %>"
				/>
			</h8>

			<c:if test="<%= address.isPrimary() %>">
				<div>
					<span class="label label-primary">
						<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
					</span>
				</div>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/organization/address_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<portlet:renderURL var="editAddressRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_address.jsp" />
</portlet:renderURL>

<aui:script require="<%= organizationScreenNavigationDisplayContext.getContactInformationJSRequire() %>">
	ContactInformation.registerContactInformationListener(
		'.modify-address-link a',
		'<%= editAddressRenderURL.toString() %>',
		1000
	);
</aui:script>