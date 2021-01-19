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
CommerceAccountAddressAdminDisplayContext commerceAccountAddressAdminDisplayContext = (CommerceAccountAddressAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceAccountId = commerceAccountAddressAdminDisplayContext.getCommerceAccountId();
SearchContainer<CommerceAddress> commerceAddressSearchContainer = commerceAccountAddressAdminDisplayContext.getSearchContainer();

PortletURL portletURL = commerceAccountAddressAdminDisplayContext.getPortletURL();
%>

<c:if test="<%= commerceAccountAddressAdminDisplayContext.hasPermission(commerceAccountId, ActionKeys.UPDATE) %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="commerceAddresses"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= portletURL %>"
				selectedDisplayStyle="list"
			/>

			<portlet:renderURL var="addCommerceAddressURL">
				<portlet:param name="mvcRenderCommandName" value="/commerce_account_admin/edit_commerce_address" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceAccountId" value="<%= String.valueOf(commerceAccountId) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-address") %>'
					url="<%= addCommerceAddressURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= portletURL %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceAddresses();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="container-fluid container-fluid-max-xl">
		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCommerceAddressIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceAddresses"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceAddressSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceAddress"
					keyProperty="commerceAddressId"
					modelVar="commerceAddress"
				>
					<liferay-ui:search-container-column-text
						href="<%= commerceAccountAddressAdminDisplayContext.getEditCommerceAddressURL(commerceAddress.getCommerceAddressId()) %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= LanguageUtil.get(request, CommerceAddressConstants.getAddressTypeLabel(commerceAddress.getType())) %>"
					/>

					<liferay-ui:search-container-column-text
						property="street1"
					/>

					<liferay-ui:search-container-column-text
						property="city"
					/>

					<liferay-ui:search-container-column-text
						property="zip"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/address_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:script>
		function <portlet:namespace />deleteCommerceAddresses() {
			if (
				confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-addresses" />'
				)
			) {
				var form = window.document['<portlet:namespace />fm'];

				form.setAttribute('method', 'post');
				form['<portlet:namespace /><%= Constants.CMD %>'].value =
					'<%= Constants.DELETE %>';
				form[
					'<portlet:namespace />deleteCommerceAddressIds'
				].value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(
					form,
					'<portlet:actionURL name="/commerce_account_admin/edit_commerce_address" />'
				);
			}
		}
	</aui:script>
</c:if>