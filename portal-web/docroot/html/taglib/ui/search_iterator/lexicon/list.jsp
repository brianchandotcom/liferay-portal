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

<%
List<ResultRowSplitterEntry> resultRowSplitterEntries = new ArrayList<ResultRowSplitterEntry>();

if (resultRowSplitter != null) {
	resultRowSplitterEntries = resultRowSplitter.split(searchContainer.getResultRows());
}
else if (!resultRows.isEmpty()) {
	resultRowSplitterEntries.add(new ResultRowSplitterEntry(StringPool.BLANK, resultRows));
}

List<com.liferay.portal.kernel.dao.search.ResultRow> firstResultRows = Collections.emptyList();

if (!resultRowSplitterEntries.isEmpty()) {
	ResultRowSplitterEntry firstResultRowSplitterEntry = resultRowSplitterEntries.get(0);

	firstResultRows = firstResultRowSplitterEntry.getResultRows();
}
%>

<div class="table-responsive">
	<table class="table table-autofit table-heading-nowrap table-list">
		<c:if test="<%= ListUtil.isNotNull(headerNames) %>">
			<thead>
				<tr>

					<%
					List entries = Collections.emptyList();

					if (!firstResultRows.isEmpty()) {
						com.liferay.portal.kernel.dao.search.ResultRow row = (com.liferay.portal.kernel.dao.search.ResultRow)firstResultRows.get(0);

						entries = row.getEntries();
					}

					for (int i = 0; i < headerNames.size(); i++) {
						String cssClass = StringPool.BLANK;

						String headerName = headerNames.get(i);

						String normalizedHeaderName = null;

						if (i < normalizedHeaderNames.size()) {
							normalizedHeaderName = normalizedHeaderNames.get(i);

							cssClass = (normalizedHeaderName == "rowChecker") ? "lfr-checkbox-column" : "lfr-" + normalizedHeaderName + "-column";
						}

						if (Validator.isNull(normalizedHeaderName)) {
							normalizedHeaderName = String.valueOf(i +1);

							cssClass = "lfr-entry-action-column";
						}

						boolean truncate = false;

						if (!entries.isEmpty()) {
							if (rowChecker != null) {
								if (i != 0) {
									com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(i - 1);

									if (entry != null) {
										cssClass += " " + entry.getCssClass();

										if (entry.isTruncate()) {
											truncate = true;

											cssClass += " table-cell-content";
										}

										if (!Validator.isBlank(entry.getAlign())) {
											cssClass += " text-" + entry.getAlign();
										}

										if (!Validator.isBlank(entry.getValign())) {
											cssClass += " text-" + entry.getValign();
										}
									}
								}
							}
							else {
								com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(i);

								if (entry != null) {
									cssClass += " " + entry.getCssClass();

									if (entry.isTruncate()) {
										truncate = true;

										cssClass += " table-cell-content";
									}
								}
							}
						}
					%>

						<th class="<%= cssClass %>" id="<%= namespace + id %>_col-<%= normalizedHeaderName %>">

							<%
							String headerNameValue = null;

							if ((rowChecker == null) || (i > 0)) {
								headerNameValue = LanguageUtil.get(resourceBundle, HtmlUtil.escape(headerName));
							}
							else {
								headerNameValue = headerName;
							}

							if (Validator.isNull(headerNameValue)) {
								headerNameValue = StringPool.NBSP;
							}
							%>

							<c:choose>
								<c:when test="<%= truncate %>">
									<span class="truncate-text">
										<%= headerNameValue %>
									</span>
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

			<%
			for (ResultRowSplitterEntry resultRowSplitterEntry : resultRowSplitterEntries) {
				List<com.liferay.portal.kernel.dao.search.ResultRow> curResultRows = resultRowSplitterEntry.getResultRows();
			%>

				<c:if test="<%= Validator.isNotNull(resultRowSplitterEntry.getTitle()) %>">
					<tr class="splitter">
						<td colspan="<%= (headerNames != null) ? headerNames.size() : StringPool.BLANK %>">
							<div class="splitter">
								<liferay-ui:message key="<%= resultRowSplitterEntry.getTitle() %>" />
							</div>
						</td>
					</tr>
				</c:if>

				<%
				boolean allRowsIsChecked = true;

				for (int i = 0; i < curResultRows.size(); i++) {
					com.liferay.portal.kernel.dao.search.ResultRow row = (com.liferay.portal.kernel.dao.search.ResultRow)curResultRows.get(i);

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

						TextSearchEntry textSearchEntry = new TextSearchEntry();

						textSearchEntry.setAlign(rowChecker.getAlign());
						textSearchEntry.setColspan(rowChecker.getColspan());
						textSearchEntry.setCssClass(rowChecker.getCssClass());
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

					<tr class="<%= GetterUtil.getString(row.getClassName()) %> <%= row.getCssClass() %> <%= row.getState() %> <%= rowIsChecked ? "active" : StringPool.BLANK %>" data-qa-id="row" <%= AUIUtil.buildData(data) %>>

						<%
						for (int j = 0; j < entries.size(); j++) {
							com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(j);

							entry.setIndex(j);

							request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);

							boolean truncate = false;

							String columnClassName = entry.getCssClass();

							if (!Validator.isBlank(entry.getAlign())) {
								columnClassName += " text-"+ entry.getAlign();
							}

							if (!Validator.isBlank(entry.getValign())) {
								columnClassName += " text-" + entry.getValign();
							}

							if (entry.isTruncate()) {
								truncate = true;

								columnClassName += " table-cell-content";
							}

							String normalizedColumnName = null;

							if (j < normalizedHeaderNames.size()) {
								normalizedColumnName = normalizedHeaderNames.get(j);

								if (!Validator.isBlank(normalizedColumnName)) {
									columnClassName += (normalizedColumnName == "rowChecker") ? " lfr-checkbox-column" : " lfr-" + normalizedColumnName + "-column";
								}
							}

							if (Validator.isNull(normalizedColumnName)) {
								columnClassName += " lfr-entry-action-column";
							}
						%>

							<td class="<%= columnClassName %>" colspan="<%= entry.getColspan() %>">
								<c:choose>
									<c:when test="<%= truncate %>">
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

<%
String rowHtmlTag = "tr";
%>

<%@ include file="/html/taglib/ui/search_iterator/lexicon/bottom.jspf" %>