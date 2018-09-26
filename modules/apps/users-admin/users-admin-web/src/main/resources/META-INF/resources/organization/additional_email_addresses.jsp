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
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

long organizationId = organizationScreenNavigationDisplayContext.getOrganizationId();

List<EmailAddress> emailAddresses = EmailAddressServiceUtil.getEmailAddresses(Organization.class.getName(), organizationId);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="additionalEmailAddresses"
/>

<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.EMAIL_ADDRESS %>" message="please-select-a-type" />

<h3 class="sheet-subtitle">
	<span class="autofit-padded-no-gutters autofit-row">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text">
				<liferay-ui:message key="additional-email-addresses" />
			</span>
		</span>
		<span class="autofit-col">
			<liferay-ui:icon
				cssClass="modify-link"
				id="addEmailAddressLink"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message="add"
				method="get"
				url="javascript:;"
			/>
		</span>
	</span>
</h3>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	cssClass="lfr-search-container-wrapper"
	emptyResultsMessage="this-organization-does-not-have-any-additional-email-addresses"
	headerNames="email-address,type,"
	id="emailAddressesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= emailAddresses.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= emailAddresses.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.EmailAddress"
		escapedModel="<%= true %>"
		keyProperty="emailAddressId"
		modelVar="emailAddress"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="email-address"
			property="address"
		/>

		<%
		ListType emailAddressListType = ListTypeServiceUtil.getListType(emailAddress.getTypeId());

		String emailAddressTypeKey = emailAddressListType.getName();
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="type"
			value="<%= LanguageUtil.get(request, emailAddressTypeKey) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
		>
			<c:if test="<%= emailAddress.isPrimary() %>">
				<span class="label label-primary">
					<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
				</span>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/organization/email_address_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<portlet:actionURL name="/users_admin/update_organization_contact_information" var="editEmailAddressActionURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="listType" value="<%= ListTypeConstants.EMAIL_ADDRESS %>" />
	<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
</portlet:actionURL>

<portlet:renderURL var="editEmailAddressRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_email_address.jsp" />
</portlet:renderURL>

<aui:script use="liferay-portlet-url">
	function <portlet:namespace />openEditEmailAddressWindow(cmd, emailAddressId) {
		var editEmailAddressRenderURL = Liferay.PortletURL.createURL('<%= editEmailAddressRenderURL.toString() %>');

		editEmailAddressRenderURL.setParameter('emailAddressId', emailAddressId);

		var title = '<%= UnicodeLanguageUtil.get(request, "edit-email-address") %>';

		if (cmd === '<%= Constants.ADD %>') {
			var title = '<%= UnicodeLanguageUtil.get(request, "add-email-address") %>';
		}

		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true,
					height: 520,
					modal: true,
					resizable: false,
					'toolbars.footer': [
						{
							cssClass: 'btn-link close-modal',
							id: 'cancelButton',
							label: '<%= UnicodeLanguageUtil.get(request, "cancel") %>',
							on: {
								click: function() {
									Liferay.Util.getWindow('<portlet:namespace />editEmailAddressModal').hide();
								}
							}
						},
						{
							cssClass: 'btn-primary',
							id: 'saveButton',
							label: '<%= LanguageUtil.get(request, "save") %>',
							on: {
								click: function(event) {
									var contentWindow = document.getElementById('<portlet:namespace />editEmailAddressModal_iframe_').contentWindow;

									var formValidator = contentWindow.Liferay.Form.get('<portlet:namespace />emailAddressFm').formValidator;

									formValidator.validate();

									if (!formValidator.hasErrors()) {
										var windowDocument = contentWindow.document;

										var editEmailAddressActionURL = Liferay.PortletURL.createURL('<%= editEmailAddressActionURL.toString() %>');

										editEmailAddressActionURL.setParameter('entryId', emailAddressId);

										editEmailAddressActionURL.setParameter('emailAddressAddress', windowDocument.getElementById('<portlet:namespace />emailAddressAddress').value);
										editEmailAddressActionURL.setParameter('emailAddressPrimary', windowDocument.getElementById('<portlet:namespace />emailAddressPrimary').checked);
										editEmailAddressActionURL.setParameter('emailAddressTypeId', windowDocument.getElementById('<portlet:namespace />emailAddressTypeId').value);

										var organizationFm = document.getElementById('<portlet:namespace />fm');

										submitForm(organizationFm, editEmailAddressActionURL.toString());

										organizationFm.submit();

										Liferay.Util.getWindow('<portlet:namespace />editEmailAddressModal').hide();
									}
								}
							}
						}
					],
					width: '600'
				},
				id: '<portlet:namespace />editEmailAddressModal',
				title: title,
				uri: editEmailAddressRenderURL.toString()
			}
		);
	}

	$('#<portlet:namespace />addEmailAddressLink').on(
		'click',
		function(event) {
			<portlet:namespace />openEditEmailAddressWindow('<%= Constants.ADD %>', '');
		}
	);

	$('body').on(
		'click',
		'.edit-email-address',
		function(event) {
			event.preventDefault();

			var currentTarget = $(event.currentTarget);

			<portlet:namespace />openEditEmailAddressWindow('<%= Constants.EDIT %>', currentTarget.data('email-address-id'));
		}
	);
</aui:script>