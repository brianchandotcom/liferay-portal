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

<%@ include file="/html/taglib/ui/search_iterator/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String markupView = (String)request.getAttribute("liferay-ui:search-iterator:markupView");
boolean paginate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search-iterator:paginate"));
ResultRowSplitter resultRowSplitter = (ResultRowSplitter)request.getAttribute("liferay-ui:search-iterator:resultRowSplitter");
String type = (String)request.getAttribute("liferay-ui:search:type");

String id = searchContainer.getId(request, namespace);

List resultRows = searchContainer.getResultRows();
List<String> headerNames = searchContainer.getHeaderNames();
List<String> normalizedHeaderNames = searchContainer.getNormalizedHeaderNames();
String emptyResultsMessage = searchContainer.getEmptyResultsMessage();
RowChecker rowChecker = searchContainer.getRowChecker();
RowMover rowMover = searchContainer.getRowMover();

if (rowChecker != null) {
	if (headerNames != null) {
		headerNames.add(0, StringPool.BLANK);

		normalizedHeaderNames.add(0, "rowChecker");
	}
}

JSONArray primaryKeysJSONArray = JSONFactoryUtil.createJSONArray();
%>

<c:if test="<%= resultRows.isEmpty() && (emptyResultsMessage != null) %>">
	<liferay-ui:empty-result-message message="<%= emptyResultsMessage %>" />
</c:if>

<div class="<%= resultRows.isEmpty() ? "hide" : StringPool.BLANK %> <%= searchContainer.getCssClass() %>">
	<div id="<%= namespace + id %>SearchContainer">
		<table class="table table-list">

		<c:if test="<%= headerNames != null %>">
			<thead>
				<tr>

				<%
				List entries = Collections.emptyList();

				if (!resultRows.isEmpty()) {
					com.liferay.portal.kernel.dao.search.ResultRow row = (com.liferay.portal.kernel.dao.search.ResultRow)resultRows.get(0);

					entries = row.getEntries();
				}

				for (int i = 0; i < headerNames.size(); i++) {
					String headerName = headerNames.get(i);

					String normalizedHeaderName = null;

					if (i < normalizedHeaderNames.size()) {
						normalizedHeaderName = normalizedHeaderNames.get(i);
					}

					if (Validator.isNull(normalizedHeaderName)) {
						normalizedHeaderName = String.valueOf(i +1);
					}

					String cssClass = StringPool.BLANK;

					if (!entries.isEmpty()) {
						if (rowChecker != null) {
							if (i == 0) {
								cssClass = "checkbox-cell checkbox list-group-item-field";
							}
							else {
								com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(i - 1);

								if (entry != null) {
									cssClass = entry.getCssClass();
								}
							}
						}
						else {
							com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(i);

							if (entry != null) {
								cssClass = entry.getCssClass();
							}
						}
					}
				%>

					<th class="<%= cssClass %>" id="<%= namespace + id %>_col-<%= normalizedHeaderName %>">

						<%
						String headerNameValue = null;

						if ((rowChecker == null) || (i > 0)) {
							headerNameValue = LanguageUtil.get(request, HtmlUtil.escape(headerName));
						}
						else {
							headerNameValue = headerName;
						}
						%>

						<c:choose>
							<c:when test="<%= Validator.isNull(headerNameValue) %>">
								<%= StringPool.NBSP %>
							</c:when>
							<c:otherwise>
								<%= headerNameValue %>
							</c:otherwise>
						</c:choose>
					</th>

				<%
				}
				%>

				</tr>
			</thead>
		</c:if>

		<tbody>

		<c:if test="<%= resultRows.isEmpty() && (emptyResultsMessage != null) %>">
			<tr>
				<td>
					<liferay-ui:empty-result-message message="<%= emptyResultsMessage %>" />
				</td>
			</tr>
		</c:if>

		<%
		boolean allRowsIsChecked = true;

		for (int i = 0; i < resultRows.size(); i++) {
			com.liferay.portal.kernel.dao.search.ResultRow row = (com.liferay.portal.kernel.dao.search.ResultRow)resultRows.get(i);

			primaryKeysJSONArray.put(row.getPrimaryKey());

			request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW, row);

			List entries = row.getEntries();

			boolean rowIsChecked = false;
			boolean rowIsDisabled = false;

			if (rowChecker != null) {
				rowIsDisabled = rowChecker.isDisabled(row.getObject());
				rowIsChecked = rowChecker.isChecked(row.getObject());

				if (!rowIsChecked) {
					allRowsIsChecked = false;
				}

				TextSearchEntry textSearchEntry = new TextSearchEntry();

				textSearchEntry.setAlign(rowChecker.getAlign());
				textSearchEntry.setColspan(rowChecker.getColspan());
				textSearchEntry.setCssClass("checkbox-cell");
				textSearchEntry.setName(rowChecker.getRowCheckBox(request, rowIsChecked, rowIsDisabled, row.getPrimaryKey()));
				textSearchEntry.setValign(rowChecker.getValign());

				row.addSearchEntry(0, textSearchEntry);

				String rowSelector = rowChecker.getRowSelector();

				if (Validator.isNull(rowSelector)) {
					Map<String, Object> rowData = row.getData();

					if (rowData == null) {
						rowData = new HashMap<String, Object>();
					}

					rowData.put("selectable", !rowIsDisabled);

					row.setData(rowData);
				}
			}

			request.setAttribute("liferay-ui:search-container-row:rowId", id.concat(StringPool.UNDERLINE.concat(row.getRowId())));

			Map<String, Object> data = row.getData();

			if (data == null) {
				data = new HashMap<String, Object>();
			}
		%>

			<tr class="<%= GetterUtil.getString(row.getClassName()) %> <%= row.getCssClass() %> <%= row.getState() %> <%= rowIsChecked ? "info" : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %>>

			<%
			for (int j = 0; j < entries.size(); j++) {
				com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(j);

				entry.setIndex(j);

				request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);

				String columnClassName = entry.getCssClass();
			%>

				<td class="<%= columnClassName %> text-<%= entry.getAlign() %> text-<%= entry.getValign() %>" colspan="<%= entry.getColspan() %>">

				<c:choose>
					<c:when test="<%= entry.isTruncate() %>">
						<span class="truncate-text">

							<%
							entry.print(pageContext.getOut(), request, response);
							%>

						</span>
					</c:when>
					<c:otherwise>

						<%
						entry.print(pageContext.getOut(), request, response);
						%>

					</c:otherwise>
				</c:choose>

				</td>

			<%
			}
			%>

			</tr>

		<%
			request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
			request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY);

			request.removeAttribute("liferay-ui:search-container-row:rowId");
		}
		%>

		<c:if test="<%= headerNames != null %>">
			<tr class="lfr-template">

				<%
				for (int i = 0; i < headerNames.size(); i++) {
				%>

					<td class="table-cell"></td>

				<%
				}
				%>

			</tr>
		</c:if>

		</tbody>
		</table>
	</div>

	<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_BOTTOM && paginate %>">
		<div class="taglib-search-iterator-page-iterator-bottom">
			<liferay-ui:search-paginator id='<%= id + "PageIteratorBottom" %>' markupView="<%= markupView %>" searchContainer="<%= searchContainer %>" type="<%= type %>" />
		</div>
	</c:if>
</div>

<c:if test="<%= Validator.isNotNull(id) %>">
	<input id="<%= namespace + id %>PrimaryKeys" name="<%= namespace + id %>PrimaryKeys" type="hidden" value="" />

	<%
	String modules = "liferay-search-container";
	String rowCheckerRowSelector = StringPool.BLANK;

	if (rowMover != null) {
		modules += ",liferay-search-container-move";
	}

	if (rowChecker != null) {
		modules += ",liferay-search-container-select";

		rowCheckerRowSelector = rowChecker.getRowSelector();

		if (Validator.isNull(rowCheckerRowSelector)) {
			rowCheckerRowSelector = "[data-selectable=\"true\"]";
		}
	}
	%>

	<aui:script use="<%= modules %>">
		var plugins = [];

		var rowSelector = 'tr<%= rowCheckerRowSelector %>';

		<c:if test="<%= rowChecker != null %>">
			plugins.push(
				{
					cfg: {
						rowSelector: rowSelector
					},
					fn: A.Plugin.SearchContainerSelect
				}
			);
		</c:if>

		<c:if test="<%= rowMover != null %>">
			var rowMoverConfig = <%= rowMover.toJSON().toString() %>;

			rowMoverConfig.rowSelector = rowSelector + rowMoverConfig.rowSelector;

			plugins.push(
				{
					cfg: rowMoverConfig,
					fn: A.Plugin.SearchContainerMove
				}
			);
		</c:if>

		var searchContainer = new Liferay.SearchContainer(
			{
				id: '<%= namespace + id %>',
				plugins: plugins
			}
		).render();

		searchContainer.updateDataStore(<%= primaryKeysJSONArray.toString() %>);

		var destroySearchContainer = function(event) {
			if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
				searchContainer.destroy();

				Liferay.detach('destroyPortlet', destroySearchContainer);
			}
		};

		Liferay.on('destroyPortlet', destroySearchContainer);
	</aui:script>
</c:if>