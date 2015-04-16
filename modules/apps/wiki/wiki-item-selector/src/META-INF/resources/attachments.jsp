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
PortletURL portletURL = (PortletURL)request.getAttribute("portletURL");

String itemSelectedCallback = (String)request.getAttribute("itemSelectedCallback");

WikiAttachmentItemSelectorCriterion wikiAttachmentItemSelectorCriterion = (WikiAttachmentItemSelectorCriterion)request.getAttribute("wikiAttachmentItemSelectorCriterion");

WikiPageResource pageResource = WikiPageResourceLocalServiceUtil.fetchWikiPageResource(wikiAttachmentItemSelectorCriterion.getWikiPageResourceId());

WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(pageResource.getNodeId(), pageResource.getTitle());
%>

<aui:form method="post" name="selectDocumentFm">
	<liferay-ui:search-container
		emptyResultsMessage="this-page-does-not-have-file-attachments"
		iteratorURL="<%= portletURL %>"
		total="<%= wikiPage.getAttachmentsFileEntriesCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= wikiPage.getAttachmentsFileEntries(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.FileEntry"
			keyProperty="fileEntryId"
			modelVar="fileEntry"
		>

			<%
			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(fileEntry.getFileEntryId());
			%>

			<liferay-ui:search-container-column-text
				name="file-name"
			>
				<liferay-ui:icon
					iconCssClass="<%= assetRenderer.getIconCssClass() %>"
					label="<%= true %>"
					message="<%= fileEntry.getTitle() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="size"
				value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("fileEntryId", fileEntry.getFileEntryId());
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	var container = $('#<portlet:namespace />selectDocumentFm');

	var selectorButtons = container.find('.selector-button');

	container.on(
		'click',
		'.selector-button',
		function(event) {
			var fileEntryId = event.target.getAttribute('data-fileEntryId');

			<%= itemSelectedCallback %>('<%= FileEntry.class.getName() %>', fileEntryId);
		}
	);
</aui:script>