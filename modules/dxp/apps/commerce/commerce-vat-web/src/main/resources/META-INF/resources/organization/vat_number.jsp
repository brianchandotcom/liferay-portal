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
CommerceVatNumber commerceVatNumber = (CommerceVatNumber)request.getAttribute(CommerceVatWebKeys.COMMERCE_VAT_NUMBER);
Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

long commerceVatNumberId = 0;

if (commerceVatNumber != null) {
	commerceVatNumberId = commerceVatNumber.getCommerceVatNumberId();
}
%>

<portlet:actionURL name="editCommerceVatNumber" var="editCommerceVatNumberActionURL" />

<aui:form action="<%= editCommerceVatNumberActionURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceVatNumber == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceVatNumberId" type="hidden" value="<%= commerceVatNumberId %>" />
	<aui:input name="className" type="hidden" value="<%= organization.getModelClassName() %>" />
	<aui:input name="classPK" type="hidden" value="<%= organization.getOrganizationId() %>" />

	<aui:model-context bean="<%= commerceVatNumber %>" model="<%= CommerceVatNumber.class %>" />

	<div class="lfr-form-content">
		<aui:fieldset>
			<aui:input name="vatNumber" />
		</aui:fieldset>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" value="save" />
	</aui:button-row>
</aui:form>