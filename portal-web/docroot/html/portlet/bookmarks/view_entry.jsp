<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

BookmarksEntry entry = (BookmarksEntry)request.getAttribute(WebKeys.BOOKMARKS_ENTRY);

entry = entry.toEscapedModel();

long entryId = entry.getEntryId();

BookmarksFolder folder = entry.getFolder();

AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.getEntry(BookmarksEntry.class.getName(), entry.getEntryId());

request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);

request.setAttribute("view_entry.jsp-entry", entry);

BookmarksUtil.addPortletBreadcrumbEntries(entry, request, renderResponse);
%>

<liferay-util:include page="/html/portlet/bookmarks/top_links.jsp" />

<c:choose>
	<c:when test="<%= Validator.isNull(redirect) %>">
		<portlet:renderURL var="backURL">
			<portlet:param name="struts_action" value="/bookmarks/view" />
			<portlet:param name="folderId" value="<%= (folder != null) ? String.valueOf(folder.getFolderId()) : String.valueOf(BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
		</portlet:renderURL>

		<liferay-ui:header
			backLabel='<%= (folder != null) ? folder.getName() : "home" %>'
			backURL="<%= backURL.toString() %>"
			escapeXml="<%= false %>"
			localizeTitle="<%= false %>"
			title="<%= entry.getName() %>"
		/>
	</c:when>
	<c:otherwise>
		<liferay-ui:header
			backURL="<%= redirect %>"
			escapeXml="<%= false %>"
			localizeTitle="<%= false %>"
			title="<%= entry.getName() %>"
		/>
	</c:otherwise>
</c:choose>

<aui:row>
	<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= 75 %>">
		<div class="lfr-asset-categories">
			<liferay-ui:asset-categories-summary
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
			/>
		</div>

		<div class="lfr-asset-tags">
			<liferay-ui:asset-tags-summary
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
				message="tags"
			/>
		</div>

		<div class="lfr-asset-url">
			<a href="<%= themeDisplay.getPathMain() %>/bookmarks/open_entry?entryId=<%= entry.getEntryId() %>"><%= entry.getUrl() %></a>
		</div>

		<div class="lfr-asset-description">
			<%= entry.getDescription() %>
		</div>

		<liferay-ui:custom-attributes-available className="<%= BookmarksEntry.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<div class="lfr-asset-metadata">
			<div class="lfr-asset-icon lfr-asset-author">
				<%= LanguageUtil.format(pageContext, "created-by-x", HtmlUtil.escape(PortalUtil.getUserName(entry.getUserId(), themeDisplay.getScopeGroupName())), false) %>
			</div>

			<div class="lfr-asset-icon lfr-asset-date">
				<%= dateFormatDate.format(entry.getCreateDate()) %>
			</div>

			<div class="lfr-asset-icon lfr-asset-downloads last">
				<%= entry.getVisits() %> <liferay-ui:message key="visits" />
			</div>
		</div>

		<c:if test="<%= enableRelatedAssets %>">
			<div class="entry-links">
				<liferay-ui:asset-links
					assetEntryId="<%= layoutAssetEntry.getEntryId() %>"
				/>
			</div>
		</c:if>

		<div class="lfr-asset-ratings">
			<liferay-ui:ratings
				className="<%= BookmarksEntry.class.getName() %>"
				classPK="<%= entryId %>"
			/>
		</div>
	</aui:col>

	<aui:col cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>" width="<%= 25 %>">
		<div class="lfr-asset-summary">
			<liferay-ui:icon
				cssClass="lfr-asset-avatar"
				image="../file_system/large/bookmark"
				message="visit"
				method="get"
				url="<%= entry.getUrl() %>"
			/>

			<div class="lfr-asset-name">
				<a href="<%= entry.getUrl() %>">
					<%= entry.getName() %>
				</a>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		%>

		<liferay-util:include page="/html/portlet/bookmarks/entry_action.jsp" />
	</aui:col>
</aui:row>