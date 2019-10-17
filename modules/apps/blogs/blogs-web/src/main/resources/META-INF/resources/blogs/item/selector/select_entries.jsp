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

<%@ include file="/blogs/item/selector/init.jsp" %>

<%
BlogEntriesItemSelectorDisplayContext blogEntriesItemSelectorDisplayContext = (BlogEntriesItemSelectorDisplayContext)request.getAttribute(BlogsWebKeys.BLOGS_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	displayContext="<%= new BlogEntriesItemSelectorManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, blogEntriesItemSelectorDisplayContext.getSearchContainer()) %>"
	searchContainerId="blogEntries"
/>

<div class="container-fluid container-fluid-max-xl item-selector lfr-item-viewer main-content-body" id="<portlet:namespace />blogEntriesContainer">
	<liferay-ui:search-container
		id="blogEntries"
		searchContainer="<%= blogEntriesItemSelectorDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.blogs.model.BlogsEntry"
			escapedModel="<%= true %>"
			keyProperty="entryId"
			modelVar="entry"
		>

			<%
			row.setCssClass("entries");

			Map<String, Object> data = new HashMap<>();

			JSONObject entryJSONObject = JSONFactoryUtil.createJSONObject();

			entryJSONObject.put("className", BlogsEntry.class.getName());
			entryJSONObject.put("classNameId", PortalUtil.getClassNameId(BlogsEntry.class.getName()));
			entryJSONObject.put("classPK", entry.getEntryId());
			entryJSONObject.put("title", BlogsEntryUtil.getDisplayTitle(resourceBundle, entry));

			data.put("value", entryJSONObject.toString());

			row.setData(data);
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(blogEntriesItemSelectorDisplayContext.getDisplayStyle(), "descriptive") %>'>

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

						<%
						Date modifiedDate = entry.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
						%>

						<span class="text-default">
							<liferay-ui:message arguments="<%= new String[] {entry.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</span>

						<p class="font-weight-bold h5">
							<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) %>
						</p>

						<span class="text-default">
							<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= entry.getStatus() %>" />
						</span>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(blogEntriesItemSelectorDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new BlogsEntryItemSelectorVerticalCard(entry, renderRequest, resourceBundle) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>

					<%
					row.setCssClass("item-preview " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200 table-title"
						name="title"
						value="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest table-cell-minw-150"
						name="author"
						property="userName"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
						name="display-date"
						property="displayDate"
					/>

					<%
					AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BlogsEntry.class.getName(), entry.getEntryId());
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-column-text-end"
						name="views"
						value="<%= String.valueOf(assetEntry.getViewCount()) %>"
					/>

					<liferay-ui:search-container-column-status
						name="status"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= blogEntriesItemSelectorDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="metal-dom/src/all/dom as dom">
	var selectEntryHandler = dom.delegate(
		document.querySelector('#<portlet:namespace/>blogEntriesContainer'),
		'click',
		'.entries',
		function(event) {
			<c:choose>
				<c:when test='<%= Objects.equals(blogEntriesItemSelectorDisplayContext.getDisplayStyle(), "icon") %>'>
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
				'<%= blogEntriesItemSelectorDisplayContext.getItemSelectedEventName() %>',
				{
					data: {
						returnType:
							'<%= InfoItemItemSelectorReturnType.class.getName() %>',
						value: event.delegateTarget.dataset.value
					}
				}
			);
		}
	);

	Liferay.on('destroyPortlet', function removeListener() {
		selectEntryHandler.removeListener();

		Liferay.detach('destroyPortlet', removeListener);
	});
</aui:script>