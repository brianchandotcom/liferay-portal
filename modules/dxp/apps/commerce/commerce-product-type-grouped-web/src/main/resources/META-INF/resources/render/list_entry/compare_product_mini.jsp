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
CPCatalogEntry cpCatalogEntry = (CPCatalogEntry)request.getAttribute("compare_product_mini.jsp-cpContentListRenderer-cpCatalogEntry");
String deleteCompareProductURL = (String)request.getAttribute("compare_product_mini.jsp-cpContentListRenderer-deleteCompareProductURL");
%>

<div class="autofit-row autofit-row-end">
	<div class="autofit-col">
		<div class="card">
			<div class="sticker sticker-top-right">
				<liferay-ui:icon
					icon="times"
					markupView="lexicon"
					message="remove"
					url="<%= deleteCompareProductURL %>"
				/>
			</div>

			<a class="aspect-ratio" href="<%= CPDefinitionFriendlyURLUtil.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">

				<%
				String img = cpCatalogEntry.getDefaultImageFileUrl();
				%>

				<c:if test="<%= Validator.isNotNull(img) %>">
					<img class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= img %>">
				</c:if>
			</a>
		</div>
	</div>

	<div class="autofit-col autofit-col-expand">
		<a class="compare-link" href="<%= CPDefinitionFriendlyURLUtil.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">
			<%= cpCatalogEntry.getName() %>
		</a>
	</div>
</div>