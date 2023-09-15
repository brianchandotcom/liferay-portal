<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/item/selector/init.jsp" %>

<%
KBArticleItemSelectorViewDisplayContext kbArticleItemSelectorViewDisplayContext = (KBArticleItemSelectorViewDisplayContext)request.getAttribute(KBArticleItemSelectorViewDisplayContext.class.getName());
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new KBArticleItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, kbArticleItemSelectorViewDisplayContext) %>"
/>

<clay:container-fluid
	cssClass="item-selector lfr-item-viewer"
	id='<%= liferayPortletResponse.getNamespace() + "articlesContainer" %>'
>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= kbArticleItemSelectorViewDisplayContext.getPortletBreadcrumbEntries() %>"
	/>

	<liferay-ui:search-container
		emptyResultsMessage="no-knowledge-base-articles-were-found"
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
					<liferay-ui:search-container-column-text>
						<liferay-user:user-portrait
							userId="<%= curKBArticle.getUserId() %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<div class="item-preview selector-button" data-returnType="<%= kbArticleItemSelectorViewDisplayContext.getKBArticleDataReturnType() %>" data-value="<%= HtmlUtil.escapeAttribute(kbArticleItemSelectorViewDisplayContext.getKBArticleDataValue(curKBArticle)) %>">

							<%
							Date modifiedDate = curKBArticle.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
							%>

							<h2 class="font-weight-bold h5 text-dark">
								<%= HtmlUtil.escape(curKBArticle.getTitle()) %>
							</h2>

							<span class="text-default">
								<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(curKBArticle.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
							</span>
						</div>

						<%
						int childKBArticlesCount = KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, curKBArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);
						%>

						<c:if test="<%= childKBArticlesCount > 0 %>">
							<span class="kb-descriptive-details">
								<aui:a href="<%= kbArticleItemSelectorViewDisplayContext.getKBArticleRowURL(curKBArticle) %>">
									<liferay-ui:message arguments="<%= childKBArticlesCount %>" key="x-child-articles" />
								</aui:a>
							</span>
						</c:if>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test="<%= curKBFolder != null %>">
					<liferay-ui:search-container-column-icon
						icon="folder"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date modifiedDate = curKBFolder.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
						%>

						<h2 class="font-weight-bold h5">
							<a href="<%= kbArticleItemSelectorViewDisplayContext.getKBFolderRowURL(curKBFolder.getGroupId(), curKBFolder.getKbFolderId()) %>">
								<%= HtmlUtil.escape(curKBFolder.getName()) %>
							</a>
						</h2>

						<span class="text-default">
							<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(curKBFolder.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</span>
					</liferay-ui:search-container-column-text>
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