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
String className = (String)request.getAttribute("emailAddresses.className");
long classPK = (Long)request.getAttribute("emailAddresses.classPK");

List<EmailAddress> emailAddresses = Collections.emptyList();

int[] emailAddressesIndexes = null;

String emailAddressesIndexesParam = ParamUtil.getString(request, "emailAddressesIndexes");

if (Validator.isNotNull(emailAddressesIndexesParam)) {
	emailAddresses = new ArrayList<EmailAddress>();

	emailAddressesIndexes = StringUtil.split(emailAddressesIndexesParam, 0);

	for (int emailAddressesIndex : emailAddressesIndexes) {
		emailAddresses.add(new EmailAddressImpl());
	}
}
else {
	if (classPK > 0) {
		emailAddresses = EmailAddressServiceUtil.getEmailAddresses(className, classPK);

		emailAddressesIndexes = new int[emailAddresses.size()];

		for (int i = 0; i < emailAddresses.size(); i++) {
			emailAddressesIndexes[i] = i;
		}
	}

	if (emailAddresses.isEmpty()) {
		emailAddresses = new ArrayList<EmailAddress>();

		emailAddresses.add(new EmailAddressImpl());

		emailAddressesIndexes = new int[] {0};
	}

	if (emailAddressesIndexes == null) {
		emailAddressesIndexes = new int[0];
	}
}
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="additionalEmailAddresses" />

<div class="alert alert-info">
	<liferay-ui:message key="email-address-and-type-are-required-fields" />
</div>

<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.EMAIL_ADDRESS %>" message="please-select-a-type" />

<aui:fieldset id="additionalEmailAddresses">

	<%
	for (int i = 0; i < emailAddressesIndexes.length; i++) {
		int emailAddressesIndex = emailAddressesIndexes[i];

		EmailAddress emailAddress = emailAddresses.get(i);
	%>

		<aui:model-context bean="<%= emailAddress %>" model="<%= EmailAddress.class %>" />

		<div class="lfr-form-row lfr-form-row-inline">
			<div class="row-fields">
				<aui:input name='<%= "emailAddressId" + emailAddressesIndex %>' type="hidden" value="<%= emailAddress.getEmailAddressId() %>" />

				<aui:input cssClass="email-field" fieldParam='<%= "emailAddressAddress" + emailAddressesIndex %>' id='<%= "emailAddressAddress" + emailAddressesIndex %>' inlineField="<%= true %>" label="email-address" name="address" width="150px" />

				<aui:select inlineField="<%= true %>" label="type" listType="<%= className + ListTypeConstants.EMAIL_ADDRESS %>" name='<%= "emailAddressTypeId" + emailAddressesIndex %>' />

				<aui:input checked="<%= emailAddress.isPrimary() %>" cssClass="primary-ctrl" id='<%= "emailAddressPrimary" + emailAddressesIndex %>' inlineField="<%= true %>" label="primary" name="emailAddressPrimary" type="radio" value="<%= emailAddressesIndex %>" />
			</div>
		</div>

	<%
	}
	%>

	<aui:input name="emailAddressesIndexes" type="hidden" value="<%= StringUtil.merge(emailAddressesIndexes) %>" />
</aui:fieldset>

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields(
		{
			contentBox: '#<portlet:namespace />additionalEmailAddresses',
			fieldIndexes: '<portlet:namespace />emailAddressesIndexes',
			namespace: '<portlet:namespace />'
		}
	).render();
</aui:script>