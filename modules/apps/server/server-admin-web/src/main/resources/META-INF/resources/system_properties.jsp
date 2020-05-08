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
String screenNavigationCategoryKey = ParamUtil.getString(request, "screenNavigationCategoryKey", ServerAdminNavigationEntryConstants.CATEGORY_KEY_SYSTEM_PROPERTIES);
String screenNavigationEntryKey = ParamUtil.getString(request, "screenNavigationEntryKey", ServerAdminNavigationEntryConstants.ENTRY_KEY_SYSTEM_PROPERTIES);

PortletURL serverURL = renderResponse.createRenderURL();

serverURL.setParameter("mvcRenderCommandName", "/server_admin/view");
serverURL.setParameter("tabs1", tabs1);
serverURL.setParameter("delta", String.valueOf(delta));
serverURL.setParameter("screenNavigationCategoryKey", screenNavigationCategoryKey);
serverURL.setParameter("screenNavigationEntryKey", screenNavigationEntryKey);

PortletURL clearResultsURL = PortletURLUtil.clone(serverURL, liferayPortletResponse);

clearResultsURL.setParameter("navigation", StringPool.NULL);
clearResultsURL.setParameter("keywords", StringPool.BLANK);

Map<String, String> filteredProperties = new TreeMap<String, String>();

Properties properties = System.getProperties();

for (Map.Entry<Object, Object> entry : properties.entrySet()) {
	String property = String.valueOf(entry.getKey());
	String value = StringPool.BLANK;

	if (ArrayUtil.contains(PropsValues.ADMIN_OBFUSCATED_PROPERTIES, property)) {
		value = StringPool.EIGHT_STARS;
	}
	else {
		value = String.valueOf(entry.getValue());
	}

	if (Validator.isNull(keywords) || property.contains(keywords) || value.contains(keywords)) {
		filteredProperties.put(property, value);
	}
}

List filteredPropertiesList = ListUtil.fromCollection(filteredProperties.entrySet());

SearchContainer propertiesSearchContainer = new SearchContainer(liferayPortletRequest, serverURL, null, null);

propertiesSearchContainer.setResults(ListUtil.subList(filteredPropertiesList, propertiesSearchContainer.getStart(), propertiesSearchContainer.getEnd()));
propertiesSearchContainer.setTotal(filteredPropertiesList.size());
%>

<clay:management-toolbar
	clearResultsURL="<%= String.valueOf(clearResultsURL) %>"
	itemsTotal="<%= propertiesSearchContainer.getTotal() %>"
	searchActionURL="<%= String.valueOf(serverURL) %>"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
/>

<clay:container>
	<liferay-ui:search-container
		emptyResultsMessage='<%= tabs2.equals("portal-properties") ? "no-portal-properties-were-found-that-matched-the-keywords" : "no-system-properties-were-found-that-matched-the-keywords" %>'
		iteratorURL="<%= serverURL %>"
		total="<%= filteredPropertiesList.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= ListUtil.subList(filteredPropertiesList, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="java.util.Map.Entry"
			modelVar="entry"
		>

			<%
			String property = String.valueOf(entry.getKey());
			String value = String.valueOf(entry.getValue());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="property"
				value="<%= HtmlUtil.escape(StringUtil.shorten(property, 80)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="value"
			>
				<c:if test="<%= Validator.isNotNull(value) %>">
					<c:choose>
						<c:when test="<%= value.length() > 80 %>">
							<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(value) %>">
								<%= HtmlUtil.escape(StringUtil.shorten(value, 80)) %>
							</span>
						</c:when>
						<c:otherwise>
							<%= HtmlUtil.escape(value) %>
						</c:otherwise>
					</c:choose>
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container>