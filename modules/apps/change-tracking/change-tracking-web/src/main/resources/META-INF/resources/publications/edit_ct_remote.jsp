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

			<aui:button-row>
				<aui:button id="saveButton" type="submit" value='<%= LanguageUtil.get(request, "submit") %>' />

				<aui:button href="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</aui:form>
	</clay:sheet>
</clay:container-fluid>