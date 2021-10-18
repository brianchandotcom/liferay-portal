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

String productDetailURL = cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay);
%>

<div class="cp-renderer">
	<liferay-util:dynamic-include key="com.liferay.commerce.product.content.web#/add_to_cart#pre" />

	<div class="card d-flex flex-column product-card">
		<div class="card-item-first position-relative">
			<a href="<%= productDetailURL %>">
				<liferay-adaptive-media:img
					class="img-fluid product-card-picture"
					fileVersion="<%= cpContentHelper.getCPDefinitionImageFileVersion(cpCatalogEntry.getCPDefinitionId(), request) %>"
				/>

				<div class="aspect-ratio-item-bottom-left">
					<commerce-ui:availability-label
						CPCatalogEntry="<%= cpCatalogEntry %>"
					/>
				</div>
			</a>
		</div>

		<div class="card-body d-flex flex-column justify-content-between py-2">
			<div class="cp-information">
				<p class="card-subtitle" title="<%= (cpSku == null) ? StringPool.BLANK : cpSku.getSku() %>">
					<span class="text-truncate-inline">
						<span class="text-truncate"><%= (cpSku == null) ? StringPool.BLANK : cpSku.getSku() %></span>
					</span>
				</p>

				<p class="card-title" title="<%= cpCatalogEntry.getName() %>">
					<a href="<%= productDetailURL %>">
						<span class="text-truncate-inline">
							<span class="text-truncate"><%= cpCatalogEntry.getName() %></span>
						</span>
					</a>
				</p>

				<p class="card-text">
					<span class="text-truncate-inline">
						<span class="d-flex flex-row text-truncate">
							<commerce-ui:price
								compact="<%= true %>"
								CPCatalogEntry="<%= cpCatalogEntry %>"
							/>
						</span>
					</span>
				</p>
			</div>

			<div>
				<c:choose>
					<c:when test="<%= (cpSku == null) || cpContentHelper.hasCPDefinitionOptionRels(cpCatalogEntry.getCPDefinitionId()) %>">
						<div class="add-to-cart d-flex my-2 pt-5" id="<%= PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE + "add_to_cart" %>">
							<a class="btn btn-block btn-secondary" href="<%= productDetailURL %>" role="button" style="margin-top: 0.35rem;">
								<liferay-ui:message key="view-all-variants" />
							</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="mt-2">
							<commerce-ui:add-to-cart
								alignment="full-width"
								CPCatalogEntry="<%= cpCatalogEntry %>"
								inline="<%= false %>"
								options='<%= "[]" %>'
								size="md"
							/>
						</div>
					</c:otherwise>
				</c:choose>

				<div class="autofit-float autofit-row autofit-row-center compare-wishlist">
					<div class="autofit-col autofit-col-expand compare-checkbox">
						<div class="autofit-section">
							<div class="custom-checkbox custom-control custom-control-primary">
								<div class="custom-checkbox custom-control">
									<commerce-ui:compare-checkbox
										CPCatalogEntry="<%= cpCatalogEntry %>"
										label='<%= LanguageUtil.get(request, "compare") %>'
									/>
								</div>
							</div>
						</div>
					</div>

					<div class="autofit-col">
						<div class="autofit-section">
							<commerce-ui:add-to-wish-list
								CPCatalogEntry="<%= cpCatalogEntry %>"
							/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<liferay-util:dynamic-include key="com.liferay.commerce.product.content.web#/add_to_cart#post" />
</div>