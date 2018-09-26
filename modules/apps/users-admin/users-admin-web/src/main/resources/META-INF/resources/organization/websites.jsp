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

List<Website> websites = WebsiteServiceUtil.getWebsites(Organization.class.getName(), organizationId);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="websites"
/>

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.WEBSITE %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-url" />

<h3 class="sheet-subtitle">
	<span class="autofit-padded-no-gutters autofit-row">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text">
				<liferay-ui:message key="websites" />
			</span>
		</span>
		<span class="autofit-col">
			<liferay-ui:icon
				cssClass="modify-link"
				id="addWebsiteLink"
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
	emptyResultsMessage="this-organization-does-not-have-any-websites"
	headerNames="website,type,"
	id="websitesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= websites.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= websites.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Website"
		escapedModel="<%= true %>"
		keyProperty="websiteId"
		modelVar="website"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="website"
			property="url"
		/>

		<%
		ListType websiteListType = ListTypeServiceUtil.getListType(website.getTypeId());

		String websiteTypeKey = websiteListType.getName();
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="type"
			value="<%= LanguageUtil.get(request, websiteTypeKey) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
		>
			<c:if test="<%= website.isPrimary() %>">
				<span class="label label-primary">
					<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
				</span>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/organization/website_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<portlet:actionURL name="/users_admin/update_organization_contact_information" var="editWebsiteActionURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="listType" value="<%= ListTypeConstants.WEBSITE %>" />
	<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
</portlet:actionURL>

<portlet:renderURL var="editWebsiteRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_website.jsp" />
</portlet:renderURL>

<aui:script use="liferay-portlet-url">
	function <portlet:namespace />openEditWebsiteWindow(cmd, websiteId) {
		var editWebsiteRenderURL = Liferay.PortletURL.createURL('<%= editWebsiteRenderURL.toString() %>');

		editWebsiteRenderURL.setParameter('websiteId', websiteId);

		var title = '<%= UnicodeLanguageUtil.get(request, "edit-website") %>';

		if (cmd === '<%= Constants.ADD %>') {
			var title = '<%= UnicodeLanguageUtil.get(request, "add-website") %>';
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
									Liferay.Util.getWindow('<portlet:namespace />editWebsiteModal').hide();
								}
							}
						},
						{
							cssClass: 'btn-primary',
							id: 'addButton',
							label: '<%= LanguageUtil.get(request, "save") %>',
							on: {
								click: function(event) {
									var contentWindow = document.getElementById('<portlet:namespace />editWebsiteModal_iframe_').contentWindow;

									var formValidator = contentWindow.Liferay.Form.get('<portlet:namespace />websiteFm').formValidator;

									formValidator.validate();

									if (!formValidator.hasErrors()) {
										var windowDocument = contentWindow.document;

										var editWebsiteActionURL = Liferay.PortletURL.createURL('<%= editWebsiteActionURL.toString() %>');

										editWebsiteActionURL.setParameter('entryId', websiteId);

										editWebsiteActionURL.setParameter('websitePrimary', windowDocument.getElementById('<portlet:namespace />websitePrimary').checked);
										editWebsiteActionURL.setParameter('websiteTypeId', windowDocument.getElementById('<portlet:namespace />websiteTypeId').value);
										editWebsiteActionURL.setParameter('websiteUrl', windowDocument.getElementById('<portlet:namespace />websiteUrl').value);

										var organizationFm = document.getElementById('<portlet:namespace />fm');

										submitForm(organizationFm, editWebsiteActionURL.toString());

										organizationFm.submit();

										Liferay.Util.getWindow('<portlet:namespace />editWebsiteModal').hide();
									}
								}
							}
						}
					],
					width: '600'
				},
				id: '<portlet:namespace />editWebsiteModal',
				title: title,
				uri: editWebsiteRenderURL.toString()
			}
		);
	}

	$('#<portlet:namespace />addWebsiteLink').on(
		'click',
		function(event) {
			<portlet:namespace />openEditWebsiteWindow('<%= Constants.ADD %>', '');
		}
	);

	$('body').on(
		'click',
		'.edit-website',
		function(event) {
			event.preventDefault();

			var currentTarget = $(event.currentTarget);

			<portlet:namespace />openEditWebsiteWindow('<%= Constants.EDIT %>', currentTarget.data('website-id'));
		}
	);
</aui:script>