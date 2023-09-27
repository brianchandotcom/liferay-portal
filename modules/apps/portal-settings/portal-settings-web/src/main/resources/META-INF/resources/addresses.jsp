<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= RoleLocalServiceUtil.hasUserRole(user.getUserId(), company.getCompanyId(), RoleConstants.ADMINISTRATOR, true) %>">

		<%
		String className = (String)request.getAttribute("addresses.className");
		long classPK = (Long)request.getAttribute("addresses.classPK");

		List<Address> addresses = Collections.emptyList();

		int[] addressesIndexes = null;

		String addressesIndexesParam = ParamUtil.getString(request, "addressesIndexes");

		if (Validator.isNotNull(addressesIndexesParam)) {
			addresses = new ArrayList<Address>();

			addressesIndexes = StringUtil.split(addressesIndexesParam, 0);

			for (int addressesIndex : addressesIndexes) {
				addresses.add(new AddressImpl());
			}
		}
		else {
			if (classPK > 0) {
				addresses = AddressServiceUtil.getAddresses(className, classPK);

				addressesIndexes = new int[addresses.size()];

				for (int i = 0; i < addresses.size(); i++) {
					addressesIndexes[i] = i;
				}
			}

			if (addresses.isEmpty()) {
				addresses = new ArrayList<Address>();

				addresses.add(new AddressImpl());

				addressesIndexes = new int[] {0};
			}

			if (addressesIndexes == null) {
				addressesIndexes = new int[0];
			}
		}
		%>

		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="addresses"
		/>

		<div class="alert alert-info">
			<liferay-ui:message key="street-1-and-city-are-required-fields.-postal-code-could-be-required-in-some-countries" />
		</div>

		<liferay-ui:error exception="<%= AddressCityException.class %>" message="please-enter-a-valid-city" />
		<liferay-ui:error exception="<%= AddressStreetException.class %>" message="please-enter-a-valid-street" />
		<liferay-ui:error exception="<%= AddressZipException.class %>" message="please-enter-a-valid-postal-code" />
		<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />
		<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.ADDRESS %>" message="please-select-a-type" />
		<liferay-ui:error exception="<%= NoSuchRegionException.class %>" message="please-select-a-region" />

		<aui:fieldset cssClass="addresses" id='<%= liferayPortletResponse.getNamespace() + "addresses" %>'>

			<%
			for (int i = 0; i < addressesIndexes.length; i++) {
				int addressesIndex = addressesIndexes[i];

				Address address = addresses.get(i);
			%>

				<aui:model-context bean="<%= address %>" model="<%= Address.class %>" />

				<div class="lfr-form-row">
					<div class="row-fields">
						<%@ include file="/addresses_address.jspf" %>
					</div>
				</div>

				<liferay-frontend:component
					componentId="CountryRegionDynamicSelect"
					context='<%=
						HashMapBuilder.<String, Object>put(
							"countrySelect", portletDisplay.getNamespace() + "addressCountryId" + addressesIndex
						).put(
							"countrySelectVal", ParamUtil.getLong(request, "addressCountryId" + addressesIndex, address.getCountryId())
						).put(
							"regionSelect", portletDisplay.getNamespace() + "addressRegionId" + addressesIndex
						).put(
							"regionSelectVal", ParamUtil.getLong(request, "addressRegionId" + addressesIndex, address.getRegionId())
						).build()
					%>'
					module="js/CountryRegionDynamicSelect"
				/>

			<%
			}
			%>

			<aui:input name="addressesIndexes" type="hidden" value="<%= StringUtil.merge(addressesIndexes) %>" />
		</aui:fieldset>

		<aui:script require='<%= npmResolvedPackageName + "/js/CountryRegionDynamicSelect as CountryRegionDynamicSelect" %>'>
			AUI().use('liferay-auto-fields', (A) => {
				new Liferay.AutoFields({
					contentBox: '#<portlet:namespace />addresses',
					fieldIndexes: '<portlet:namespace />addressesIndexes',
					namespace: '<portlet:namespace />',
					on: {
						clone: function (event) {
							var guid = event.guid;
							var row = event.row;

							var dynamicSelects = row.one(
								'select[data-componentType=dynamic_select]'
							);

							if (dynamicSelects) {
								dynamicSelects.detach('change');
							}

							CountryRegionDynamicSelect.default({
								countrySelect:
									'<portlet:namespace />addressCountryId' + guid,
								regionSelect: '<portlet:namespace />addressRegionId' + guid,
							});
						},
					},
				}).render();
			});
		</aui:script>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="you-do-not-have-the-required-permissions-to-access-this-content" />
		</div>
	</c:otherwise>
</c:choose>