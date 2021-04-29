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

<%@ include file="/item/selector/init.jsp" %>

<%
JournalArticleItemSelectorViewDisplayContext journalArticleItemSelectorViewDisplayContext = (JournalArticleItemSelectorViewDisplayContext)request.getAttribute(JournalWebConstants.JOURNAL_ARTICLE_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new JournalArticleItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, journalArticleItemSelectorViewDisplayContext) %>"
/>

<clay:container-fluid
	cssClass="item-selector lfr-item-viewer"
	id='<%= liferayPortletResponse.getNamespace() + "articlesContainer" %>'
>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= journalArticleItemSelectorViewDisplayContext.getPortletBreadcrumbEntries() %>"
	/>

	<liferay-ui:search-container
		emptyResultsMessage="no-web-content-was-found"
		id="articles"
		searchContainer="<%= journalArticleItemSelectorViewDisplayContext.getSearchContainer() %>"
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
				curArticle = journalArticleItemSelectorViewDisplayContext.getLatestArticle((JournalArticle)result);
			}
			%>

			<c:choose>
				<c:when test="<%= curArticle != null %>">

					<%
					row.setCssClass("articles selector-button" + row.getCssClass());

					row.setData(journalArticleItemSelectorViewDisplayContext.getJournalArticleContext(curArticle));
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(journalArticleItemSelectorViewDisplayContext.getDisplayStyle(), "descriptive") %>'>

							<%
							row.setCssClass("item-preview " + row.getCssClass());
							%>

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
									<%= HtmlUtil.escape(curArticle.getTitle(locale, true)) %>
								</p>

								<c:if test="<%= journalArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
									<h6 class="text-default">
										<liferay-ui:message key="location" />:
										<span class="text-secondary">
											<clay:icon
												symbol="<%= journalArticleItemSelectorViewDisplayContext.getGroupCssIcon(curArticle.getGroupId()) %>"
											/>

											<small><%= journalArticleItemSelectorViewDisplayContext.getGroupLabel(curArticle.getGroupId(), locale) %></small>
										</span>
									</h6>
								</c:if>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test='<%= Objects.equals(journalArticleItemSelectorViewDisplayContext.getDisplayStyle(), "icon") %>'>

							<%
							row.setCssClass("card-page-item card-page-item-directory entry " + row.getCssClass());
							%>

							<liferay-ui:search-container-column-text>
								<clay:vertical-card
									verticalCard="<%= new JournalArticleItemSelectorVerticalCard(curArticle, renderRequest) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>

							<%
							row.setCssClass("item-preview " + row.getCssClass());
							%>

							<c:if test="<%= journalArticleItemSelectorViewDisplayContext.showArticleId() %>">
								<liferay-ui:search-container-column-text
									name="id"
									value="<%= HtmlUtil.escape(curArticle.getArticleId()) %>"
								/>
							</c:if>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 table-title"
								name="title"
								value="<%= curArticle.getTitle(locale, true) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 text-truncate"
								name="description"
								value="<%= StringUtil.shorten(HtmlUtil.stripHtml(curArticle.getDescription(locale)), 200) %>"
							/>

							<c:if test="<%= journalArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
								<liferay-ui:search-container-column-text
									name="location"
								>
									<span class="text-secondary">
										<clay:icon
											symbol="<%= journalArticleItemSelectorViewDisplayContext.getGroupCssIcon(curArticle.getGroupId()) %>"
										/>

										<small><%= journalArticleItemSelectorViewDisplayContext.getGroupLabel(curArticle.getGroupId(), locale) %></small>
									</span>
								</liferay-ui:search-container-column-text>
							</c:if>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-100"
								name="author"
								value="<%= HtmlUtil.escape(PortalUtil.getUserName(curArticle)) %>"
							/>

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
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="<%= curFolder != null %>">

					<%
					PortletURL rowURL = PortletURLBuilder.create(
						journalArticleItemSelectorViewDisplayContext.getPortletURL()
					).setParameter(
						"folderId", curFolder.getFolderId()
					).setParameter(
						"groupId", curFolder.getGroupId()
					).build();
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(journalArticleItemSelectorViewDisplayContext.getDisplayStyle(), "descriptive") %>'>
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
									<a href="<%= rowURL %>">
										<%= HtmlUtil.escape(curFolder.getName()) %>
									</a>
								</p>

								<c:if test="<%= journalArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
									<h6 class="text-default">
										<liferay-ui:message key="location" />:
										<span class="text-secondary">
											<clay:icon
												symbol="<%= journalArticleItemSelectorViewDisplayContext.getGroupCssIcon(curFolder.getGroupId()) %>"
											/>

											<small><%= journalArticleItemSelectorViewDisplayContext.getGroupLabel(curFolder.getGroupId(), locale) %></small>
										</span>
									</h6>
								</c:if>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test='<%= Objects.equals(journalArticleItemSelectorViewDisplayContext.getDisplayStyle(), "icon") %>'>

							<%
							row.setCssClass("card-page-item card-page-item-directory " + row.getCssClass());
							%>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>
								<div class="card card-horizontal card-interactive card-interactive-secondary card-type-directory">
									<div class="card-body">
										<div class="card-row">
											<clay:content-col>
												<clay:sticker
													displayType="secondary"
													icon="folder"
													inline="<%= true %>"
												/>
											</clay:content-col>

											<div class="autofit-col autofit-col-expand autofit-col-gutters">
												<a class="card-title text-truncate" href="<%= rowURL %>" title="<%= HtmlUtil.escapeAttribute(curFolder.getName()) %>">
													<%= HtmlUtil.escape(curFolder.getName()) %>
												</a>

												<c:if test="<%= journalArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
													<span class="text-secondary">
														<clay:icon
															symbol="<%= journalArticleItemSelectorViewDisplayContext.getGroupCssIcon(curFolder.getGroupId()) %>"
														/>

														<small><%= journalArticleItemSelectorViewDisplayContext.getGroupLabel(curFolder.getGroupId(), locale) %></small>
													</span>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 table-list-title"
								href="<%= rowURL %>"
								name="title"
								value="<%= HtmlUtil.escape(curFolder.getName()) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 text-truncate"
								name="description"
								value="<%= HtmlUtil.escape(curFolder.getDescription()) %>"
							/>

							<c:if test="<%= journalArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
								<liferay-ui:search-container-column-text
									name="location"
								>
									<span class="text-secondary">
										<clay:icon
											symbol="<%= journalArticleItemSelectorViewDisplayContext.getGroupCssIcon(curFolder.getGroupId()) %>"
										/>

										<small><%= journalArticleItemSelectorViewDisplayContext.getGroupLabel(curFolder.getGroupId(), locale) %></small>
									</span>
								</liferay-ui:search-container-column-text>
							</c:if>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-150"
								name="author"
								value="<%= HtmlUtil.escape(PortalUtil.getUserName(curFolder)) %>"
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
						</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= journalArticleItemSelectorViewDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			resultRowSplitter="<%= new JournalResultRowSplitter() %>"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>