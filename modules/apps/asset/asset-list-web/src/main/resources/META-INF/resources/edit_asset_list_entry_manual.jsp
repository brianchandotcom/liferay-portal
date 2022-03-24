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
portletDisplay.setURLBack(editAssetListDisplayContext.getBackURL());

AssetListEntry assetListEntry = assetListDisplayContext.getAssetListEntry();
%>

<portlet:actionURL name="/asset_list/add_asset_entry_selection" var="addAssetEntrySelectionURL">
	<portlet:param name="mvcPath" value="/edit_asset_list_entry.jsp" />
</portlet:actionURL>

<portlet:actionURL name="/asset_list/update_asset_list_entry_manual" var="updateAssetListEntryURL" />

<c:choose>
	<c:when test="<%= Validator.isNull(assetListEntry.getAssetEntryType()) %>">
		<liferay-frontend:edit-form
			action="<%= updateAssetListEntryURL %>"
			cssClass="pt-0"
			fluid="<%= true %>"
			method="post"
			name="fm"
		>
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="backURL" type="hidden" value="<%= editAssetListDisplayContext.getBackURL() %>" />
			<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />

			<div class="mb-3 text-muted">
				<liferay-ui:message key="choose-the-asset-type-you-want-to-use-for-this-manual-collection" />
			</div>

			<liferay-frontend:edit-form-body>
				<liferay-util:include page="/asset_list/source.jsp" servletContext="<%= application %>" />
			</liferay-frontend:edit-form-body>

			<c:if test="<%= !editAssetListDisplayContext.isLiveGroup() %>">
				<liferay-frontend:edit-form-footer>
					<aui:button disabled="<%= editAssetListDisplayContext.isNoAssetTypeSelected() %>" id="saveButton" onClick='<%= liferayPortletResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />

					<aui:button href="<%= editAssetListDisplayContext.getBackURL() %>" type="cancel" />
				</liferay-frontend:edit-form-footer>
			</c:if>
		</liferay-frontend:edit-form>
	</c:when>
	<c:otherwise>
		<liferay-frontend:edit-form
			action="<%= addAssetEntrySelectionURL %>"
			cssClass="pt-0"
			fluid="<%= true %>"
			method="post"
			name="fm"
		>
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="backURL" type="hidden" value="<%= editAssetListDisplayContext.getBackURL() %>" />
			<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
			<aui:input name="segmentsEntryId" type="hidden" value="<%= assetListDisplayContext.getSegmentsEntryId() %>" />
			<aui:input name="assetEntryIds" type="hidden" />

			<liferay-frontend:edit-form-body>
				<h3 class="sheet-title">
					<clay:content-row
						verticalAlign="center"
					>
						<clay:content-col>
							<%= HtmlUtil.escape(editAssetListDisplayContext.getSegmentsEntryName(editAssetListDisplayContext.getSegmentsEntryId(), locale)) %>
						</clay:content-col>

						<clay:content-col
							cssClass="inline-item-after"
						>
							<liferay-util:include page="/asset_list_entry_variation_action.jsp" servletContext="<%= application %>" />
						</clay:content-col>
					</clay:content-row>
				</h3>

				<h3 class="sheet-title text-uppercase">
					<clay:content-row
						containerElement="span"
						noGutters="true"
					>
						<clay:content-col
							containerElement="span"
							expand="<%= true %>"
						>
							<span class="heading-text">
								<liferay-ui:message key="collection-items" />
							</span>
						</clay:content-col>

						<c:if test="<%= !editAssetListDisplayContext.isLiveGroup() %>">
							<clay:content-col
								containerElement="span"
							>
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
											url="javascript:void(0);"
										/>

									<%
									}
									%>

								</liferay-ui:icon-menu>
							</clay:content-col>
						</c:if>
					</clay:content-row>
				</h3>

				<liferay-ui:search-container
					compactEmptyResultsMessage="<%= true %>"
					emptyResultsMessage="no-collection-items-are-selected"
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

						<c:if test="<%= !editAssetListDisplayContext.isLiveGroup() %>">
							<liferay-ui:search-container-column-jsp
								path="/asset_list/asset_selection_order_up_action.jsp"
							/>

							<liferay-ui:search-container-column-jsp
								path="/asset_list/asset_selection_order_down_action.jsp"
							/>

							<liferay-ui:search-container-column-jsp
								path="/asset_list/asset_selection_action.jsp"
							/>
						</c:if>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</liferay-frontend:edit-form-body>
		</liferay-frontend:edit-form>
	</c:otherwise>
</c:choose>

<aui:script require="frontend-js-web/index as frontendJsWeb">
	var {delegate} = frontendJsWeb;

	var delegateHandler = delegate(
		document.body,
		'click',
		'.asset-selector a',
		(event) => {
			event.preventDefault();

			var delegateTarget = event.delegateTarget;

			Liferay.Util.openSelectionModal({
				customSelectEvent: true,
				multiple: true,
				onSelect: function (selectedItems) {
					if (selectedItems) {
						var assetEntryIds = [];

						Array.prototype.forEach.call(
							selectedItems,
							(assetEntry) => {
								assetEntryIds.push(assetEntry.value);
							}
						);

						Liferay.Util.postForm(document.<portlet:namespace />fm, {
							data: {
								assetEntryIds: assetEntryIds.join(','),
							},
						});
					}
				},
				selectEventName: '<portlet:namespace />selectAsset',
				title: delegateTarget.dataset.title,
				url: delegateTarget.dataset.href,
			});
		}
	);

	var onDestroyPortlet = function () {
		delegateHandler.dispose();

		Liferay.detach('destroyPortlet', onDestroyPortlet);
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);
</aui:script>