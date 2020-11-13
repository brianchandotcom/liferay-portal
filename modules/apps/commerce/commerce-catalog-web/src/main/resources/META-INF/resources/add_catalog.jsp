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

<portlet:actionURL name="editCommerceCatalog" var="editCommerceCatalogActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-catalog") %>'
>
	<div class="col-12 lfr-form-content">
		<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
			<aui:input bean="<%= commerceCatalog %>" model="<%= CommerceCatalog.class %>" name="name" required="<%= true %>" />

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
		</aui:form>

		<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils">
			Liferay.provide(
				window,
				'<portlet:namespace />apiSubmit',
				function (form) {
					var API_URL = '/o/headless-commerce-admin-catalog/v1.0/catalogs';

					window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
						isLoading: true,
					});

					FormUtils.apiSubmit(form, API_URL)
						.then(function (payload) {
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
						.catch(function () {
							window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
								isLoading: false,
							});

							new Liferay.Notification({
								closeable: true,
								delay: {
									hide: 5000,
									show: 0,
								},
								duration: 500,
								message:
									'<liferay-ui:message key="an-unexpected-error-occurred" />',
								render: true,
								title: '<liferay-ui:message key="danger" />',
								type: 'danger',
							});
						});
				},
				['liferay-portlet-url']
			);
		</aui:script>
	</div>
</commerce-ui:modal-content>