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

<%@ include file="/html/taglib/ui/search_iterator/lexicon/top.jspf" %>

<div class="lfr-search-container-list">

	<%
	if (searchResultCssClass == null) {
		searchResultCssClass = "list-group list-group-notification show-quick-actions-on-hover";
	}

	List<ResultRowSplitterEntry> resultRowSplitterEntries = new ArrayList<ResultRowSplitterEntry>();

	if (resultRowSplitter != null) {
		resultRowSplitterEntries = resultRowSplitter.split(searchContainer.getResultRows());
	}
	else {
		resultRowSplitterEntries.add(new ResultRowSplitterEntry(StringPool.BLANK, resultRows));
	}

	for (ResultRowSplitterEntry resultRowSplitterEntry : resultRowSplitterEntries) {
		List<com.liferay.portal.kernel.dao.search.ResultRow> curResultRows = resultRowSplitterEntry.getResultRows();
	%>

		<ul class="<%= searchResultCssClass %>">
			<c:if test="<%= Validator.isNotNull(resultRowSplitterEntry.getTitle()) %>">
				<li class="list-group-header">
					<div class="list-group-header-title">
						<liferay-ui:message key="<%= resultRowSplitterEntry.getTitle() %>" />
					</div>
				</li>
			</c:if>

			<c:if test="<%= (headerNames != null) && Validator.isNotNull(headerNames.get(0)) %>">
				<li class="list-group-header">
					<div class="list-group-header-title">
						<liferay-ui:message key="<%= HtmlUtil.escape(headerNames.get(0)) %>" />
					</div>
				</li>
			</c:if>

			<%
			boolean allRowsIsChecked = true;

			for (int i = 0; i < curResultRows.size(); i++) {
				com.liferay.portal.kernel.dao.search.ResultRow row = curResultRows.get(i);

				primaryKeysJSONArray.put(row.getPrimaryKey());

				request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW, row);

				List entries = row.getEntries();

				boolean rowIsChecked = false;
				boolean rowIsDisabled = false;

				if (rowChecker != null) {
					rowIsChecked = rowChecker.isChecked(row.getObject());
					rowIsDisabled = rowChecker.isDisabled(row.getObject());

					if (!rowIsChecked) {
						allRowsIsChecked = false;
					}

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

				<li class="list-group-item list-group-item-flex <%= GetterUtil.getString(row.getClassName()) %> <%= row.getCssClass() %> <%= rowIsChecked ? "active" : StringPool.BLANK %> <%= Validator.isNotNull(row.getState()) ? "list-group-item-" + row.getState() : StringPool.BLANK %>" data-qa-id="row" <%= AUIUtil.buildData(data) %>>
					<c:if test="<%= rowChecker != null %>">
						<div class="autofit-col">
							<div class="checkbox">
								<label>
									<%= rowChecker.getRowCheckBox(request, row) %>
								</label>
							</div>
						</div>
					</c:if>

					<%
					for (int j = 0; j < entries.size(); j++) {
						com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(j);

						entry.setIndex(j);

						request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);
					%>

						<div class="<%= entry.getCssClass() %> <%= (entry.getColspan() > 1) ? "autofit-col autofit-col-expand" : "autofit-col" %>" data-qa-id="rowItemContent">

							<%
							entry.print(pageContext.getOut(), request, response);
							%>

						</div>

					<%
					}
					%>

				</li>

			<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY);

				request.removeAttribute("liferay-ui:search-container-row:rowId");
			}
			%>

			<li class="lfr-template list-group-item"></li>
		</ul>

	<%
	}

	String rowHtmlTag = "li";
	%>

</div>

<%@ include file="/html/taglib/ui/search_iterator/lexicon/bottom.jspf" %>