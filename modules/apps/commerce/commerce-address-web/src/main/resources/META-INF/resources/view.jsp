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
CommerceCountriesDisplayContext commerceCountriesDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceCountriesDisplayContext.hasPermission(ActionKeys.MANAGE_COUNTRIES) %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="commerceCountries"
	>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
				portletURL="<%= commerceCountriesDisplayContext.getPortletURL() %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= commerceCountriesDisplayContext.getOrderByCol() %>"
				orderByType="<%= commerceCountriesDisplayContext.getOrderByType() %>"
				orderColumns='<%= new String[] {"name", "priority"} %>'
				portletURL="<%= commerceCountriesDisplayContext.getPortletURL() %>"
			/>

			<li>
				<liferay-commerce:search-input
					actionURL="<%= commerceCountriesDisplayContext.getPortletURL() %>"
					formName="searchFm"
				/>
			</li>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= commerceCountriesDisplayContext.getPortletURL() %>"
				selectedDisplayStyle="list"
			/>

			<portlet:renderURL var="addCommerceCountryURL">
				<portlet:param name="mvcRenderCommandName" value="/commerce_country/edit_commerce_country" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-country") %>'
					url="<%= addCommerceCountryURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceCountries();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="container-fluid container-fluid-max-xl">
		<portlet:actionURL name="/commerce_country/edit_commerce_country" var="editCommerceCountryActionURL" />

		<aui:form action="<%= editCommerceCountryActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCountryIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceCountries"
				searchContainer="<%= commerceCountriesDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Country"
					keyProperty="countryId"
					modelVar="country"
				>
					<liferay-ui:search-container-column-text
						cssClass="font-weight-bold table-cell-expand"
						href='<%=
							PortletURLBuilder.createRenderURL(
								renderResponse
							).setMVCRenderCommandName(
								"/commerce_country/edit_commerce_country"
							).setRedirect(
								currentURL
							).setParameter(
								"countryId", country.getCountryId()
							).buildPortletURL()
						%>'
						name="name"
						value="<%= HtmlUtil.escape(country.getTitle(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="billing-allowed"
					>
						<c:choose>
							<c:when test="<%= country.isBillingAllowed() %>">
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
						cssClass="table-cell-expand"
						name="shipping-allowed"
					>
						<c:choose>
							<c:when test="<%= country.isShippingAllowed() %>">
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
						cssClass="table-cell-expand"
						name="two-letter-iso-code"
						property="a2"
					/>

					<liferay-ui:search-container-column-text
						name="active"
					>
						<c:choose>
							<c:when test="<%= country.isActive() %>">
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
						name="priority"
						property="position"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/commerce_country_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:script>
		function <portlet:namespace />deleteCommerceCountries() {
			if (
				confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-countries" />'
				)
			) {
				var form = window.document['<portlet:namespace />fm'];

				form[
					'<portlet:namespace />deleteCountryIds'
				].value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(form);
			}
		}
	</aui:script>
</c:if>