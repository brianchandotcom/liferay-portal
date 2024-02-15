<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/search_iterator/init.jsp" %>

<%@ include file="/html/taglib/ui/search_iterator/top.jspf" %>

<%
if (searchResultCssClass == null) {
	searchResultCssClass = "show-quick-actions-on-hover table table-autofit table-head-bordered table-heading-nowrap table-hover table-list table-striped";
}

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

if (fixedHeader) {
	searchResultCssClass += " lfr-search-iterator-fixed-header-table";
}
%>

<div class="table-responsive">
	<table class="<%= searchResultCssClass %>">
		<c:if test="<%= Validator.isNotNull(summary) %>">
			<caption class="sr-only"><%= summary %></caption>
		</c:if>

		<c:if test="<%= ListUtil.isNotNull(headerNames) %>">
			<liferay-util:buffer
				var="theadContent"
			>

				<%
				List entries = Collections.emptyList();

				if (!firstResultRows.isEmpty()) {
					com.liferay.portal.kernel.dao.search.ResultRow row = firstResultRows.get(0);

					entries = row.getEntries();
				}

				for (int i = 0; i < headerNames.size(); i++) {
					String cssClass = StringPool.BLANK;

					String headerName = headerNames.get(i);

					String normalizedHeaderName = null;

					if (i < normalizedHeaderNames.size()) {
						normalizedHeaderName = normalizedHeaderNames.get(i);
					}

					if (Validator.isNotNull(normalizedHeaderName)) {
						cssClass = normalizedHeaderName.equals("rowChecker") ? "lfr-checkbox-column" : "lfr-" + normalizedHeaderName + "-column";
					}
					else {
						normalizedHeaderName = String.valueOf(i + 1);

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

										cssClass += " table-cell-expand";
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

									cssClass += " table-cell-expand";
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
							headerNameValue = HtmlUtil.escape(headerName);
						}

						if (Validator.isNull(headerNameValue)) {
							headerNameValue = StringPool.NBSP;
						}

						String helpMessage = null;

						if (helpMessages != null) {
							helpMessage = helpMessages.get(headerName);
						}
						%>

						<c:choose>
							<c:when test="<%= (rowChecker != null) && (i == 0) %>">
								<span class="sr-only">
									<liferay-ui:message key="item-selection" />
								</span>
							</c:when>
							<c:when test="<%= Validator.isNull(headerName) && (i == (headerNames.size() - 1)) %>">
								<span class="sr-only">
									<liferay-ui:message key="item-actions" />
								</span>
							</c:when>
							<c:when test="<%= truncate %>">
								<span class="text-truncate">
									<%= headerNameValue %>

									<c:if test="<%= Validator.isNotNull(helpMessage) %>">
										<liferay-ui:icon-help message="<%= helpMessage %>" />
									</c:if>
								</span>
							</c:when>
							<c:otherwise>
								<%= headerNameValue %>

								<c:if test="<%= Validator.isNotNull(helpMessage) %>">
									<liferay-ui:icon-help message="<%= helpMessage %>" />
								</c:if>
							</c:otherwise>
						</c:choose>
					</th>

				<%
				}
				%>

			</liferay-util:buffer>

			<thead>
				<tr>
					<%= theadContent %>
				</tr>

				<c:if test="<%= fixedHeader %>">
					<tr aria-hidden="true" class="hide lfr-search-iterator-fixed-header" id="<%= namespace + id %>fixedHeader">
						<th>
							<div class="lfr-search-iterator-fixed-header-inner-wrapper">
								<table>
									<thead>
										<tr>
											<%= theadContent %>
										</tr>
									</thead>
								</table>
							</div>
						</th>
					</tr>
				</c:if>
			</thead>
		</c:if>

		<tbody>

			<%
			for (ResultRowSplitterEntry resultRowSplitterEntry : resultRowSplitterEntries) {
				List<com.liferay.portal.kernel.dao.search.ResultRow> curResultRows = resultRowSplitterEntry.getResultRows();
			%>

				<c:if test="<%= Validator.isNotNull(resultRowSplitterEntry.getTitle()) %>">
					<tr class="table-divider">
						<td colspan="<%= (headerNames != null) ? headerNames.size() : StringPool.BLANK %>">
							<liferay-ui:message key="<%= resultRowSplitterEntry.getTitle() %>" />
						</td>
					</tr>
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

					String rowElementId = namespace + id + "_" + row.getRowId();

					request.setAttribute("liferay-ui:search-container-row:rowElementId", rowElementId);

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
						textSearchEntry.setName(rowChecker.getRowCheckBox(request, row));
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

					<tr class="<%= GetterUtil.getString(row.getClassName()) %> <%= row.getCssClass() %> <%= row.getState() %> <%= rowIsChecked ? "active" : StringPool.BLANK %>" data-qa-id="row" id="<%= rowElementId %>" <%= AUIUtil.buildData(data) %>>

						<%
						for (int j = 0; j < entries.size(); j++) {
							com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(j);

							entry.setIndex(j);

							request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);

							boolean truncate = false;

							String columnClassName = entry.getCssClass();

							if (!Validator.isBlank(entry.getAlign())) {
								columnClassName += " text-" + entry.getAlign();
							}

							if (!Validator.isBlank(entry.getValign())) {
								columnClassName += " text-" + entry.getValign();
							}

							if (entry.isTruncate()) {
								truncate = true;

								columnClassName += " table-cell-expand";
							}

							String normalizedColumnName = null;

							if ((normalizedHeaderNames != null) && (j < normalizedHeaderNames.size())) {
								normalizedColumnName = normalizedHeaderNames.get(j);

								if (!Validator.isBlank(normalizedColumnName)) {
									columnClassName += normalizedColumnName.equals("rowChecker") ? " lfr-checkbox-column" : " lfr-" + normalizedColumnName + "-column";
								}
							}

							if (Validator.isNull(normalizedColumnName)) {
								columnClassName += " lfr-entry-action-column";
							}
						%>

							<td class="<%= columnClassName %>" colspan="<%= entry.getColspan() %>">
								<c:choose>
									<c:when test="<%= truncate %>">
										<span class="text-truncate">

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

					request.removeAttribute("liferay-ui:search-container-row:rowElementId");
					request.removeAttribute("liferay-ui:search-container-row:rowId");
				}
			}
			%>

			<c:if test="<%= headerNames != null %>">
				<tr class="d-none <%= searchContainerRowCssClass %>" data-qa-id="row">

					<%
					for (int i = 0; i < headerNames.size(); i++) {
					%>

						<td></td>

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

<%@ include file="/html/taglib/ui/search_iterator/bottom.jspf" %>