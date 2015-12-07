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
List<String> cacheManagerNames = CacheStatisticsUtil.getCacheManagerNames();

if (Validator.isNull(tabs3) && !cacheManagerNames.isEmpty()) {
	tabs3 = cacheManagerNames.get(0);
}

String keywords = ParamUtil.getString(request, "keywords");

PortletURL serverURL = renderResponse.createRenderURL();

serverURL.setParameter("mvcRenderCommandName", "/server_admin/view");
serverURL.setParameter("tabs1", tabs1);
serverURL.setParameter("tabs2", tabs2);
serverURL.setParameter("tabs3", tabs3);

numberFormat = NumberFormat.getInstance(locale);

NumberFormat percentFormat = NumberFormat.getPercentInstance(locale);

percentFormat.setMaximumFractionDigits(3);
%>

<liferay-ui:tabs
	names="<%= StringUtil.merge(cacheManagerNames) %>"
	param="tabs3"
	portletURL="<%= serverURL %>"
/>

<div class="form-search">
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />

	<liferay-ui:input-search placeholder='<%= LanguageUtil.get(request, "keywords") %>'>
		<aui:validator errorMessage='<%= LanguageUtil.format(request, "field-must-contain-only-following-characters-x", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.*", false) %>' name="custom">
			function(val) {
				var pattern = new RegExp('[A-Za-z0-9.*]+');

				if (val.match(pattern)) {
					return true;
				}

				return false;
			}
		</aui:validator>
	</liferay-ui:input-search>
</div>

<liferay-ui:search-container
	emptyResultsMessage="no-caches-found"
	iteratorURL="<%= serverURL %>"
	var="searchContainer"
>

	<liferay-ui:search-container-results>

		<%
		List<CacheStatistics> cacheStatisticsList = CacheStatisticsUtil.getCacheStatisticsList(tabs3, keywords);

		String orderByCol = ParamUtil.getString(request, "orderByCol");

		if (Validator.isNotNull(orderByCol)) {
			Collections.sort(cacheStatisticsList, new BeanComparator(orderByCol));

			String orderByType = ParamUtil.getString(request, "orderByType");

			if (StringUtil.equalsIgnoreCase(orderByType, "asc")) {
				Collections.reverse(cacheStatisticsList);
			}
		}

		pageContext.setAttribute("results", ListUtil.subList(cacheStatisticsList, searchContainer.getStart(), searchContainer.getEnd()));
		pageContext.setAttribute("total", cacheStatisticsList.size());
		%>

	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.server.admin.web.util.CacheStatistics"
		modelVar="cacheStatistics"
		servletContext="<%= application %>"
	>

		<liferay-ui:search-container-column-text
			name="name"
			orderable="<%= true %>"
			orderableProperty="name"
			value="<%= cacheStatistics.getName() %>"
		/>

		<liferay-ui:search-container-column-text
			name="object-count"
			orderable="<%= true %>"
			orderableProperty="objectCount"
			value="<%= numberFormat.format(cacheStatistics.getObjectCount()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="cache-hits"
			orderable="<%= true %>"
			orderableProperty="cacheHits"
			value="<%= numberFormat.format(cacheStatistics.getCacheHits()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="cache-hit-percentage"
			orderable="<%= true %>"
			orderableProperty="cacheHitPercentage"
			value="<%= percentFormat.format(cacheStatistics.getCacheHitPercentage()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="cache-misses"
			orderable="<%= true %>"
			orderableProperty="cacheMisses"
			value="<%= numberFormat.format(cacheStatistics.getCacheMisses()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="cache-miss-percentage"
			orderable="<%= true %>"
			orderableProperty="cacheMissPercentage"
			value="<%= percentFormat.format(cacheStatistics.getCacheMissPercentage()) %>"
		/>

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>