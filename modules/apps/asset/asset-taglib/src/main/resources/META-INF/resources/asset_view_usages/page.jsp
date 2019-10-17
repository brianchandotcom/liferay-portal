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

<%@ include file="/asset_view_usages/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-asset:asset-view-usages:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-asset:asset-view-usages:classPK"));

AssetEntryUsagesDisplayContext assetEntryUsagesDisplayContext = new AssetEntryUsagesDisplayContext(renderRequest, renderResponse, className, classPK);
%>

<div id="<portlet:namespace />assetEntryUsagesList">
	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		searchContainer="<%= assetEntryUsagesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.model.AssetEntryUsage"
			modelVar="assetEntryUsage"
		>
			<liferay-ui:search-container-column-text
				name="pages"
			>
				<h5>
					<%= HtmlUtil.escape(assetEntryUsagesDisplayContext.getAssetEntryUsageName(assetEntryUsage)) %>
				</h5>

				<div class="text-secondary">
					<%= LanguageUtil.get(request, assetEntryUsagesDisplayContext.getAssetEntryUsageTypeLabel(assetEntryUsage)) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="text-right"
			>
				<c:if test="<%= assetEntryUsagesDisplayContext.isShowPreview(assetEntryUsage) %>">

					<%
					Layout curLayout = LayoutLocalServiceUtil.fetchLayout(assetEntryUsage.getPlid());
					%>

					<c:if test="<%= curLayout != null %>">

						<%
						Map<String, String> data = new HashMap<>();

						data.put("href", assetEntryUsagesDisplayContext.getPreviewURL(assetEntryUsage));
						%>

						<clay:button
							data="<%= data %>"
							elementClasses="preview-asset-entry-usage table-action-link"
							icon="view"
							monospaced="<%= true %>"
							style="secondary"
						/>
					</c:if>
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
			searchResultCssClass="table table-autofit table-heading-nowrap"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="metal-dom/src/all/dom as dom">
	if (document.querySelector('#<portlet:namespace/>assetEntryUsagesList')) {
		var previewAssetEntryUsagesList = dom.delegate(
			document.querySelector('#<portlet:namespace/>assetEntryUsagesList'),
			'click',
			'.preview-asset-entry-usage',
			function(event) {
				var delegateTarget = event.delegateTarget;

				Liferay.Util.openWindow({
					dialog: {
						destroyOnHide: true,
						modal: true
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer article-preview'
					},
					title: '<liferay-ui:message key="preview" />',
					uri: delegateTarget.getAttribute('data-href')
				});
			}
		);

		function removeListener() {
			previewAssetEntryUsagesList.removeListener();

			Liferay.detach('destroyPortlet', removeListener);
		}

		Liferay.on('destroyPortlet', removeListener);
	}
</aui:script>