<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

AccountEntry accountEntry = commerceContext.getAccountEntry();
CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);
CPContentSkuOptionsHelper cpContentSkuOptionsHelper = (CPContentSkuOptionsHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_SKU_OPTIONS_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

CPSku cpSku = cpContentHelper.getDefaultCPSku(cpCatalogEntry);

long cpDefinitionId = cpCatalogEntry.getCPDefinitionId();
%>

<div class="mb-5 product-detail" id="<portlet:namespace /><%= cpDefinitionId %>ProductContent">
	<div class="row">
		<div class="col-12 col-md-6">
			<commerce-ui:gallery
				CPDefinitionId="<%= cpDefinitionId %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
			/>
		</div>

		<div class="col-12 col-md-6">
			<header>
				<div class="availability d-flex mb-4">
					<div>
						<commerce-ui:availability-label
							CPCatalogEntry="<%= cpCatalogEntry %>"
							namespace="<%= liferayPortletResponse.getNamespace() %>"
						/>
					</div>

					<div class="ml-3 stock-quantity text-truncate-inline">
						<span class="text-truncate" data-text-cp-instance-stock-quantity>
							<span class="<%= ((cpSku != null) && cpSku.isDiscontinued()) ? StringPool.BLANK : "hide" %>">
								<span class="text-danger">
									<liferay-ui:message key="discontinued" />
								</span>
								-
							</span>

							<%
							String stockQuantity = cpContentHelper.getStockQuantity(request);
							%>

							<span data-qa-id="in-stock-quantity"><%= Validator.isNull(stockQuantity) ? StringPool.BLANK : LanguageUtil.format(request, "x-in-stock", stockQuantity) %></span>
						</span>
					</div>
				</div>

				<%
				boolean hasReplacement = cpContentHelper.hasReplacement(cpSku, request);
				%>

				<c:if test="<%= hasReplacement %>">
					<p class="product-description"><liferay-ui:message key="this-product-is-discontinued.-you-can-see-the-replacement-product-by-clicking-on-the-button-below" /></p>

					<aui:button cssClass="btn-sm my-2" href="<%= cpContentHelper.getReplacementCommerceProductFriendlyURL(cpSku, themeDisplay) %>" primary="<%= true %>" value="replacement-product" />
				</c:if>

				<c:if test="<%= (cpSku != null) && (cpSku.getDiscontinuedDate() != null) %>">

					<%
					Format dateFormat = FastDateFormatFactoryUtil.getDate(locale, timeZone);
					%>

					<p class="my-2">
						<span class="font-weight-semi-bold">
							<liferay-ui:message key="end-of-life" />
						</span>
						<span>
							<%= dateFormat.format(cpSku.getDiscontinuedDate()) %>
						</span>
					</p>
				</c:if>

				<p class="m-0" data-text-cp-instance-incoming-quantity-label>
					<c:if test="<%= cpSku != null %>">
						<%= cpContentHelper.getIncomingQuantityLabel(company.getCompanyId(), locale, cpSku.getSku(), StringPool.BLANK, user) %>
					</c:if>
				</p>

				<%
				String hideCssClass = StringPool.BLANK;

				if (hasReplacement) {
					hideCssClass = "hide";
				}
				%>

				<p class="my-2 <%= hideCssClass %>" data-text-cp-instance-sku>
					<span class="font-weight-semi-bold">
						<liferay-ui:message arguments="<%= StringPool.BLANK %>" key="sku-x" />
					</span>
					<span>
						<%= (cpSku == null) ? StringPool.BLANK : HtmlUtil.escape(cpSku.getSku()) %>
					</span>
				</p>

				<h2 class="product-header-title"><%= HtmlUtil.escape(cpCatalogEntry.getName()) %></h2>

				<p class="my-2 <%= hideCssClass %>" data-text-cp-instance-manufacturer-part-number>
					<span class="font-weight-semi-bold">
						<liferay-ui:message key="mpn-colon" />
					</span>
					<span>
						<%= (cpSku == null) ? StringPool.BLANK : HtmlUtil.escape(cpSku.getManufacturerPartNumber()) %>
					</span>
				</p>

				<p class="my-2 <%= hideCssClass %>" data-text-cp-instance-gtin>
					<span class="font-weight-semi-bold">
						<liferay-ui:message arguments="<%= StringPool.BLANK %>" key="gtin-x" />
					</span>
					<span>
						<%= (cpSku == null) ? StringPool.BLANK : HtmlUtil.escape(cpSku.getGtin()) %>
					</span>
				</p>
			</header>

			<p class="mt-3 product-description"><%= HtmlUtil.escape(cpCatalogEntry.getShortDescription()) %></p>

			<h4 class="commerce-subscription-info mt-3">
				<c:if test="<%= cpSku != null %>">
					<commerce-ui:product-subscription-info
						CPInstanceId="<%= cpSku.getCPInstanceId() %>"
					/>
				</c:if>

				<span data-text-cp-instance-subscription-info></span>
				<span data-text-cp-instance-delivery-subscription-info></span>
			</h4>

			<div class="product-detail-options">
				<commerce-ui:option-selector
					CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
				/>
			</div>

			<c:choose>
				<c:when test="<%= cpSku != null %>">
					<div class="availability-estimate mt-1"><%= HtmlUtil.escape(cpContentHelper.getAvailabilityEstimateLabel(request)) %></div>
				</c:when>
				<c:otherwise>
					<div class="availability-estimate mt-1" data-text-cp-instance-availability-estimate></div>
				</c:otherwise>
			</c:choose>

			<liferay-util:dynamic-include key="com.liferay.commerce.product.type.grouped.web#/grouped_product_type.jsp#" />

			<div class="mt-3 price-container row">
				<div class="col col-lg-9 col-xl-6">
					<commerce-ui:price
						CPCatalogEntry="<%= cpCatalogEntry %>"
						namespace="<%= liferayPortletResponse.getNamespace() %>"
						showDefaultSkuPrice="<%= true %>"
					/>
				</div>
			</div>

			<c:if test="<%= cpSku != null %>">
				<liferay-commerce:tier-price
					CPInstanceId="<%= cpSku.getCPInstanceId() %>"
					taglibQuantityInputId='<%= liferayPortletResponse.getNamespace() + cpDefinitionId + "Quantity" %>'
				/>
			</c:if>

			<%
			BigDecimal minOrderQuantity = cpContentHelper.getMinOrderQuantity(cpDefinitionId);
			%>

			<c:if test="<%= BigDecimalUtil.gt(minOrderQuantity, CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY) %>">
				<span class="min-quantity-per-order">
					<liferay-ui:message arguments="<%= minOrderQuantity.intValue() %>" key="minimum-quantity-per-order-x" />
				</span>
			</c:if>

			<div class="align-items-end d-flex mt-3 product-detail-actions">
				<commerce-ui:add-to-cart
					alignment="left"
					CPCatalogEntry="<%= cpCatalogEntry %>"
					inline="<%= true %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
					showUnitOfMeasureSelector="<%= true %>"
					size="lg"
					skuOptions="<%= cpContentSkuOptionsHelper.getDefaultCPInstanceSkuOptions(cpCatalogEntry.getCPDefinitionId(), request) %>"
				/>

				<commerce-ui:request-quote
					CPCatalogEntry="<%= cpCatalogEntry %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
				/>

				<commerce-ui:add-to-wish-list
					CPCatalogEntry="<%= cpCatalogEntry %>"
					large="<%= true %>"
				/>

				<liferay-util:dynamic-include key="com.liferay.commerce.product.type.virtual.web#/virtual_product_type.jsp#" />
			</div>

			<div class="mt-3">
				<commerce-ui:compare-checkbox
					CPCatalogEntry="<%= cpCatalogEntry %>"
					label='<%= LanguageUtil.get(resourceBundle, "compare") %>'
				/>
			</div>
		</div>
	</div>
</div>

<%
List<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValues = cpContentHelper.getCPDefinitionSpecificationOptionValues(cpDefinitionId);
List<CPMedia> cpMedias = cpContentHelper.getCPMedias(cpDefinitionId, themeDisplay);
List<CPOptionCategory> cpOptionCategories = cpContentHelper.getCPOptionCategories(company.getCompanyId());

String description = cpCatalogEntry.getDescription();

boolean hasCPDefinitionSpecificationOptionValues = cpContentHelper.hasCPDefinitionSpecificationOptionValues(cpDefinitionId);
boolean hasCPMedia = !cpMedias.isEmpty();
boolean hasDescription = !Validator.isBlank(description);
boolean hasDirectReplacement = cpContentHelper.hasDirectReplacement(cpSku);

String navCPMediaId = liferayPortletResponse.getNamespace() + "navCPMedia";
String navDescriptionId = liferayPortletResponse.getNamespace() + "navDescription";
String navReplacementsId = liferayPortletResponse.getNamespace() + "navReplacements";
String navSpecificationsId = liferayPortletResponse.getNamespace() + "navSpecifications";
%>

<div>
	<react:component
		module="product_detail/render/js/Tabs"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"hasCPDefinitionSpecificationOptionValues", hasCPDefinitionSpecificationOptionValues
			).put(
				"hasCPMedia", hasCPMedia
			).put(
				"hasDescription", hasDescription
			).put(
				"hasReplacements", hasDirectReplacement
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"navCPMediaId", navCPMediaId
			).put(
				"navDescriptionId", navDescriptionId
			).put(
				"navReplacementsId", navReplacementsId
			).put(
				"navSpecificationsId", navSpecificationsId
			).build()
		%>'
	/>
</div>

<div class="tab-content">
	<c:if test="<%= hasDescription %>">
		<div aria-labelledby="navUnderlineFieldsTab" class="fade <portlet:namespace />tab-element tab-pane" id="<%= navDescriptionId %>" role="tabpanel">
			<div class="p-4">
				<%= description %>
			</div>
		</div>
	</c:if>

	<c:if test="<%= hasCPDefinitionSpecificationOptionValues %>">
		<div aria-labelledby="navUnderlineFieldsTab" class="fade <portlet:namespace />tab-element tab-pane" id="<%= navSpecificationsId %>" role="tabpanel">
			<dl class="specification-list">

				<%
				for (CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue : cpDefinitionSpecificationOptionValues) {
					CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();
				%>

					<dt class="specification-term">
						<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>
					</dt>
					<dd class="specification-desc">
						<%= HtmlUtil.escape(cpDefinitionSpecificationOptionValue.getValue(languageId)) %>
					</dd>

				<%
				}

				for (CPOptionCategory cpOptionCategory : cpOptionCategories) {
					List<CPDefinitionSpecificationOptionValue> categorizedCPDefinitionSpecificationOptionValues = cpContentHelper.getCategorizedCPDefinitionSpecificationOptionValues(cpDefinitionId, cpOptionCategory.getCPOptionCategoryId());
				%>

					<c:if test="<%= !categorizedCPDefinitionSpecificationOptionValues.isEmpty() %>">
						<table class="table table-sm table-striped">
							<thead>
								<tr>
									<th class="table-cell-expand"><%= HtmlUtil.escape(cpOptionCategory.getTitle(locale)) %></th>
									<th class="table-column-text-end"></th>
								</tr>
							</thead>

							<tbody>

								<%
								for (CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue : categorizedCPDefinitionSpecificationOptionValues) {
									CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();
								%>

									<tr>
										<td class="specification-term table-cell-minw-150 table-title">
											<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>
										</td>
										<td class="specification-desc table-cell-expand">
											<%= HtmlUtil.escape(cpDefinitionSpecificationOptionValue.getValue(languageId)) %>
										</td>
									</tr>

								<%
								}
								%>

							</tbody>
						</table>
					</c:if>

				<%
				}
				%>

			</dl>
		</div>
	</c:if>

	<c:if test="<%= hasCPMedia %>">
		<div aria-labelledby="navUnderlineFieldsTab" class="fade <portlet:namespace />tab-element tab-pane" id="<%= navCPMediaId %>" role="tabpanel">
			<ul class="list-group">

				<%
				for (CPMedia cpMedia : cpMedias) {
				%>

					<li class="list-group-item list-group-item-flex">
						<div class="autofit-col my-auto">
							<aui:icon cssClass="icon-monospaced" image="document-default" markupView="lexicon" />
						</div>

						<div class="autofit-col autofit-col-expand">
							<h5><%= HtmlUtil.escape(cpMedia.getTitle()) %></h5>

							<p class="m-0"><%= LanguageUtil.formatStorageSize(cpMedia.getSize(), locale) %></p>
						</div>

						<div class="autofit-col my-auto">
							<clay:link
								borderless="<%= false %>"
								cssClass="btn-secondary"
								href="<%= cpMedia.getDownloadURL() %>"
								label='<%= LanguageUtil.get(request, "download") %>'
								target="_blank"
								type="button"
							/>
						</div>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<c:if test="<%= hasDirectReplacement %>">
		<div aria-labelledby="navUnderlineReplacementsTab" class="fade <portlet:namespace />tab-element tab-pane" id="<%= navReplacementsId %>" role="tabpanel" style="display: block; height: 0px; visibility: hidden;">
			<frontend-data-set:classic-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commerceAccountId", (accountEntry == null) ? "0" : String.valueOf(accountEntry.getAccountEntryId())
					).put(
						"commerceChannelGroupId", String.valueOf(commerceContext.getCommerceChannelGroupId())
					).put(
						"commerceOrderId", (commerceOrder == null) ? "0" : String.valueOf(commerceOrder.getCommerceOrderId())
					).put(
						"cpInstanceUuid", cpSku.getCPInstanceUuid()
					).put(
						"cProductId", String.valueOf(cpCatalogEntry.getCProductId())
					).build()
				%>'
				dataProviderKey="<%= CPContentFDSNames.REPLACEMENT_CP_INSTANCES %>"
				id="<%= CPContentFDSNames.REPLACEMENT_CP_INSTANCES %>"
				itemsPerPage="<%= 10 %>"
				style="stacked"
			/>
		</div>
	</c:if>
</div>