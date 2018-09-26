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
String redirect = ParamUtil.getString(request, "redirect");
long emailAddressId = ParamUtil.getLong(request, "emailAddressId", 0L);

EmailAddress emailAddress = null;

if (emailAddressId > 0L) {
	emailAddress = EmailAddressServiceUtil.getEmailAddress(emailAddressId);
}
%>

<div class="card-horizontal main-content-card">
	<aui:form action="<%= redirect %>" cssClass="container-fluid container-fluid-max-xl container-form-lg" method="post" name="emailAddressFm">
		<div class="alert alert-info">
			<liferay-ui:message key="email-address-and-type-are-required-fields" />
		</div>

		<aui:model-context bean="<%= emailAddress %>" model="<%= EmailAddress.class %>" />

		<aui:input name="emailAddressId" type="hidden" value="<%= emailAddressId %>" />

		<aui:input checked="<%= (emailAddress != null)? emailAddress.isPrimary() : false %>" id="emailAddressPrimary" label="make-primary" name="emailAddressPrimary" type="checkbox" />

		<aui:select inlineField="<%= true %>" label="type" listType="<%= Organization.class.getName() + ListTypeConstants.EMAIL_ADDRESS %>" name="emailAddressTypeId" />

		<aui:input fieldParam="emailAddressAddress" id="emailAddressAddress" name="address" required="<%= true %>" />
	</aui:form>
</div>