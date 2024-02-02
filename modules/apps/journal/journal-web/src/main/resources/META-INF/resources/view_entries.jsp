<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

Map<String, Object> componentContext = journalDisplayContext.getComponentContext();
%>

<liferay-ui:search-container
	cssClass='<%= journalDisplayContext.isSearch() ? "pt-0" : StringPool.BLANK %>'
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
					).setMVCRenderCommandName(
						"/journal/edit_article"
					).setRedirect(
						currentURL
					).setParameter(
						"articleId", curArticle.getArticleId()
					).setParameter(
						"backURLTitle", portletDisplay.getPortletDisplayName()
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
						<liferay-ui:search-container-column-icon
							icon="web-content"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-text
							colspan="<%= 2 %>"
						>
							<div class="d-flex">
								<c:choose>
									<c:when test="<%= editURL != StringPool.BLANK %>">
										<clay:link
											cssClass="d-block lfr-portal-tooltip text-dark text-truncate"
											href="<%= editURL %>"
											label="<%= title %>"
											title="<%= HtmlUtil.escape(title) %>"
										/>
									</c:when>
									<c:otherwise>
										<span class="d-block lfr-portal-tooltip text-dark text-truncate" title="<%= HtmlUtil.escape(title) %>">
											<%= HtmlUtil.escape(title) %>
										</span>
									</c:otherwise>
								</c:choose>
							</div>

							<span class="c-pt-1 text-secondary">
								<c:choose>
									<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-202534") && journalDisplayContext.isNavigationMine() %>'>

										<%
										Date createDate = curArticle.getCreateDate();

										String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
										%>

										<liferay-ui:message arguments="<%= new String[] {modifiedDateDescription, HtmlUtil.escape(curArticle.getUserName())} %>" key="created-x-ago-by-x" />
									</c:when>
									<c:otherwise>

										<%
										Date modifiedDate = curArticle.getModifiedDate();

										String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
										%>

										<liferay-ui:message arguments="<%= new String[] {modifiedDateDescription, HtmlUtil.escape(curArticle.getStatusByUserName())} %>" key="modified-x-ago-by-x" />
									</c:otherwise>
								</c:choose>
							</span>

							<c:if test="<%= journalDisplayContext.isSearch() && ((curArticle.getFolderId() <= 0) || JournalFolderPermission.contains(permissionChecker, curArticle.getFolder(), ActionKeys.VIEW)) %>">
								<c:choose>
									<c:when test="<%= curArticle.getFolderId() != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
										<liferay-site-navigation:breadcrumb
											breadcrumbEntries="<%= JournalPortletUtil.getPortletBreadcrumbEntries(curArticle.getFolder(), request, true, liferayPortletResponse) %>"
											cssClass="c-pl-0 c-pt-0"
										/>
									</c:when>
									<c:otherwise>
										<liferay-site-navigation:breadcrumb
											breadcrumbEntries="<%= JournalPortletUtil.getPortletBreadcrumbEntries(null, request, true, liferayPortletResponse) %>"
											cssClass="c-pl-0 c-pt-0"
										/>
									</c:otherwise>
								</c:choose>
							</c:if>

							<span class="align-items-center d-flex text-default">
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

							<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-198959") && curArticle.isScheduled() %>'>

								<%
								String scheduledArticleMessage = journalDisplayContext.getScheduledArticleMessage(curArticle);
								%>

									<span
										aria-label='<%= scheduledArticleMessage %>'
										class="icon-tooltip lfr-portal-tooltip"
										title='<%= scheduledArticleMessage %>'
									>
										<clay:icon
											cssClass="mt-0"
											symbol="question-circle-full"
										/>
								</span>
							</c:if>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								aria-label='<%= LanguageUtil.format(request, "actions-for-x", HtmlUtil.escape(title), false) %>'
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
								verticalCard="<%= new JournalArticleVerticalCard(curArticle, renderRequest, renderResponse, searchContainer.getRowChecker(), assetDisplayPageFriendlyURLProvider, trashHelper, journalDisplayContext.isNavigationMine()) %>"
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

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-cell-minw-200 table-title"
							name="title"
						>
							<div class="autofit-row">
								<div class="autofit-col pr-1">
									<clay:sticker
										cssClass="sticker-document"
										displayType="secondary"
										icon="web-content"
									/>
								</div>

								<div class="autofit-col autofit-col-expand pl-1">
									<div class="table-title">
										<clay:link
											href="<%= editURL %>"
											label="<%= title %>"
										/>
									</div>
								</div>
							</div>
						</liferay-ui:search-container-column-text>

						<c:if test='<%= !FeatureFlagManagerUtil.isEnabled("LPS-194763") %>'>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 text-truncate"
								name="description"
								value="<%= StringUtil.shorten(HtmlUtil.stripHtml(curArticle.getDescription(locale)), 200) %>"
							/>
						</c:if>

						<c:if test="<%= journalDisplayContext.isSearch() && ((curArticle.getFolderId() <= 0) || JournalFolderPermission.contains(permissionChecker, curArticle.getFolder(), ActionKeys.VIEW)) %>">
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-200"
								name="path"
							>
								<c:choose>
									<c:when test="<%= curArticle.getFolderId() != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
										<liferay-site-navigation:breadcrumb
											breadcrumbEntries="<%= JournalPortletUtil.getPortletBreadcrumbEntries(curArticle.getFolder(), request, true, liferayPortletResponse) %>"
											cssClass="c-pl-0 c-pt-0"
										/>
									</c:when>
									<c:otherwise>
										<liferay-site-navigation:breadcrumb
											breadcrumbEntries="<%= JournalPortletUtil.getPortletBreadcrumbEntries(null, request, true, liferayPortletResponse) %>"
											cssClass="c-pl-0 c-pt-0"
										/>
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>
						</c:if>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smallest table-cell-minw-100"
							name="author"
							value="<%= HtmlUtil.escape(curArticle.getUserName()) %>"
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

						<c:choose>
							<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-194763") %>'>
								<c:if test="<%= !journalDisplayContext.isHighlightedDDMStructure() %>">

									<%
									DDMStructure ddmStructure = curArticle.getDDMStructure();
									%>

									<liferay-ui:search-container-column-text
										cssClass="table-cell-expand-smallest table-cell-minw-100"
										name="type"
										value="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
									/>
								</c:if>

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

								<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-202534") %>'>
									<liferay-ui:search-container-column-date
										cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
										name="create-date"
										value="<%= curArticle.getCreateDate() %>"
									/>
								</c:if>
							</c:when>
							<c:otherwise>
								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-smallest"
									name="modified"
								>

									<%
									Date modifiedDate = curArticle.getModifiedDate();

									String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
									%>

									<liferay-ui:message arguments="<%= new String[] {modifiedDateDescription, HtmlUtil.escape(curArticle.getStatusByUserName())} %>" key="modified-x-ago-by-x" />
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-date
									cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
									name="display-date"
									value="<%= curArticle.getDisplayDate() %>"
								/>

								<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-202534") %>'>
									<liferay-ui:search-container-column-date
										cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
										name="create-date"
										value="<%= curArticle.getCreateDate() %>"
									/>
								</c:if>

								<%
								DDMStructure ddmStructure = curArticle.getDDMStructure();
								%>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-smallest table-cell-minw-100"
									name="type"
									value="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
								/>
							</c:otherwise>
						</c:choose>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
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

							<div class="d-flex">
								<c:choose>
									<c:when test="<%= rowURL.toString() != StringPool.BLANK %>">
										<clay:link
											cssClass="d-block lfr-portal-tooltip text-dark text-truncate"
											href="<%= rowURL.toString() %>"
											label="<%= HtmlUtil.escape(curFolder.getName()) %>"
											title="<%= HtmlUtil.escape(curFolder.getName()) %>"
										/>
									</c:when>
									<c:otherwise>
										<span class="d-block lfr-portal-tooltip text-dark text-truncate" title="<%= HtmlUtil.escape(curFolder.getName()) %>">
											<%= HtmlUtil.escape(curFolder.getName()) %>
										</span>
									</c:otherwise>
								</c:choose>
							</div>

							<span class="c-pt-1 text-secondary">
								<liferay-ui:message arguments="<%= new String[] {createDateDescription, HtmlUtil.escape(curFolder.getUserName())} %>" key="modified-x-ago-by-x" />
							</span>

							<c:if test="<%= journalDisplayContext.isSearch() && ((curFolder.getParentFolderId() <= 0) || JournalFolderPermission.contains(permissionChecker, curFolder.getParentFolder(), ActionKeys.VIEW)) %>">
								<liferay-site-navigation:breadcrumb
									breadcrumbEntries="<%= JournalPortletUtil.getPortletBreadcrumbEntries(curFolder.getParentFolder(), request, true, liferayPortletResponse) %>"
									cssClass="c-pl-0 c-pt-0"
								/>
							</c:if>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
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
								additionalProps='<%=
									HashMapBuilder.<String, Object>put(
										"trashEnabled", componentContext.get("trashEnabled")
									).build()
								%>'
								horizontalCard="<%= new JournalFolderHorizontalCard(curFolder, journalDisplayContext.getDisplayStyle(), renderRequest, renderResponse, searchContainer.getRowChecker(), trashHelper) %>"
								propsTransformer="js/ElementsDefaultPropsTransformer"
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
							name="title"
						>
							<div class="autofit-row">
								<div class="autofit-col pr-1">
									<clay:sticker
										cssClass="sticker-document"
										displayType="secondary"
										icon="folder"
									/>
								</div>

								<div class="autofit-col autofit-col-expand pl-1">
									<div class="table-title">
										<clay:link
											href="<%= rowURL.toString() %>"
											label="<%= HtmlUtil.escape(curFolder.getName()) %>"
										/>
									</div>
								</div>
							</div>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-cell-minw-200 text-truncate"
							name="description"
							value="<%= HtmlUtil.escape(curFolder.getDescription()) %>"
						/>

						<c:if test="<%= journalDisplayContext.isSearch() && ((curFolder.getParentFolderId() <= 0) || JournalFolderPermission.contains(permissionChecker, curFolder.getParentFolder(), ActionKeys.VIEW)) %>">
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-200"
								name="path"
							>
								<liferay-site-navigation:breadcrumb
									breadcrumbEntries="<%= JournalPortletUtil.getPortletBreadcrumbEntries(curFolder.getParentFolder(), request, true, liferayPortletResponse) %>"
									cssClass="c-pl-0 c-pt-0"
								/>
							</liferay-ui:search-container-column-text>
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

						<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-202534") %>'>
							<liferay-ui:search-container-column-date
								cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
								name="create-date"
								value="<%= curFolder.getCreateDate() %>"
							/>
						</c:if>

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
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
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
		resultRowSplitter='<%= Objects.equals(journalDisplayContext.getDisplayStyle(), "icon") ? new JournalResultRowSplitter() : null %>'
		searchContainer="<%= searchContainer %>"
	/>
</liferay-ui:search-container>

<portlet:renderURL var="moveEntryURL">
	<portlet:param name="mvcPath" value="/move_articles_and_folders.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<portlet:actionURL var="editEntryURL" />

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"editEntryURL", editEntryURL
		).put(
			"moveEntryURL", moveEntryURL
		).put(
			"searchContainerId", "articles"
		).build()
	%>'
	module="js/Navigation"
	servletContext="<%= application %>"
/>