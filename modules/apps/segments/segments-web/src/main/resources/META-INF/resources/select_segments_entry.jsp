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
SelectSegmentsEntryDisplayContext selectSegmentsEntryDisplayContext = (SelectSegmentsEntryDisplayContext)request.getAttribute(SegmentsWebKeys.SELECT_SEGMENTS_ENTRY_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	clearResultsURL="<%= selectSegmentsEntryDisplayContext.getClearResultsURL() %>"
	componentId="selectSegmentsEntryManagementToolbar"
	disabled="<%= selectSegmentsEntryDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= selectSegmentsEntryDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= selectSegmentsEntryDisplayContext.getTotalItems() %>"
	searchActionURL="<%= selectSegmentsEntryDisplayContext.getSearchActionURL() %>"
	searchContainerId="selectSegmentsEntry"
	searchFormName="searchFm"
	sortingOrder="<%= selectSegmentsEntryDisplayContext.getOrderByType() %>"
	sortingURL="<%= selectSegmentsEntryDisplayContext.getSortingURL() %>"
/>

<aui:form cssClass="container-fluid-1280" name="selectSegmentsEntryFm">
	<liferay-ui:search-container
		searchContainer="<%= selectSegmentsEntryDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.segments.model.SegmentsEntry"
			keyProperty="segmentsEntryId"
			modelVar="segmentsEntry"
			rowVar="row"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-title"
				name="name"
			>
				<c:choose>
					<c:when test="<%= !ArrayUtil.contains(selectSegmentsEntryDisplayContext.getSelectedSegmentsEntryIds(), segmentsEntry.getSegmentsEntryId()) %>">

						<%
						Map<String, Object> data = new HashMap();

						data.put("entityid", segmentsEntry.getSegmentsEntryId());
						data.put("entityname", segmentsEntry.getName(locale));
						%>

						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= HtmlUtil.escape(segmentsEntry.getName(locale)) %>
						</aui:a>
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(segmentsEntry.getName(locale)) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-150"
				name="scope"
				value="<%= HtmlUtil.escape(selectSegmentsEntryDisplayContext.getGroupDescriptiveName(segmentsEntry)) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smallest table-cell-minw-150 table-cell-ws-nowrap"
				name="modified-date"
				value="<%= segmentsEntry.getModifiedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectSegmentsEntryFm',
		'<%= HtmlUtil.escapeJS(selectSegmentsEntryDisplayContext.getEventName()) %>'
	);
</aui:script>