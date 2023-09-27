<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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