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
if (searchResultCssClass == null) {
	searchResultCssClass = "table table-bordered table-hover table-striped";
}

int end = searchContainer.getEnd();
int total = searchContainer.getTotal();

if (end > total) {
	end = total;
}

if (rowChecker != null) {
	if (headerNames != null) {
		headerNames.add(0, rowChecker.getAllRowsCheckBox(request));

		normalizedHeaderNames.add(0, "rowChecker");
	}
}

String url = StringPool.BLANK;

PortletURL iteratorURL = searchContainer.getIteratorURL();

if (iteratorURL != null) {
	url = iteratorURL.toString();
	url = HttpUtil.removeParameter(url, namespace + searchContainer.getOrderByColParam());
	url = HttpUtil.removeParameter(url, namespace + searchContainer.getOrderByTypeParam());
}
%>

<c:if test="<%= emptyResultsMessage != null %>">
	<div class="alert alert-info <%= resultRows.isEmpty() ? StringPool.BLANK : "hide" %>" id="<%= namespace + id %>EmptyResultsMessage">
		<%= LanguageUtil.get(resourceBundle, emptyResultsMessage) %>
	</div>
</c:if>

<div class="lfr-search-container lfr-search-container-wrapper <%= resultRows.isEmpty() ? "hide" : StringPool.BLANK %> <%= searchContainer.getCssClass() %>">
	<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP && (resultRows.size() > PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP_DELTA) && paginate %>">
		<div class="taglib-search-iterator-page-iterator-top">
			<liferay-ui:search-paginator
				id='<%= id + "PageIteratorTop" %>'
				searchContainer="<%= searchContainer %>"
				type="<%= type %>"
			/>
		</div>
	</c:if>

	<div id="<%= namespace + id %>SearchContainer">
		<table class="<%= searchResultCssClass %>">

		<c:if test="<%= Validator.isNotNull(summary) %>">
			<caption class="sr-only"><%= summary %></caption>
		</c:if>

		<c:if test="<%= ListUtil.isNotNull(headerNames) %>">
			<thead class="table-columns">
				<tr>

				<%
				for (int i = 0; i < headerNames.size(); i++) {
					String headerName = headerNames.get(i);

					String normalizedHeaderName = null;

					if (i < normalizedHeaderNames.size()) {
						normalizedHeaderName = normalizedHeaderNames.get(i);
					}

					if (Validator.isNull(normalizedHeaderName)) {
						normalizedHeaderName = String.valueOf(i + 1);
					}

					String orderKey = null;
					String orderByType = null;
					boolean orderCurrentHeader = false;

					if (orderableHeaders != null) {
						orderKey = (String)orderableHeaders.get(headerName);

						if (orderKey != null) {
							orderByType = searchContainer.getOrderByType();

							if (orderKey.equals(searchContainer.getOrderByCol())) {
								orderCurrentHeader = true;
							}
						}
					}

					String cssClass = StringPool.BLANK;

					if (headerNames.size() == 1) {
						cssClass = "only";
					}
					else if (i == 0) {
						cssClass = "table-first-header";
					}
					else if (i == (headerNames.size() - 1)) {
						cssClass = "table-last-header";
					}

					if (orderCurrentHeader) {
						cssClass += " table-sorted";

						if (HtmlUtil.escapeAttribute(orderByType).equals("desc")) {
							cssClass += " table-sorted-desc";
						}
					}

					if (Objects.equals(orderByType, "asc")) {
						orderByType = "desc";
					}
					else {
						orderByType = "asc";
					}
				%>

					<th
						class="<%= cssClass %>"
						id="<%= namespace + id %>_col-<%= normalizedHeaderName %>"

						<%--

						// Minimize the width of the first column if and only if
						// it is a row checker.

						--%>

						<c:if test="<%= (rowChecker != null) && (i == 0) %>">
							width="1%"
						</c:if>
					>
						<c:if test="<%= orderKey != null %>">
							<div class="table-sort-liner">

								<%
								String orderByJS = searchContainer.getOrderByJS();
								%>

								<c:choose>
									<c:when test="<%= Validator.isNull(orderByJS) %>">

										<%
										url = HttpUtil.setParameter(url, namespace + searchContainer.getOrderByColParam(), orderKey);
										url = HttpUtil.setParameter(url, namespace + searchContainer.getOrderByTypeParam(), orderByType);
										%>

										<a href="<%= url %>">
									</c:when>
									<c:otherwise>
										<a href="<%= StringUtil.replace(orderByJS, new String[] {"orderKey", "orderByType"}, new String[] {orderKey, orderByType}) %>">
									</c:otherwise>
								</c:choose>
						</c:if>

							<%
							String headerNameValue = null;

							if ((rowChecker == null) || (i > 0)) {
								headerNameValue = LanguageUtil.get(resourceBundle, HtmlUtil.escape(headerName));
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

						<c:if test="<%= orderKey != null %>">
									<span class="table-sort-indicator"></span>
								</a>
							</div>
						</c:if>
					</th>

				<%
				}
				%>

				</tr>
			</thead>
		</c:if>

		<tbody class="table-data">

		<%
		boolean allRowsIsChecked = true;

		for (int i = 0; i < resultRows.size(); i++) {
			com.liferay.portal.kernel.dao.search.ResultRow row = (com.liferay.portal.kernel.dao.search.ResultRow)resultRows.get(i);

			primaryKeysJSONArray.put(row.getPrimaryKey());

			request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW, row);

			List entries = row.getEntries();

			boolean rowIsChecked = false;

			if (rowChecker != null) {
				rowIsChecked = rowChecker.isChecked(row.getObject());

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
			}

			request.setAttribute("liferay-ui:search-container-row:rowId", id.concat(StringPool.UNDERLINE.concat(row.getRowId())));

			Map<String, Object> data = row.getData();
		%>

			<tr class="<%= GetterUtil.getString(row.getClassName()) %> <%= row.getCssClass() %> <%= row.getState() %> <%= rowIsChecked ? "info" : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %>>

			<%
			for (int j = 0; j < entries.size(); j++) {
				com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(j);

				String normalizedHeaderName = null;

				if ((normalizedHeaderNames != null) && (j < normalizedHeaderNames.size())) {
					normalizedHeaderName = normalizedHeaderNames.get(j);
				}

				if (Validator.isNull(normalizedHeaderName)) {
					normalizedHeaderName = String.valueOf(j + 1);
				}

				entry.setIndex(j);

				request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);

				String columnClassName = entry.getCssClass();

				if (entries.size() == 1) {
					columnClassName += " only";
				}
				else if (j == 0) {
					columnClassName += " first";
				}
				else if ((j + 1) == entries.size()) {
					columnClassName += " last";
				}

				if (!Validator.isBlank(entry.getAlign())) {
					columnClassName += " text-" + entry.getAlign();
				}

				if (!Validator.isBlank(entry.getValign())) {
					columnClassName += " text-" + entry.getValign();
				}
			%>

				<td class="table-cell <%= columnClassName %>" colspan="<%= entry.getColspan() %>">

					<%
					entry.print(pageContext.getOut(), request, response);
					%>

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
			<liferay-ui:search-paginator
				id='<%= id + "PageIteratorBottom" %>'
				searchContainer="<%= searchContainer %>"
				type="<%= type %>"
			/>
		</div>
	</c:if>
</div>

<c:if test="<%= (rowChecker != null) && !resultRows.isEmpty() && Validator.isNotNull(rowChecker.getAllRowsId()) && allRowsIsChecked %>">
	<script>
		(function() {
			var form = document.<%= rowChecker.getFormName() %>;

			var allRowsIdCheckbox = form.querySelector('#<%= namespace + id %>SearchContainer input[name="<%= rowChecker.getAllRowsId() %>"]');

			if (allRowsIdCheckbox) {
				allRowsIdCheckbox.checked = true;
			}
		})();
	</script>
</c:if>

<c:if test="<%= Validator.isNotNull(id) %>">
	<input id="<%= namespace + id %>PrimaryKeys" name="<%= namespace + id %>PrimaryKeys" type="hidden" value="" />

	<aui:script use="liferay-search-container">
		var searchContainer = new Liferay.SearchContainer(
			{
				id: '<%= namespace + id %>'
			}
		).render();

		searchContainer.updateDataStore(<%= primaryKeysJSONArray.toString() %>);
	</aui:script>
</c:if>