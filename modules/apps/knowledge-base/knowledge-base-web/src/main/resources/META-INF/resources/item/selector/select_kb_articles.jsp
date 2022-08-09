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
KBArticleItemSelectorViewDisplayContext kbArticleItemSelectorViewDisplayContext = (KBArticleItemSelectorViewDisplayContext)request.getAttribute(KBArticleItemSelectorViewDisplayContext.class.getName());
%>

<clay:container-fluid
	cssClass="item-selector lfr-item-viewer"
	id='<%= liferayPortletResponse.getNamespace() + "articlesContainer" %>'
>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= kbArticleItemSelectorViewDisplayContext.getPortletBreadcrumbEntries() %>"
	/>

	<liferay-ui:search-container
		emptyResultsMessage="no-web-content-was-found"
		id="articles"
		searchContainer="<%= kbArticleItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="object"
		>

			<%
			KBArticle curKBArticle = null;
			KBFolder curKBFolder = null;

			Object result = row.getObject();

			if (result instanceof KBFolder) {
				curKBFolder = (KBFolder)result;
			}
			else {
				curKBArticle = kbArticleItemSelectorViewDisplayContext.getLatestArticle((KBArticle)result);
			}
			%>

			<c:choose>
				<c:when test="<%= curKBArticle != null %>">

					<%
					row.setCssClass("articles selector-button" + row.getCssClass());

					row.setData(kbArticleItemSelectorViewDisplayContext.getKBArticleContext(curKBArticle));
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(kbArticleItemSelectorViewDisplayContext.getDisplayStyle(), "descriptive") %>'>

							<%
							row.setCssClass("item-preview " + row.getCssClass());
							%>

							<liferay-ui:search-container-column-text>
								<liferay-ui:user-portrait
									userId="<%= curKBArticle.getUserId() %>"
								/>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>

								<%
								Date createDate = curKBArticle.getModifiedDate();

								String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
								%>

								<span class="text-default">
									<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(curKBArticle.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
								</span>

								<p class="font-weight-bold h5">
									<%= HtmlUtil.escape(curKBArticle.getTitle()) %>
								</p>

								<c:if test="<%= kbArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
									<h6 class="text-default">
										<liferay-ui:message key="location" />:
										<span class="text-secondary">
											<clay:icon
												symbol="<%= kbArticleItemSelectorViewDisplayContext.getGroupCssIcon(curKBArticle.getGroupId()) %>"
											/>

											<small><%= kbArticleItemSelectorViewDisplayContext.getGroupLabel(curKBArticle.getGroupId(), locale) %></small>
										</span>
									</h6>
								</c:if>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>

							<%
							row.setCssClass("item-preview " + row.getCssClass());
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 table-title"
								name="title"
								value="<%= curKBArticle.getTitle() %>"
							/>

							<c:if test="<%= kbArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
								<liferay-ui:search-container-column-text
									name="location"
								>
									<span class="text-secondary">
										<clay:icon
											symbol="<%= kbArticleItemSelectorViewDisplayContext.getGroupCssIcon(curKBArticle.getGroupId()) %>"
										/>

										<small><%= kbArticleItemSelectorViewDisplayContext.getGroupLabel(curKBArticle.getGroupId(), locale) %></small>
									</span>
								</liferay-ui:search-container-column-text>
							</c:if>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-100"
								name="author"
								value="<%= HtmlUtil.escape(PortalUtil.getUserName(curKBArticle)) %>"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
								name="modified-date"
								value="<%= curKBArticle.getModifiedDate() %>"
							/>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="<%= curKBFolder != null %>">

					<%
					PortletURL rowURL = PortletURLBuilder.create(
						kbArticleItemSelectorViewDisplayContext.getPortletURL()
					).setParameter(
						"groupId", curKBFolder.getGroupId()
					).setParameter(
						"kbFolderId", curKBFolder.getKbFolderId()
					).buildPortletURL();
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(kbArticleItemSelectorViewDisplayContext.getDisplayStyle(), "descriptive") %>'>
							<liferay-ui:search-container-column-icon
								icon="folder"
								toggleRowChecker="<%= true %>"
							/>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>

								<%
								Date createDate = curKBFolder.getCreateDate();

								String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
								%>

								<span class="text-default">
									<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(curKBFolder.getUserName()), createDateDescription} %>" key="x-modified-x-ago" />
								</span>

								<p class="font-weight-bold h5">
									<a href="<%= rowURL %>">
										<%= HtmlUtil.escape(curKBFolder.getName()) %>
									</a>
								</p>

								<c:if test="<%= kbArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
									<h6 class="text-default">
										<liferay-ui:message key="location" />:
										<span class="text-secondary">
											<clay:icon
												symbol="<%= kbArticleItemSelectorViewDisplayContext.getGroupCssIcon(curKBFolder.getGroupId()) %>"
											/>

											<small><%= kbArticleItemSelectorViewDisplayContext.getGroupLabel(curKBFolder.getGroupId(), locale) %></small>
										</span>
									</h6>
								</c:if>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test='<%= Objects.equals(kbArticleItemSelectorViewDisplayContext.getDisplayStyle(), "icon") %>'>

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
												<a class="card-title text-truncate" href="<%= rowURL %>" title="<%= HtmlUtil.escapeAttribute(curKBFolder.getName()) %>">
													<%= HtmlUtil.escape(curKBFolder.getName()) %>
												</a>

												<c:if test="<%= kbArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
													<span class="text-secondary">
														<clay:icon
															symbol="<%= kbArticleItemSelectorViewDisplayContext.getGroupCssIcon(curKBFolder.getGroupId()) %>"
														/>

														<small><%= kbArticleItemSelectorViewDisplayContext.getGroupLabel(curKBFolder.getGroupId(), locale) %></small>
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
								value="<%= HtmlUtil.escape(curKBFolder.getName()) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 text-truncate"
								name="description"
								value="<%= HtmlUtil.escape(curKBFolder.getDescription()) %>"
							/>

							<c:if test="<%= kbArticleItemSelectorViewDisplayContext.isSearchEverywhere() %>">
								<liferay-ui:search-container-column-text
									name="location"
								>
									<span class="text-secondary">
										<clay:icon
											symbol="<%= kbArticleItemSelectorViewDisplayContext.getGroupCssIcon(curKBFolder.getGroupId()) %>"
										/>

										<small><%= kbArticleItemSelectorViewDisplayContext.getGroupLabel(curKBFolder.getGroupId(), locale) %></small>
									</span>
								</liferay-ui:search-container-column-text>
							</c:if>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-150"
								name="author"
								value="<%= HtmlUtil.escape(PortalUtil.getUserName(curKBFolder)) %>"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
								name="modified-date"
								value="<%= curKBFolder.getModifiedDate() %>"
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
			displayStyle="<%= kbArticleItemSelectorViewDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			resultRowSplitter="<%= new KBResultRowSplitter() %>"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>