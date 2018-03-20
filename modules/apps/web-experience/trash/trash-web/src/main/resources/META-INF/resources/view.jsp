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
String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = trashDisplayContext.getPortletURL();

boolean approximate = false;

EntrySearch entrySearch = new EntrySearch(renderRequest, portletURL);

EntrySearchTerms searchTerms = (EntrySearchTerms)entrySearch.getSearchTerms();

List trashEntries = null;

if (Validator.isNotNull(searchTerms.getKeywords())) {
	Sort sort = SortFactoryUtil.getSort(TrashEntry.class, entrySearch.getOrderByCol(), entrySearch.getOrderByType());

	BaseModelSearchResult<TrashEntry> baseModelSearchResult = TrashEntryLocalServiceUtil.searchTrashEntries(company.getCompanyId(), themeDisplay.getScopeGroupId(), user.getUserId(), searchTerms.getKeywords(), entrySearch.getStart(), entrySearch.getEnd(), sort);

	entrySearch.setTotal(baseModelSearchResult.getLength());

	trashEntries = baseModelSearchResult.getBaseModels();
}
else {
	TrashEntryList trashEntryList = null;

	if (Objects.equals(trashDisplayContext.getNavigation(), "all")) {
		trashEntryList = TrashEntryServiceUtil.getEntries(themeDisplay.getScopeGroupId(), entrySearch.getStart(), entrySearch.getEnd(), entrySearch.getOrderByComparator());
	}
	else {
		trashEntryList = TrashEntryServiceUtil.getEntries(themeDisplay.getScopeGroupId(), trashDisplayContext.getNavigation(), entrySearch.getStart(), entrySearch.getEnd(), entrySearch.getOrderByComparator());
	}

	entrySearch.setTotal(trashEntryList.getCount());

	trashEntries = trashEntryList.getOriginalTrashEntries();

	approximate = trashEntryList.isApproximate();
}

entrySearch.setResults(trashEntries);

EmptyOnClickRowChecker emptyOnClickRowChecker = new EmptyOnClickRowChecker(renderResponse);

emptyOnClickRowChecker.setRememberCheckBoxStateURLRegex("^(?!.*" + liferayPortletResponse.getNamespace() + "redirect).*^(?!.*/entry/)");

entrySearch.setRowChecker(emptyOnClickRowChecker);

if ((entrySearch.getTotal() == 0) && Validator.isNotNull(searchTerms.getKeywords())) {
	entrySearch.setEmptyResultsMessage(LanguageUtil.format(request, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(searchTerms.getKeywords()) + "</strong>", false));
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "recycle-bin"), portletURL.toString());

if (Validator.isNotNull(keywords)) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "search") + ": " + keywords, currentURL);
}

request.setAttribute("view.jsp-recycleBinEntrySearch", entrySearch);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= trashDisplayContext.getNavigationItems() %>"
/>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/restore_path.jsp" servletContext="<%= application %>" />

<liferay-ui:error exception="<%= RestoreEntryException.class %>">

	<%
	RestoreEntryException ree = (RestoreEntryException)errorException;
	%>

	<c:if test="<%= ree.getType() == RestoreEntryException.DUPLICATE %>">
		<liferay-ui:message key="unable-to-move-this-item-to-the-selected-destination" />
	</c:if>

	<c:if test="<%= ree.getType() == RestoreEntryException.INVALID_CONTAINER %>">
		<liferay-ui:message key="the-destination-you-selected-is-an-invalid-container.-please-select-a-different-destination" />
	</c:if>

	<c:if test="<%= ree.getType() == RestoreEntryException.INVALID_STATUS %>">
		<liferay-ui:message key="unable-to-restore-this-item" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error exception="<%= TrashEntryException.class %>" message="unable-to-move-this-item-to-the-recycle-bin" />

<liferay-ui:error exception="<%= TrashPermissionException.class %>">

	<%
	TrashPermissionException tpe = (TrashPermissionException)errorException;
	%>

	<c:if test="<%= tpe.getType() == TrashPermissionException.DELETE %>">
		<liferay-ui:message key="you-do-not-have-permission-to-delete-this-item" />
	</c:if>

	<c:if test="<%= tpe.getType() == TrashPermissionException.EMPTY_TRASH %>">
		<liferay-ui:message key="unable-to-completely-empty-trash-you-do-not-have-permission-to-delete-one-or-more-items" />
	</c:if>

	<c:if test="<%= tpe.getType() == TrashPermissionException.MOVE %>">
		<liferay-ui:message key="you-do-not-have-permission-to-move-this-item-to-the-selected-destination" />
	</c:if>

	<c:if test="<%= tpe.getType() == TrashPermissionException.RESTORE %>">
		<liferay-ui:message key="you-do-not-have-permission-to-restore-this-item" />
	</c:if>

	<c:if test="<%= tpe.getType() == TrashPermissionException.RESTORE_OVERWRITE %>">
		<liferay-ui:message key="you-do-not-have-permission-to-replace-an-existing-item-with-the-selected-one" />
	</c:if>

	<c:if test="<%= tpe.getType() == TrashPermissionException.RESTORE_RENAME %>">
		<liferay-ui:message key="you-do-not-have-permission-to-rename-this-item" />
	</c:if>
</liferay-ui:error>

<portlet:actionURL name="deleteEntries" var="deleteEntriesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/trash/info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="trash"
	>
		<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>

		<aui:form action="<%= deleteEntriesURL %>" name="fm">
			<liferay-ui:search-container
				id="trash"
				searchContainer="<%= entrySearch %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.trash.model.TrashEntry"
					keyProperty="entryId"
					modelVar="trashEntry"
					rowVar="row"
				>

					<%
					TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashEntry.getClassName());

					TrashRenderer trashRenderer = trashHandler.getTrashRenderer(trashEntry.getClassPK());

					String viewContentURLString = null;

					if (trashRenderer != null) {
						PortletURL viewContentURL = renderResponse.createRenderURL();

						viewContentURL.setParameter("mvcPath", "/view_content.jsp");

						if (trashEntry.getRootEntry() != null) {
							viewContentURL.setParameter("classNameId", String.valueOf(trashEntry.getClassNameId()));
							viewContentURL.setParameter("classPK", String.valueOf(trashEntry.getClassPK()));
						}
						else {
							viewContentURL.setParameter("trashEntryId", String.valueOf(trashEntry.getEntryId()));
						}

						viewContentURLString = viewContentURL.toString();
					}

					String actionPath = "/view_content_action.jsp";

					if (Validator.isNotNull(trashRenderer.renderActions(renderRequest, renderResponse))) {
						actionPath = trashRenderer.renderActions(renderRequest, renderResponse);
					}
					else if (trashEntry.getRootEntry() == null) {
						actionPath = "/entry_action.jsp";
					}
					else {
						request.setAttribute("view.jsp-className", trashRenderer.getClassName());
						request.setAttribute("view.jsp-classPK", String.valueOf(trashRenderer.getClassPK()));
					}
					%>

					<c:choose>
						<c:when test="<%= trashDisplayContext.isDescriptiveView() %>">
							<liferay-ui:search-container-column-icon
								icon="<%= trashRenderer.getIconCssClass() %>"
								toggleRowChecker="<%= true %>"
							/>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>
								<h6 class="text-default">
									<liferay-ui:message arguments="<%= dateFormatDateTime.format(trashEntry.getCreateDate()) %>" key="removed-x" />
								</h6>

								<h5>
									<aui:a href="<%= viewContentURLString %>">
										<%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %>
									</aui:a>
								</h5>

								<h6 class="text-default">
									<strong><liferay-ui:message key="type" />:</strong> <%= ResourceActionsUtil.getModelResource(locale, trashEntry.getClassName()) %>
								</h6>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-jsp
								path="<%= actionPath %>"
							/>
						</c:when>
						<c:when test="<%= trashDisplayContext.isIconView() %>">

							<%
							row.setCssClass("entry-card lfr-asset-item");
							%>

							<liferay-ui:search-container-column-text>
								<liferay-frontend:icon-vertical-card
									actionJsp="<%= actionPath %>"
									actionJspServletContext="<%= application %>"
									icon="<%= trashRenderer.getIconCssClass() %>"
									resultRow="<%= row %>"
									rowChecker="<%= searchContainer.getRowChecker() %>"
									title="<%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %>"
									url="<%= viewContentURLString %>"
								>
									<%@ include file="/trash_entry_vertical_card.jspf" %>
								</liferay-frontend:icon-vertical-card>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test="<%= trashDisplayContext.isListView() %>">
							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="name"
							>
								<aui:a href="<%= viewContentURLString %>">
									<%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %>
								</aui:a>

								<c:if test="<%= trashEntry.getRootEntry() != null %>">

									<%
									TrashEntry rootEntry = trashEntry.getRootEntry();

									TrashHandler rootTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(rootEntry.getClassName());

									TrashRenderer rootTrashRenderer = rootTrashHandler.getTrashRenderer(rootEntry.getClassPK());

									String viewRootContentURLString = null;

									if (rootTrashRenderer != null) {
										PortletURL viewContentURL = renderResponse.createRenderURL();

										viewContentURL.setParameter("mvcPath", "/view_content.jsp");
										viewContentURL.setParameter("trashEntryId", String.valueOf(rootEntry.getEntryId()));

										viewRootContentURLString = viewContentURL.toString();
									}
									%>

									<liferay-util:buffer
										var="rootEntryIcon"
									>
										<liferay-ui:icon
											label="<%= true %>"
											message="<%= HtmlUtil.escape(rootTrashRenderer.getTitle(locale)) %>"
											method="get"
											url="<%= viewRootContentURLString %>"
										/>
									</liferay-util:buffer>

									<span class="trash-root-entry">(<liferay-ui:message arguments="<%= rootEntryIcon %>" key="<%= rootTrashHandler.getDeleteMessage() %>" translateArguments="<%= false %>" />)</span>
								</c:if>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								name="type"
								value="<%= ResourceActionsUtil.getModelResource(locale, trashEntry.getClassName()) %>"
							/>

							<liferay-ui:search-container-column-date
								name="removed-date"
								value="<%= trashEntry.getCreateDate() %>"
							/>

							<liferay-ui:search-container-column-text
								name="removed-by"
								value="<%= HtmlUtil.escape(trashEntry.getUserName()) %>"
							/>

							<liferay-ui:search-container-column-jsp
								path="<%= actionPath %>"
							/>
						</c:when>
					</c:choose>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= trashDisplayContext.getDisplayStyle() %>"
					markupView="lexicon"
					type='<%= approximate ? "more" : "regular" %>'
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>