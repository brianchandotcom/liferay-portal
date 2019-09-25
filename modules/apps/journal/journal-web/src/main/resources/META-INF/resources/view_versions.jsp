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

<liferay-ui:search-container
	emptyResultsMessage="no-web-content-was-found"
	searchContainer="<%= journalDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.journal.model.JournalArticle"
		modelVar="articleVersion"
	>

		<%
		row.setPrimaryKey(articleVersion.getArticleId() + JournalPortlet.VERSION_SEPARATOR + articleVersion.getVersion());
		%>

		<c:choose>
			<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "descriptive") %>'>
				<liferay-ui:search-container-column-text>
					<liferay-ui:user-portrait
						userId="<%= articleVersion.getUserId() %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>

					<%
					Date createDate = articleVersion.getModifiedDate();

					String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
					%>

					<h6 class="text-default">
						<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(articleVersion.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
					</h6>

					<h5>
						<%= HtmlUtil.escape(articleVersion.getTitle(locale)) %>
					</h5>

					<h6 class="text-default">
						<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= articleVersion.getStatus() %>" version="<%= String.valueOf(articleVersion.getVersion()) %>" />
					</h6>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						defaultEventHandler="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
						dropdownItems="<%= journalDisplayContext.getArticleVersionActionDropdownItems(articleVersion) %>"
					/>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "icon") %>'>

				<%
				row.setCssClass("entry-card lfr-asset-item");
				%>

				<liferay-ui:search-container-column-text>
					<clay:vertical-card
						verticalCard="<%= new JournalArticleVersionVerticalCard(articleVersion, renderRequest, renderResponse, searchContainer.getRowChecker(), assetDisplayPageFriendlyURLProvider, trashHelper) %>"
					/>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "list") %>'>
				<liferay-ui:search-container-column-text
					name="id"
					value="<%= HtmlUtil.escape(articleVersion.getArticleId()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="title"
					value="<%= HtmlUtil.escape(articleVersion.getTitle(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					name="version"
					orderable="<%= true %>"
				/>

				<liferay-ui:search-container-column-status
					name="status"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					orderable="<%= true %>"
					property="modifiedDate"
				/>

				<c:if test="<%= articleVersion.getDisplayDate() != null %>">
					<liferay-ui:search-container-column-date
						name="display-date"
						orderable="<%= true %>"
						property="displayDate"
					/>
				</c:if>

				<liferay-ui:search-container-column-text
					name="author"
					value="<%= HtmlUtil.escape(PortalUtil.getUserName(articleVersion)) %>"
				/>

				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						defaultEventHandler="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
						dropdownItems="<%= journalDisplayContext.getArticleVersionActionDropdownItems(articleVersion) %>"
					/>
				</liferay-ui:search-container-column-text>
			</c:when>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="<%= journalDisplayContext.getDisplayStyle() %>"
		markupView="lexicon"
		searchContainer="<%= searchContainer %>"
	/>
</liferay-ui:search-container>

<liferay-frontend:component
	componentId="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
	context="<%= journalDisplayContext.getComponentContext() %>"
	module="js/ElementsDefaultEventHandler.es"
/>