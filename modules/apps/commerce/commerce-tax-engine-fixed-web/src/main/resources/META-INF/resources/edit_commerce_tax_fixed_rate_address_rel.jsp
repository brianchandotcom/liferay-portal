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