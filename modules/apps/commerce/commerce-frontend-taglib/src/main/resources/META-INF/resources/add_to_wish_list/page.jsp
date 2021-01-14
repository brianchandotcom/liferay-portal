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

<%@ include file="/add_to_wish_list/init.jsp" %>

<div class="add-to-wish-list" id="<%= addToWishListId %>">
	<liferay-util:include page="/add_to_wish_list/skeleton.jsp" servletContext="<%= application %>" />
</div>

<aui:script require="commerce-frontend-js/components/add_to_wish_list/entry as AddToWishList">
	const initialProps = {
		accountId: <%= commerceAccountId %>,
		cpDefinitionId: <%= cpDefinitionId %>,
		large: <%= large %>,
		isInWishList: <%= inWishList %>,
		skuId: <%= skuId %>,
		spritemap: '<%= spritemap %>',
	};

	AddToWishList.default(
		'<%= addToWishListId %>',
		'<%= addToWishListId %>',
		initialProps
	);
</aui:script>