<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM, SearchContainer.DEFAULT_DELTA);
String keywords = ParamUtil.getString(request, "keywords");

PortletURL searchURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/server_admin/view"
).setTabs1(
	tabs1
).setParameter(
	"delta", delta
).buildPortletURL();

PortletURL clearResultsURL = PortletURLBuilder.create(
	PortletURLUtil.clone(searchURL, liferayPortletResponse)
).setKeywords(
	StringPool.BLANK
).setNavigation(
	(String)null
).buildPortletURL();

SearchContainer<Map.Entry<String, String>> loggerSearchContainer = new SearchContainer(liferayPortletRequest, searchURL, null, null);

Map<String, String> currentPriorities = new TreeMap<>();

Map<String, String> priorities = Log4JUtil.getPriorities();

for (Map.Entry<String, String> entry : priorities.entrySet()) {
	String loggerName = entry.getKey();

	if (Validator.isNull(keywords) || loggerName.contains(keywords)) {
		currentPriorities.put(loggerName, entry.getValue());
	}
}

loggerSearchContainer.setResultsAndTotal(ListUtil.fromCollection(currentPriorities.entrySet()));

PortletURL addLogCategoryURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/server_admin/add_log_category"
).setRedirect(
	currentURL
).buildPortletURL();

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

<clay:management-toolbar
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
				cssClass="table-cell-expand table-title"
				name="category"
				value="<%= HtmlUtil.escape(name) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-150 table-cell-ws-nowrap"
				name="level"
			>

				<%
				String priority = (String)entry.getValue();
				%>

				<aui:select label="" name='<%= liferayPortletResponse.getNamespace() + "logLevel" + HtmlUtil.escapeAttribute(name) %>' useNamespace="<%= false %>" wrapperCssClass="mb-0">

					<%
					for (int j = 0; j < _ALL_PRIORITIES.length; j++) {
					%>

						<aui:option label="<%= _ALL_PRIORITIES[j] %>" selected="<%= priority.equals(_ALL_PRIORITIES[j]) %>" value="<%= _ALL_PRIORITIES[j] %>" />

					<%
					}
					%>

				</aui:select>
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