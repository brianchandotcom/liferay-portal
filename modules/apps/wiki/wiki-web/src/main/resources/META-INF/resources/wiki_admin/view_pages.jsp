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

String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, navigation), portletURL.toString());

WikiListPagesDisplayContext wikiListPagesDisplayContext = wikiDisplayContextProvider.getWikiListPagesDisplayContext(request, response, node);

SearchContainer wikiPagesSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, wikiListPagesDisplayContext.getEmptyResultsMessage());

if (Validator.isNull(keywords)) {
	String orderByCol = ParamUtil.getString(request, "orderByCol");
	String orderByType = ParamUtil.getString(request, "orderByType");

	if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
		portalPreferences.setValue(WikiPortletKeys.WIKI_ADMIN, "pages-order-by-col", orderByCol);
		portalPreferences.setValue(WikiPortletKeys.WIKI_ADMIN, "pages-order-by-type", orderByType);
	}
	else {
		orderByCol = portalPreferences.getValue(WikiPortletKeys.WIKI_ADMIN, "pages-order-by-col", "modifiedDate");
		orderByType = portalPreferences.getValue(WikiPortletKeys.WIKI_ADMIN, "pages-order-by-type", "desc");
	}

	request.setAttribute("view_pages.jsp-orderByCol", orderByCol);
	request.setAttribute("view_pages.jsp-orderByType", orderByType);

	wikiPagesSearchContainer.setOrderByType(orderByType);
	wikiPagesSearchContainer.setOrderByCol(orderByCol);
}

wikiPagesSearchContainer.setRowChecker(new PagesChecker(liferayPortletRequest, liferayPortletResponse));

wikiListPagesDisplayContext.populateResultsAndTotal(wikiPagesSearchContainer);

List<WikiPage> pages = wikiPagesSearchContainer.getResults();

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
	disabled="<%= pages.isEmpty() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="wikiPages"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			cssClass="infoPanelToggler"
			disabled="<%= false %>"
			href="javascript:;"
			icon="info-circle"
			label="info"
			sidenavId='<%= liferayPortletResponse.getNamespace() + "infoPanelId" %>'
		/>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive", "list"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= Validator.isNull(keywords) %>">
		<liferay-frontend:management-bar-filters>
			<liferay-util:include page="/wiki_admin/view_pages_filters.jsp" servletContext="<%= application %>" />
		</liferay-frontend:management-bar-filters>
	</c:if>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			disabled="<%= false %>"
			href="javascript:;"
			icon="info-circle"
			label="info"
			sidenavId='<%= liferayPortletResponse.getNamespace() + "infoPanelId" %>'
		/>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deletePages();" %>' iconCssClass='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "icon-trash" : "icon-remove" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/wiki/page_info_panel" var="sidebarPanelURL">
		<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
		<portlet:param name="showSidebarHeader" value="<%= Boolean.TRUE.toString() %>" />
	</liferay-portlet:resourceURL>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="wikiPages"
	>

		<%
		request.setAttribute(WikiWebKeys.SHOW_SIDEBAR_HEADER, true);
		request.setAttribute(WikiWebKeys.WIKI_NODE, node);
		%>

		<liferay-util:include page="/wiki_admin/page_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">

		<%
		WikiVisualizationHelper wikiVisualizationHelper = new WikiVisualizationHelper(wikiRequestHelper, wikiPortletInstanceSettingsHelper, wikiGroupServiceConfiguration);
		%>

		<c:if test="<%= wikiVisualizationHelper.isUndoTrashControlVisible() %>">

			<%
			PortletURL undoTrashURL = wikiURLHelper.getUndoTrashURL();
			%>

			<liferay-trash:undo portletURL="<%= undoTrashURL.toString() %>" />
		</c:if>

		<aui:form action="<%= wikiURLHelper.getSearchURL() %>" method="get" name="fm">
			<aui:input name="nodeId" type="hidden" value="<%= String.valueOf(node.getNodeId()) %>" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-ui:search-container
				id="wikiPages"
				searchContainer="<%= wikiPagesSearchContainer %>"
				total="<%= wikiPagesSearchContainer.getTotal() %>"
			>
				<liferay-ui:search-container-results
					results="<%= wikiPagesSearchContainer.getResults() %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.wiki.model.WikiPage"
					keyProperty="pageId"
					modelVar="curPage"
				>

					<%
					PortletURL rowURL = renderResponse.createRenderURL();

					if (!navigation.equals("draft-pages") || Validator.isNotNull(keywords)) {
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
								icon="wiki-page"
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
											<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(curPage.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
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
								name="revision"
								value="<%= revision %>"
							/>

							<liferay-ui:search-container-column-text
								name="user"
								value="<%= HtmlUtil.escape(PortalUtil.getUserName(curPage)) %>"
							/>

							<liferay-ui:search-container-column-date
								name="modified-date"
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