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
CommerceCountriesDisplayContext commerceCountriesDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceCountry> commerceCountrySearchContainer = commerceCountriesDisplayContext.getSearchContainer();

boolean hasManageCommerceCountriesPermission = CommercePermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);
%>

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
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= commerceCountriesDisplayContext.getPortletURL() %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(commerceCountriesDisplayContext.getPortletURL()) %>" name="searchFm">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceCountriesDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= hasManageCommerceCountriesPermission %>">
			<portlet:renderURL var="addCommerceCountryURL">
				<portlet:param name="mvcRenderCommandName" value="editCommerceCountry" />
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
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCommerceCountriesPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceCountries();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCommerceCountry" var="editCommerceCountryActionURL" />

	<aui:form action="<%= editCommerceCountryActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceCountryIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceCountries"
			searchContainer="<%= commerceCountrySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceCountry"
				keyProperty="commerceCountryId"
				modelVar="commerceCountry"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "editCommerceCountry");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("commerceCountryId", String.valueOf(commerceCountry.getCommerceCountryId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="important table-cell-content"
					href="<%= rowURL %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="billing-allowed"
					value='<%= LanguageUtil.get(request, commerceCountry.isBillingAllowed() ? "yes" : "no") %>'
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="shipping-allowed"
					value='<%= LanguageUtil.get(request, commerceCountry.isShippingAllowed() ? "yes" : "no") %>'
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="two-letters-iso-code"
					property="twoLettersISOCode"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="active"
					value='<%= LanguageUtil.get(request, commerceCountry.isActive() ? "yes" : "no") %>'
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="priority"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/country_action.jsp"
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
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-countries" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceCountryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>