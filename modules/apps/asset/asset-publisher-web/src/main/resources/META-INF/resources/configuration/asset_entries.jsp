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
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");

List<AssetEntry> assetEntries = assetPublisherHelper.getAssetEntries(renderRequest, portletPreferences, permissionChecker, assetPublisherDisplayContext.getGroupIds(), true, assetPublisherDisplayContext.isEnablePermissions(), true, AssetRendererFactory.TYPE_LATEST);
%>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="none"
	iteratorURL="<%= configurationRenderURL %>"
	total="<%= assetEntries.size() %>"
>
	<liferay-ui:search-container-results
		calculateStartAndEnd="<%= true %>"
		results="<%= assetEntries %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.asset.kernel.model.AssetEntry"
		escapedModel="<%= true %>"
		keyProperty="entryId"
		modelVar="assetEntry"
	>

		<%
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
			path="/configuration/asset_selection_action.jsp"
		/>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/configuration/asset_selection_order_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= total > SearchContainer.DEFAULT_DELTA %>"
	/>
</liferay-ui:search-container>

<c:if test='<%= SessionMessages.contains(renderRequest, "deletedMissingAssetEntries") %>'>
	<div class="alert alert-info">
		<liferay-ui:message key="the-selected-assets-have-been-removed-from-the-list-because-they-do-not-belong-in-the-scope-of-this-widget" />
	</div>
</c:if>

<%
long[] groupIds = assetPublisherDisplayContext.getGroupIds();
%>

<c:if test="<%= ArrayUtil.isNotEmpty(groupIds) %>">

<div class="d-flex">

<%
for (long groupId : groupIds) {
	Group group = GroupLocalServiceUtil.getGroup(groupId);

	String title = LanguageUtil.format(request, (groupIds.length == 1) ? "select" : "select-in-x", HtmlUtil.escape(group.getDescriptiveName(locale)), false);
%>

		<clay:dropdown-menu
			additionalProps='<%=
				HashMapBuilder.<String, Object>put(
					"currentURL", currentURL
				).build()
			%>'
			aria-label="<%= title %>"
			cssClass="mr-2"
			displayType="secondary"
			dropdownItems="<%= assetPublisherDisplayContext.getDropdownItems(group) %>"
			label="select"
			propsTransformer="js/AssetEntrySelectionDropdownPropsTransformer"
			title="<%= title %>"
		/>

<%
}
%>

</div>

</c:if>

<script>
	function <portlet:namespace />moveSelectionDown(assetEntryOrder) {
		Liferay.Util.postForm(document.<portlet:namespace />fm, {
			data: {
				assetEntryOrder: assetEntryOrder,
				cmd: 'move-selection-down',
				redirect: '<%= HtmlUtil.escapeJS(currentURL) %>',
			},
		});
	}

	function <portlet:namespace />moveSelectionUp(assetEntryOrder) {
		Liferay.Util.postForm(document.<portlet:namespace />fm, {
			data: {
				assetEntryOrder: assetEntryOrder,
				cmd: 'move-selection-up',
				redirect: '<%= HtmlUtil.escapeJS(currentURL) %>',
			},
		});
	}
</script>