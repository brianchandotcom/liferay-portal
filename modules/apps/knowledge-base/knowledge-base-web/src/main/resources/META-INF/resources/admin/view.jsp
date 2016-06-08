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

<%@ include file="/admin/init.jsp" %>

<%
long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long parentResourceClassNameId = ParamUtil.getLong(request, "parentResourceClassNameId", kbFolderClassNameId);
long parentResourcePrimKey = ParamUtil.getLong(request, "parentResourcePrimKey", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

String keywords = ParamUtil.getString(request, "keywords");

SearchContainer kbEntriesSearchContainer = new KBArticleSearch(renderRequest, PortletURLUtil.clone(currentURLObj, renderResponse));

boolean kbFolderView = (parentResourceClassNameId == kbFolderClassNameId);

if (Validator.isNotNull(keywords)) {
	KBArticleSearchDisplay kbArticleSearchDisplay = KBArticleServiceUtil.getKBArticleSearchDisplay(scopeGroupId, keywords, keywords, WorkflowConstants.STATUS_ANY, null, null, false, new int[0], kbEntriesSearchContainer.getCur(), kbEntriesSearchContainer.getDelta(), kbEntriesSearchContainer.getOrderByComparator());

	kbEntriesSearchContainer.setResults(kbArticleSearchDisplay.getResults());
	kbEntriesSearchContainer.setTotal(kbArticleSearchDisplay.getTotal());
}
else if (kbFolderView) {
	kbEntriesSearchContainer.setTotal(KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(scopeGroupId, parentResourcePrimKey, WorkflowConstants.STATUS_ANY));
	kbEntriesSearchContainer.setResults(KBFolderServiceUtil.getKBFoldersAndKBArticles(scopeGroupId, parentResourcePrimKey, WorkflowConstants.STATUS_ANY, kbEntriesSearchContainer.getStart(), kbEntriesSearchContainer.getEnd(), kbEntriesSearchContainer.getOrderByComparator()));
}
else {
	kbEntriesSearchContainer.setTotal(KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, parentResourcePrimKey, WorkflowConstants.STATUS_ANY));
	kbEntriesSearchContainer.setResults(KBArticleServiceUtil.getKBArticles(scopeGroupId, parentResourcePrimKey, WorkflowConstants.STATUS_ANY, kbEntriesSearchContainer.getStart(), kbEntriesSearchContainer.getEnd(), kbEntriesSearchContainer.getOrderByComparator()));
}

kbEntriesSearchContainer.setRowChecker(new EntriesChecker(liferayPortletRequest, liferayPortletResponse));

List kbEntries = kbEntriesSearchContainer.getResults();

KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse, templatePath);

if (parentResourcePrimKey != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	String headerTitle = StringPool.BLANK;

	if (kbFolderView) {
		KBFolder kbFolder = KBFolderLocalServiceUtil.getKBFolder(parentResourcePrimKey);

		headerTitle = kbFolder.getName();
	}
	else {
		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(parentResourcePrimKey, WorkflowConstants.STATUS_ANY);

		headerTitle = kbArticle.getTitle();
	}

	renderResponse.setTitle(headerTitle);
}
%>

<liferay-util:include page="/admin/common/top_tabs.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar
	disabled="<%= kbEntries.isEmpty() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="kbEntries"
>
	<c:if test="<%= Validator.isNull(keywords) %>">

		<%
		PortletURL displayStyleURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
		%>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"descriptive"} %>'
				portletURL="<%= displayStyleURL %>"
				selectedDisplayStyle="descriptive"
			/>
		</liferay-frontend:management-bar-buttons>

		<%
		PortletURL navigationPortletURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
		%>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= navigationPortletURL %>"
			/>
		</liferay-frontend:management-bar-filters>
	</c:if>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">

	<%
	KnowledgeBaseUtil.addPortletBreadcrumbEntries(parentResourceClassNameId, parentResourcePrimKey, "/admin/view.jsp", request, renderResponse);
	%>

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-portlet:actionURL name="deleteKBArticlesAndFolders" varImpl="deleteKBArticlesAndFoldersURL" />

	<aui:form action="<%= deleteKBArticlesAndFoldersURL %>" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:error exception="<%= KBArticlePriorityException.class %>" message='<%= LanguageUtil.format(request, "please-enter-a-priority-that-is-greater-than-x", "0", false) %>' translateMessage="<%= false %>" />

		<c:if test='<%= SessionMessages.contains(renderRequest, "importedKBArticlesCount") %>'>

			<%
			int importedKBArticlesCount = GetterUtil.getInteger(SessionMessages.get(renderRequest, "importedKBArticlesCount"));
			%>

			<c:choose>
				<c:when test="<%= importedKBArticlesCount > 0 %>">
					<div class="alert alert-success">
						<liferay-ui:message key="your-request-completed-successfully" />

						<liferay-ui:message arguments="<%= importedKBArticlesCount %>" key="a-total-of-x-articles-have-been-imported" />
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-warning">
						<liferay-ui:message
							arguments="<%= StringUtil.merge(kbGroupServiceConfiguration.markdownImporterArticleExtensions(), StringPool.COMMA_AND_SPACE) %>"
							key="nothing-was-imported-no-articles-were-found-with-one-of-the-supported-extensions-x"
						/>
					</div>
				</c:otherwise>
			</c:choose>
		</c:if>

		<liferay-ui:search-container
			id="kbEntries"
			searchContainer="<%= kbEntriesSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="Object"
				modelVar="kbEntry"
			>
				<c:choose>
					<c:when test="<%= kbEntry instanceof KBFolder %>">

						<%
						KBFolder kbFolder = (KBFolder)kbEntry;

						kbFolder = kbFolder.toEscapedModel();

						Date modifiedDate = kbFolder.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);

						row.setPrimaryKey(String.valueOf(kbFolder.getKbFolderId()));
						%>

						<liferay-ui:search-container-column-icon
							icon="folder"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-portlet:renderURL varImpl="rowURL">
							<portlet:param name="mvcPath" value="/admin/view.jsp" />
							<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbFolder.getClassNameId()) %>" />
							<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:search-container-column-text colspan="<%= 2 %>">
							<h5 class="text-default">
								<liferay-ui:message arguments="<%= new String[] {kbFolder.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
							</h5>

							<h4>
								<aui:a href="<%= rowURL.toString() %>">
									<%= kbFolder.getName() %>
								</aui:a>
							</h4>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							path="/admin/folder_action.jsp"
						/>
					</c:when>
					<c:otherwise>

						<%
						KBArticle kbArticle = (KBArticle)kbEntry;

						kbArticle = kbArticle.toEscapedModel();

						Date modifiedDate = kbArticle.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);

						row.setPrimaryKey(String.valueOf(kbArticle.getResourcePrimKey()));
						%>

						<liferay-ui:search-container-column-user
							cssClass="user-icon-lg"
							showDetails="<%= false %>"
							userId="<%= kbArticle.getUserId() %>"
						/>

						<%
						PortletURL viewURL = kbArticleURLHelper.createViewWithRedirectURL(kbArticle, currentURL);
						%>

						<liferay-ui:search-container-column-text colspan="<%= 2 %>">
							<h5 class="text-default">
								<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbArticle.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
							</h5>

							<h4>
								<aui:a href="<%= viewURL.toString() %>">
									<%= kbArticle.getTitle() %>
								</aui:a>
							</h4>

							<h5 class="text-default">
								<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= kbArticle.getStatus() %>" />

								<%
								int childKBArticlesCount = KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);
								%>

								<c:if test="<%= childKBArticlesCount > 0 %>">
									<liferay-portlet:renderURL varImpl="childKBArticlesURL">
										<portlet:param name="mvcPath" value="/admin/view.jsp" />
										<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
										<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
									</liferay-portlet:renderURL>

									<span class="kb-article-descriptive-details">
										<aui:a href="<%= childKBArticlesURL.toString() %>">
											<liferay-ui:message arguments="<%= childKBArticlesCount %>" key="x-child-articles" />
										</aui:a>
									</span>
								</c:if>

								<span class="kb-article-descriptive-details">
									<liferay-ui:message arguments="<%= BigDecimal.valueOf(kbArticle.getPriority()).toPlainString() %>" key="priority-x" />
								</span>

								<span class="kb-article-descriptive-details">
									<liferay-ui:message arguments="<%= kbArticle.getViewCount() %>" key="x-views" />
								</span>
							</h5>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							align="right"
							cssClass="entry-action"
							path="/admin/article_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" resultRowSplitter="<%= kbFolderView ? new KBResultRowSplitter() : null %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<liferay-util:include page="/admin/add_button.jsp" servletContext="<%= application %>" />

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}
</aui:script>