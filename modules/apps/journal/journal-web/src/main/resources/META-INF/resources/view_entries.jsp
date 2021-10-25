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

Map<String, Object> componentContext = journalDisplayContext.getComponentContext();
%>

<liferay-ui:search-container
	emptyResultsMessage="no-web-content-was-found"
	id="articles"
	searchContainer="<%= journalDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="Object"
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
				String title = curArticle.getTitle(locale);

				if (Validator.isNull(title)) {
					title = curArticle.getTitle(LocaleUtil.fromLanguageId(curArticle.getDefaultLanguageId()));
				}

				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", journalDisplayContext.getAvailableActions(curArticle)
					).put(
						"draggable", !BrowserSnifferUtil.isMobile(request) && (JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE))
					).put(
						"title", HtmlUtil.escape(title)
					).build());

				row.setPrimaryKey(HtmlUtil.escape(curArticle.getArticleId()));

				String editURL = StringPool.BLANK;

				if (JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE)) {
					editURL = PortletURLBuilder.createRenderURL(
						liferayPortletResponse
					).setMVCPath(
						"/edit_article.jsp"
					).setRedirect(
						currentURL
					).setParameter(
						"articleId", curArticle.getArticleId()
					).setParameter(
						"folderId", curArticle.getFolderId()
					).setParameter(
						"groupId", curArticle.getGroupId()
					).setParameter(
						"referringPortletResource", referringPortletResource
					).setParameter(
						"version", curArticle.getVersion()
					).buildString();
				}
				%>

				<c:choose>
					<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "descriptive") %>'>

						<%
						List<JournalArticle> articles = JournalArticleLocalServiceUtil.getArticles(curArticle.getGroupId(), curArticle.getArticleId(), 0, 1, new ArticleVersionComparator(true));

						JournalArticle article = articles.get(0);
						%>

						<liferay-ui:search-container-column-text>
							<liferay-ui:user-portrait
								userId="<%= article.getUserId() %>"
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
								<liferay-ui:message arguments="<%= new String[] {modifiedDateDescription, HtmlUtil.escape(curArticle.getUserName())} %>" key="modified-x-ago-by-x" />
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
									<clay:label
										displayType="success"
										label="approved"
									/>
								</c:if>

								<clay:label
									displayType="<%= WorkflowConstants.getStatusStyle(curArticle.getStatus()) %>"
									label="<%= WorkflowConstants.getStatusLabel(curArticle.getStatus()) %>"
								/>
							</span>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								dropdownItems="<%= journalDisplayContext.getArticleActionDropdownItems(curArticle) %>"
								propsTransformer="js/ElementsDefaultPropsTransformer"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "icon") %>'>
						<liferay-ui:search-container-column-text>
							<clay:vertical-card
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								propsTransformer="js/ElementsDefaultPropsTransformer"
								verticalCard="<%= new JournalArticleVerticalCard(curArticle, renderRequest, renderResponse, searchContainer.getRowChecker(), assetDisplayPageFriendlyURLProvider, trashHelper) %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:otherwise>
						<c:if test="<%= !journalWebConfiguration.journalArticleForceAutogenerateId() || journalWebConfiguration.journalArticleShowId() %>">
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
							cssClass="table-cell-expand table-cell-minw-200 text-truncate"
							name="description"
							value="<%= StringUtil.shorten(HtmlUtil.stripHtml(curArticle.getDescription(locale)), 200) %>"
						/>

						<c:if test="<%= journalDisplayContext.isSearch() %>">
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-200"
								name="path"
								value="<%= JournalHelperUtil.getAbsolutePath(liferayPortletRequest, curArticle.getFolderId()) %>"
							/>
						</c:if>

						<%
						List<JournalArticle> articles = JournalArticleLocalServiceUtil.getArticles(curArticle.getGroupId(), curArticle.getArticleId(), 0, 1, new ArticleVersionComparator(true));
						%>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smallest table-cell-minw-100"
							name="author"
							value="<%= HtmlUtil.escape(PortalUtil.getUserName(articles.get(0))) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="text-nowrap"
							name="status"
						>
							<c:if test="<%= !curArticle.isApproved() && curArticle.hasApprovedVersion() %>">
								<clay:label
									displayType="success"
									label="approved"
								/>
							</c:if>

							<clay:label
								displayType="<%= WorkflowConstants.getStatusStyle(curArticle.getStatus()) %>"
								label="<%= WorkflowConstants.getStatusLabel(curArticle.getStatus()) %>"
							/>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smallest"
							name="modified"
						>

							<%
							Date createDate = curArticle.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
							%>

							<liferay-ui:message arguments="<%= new String[] {modifiedDateDescription, HtmlUtil.escape(curArticle.getUserName())} %>" key="modified-x-ago-by-x" />
						</liferay-ui:search-container-column-text>

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
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								dropdownItems="<%= journalDisplayContext.getArticleActionDropdownItems(curArticle) %>"
								propsTransformer="js/ElementsDefaultPropsTransformer"
							/>
						</liferay-ui:search-container-column-text>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="<%= curFolder != null %>">

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", journalDisplayContext.getAvailableActions(curFolder)
					).put(
						"draggable", !BrowserSnifferUtil.isMobile(request) && (JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE))
					).put(
						"folder", true
					).put(
						"folder-id", curFolder.getFolderId()
					).put(
						"title", HtmlUtil.escape(curFolder.getName())
					).build());
				row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));

				PortletURL rowURL = PortletURLBuilder.createRenderURL(
					liferayPortletResponse
				).setParameter(
					"displayStyle", journalDisplayContext.getDisplayStyle()
				).setParameter(
					"folderId", curFolder.getFolderId()
				).setParameter(
					"groupId", curFolder.getGroupId()
				).buildPortletURL();
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

							<c:if test="<%= journalDisplayContext.isSearch() && ((curFolder.getParentFolderId() <= 0) || JournalFolderPermission.contains(permissionChecker, curFolder.getParentFolder(), ActionKeys.VIEW)) %>">
								<h5>
									<%= JournalHelperUtil.getAbsolutePath(liferayPortletRequest, curFolder.getParentFolderId()) %>
								</h5>
							</c:if>

							<span class="text-default">
								<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= curFolder.getStatus() %>" />
							</span>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								dropdownItems="<%= journalDisplayContext.getFolderActionDropdownItems(curFolder) %>"
								propsTransformer="js/ElementsDefaultPropsTransformer"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:when test='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "icon") %>'>

						<%
						row.setCssClass("card-page-item card-page-item-directory " + row.getCssClass());
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
						<c:if test="<%= !journalWebConfiguration.journalArticleForceAutogenerateId() || journalWebConfiguration.journalArticleShowId() %>">
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
							cssClass="table-cell-expand table-cell-minw-200 text-truncate"
							name="description"
							value="<%= HtmlUtil.escape(curFolder.getDescription()) %>"
						/>

						<c:if test="<%= journalDisplayContext.isSearch() %>">
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-200"
								name="path"
								value="<%= JournalHelperUtil.getAbsolutePath(liferayPortletRequest, curFolder.getParentFolderId()) %>"
							/>
						</c:if>

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
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								dropdownItems="<%= journalDisplayContext.getFolderActionDropdownItems(curFolder) %>"
								propsTransformer="js/ElementsDefaultPropsTransformer"
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

<aui:script use="liferay-journal-navigation">
	var journalNavigation = new Liferay.Portlet.JournalNavigation({
		editEntryUrl: '<portlet:actionURL />',
		form: {
			method: 'POST',
			node: A.one(document.<portlet:namespace />fm),
		},
		moveEntryUrl:
			'<portlet:renderURL><portlet:param name="mvcPath" value="/move_articles_and_folders.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>',
		namespace: '<portlet:namespace />',
		searchContainerId: 'articles',
	});

	var clearJournalNavigationHandles = function (event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			journalNavigation.destroy();

			Liferay.detach('destroyPortlet', clearJournalNavigationHandles);
		}
	};

	Liferay.on('destroyPortlet', clearJournalNavigationHandles);
</aui:script>