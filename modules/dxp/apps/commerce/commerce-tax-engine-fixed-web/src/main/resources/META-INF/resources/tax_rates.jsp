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
CommerceTaxFixedRatesDisplayContext commerceTaxFixedRatesDisplayContext = (CommerceTaxFixedRatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceTaxFixedRate> commerceTaxFixedRateSearchContainer = commerceTaxFixedRatesDisplayContext.getSearchContainer();

boolean hasManageCommerceTaxMethodsPermission = CommercePermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceTaxFixedRates"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceTaxFixedRatesDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceTaxFixedRatesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceTaxFixedRatesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= commerceTaxFixedRatesDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceTaxFixedRatesDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCommerceTaxMethodsPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceTaxFixedRates();" %>' icon="times" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<portlet:actionURL name="editCommerceTaxFixedRate" var="editCommerceTaxFixedRateActionURL" />

<aui:form action="<%= editCommerceTaxFixedRateActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCommerceTaxFixedRateIds" type="hidden" />

	<liferay-ui:error exception="<%= CommerceTaxFixedRateCommerceTaxCategoryIdException.class %>" message="please-select-a-valid-tax-category" />
	<liferay-ui:error exception="<%= NoSuchTaxCategoryException.class %>" message="the-tax-category-could-not-be-found" />
	<liferay-ui:error exception="<%= NoSuchTaxFixedRateException.class %>" message="the-tax-fixed-rate-could-not-be-found" />

	<liferay-ui:search-container
		id="commerceTaxFixedRates"
		searchContainer="<%= commerceTaxFixedRateSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate"
			keyProperty="commerceTaxFixedRateId"
			modelVar="commerceTaxFixedRate"
		>

			<%
			CommerceTaxCategory commerceTaxCategory = commerceTaxFixedRate.getCommerceTaxCategory();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>
				<%= HtmlUtil.escape(commerceTaxCategory.getName(languageId)) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="rate"
			/>

			<liferay-ui:search-container-column-date
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/tax_rate_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= hasManageCommerceTaxMethodsPermission %>">
	<liferay-portlet:renderURL var="addCommerceTaxFixedRateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="editCommerceTaxFixedRate" />
		<portlet:param name="commerceTaxMethodId" value="<%= String.valueOf(commerceTaxFixedRatesDisplayContext.getCommerceTaxMethodId()) %>" />
	</liferay-portlet:renderURL>

	<%
	String url = commerceTaxFixedRatesDisplayContext.getAddTaxRateURL("editCommerceTaxFixedRate", true, addCommerceTaxFixedRateURL);
	%>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(resourceBundle, "add-tax-rate") %>' url="<%= url %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace/>editCommerceTaxFixedRate',
		function(isNew, uri) {
			var title = '<liferay-ui:message key="edit-tax-rate" />';

			if (isNew) {
				title = '<liferay-ui:message key="add-tax-rate" />';
			}

			Liferay.Util.openWindow(
				{
					dialog: {
						centered: true,
						destroyOnClose: true,
						height: 400,
						modal: true,
						width: 500
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer'
					},
					id: 'editTaxFixedRateDialog',
					title: title,
					uri: uri
				}
			);
		}
	);

	Liferay.provide(
		window,
		'refreshPortlet',
		function() {
			var curPortlet = '#p_p_id<portlet:namespace/>';

			Liferay.Portlet.refresh(curPortlet);
		},
		['aui-dialog','aui-dialog-iframe']
	);

	Liferay.provide(
		window,
		'closePopup',
		function(dialogId) {
			var dialog = Liferay.Util.Window.getById(dialogId);

			dialog.destroy();
		},
		['liferay-util-window']
	);

	function <portlet:namespace />deleteCommerceTaxFixedRates() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-tax-rates" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceTaxFixedRateIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>