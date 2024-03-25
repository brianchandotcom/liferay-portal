<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		boolean hasManageLinkSupplierPermission = commerceCatalogDisplayContext.hasManageLinkSupplierPermission(Constants.ADD);
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

					<liferay-frontend:component
						context='<%=
							HashMapBuilder.<String, Object>put(
								"apiUrl", String.valueOf(commerceCatalogDisplayContext.getAccountEntriesAPIURL())
							).put(
								"initialLabel", (defaultAccountEntry == null) ? StringPool.BLANK : defaultAccountEntry.getName()
							).put(
								"initialValue", (defaultAccountEntry == null) ? 0 : defaultAccountEntry.getAccountEntryId()
							).put(
								"inputId", liferayPortletResponse.getNamespace() + "accountEntryId"
							).put(
								"inputName", "accountId"
							).put(
								"itemsKey", "id"
							).put(
								"itemsLabel", "name"
							).build()
						%>'
						module="{addCommerceCatalogAutocomplete} from commerce-catalog-web"
					/>
				</div>
			</div>
		</c:if>
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"editCatalogPortletURL", String.valueOf(editCatalogPortletURL)
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
		module="{addCommerceCatalog} from commerce-catalog-web"
	/>
</commerce-ui:modal-content>