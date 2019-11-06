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

<portlet:actionURL name="/asset_list/add_asset_entry_selection" var="addAssetEntrySelectionURL" />

<liferay-frontend:edit-form
	action="<%= addAssetEntrySelectionURL %>"
	cssClass="pt-0"
	fluid="<%= true %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= assetListDisplayContext.getSegmentsEntryId() %>" />
	<aui:input name="assetEntryIds" type="hidden" />

	<liferay-frontend:edit-form-body>
		<h3 class="sheet-title">
			<div class="autofit-row autofit-row-center">
				<div class="autofit-col">
					<%= HtmlUtil.escape(editAssetListDisplayContext.getSegmentsEntryName(editAssetListDisplayContext.getSegmentsEntryId(), locale)) %>
				</div>

				<div class="autofit-col autofit-col-end inline-item-after">
					<liferay-util:include page="/asset_list_entry_variation_action.jsp" servletContext="<%= application %>" />
				</div>
			</div>
		</h3>

		<h3 class="sheet-title text-uppercase">
			<span class="autofit-padded-no-gutters autofit-row">
				<span class="autofit-col autofit-col-expand">
					<span class="heading-text">
						<liferay-ui:message key="asset-entries" />
					</span>
				</span>
				<span class="autofit-col">
					<liferay-ui:icon-menu
						direction="right"
						message="select"
						showArrow="<%= false %>"
						showWhenSingleIcon="<%= true %>"
						triggerCssClass="btn-sm"
					>

						<%
						Map<String, Map<String, Object>> manualAddIconDataMap = editAssetListDisplayContext.getManualAddIconDataMap();

						for (Map.Entry<String, Map<String, Object>> entry : manualAddIconDataMap.entrySet()) {
						%>

							<liferay-ui:icon
								cssClass="asset-selector"
								data="<%= entry.getValue() %>"
								id="<%= themeDisplay.getScopeGroupId() + HtmlUtil.getAUICompatibleId(entry.getKey()) %>"
								message="<%= HtmlUtil.escape(entry.getKey()) %>"
								url="javascript:;"
							/>

						<%
						}
						%>

					</liferay-ui:icon-menu>
				</span>
			</span>
		</h3>

		<liferay-ui:search-container
			compactEmptyResultsMessage="<%= true %>"
			emptyResultsMessage="no-assets-are-selected"
			id="assetEntriesSearchContainer"
			searchContainer="<%= editAssetListDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.asset.list.model.AssetListEntryAssetEntryRel"
				escapedModel="<%= true %>"
				keyProperty="entryId"
				modelVar="assetListEntryAssetEntryRel"
			>

				<%
				AssetEntry assetEntry = AssetEntryServiceUtil.getEntry(assetListEntryAssetEntryRel.getAssetEntryId());

				AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetEntry.getClassName());

				AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK(), AssetRendererFactory.TYPE_LATEST);
				%>

				<liferay-ui:search-container-column-text
					name="title"
					truncate="<%= true %>"
				>
					<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>

					<c:if test="<%= !assetEntry.isVisible() %>">
						(<aui:workflow-status
							markupView="lexicon"
							showIcon="<%= false %>"
							showLabel="<%= false %>"
							status="<%= assetRenderer.getStatus() %>"
							statusMessage='<%= (assetRenderer.getStatus() == 0) ? "not-visible" : WorkflowConstants.getStatusLabel(assetRenderer.getStatus()) %>'
						/>)
					</c:if>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="type"
					value="<%= assetRendererFactory.getTypeName(locale) %>"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					value="<%= assetEntry.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-jsp
					path="/asset_list/asset_selection_order_up_action.jsp"
				/>

				<liferay-ui:search-container-column-jsp
					path="/asset_list/asset_selection_order_down_action.jsp"
				/>

				<liferay-ui:search-container-column-jsp
					path="/asset_list/asset_selection_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>

<aui:script require="metal-dom/src/dom as dom, frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var delegateHandler = dom.delegate(
		document.body,
		'click',
		'.asset-selector a',
		function(event) {
			event.preventDefault();

			const delegateTarget = event.delegateTarget;

			const itemSelectorDialog = new ItemSelectorDialog.default({
				eventName: '<portlet:namespace />selectAsset',
				title: encodeURI(delegateTarget.dataset.title),
				url: delegateTarget.dataset.href
			});

			itemSelectorDialog.open();

			itemSelectorDialog.on('selectedItemChange', function(event) {
				const selectedItems = event.selectedItem;

				if (selectedItems) {
					const assetEntryIds = [];

					Array.prototype.forEach.call(selectedItems, function(
						assetEntry
					) {
						assetEntryIds.push(assetEntry.entityid);
					});

					Liferay.Util.postForm(document.<portlet:namespace />fm, {
						data: {
							assetEntryIds: assetEntryIds.join(',')
						}
					});
				}
			});
		}
	);

	var onDestroyPortlet = function() {
		delegateHandler.removeListener();

		Liferay.detach('destroyPortlet', onDestroyPortlet);
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);
</aui:script>