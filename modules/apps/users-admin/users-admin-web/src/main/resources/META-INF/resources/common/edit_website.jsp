<%@ page
	import="com.liferay.users.admin.web.internal.constants.UsersAdminWebKeys" %><%--
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
String backURL = ParamUtil.getString(request, "backURL");
String className = ParamUtil.getString(request, "className");
Long classPK = ParamUtil.getLong(request, "classPK");
long primaryKey = ParamUtil.getLong(request, "primaryKey", 0L);
String redirect = ParamUtil.getString(request, "redirect");
String sheetTitle = ParamUtil.getString(request, "sheetTitle");

Website website = null;

if (primaryKey > 0L) {
	website = WebsiteServiceUtil.getWebsite(primaryKey);
}

if (portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);

	String portletTitle = (String)request.getAttribute(UsersAdminWebKeys.PORTLET_TITLE);

	renderResponse.setTitle(portletTitle);

}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "contact-information"), redirect);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, sheetTitle), null);
%>

<portlet:actionURL name="/users_admin/update_contact_information" var="actionURL" />

<aui:form action="<%= actionURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="className" type="hidden" value="<%= className %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="errorMvcRenderCommandName" type="hidden" value="/users_admin/edit_website" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.WEBSITE %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= String.valueOf(primaryKey) %>" />
	<aui:input name="sheetTitle" type="hidden" value="<%= sheetTitle %>" />

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
			<h2 class="sheet-title"><liferay-ui:message key="<%= sheetTitle %>" /></h2>

			<div class="sheet-section">
				<clay:alert
					message='<%= LanguageUtil.format(request, "url-must-start-with-x-or-x", new String[] {"http://", "https://"}, false) %>'
					style="info"
					title='<%= LanguageUtil.get(request, "info") + ":" %>'
				/>

				<aui:model-context bean="<%= website %>" model="<%= Website.class %>" />

				<aui:input checked="<%= (website != null)? website.isPrimary() : false %>" id="websitePrimary" label="make-primary" name="websitePrimary" type="checkbox" />

				<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.WEBSITE %>" message="please-select-a-type" />

				<aui:select inlineField="<%= true %>" label="type" listType="<%= className + ListTypeConstants.WEBSITE %>" name="websiteTypeId" />

				<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-url" />

				<aui:input fieldParam="websiteUrl" id="websiteUrl" name="url" required="<%= true %>" />
			</div>

			<div class="sheet-footer">
				<aui:button primary="<%= true %>" type="submit" />

				<aui:button href="<%= redirect %>" type="cancel" />
			</div>
		</div>
	</div>
</aui:form>