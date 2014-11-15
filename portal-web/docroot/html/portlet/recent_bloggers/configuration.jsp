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

<%@ include file="/html/portlet/recent_bloggers/init.jsp" %>

<%
String organizationName = StringPool.BLANK;

Organization organization = null;

if (organizationId > 0) {
	organization = OrganizationLocalServiceUtil.getOrganization(organizationId);

	organizationName = organization.getName();
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--organizationId--" type="hidden" value="<%= organizationId %>" />

	<aui:fieldset>
		<aui:select name="preferences--selectionMethod--" value="<%= selectionMethod %>">
			<aui:option label="users" />
			<aui:option label="scope" />
		</aui:select>

		<div class="form-group" id="<portlet:namespace />UsersSelectionOptions">
			<aui:input label="organization" name="organizationName" type="resource" value="<%= organizationName %>" />

			<aui:button name="selectOrganizationButton" value="select" />

			<aui:button disabled="<%= organizationId <= 0 %>" name="removeOrganizationButton" onClick='<%= renderResponse.getNamespace() + "removeOrganization();" %>' value="remove" />
		</div>

		<aui:select name="preferences--displayStyle--">
			<aui:option label="user-name-and-image" selected='<%= displayStyle.equals("user-name-and-image") %>' />
			<aui:option label="user-name" selected='<%= displayStyle.equals("user-name") %>' />
		</aui:select>

		<aui:select label="maximum-bloggers-to-display" name="preferences--max--" value="<%= max %>">
			<aui:option label="1" />
			<aui:option label="2" />
			<aui:option label="3" />
			<aui:option label="4" />
			<aui:option label="5" />
			<aui:option label="10" />
			<aui:option label="15" />
			<aui:option label="20" />
			<aui:option label="25" />
			<aui:option label="30" />
			<aui:option label="40" />
			<aui:option label="50" />
			<aui:option label="60" />
			<aui:option label="70" />
			<aui:option label="80" />
			<aui:option label="90" />
			<aui:option label="100" />
		</aui:select>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	AUI.$('#<portlet:namespace />selectOrganizationButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					id: '<portlet:namespace />selectOrganization',
					title: '<liferay-ui:message arguments="organization" key="select-x" />',
					uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/select_organization" /><portlet:param name="tabs1" value="organizations" /></portlet:renderURL>'
				},
				function(event) {
					var form = AUI.$(document.<portlet:namespace />fm);

					form.fm('organizationId').val(event.organizationid);

					form.fm('organizationName').val(event.name);

					Liferay.Util.toggleDisabled('#<portlet:namespace />removeOrganizationButton', false);
				}
			);
		}
	);

	function <portlet:namespace />removeOrganization() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('organizationId').val('');

		form.fm('organizationName').val('');

		Liferay.Util.toggleDisabled('#<portlet:namespace />removeOrganizationButton', true);
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />selectionMethod', 'users', '<portlet:namespace />UsersSelectionOptions');
</aui:script>