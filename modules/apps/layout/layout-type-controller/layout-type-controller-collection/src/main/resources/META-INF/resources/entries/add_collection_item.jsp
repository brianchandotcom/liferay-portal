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

<%@ include file="/entries/init.jsp" %>

<%
List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders = (List<AssetPublisherAddItemHolder>)request.getAttribute(CollectionPageLayoutTypeControllerWebKeys.ASSET_PUBLISHER_ADD_ITEM_HOLDERS);
%>

<liferay-ui:success key="collectionItemAdded" message="your-request-completed-successfully" />

<li class="control-menu-nav-item control-menu-nav-item-content">
	<c:choose>
		<c:when test="<%= assetPublisherAddItemHolders.size() == 1 %>">

			<%
			AssetPublisherAddItemHolder assetPublisherAddItemHolder = assetPublisherAddItemHolders.get(0);

			String label = LanguageUtil.format(request, "new-x", new Object[] {assetPublisherAddItemHolder.getModelResource()});
			%>

			<clay:link
				aria-label="<%= label %>"
				borderless="<%= true %>"
				cssClass="lfr-portal-tooltip"
				data-title="<%= label %>"
				displayType="unstyled"
				href="<%= String.valueOf(assetPublisherAddItemHolder.getPortletURL()) %>"
				icon="plus"
				monospaced="<%= true %>"
				small="<%= true %>"
			/>
		</c:when>
		<c:otherwise>

			<%
			String label = LanguageUtil.get(resourceBundle, "new-collection-page-item");

			CollectionItemsDetailDisplayContext collectionItemsDetailDisplayContext = (CollectionItemsDetailDisplayContext)request.getAttribute(CollectionPageLayoutTypeControllerWebKeys.COLLECTION_ITEMS_DETAIL_DISPLAY_CONTEXT);
			%>

			<clay:dropdown-menu
				aria-label="<%= label %>"
				borderless="<%= true %>"
				displayType="unstyled"
				dropdownItems="<%= collectionItemsDetailDisplayContext.getDropdownItems(assetPublisherAddItemHolders) %>"
				icon="plus"
				monospaced="<%= true %>"
				small="<%= true %>"
				title="<%= label %>"
			/>
		</c:otherwise>
	</c:choose>
</li>