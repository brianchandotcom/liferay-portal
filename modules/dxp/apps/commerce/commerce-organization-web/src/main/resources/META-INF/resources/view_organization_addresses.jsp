<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationAddressesDisplayContext commerceOrganizationAddressesDisplayContext = (CommerceOrganizationAddressesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:form action="<%= String.valueOf(commerceOrganizationAddressesDisplayContext.getPortletURL()) %>" method="post" name="searchFm">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="commerceAddresses"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "toggleFilter(false);" %>'
				iconCssClass="icon-filter"
				id="filterButton"
				label="filter"
			/>

			<c:if test="<%= commerceOrganizationAddressesDisplayContext.hasManageCommerceAddressPermission() %>">
				<liferay-frontend:add-menu
					inline="<%= true %>"
				>
					<liferay-frontend:add-menu-item
						title='<%= LanguageUtil.get(request, "add-address") %>'
						url="<%= commerceOrganizationAddressesDisplayContext.getAddCommerceAddressHref() %>"
					/>
				</liferay-frontend:add-menu>
			</c:if>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<li>
				<liferay-portlet:renderURLParams varImpl="searchURL" />

				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</li>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceAddresses();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="form-group-autofit hide" id="<portlet:namespace />filterSettings">
		<div class="form-group-item">
			<aui:button cssClass="btn-outline-borderless btn-outline-primary" type="submit" value="apply-filters" />
		</div>
	</div>
</aui:form>

<portlet:actionURL name="editCommerceAddress" var="editCommerceAddressActionURL" />

<aui:form action="<%= editCommerceAddressActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCommerceAddressIds" type="hidden" />

	<div class="container-fluid-1280">
		<liferay-ui:search-container
			id="commerceAddresses"
			searchContainer="<%= commerceOrganizationAddressesDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceAddress"
				cssClass="entry-display-style"
				keyProperty="commerceAddressId"
				modelVar="commerceAddress"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= commerceOrganizationAddressesDisplayContext.getEditCommerceAddressHref(commerceAddress.getCommerceAddressId()) %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="street1"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="city"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="zip"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/address_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				searchContainer="<%= commerceOrganizationAddressesDisplayContext.getSearchContainer() %>"
			/>
		</liferay-ui:search-container>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />deleteCommerceAddresses() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-addresses" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceAddressIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}

	function <portlet:namespace />editCommerceAddress(title, uri) {
		Liferay.Util.openWindow(
			{
				dialog: {
					centered: true,
					destroyOnClose: true,
					height: 800,
					modal: true,
					width: 900
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				id: 'editCommerceAddressDialog',
				title: title,
				uri: uri
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />closePopup',
		function(dialogId) {
			var dialog = Liferay.Util.Window.getById(dialogId);

			dialog.destroy();
		},
		['liferay-util-window']
	);
</aui:script>