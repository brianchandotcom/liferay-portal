<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AssetDisplayPagesItemSelectorCustomViewDisplayContext assetDisplayPagesItemSelectorCustomViewDisplayContext = (AssetDisplayPagesItemSelectorCustomViewDisplayContext)request.getAttribute(AssetDisplayPagesItemSelectorCustomViewDisplayContext.class.getName());
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new AssetDisplayPagesItemSelectorCustomViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, assetDisplayPagesItemSelectorCustomViewDisplayContext) %>"
/>

<clay:container-fluid>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= assetDisplayPagesItemSelectorCustomViewDisplayContext.getLayoutPageTemplateBreadcrumbEntries() %>"
	/>

	<liferay-ui:search-container
		id="displayPages"
		searchContainer="<%= assetDisplayPagesItemSelectorCustomViewDisplayContext.getAssetDisplayPageSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="object"
		>
			<c:choose>
				<c:when test="<%= object instanceof LayoutPageTemplateCollection %>">
					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<clay:horizontal-card
							horizontalCard="<%= new LayoutPageTemplateCollectionHorizontalCard(assetDisplayPagesItemSelectorCustomViewDisplayContext, (LayoutPageTemplateCollection)object) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new LayoutPageTemplateEntryVerticalCard((LayoutPageTemplateEntry)object, renderRequest) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			resultRowSplitter="<%= new LayoutPageTemplateResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>