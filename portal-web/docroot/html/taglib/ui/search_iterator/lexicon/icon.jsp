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

<div id="<%= namespace + id %>SearchContainer">

	<%
	request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_CHECKER, rowChecker);

	boolean allRowsIsChecked = true;

	List<List<com.liferay.portal.kernel.dao.search.ResultRow>> resultRowsList = new ArrayList<List<com.liferay.portal.kernel.dao.search.ResultRow>>();

	if (resultRowSplitter != null) {
		resultRowsList = resultRowSplitter.split(searchContainer.getResultRows());
	}
	else {
		resultRowsList.add(resultRows);
	}

	for (int i = 0; i < resultRowsList.size(); i++) {
		List<com.liferay.portal.kernel.dao.search.ResultRow> curResultRows = resultRowsList.get(i);
	%>

		<ul class="<%= searchContainer.getCssClass() %> <%= resultRows.isEmpty() ? "hide" : StringPool.BLANK %> list-unstyled row">

			<%
			for (int j = 0; j < curResultRows.size(); j++) {
				com.liferay.portal.kernel.dao.search.ResultRow row = curResultRows.get(j);

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

				<li class="<%= GetterUtil.getString(row.getClassName()) %> <%= row.getCssClass() %> <%= rowIsChecked ? "active" : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %>>

					<%
					for (int k = 0; k < entries.size(); k++) {
						com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(k);

						entry.setIndex(k);

						request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);
					%>

							<%
							entry.print(pageContext.getOut(), request, response);
							%>

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

				<c:if test="<%= i == (resultRowsList.size() - 1) %>">
					<li></li>
				</c:if>
			</ul>

		<%
		}
		%>

</div>

<%
String rowHtmlTag = "li";
%>

<%@ include file="/html/taglib/ui/search_iterator/lexicon/bottom.jspf" %>