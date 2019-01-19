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
Long classPK = ParamUtil.getLong(request, "classPK");
long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId");
long primaryKey = ParamUtil.getLong(request, "primaryKey", 0L);
String redirect = ParamUtil.getString(request, "redirect");

EmailAddress emailAddress = null;

if (primaryKey > 0L) {
	emailAddress = EmailAddressServiceUtil.getEmailAddress(primaryKey);
}

EditContactInformationDisplayContext editContactInformationDisplayContext = new EditContactInformationDisplayContext(className, classPK, parentOrganizationId, renderResponse, request);

if (!portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(editContactInformationDisplayContext.getBackURL());

	renderResponse.setTitle(editContactInformationDisplayContext.getPortletTitle());
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "contact-information"), redirect);

String sheetTitle;

if (primaryKey > 0) {
	sheetTitle = LanguageUtil.get(request, "edit-email-address");
}
else {
	sheetTitle = LanguageUtil.get(request, "add-email-address");
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, sheetTitle), null);
%>

<portlet:actionURL name="/users_admin/update_contact_information" var="actionURL" />

<aui:form action="<%= actionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="className" type="hidden" value="<%= className %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="errorMVCPath" type="hidden" value="/common/edit_email_address.jsp" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.EMAIL_ADDRESS %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= String.valueOf(primaryKey) %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<div class="container-fluid container-fluid-max-xl">
		<div class="sheet-lg" id="breadcrumb">
			<liferay-ui:breadcrumb
				showCurrentGroup="<%= false %>"
				showGuestGroup="<%= false %>"
				showLayout="<%= false %>"
				showPortletBreadcrumb="<%= true %>"
			/>
		</div>

		<div class="sheet sheet-lg">
			<div class="sheet-header">
				<h2 class="sheet-title"><liferay-ui:message key="<%= sheetTitle %>" /></h2>
			</div>

			<div class="sheet-section">
				<aui:model-context bean="<%= emailAddress %>" model="<%= EmailAddress.class %>" />

				<aui:input checked="<%= (emailAddress != null)? emailAddress.isPrimary() : false %>" id="emailAddressPrimary" label="make-primary" name="emailAddressPrimary" type="checkbox" />

				<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.EMAIL_ADDRESS %>" message="please-select-a-type" />

				<aui:select inlineField="<%= true %>" label="type" listType="<%= className + ListTypeConstants.EMAIL_ADDRESS %>" name="emailAddressTypeId" />

				<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />

				<aui:input fieldParam="emailAddressAddress" id="emailAddressAddress" name="address" required="<%= true %>" />
			</div>

			<div class="sheet-footer">
				<aui:button primary="<%= true %>" type="submit" />

				<aui:button href="<%= redirect %>" type="cancel" />
			</div>
		</div>
	</div>
</aui:form>