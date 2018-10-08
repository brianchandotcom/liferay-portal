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

<c:if test="<%= addresses.isEmpty() %>">
	<div class="addresses-empty-results-message-wrapper">
		<liferay-ui:empty-result-message
			message="this-organization-does-not-have-any-addresses"
		/>
	</div>
</c:if>

<div class="addresses-table-wrapper table-responsive <%= addresses.isEmpty() ? "hide" : "" %>">
	<table class="table table-autofit">
		<tbody>

			<%
			for (Address address : addresses) {
			%>

				<tr>
					<td>
						<div class="sticker sticker-secondary sticker-static">
							<aui:icon image="picture" markupView="lexicon" />
						</div>
					</td>
					<td class="table-cell-expand">
						<h4>

							<%
							ListType listType = address.getType();
							%>

							<liferay-ui:message key="<%= listType.getName() %>" />
						</h4>

						<div class="address-display-wrapper">
							<liferay-text-localizer:address-display
								address="<%= address %>"
							/>
						</div>

						<c:if test="<%= address.isPrimary() %>">
							<div>
								<span class="label label-primary">
									<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
								</span>
							</div>
						</c:if>
					</td>
					<td>
						<span class="autofit-col lfr-search-container-wrapper">
							<liferay-util:include page="/organization/address_action.jsp" servletContext="<%= application %>">
								<liferay-util:param name="addressId" value="<%= String.valueOf(address.getAddressId()) %>" />
							</liferay-util:include>
						</span>
					</td>
				</tr>

			<%
			}
			%>

		</tbody>
	</table>
</div>

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