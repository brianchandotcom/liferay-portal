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
String organizationName = StringPool.BLANK;

Organization organization = null;

if (organizationId > 0) {
	organization = OrganizationLocalServiceUtil.getOrganization(organizationId);

	organizationName = organization.getName();
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--organizationId--" type="hidden" value="<%= organizationId %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset>
			<aui:select name="preferences--selectionMethod--" value="<%= selectionMethod %>">
				<aui:option label="users" />
				<aui:option label="scope" />
			</aui:select>

			<div class="form-group" id="<portlet:namespace />UsersSelectionOptions">
				<aui:input label="organization" name="organizationName" type="resource" value="<%= organizationName %>" />

				<aui:button name="selectOrganizationButton" value="select" />

				<%
				String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('organizationId', 'organizationName', this, '" + renderResponse.getNamespace() + "');";
				%>

				<aui:button name="removeOrganizationButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
			</div>

			<aui:script>
				var <portlet:namespace />selectOrganizationButton = document.getElementById(
					'<portlet:namespace />selectOrganizationButton'
				);

				if (<portlet:namespace />selectOrganizationButton) {
					<portlet:namespace />selectOrganizationButton.addEventListener(
						'click',
						function(event) {
							Liferay.Util.selectEntity(
								{
									dialog: {
										constrain: true,
										destroyOnHide: true,
										modal: true
									},

									<%
									String portletId = PortletProviderUtil.getPortletId(User.class.getName(), PortletProvider.Action.VIEW);
									%>

									id:
										'<%= PortalUtil.getPortletNamespace(portletId) %>selectOrganization',
									title:
										'<liferay-ui:message arguments="organization" key="select-x" />',

									<%
									PortletURL selectOrganizationURL = PortletProviderUtil.getPortletURL(request, Organization.class.getName(), PortletProvider.Action.BROWSE);

									selectOrganizationURL.setWindowState(LiferayWindowState.POP_UP);
									%>

									uri: '<%= selectOrganizationURL.toString() %>'
								},
								function(event) {
									var form = document.getElementById(
										'<portlet:namespace />fm'
									);

									if (form) {
										var organizationId = form.querySelector(
											'#<portlet:namespace />organizationId'
										);

										if (organizationId) {
											organizationId.setAttribute(
												'value',
												event.entityid
											);
										}

										var organizationName = form.querySelector(
											'#<portlet:namespace />organizationName'
										);

										if (organizationName) {
											organizationName.setAttribute(
												'value',
												event.entityname
											);
										}

										Liferay.Util.toggleDisabled(
											'#<portlet:namespace />removeOrganizationButton',
											false
										);
									}
								}
							);
						}
					);
				}

				Liferay.Util.toggleSelectBox(
					'<portlet:namespace />selectionMethod',
					'users',
					'<portlet:namespace />UsersSelectionOptions'
				);
			</aui:script>

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
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>