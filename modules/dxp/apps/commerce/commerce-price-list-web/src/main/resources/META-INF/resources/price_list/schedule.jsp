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
CommercePriceList commercePriceList = (CommercePriceList)request.getAttribute(CommerceWebKeys.COMMERCE_PRICE_LIST);

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((commercePriceList != null) && (commercePriceList.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="status"
/>

<aui:model-context bean="<%= commercePriceList %>" model="<%= CommercePriceList.class %>" />

<liferay-ui:error exception="<%= CommercePriceListExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />

<aui:fieldset>
	<aui:input formName="fm" name="displayDate" />

	<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
</aui:fieldset>