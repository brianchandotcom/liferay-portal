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

<%@ include file="/html/taglib/ui/search_iterator/lexicon/top.jsp" %>

<ul class="<%= searchContainer.getCssClass() %> <%= resultRows.isEmpty() ? "hide" : StringPool.BLANK %> tabular-list-group" id="<%= namespace + id %>SearchContainer">
	<c:if test="<%= (headerNames != null) && Validator.isNotNull(headerNames.get(0)) %>">
		<li class="list-group-heading"><liferay-ui:message key="<%= headerNames.get(0) %>" /></li>
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

		<li class="list-group-item <%= GetterUtil.getString(row.getClassName()) %> <%= row.getCssClass() %> <%= rowIsChecked ? "active" : StringPool.BLANK %> <%= Validator.isNotNull(row.getState()) ? "list-group-item-" + row.getState() : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %>>
			<c:if test="<%= rowChecker != null %>">
				<div class="checkbox hidden-sm hidden-x list-group-item-field">
					<label>
						<%= rowChecker.getRowCheckBox(request, rowIsChecked, rowChecker.isDisabled(row.getObject()), row.getPrimaryKey()) %>
					</label>
				</div>
			</c:if>

			<%
			for (int j = 0; j < entries.size(); j++) {
				com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(j);

				entry.setIndex(j);

				request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);
			%>

				<div class="<%= entry.getCssClass() %> <%= entry.getColspan() > 1 ? "list-group-item-content" : "list-group-item-field" %>">

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
String rowHtmlTag = "li";
%>

<%@ include file="/html/taglib/ui/search_iterator/lexicon/bottom.jsp" %>