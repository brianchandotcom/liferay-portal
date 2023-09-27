<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceTaxFixedRateAddressRelsDisplayContext commerceTaxFixedRateAddressRelsDisplayContext = (CommerceTaxFixedRateAddressRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = commerceTaxFixedRateAddressRelsDisplayContext.getCommerceTaxFixedRateAddressRel();
%>

<portlet:actionURL name="/commerce_tax_methods/edit_commerce_tax_fixed_rate_address_rel" var="editCommerceTaxFixedRateAddressRelActionURL" />

<c:choose>
	<c:when test="<%= commerceTaxFixedRateAddressRel == null %>">
		<commerce-ui:modal-content
			title='<%= LanguageUtil.get(resourceBundle, "add-tax-rate-setting") %>'
		>
			<aui:form action="<%= editCommerceTaxFixedRateAddressRelActionURL %>" method="post" name="fm">
				<%@ include file="/edit_commerce_tax_fixed_rate_address_rel.jspf" %>
			</aui:form>
		</commerce-ui:modal-content>
	</c:when>
	<c:otherwise>
		<liferay-frontend:side-panel-content
			title='<%= LanguageUtil.get(resourceBundle, "edit-tax-rate-setting") %>'
		>
			<aui:form action="<%= editCommerceTaxFixedRateAddressRelActionURL %>" method="post" name="fm">
				<commerce-ui:panel>
					<%@ include file="/edit_commerce_tax_fixed_rate_address_rel.jspf" %>
				</commerce-ui:panel>

				<aui:button-row>
					<aui:button cssClass="btn-lg" type="submit" />
				</aui:button-row>
			</aui:form>
		</liferay-frontend:side-panel-content>
	</c:otherwise>
</c:choose>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"companyId", company.getCompanyId()
		).put(
			"countryId", commerceTaxFixedRateAddressRelsDisplayContext.getCountryId()
		).put(
			"regionId", commerceTaxFixedRateAddressRelsDisplayContext.getRegionId()
		).build()
	%>'
	module="js/editCommerceTaxFixedRateAddressRel"
/>