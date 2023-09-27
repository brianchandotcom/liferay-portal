<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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