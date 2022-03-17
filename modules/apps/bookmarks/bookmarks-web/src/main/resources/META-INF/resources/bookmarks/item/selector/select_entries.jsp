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

<%@ include file="/bookmarks/item/selector/init.jsp" %>

<%
BookmarkEntriesItemSelectorDisplayContext bookmarkEntriesItemSelectorDisplayContext = (BookmarkEntriesItemSelectorDisplayContext)request.getAttribute(BookmarksWebKeys.BOOKMARKS_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	displayContext="<%= new BookmarkEntriesItemSelectorManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, bookmarkEntriesItemSelectorDisplayContext.getSearchContainer()) %>"
	searchContainerId="bookmarkEntries"
/>

<div class="container-fluid container-fluid-max-xl item-selector lfr-item-viewer main-content-body" id="<portlet:namespace />bookmarkEntriesContainer">
	<liferay-ui:search-container
		id="bookmarkEntries"
		searchContainer="<%= bookmarkEntriesItemSelectorDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.bookmarks.model.BookmarksEntry"
			escapedModel="<%= true %>"
			keyProperty="entryId"
			modelVar="entry"
		>

			<%
			row.setCssClass("entries");

			JSONObject entryJSONObject = JSONUtil.put(
				"className", BookmarksEntry.class.getName()
			).put(
				"classNameId", PortalUtil.getClassNameId(BookmarksEntry.class.getName())
			).put(
				"classPK", entry.getEntryId()
			).put(
				"name", entry.getName()
			);

			row.setData(
				HashMapBuilder.<String, Object>put(
					"value", entryJSONObject.toString()
				).build());
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(bookmarkEntriesItemSelectorDisplayContext.getDisplayStyle(), "descriptive") %>'>

					<%
					row.setCssClass("item-preview " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-user
						showDetails="<%= false %>"
						userId="<%= entry.getUserId() %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<span class="text-default">
							<liferay-ui:message arguments="<%= entry.getUserName() %>" key="x-modified-x-ago" />
						</span>

						<p class="font-weight-bold h5">
							<%= entry.getName() %>
						</p>

						<span class="text-default">
							<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= entry.getStatus() %>" />
						</span>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>

					<%
					row.setCssClass("item-preview " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200 table-name"
						name="name"
						value="<%= entry.getName() %>"
					/>

					<%
					AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BookmarksEntry.class.getName(), entry.getEntryId());
					%>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= bookmarkEntriesItemSelectorDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="metal-dom/src/all/dom as dom">
	var selectEntryHandler = dom.delegate(
		document.querySelector('#<portlet:namespace />bookmarkEntriesContainer'),
		'click',
		'.entries',
		(event) => {
			<c:choose>
				<c:when test='<%= Objects.equals(bookmarkEntriesItemSelectorDisplayContext.getDisplayStyle(), "icon") %>'>
					dom.removeClasses(
						document.querySelectorAll('.form-check-card.active'),
						'active'
					);
					dom.addClasses(
						dom.closest(event.delegateTarget, '.form-check-card'),
						'active'
					);
				</c:when>
				<c:otherwise>
					dom.removeClasses(
						document.querySelectorAll('.entries.active'),
						'active'
					);
					dom.addClasses(dom.closest(event.delegateTarget, '.entries'), 'active');
				</c:otherwise>
			</c:choose>

			Liferay.Util.getOpener().Liferay.fire(
				'<%= bookmarkEntriesItemSelectorDisplayContext.getItemSelectedEventName() %>',
				{
					data: {
						returnType:
							'<%= InfoItemItemSelectorReturnType.class.getName() %>',
						value: event.delegateTarget.dataset.value,
					},
				}
			);
		}
	);

	Liferay.on('destroyPortlet', function removeListener() {
		selectEntryHandler.removeListener();

		Liferay.detach('destroyPortlet', removeListener);
	});
</aui:script>