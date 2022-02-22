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
ViewSystemPropertiesDisplayContext viewSystemPropertiesDisplayContext = new ViewSystemPropertiesDisplayContext(request, liferayPortletRequest, liferayPortletResponse, renderResponse);
%>

<clay:management-toolbar
	clearResultsURL="<%= String.valueOf(viewSystemPropertiesDisplayContext.getClearResultsURL()) %>"
	itemsTotal="<%= viewSystemPropertiesDisplayContext.getSearchContainerTotal() %>"
	searchActionURL="<%= String.valueOf(viewSystemPropertiesDisplayContext.getPortletURL()) %>"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		searchContainer="<%= viewSystemPropertiesDisplayContext.getSearchContainer() %>"
	>
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
</clay:container-fluid>