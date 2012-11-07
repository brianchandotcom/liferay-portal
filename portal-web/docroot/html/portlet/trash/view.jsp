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
%>

<c:if test="<%= SessionMessages.contains(renderRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA) %>">
	<div class="portlet-msg-success">

		<%
		Map<String, List<String>> data = (HashMap<String, List<String>>)SessionMessages.get(renderRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA);

		List<String> restoreLinks = data.get("restoreLinks");
		List<String> restoreMessages = data.get("restoreMessages");
		%>

		<c:choose>
			<c:when test="<%= (data != null) && (restoreLinks != null) && (restoreMessages != null) && (restoreLinks.size() > 0) && (restoreMessages.size() > 0) %>">

				<%
				StringBundler sb = new StringBundler(5 * restoreMessages.size());

				for (int i = 0; i < restoreLinks.size(); i++) {
					sb.append("<a href=\"");
					sb.append(restoreLinks.get(i));
					sb.append("\">");
					sb.append(restoreMessages.get(i));
					sb.append("</a> ");
				}
				%>

				<liferay-ui:message arguments="<%= sb.toString() %>" key="the-item-has-been-restored-to-x" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="the-item-has-been-restored" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<c:if test="<%= group.isStagingGroup() %>">
	<liferay-ui:tabs
		names="staging,live"
		url="<%= portletURL.toString() %>"
	/>
</c:if>

<c:if test="<%= Validator.isNotNull(keywords) %>">
	<liferay-ui:header
		backURL="<%= redirect %>"
		title="search"
	/>
</c:if>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/trash/view" />
	<portlet:param name="redirect" value="<%= redirect %>" />
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

			Hits hits = TrashEntryLocalServiceUtil.search(company.getCompanyId(), groupId, user.getUserId(), searchTerms.getKeywords(), searchContainer.getStart(), searchContainer.getEnd(), sort);

			pageContext.setAttribute("results", TrashUtil.getEntries(hits));
			pageContext.setAttribute("total", hits.getLength());
		}
		else {
			TrashEntryList trashEntryList = TrashEntryServiceUtil.getEntries(groupId, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			pageContext.setAttribute("results", TrashEntryImpl.toModels(trashEntryList.getArray()));
			pageContext.setAttribute("total", trashEntryList.getCount());

			approximate = trashEntryList.isApproximate();
		}

		if ((total == 0) && Validator.isNotNull(searchTerms.getKeywords())) {
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
			<liferay-ui:icon label="<%= true %>" message="<%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %>" src="<%= trashRenderer.getIconPath(renderRequest) %>" url="<%= viewContentURLString %>" />

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
						message="<%= rootTrashRenderer.getTitle(locale) %>"
						src="<%= rootTrashRenderer.getIconPath(renderRequest) %>"
						url="<%= viewRootContentURLString %>"
					/>
				</liferay-util:buffer>

				<span class="trash-root-entry">(<liferay-ui:message arguments="<%= rootEntryIcon %>" key="<%= rootTrashHandler.getDeleteMessage() %>" />)</span>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="type"
			orderable="<%= true %>"
			value="<%= ResourceActionsUtil.getModelResource(locale, entry.getClassName()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="removed-date"
			orderable="<%= true %>"
		>
			<span title="<liferay-ui:message arguments="<%= dateFormatDateTime.format(entry.getCreateDate()) %>" key="deleted-x" />">

				<%
				Date createDate = entry.getCreateDate();
				%>

				<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(pageContext, System.currentTimeMillis() - createDate.getTime(), true) %>" key="x-ago" />
			</span>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="removed-by"
			orderable="<%= true %>"
			value="<%= HtmlUtil.escape(entry.getUserName()) %>"
		/>

		<c:choose>
			<c:when test="<%= entry.getRootEntry() == null || Validator.isNotNull(trashRenderer.renderActions(renderRequest, renderResponse)) %>">
				<liferay-ui:search-container-column-jsp
					align="right"
					path='<%= entry.getRootEntry() == null ? "/html/portlet/trash/entry_action.jsp" : trashRenderer.renderActions(renderRequest, renderResponse) %>'
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:search-container-column-text> </liferay-ui:search-container-column-text>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<portlet:actionURL var="emptyTrashURL">
		<portlet:param name="struts_action" value="/trash/edit_entry" />
	</portlet:actionURL>

	<liferay-ui:trash-empty portletURL="<%= emptyTrashURL %>" totalEntries="<%= total %>" />

	<aui:form action="<%= searchURL.toString() %>" method="get" name="fm">
		<liferay-portlet:renderURLParams varImpl="searchURL" />
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteTrashEntryIds" type="hidden" />
		<aui:input name="restoreTrashEntryIds" type="hidden" />

		<aui:button-row>
			<liferay-ui:search-form
				page="/html/portlet/trash/entry_search.jsp"
			/>
		</aui:button-row>
	</aui:form>

	<div class="separator"><!-- --></div>

	<liferay-ui:search-iterator type='<%= approximate ? "more" : "regular" %>' />
</liferay-ui:search-container>

<aui:script use="liferay-restore-entry">
	new Liferay.RestoreEntry(
		{
			checkEntryURL: '<portlet:actionURL><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECK %>" /><portlet:param name="struts_action" value="/trash/edit_entry" /></portlet:actionURL>',
			namespace: '<portlet:namespace />',
			restoreEntryURL: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/trash/restore_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'
		}
	);
</aui:script>

<%
if (Validator.isNotNull(keywords)) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
}
%>