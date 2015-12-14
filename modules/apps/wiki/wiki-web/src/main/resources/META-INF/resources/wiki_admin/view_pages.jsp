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

<%@ include file="/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

String navigation = ParamUtil.getString(request, "navigation", "all-pages");

PortletURL portletURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, navigation), portletURL.toString());

String emptyResultsMessage = null;

if (navigation.equals("all-pages")) {
	emptyResultsMessage = "there-are-no-pages";
}
else if (navigation.equals("draft-pages")) {
	emptyResultsMessage = "there-are-no-drafts";
}
else if (navigation.equals("frontpage")) {
	emptyResultsMessage = LanguageUtil.format(request, "there-is-no-x", new String[] {wikiGroupServiceConfiguration.frontPageName()}, false);
}
else if (navigation.equals("orphan-pages")) {
	emptyResultsMessage = "there-are-no-orphan-changes";
}
else if (navigation.equals("recent-changes")) {
	emptyResultsMessage = "there-are-no-recent-changes";
}

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(WikiPortletKeys.WIKI_ADMIN, "pages-order-by-col", orderByCol);
	portalPreferences.setValue(WikiPortletKeys.WIKI_ADMIN, "pages-order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(WikiPortletKeys.WIKI_ADMIN, "pages-order-by-col", "title");
	orderByType = portalPreferences.getValue(WikiPortletKeys.WIKI_ADMIN, "pages-order-by-type", "asc");
}

request.setAttribute("view_pages.jsp-orderByCol", orderByCol);
request.setAttribute("view_pages.jsp-orderByType", orderByType);

SearchContainer wikiPagesSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, emptyResultsMessage);

wikiPagesSearchContainer.setOrderByType(orderByType);
wikiPagesSearchContainer.setOrderByCol(orderByCol);
wikiPagesSearchContainer.setOrderByComparator(WikiPortletUtil.getPageOrderByComparator(orderByCol, orderByType));
wikiPagesSearchContainer.setRowChecker(new PagesChecker(liferayPortletRequest, liferayPortletResponse));

int pagesCount = 0;
List<WikiPage> pages = new ArrayList<WikiPage>();

if (navigation.equals("all-pages")) {
	pagesCount = WikiPageServiceUtil.getPagesCount(themeDisplay.getScopeGroupId(), node.getNodeId(), true, themeDisplay.getUserId(), true, WorkflowConstants.STATUS_APPROVED);

	pages = WikiPageServiceUtil.getPages(themeDisplay.getScopeGroupId(), node.getNodeId(), true, themeDisplay.getUserId(), true, WorkflowConstants.STATUS_APPROVED, wikiPagesSearchContainer.getStart(), wikiPagesSearchContainer.getEnd(), wikiPagesSearchContainer.getOrderByComparator());
}
else if (navigation.equals("draft-pages")) {
	long draftUserId = user.getUserId();

	if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		draftUserId = 0;
	}

	int status = WorkflowConstants.STATUS_DRAFT;

	pagesCount = WikiPageServiceUtil.getPagesCount(themeDisplay.getScopeGroupId(), draftUserId, node.getNodeId(), status);

	pages = WikiPageServiceUtil.getPages(themeDisplay.getScopeGroupId(), draftUserId, node.getNodeId(), status, wikiPagesSearchContainer.getStart(), wikiPagesSearchContainer.getEnd());
}
else if (navigation.equals("frontpage")) {

	WikiPage wikiPage = WikiPageServiceUtil.getPage(scopeGroupId, node.getNodeId(), wikiGroupServiceConfiguration.frontPageName());

	pagesCount = 1;

	pages.add(wikiPage);
}
else if (navigation.equals("orphan-pages")) {
	List<WikiPage> orphans = WikiPageServiceUtil.getOrphans(themeDisplay.getScopeGroupId(), node.getNodeId());

	pagesCount = orphans.size();

	pages = ListUtil.subList(orphans, wikiPagesSearchContainer.getStart(), wikiPagesSearchContainer.getEnd());
}
else if (navigation.equals("recent-changes")) {
	pagesCount = WikiPageServiceUtil.getRecentChangesCount(themeDisplay.getScopeGroupId(), node.getNodeId());

	pages = WikiPageServiceUtil.getRecentChanges(themeDisplay.getScopeGroupId(), node.getNodeId(), wikiPagesSearchContainer.getStart(), wikiPagesSearchContainer.getEnd());
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);

PortletURL backToNodeURL = wikiURLHelper.getBackToNodeURL(node);

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backToNodeURL.toString());

	renderResponse.setTitle(node.getName());
}

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(WikiPortletKeys.WIKI_ADMIN, "pages-display-style", "descriptive");
}
else {
	portalPreferences.setValue(WikiPortletKeys.WIKI_ADMIN, "pages-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}
%>

<liferay-util:include page="/wiki_admin/pages_navigation.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar
	includeCheckBox="<%= !pages.isEmpty() %>"
	searchContainerId="wikiPages"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive", "list"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-util:include page="/wiki_admin/view_pages_filters.jsp" servletContext="<%= application %>" />
		</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>

		<%
		String taglibURL = "javascript:" + renderResponse.getNamespace() + "deletePages();";

		boolean isTrashEnabled = TrashUtil.isTrashEnabled(scopeGroupId);
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" iconCssClass='<%= isTrashEnabled ? "icon-trash" : "icon-remove" %>' label='<%= isTrashEnabled ? "recycle-bin" : "delete" %>' />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">

	<%
	WikiVisualizationHelper wikiVisualizationHelper = new WikiVisualizationHelper(wikiRequestHelper, wikiPortletInstanceSettingsHelper, wikiGroupServiceConfiguration);
	%>

	<c:if test="<%= wikiVisualizationHelper.isUndoTrashControlVisible() %>">

		<%
		PortletURL undoTrashURL = wikiURLHelper.getUndoTrashURL();
		%>

		<liferay-ui:trash-undo portletURL="<%= undoTrashURL.toString() %>" />
	</c:if>

	<aui:form action="<%= wikiURLHelper.getSearchURL() %>" method="get" name="fm">
		<aui:input name="nodeId" type="hidden" value="<%= String.valueOf(node.getNodeId()) %>" />
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			id="wikiPages"
			searchContainer="<%= wikiPagesSearchContainer %>"
			total="<%= pagesCount %>"
		>
			<liferay-ui:search-container-results
				results="<%= pages %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.wiki.model.WikiPage"
				keyProperty="pageId"
				modelVar="curPage"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

			if (!navigation.equals("draft_pages")) {
				rowURL.setParameter("mvcRenderCommandName", "/wiki/view");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("nodeName", curPage.getNode().getName());
			}
			else {
				rowURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("nodeId", String.valueOf(curPage.getNodeId()));
			}

			rowURL.setParameter("title", curPage.getTitle());
			%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							icon="folder"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-text colspan="<%= 2 %>">

							<%
							Date modifiedDate = curPage.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
							%>

							<h5 class="text-default">
								<c:choose>
									<c:when test="<%= Validator.isNotNull(curPage.getUserName()) %>">
										<liferay-ui:message arguments="<%= new String[] {curPage.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
									</c:when>
									<c:otherwise>
										<liferay-ui:message arguments="<%= new String[] {modifiedDateDescription} %>" key="modified-x-ago" />
									</c:otherwise>
								</c:choose>
							</h5>

							<h4>
								<aui:a href="<%= rowURL.toString() %>">
									<%= curPage.getTitle() %>
								</aui:a>
							</h4>

							<h5 class="text-default">
								<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= curPage.getStatus() %>" />
							</h5>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
								path="/wiki/page_action.jsp"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="title"
							value="<%= curPage.getTitle() %>"
						/>

						<liferay-ui:search-container-column-status
							href="<%= rowURL %>"
							name="status"
							status="<%= curPage.getStatus() %>"
						/>

						<%
						String revision = String.valueOf(curPage.getVersion());

						if (curPage.isMinorEdit()) {
							revision += " (" + LanguageUtil.get(request, "minor-edit") + ")";
						}
						%>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="revision"
							value="<%= revision %>"
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="user"
							value="<%= PortalUtil.getUserName(curPage) %>"
						/>

						<liferay-ui:search-container-column-date
							href="<%= rowURL %>"
							name="date"
							value="<%= curPage.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-jsp
							cssClass="entry-action"
							path="/wiki/page_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<liferay-util:include page="/wiki_admin/add_page_button.jsp" servletContext="<%= application %>" />

<aui:script>
	function <portlet:namespace />deletePages() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');

			submitForm(form, '<portlet:actionURL name="/wiki/edit_page" />');
		}
	}
</aui:script>