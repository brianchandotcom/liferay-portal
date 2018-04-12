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
CommerceTaxFixedRateAddressRelsDisplayContext commerceTaxFixedRateAddressRelsDisplayContext = (CommerceTaxFixedRateAddressRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRelSearchContainer = commerceTaxFixedRateAddressRelsDisplayContext.getSearchContainer();

boolean hasManageCommerceTaxMethodsPermission = CommercePermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceTaxFixedRateAddressRels"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceTaxFixedRateAddressRelsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceTaxFixedRateAddressRelsDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceTaxFixedRateAddressRelsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= commerceTaxFixedRateAddressRelsDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceTaxFixedRateAddressRelsDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= hasManageCommerceTaxMethodsPermission %>">
			<portlet:renderURL var="addCommerceTaxFixedRateAddressRelURL">
				<portlet:param name="mvcRenderCommandName" value="editCommerceTaxFixedRateAddressRel" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceTaxMethodId" value="<%= String.valueOf(commerceTaxFixedRateAddressRelsDisplayContext.getCommerceTaxMethodId()) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(resourceBundle, "add-tax-rate-setting") %>'
					url="<%= addCommerceTaxFixedRateAddressRelURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCommerceTaxMethodsPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceTaxFixedRateAddressRels();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<portlet:actionURL name="editCommerceTaxFixedRateAddressRel" var="editCommerceTaxFixedRateAddressRelActionURL" />

<aui:form action="<%= editCommerceTaxFixedRateAddressRelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCommerceTaxFixedRateAddressRelIds" type="hidden" />

	<liferay-ui:search-container
		id="commerceTaxFixedRateAddressRels"
		searchContainer="<%= commerceTaxFixedRateAddressRelSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel"
			keyProperty="commerceTaxFixedRateAddressRelId"
			modelVar="commerceTaxFixedRateAddressRel"
		>

			<%
			CommerceCountry commerceCountry = commerceTaxFixedRateAddressRel.getCommerceCountry();
			CommerceRegion commerceRegion = commerceTaxFixedRateAddressRel.getCommerceRegion();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="tax-rate"
			>

				<%
				CPTaxCategory cpTaxCategory = commerceTaxFixedRateAddressRel.getCPTaxCategory();
				%>

				<%= HtmlUtil.escape(cpTaxCategory.getName(languageId)) %>
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
				name="zip"
			>
				<%= Validator.isBlank(commerceTaxFixedRateAddressRel.getZip()) ? StringPool.STAR : HtmlUtil.escape(commerceTaxFixedRateAddressRel.getZip()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="rate"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/address_tax_fixed_rate_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />deleteCommerceTaxFixedRateAddressRels() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-tax-rate-settings" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceTaxFixedRateAddressRelIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>