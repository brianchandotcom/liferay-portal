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

SearchContainer kbObjectsSearchContainer = new KBObjectsSearch(renderRequest, PortletURLUtil.clone(currentURLObj, renderResponse));

boolean kbFolderView = (parentResourceClassNameId == kbFolderClassNameId);

if (Validator.isNotNull(keywords)) {
	KBArticleSearchDisplay kbArticleSearchDisplay = KBArticleServiceUtil.getKBArticleSearchDisplay(scopeGroupId, keywords, keywords, WorkflowConstants.STATUS_ANY, null, null, false, new int[0], kbObjectsSearchContainer.getCur(), kbObjectsSearchContainer.getDelta(), kbObjectsSearchContainer.getOrderByComparator());

	kbObjectsSearchContainer.setResults(kbArticleSearchDisplay.getResults());
	kbObjectsSearchContainer.setTotal(kbArticleSearchDisplay.getTotal());
}
else if (kbFolderView) {
	kbObjectsSearchContainer.setTotal(KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(scopeGroupId, parentResourcePrimKey, WorkflowConstants.STATUS_ANY));
	kbObjectsSearchContainer.setResults(KBFolderServiceUtil.getKBFoldersAndKBArticles(scopeGroupId, parentResourcePrimKey, WorkflowConstants.STATUS_ANY, kbObjectsSearchContainer.getStart(), kbObjectsSearchContainer.getEnd(), kbObjectsSearchContainer.getOrderByComparator()));
}
else {
	kbObjectsSearchContainer.setTotal(KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, parentResourcePrimKey, WorkflowConstants.STATUS_ANY));
	kbObjectsSearchContainer.setResults(KBArticleServiceUtil.getKBArticles(scopeGroupId, parentResourcePrimKey, WorkflowConstants.STATUS_ANY, kbObjectsSearchContainer.getStart(), kbObjectsSearchContainer.getEnd(), kbObjectsSearchContainer.getOrderByComparator()));
}

kbObjectsSearchContainer.setRowChecker(new EntriesChecker(liferayPortletRequest, liferayPortletResponse));

List kbObjects = kbObjectsSearchContainer.getResults();

KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse, templatePath);

if (parentResourcePrimKey != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	String headerTitle = StringPool.BLANK;

	if (kbFolderView) {
		KBFolder kbFolder = KBFolderServiceUtil.getKBFolder(parentResourcePrimKey);

		headerTitle = kbFolder.getName();
	}
	else {
		KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(parentResourcePrimKey, WorkflowConstants.STATUS_ANY);

		headerTitle = kbArticle.getTitle();
	}

	renderResponse.setTitle(headerTitle);
}
%>

<liferay-util:include page="/admin/common/top_tabs.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar
	disabled="<%= kbObjects.isEmpty() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="kbObjects"
>
	<c:if test="<%= Validator.isNull(keywords) %>">

		<%
		PortletURL displayStyleURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
		%>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>

			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"descriptive"} %>'
				portletURL="<%= displayStyleURL %>"
				selectedDisplayStyle="descriptive"
			/>

			<liferay-util:include page="/admin/add_button.jsp" servletContext="<%= application %>" />
		</liferay-frontend:management-bar-buttons>
	</c:if>

	<liferay-frontend:management-bar-filters>
		<c:if test="<%= Validator.isNull(keywords) %>">

			<%
			PortletURL navigationPortletURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
			%>

			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= navigationPortletURL %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= kbObjectsSearchContainer.getOrderByCol() %>"
				orderByType="<%= kbObjectsSearchContainer.getOrderByType() %>"
				orderColumns='<%= new String[] {"priority", "modified-date", "title", "view-count"} %>'
				portletURL="<%= PortletURLUtil.clone(currentURLObj, liferayPortletResponse) %>"
			/>
		</c:if>

		<li>
			<liferay-portlet:renderURL varImpl="searchURL">
				<portlet:param name="mvcPath" value="/admin/search.jsp" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= searchURL %>" method="get" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="searchURL" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<liferay-ui:input-search
					id="keywords"
					markupView="lexicon"
					placeholder='<%= LanguageUtil.get(request, "search") %>'
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="infoPanel" var="sidebarPanelURL">
		<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(parentResourceClassNameId) %>" />
		<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(parentResourcePrimKey) %>" />
		<portlet:param name="showSidebarHeader" value="<%= Boolean.TRUE.toString() %>" />
	</liferay-portlet:resourceURL>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="kbObjects"
	>

		<%
		request.setAttribute(KBWebKeys.SHOW_SIDEBAR_HEADER, Boolean.TRUE);
		%>

		<liferay-util:include page="/admin/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">

		<%
		KBAdminViewDisplayContext kbAdminViewDisplayContext = new KBAdminViewDisplayContext(parentResourceClassNameId, parentResourcePrimKey, request, liferayPortletResponse);

		kbAdminViewDisplayContext.populatePortletBreadcrumbEntries(currentURLObj);
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
							<liferay-ui:message arguments="<%= HtmlUtil.escape(StringUtil.merge(kbGroupServiceConfiguration.markdownImporterArticleExtensions(), StringPool.COMMA_AND_SPACE)) %>" key="nothing-was-imported-no-articles-were-found-with-one-of-the-supported-extensions-x" />
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<liferay-ui:search-container
				id="kbObjects"
				searchContainer="<%= kbObjectsSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="Object"
					modelVar="kbObject"
				>
					<c:choose>
						<c:when test="<%= kbObject instanceof KBFolder %>">

							<%
							KBFolder kbFolder = (KBFolder)kbObject;

							Date modifiedDate = kbFolder.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);

							row.setPrimaryKey(String.valueOf(kbFolder.getKbFolderId()));
							%>

							<liferay-ui:search-container-column-icon
								icon="folder"
								toggleRowChecker="<%= true %>"
							/>

							<liferay-portlet:renderURL varImpl="rowURL">
								<portlet:param name="mvcPath" value="/admin/view_folders.jsp" />
								<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbFolder.getClassNameId()) %>" />
								<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>
								<h5 class="text-default">
									<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbFolder.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
								</h5>

								<h4>
									<aui:a href="<%= rowURL.toString() %>">
										<%= HtmlUtil.escape(kbFolder.getName()) %>
									</aui:a>
								</h4>

								<h5 class="text-default">
									<span>
										<liferay-ui:message arguments="<%= KBFolderServiceUtil.getKBFoldersCount(kbFolder.getGroupId(), kbFolder.getKbFolderId()) %>" key="x-folders" />
									</span>
									<span class="kb-descriptive-details">
										<liferay-ui:message arguments="<%= KBArticleServiceUtil.getKBArticlesCount(kbFolder.getGroupId(), kbFolder.getKbFolderId(), WorkflowConstants.STATUS_ANY) %>" key="x-articles" />
									</span>
								</h5>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-jsp
								path="/admin/folder_action.jsp"
							/>
						</c:when>
						<c:otherwise>

							<%
							KBArticle kbArticle = (KBArticle)kbObject;

							Date modifiedDate = kbArticle.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);

							row.setPrimaryKey(String.valueOf(kbArticle.getResourcePrimKey()));
							%>

							<liferay-ui:search-container-column-user
								showDetails="<%= false %>"
								userId="<%= kbArticle.getUserId() %>"
							/>

							<%
							PortletURL viewURL = kbArticleURLHelper.createViewWithRedirectURL(kbArticle, currentURL);
							%>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>
								<h5 class="text-default">
									<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbArticle.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
								</h5>

								<h4>
									<aui:a href="<%= viewURL.toString() %>">
										<%= HtmlUtil.escape(kbArticle.getTitle()) %>
									</aui:a>
								</h4>

								<h5 class="text-default">
									<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= kbArticle.getStatus() %>" />

									<%
									int childKBArticlesCount = KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);
									%>

									<c:if test="<%= childKBArticlesCount > 0 %>">
										<liferay-portlet:renderURL varImpl="childKBArticlesURL">
											<portlet:param name="mvcPath" value="/admin/view_articles.jsp" />
											<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
											<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
											<portlet:param name="redirect" value="<%= currentURL %>" />
										</liferay-portlet:renderURL>

										<span class="kb-descriptive-details">
											<aui:a href="<%= childKBArticlesURL.toString() %>">
												<liferay-ui:message arguments="<%= childKBArticlesCount %>" key="x-child-articles" />
											</aui:a>
										</span>
									</c:if>

									<span class="kb-descriptive-details">
										<liferay-ui:message arguments="<%= BigDecimal.valueOf(kbArticle.getPriority()).toPlainString() %>" key="priority-x" />
									</span>
									<span class="kb-descriptive-details">
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

				<liferay-ui:search-iterator
					displayStyle="descriptive"
					markupView="lexicon"
					resultRowSplitter="<%= kbFolderView ? new KBResultRowSplitter() : null %>"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}
</aui:script>