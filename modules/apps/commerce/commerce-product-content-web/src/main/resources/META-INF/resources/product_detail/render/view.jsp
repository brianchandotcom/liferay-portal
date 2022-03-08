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
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

CommerceAccount commerceAccount = commerceContext.getCommerceAccount();
CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

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

		<div class="col-12 col-md-6 d-flex flex-column justify-content-center">
			<header>
				<div class="availability d-flex mb-4">
					<div>
						<commerce-ui:availability-label
							CPCatalogEntry="<%= cpCatalogEntry %>"
							namespace="<%= liferayPortletResponse.getNamespace() %>"
						/>
					</div>

					<div class="col stock-quantity text-truncate-inline">
						<span class="text-truncate" data-text-cp-instance-stock-quantity>
							<span class="<%= ((cpSku != null) && cpSku.isDiscontinued()) ? StringPool.BLANK : "hide" %>">
								<span class="text-danger">
									<%= LanguageUtil.get(request, "discontinued") %>
								</span>
								-
							</span>
							<span data-qa-id="in-stock-quantity"><%= LanguageUtil.format(request, "x-in-stock", cpContentHelper.getStockQuantity(request)) %></span>
						</span>
					</div>
				</div>

				<%
				boolean hasReplacement = cpContentHelper.hasReplacement(cpSku, request);
				%>

				<c:if test="<%= hasReplacement %>">
					<p class="product-description"><%= LanguageUtil.get(request, "this-product-is-discontinued.-you-can-see-the-replacement-product-by-clicking-on-the-button-below") %></p>

					<aui:button cssClass="btn btn-primary btn-sm my-2" href="<%= cpContentHelper.getReplacementCommerceProductFriendlyURL(cpSku, themeDisplay) %>" value="replacement-product" />
				</c:if>

				<%
				Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("MMMMM dd, yyyy", locale, timeZone);
				Date discontinuedDate = cpSku.getDiscontinuedDate();
				%>

				<c:if test="<%= discontinuedDate != null %>">
					<p class="my-2">
						<span class="font-weight-semi-bold">
							<%= LanguageUtil.get(request, "end-of-life") %>
						</span>
						<span>
							<%= dateFormat.format(discontinuedDate) %>
						</span>
					</p>
				</c:if>

				<%
				String hideCssClass = StringPool.BLANK;

				if (hasReplacement) {
					hideCssClass = "hide";
				}
				%>

				<p class="my-2 <%= hideCssClass %>" data-text-cp-instance-sku>
					<span class="font-weight-semi-bold">
						<%= LanguageUtil.get(request, "sku") %>
					</span>
					<span>
						<%= (cpSku == null) ? StringPool.BLANK : HtmlUtil.escape(cpSku.getSku()) %>
					</span>
				</p>

				<h2 class="product-header-title"><%= HtmlUtil.escape(cpCatalogEntry.getName()) %></h2>

				<p class="my-2 <%= hideCssClass %>" data-text-cp-instance-manufacturer-part-number>
					<span class="font-weight-semi-bold">
						<%= LanguageUtil.get(request, "mpn") %>
					</span>
					<span>
						<%= (cpSku == null) ? StringPool.BLANK : HtmlUtil.escape(cpSku.getManufacturerPartNumber()) %>
					</span>
				</p>

				<p class="my-2 <%= hideCssClass %>" data-text-cp-instance-gtin>
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
				<form data-senna-off="true" name="fm">
					<%= cpContentHelper.renderOptions(renderRequest, renderResponse) %>
				</form>

				<liferay-portlet:actionURL name="/cp_content_web/check_cp_instance" portletName="com_liferay_commerce_product_content_web_internal_portlet_CPContentPortlet" var="checkCPInstanceURL">
					<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				</liferay-portlet:actionURL>

				<liferay-frontend:component
					context='<%=
						HashMapBuilder.<String, Object>put(
							"actionURL", checkCPInstanceURL
						).put(
							"cpDefinitionId", cpDefinitionId
						).put(
							"namespace", liferayPortletResponse.getNamespace()
						).build()
					%>'
					module="product_detail/render/js/ProductOptionsHandler"
				/>
			</div>

			<c:choose>
				<c:when test="<%= cpSku != null %>">
					<div class="availability-estimate mt-1"><%= cpContentHelper.getAvailabilityEstimateLabel(request) %></div>
				</c:when>
				<c:otherwise>
					<div class="availability-estimate mt-1" data-text-cp-instance-availability-estimate></div>
				</c:otherwise>
			</c:choose>

			<div class="mt-3 price-container row">
				<div class="col-lg-9 col-sm-12 col-xl-6">
					<commerce-ui:price
						CPCatalogEntry="<%= cpCatalogEntry %>"
						namespace="<%= liferayPortletResponse.getNamespace() %>"
					/>
				</div>
			</div>

			<c:if test="<%= cpSku != null %>">
				<liferay-commerce:tier-price
					CPInstanceId="<%= cpSku.getCPInstanceId() %>"
					taglibQuantityInputId='<%= liferayPortletResponse.getNamespace() + cpDefinitionId + "Quantity" %>'
				/>
			</c:if>

			<div class="align-items-center d-flex mt-3 product-detail-actions">
				<commerce-ui:add-to-cart
					alignment="left"
					CPCatalogEntry="<%= cpCatalogEntry %>"
					inline="<%= true %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
					options="[]"
					size="lg"
				/>

				<commerce-ui:add-to-wish-list
					CPCatalogEntry="<%= cpCatalogEntry %>"
					large="<%= true %>"
				/>
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

<c:if test="<%= !cpMedias.isEmpty() %>">
	<commerce-ui:panel
		elementClasses="mb-3"
		title='<%= LanguageUtil.get(resourceBundle, "attachments") %>'
	>
		<dl class="specification-list">

			<%
			int attachmentsCount = 0;

			for (CPMedia cpMedia : cpMedias) {
			%>

				<dt class="specification-term">
					<%= HtmlUtil.escape(cpMedia.getTitle()) %>
				</dt>
				<dd class="specification-desc">
					<aui:icon cssClass="icon-monospaced" image="download" markupView="lexicon" target="_blank" url="<%= cpMedia.getDownloadURL() %>" />
				</dd>

				<%
				attachmentsCount = attachmentsCount + 1;
				%>

				<c:if test="<%= attachmentsCount >= 2 %>">
					<dt class="specification-empty specification-term"></dt>
					<dd class="specification-desc specification-empty"></dd>

					<%
					attachmentsCount = 0;
					%>

				</c:if>

			<%
			}
			%>

		</dl>
	</commerce-ui:panel>
</c:if>

<c:if test="<%= cpContentHelper.hasDirectReplacement(cpSku) %>">
	<commerce-ui:panel
		elementClasses="mb-3"
		title='<%= LanguageUtil.get(resourceBundle, "replacements") %>'
	>
		<clay:data-set-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commerceAccountId", (commerceAccount == null) ? "0" : String.valueOf(commerceAccount.getCommerceAccountId())
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
			dataProviderKey="<%= CPContentDataSetConstants.COMMERCE_DATA_SET_KEY_REPLACEMENT_CP_INSTANCES %>"
			id="<%= CPContentDataSetConstants.COMMERCE_DATA_SET_KEY_REPLACEMENT_CP_INSTANCES %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= currentURLObj %>"
			style="stacked"
		/>
	</commerce-ui:panel>
</c:if>