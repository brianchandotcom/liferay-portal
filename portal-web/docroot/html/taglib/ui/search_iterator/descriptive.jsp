<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/search_iterator/init.jsp" %>

<%@ include file="/html/taglib/ui/search_iterator/top.jspf" %>

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

		<dl class="<%= searchResultCssClass %>">
			<c:choose>
				<c:when test="<%= Validator.isNotNull(resultRowSplitterEntry.getTitle()) || ((headerNames != null) && Validator.isNotNull(headerNames.get(0))) %>">
					<c:if test="<%= Validator.isNotNull(resultRowSplitterEntry.getTitle()) %>">
						<dt class="list-group-header">
							<div class="list-group-header-title">
								<liferay-ui:message key="<%= resultRowSplitterEntry.getTitle() %>" />
							</div>
						</dt>
					</c:if>

					<c:if test="<%= (headerNames != null) && Validator.isNotNull(headerNames.get(0)) %>">
						<dt class="list-group-header">
							<div class="list-group-header-title">
								<liferay-ui:message key="<%= HtmlUtil.escape(headerNames.get(0)) %>" />
							</div>
						</dt>
					</c:if>
				</c:when>
				<c:otherwise>
					<dt class="sr-only">
						<%= PortalUtil.getPortletTitle(portletRequest) %>
					</dt>
				</c:otherwise>
			</c:choose>

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

				String rowElementId = namespace + id + "_" + row.getRowId();

				request.setAttribute("liferay-ui:search-container-row:rowElementId", rowElementId);
			%>

				<dd class="list-group-item list-group-item-flex <%= GetterUtil.getString(row.getClassName()) %> <%= row.getCssClass() %> <%= rowIsChecked ? "active" : StringPool.BLANK %> <%= Validator.isNotNull(row.getState()) ? "list-group-item-" + row.getState() : StringPool.BLANK %>" data-qa-id="row" id="<%= rowElementId %>" <%= AUIUtil.buildData(data) %>>
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

				</dd>

			<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY);

				request.removeAttribute("liferay-ui:search-container-row:rowElementId");
				request.removeAttribute("liferay-ui:search-container-row:rowId");
			}
			%>

			<dd class="d-none list-group-item"></dd>
		</dl>

	<%
	}

	String rowHtmlTag = "dd";
	%>

</div>

<%@ include file="/html/taglib/ui/search_iterator/bottom.jspf" %>