<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

long groupId = themeDisplay.getScopeGroupId();

Group group = GroupLocalServiceUtil.getGroup(groupId);

if (group.isStagingGroup() && tabs1.equals("live")) {
	groupId = group.getLiveGroupId();
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/trash/view");
portletURL.setParameter("tabs1", tabs1);
%>

<c:if test="<%= group.isStagingGroup() %>">
	<liferay-ui:tabs
		names="staging,live"
		url="<%= portletURL.toString() %>"
	/>
</c:if>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/trash/view" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL.toString() %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="deleteEntryIds" type="hidden" />
	<aui:input name="restoreEntryIds" type="hidden" />

	<liferay-ui:search-container
		rowChecker="<%= new RowChecker(renderResponse) %>"
		searchContainer="<%= new EntrySearch(renderRequest, portletURL) %>"
	>

		<%
		EntryDisplayTerms displayTerms = (EntryDisplayTerms)searchContainer.getDisplayTerms();
		EntrySearchTerms searchTerms = (EntrySearchTerms)searchContainer.getSearchTerms();
		boolean aproximate = false;
		%>

		<liferay-ui:search-container-results>

			<%
			if (Validator.isNotNull(searchTerms.getKeywords())) {
				LinkedHashMap entryParams = new LinkedHashMap();

				Sort sort = SortFactoryUtil.getSort(TrashEntry.class, searchContainer.getOrderByCol(), searchContainer.getOrderByType());

				Hits hits = TrashEntryServiceUtil.search(company.getCompanyId(), groupId, user.getUserId(), searchTerms.getKeywords(), searchContainer.getStart(), searchContainer.getEnd(), sort);

				total = hits.getLength();

				pageContext.setAttribute("results", TrashUtil.getEntries(hits));
				pageContext.setAttribute("total", total);
			}
			else {
				Object[] entries = TrashEntryServiceUtil.getEntries(groupId, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

				pageContext.setAttribute("results", entries[0]);
				pageContext.setAttribute("total", entries[1]);

				aproximate = (Boolean)entries[2];
			}

			if ((total == 0) && (Validator.isNotNull(searchTerms.getKeywords()))) {
				searchContainer.setEmptyResultsMessage(LanguageUtil.format(pageContext, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(searchTerms.getKeywords()) + "</strong>"));
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
				viewContentURL.setParameter("entryId", String.valueOf(entry.getEntryId()));
				viewContentURL.setParameter("type", trashRenderer.getType());
				viewContentURL.setParameter("showActions", Boolean.FALSE.toString());
				viewContentURL.setParameter("showAssetMetadata", Boolean.TRUE.toString());
				viewContentURL.setParameter("showEditURL", Boolean.FALSE.toString());

				viewContentURLString = viewContentURL.toString();
			}
			%>

			<liferay-ui:search-container-column-text
				href="<%= viewContentURLString %>"
				name="name"
			>
				<liferay-ui:icon label="<%= true %>" message="<%= trashRenderer.getTitle(locale) %>" src="<%= trashRenderer.getIconPath(renderRequest) %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="type"
				orderable="<%= true %>"
				value="<%= LanguageUtil.get(pageContext, trashRenderer.getType()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="removed-date"
				orderable="<%= true %>"
				value="<%= dateFormatDateTime.format(entry.getCreateDate()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="removed-by"
				orderable="<%= true %>"
				value="<%= entry.getUserName() %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/trash/entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<aui:button-row>
			<liferay-ui:search-form
				page="/html/portlet/trash/entry_search.jsp"
			/>

			<c:if test="<%= total > 0 %>">
				<aui:button name="deleteButton" onClick='<%= renderResponse.getNamespace() + "deleteEntries();" %>' value="delete" />

				<aui:button name="restoreButton" onClick='<%= renderResponse.getNamespace() + "restoreEntries();" %>' value="restore" />

				<aui:button name="emptyTrashButton" onClick='<%= renderResponse.getNamespace() + "emptyTrash();" %>' value="empty-the-recycle-bin" />
			</c:if>
		</aui:button-row>

		<div class="separator"><!-- --></div>

		<liferay-ui:search-iterator type='<%= aproximate ? "more" : "regular" %>' />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.provide(
		window,
		'<portlet:namespace />deleteEntries',
		function() {
			var deleteEntryIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
				document.<portlet:namespace />fm.method = "post";
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
				document.<portlet:namespace />fm.<portlet:namespace />deleteEntryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/trash/edit_entry" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />emptyTrash',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-empty-the-recycle-bin") %>')) {
				document.<portlet:namespace />fm.method = "post";
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EMPTY_TRASH %>";
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/trash/edit_entry" /></portlet:actionURL>");
			}
		}
	);

	Liferay.provide(
		window,
		'<portlet:namespace />restoreEntries',
		function() {
			var restoreEntryIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-restore-the-selected-entries") %>')) {
				document.<portlet:namespace />fm.method = "post";
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.RESTORE %>";
				document.<portlet:namespace />fm.<portlet:namespace />restoreEntryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/trash/edit_entry" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>

<aui:script use="liferay-trash">
	new Liferay.Portlet.Trash(
		{
			checkEntryURL: '<portlet:actionURL><portlet:param name="<%= Constants.CMD %>" value="checkEntry" /><portlet:param name="struts_action" value="/trash/edit_entry" /></portlet:actionURL>',
			namespace: '<portlet:namespace />',
			restoreEntryURL: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/trash/restore_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'
		}
	);
</aui:script>