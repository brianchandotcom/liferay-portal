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
CPCatalogEntry cpCatalogEntry = (CPCatalogEntry)request.getAttribute("compare_product.jsp-cpContentListRenderer-cpCatalogEntry");
CPInstance cpInstance = (CPInstance)request.getAttribute("compare_product.jsp-cpContentListRenderer-cpInstance");
String deleteCompareProductURL = (String)request.getAttribute("compare_product.jsp-cpContentListRenderer-deleteCompareProductURL");
%>

<div class="card">
	<div class="autofit-row">
		<div class="autofit-col autofit-col-expand">
			<liferay-ui:icon
				cssClass="compare-remove-item link-monospaced"
				icon="times"
				markupView="lexicon"
				url="<%= deleteCompareProductURL %>"
			/>
		</div>
	</div>

	<a class="product-image-container" href="<%= CPDefinitionFriendlyURLUtil.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">

		<%
		String img = cpCatalogEntry.getDefaultImageFileUrl();
		%>

		<c:if test="<%= Validator.isNotNull(img) %>">
			<img class="product-image" src="<%= img %>">
		</c:if>
	</a>

	<div class="card-section-expand">
		<div class="card-title">
			<a href="<%= CPDefinitionFriendlyURLUtil.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">
				<%= cpCatalogEntry.getName() %>
			</a>
		</div>

		<c:if test="<%= cpInstance != null %>">
			<div class="card-subtitle">
				<liferay-ui:message arguments="<%= cpInstance.getSku() %>" key="sku-x" />
			</div>
		</c:if>
	</div>

	<div class="autofit-float autofit-row autofit-row-end product-price-section">
		<div class="autofit-col">
			<span class="product-price">
				<liferay-commerce:price
					CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>"
				/>
			</span>
		</div>
	</div>

	<%
	String quantityInputId = cpCatalogEntry.getCPDefinitionId() + "_quantity";
	%>

	<div class="product-footer">
		<c:if test="<%= cpInstance != null %>">
			<div class="autofit-row product-actions">
				<div class="autofit-col autofit-col-expand">
					<liferay-commerce:quantity-input CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>" name="<%= quantityInputId %>" useSelect="<%= false %>" />
				</div>

				<div class="autofit-col">
					<liferay-commerce-cart:add-to-cart
						CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>"
						CPInstanceId="<%= cpInstance.getCPInstanceId() %>"
						elementClasses="btn-block btn-primary text-truncate"
						taglibQuantityInputId='<%= renderResponse.getNamespace() + quantityInputId %>'
					/>
				</div>
			</div>
		</c:if>

		<div class="product-subactions">
			<a href="#placeholder">Add to list +</a>
		</div>
	</div>
</div>