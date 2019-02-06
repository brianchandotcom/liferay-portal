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
JournalArticle article = journalDisplayContext.getArticle();
%>

<c:choose>
	<c:when test="<%= article == null %>">
		<div class="alert alert-danger">
			<liferay-ui:message key="the-selected-web-content-no-longer-exists" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		JournalHistoryDisplayContext journalHistoryDisplayContext = new JournalHistoryDisplayContext(renderRequest, renderResponse, journalDisplayContext.getArticle());

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(journalHistoryDisplayContext.getBackURL());

		renderResponse.setTitle(article.getTitle(locale));
		%>

		<clay:navigation-bar
			inverted="<%= true %>"
			navigationItems="<%= journalHistoryDisplayContext.getNavigationItems() %>"
		/>

		<clay:management-toolbar
			actionDropdownItems="<%= journalHistoryDisplayContext.getActionItemsDropdownItems() %>"
			componentId="journalHistoryManagementToolbar"
			filterDropdownItems="<%= journalHistoryDisplayContext.getFilterItemsDropdownItems() %>"
			itemsTotal="<%= journalHistoryDisplayContext.getTotalItems() %>"
			searchContainerId="articleVersions"
			showSearch="<%= false %>"
			sortingOrder="<%= journalHistoryDisplayContext.getOrderByType() %>"
			sortingURL="<%= journalHistoryDisplayContext.getSortingURL() %>"
			viewTypeItems="<%= journalHistoryDisplayContext.getViewTypeItems() %>"
		/>

		<%
		PortletURL portletURL = journalHistoryDisplayContext.getPortletURL();
		%>

		<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
			<aui:input name="referringPortletResource" type="hidden" value="<%= journalHistoryDisplayContext.getReferringPortletResource() %>" />
			<aui:input name="groupId" type="hidden" value="<%= String.valueOf(article.getGroupId()) %>" />

			<liferay-ui:search-container
				id="articleVersions"
				searchContainer="<%= journalHistoryDisplayContext.getArticleSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.journal.model.JournalArticle"
					modelVar="articleVersion"
				>

					<%
					row.setPrimaryKey(articleVersion.getArticleId() + JournalPortlet.VERSION_SEPARATOR + articleVersion.getVersion());
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(journalHistoryDisplayContext.getDisplayStyle(), "descriptive") %>'>
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
									dropdownItems="<%= journalDisplayContext.getArticleHistoryActionDropdownItems(articleVersion) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test='<%= Objects.equals(journalHistoryDisplayContext.getDisplayStyle(), "icon") %>'>

							<%
							row.setCssClass("entry-card lfr-asset-item");
							%>

							<liferay-ui:search-container-column-text>
								<clay:vertical-card
									verticalCard="<%= new JournalArticleHistoryVerticalCard(articleVersion, renderRequest, renderResponse, searchContainer.getRowChecker(), trashHelper) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test='<%= Objects.equals(journalHistoryDisplayContext.getDisplayStyle(), "list") %>'>
							<liferay-ui:search-container-column-text
								name="id"
								value="<%= HtmlUtil.escape(articleVersion.getArticleId()) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 table-title"
								name="title"
								value="<%= HtmlUtil.escape(articleVersion.getTitle(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-100"
								name="version"
								orderable="<%= true %>"
							/>

							<liferay-ui:search-container-column-status
								name="status"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
								name="modified-date"
								orderable="<%= true %>"
								property="modifiedDate"
							/>

							<c:if test="<%= article.getDisplayDate() != null %>">
								<liferay-ui:search-container-column-date
									cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
									name="display-date"
									orderable="<%= true %>"
									property="displayDate"
								/>
							</c:if>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-100"
								name="author"
								value="<%= HtmlUtil.escape(PortalUtil.getUserName(articleVersion)) %>"
							/>

							<liferay-ui:search-container-column-text>
								<clay:dropdown-actions
									defaultEventHandler="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
									dropdownItems="<%= journalDisplayContext.getArticleHistoryActionDropdownItems(articleVersion) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
					</c:choose>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= journalHistoryDisplayContext.getDisplayStyle() %>"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>

		<liferay-frontend:component
			componentId="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
			context="<%= journalDisplayContext.getComponentContext() %>"
			module="js/ElementsDefaultEventHandler.es"
		/>

		<aui:script>
			var ACTIONS = {};

			<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
				ACTIONS.deleteArticles = function() {
					if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-version") %>')) {
						var form = AUI.$(document.<portlet:namespace />fm);

						submitForm(form, '<portlet:actionURL name="deleteArticles"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
					}
				};
			</c:if>

			<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
				ACTIONS.expireArticles = function() {
					if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-expire-the-selected-version") %>')) {
						var form = AUI.$(document.<portlet:namespace />fm);

						submitForm(form, '<portlet:actionURL name="expireArticles"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
					}
				};
			</c:if>

			Liferay.componentReady('journalHistoryManagementToolbar').then(
				function(managementToolbar) {
					managementToolbar.on(
						'actionItemClicked',
						function(event) {
							var itemData = event.data.item.data;

							if (itemData && itemData.action && ACTIONS[itemData.action]) {
								ACTIONS[itemData.action]();
							}
						}
					);
				}
			);
		</aui:script>
	</c:otherwise>
</c:choose>