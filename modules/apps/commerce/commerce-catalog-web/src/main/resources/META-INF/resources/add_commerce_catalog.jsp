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
CommerceCatalogDisplayContext commerceCatalogDisplayContext = (CommerceCatalogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL editCatalogPortletURL = commerceCatalogDisplayContext.getEditCommerceCatalogRenderURL();
CommerceCatalog commerceCatalog = commerceCatalogDisplayContext.getCommerceCatalog();
List<CommerceCurrency> commerceCurrencies = commerceCatalogDisplayContext.getCommerceCurrencies();
%>

<portlet:actionURL name="/commerce_catalogs/edit_commerce_catalog" var="editCommerceCatalogActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-catalog") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<aui:input bean="<%= commerceCatalog %>" model="<%= CommerceCatalog.class %>" name="name" required="<%= true %>" />

		<%
		boolean hasManageLinkSupplierPermission = FeatureFlagManagerUtil.isEnabled("COMMERCE-10890") && commerceCatalogDisplayContext.hasManageLinkSupplierPermission(Constants.ADD);
		%>

		<div class="row">
			<div class="col-<%= hasManageLinkSupplierPermission ? "6" : "12" %>">
				<aui:select helpMessage="the-default-language-for-the-content-within-this-catalog" label="default-catalog-language" name="defaultLanguageId" required="<%= true %>" title="language">

					<%
					String catalogDefaultLanguageId = themeDisplay.getLanguageId();

					if (commerceCatalog != null) {
						catalogDefaultLanguageId = commerceCatalog.getCatalogDefaultLanguageId();
					}

					Set<Locale> siteAvailableLocales = LanguageUtil.getAvailableLocales(themeDisplay.getScopeGroupId());

					for (Locale siteAvailableLocale : siteAvailableLocales) {
					%>

						<aui:option label="<%= siteAvailableLocale.getDisplayName(locale) %>" lang="<%= LocaleUtil.toW3cLanguageId(siteAvailableLocale) %>" selected="<%= catalogDefaultLanguageId.equals(LanguageUtil.getLanguageId(siteAvailableLocale)) %>" value="<%= LocaleUtil.toLanguageId(siteAvailableLocale) %>" />

					<%
					}
					%>

				</aui:select>
			</div>

			<div class="col-<%= hasManageLinkSupplierPermission ? "6" : "12" %>">
				<aui:select label="currency" name="currencyCode" required="<%= true %>" title="currency">

					<%
					for (CommerceCurrency commerceCurrency : commerceCurrencies) {
						String commerceCurrencyCode = commerceCurrency.getCode();
					%>

						<aui:option label="<%= commerceCurrency.getName(locale) %>" selected="<%= (commerceCatalog == null) ? commerceCurrency.isPrimary() : commerceCurrencyCode.equals(commerceCatalog.getCommerceCurrencyCode()) %>" value="<%= commerceCurrencyCode %>" />

					<%
					}
					%>

				</aui:select>
			</div>
		</div>

		<c:if test="<%= hasManageLinkSupplierPermission %>">
			<div class="row">
				<div class="col-12">
					<label class="control-label" for="accountEntryId">
						<liferay-ui:message key="link-catalog-to-a-supplier" />

						<span class="reference-mark">
							<clay:icon
								symbol="asterisk"
							/>

							<span class="hide-accessible sr-only">
								<liferay-ui:message key="required" />
							</span>
						</span>
					</label>

					<div class="mb-4" id="link-account-entry-autocomplete-root"></div>

					<%
					AccountEntry defaultAccountEntry = commerceCatalogDisplayContext.getDefaultAccountEntry();
					%>

					<aui:script require="commerce-frontend-js/components/autocomplete/entry as autocomplete">
						autocomplete.default('autocomplete', 'link-account-entry-autocomplete-root', {
							apiUrl: '<%= commerceCatalogDisplayContext.getAccountEntriesAPIURL() %>',
							initialLabel:
								'<%= (defaultAccountEntry == null) ? StringPool.BLANK : HtmlUtil.escapeJS(defaultAccountEntry.getName()) %>',
							initialValue:
								'<%= (defaultAccountEntry == null) ? 0 : defaultAccountEntry.getAccountEntryId() %>',
							inputId: '<%= liferayPortletResponse.getNamespace() %>accountEntryId',
							inputName: 'accountId',
							itemsKey: 'id',
							itemsLabel: 'name',
							required: true,
						});
					</aui:script>
				</div>
			</div>
		</c:if>
	</aui:form>

	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils">
		Liferay.provide(
			window,
			'<portlet:namespace />apiSubmit',
			(form) => {
				var API_URL = '/o/headless-commerce-admin-catalog/v1.0/catalogs';

				window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
					isLoading: true,
				});

				FormUtils.apiSubmit(form, API_URL)
					.then((payload) => {
						var redirectURL = new Liferay.PortletURL.createURL(
							'<%= editCatalogPortletURL.toString() %>'
						);

						redirectURL.setParameter('commerceCatalogId', payload.id);
						redirectURL.setParameter('p_auth', Liferay.authToken);

						window.parent.Liferay.fire(events.CLOSE_MODAL, {
							redirectURL: redirectURL.toString(),
							successNotification: {
								showSuccessNotification: true,
								message:
									'<liferay-ui:message key="your-request-completed-successfully" />',
							},
						});
					})
					.catch((error) => {
						window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
							isLoading: false,
						});

						window.parent.Liferay.Util.openToast({
							message:
								error.title ||
								'<liferay-ui:message key="an-unexpected-error-occurred" />',
							title: '<liferay-ui:message key="danger" />',
							type: 'danger',
						});
					});
			},
			['liferay-portlet-url']
		);
	</aui:script>
</commerce-ui:modal-content>