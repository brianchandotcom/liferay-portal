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
SearchContainer articleSearchContainer = journalDisplayContext.getSearchContainer(true);

String displayStyle = journalDisplayContext.getDisplayStyle();
%>

<liferay-ui:search-container
	emptyResultsMessage="no-web-content-was-found"
	searchContainer="<%= articleSearchContainer %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.journal.model.JournalArticle"
		modelVar="articleVersion"
	>

		<%
		row.setPrimaryKey(articleVersion.getArticleId() + JournalPortlet.VERSION_SEPARATOR + articleVersion.getVersion());
		%>

		<c:choose>
			<c:when test='<%= displayStyle.equals("descriptive") %>'>
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

				<liferay-ui:search-container-column-jsp
					path="/article_version_action.jsp"
				/>
			</c:when>
			<c:when test='<%= displayStyle.equals("icon") %>'>

				<%
				row.setCssClass("entry-card lfr-asset-item");
				%>

				<liferay-ui:search-container-column-text>

					<%
					String articleImageURL = articleVersion.getArticleImageURL(themeDisplay);
					%>

					<c:choose>
						<c:when test="<%= Validator.isNotNull(articleImageURL) %>">
							<liferay-frontend:vertical-card
								actionJsp="/article_version_action.jsp"
								actionJspServletContext="<%= application %>"
								imageUrl="<%= articleImageURL %>"
								resultRow="<%= row %>"
								rowChecker="<%= searchContainer.getRowChecker() %>"
								title="<%= articleVersion.getTitle(locale) %>"
							>
								<%@ include file="/article_version_vertical_card.jspf" %>
							</liferay-frontend:vertical-card>
						</c:when>
						<c:otherwise>
							<liferay-frontend:icon-vertical-card
								actionJsp="/article_version_action.jsp"
								actionJspServletContext="<%= application %>"
								icon="web-content"
								resultRow="<%= row %>"
								rowChecker="<%= searchContainer.getRowChecker() %>"
								title="<%= articleVersion.getTitle(locale) %>"
							>
								<%@ include file="/article_version_vertical_card.jspf" %>
							</liferay-frontend:icon-vertical-card>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:when test='<%= displayStyle.equals("list") %>'>
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

				<liferay-ui:search-container-column-jsp
					path="/article_version_action.jsp"
				/>
			</c:when>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="<%= displayStyle %>"
		markupView="lexicon"
		resultRowSplitter="<%= journalDisplayContext.isSearch() ? null : new JournalResultRowSplitter() %>"
		searchContainer="<%= articleSearchContainer %>"
	/>
</liferay-ui:search-container>