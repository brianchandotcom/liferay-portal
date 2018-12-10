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
JSONArray breadcrumbEntriesJSONArray = layoutsAdminDisplayContext.getBreadcrumbEntriesJSONArray();
%>

<ol class="breadcrumb">

	<%
	for (int i = 0; i < breadcrumbEntriesJSONArray.length(); i++) {
		JSONObject breadcrumbEntryJSONObject = breadcrumbEntriesJSONArray.getJSONObject(i);
	%>

		<c:choose>
			<c:when test="<%= i < (breadcrumbEntriesJSONArray.length() - 1) %>">
				<li class="breadcrumb-item">
					<a class="breadcrumb-link" href="<%= breadcrumbEntryJSONObject.getString("url") %>">
						<span class="breadcrumb-text-truncate"><%= HtmlUtil.escape(breadcrumbEntryJSONObject.getString("title")) %></span>
					</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="active breadcrumb-item">
					<span class="breadcrumb-text-truncate"><%= HtmlUtil.escape(breadcrumbEntryJSONObject.getString("title")) %></span>
				</li>
			</c:otherwise>
		</c:choose>

	<%
	}
	%>

</ol>

<liferay-ui:search-container
	id="pages"
	searchContainer="<%= layoutsAdminDisplayContext.getLayoutsSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Layout"
		keyProperty="plid"
		modelVar="layout"
	>

		<%
		PortletURL portletURL = layoutsAdminDisplayContext.getPortletURL();

		portletURL.setParameter("selPlid", String.valueOf(layout.getPlid()));
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-cell-minw-200 table-title"
			href="<%= portletURL %>"
			name="title"
			value="<%= layout.getName(locale) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-cell-minw-200"
			name="relative-path"
		>
			<ol class="breadcrumb">

				<%
				List<Layout> curLayouts = layout.getAncestors();

				Collections.reverse(curLayouts);

				boolean showLayoutPath = false;

				if (layoutsAdminDisplayContext.getSelPlid() <= 0) {
					showLayoutPath = true;
				}

				for (Layout curLayout : curLayouts) {
				%>

					<c:if test="<%= showLayoutPath %>">
						<li class="breadcrumb-item">
							<span class="breadcrumb-text-truncate"><%= HtmlUtil.escape(curLayout.getName(locale)) %></span>
						</li>
					</c:if>

				<%
					if (curLayout.getPlid() == layoutsAdminDisplayContext.getSelPlid()) {
						showLayoutPath = true;
					}
				}
				%>

				<li class="active breadcrumb-item">
					<span class="breadcrumb-text-truncate"><%= HtmlUtil.escape(layout.getName(locale)) %></span>
				</li>
			</ol>
		</liferay-ui:search-container-column-text>

		<%
		LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(layout.getType());

		ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-ws-nowrap"
			name="type"
			value='<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + layout.getType()) %>'
		/>

		<liferay-ui:search-container-column-date
			cssClass="table-cell-ws-nowrap"
			name="create-date"
			property="createDate"
		/>

		<liferay-ui:search-container-column-jsp
			path="/layout_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="list"
		markupView="lexicon"
	/>
</liferay-ui:search-container>