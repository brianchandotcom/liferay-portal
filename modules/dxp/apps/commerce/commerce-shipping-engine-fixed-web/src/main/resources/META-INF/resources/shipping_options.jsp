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
CommerceShippingFixedOptionsDisplayContext commerceShippingFixedOptionsDisplayContext = (CommerceShippingFixedOptionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceShippingFixedOption> commerceShippingFixedOptionSearchContainer = commerceShippingFixedOptionsDisplayContext.getSearchContainer();

boolean hasManageCommerceShippingMethodsPermission = CommercePermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceShippingFixedOptions"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceShippingFixedOptionsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceShippingFixedOptionsDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceShippingFixedOptionsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= commerceShippingFixedOptionsDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceShippingFixedOptionsDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCommerceShippingMethodsPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceShippingFixedOptions();" %>' icon="times" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<portlet:actionURL name="editCommerceShippingFixedOption" var="editCommerceShippingFixedOptionActionURL" />

<aui:form action="<%= editCommerceShippingFixedOptionActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCommerceShippingFixedOptionIds" type="hidden" />

	<liferay-ui:search-container
		id="commerceShippingFixedOptions"
		searchContainer="<%= commerceShippingFixedOptionSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption"
			keyProperty="commerceShippingFixedOptionId"
			modelVar="commerceShippingFixedOption"
		>
			<liferay-portlet:renderURL var="editCommerceShippingFixedOptionURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="editCommerceShippingFixedOption" />
				<portlet:param name="commerceShippingFixedOptionId" value="<%= String.valueOf(commerceShippingFixedOption.getCommerceShippingFixedOptionId()) %>" />
				<portlet:param name="commerceShippingMethodId" value="<%= String.valueOf(commerceShippingFixedOption.getCommerceShippingMethodId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href='<%= commerceShippingFixedOptionsDisplayContext.getEditURL("editCommerceShippingFixedOption", false, editCommerceShippingFixedOptionURL) %>'
				name="name"
			>
				<%= HtmlUtil.escape(commerceShippingFixedOption.getName(languageId)) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
			>
				<%= HtmlUtil.escape(commerceShippingFixedOption.getDescription(languageId)) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="amount"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="priority"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/shipping_option_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= hasManageCommerceShippingMethodsPermission %>">
	<liferay-portlet:renderURL var="addCommerceShippingFixedOptionURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="editCommerceShippingFixedOption" />
		<portlet:param name="commerceShippingMethodId" value="<%= String.valueOf(commerceShippingFixedOptionsDisplayContext.getCommerceShippingMethodId()) %>" />
	</liferay-portlet:renderURL>

	<%
	String url = commerceShippingFixedOptionsDisplayContext.getEditURL("editCommerceShippingFixedOption", true, addCommerceShippingFixedOptionURL);
	%>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(resourceBundle, "add-shipping-option") %>' url="<%= url %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script>
	function <portlet:namespace />deleteCommerceShippingFixedOptions() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-shipping-options" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceShippingFixedOptionIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}

	function <portlet:namespace/>editCommerceShippingFixedOption(isNew, uri) {
		var title = '<liferay-ui:message key="edit-shipping-option" />';

		if (isNew) {
			title = '<liferay-ui:message key="add-shipping-option" />';
		}

		Liferay.Util.openWindow(
			{
				dialog: {
					centered: true,
					destroyOnClose: true,
					height: 600,
					modal: true,
					width: 600
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				id: 'editShippingFixedOptionDialog',
				title: title,
				uri: uri
			}
		);
	}

	Liferay.provide(
		window,
		'closePopup',
		function(dialogId) {
			var dialog = Liferay.Util.Window.getById(dialogId);

			dialog.destroy();
		},
		['liferay-util-window']
	);
</aui:script>