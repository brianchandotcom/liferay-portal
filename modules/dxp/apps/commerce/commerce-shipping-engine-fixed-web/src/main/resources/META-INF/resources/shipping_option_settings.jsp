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
CommerceShippingFixedOptionRelsDisplayContext commerceShippingFixedOptionRelsDisplayContext = (CommerceShippingFixedOptionRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceShippingFixedOptionRel> commerceShippingFixedOptionRelSearchContainer = commerceShippingFixedOptionRelsDisplayContext.getSearchContainer();

boolean hasManageCommerceShippingMethodsPermission = CommercePermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);
%>

<c:choose>
	<c:when test="<%= commerceShippingFixedOptionRelsDisplayContext.isVisible() %>">
		<liferay-frontend:management-bar
			includeCheckBox="<%= true %>"
			searchContainerId="commerceShippingFixedOptionRels"
		>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"all"} %>'
					portletURL="<%= commerceShippingFixedOptionRelsDisplayContext.getPortletURL() %>"
				/>

				<liferay-frontend:management-bar-sort
					orderByCol="<%= commerceShippingFixedOptionRelsDisplayContext.getOrderByCol() %>"
					orderByType="<%= commerceShippingFixedOptionRelsDisplayContext.getOrderByType() %>"
					orderColumns='<%= new String[] {"country"} %>'
					portletURL="<%= commerceShippingFixedOptionRelsDisplayContext.getPortletURL() %>"
				/>
			</liferay-frontend:management-bar-filters>

			<liferay-frontend:management-bar-buttons>
				<liferay-frontend:management-bar-display-buttons
					displayViews='<%= new String[] {"list"} %>'
					portletURL="<%= commerceShippingFixedOptionRelsDisplayContext.getPortletURL() %>"
					selectedDisplayStyle="list"
				/>
			</liferay-frontend:management-bar-buttons>

			<c:if test="<%= hasManageCommerceShippingMethodsPermission %>">
				<liferay-frontend:management-bar-action-buttons>
					<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceShippingFixedOptionRels();" %>' icon="times" label="delete" />
				</liferay-frontend:management-bar-action-buttons>
			</c:if>
		</liferay-frontend:management-bar>

		<portlet:actionURL name="editCommerceShippingFixedOptionRel" var="editCommerceShippingFixedOptionRelActionURL" />

		<aui:form action="<%= editCommerceShippingFixedOptionRelActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCommerceShippingFixedOptionRelIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceShippingFixedOptionRels"
				searchContainer="<%= commerceShippingFixedOptionRelSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel"
					keyProperty="CommerceShippingFixedOptionRelId"
					modelVar="commerceShippingFixedOptionRel"
				>

					<%
					CommerceCountry commerceCountry = commerceShippingFixedOptionRel.getCommerceCountry();
					CommerceRegion commerceRegion = commerceShippingFixedOptionRel.getCommerceRegion();
					CommerceShippingFixedOption commerceShippingFixedOption = commerceShippingFixedOptionRel.getCommerceShippingFixedOption();
					CommerceShippingMethod commerceShippingMethod = commerceShippingFixedOptionRel.getCommerceShippingMethod();
					CommerceWarehouse commerceWarehouse = commerceShippingFixedOptionRel.getCommerceWarehouse();
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="warehouse"
					>
						<%= (commerceWarehouse == null) ? StringPool.STAR : HtmlUtil.escape(commerceWarehouse.getName()) %>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="shipping-method"
					>
						<%= HtmlUtil.escape(commerceShippingMethod.getName(languageId)) %>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="shipping-option"
					>
						<%= HtmlUtil.escape(commerceShippingFixedOption.getName(languageId)) %>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="country"
					>
						<%= (commerceCountry == null) ? StringPool.STAR : HtmlUtil.escape(commerceCountry.getName(languageId)) %>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="region"
					>
						<%= (commerceRegion == null) ? StringPool.STAR : HtmlUtil.escape(commerceRegion.getName()) %>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						property="zip"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/shipping_option_setting_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator markupView="lexicon" />
			</liferay-ui:search-container>
		</aui:form>

		<c:if test="<%= hasManageCommerceShippingMethodsPermission %>">
			<portlet:renderURL var="addCommerceShippingFixedOptionRelURL">
				<portlet:param name="mvcRenderCommandName" value="editCommerceShippingFixedOptionRel" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceShippingMethodId" value="<%= String.valueOf(commerceShippingFixedOptionRelsDisplayContext.getCommerceShippingMethodId()) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu>
				<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-shipping-option-setting") %>' url="<%= addCommerceShippingFixedOptionRelURL.toString() %>" />
			</liferay-frontend:add-menu>
		</c:if>

		<aui:script>
			function <portlet:namespace />deleteCommerceShippingFixedOptionRels() {
				if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-shipping-option-settings") %>')) {
					var form = AUI.$(document.<portlet:namespace />fm);

					form.fm('deleteCommerceShippingFixedOptionRelIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

					submitForm(form);
				}
			}
		</aui:script>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="there-are-no-shipping-options" />
			<liferay-ui:message key="please-add-at-least-one-shipping-option" />
		</div>
	</c:otherwise>
</c:choose>