<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CTRemote ctRemote = (CTRemote)request.getAttribute(CTWebKeys.CT_REMOTE);

if (ctRemote != null) {
	renderResponse.setTitle(LanguageUtil.get(request, "edit-remote-server"));
}
else {
	renderResponse.setTitle(LanguageUtil.get(request, "add-remote-server"));
}
%>

<liferay-portlet:actionURL name="/change_tracking/edit_ct_remote" var="actionURL">
	<liferay-portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:actionURL>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:sheet>
		<aui:form action="<%= actionURL %>" cssClass="lfr-export-dialog" method="post" name="editCTRemoteFm">
			<aui:input name="ctRemoteId" type="hidden" value="<%= (ctRemote != null) ? ctRemote.getCtRemoteId() : 0 %>" />

			<aui:input label="name" name="name" placeholder="ct-remote-name-placeholder" value='<%= (ctRemote != null) ? ctRemote.getName() : "" %>'>
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(CTRemote.class.getName(), "name") %></aui:validator>
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="description" name="description" placeholder="ct-remote-description-placeholder" type="textarea" value='<%= (ctRemote != null) ? ctRemote.getDescription() : "" %>'>
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(CTRemote.class.getName(), "description") %></aui:validator>
			</aui:input>

			<aui:input label="url" name="url" placeholder="ct-remote-url-placeholder" value='<%= (ctRemote != null) ? ctRemote.getUrl() : "" %>'>
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(CTRemote.class.getName(), "url") %></aui:validator>
				<aui:validator name="required" />
				<aui:validator name="url" />
			</aui:input>

			<aui:input label="client-id" name="clientId" placeholder="ct-remote-client-id-placeholder" value='<%= (ctRemote != null) ? ctRemote.getClientId() : "" %>'>
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(CTRemote.class.getName(), "clientId") %></aui:validator>
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="client-secret" name="clientSecret" placeholder="ct-remote-client-secret-placeholder" type="password" value='<%= (ctRemote != null) ? ctRemote.getClientSecret() : "" %>'>
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(CTRemote.class.getName(), "clientSecret") %></aui:validator>
				<aui:validator name="required" />
			</aui:input>

			<aui:button-row>
				<aui:button id="saveButton" type="submit" value='<%= LanguageUtil.get(request, "submit") %>' />

				<aui:button href="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</aui:form>
	</clay:sheet>
</clay:container-fluid>