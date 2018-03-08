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
CommerceCurrenciesDisplayContext commerceCurrenciesDisplayContext = (CommerceCurrenciesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceCurrency> commerceCurrencySearchContainer = commerceCurrenciesDisplayContext.getSearchContainer();

boolean hasManageCommerceCurrenciesPermission = CommerceCurrencyPermission.contains(permissionChecker, scopeGroupId, CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceCurrencies"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
			portletURL="<%= commerceCurrenciesDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceCurrenciesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceCurrenciesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= commerceCurrenciesDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceCurrenciesDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCommerceCurrenciesPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button cssClass="btn-update-exchange-rates" href='<%= "javascript:" + renderResponse.getNamespace() + "updateExchangeRates();" %>' label="update-exchange-rates" />

			<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceCurrencies();" %>' icon="times" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCommerceCurrency" var="editCommerceCurrencyActionURL" />

	<aui:form action="<%= editCommerceCurrencyActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceCurrencyIds" type="hidden" />
		<aui:input name="updateCommerceCurrencyExchangeRateIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceCurrencies"
			searchContainer="<%= commerceCurrencySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.currency.model.CommerceCurrency"
				keyProperty="commerceCurrencyId"
				modelVar="commerceCurrency"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "editCommerceCurrency");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("commerceCurrencyId", String.valueOf(commerceCurrency.getCommerceCurrencyId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="code"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="exchange-rate"
					property="rate"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="primary"
				>
					<c:if test="<%= commerceCurrency.isPrimary() %>">
						<liferay-ui:icon
							cssClass="commerce-admin-icon-check"
							icon="check"
							markupView="lexicon"
						/>
					</c:if>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="active"
				>
					<c:choose>
						<c:when test="<%= commerceCurrency.isActive() %>">
							<liferay-ui:icon
								cssClass="commerce-admin-icon-check"
								icon="check"
								markupView="lexicon"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon
								cssClass="commerce-admin-icon-times"
								icon="times"
								markupView="lexicon"
							/>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="priority"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/currency_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= hasManageCommerceCurrenciesPermission %>">
	<portlet:renderURL var="addCommerceCurrencyURL">
		<portlet:param name="mvcRenderCommandName" value="editCommerceCurrency" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-currency") %>' url="<%= addCommerceCurrencyURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script>
	function <portlet:namespace />deleteCommerceCurrencies() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-currencies" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceCurrencyIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}

	function <portlet:namespace />updateExchangeRates() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-update-the-exchange-rate-of-the-selected-currencies" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('<%= Constants.CMD %>').val('updateExchangeRates');
			form.fm('updateCommerceCurrencyExchangeRateIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>