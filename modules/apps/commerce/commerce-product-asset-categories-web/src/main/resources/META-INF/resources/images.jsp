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

PortletURL portletURL = PortletURLBuilder.create(
	currentURLObj
).setParameter(
	"historyKey", liferayPortletResponse.getNamespace() + "images"
).build();

SearchContainer<CPAttachmentFileEntry> cpAttachmentFileEntrySearchContainer = new SearchContainer<>(liferayPortletRequest, portletURL, null, null);

List<CPAttachmentFileEntry> cpAttachmentFileEntries = cpAttachmentFileEntryService.getCPAttachmentFileEntries(PortalUtil.getClassNameId(AssetCategory.class), assetCategory.getCategoryId(), CPAttachmentFileEntryConstants.TYPE_IMAGE, WorkflowConstants.STATUS_ANY, cpAttachmentFileEntrySearchContainer.getStart(), cpAttachmentFileEntrySearchContainer.getEnd());

cpAttachmentFileEntrySearchContainer.setTotal(cpAttachmentFileEntryService.getCPAttachmentFileEntriesCount(PortalUtil.getClassNameId(AssetCategory.class), assetCategory.getCategoryId(), CPAttachmentFileEntryConstants.TYPE_IMAGE, WorkflowConstants.STATUS_ANY));
cpAttachmentFileEntrySearchContainer.setResults(cpAttachmentFileEntries);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new CategoryCPAttachmentFileEntriesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, cpAttachmentFileEntrySearchContainer) %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-images"
		id="cpAttachmentFileEntries"
		searchContainer="<%= cpAttachmentFileEntrySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPAttachmentFileEntry"
			keyProperty="CPAttachmentFileEntryId"
			modelVar="cpAttachmentFileEntry"
		>

			<%
			FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

			String thumbnailSrc = CommerceMediaResolverUtil.getThumbnailUrl(cpAttachmentFileEntry.getCPAttachmentFileEntryId());
			%>

			<c:choose>
				<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
					<liferay-ui:search-container-column-image
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
				cssClass="table-cell-expand"
				name="title"
				value="<%= HtmlUtil.escape(cpAttachmentFileEntry.getTitle(languageId)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				name="extension"
				value="<%= HtmlUtil.escape(fileEntry.getExtension()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				property="priority"
			/>

			<liferay-ui:search-container-column-status
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				name="status"
				status="<%= cpAttachmentFileEntry.getStatus() %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				name="modified-date"
				property="modifiedDate"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				name="display-date"
				property="displayDate"
			/>

			<liferay-ui:search-container-column-jsp
				path="/image_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>