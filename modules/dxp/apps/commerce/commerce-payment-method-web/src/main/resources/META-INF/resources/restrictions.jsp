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
ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommercePaymentMethodRestrictionsDisplayContext commercePaymentMethodRestrictionsDisplayContext = (CommercePaymentMethodRestrictionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePaymentMethod commercePaymentMethod = commercePaymentMethodRestrictionsDisplayContext.getCommercePaymentMethod();

SearchContainer<CommerceAddressRestriction> commerceAddressRestrictionSearchContainer = commercePaymentMethodRestrictionsDisplayContext.getSearchContainer();

boolean hasManageCommercePaymentMethodsPermission = CommercePermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_PAYMENT_METHODS);

String title = commercePaymentMethod.getName(locale) + StringPool.SPACE + LanguageUtil.get(request, "restrictions");

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<div class="container-fluid-1280">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="commerceAddressRestrictions"
	>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= commercePaymentMethodRestrictionsDisplayContext.getPortletURL() %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= commercePaymentMethodRestrictionsDisplayContext.getPortletURL() %>"
				selectedDisplayStyle="list"
			/>

			<c:if test="<%= hasManageCommercePaymentMethodsPermission %>">
				<portlet:actionURL name="editCommercePaymentMethodAddressRestriction" var="addCommercePaymentMethodAddressRestrictionURL" />

				<aui:form action="<%= addCommercePaymentMethodAddressRestrictionURL %>" cssClass="hide" name="addCommerceAddressRestrictionFm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="className" type="hidden" value="<%= CommercePaymentMethod.class.getName() %>" />
					<aui:input name="classPK" type="hidden" value="<%= commercePaymentMethodRestrictionsDisplayContext.getCommercePaymentMethodId() %>" />
					<aui:input name="commerceCountryIds" type="hidden" value="" />
				</aui:form>

				<liferay-frontend:add-menu
					inline="<%= true %>"
				>
					<liferay-frontend:add-menu-item
						id="addCommerceAddressRestriction"
						title='<%= LanguageUtil.get(request, "add-restriction") %>'
						url="javascript:;"
					/>
				</liferay-frontend:add-menu>
			</c:if>
		</liferay-frontend:management-bar-buttons>

		<c:if test="<%= hasManageCommercePaymentMethodsPermission %>">
			<liferay-frontend:management-bar-action-buttons>
				<liferay-frontend:management-bar-button
					href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceAddressRestrictions();" %>'
					icon="times"
					label="delete"
				/>
			</liferay-frontend:management-bar-action-buttons>
		</c:if>
	</liferay-frontend:management-bar>

	<portlet:actionURL name="editCommercePaymentMethodAddressRestriction" var="editCommercePaymentMethodAddressRestrictionActionURL" />

	<aui:form action="<%= editCommercePaymentMethodAddressRestrictionActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceAddressRestrictionIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceAddressRestrictions"
			searchContainer="<%= commerceAddressRestrictionSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceAddressRestriction"
				keyProperty="commerceAddressRestrictionId"
				modelVar="commerceAddressRestriction"
			>

				<%
				CommerceCountry commerceCountry = commerceAddressRestriction.getCommerceCountry();
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="name"
				>
					<%= HtmlUtil.escape(commerceCountry.getName(locale)) %>
				</liferay-ui:search-container-column-text>

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
				>
					<%= HtmlUtil.escape(commerceCountry.getTwoLettersISOCode()) %>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/restriction_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceAddressRestrictions() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-restrictions" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceAddressRestrictionIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCommerceAddressRestriction').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'countriesSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />commerceCountryIds').val(selectedItems);

								var addCommerceAddressRestrictionFm = $('#<portlet:namespace />addCommerceAddressRestrictionFm');

								submitForm(addCommerceAddressRestrictionFm);
							}
						}
					},
					title: '<liferay-ui:message key="add-restrictions" />',
					url: '<%= commercePaymentMethodRestrictionsDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>