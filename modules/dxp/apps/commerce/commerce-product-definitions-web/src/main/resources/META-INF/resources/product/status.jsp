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
CommerceProductDefinition commerceProductDefinition = (CommerceProductDefinition)request.getAttribute(CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION);

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if (commerceProductDefinition != null) {
	if (commerceProductDefinition.getExpirationDate() != null) {
		neverExpire = false;
	}
}
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="status" />

<aui:model-context bean="<%= commerceProductDefinition %>" model="<%= CommerceProductDefinition.class %>" />

<aui:fieldset cssClass="col-md-4">
	<aui:input name="published" type="checkbox" />

	<aui:input formName="fm1" name="displayDate" />

	<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm1" name="expirationDate" />
</aui:fieldset>