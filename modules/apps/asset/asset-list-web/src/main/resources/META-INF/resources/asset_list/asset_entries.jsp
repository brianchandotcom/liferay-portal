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
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="assetEntryIds" type="hidden" />

	<liferay-frontend:edit-form-body>
		<h3 class="sheet-subtitle">
			<span class="autofit-padded-no-gutters autofit-row">
				<span class="autofit-col autofit-col-expand">
					<span class="heading-text">
						<liferay-ui:message key="asset-entries" />
					</span>
				</span>
			</span>
		</h3>

		<liferay-ui:search-container
			compactEmptyResultsMessage="<%= true %>"
			emptyResultsMessage="none"
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
					path="/asset_list/asset_selection_action.jsp"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/asset_list/asset_selection_order_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-ui:icon-menu
			direction="right"
			message="select"
			showArrow="<%= false %>"
			showWhenSingleIcon="<%= true %>"
		>

			<%
			Map<String, Map<String, Object>> manualAddIconDataMap = editAssetListDisplayContext.getManualAddIconDataMap();

			for (Map.Entry<String, Map<String, Object>> entry : manualAddIconDataMap.entrySet()) {
			%>

				<liferay-ui:icon
					cssClass="asset-selector"
					data="<%= entry.getValue() %>"
					id="<%= themeDisplay.getScopeGroupId() + FriendlyURLNormalizerUtil.normalize(entry.getKey()) %>"
					message="<%= HtmlUtil.escape(entry.getKey()) %>"
					url="javascript:;"
				/>

			<%
			}
			%>

		</liferay-ui:icon-menu>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="liferay-item-selector-dialog">
	$('body').on(
		'click',
		'.asset-selector a',
		function(event) {
			event.preventDefault();

			var currentTarget = $(event.currentTarget);

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectAsset',
					id: '<portlet:namespace />selectAsset' + currentTarget.attr('id'),
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								var assetEntryIds = [];

								selectedItems.forEach(
									function(assetEntry) {
										assetEntryIds.push(assetEntry.entityid);
									}
								);

								<portlet:namespace />fm.<portlet:namespace />assetEntryIds.value = assetEntryIds.join(',');

								submitForm(document.<portlet:namespace />fm);
							}
						}
					},
					title: currentTarget.data('title'),
					url: currentTarget.data('href')
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>