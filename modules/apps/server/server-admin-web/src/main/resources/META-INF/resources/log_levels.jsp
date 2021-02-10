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
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM, SearchContainer.DEFAULT_DELTA);
String keywords = ParamUtil.getString(request, "keywords");

PortletURL searchURL = renderResponse.createRenderURL();

searchURL.setParameter("mvcRenderCommandName", "/server_admin/view");
searchURL.setParameter("tabs1", tabs1);
searchURL.setParameter("delta", String.valueOf(delta));

PortletURL clearResultsURL = PortletURLUtil.clone(searchURL, liferayPortletResponse);

clearResultsURL.setParameter("navigation", (String)null);
clearResultsURL.setParameter("keywords", StringPool.BLANK);

SearchContainer<Map.Entry<String, String>> loggerSearchContainer = new SearchContainer(liferayPortletRequest, searchURL, null, null);

Map<String, String> currentPriorities = new TreeMap<>();

Map<String, String> priorities = Log4JUtil.getPriorities();

for (Map.Entry<String, String> entry : priorities.entrySet()) {
	String loggerName = entry.getKey();

	if (Validator.isNull(keywords) || loggerName.contains(keywords)) {
		currentPriorities.put(loggerName, entry.getValue());
	}
}

List<Map.Entry<String, String>> currentPrioritiesList = ListUtil.fromCollection(currentPriorities.entrySet());

loggerSearchContainer.setResults(ListUtil.subList(currentPrioritiesList, loggerSearchContainer.getStart(), loggerSearchContainer.getEnd()));
loggerSearchContainer.setTotal(currentPrioritiesList.size());

PortletURL addLogCategoryURL = renderResponse.createRenderURL();

addLogCategoryURL.setParameter("mvcRenderCommandName", "/server_admin/add_log_category");
addLogCategoryURL.setParameter("redirect", currentURL);

CreationMenu creationMenu =
	new CreationMenu() {
		{
			addPrimaryDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(addLogCategoryURL);
					dropdownItem.setLabel(LanguageUtil.get(request, "add-category"));
				});
		}
	};
%>

<clay:management-toolbar-v2
	clearResultsURL="<%= String.valueOf(clearResultsURL) %>"
	creationMenu="<%= creationMenu %>"
	itemsTotal="<%= loggerSearchContainer.getTotal() %>"
	searchActionURL="<%= String.valueOf(searchURL) %>"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= true %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		searchContainer="<%= loggerSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="java.util.Map.Entry"
			modelVar="entry"
		>

			<%
			String name = (String)entry.getKey();
			%>

			<liferay-ui:search-container-column-text
				name="category"
				value="<%= HtmlUtil.escape(name) %>"
			/>

			<liferay-ui:search-container-column-text
				name="level"
			>

				<%
				String priority = (String)entry.getValue();
				%>

				<select name="<%= liferayPortletResponse.getNamespace() + "logLevel" + HtmlUtil.escapeAttribute(name) %>">

					<%
					for (int j = 0; j < allPriorities.length; j++) {
					%>

						<option <%= priority.equals(allPriorities[j]) ? "selected" : StringPool.BLANK %> value="<%= allPriorities[j] %>"><%= allPriorities[j] %></option>

					<%
					}
					%>

				</select>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<aui:button-row>
		<aui:button cssClass="save-server-button" data-cmd="updateLogLevels" value="save" />
	</aui:button-row>
</clay:container-fluid>