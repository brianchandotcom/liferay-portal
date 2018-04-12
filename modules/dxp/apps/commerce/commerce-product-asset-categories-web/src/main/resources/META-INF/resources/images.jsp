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
AssetCategory assetCategory = (AssetCategory)request.getAttribute(WebKeys.ASSET_CATEGORY);

CPAttachmentFileEntryService cpAttachmentFileEntryService = (CPAttachmentFileEntryService)request.getAttribute("cpAttachmentFileEntryService");

PortletURL portletURL = currentURLObj;

portletURL.setParameter("historyKey", renderResponse.getNamespace() + "images");

SearchContainer<CPAttachmentFileEntry> cpAttachmentFileEntrySearchContainer = new SearchContainer<>(liferayPortletRequest, portletURL, null, null);

int cpAttachmentFileEntriesCount = cpAttachmentFileEntryService.getCPAttachmentFileEntriesCount(PortalUtil.getClassNameId(AssetCategory.class), assetCategory.getCategoryId(), CPAttachmentFileEntryConstants.TYPE_IMAGE, WorkflowConstants.STATUS_ANY);

List<CPAttachmentFileEntry> cpAttachmentFileEntries = cpAttachmentFileEntryService.getCPAttachmentFileEntries(PortalUtil.getClassNameId(AssetCategory.class), assetCategory.getCategoryId(), CPAttachmentFileEntryConstants.TYPE_IMAGE, WorkflowConstants.STATUS_ANY, cpAttachmentFileEntrySearchContainer.getStart(), cpAttachmentFileEntrySearchContainer.getEnd());

cpAttachmentFileEntrySearchContainer.setTotal(cpAttachmentFileEntriesCount);
cpAttachmentFileEntrySearchContainer.setResults(cpAttachmentFileEntries);
%>

<liferay-frontend:management-bar
	searchContainerId="cpAttachmentFileEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>

		<liferay-portlet:renderURL var="addAttachmentFileEntryURL">
			<portlet:param name="mvcRenderCommandName" value="editAssetCategoryCPAttachmentFileEntry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="assetCategoryId" value="<%= String.valueOf(assetCategory.getCategoryId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "add-image") %>'
				url="<%= addAttachmentFileEntryURL.toString() %>"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />attachmentFileEntriesContainer">
	<div class="product-attachments-container" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			emptyResultsMessage="there-are-no-images"
			id="cpAttachmentFileEntries"
			searchContainer="<%= cpAttachmentFileEntrySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.product.model.CPAttachmentFileEntry"
				cssClass="entry-display-style"
				keyProperty="CPAttachmentFileEntryId"
				modelVar="cpAttachmentFileEntry"
			>

				<%
				FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

				String thumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, themeDisplay);
				%>

				<c:choose>
					<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
						<liferay-ui:search-container-column-image
							cssClass="table-cell-content"
							name="image"
							src="<%= thumbnailSrc %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-icon
							icon="documents-and-media"
						/>
					</c:otherwise>
				</c:choose>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="title"
					value="<%= HtmlUtil.escape(cpAttachmentFileEntry.getTitle(languageId)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="extension"
					value="<%= HtmlUtil.escape(fileEntry.getExtension()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="priority"
				/>

				<liferay-ui:search-container-column-status
					cssClass="table-cell-content"
					name="status"
					status="<%= cpAttachmentFileEntry.getStatus() %>"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="modified-date"
					property="modifiedDate"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="display-date"
					property="displayDate"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/image_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</div>
</div>