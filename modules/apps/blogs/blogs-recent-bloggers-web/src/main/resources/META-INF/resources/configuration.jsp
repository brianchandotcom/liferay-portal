<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('organizationId', 'organizationName', this, '" + liferayPortletResponse.getNamespace() + "');";
				%>

				<aui:button name="removeOrganizationButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
			</div>

			<aui:script>
				var <portlet:namespace />selectOrganizationButton = document.getElementById(
					'<portlet:namespace />selectOrganizationButton'
				);

				<portlet:namespace />selectOrganizationButton.addEventListener(
					'click',
					(event) => {
						Liferay.Util.openSelectionModal({
							onSelect: function (event) {
								var form = document.getElementById('<portlet:namespace />fm');

								if (form) {
									const valueJSON = JSON.parse(event.value);

									var organizationId = form.querySelector(
										'#<portlet:namespace />organizationId'
									);

									if (organizationId) {
										organizationId.setAttribute(
											'value',
											valueJSON.organizationId
										);
									}

									var organizationName = form.querySelector(
										'#<portlet:namespace />organizationName'
									);

									if (organizationName) {
										organizationName.setAttribute('value', valueJSON.name);
									}

									Liferay.Util.toggleDisabled(
										'#<portlet:namespace />removeOrganizationButton',
										false
									);
								}
							},
							selectEventName: '<portlet:namespace />selectOrganization',
							title:
								'<liferay-ui:message arguments="organization" key="select-x" />',
							url:
								'<%= request.getAttribute(RecentBloggersWebKeys.ORGANIZATION_ITEM_SELECTOR_URL) %>',
						});
					}
				);

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
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>