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

<%@ include file="/blogs_aggregator/init.jsp" %>

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
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:select name="preferences--selectionMethod--" value="<%= selectionMethod %>">
					<aui:option label="users" />
					<aui:option label="scope" />
				</aui:select>

				<div class="form-group <%= selectionMethod.equals("users") ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />usersSelectionOptions">
					<aui:input label="organization" name="organizationName" type="resource" value="<%= organizationName %>" />

					<aui:button name="selectOrganizationButton" value="select" />

					<%
					String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('organizationId', 'organizationName', this, '" + liferayPortletResponse.getNamespace() + "');";
					%>

					<aui:button name="removeOrganizationButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
				</div>

				<aui:script sandbox="<%= true %>">
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
										var organizationId = form.querySelector(
											'#<portlet:namespace />organizationId'
										);

										if (organizationId) {
											organizationId.setAttribute('value', event.entityid);
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
									}

									Liferay.Util.toggleDisabled(
										'#<portlet:namespace />removeOrganizationButton',
										false
									);
								},

								<%
								String portletId = PortletProviderUtil.getPortletId(User.class.getName(), PortletProvider.Action.VIEW);
								%>

								selectEventName:
									'<%= PortalUtil.getPortletNamespace(portletId) %>selectOrganization',

								title:
									'<liferay-ui:message arguments="organization" key="select-x" />',

								<%
								PortletURL selectOrganizationURL = PortletURLBuilder.create(
									PortletProviderUtil.getPortletURL(request, Organization.class.getName(), PortletProvider.Action.BROWSE)
								).setTabs1(
									"organizations"
								).setWindowState(
									LiferayWindowState.POP_UP
								).buildPortletURL();
								%>

								url: '<%= selectOrganizationURL.toString() %>',
							});
						}
					);
				</aui:script>

				<aui:script sandbox="<%= true %>">
					var selectionMethodElement = document.getElementById(
						'<portlet:namespace />selectionMethod'
					);

					if (selectionMethodElement) {
						selectionMethodElement.addEventListener('change', (event) => {
							var usersSelectionOptions = document.getElementById(
								'<portlet:namespace />usersSelectionOptions'
							);

							if (usersSelectionOptions) {
								var showUsersSelectionOptions = !(
									selectionMethodElement.value === 'users'
								);

								usersSelectionOptions.classList.toggle(
									'hide',
									showUsersSelectionOptions
								);
							}
						});
					}
				</aui:script>

				<aui:select name="preferences--displayStyle--" value="<%= displayStyle %>">
					<aui:option label="body-and-image" />
					<aui:option label="body" />
					<aui:option label="abstract" />
					<aui:option label="abstract-without-title" />
					<aui:option label="quote" />
					<aui:option label="quote-without-title" />
					<aui:option label="title" />
				</aui:select>

				<aui:select label="maximum-items-to-display" name="preferences--max--" value="<%= max %>">
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

				<aui:input name="preferences--showTags--" type="checkbox" value="<%= showTags %>" />

				<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
					<div class="sheet-subtitle">
						<liferay-ui:message key="rss-settings" />
					</div>

					<liferay-rss:rss-settings
						delta="<%= rssDelta %>"
						displayStyle="<%= rssDisplayStyle %>"
						enabled="<%= enableRSS %>"
						feedType="<%= rssFeedType %>"
					/>
				</c:if>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>