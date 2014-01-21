<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/trash/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "staging");

String redirect = ParamUtil.getString(request, "redirect");

String keywords = ParamUtil.getString(request, "keywords");

long groupId = themeDisplay.getScopeGroupId();

Group group = GroupLocalServiceUtil.getGroup(groupId);

if (group.isStagingGroup() && tabs1.equals("live")) {
	groupId = group.getLiveGroupId();
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/trash/view");
portletURL.setParameter("tabs1", tabs1);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "recycle-bin"), portletURL.toString());

if (Validator.isNotNull(keywords)) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
}
%>

<liferay-util:include page="/html/portlet/trash/restore_path.jsp" />

<liferay-ui:error exception="<%= DuplicateEntryException.class %>">
	<liferay-ui:message key="unable-to-move-this-item-to-the-selected-destination" />
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

<c:if test="<%= group.isStagingGroup() %>">
	<liferay-ui:tabs
		names="staging,live"
		url="<%= portletURL.toString() %>"
	/>
</c:if>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/trash/view" />
</liferay-portlet:renderURL>

<liferay-ui:search-container
	searchContainer="<%= new EntrySearch(renderRequest, portletURL) %>"
>

	<%
	boolean approximate = false;
	%>

	<liferay-ui:search-container-results>

		<%
		EntrySearchTerms searchTerms = (EntrySearchTerms)searchContainer.getSearchTerms();

		if (Validator.isNotNull(searchTerms.getKeywords())) {
			Sort sort = SortFactoryUtil.getSort(TrashEntry.class, searchContainer.getOrderByCol(), searchContainer.getOrderByType());

			BaseModelSearchResult<TrashEntry> baseModelSearchResult = TrashEntryLocalServiceUtil.searchTrashEntries(company.getCompanyId(), groupId, user.getUserId(), searchTerms.getKeywords(), searchContainer.getStart(), searchContainer.getEnd(), sort);

			searchContainer.setTotal(baseModelSearchResult.getLength());

			results = baseModelSearchResult.getBaseModels();
		}
		else {
			TrashEntryList trashEntryList = TrashEntryServiceUtil.getEntries(groupId, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setTotal(trashEntryList.getCount());

			results = TrashEntryImpl.toModels(trashEntryList.getArray());

			approximate = trashEntryList.isApproximate();
		}

		searchContainer.setResults(results);

		if ((searchContainer.getTotal() == 0) && Validator.isNotNull(searchTerms.getKeywords())) {
			searchContainer.setEmptyResultsMessage(LanguageUtil.format(pageContext, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(searchTerms.getKeywords()) + "</strong>", false));
		}
		%>

	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.trash.model.TrashEntry"
		keyProperty="entryId"
		modelVar="entry"
	>

		<%
		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(entry.getClassName());

		TrashRenderer trashRenderer = trashHandler.getTrashRenderer(entry.getClassPK());

		String viewContentURLString = null;

		if (trashRenderer != null) {
			PortletURL viewContentURL = renderResponse.createRenderURL();

			viewContentURL.setParameter("struts_action", "/trash/view_content");
			viewContentURL.setParameter("redirect", currentURL);

			if (entry.getRootEntry() != null) {
				viewContentURL.setParameter("className", entry.getClassName());
				viewContentURL.setParameter("classPK", String.valueOf(entry.getClassPK()));
			}
			else {
				viewContentURL.setParameter("trashEntryId", String.valueOf(entry.getEntryId()));
			}

			viewContentURL.setParameter("type", trashRenderer.getType());
			viewContentURL.setParameter("showActions", Boolean.FALSE.toString());
			viewContentURL.setParameter("showAssetMetadata", Boolean.TRUE.toString());
			viewContentURL.setParameter("showEditURL", Boolean.FALSE.toString());

			viewContentURLString = viewContentURL.toString();
		}
		%>

		<liferay-ui:search-container-column-text
			name="name"
		>
			<liferay-ui:icon
				label="<%= true %>"
				message="<%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %>"
				method="get" src="<%= trashRenderer.getIconPath(renderRequest) %>"
				url="<%= viewContentURLString %>"
			/>

			<c:if test="<%= entry.getRootEntry() != null %>">

				<%
				TrashEntry rootEntry = entry.getRootEntry();

				TrashHandler rootTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(rootEntry.getClassName());

				TrashRenderer rootTrashRenderer = rootTrashHandler.getTrashRenderer(rootEntry.getClassPK());

				String viewRootContentURLString = null;

				if (rootTrashRenderer != null) {
					PortletURL viewContentURL = renderResponse.createRenderURL();

					viewContentURL.setParameter("struts_action", "/trash/view_content");
					viewContentURL.setParameter("redirect", currentURL);
					viewContentURL.setParameter("trashEntryId", String.valueOf(rootEntry.getEntryId()));
					viewContentURL.setParameter("type", rootTrashRenderer.getType());
					viewContentURL.setParameter("showActions", Boolean.FALSE.toString());
					viewContentURL.setParameter("showAssetMetadata", Boolean.TRUE.toString());
					viewContentURL.setParameter("showEditURL", Boolean.FALSE.toString());

					viewRootContentURLString = viewContentURL.toString();
				}
				%>

				<liferay-util:buffer var="rootEntryIcon">
					<liferay-ui:icon
						label="<%= true %>"
						message="<%= HtmlUtil.escape(rootTrashRenderer.getTitle(locale)) %>"
						method="get"
						src="<%= rootTrashRenderer.getIconPath(renderRequest) %>"
						url="<%= viewRootContentURLString %>"
					/>
				</liferay-util:buffer>

				<span class="trash-root-entry">(<liferay-ui:message arguments="<%= rootEntryIcon %>" key="<%= rootTrashHandler.getDeleteMessage() %>" translateArguments="<%= false %>" />)</span>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="type"
			orderable="<%= true %>"
			value="<%= ResourceActionsUtil.getModelResource(locale, entry.getClassName()) %>"
		/>

		<liferay-ui:search-container-column-date
			name="removed-date"
			orderable="<%= true %>"
			value="<%= entry.getCreateDate() %>"
		/>

		<liferay-ui:search-container-column-text
			name="removed-by"
			orderable="<%= true %>"
			value="<%= HtmlUtil.escape(entry.getUserName()) %>"
		/>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(trashRenderer.renderActions(renderRequest, renderResponse)) %>">
				<liferay-ui:search-container-column-jsp
					align="right"
					path="<%= trashRenderer.renderActions(renderRequest, renderResponse) %>"
				/>
			</c:when>
			<c:when test="<%= entry.getRootEntry() == null %>">
				<liferay-ui:search-container-column-jsp
					align="right"
					path="/html/portlet/trash/entry_action.jsp"
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:search-container-column-text align="right">

					<%
					request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

					request.setAttribute(WebKeys.TRASH_RENDERER, trashRenderer);
					%>

					<liferay-util:include page="/html/portlet/trash/view_content_action.jsp" />
				</liferay-ui:search-container-column-text>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<portlet:actionURL var="emptyTrashURL">
		<portlet:param name="struts_action" value="/trash/edit_entry" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	</portlet:actionURL>

	<liferay-ui:trash-empty
		portletURL="<%= emptyTrashURL %>"
		totalEntries="<%= searchContainer.getTotal() %>"
	/>

	<aui:form action="<%= searchURL.toString() %>" method="get" name="fm">
		<liferay-portlet:renderURLParams varImpl="searchURL" />
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="deleteTrashEntryIds" type="hidden" />
		<aui:input name="restoreTrashEntryIds" type="hidden" />

		<liferay-ui:search-form
			page="/html/portlet/trash/entry_search.jsp"
		/>
	</aui:form>

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showCurrentPortlet="<%= true %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<div class="separator"><!-- --></div>

	<liferay-ui:search-iterator type='<%= approximate ? "more" : "regular" %>' />
</liferay-ui:search-container>