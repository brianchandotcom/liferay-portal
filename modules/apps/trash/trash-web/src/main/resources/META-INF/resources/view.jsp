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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = renderResponse.createRenderURL();

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
	TrashEntryList trashEntryList = TrashEntryServiceUtil.getEntries(themeDisplay.getScopeGroupId(), entrySearch.getStart(), entrySearch.getEnd(), entrySearch.getOrderByComparator());

	entrySearch.setTotal(trashEntryList.getCount());

	trashEntries = TrashEntryImpl.toModels(trashEntryList.getArray());

	approximate = trashEntryList.isApproximate();
}

entrySearch.setResults(trashEntries);
entrySearch.setRowChecker(new EmptyOnClickRowChecker(renderResponse));

if ((entrySearch.getTotal() == 0) && Validator.isNotNull(searchTerms.getKeywords())) {
	entrySearch.setEmptyResultsMessage(LanguageUtil.format(request, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(searchTerms.getKeywords()) + "</strong>", false));
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "recycle-bin"), portletURL.toString());

if (Validator.isNotNull(keywords)) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "search") + ": " + keywords, currentURL);
}
%>

<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="trash"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= entrySearch.getOrderByCol() %>"
			orderByType="<%= entrySearch.getOrderByType() %>"
			orderColumns='<%= new String[] {"removed-date"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedEntries" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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
</liferay-ui:error>

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

<div class="container-fluid-1280">
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
				className="com.liferay.trash.kernel.model.TrashEntry"
				keyProperty="entryId"
				modelVar="entry"
			>

				<%
				TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(entry.getClassName());

				TrashRenderer trashRenderer = trashHandler.getTrashRenderer(entry.getClassPK());

				String viewContentURLString = null;

				if (trashRenderer != null) {
					PortletURL viewContentURL = renderResponse.createRenderURL();

					if (!trashHandler.isContainerModel()) {
						viewContentURL.setWindowState(LiferayWindowState.POP_UP);

						viewContentURL.setParameter("mvcPath", "/preview.jsp");
					}
					else {
						viewContentURL.setParameter("mvcPath", "/view_content.jsp");
					}

					if (entry.getRootEntry() != null) {
						viewContentURL.setParameter("classNameId", String.valueOf(entry.getClassNameId()));
						viewContentURL.setParameter("classPK", String.valueOf(entry.getClassPK()));
					}
					else {
						viewContentURL.setParameter("trashEntryId", String.valueOf(entry.getEntryId()));
					}

					viewContentURLString = viewContentURL.toString();
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="text-strong"
					name="name"
				>
					<c:choose>
						<c:when test="<%= !trashHandler.isContainerModel() %>">

							<%
							Map<String, Object> data = new HashMap<String, Object>();

							data.put("title", HtmlUtil.escape(trashRenderer.getTitle(locale)));
							data.put("url", viewContentURLString);
							%>

							<aui:a cssClass="preview" data="<%= data %>" href="javascript:;">
								<%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %>
							</aui:a>
						</c:when>
						<c:otherwise>
							<aui:a href="<%= viewContentURLString %>">
								<%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %>
							</aui:a>
						</c:otherwise>
					</c:choose>

					<c:if test="<%= entry.getRootEntry() != null %>">

						<%
						TrashEntry rootEntry = entry.getRootEntry();

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

						<liferay-util:buffer var="rootEntryIcon">
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
					value="<%= ResourceActionsUtil.getModelResource(locale, entry.getClassName()) %>"
				/>

				<liferay-ui:search-container-column-date
					name="removed-date"
					value="<%= entry.getCreateDate() %>"
				/>

				<liferay-ui:search-container-column-text
					name="removed-by"
					value="<%= HtmlUtil.escape(entry.getUserName()) %>"
				/>

				<c:choose>
					<c:when test="<%= Validator.isNotNull(trashRenderer.renderActions(renderRequest, renderResponse)) %>">
						<liferay-ui:search-container-column-jsp
							cssClass="list-group-item-field"
							path="<%= trashRenderer.renderActions(renderRequest, renderResponse) %>"
						/>
					</c:when>
					<c:when test="<%= entry.getRootEntry() == null %>">
						<liferay-ui:search-container-column-jsp
							cssClass="list-group-item-field"
							path="/entry_action.jsp"
						/>
					</c:when>
					<c:otherwise>

						<%
						request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

						request.setAttribute(WebKeys.TRASH_RENDERER, trashRenderer);
						%>

						<liferay-ui:search-container-column-jsp
							cssClass="list-group-item-field"
							path="/view_content_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" type='<%= approximate ? "more" : "regular" %>' />
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedEntries').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>

<aui:script use="liferay-url-preview">
	A.one('#<portlet:namespace />fm').delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var urlPreview = new Liferay.UrlPreview(
				{
					title: currentTarget.attr('data-title'),
					url: currentTarget.attr('data-url')
				}
			);

			urlPreview.open();
		},
		'.preview'
	);
</aui:script>