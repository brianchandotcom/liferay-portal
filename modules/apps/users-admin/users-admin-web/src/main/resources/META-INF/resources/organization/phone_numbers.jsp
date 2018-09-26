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

Organization organization = organizationScreenNavigationDisplayContext.getOrganization();

List<Phone> phones = PhoneServiceUtil.getPhones(Organization.class.getName(), organizationId);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="phoneNumbers"
/>

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.PHONE %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= PhoneNumberException.class %>" message="please-enter-a-valid-phone-number" />
<liferay-ui:error exception="<%= PhoneNumberExtensionException.class %>" message="please-enter-a-valid-phone-number-extension" />

<h3 class="sheet-subtitle">
	<span class="autofit-padded-no-gutters autofit-row">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text">
				<liferay-ui:message key="phone-numbers" />
			</span>
		</span>
		<span class="autofit-col">
			<liferay-ui:icon
				cssClass="modify-link"
				id="addPhoneNumberLink"
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
	emptyResultsMessage="this-organization-does-not-have-any-phone-numbers"
	headerNames="phone-number,type,extension,"
	id="phonesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= phones.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= phones.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Phone"
		escapedModel="<%= true %>"
		keyProperty="phoneId"
		modelVar="phone"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="phone-number"
			property="number"
		/>

		<%
		ListType phoneListType = ListTypeServiceUtil.getListType(phone.getTypeId());

		String phoneTypeKey = phoneListType.getName();
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="type"
			value="<%= LanguageUtil.get(request, phoneTypeKey) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="extension"
			property="extension"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
		>
			<c:if test="<%= phone.isPrimary() %>">
				<span class="label label-primary">
					<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
				</span>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/organization/phone_number_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<portlet:actionURL name="/users_admin/update_organization_contact_information" var="editPhoneActionURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="listType" value="<%= ListTypeConstants.PHONE %>" />
	<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
</portlet:actionURL>

<portlet:renderURL var="editPhoneRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_phone_number.jsp" />
</portlet:renderURL>

<aui:script use="liferay-portlet-url">
	function <portlet:namespace />openEditPhoneWindow(cmd, phoneId) {
		var editPhoneRenderURL = Liferay.PortletURL.createURL('<%= editPhoneRenderURL.toString() %>');

		editPhoneRenderURL.setParameter('phoneId', phoneId);

		var title = '<%= UnicodeLanguageUtil.get(request, "edit-phone-number") %>';

		if (cmd === '<%= Constants.ADD %>') {
			var title = '<%= UnicodeLanguageUtil.get(request, "add-phone-number") %>';
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
									Liferay.Util.getWindow('<portlet:namespace />editPhoneModal').hide();
								}
							}
						},
						{
							cssClass: 'btn-primary',
							id: 'addButton',
							label: '<%= LanguageUtil.get(request, "save") %>',
							on: {
								click: function(event) {
									var windowDocument = document.getElementById('<portlet:namespace />editPhoneModal_iframe_').contentWindow.document;

									var editPhoneActionURL = Liferay.PortletURL.createURL('<%= editPhoneActionURL.toString() %>');

									editPhoneActionURL.setParameter('entryId', phoneId);

									editPhoneActionURL.setParameter('phoneExtension', windowDocument.getElementById('<portlet:namespace />phoneExtension').value);
									editPhoneActionURL.setParameter('phoneNumber', windowDocument.getElementById('<portlet:namespace />phoneNumber').value);
									editPhoneActionURL.setParameter('phonePrimary', windowDocument.getElementById('<portlet:namespace />phonePrimary').checked);
									editPhoneActionURL.setParameter('phoneTypeId', windowDocument.getElementById('<portlet:namespace />phoneTypeId').value);

									var organizationFm = document.getElementById('<portlet:namespace />fm');

									submitForm(organizationFm, editPhoneActionURL.toString());

									organizationFm.submit();

									Liferay.Util.getWindow('<portlet:namespace />editPhoneModal').hide();
								}
							}
						}
					],
					width: '600'
				},
				id: '<portlet:namespace />editPhoneModal',
				title: title,
				uri: editPhoneRenderURL.toString()
			}
		);
	}

	$('#<portlet:namespace />addPhoneNumberLink').on(
		'click',
		function(event) {
			<portlet:namespace />openEditPhoneWindow('<%= Constants.ADD %>', '');
		}
	);

	$('body').on(
		'click',
		'.edit-phone',
		function(event) {
			event.preventDefault();

			var currentTarget = $(event.currentTarget);

			<portlet:namespace />openEditPhoneWindow('<%= Constants.EDIT %>', currentTarget.data('phone-id'));
		}
	);
</aui:script>