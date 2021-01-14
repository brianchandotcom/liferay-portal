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
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

CPSku cpSku = cpContentHelper.getDefaultCPSku(cpCatalogEntry);

long cpDefinitionId = cpCatalogEntry.getCPDefinitionId();

CPContentModel cpContentModel = (CPContentModel)request.getAttribute("cpContentModel");

String hideClass = "hide";
long skuId = 0;

if (cpSku != null) {
	hideClass = StringPool.BLANK;
	skuId = cpSku.getCPInstanceId();
}
%>

<div class="mb-5 product-detail" id="<portlet:namespace /><%= cpDefinitionId %>ProductContent">
	<div class="row">
		<div class="col-12 col-md-6">
			<commerce-ui:gallery CPDefinitionId="<%= cpDefinitionId %>" />
		</div>

		<div class="col-12 col-md-6 d-flex flex-column justify-content-center">
			<header>
				<div class="availability d-flex mb-4">
					<div>
						<commerce-ui:availability-label
							lowStock="<%= cpContentModel.isLowStock() %>"
							stockQuantity="<%= cpContentModel.getStockQuantity() %>"
							willUpdate="<%= true %>"
						/>
					</div>

					<div class="col stock-quantity text-truncate-inline <%= hideClass %>">
						<span class="text-truncate" data-text-cp-instance-stock-quantity>

							<%
							if (cpSku != null) {
							%>

								<%= LanguageUtil.format(request, "x-in-stock", cpContentHelper.getStockQuantity(request)) %>

							<%
							}
							%>

						</span>
					</div>
				</div>

				<p class="my-2 <%= hideClass %>" data-text-cp-instance-sku>
					<span class="font-weight-semi-bold">
						<%= LanguageUtil.get(request, "sku") %>
					</span>
					<span>
						<%= (cpSku == null) ? StringPool.BLANK : HtmlUtil.escape(cpSku.getSku()) %>
					</span>
				</p>

				<h2 class="product-header-title"><%= HtmlUtil.escape(cpCatalogEntry.getName()) %></h2>

				<p class="my-2 <%= hideClass %>" data-text-cp-instance-manufacturer-part-number>
					<span class="font-weight-semi-bold">
						<%= LanguageUtil.get(request, "mpn") %>
					</span>
					<span>
						<%= (cpSku == null) ? StringPool.BLANK : HtmlUtil.escape(cpSku.getManufacturerPartNumber()) %>
					</span>
				</p>

				<p class="my-2 <%= hideClass %>" data-text-cp-instance-gtin>
					<span class="font-weight-semi-bold">
						<%= LanguageUtil.get(request, "gtin") %>
					</span>
					<span>
						<%= (cpSku == null) ? StringPool.BLANK : HtmlUtil.escape(cpSku.getGtin()) %>
					</span>
				</p>
			</header>

			<p class="mt-3 product-description"><%= cpCatalogEntry.getDescription() %></p>

			<h4 class="commerce-subscription-info mt-3 w-100">
				<c:if test="<%= cpSku != null %>">
					<commerce-ui:product-subscription-info
						CPInstanceId="<%= cpSku.getCPInstanceId() %>"
					/>
				</c:if>

				<span data-text-cp-instance-subscription-info></span>
				<span data-text-cp-instance-delivery-subscription-info></span>
			</h4>

			<div class="product-detail-options">
				<%= cpContentHelper.renderOptions(renderRequest, renderResponse) %>

				<%@ include file="/product_detail/render/form_handlers/metal_js.jspf" %>
			</div>

			<c:choose>
				<c:when test="<%= cpSku != null %>">
					<div class="availability-estimate mt-1"><%= cpContentHelper.getAvailabilityEstimateLabel(request) %></div>
				</c:when>
				<c:otherwise>
					<div class="availability-estimate mt-1" data-text-cp-instance-availability-estimate></div>
				</c:otherwise>
			</c:choose>

			<div class="mt-3 price">
				<commerce-ui:price
					CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>"
					CPInstanceId="<%= (cpSku == null) ? 0 : cpSku.getCPInstanceId() %>"
				/>
			</div>

			<c:if test="<%= cpSku != null %>">
				<liferay-commerce:tier-price
					CPInstanceId="<%= cpSku.getCPInstanceId() %>"
					taglibQuantityInputId='<%= liferayPortletResponse.getNamespace() + cpDefinitionId + "Quantity" %>'
				/>
			</c:if>

			<div class="align-items-center d-flex mt-3 product-detail-actions">
				<commerce-ui:add-to-order
					channelId="<%= cpContentModel.getChannelId() %>"
					commerceAccountId="<%= cpContentModel.getAccountId() %>"
					currencyCode="<%= cpContentModel.getCurrencyCode() %>"
					disabled="<%= skuId == 0 %>"
					inCart="<%= cpContentModel.isInCart() %>"
					options='<%= "[]" %>'
					orderId="<%= cpContentModel.getOrderId() %>"
					skuId="<%= skuId %>"
					spritemap="<%= cpContentModel.getSpritemap() %>"
					stockQuantity="<%= cpContentModel.getStockQuantity() %>"
					willUpdate="<%= true %>"
				/>

				<commerce-ui:add-to-wish-list
					commerceAccountId="<%= cpContentModel.getAccountId() %>"
					cpDefinitionId="<%= cpDefinitionId %>"
					inWishList="<%= cpContentModel.isInWishList() %>"
					large="<%= true %>"
					skuId="<%= skuId %>"
					spritemap="<%= cpContentModel.getSpritemap() %>"
				/>
			</div>

			<div class="mt-3">
				<commerce-ui:compare-checkbox
					CPDefinitionId="<%= cpDefinitionId %>"
					label="<%= LanguageUtil.get(resourceBundle, "compare") %>"
				/>
			</div>
		</div>
	</div>
</div>

<%
List<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValues = cpContentHelper.getCPDefinitionSpecificationOptionValues(cpDefinitionId);
List<CPOptionCategory> cpOptionCategories = cpContentHelper.getCPOptionCategories(company.getCompanyId());
List<CPMedia> cpAttachmentFileEntries = cpContentHelper.getCPAttachmentFileEntries(cpDefinitionId, themeDisplay);
%>

<c:if test="<%= cpContentHelper.hasCPDefinitionSpecificationOptionValues(cpDefinitionId) %>">
	<commerce-ui:panel
		elementClasses="flex-column mb-3"
		title='<%= LanguageUtil.get(resourceBundle, "specifications") %>'
	>
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

					<%
					for (CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue : categorizedCPDefinitionSpecificationOptionValues) {
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
					%>

				</c:if>

			<%
			}
			%>

		</dl>
	</commerce-ui:panel>
</c:if>

<c:if test="<%= !cpAttachmentFileEntries.isEmpty() %>">
	<commerce-ui:panel
		elementClasses="mb-3"
		title='<%= LanguageUtil.get(resourceBundle, "attachments") %>'
	>
		<dl class="specification-list">

			<%
			int attachmentsCount = 0;

			for (CPMedia curCPAttachmentFileEntry : cpAttachmentFileEntries) {
			%>

				<dt class="specification-term">
					<%= HtmlUtil.escape(curCPAttachmentFileEntry.getTitle()) %>
				</dt>
				<dd class="specification-desc">
					<aui:icon cssClass="icon-monospaced" image="download" markupView="lexicon" target="_blank" url="<%= curCPAttachmentFileEntry.getDownloadUrl() %>" />
				</dd>

				<%
				attachmentsCount = attachmentsCount + 1;

				if (attachmentsCount >= 2) {
				%>

					<dt class="specification-empty specification-term"></dt>
					<dd class="specification-desc specification-empty"></dd>

			<%
					attachmentsCount = 0;
				}
			}
			%>

		</dl>
	</commerce-ui:panel>
</c:if>