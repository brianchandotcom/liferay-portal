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

<div class="autofit-col">
	<div class="autofit-section">
		<commerce-ui:add-to-wish-list
			commerceAccountId="<%= cpContentListEntryModel.getAccountId() %>"
			cpDefinitionId="<%= cpContentListEntryModel.getCpDefinitionId() %>"
			inWishList="<%= cpContentListEntryModel.isInWishList() %>"
			skuId="<%= cpContentListEntryModel.getSkuId() %>"
			spritemap="<%= spritemap %>"
		/>
	</div>
</div>