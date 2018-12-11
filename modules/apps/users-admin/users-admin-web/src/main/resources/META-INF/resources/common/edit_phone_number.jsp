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

long phoneId = ParamUtil.getLong(request, "primaryKey", 0L);

Phone phone = null;

if (phoneId > 0L) {
	phone = PhoneServiceUtil.getPhone(phoneId);
}
%>

<aui:form cssClass="modal-body" name="fm">
	<clay:alert
		message='<%= LanguageUtil.get(request, "extension-must-be-numeric") %>'
		style="info"
		title='<%= LanguageUtil.get(request, "info") + ":" %>'
	/>

	<aui:model-context bean="<%= phone %>" model="<%= Phone.class %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= phoneId %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.PHONE %>" />

	<aui:input checked="<%= (phone != null)? phone.isPrimary() : false %>" id="phonePrimary" label="make-primary" name="phonePrimary" type="checkbox" />

	<aui:select inlineField="<%= true %>" label="type" listType="<%= className + ListTypeConstants.PHONE %>" name="phoneTypeId" />

	<aui:input fieldParam="phoneNumber" id="phoneNumber" name="number" required="<%= true %>" />

	<aui:input fieldParam="phoneExtension" id="phoneExtension" name="extension">
		<aui:validator name="digits" />
	</aui:input>
</aui:form>