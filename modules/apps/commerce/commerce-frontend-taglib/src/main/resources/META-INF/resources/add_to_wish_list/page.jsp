<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/add_to_wish_list/init.jsp" %>

<div class="add-to-wish-list" id="<%= addToWishListId %>">
	<liferay-util:include page="/add_to_wish_list/skeleton.jsp" servletContext="<%= application %>" />
</div>

<aui:script require="commerce-frontend-js/components/add_to_wish_list/entry as AddToWishList">
	const initialProps = {
		accountId: <%= commerceAccountId %>,
		cpDefinitionId: <%= cpCatalogEntry.getCPDefinitionId() %>,
		large: <%= large %>,
		isInWishList: <%= inWishList %>,
		skuId: <%= skuId %>,
	};

	AddToWishList.default(
		'<%= addToWishListId %>',
		'<%= addToWishListId %>',
		initialProps
	);
</aui:script>