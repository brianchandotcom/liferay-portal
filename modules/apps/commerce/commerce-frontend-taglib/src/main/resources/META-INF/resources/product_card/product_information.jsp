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

<%@ include file="/product_card/init.jsp" %>

<div class="cp-information">
	<p class="card-subtitle" title="<%= sku %>">
		<span class="text-truncate-inline">
			<span class="text-truncate"><%= sku %></span>
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
					CPDefinitionId='<%= cpCatalogEntry.getCPDefinitionId() %>'
					displayOneLine="<%= true %>"
				/>
			</span>
		</span>
	</p>
</div>