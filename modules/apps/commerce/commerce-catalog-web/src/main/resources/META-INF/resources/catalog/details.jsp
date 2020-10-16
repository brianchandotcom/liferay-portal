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

CommerceCatalog commerceCatalog = commerceCatalogDisplayContext.getCommerceCatalog();
List<CommerceCurrency> commerceCurrencies = commerceCatalogDisplayContext.getCommerceCurrencies();
FileEntry fileEntry = commerceCatalogDisplayContext.getDefaultFileEntry();

CommercePriceList baseCommercePriceList = commerceCatalogDisplayContext.getBaseCommercePriceList(CommercePriceListConstants.TYPE_PRICE_LIST);
CommercePriceList basePromotionCommercePriceList = commerceCatalogDisplayContext.getBaseCommercePriceList(CommercePriceListConstants.TYPE_PROMOTION);

long fileEntryId = BeanParamUtil.getLong(fileEntry, request, "fileEntryId");

boolean isViewOnly = !commerceCatalogDisplayContext.hasPermission(commerceCatalog.getCommerceCatalogId(), ActionKeys.UPDATE);
%>

<portlet:actionURL name="/commerce_catalogs/edit_commerce_catalog" var="editCommerceCatalogActionURL" />

<aui:form action="<%= editCommerceCatalogActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceCatalog == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
	<aui:input name="baseCommercePriceListId" type="hidden" value="<%= commerceCatalogDisplayContext.getBaseCommercePriceListId(CommercePriceListConstants.TYPE_PRICE_LIST) %>" />
	<aui:input name="basePromotionCommercePriceListId" type="hidden" value="<%= commerceCatalogDisplayContext.getBaseCommercePriceListId(CommercePriceListConstants.TYPE_PROMOTION) %>" />
	<aui:input name="commerceCatalogId" type="hidden" value="<%= (commerceCatalog == null) ? 0 : commerceCatalog.getCommerceCatalogId() %>" />

	<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="please-select-an-existing-file" />
	<liferay-ui:error exception="<%= NoSuchPriceListException.class %>" message="please-select-an-existing-price-list-or-promotion" />

	<div class="row">
		<div class="col-8">
			<commerce-ui:panel
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<div class="col-12 lfr-form-content">
					<aui:input bean="<%= commerceCatalog %>" disabled="<%= isViewOnly %>" model="<%= CommerceCatalog.class %>" name="name" required="<%= true %>" />

					<aui:select disabled="<%= isViewOnly %>" helpMessage="the-default-language-for-the-content-within-this-catalog" label="default-catalog-language" name="catalogDefaultLanguageId" required="<%= true %>" title="language">

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

					<aui:select disabled="<%= isViewOnly %>" label="currency" name="commerceCurrencyCode" required="<%= true %>" title="currency">

						<%
						for (CommerceCurrency commerceCurrency : commerceCurrencies) {
							String commerceCurrencyCode = commerceCurrency.getCode();
						%>

							<aui:option label="<%= HtmlUtil.escape(commerceCurrency.getName(locale)) %>" selected="<%= (commerceCatalog == null) ? commerceCurrency.isPrimary() : commerceCurrencyCode.equals(commerceCatalog.getCommerceCurrencyCode()) %>" value="<%= HtmlUtil.escape(commerceCurrencyCode) %>" />

						<%
						}
						%>

					</aui:select>

					<c:if test="<%= commerceCatalogDisplayContext.showBasePriceListInputs() %>">
						<label class="control-label" for="baseCommercePriceListId"><%= LanguageUtil.get(request, "base-price-list") %></label>

						<div class="mb-4" id="base-price-list-autocomplete-root"></div>

						<aui:script require="commerce-frontend-js/components/autocomplete/entry as autocomplete, commerce-frontend-js/utilities/eventsDefinitions as events">
							autocomplete.default('autocomplete', 'base-price-list-autocomplete-root', {
								apiUrl:
									'<%= commerceCatalogDisplayContext.getPriceListsApiUrl(CommercePriceListConstants.TYPE_PRICE_LIST) %>',
								initialLabel:
									'<%= (baseCommercePriceList == null) ? StringPool.BLANK : HtmlUtil.escapeJS(baseCommercePriceList.getName()) %>',
								initialValue:
									'<%= (baseCommercePriceList == null) ? 0 : baseCommercePriceList.getCommercePriceListId() %>',
								inputId: 'baseCommercePriceListId',
								inputName:
									'<%= liferayPortletResponse.getNamespace() %>baseCommercePriceListId',
								itemsKey: 'id',
								itemsLabel: 'name',
								onValueUpdated: function (value, priceListData) {
									if (value) {
										window.document.querySelector(
											'#<portlet:namespace />baseCommercePriceListId'
										).value = priceListData.id;
									}
									else {
										window.document.querySelector(
											'#<portlet:namespace />baseCommercePriceListId'
										).value = 0;
									}
								},
								required: true,
							});
						</aui:script>

						<label class="control-label" for="basePromotionCommercePriceListId"><%= LanguageUtil.get(request, "base-promotion") %></label>

						<div class="mb-4" id="base-promotion-autocomplete-root"></div>

						<aui:script require="commerce-frontend-js/components/autocomplete/entry as autocomplete, commerce-frontend-js/utilities/eventsDefinitions as events">
							autocomplete.default('autocomplete', 'base-promotion-autocomplete-root', {
								apiUrl:
									'<%= commerceCatalogDisplayContext.getPriceListsApiUrl(CommercePriceListConstants.TYPE_PROMOTION) %>',
								initialLabel:
									'<%= (basePromotionCommercePriceList == null) ? StringPool.BLANK : HtmlUtil.escapeJS(basePromotionCommercePriceList.getName()) %>',
								initialValue:
									'<%= (basePromotionCommercePriceList == null) ? 0 : basePromotionCommercePriceList.getCommercePriceListId() %>',
								inputId: 'basePromotionCommercePriceListId',
								inputName:
									'<%= liferayPortletResponse.getNamespace() %>basePromotionCommercePriceListId',
								itemsKey: 'id',
								itemsLabel: 'name',
								onValueUpdated: function (value, priceListData) {
									if (value) {
										window.document.querySelector(
											'#<portlet:namespace />basePromotionCommercePriceListId'
										).value = priceListData.id;
									}
									else {
										window.document.querySelector(
											'#<portlet:namespace />basePromotionCommercePriceListId'
										).value = 0;
									}
								},
								required: true,
							});
						</aui:script>
					</c:if>
				</div>
			</commerce-ui:panel>
		</div>

		<div class="col-4">
			<commerce-ui:panel
				elementClasses="flex-fill h-100"
				title='<%= LanguageUtil.get(request, "default-catalog-image") %>'
			>
				<div class="row">
					<div class="col-12 h-100">
						<aui:model-context bean="<%= fileEntry %>" model="<%= FileEntry.class %>" />

						<div class="lfr-attachment-cover-image-selector">
							<portlet:actionURL name="/commerce_catalogs/upload_commerce_media_default_image" var="uploadCommerceMediaDefaultImageActionURL" />

							<liferay-item-selector:image-selector
								draggableImage="vertical"
								fileEntryId="<%= fileEntryId %>"
								itemSelectorEventName="addFileEntry"
								itemSelectorURL="<%= commerceCatalogDisplayContext.getImageItemSelectorUrl() %>"
								maxFileSize="<%= commerceCatalogDisplayContext.getImageMaxSize() %>"
								paramName="fileEntry"
								uploadURL="<%= uploadCommerceMediaDefaultImageActionURL %>"
								validExtensions="<%= StringUtil.merge(commerceCatalogDisplayContext.getImageExtensions(), StringPool.COMMA_AND_SPACE) %>"
							/>
						</div>
					</div>
				</div>
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>