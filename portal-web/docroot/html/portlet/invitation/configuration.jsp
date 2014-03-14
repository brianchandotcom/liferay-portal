<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/invitation/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-ui:error key="emailMessageBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailMessageSubject" message="please-enter-a-valid-subject" />

	<liferay-ui:email-notification-settings
		emailBody='<%= ParamUtil.getString(request, "emailMessageBody", InvitationUtil.getEmailMessageBody(portletPreferences)) %>'
		emailDefinitionTerms="<%= InvitationUtil.getEmailDefinitionTerms(renderRequest) %>"
		emailParam="emailMessage"
		emailSubject='<%= ParamUtil.getString(request, "emailMessageSubject", InvitationUtil.getEmailMessageSubject(portletPreferences)) %>'
		showEmailEnabled="<%= false %>"
	/>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailMessageBody--'].value = window['<portlet:namespace />emailMessage'].getHTML();
		}
		catch (e) {
		}

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>