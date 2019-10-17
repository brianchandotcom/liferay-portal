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
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
%>

<liferay-ui:search-container
	emptyResultsMessage="no-web-content-was-found"
	id="articles"
	searchContainer="<%= journalDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="Object"
		cssClass="entry-display-style"
		modelVar="object"
	>

		<%
		JournalArticle curArticle = null;
		JournalFolder curFolder = null;

		Object result = row.getObject();

		if (result instanceof JournalFolder) {
			curFolder = (JournalFolder)result;
		}
		else {
			curArticle = journalDisplayContext.getLatestArticle((JournalArticle)result);
		}
		%>

		<c:choose>
			<c:when test="<%= curArticle != null %>">

				<%
				Map<String, Object> rowData = new HashMap<String, Object>();

				rowData.put("actions", journalDisplayContext.getAvailableActions(curArticle));
				rowData.put("draggable", !BrowserSnifferUtil.isMobile(request) && (JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE)));

				String title = curArticle.getTitle(locale);

				if (Validator.isNull(title)) {
					title = curArticle.getTitle(LocaleUtil.fromLanguageId(curArticle.getDefaultLanguageId()));
				}

				rowData.put("title", HtmlUtil.escape(title));

				row.setData(rowData);

				row.setPrimaryKey(HtmlUtil.escape(curArticle.getArticleId()));

				String editURL = StringPool.BLANK;

				if (JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE)) {
					PortletURL editArticleURL = liferayPortletResponse.createRenderURL();

					editArticleURL.setParameter("mvcPath", "/edit_article.jsp");
					editArticleURL.setParameter("redirect", currentURL);
					editArticleURL.setParameter("referringPortletResource", referringPortletResource);
					editArticleURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
					editArticleURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
					editArticleURL.setParameter("articleId", curArticle.getArticleId());
					editArticleURL.setParameter("version", String.valueOf(curArticle.getVersion()));

					editURL = editArticleURL.toString();
				}
				%>

				<c:choose>
					<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "descriptive") %>'>
						<liferay-ui:search-container-column-text>
							<liferay-ui:user-portrait
								userId="<%= curArticle.getUserId() %>"
							/>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							colspan="<%= 2 %>"
						>

							<%
							Date createDate = curArticle.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
							%>

							<span class="text-default">
								<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(curArticle.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
							</span>

							<p class="font-weight-bold h5">
								<aui:a href="<%= editURL %>">
									<%= HtmlUtil.escape(title) %>
								</aui:a>
							</p>

							<c:if test="<%= journalDisplayContext.isSearch() && ((curArticle.getFolderId() <= 0) || JournalFolderPermission.contains(permissionChecker, curArticle.getFolder(), ActionKeys.VIEW)) %>">
								<h5>
									<%= JournalHelperUtil.getAbsolutePath(liferayPortletRequest, curArticle.getFolderId()) %>
								</h5>
							</c:if>

							<span class="text-default">
								<c:if test="<%= !curArticle.isApproved() && curArticle.hasApprovedVersion() %>">
									<span class="label label-success text-uppercase">
										<liferay-ui:message key="approved" />
									</span>
								</c:if>

								<span class="label label-<%= LabelItem.getStyleFromWorkflowStatus(curArticle.getStatus()) %> text-uppercase">
									<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(curArticle.getStatus()) %>" />
								</span>
							</span>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								defaultEventHandler="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
								dropdownItems="<%= journalDisplayContext.getArticleActionDropdownItems(curArticle) %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "icon") %>'>

						<%
						row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
						%>

						<liferay-ui:search-container-column-text>
							<clay:vertical-card
								verticalCard="<%= new JournalArticleVerticalCard(curArticle, renderRequest, renderResponse, searchContainer.getRowChecker(), assetDisplayPageFriendlyURLProvider, trashHelper) %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:otherwise>
						<c:if test="<%= !journalWebConfiguration.journalArticleForceAutogenerateId() %>">
							<liferay-ui:search-container-column-text
								name="id"
								value="<%= HtmlUtil.escape(curArticle.getArticleId()) %>"
							/>
						</c:if>

						<liferay-ui:search-container-column-jsp
							cssClass="table-cell-expand table-cell-minw-200 table-title"
							href="<%= editURL %>"
							name="title"
							path="/article_title.jsp"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-cell-minw-200"
							name="description"
							value="<%= StringUtil.shorten(HtmlUtil.stripHtml(curArticle.getDescription(locale)), 200) %>"
						/>

						<c:if test="<%= journalDisplayContext.isSearch() && ((curArticle.getFolderId() <= 0) || JournalFolderPermission.contains(permissionChecker, curArticle.getFolder(), ActionKeys.VIEW)) %>">
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-200"
								name="path"
								value="<%= JournalHelperUtil.getAbsolutePath(liferayPortletRequest, curArticle.getFolderId()) %>"
							/>
						</c:if>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smallest table-cell-minw-100"
							name="author"
							value="<%= HtmlUtil.escape(PortalUtil.getUserName(curArticle)) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="text-nowrap"
							name="status"
						>
							<c:if test="<%= !curArticle.isApproved() && curArticle.hasApprovedVersion() %>">
								<span class="label label-success text-uppercase">
									<liferay-ui:message key="approved" />
								</span>
							</c:if>

							<span class="label label-<%= LabelItem.getStyleFromWorkflowStatus(curArticle.getStatus()) %> text-uppercase">
								<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(curArticle.getStatus()) %>" />
							</span>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-date
							cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
							name="modified-date"
							value="<%= curArticle.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-date
							cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
							name="display-date"
							value="<%= curArticle.getDisplayDate() %>"
						/>

						<%
						DDMStructure ddmStructure = curArticle.getDDMStructure();
						%>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smallest table-cell-minw-100"
							name="type"
							value="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
						/>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								defaultEventHandler="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
								dropdownItems="<%= journalDisplayContext.getArticleActionDropdownItems(curArticle) %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="<%= curFolder != null %>">

				<%
				Map<String, Object> rowData = new HashMap<String, Object>();

				rowData.put("actions", journalDisplayContext.getAvailableActions(curFolder));
				rowData.put("draggable", !BrowserSnifferUtil.isMobile(request) && (JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE)));
				rowData.put("folder", true);
				rowData.put("folder-id", curFolder.getFolderId());
				rowData.put("title", HtmlUtil.escape(curFolder.getName()));

				row.setData(rowData);
				row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));

				PortletURL rowURL = liferayPortletResponse.createRenderURL();

				rowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
				rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
				rowURL.setParameter("displayStyle", journalDisplayContext.getDisplayStyle());
				%>

				<c:choose>
					<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "descriptive") %>'>
						<liferay-ui:search-container-column-icon
							icon="folder"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-text
							colspan="<%= 2 %>"
						>

							<%
							Date createDate = curFolder.getCreateDate();

							String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
							%>

							<span class="text-default">
								<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(curFolder.getUserName()), createDateDescription} %>" key="x-modified-x-ago" />
							</span>

							<p class="font-weight-bold h5">
								<aui:a href="<%= rowURL.toString() %>">
									<%= HtmlUtil.escape(curFolder.getName()) %>
								</aui:a>
							</p>

							<span class="text-default">
								<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= curFolder.getStatus() %>" />
							</span>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								defaultEventHandler="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
								dropdownItems="<%= journalDisplayContext.getFolderActionDropdownItems(curFolder) %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "icon") %>'>

						<%
						row.setCssClass("entry-card lfr-asset-folder " + row.getCssClass());
						%>

						<liferay-ui:search-container-column-text
							colspan="<%= 2 %>"
						>
							<clay:horizontal-card
								horizontalCard="<%= new JournalFolderHorizontalCard(curFolder, journalDisplayContext.getDisplayStyle(), renderRequest, renderResponse, searchContainer.getRowChecker(), trashHelper) %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:otherwise>
						<c:if test="<%= !journalWebConfiguration.journalArticleForceAutogenerateId() %>">
							<liferay-ui:search-container-column-text
								name="id"
								value="<%= HtmlUtil.escape(String.valueOf(curFolder.getFolderId())) %>"
							/>
						</c:if>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-cell-minw-200 table-list-title"
							href="<%= rowURL.toString() %>"
							name="title"
							value="<%= HtmlUtil.escape(curFolder.getName()) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-cell-minw-200"
							name="description"
							value="<%= HtmlUtil.escape(curFolder.getDescription()) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smallest table-cell-minw-150"
							name="author"
							value="<%= HtmlUtil.escape(PortalUtil.getUserName(curFolder)) %>"
						/>

						<liferay-ui:search-container-column-text
							name="status"
							value="--"
						/>

						<liferay-ui:search-container-column-date
							cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
							name="modified-date"
							value="<%= curFolder.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
							name="display-date"
							value="--"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smallest table-cell-minw-150"
							name="type"
							value='<%= LanguageUtil.get(request, "folder") %>'
						/>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								defaultEventHandler="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
								dropdownItems="<%= journalDisplayContext.getFolderActionDropdownItems(curFolder) %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="<%= journalDisplayContext.getDisplayStyle() %>"
		markupView="lexicon"
		resultRowSplitter="<%= journalDisplayContext.isSearch() ? null : new JournalResultRowSplitter() %>"
		searchContainer="<%= searchContainer %>"
	/>
</liferay-ui:search-container>

<liferay-frontend:component
	componentId="<%= JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
	context="<%= journalDisplayContext.getComponentContext() %>"
	module="js/ElementsDefaultEventHandler.es"
/>

<aui:script use="liferay-journal-navigation">
	var journalNavigation = new Liferay.Portlet.JournalNavigation({
		editEntryUrl: '<portlet:actionURL />',
		form: {
			method: 'POST',
			node: A.one(document.<portlet:namespace />fm)
		},
		moveEntryUrl:
			'<portlet:renderURL><portlet:param name="mvcPath" value="/move_entries.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>',
		namespace: '<portlet:namespace />',
		searchContainerId: 'articles'
	});

	var clearJournalNavigationHandles = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			journalNavigation.destroy();

			Liferay.detach('destroyPortlet', clearJournalNavigationHandles);
		}
	};

	Liferay.on('destroyPortlet', clearJournalNavigationHandles);
</aui:script>