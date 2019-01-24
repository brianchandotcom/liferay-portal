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
EditContactInformationDisplayContext editContactInformationDisplayContext = new EditContactInformationDisplayContext("email-address", renderResponse, request);

editContactInformationDisplayContext.setPortletDisplay(portletDisplay, portletName);

EmailAddress emailAddress = null;

if (editContactInformationDisplayContext.getPrimaryKey() > 0) {
	emailAddress = EmailAddressServiceUtil.getEmailAddress(editContactInformationDisplayContext.getPrimaryKey());
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "contact-information"), editContactInformationDisplayContext.getRedirect());

PortalUtil.addPortletBreadcrumbEntry(request, editContactInformationDisplayContext.getSheetTitle(), null);
%>

<portlet:actionURL name="/users_admin/update_contact_information" var="actionURL" />

<aui:form action="<%= actionURL %>" method="post" name="fm">
	<aui:input name="errorMVCPath" type="hidden" value="/common/edit_email_address.jsp" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="redirect" type="hidden" value="<%= editContactInformationDisplayContext.getRedirect() %>" />
	<aui:input name="className" type="hidden" value="<%= editContactInformationDisplayContext.getClassName() %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(editContactInformationDisplayContext.getClassPK()) %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.EMAIL_ADDRESS %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= String.valueOf(editContactInformationDisplayContext.getPrimaryKey()) %>" />

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
				<h2 class="sheet-title"><%= editContactInformationDisplayContext.getSheetTitle() %></h2>
			</div>

			<div class="sheet-section">
				<aui:model-context bean="<%= emailAddress %>" model="<%= EmailAddress.class %>" />

				<aui:input checked="<%= (emailAddress != null)? emailAddress.isPrimary() : false %>" id="emailAddressPrimary" label="make-primary" name="emailAddressPrimary" type="checkbox" />

				<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + editContactInformationDisplayContext.getClassName() + ListTypeConstants.EMAIL_ADDRESS %>" message="please-select-a-type" />

				<aui:select inlineField="<%= true %>" label="type" listType="<%= editContactInformationDisplayContext.getClassName() + ListTypeConstants.EMAIL_ADDRESS %>" name="emailAddressTypeId" />

				<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />

				<aui:input fieldParam="emailAddressAddress" id="emailAddressAddress" name="address" required="<%= true %>" />
			</div>

			<div class="sheet-footer">
				<aui:button primary="<%= true %>" type="submit" />

				<aui:button href="<%= editContactInformationDisplayContext.getRedirect() %>" type="cancel" />
			</div>
		</div>
	</div>
</aui:form>