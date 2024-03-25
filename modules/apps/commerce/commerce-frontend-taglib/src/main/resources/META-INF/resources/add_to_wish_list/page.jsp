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

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"accountId", commerceAccountId
		).put(
			"addToWishListId", addToWishListId
		).put(
			"cpDefinitionId", cpCatalogEntry.getCPDefinitionId()
		).put(
			"isInWishList", inWishList
		).put(
			"large", large
		).put(
			"skuId", skuId
		).build()
	%>'
	module="{addToListWish} from commerce-frontend-taglib"
/>