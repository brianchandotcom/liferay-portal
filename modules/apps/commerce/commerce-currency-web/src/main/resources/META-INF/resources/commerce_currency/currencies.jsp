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
%>

<c:if test="<%= commerceCurrenciesDisplayContext.hasManageCommerceCurrencyPermission() %>">
	<clay:management-toolbar
		managementToolbarDisplayContext="<%= new CommerceCurrenciesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, commerceCurrenciesDisplayContext.getSearchContainer()) %>"
		propsTransformer="js/CommerceCurrenciesManagementToolbarPropsTransformer"
	/>

	<portlet:actionURL name="/commerce_currency/edit_commerce_currency" var="editCommerceCurrencyActionURL" />

	<aui:form action="<%= editCommerceCurrencyActionURL %>" cssClass="container" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			id="commerceCurrencies"
			searchContainer="<%= commerceCurrenciesDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.currency.model.CommerceCurrency"
				keyProperty="commerceCurrencyId"
				modelVar="commerceCurrency"
			>
				<liferay-ui:search-container-column-text
					cssClass="font-weight-bold important table-cell-expand"
					href='<%=
						PortletURLBuilder.createRenderURL(
							renderResponse
						).setMVCRenderCommandName(
							"/commerce_currency/edit_commerce_currency"
						).setRedirect(
							currentURL
						).setParameter(
							"commerceCurrencyId", commerceCurrency.getCommerceCurrencyId()
						).buildPortletURL()
					%>'
					name="name"
					value="<%= HtmlUtil.escape(commerceCurrency.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="code"
					value="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="exchange-rate"
					value="<%= commerceCurrenciesDisplayContext.format(commerceCurrency.getRate()) %>"
				/>

				<liferay-ui:search-container-column-text
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
					property="priority"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/currency_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</c:if>