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

AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(className, classPK);

List<AssetEntryUsage> assetEntryUsages = AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(assetEntry.getEntryId());
%>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="there-are-no-asset-entry-usages"
	total="<%= assetEntryUsages.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= assetEntryUsages %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.asset.model.AssetEntryUsage"
		modelVar="assetEntryUsage"
	>

		<%
		String name = StringPool.BLANK;
		String previewURL = StringPool.BLANK;

		long classNameId = assetEntryUsage.getClassNameId();

		if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
			Layout curLayout = LayoutLocalServiceUtil.fetchLayout(assetEntryUsage.getClassPK());

			if (curLayout != null) {
				name = curLayout.getName(locale);

				previewURL = PortalUtil.getLayoutFriendlyURL(curLayout, themeDisplay);
			}
		}
		else {
			LayoutPageTemplateEntry layoutPageTemplateEntry = LayoutPageTemplateEntryLocalServiceUtil.fetchLayoutPageTemplateEntry(assetEntryUsage.getClassPK());

			if (layoutPageTemplateEntry != null) {
				name = layoutPageTemplateEntry.getName();

				Layout curLayout = LayoutLocalServiceUtil.fetchLayout(layoutPageTemplateEntry.getPlid());

				previewURL = PortalUtil.getLayoutFriendlyURL(curLayout, themeDisplay);
			}
		}
		%>

		<liferay-ui:search-container-column-text
			name="pages"
		>
			<h5>
				<%= HtmlUtil.escape(name) %>
			</h5>

			<div class="text-secondary">
				<c:choose>
					<c:when test="<%= assetEntryUsage.getClassNameId() == PortalUtil.getClassNameId(AssetDisplayPageEntry.class) %>">
						<liferay-ui:message key="display-page" />
					</c:when>
					<c:when test="<%= assetEntryUsage.getClassNameId() == PortalUtil.getClassNameId(Layout.class) %>">
						<liferay-ui:message key="page" />
					</c:when>
					<c:when test="<%= assetEntryUsage.getClassNameId() == PortalUtil.getClassNameId(FragmentEntryLink.class) %>">
						<liferay-ui:message key="fragment" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="page-template" />
					</c:otherwise>
				</c:choose>
			</div>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			cssClass="text-right"
		>

			<%
			Map<String, String> data = new HashMap<>();

			data.put("href", previewURL);
			data.put("title", assetEntry.getTitle(locale));
			%>

			<clay:button
				data="<%= data %>"
				elementClasses="preview-asset-entry-usage table-action-link"
				icon="view"
				monospaced="<%= true %>"
				style="secondary"
			/>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= false %>"
		searchResultCssClass="table table-autofit table-heading-nowrap"
	/>
</liferay-ui:search-container>

<aui:script require="metal-dom/src/all/dom as dom">
	var previewAssetEntryUsagesList = dom.delegate(
		document.querySelector('#<portlet:namespace/>assetEntryUsagesList'),
		'click',
		'.preview-asset-entry-usage',
		function(event) {
			var delegateTarget = event.delegateTarget;

			Liferay.fire(
				'previewArticle',
				{
					title: delegateTarget.getAttribute('data-title'),
					uri: delegateTarget.getAttribute('data-href')
				}
			);
		}
	);

	function removeListener() {
		previewAssetEntryUsagesList.removeListener();

		Liferay.detach('destroyPortlet', removeListener);
	}

	Liferay.on('destroyPortlet', removeListener);
</aui:script>