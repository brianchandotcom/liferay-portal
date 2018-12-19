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
String className = ParamUtil.getString(request, "className");

long emailAddressId = ParamUtil.getLong(request, "primaryKey", 0L);

EmailAddress emailAddress = null;

if (emailAddressId > 0L) {
	emailAddress = EmailAddressServiceUtil.getEmailAddress(emailAddressId);
}
%>

<aui:form cssClass="modal-body" name="fm">
	<aui:model-context bean="<%= emailAddress %>" model="<%= EmailAddress.class %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= emailAddressId %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.EMAIL_ADDRESS %>" />

	<aui:input checked="<%= (emailAddress != null)? emailAddress.isPrimary() : false %>" id="emailAddressPrimary" label="make-primary" name="emailAddressPrimary" type="checkbox" />

	<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.EMAIL_ADDRESS %>" message="please-select-a-type" />

	<aui:select inlineField="<%= true %>" label="type" listType="<%= className + ListTypeConstants.EMAIL_ADDRESS %>" name="emailAddressTypeId" />

	<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />

	<aui:input fieldParam="emailAddressAddress" id="emailAddressAddress" name="address" required="<%= true %>" />
</aui:form>