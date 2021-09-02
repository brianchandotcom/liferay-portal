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
String label = LanguageUtil.get(resourceBundle, "new-collection-page-item");

List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders = (List<AssetPublisherAddItemHolder>)request.getAttribute(CollectionPageLayoutTypeControllerWebKeys.ASSET_PUBLISHER_ADD_ITEM_HOLDERS);

if (assetPublisherAddItemHolders.size() == 1) {
	AssetPublisherAddItemHolder assetPublisherAddItemHolder = assetPublisherAddItemHolders.get(0);

	label = LanguageUtil.format(request, "new-x", new Object[] {assetPublisherAddItemHolder.getModelResource()});
}

Map<String, Object> data = HashMapBuilder.<String, Object>put(
	"title", label
).build();
%>

<liferay-ui:success key="collectionItemAdded" message="your-request-completed-successfully" />

<li class="control-menu-nav-item control-menu-nav-item-content">
	<a aria-label="<%= label %>" data-title="<%= label %>">
		<c:choose>
			<c:when test="<%= assetPublisherAddItemHolders.size() == 1 %>">

				<%
				AssetPublisherAddItemHolder assetPublisherAddItemHolder = assetPublisherAddItemHolders.get(0);

				PortletURL portletURL = PortletURLBuilder.create(
					assetPublisherAddItemHolder.getPortletURL()
				).setRedirect(
					currentURL
				).buildPortletURL();
				%>

				<liferay-ui:icon
					data="<%= data %>"
					icon="plus"
					linkCssClass="icon-monospaced lfr-portal-tooltip"
					markupView="lexicon"
					message="<%= label %>"
					url="<%= portletURL.toString() %>"
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:icon-menu
					cssClass="lfr-portal-tooltip"
					data="<%= data %>"
					direction="left-side"
					icon="plus"
					markupView="lexicon"
					message="<%= label %>"
				>

					<%
					for (AssetPublisherAddItemHolder assetPublisherAddItemHolder : assetPublisherAddItemHolders) {
						PortletURL portletURL = PortletURLBuilder.create(
							assetPublisherAddItemHolder.getPortletURL()
						).setRedirect(
							currentURL
						).buildPortletURL();
					%>

						<liferay-ui:icon
							message="<%= assetPublisherAddItemHolder.getModelResource() %>"
							url="<%= portletURL.toString() %>"
						/>

					<%
					}
					%>

				</liferay-ui:icon-menu>
			</c:otherwise>
		</c:choose>
	</a>
</li>