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
AssetDisplayPagesItemSelectorViewDisplayContext assetDisplayPagesItemSelectorViewDisplayContext = (AssetDisplayPagesItemSelectorViewDisplayContext)request.getAttribute(AssetDisplayPageItemSelectorWebKeys.ASSET_DISPLAY_PAGES_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);
%>

<clay:management-toolbar-v2
	displayContext="<%= new AssetDisplayPagesItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, assetDisplayPagesItemSelectorViewDisplayContext) %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		id="displayPages"
		searchContainer="<%= assetDisplayPagesItemSelectorViewDisplayContext.getAssetDisplayPageSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			keyProperty="layoutPageTemplateEntryId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			row.setCssClass("entry-card form-check-card lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new LayoutPageTemplateEntryVerticalCard(layoutPageTemplateEntry, renderRequest) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
	var delegate = delegateModule.default;

	var selectFragmentEntryHandler = delegate(
		document.querySelector('#<portlet:namespace />fm'),
		'click',
		'.layout-page-template-entry',
		function (event) {
			var activeCards = document.querySelectorAll('.form-check-card.active');

			if (activeCards.length) {
				activeCards.forEach(function (card) {
					card.classList.remove('active');
				});
			}

			var newSelectedCard = event.delegateTarget.closest('.form-check-card');

			if (newSelectedCard) {
				newSelectedCard.classList.add('active');
			}

			Liferay.Util.getOpener().Liferay.fire(
				'<%= assetDisplayPagesItemSelectorViewDisplayContext.getItemSelectedEventName() %>',
				{
					data: event.delegateTarget.dataset,
				}
			);
		}
	);

	function removeListener() {
		selectFragmentEntryHandler.dispose();

		Liferay.detach('destroyPortlet', removeListener);
	}

	Liferay.on('destroyPortlet', removeListener);
</aui:script>