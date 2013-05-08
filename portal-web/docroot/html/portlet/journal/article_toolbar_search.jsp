<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String keywords = ParamUtil.getString(request, "keywords");

boolean advancedSearch = ParamUtil.getBoolean(request, ArticleDisplayTerms.ADVANCED_SEARCH);

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view");
portletURL.setParameter("folderId", String.valueOf(folderId));
%>

<div class="lfr-search-combobox search-button-container" id="<portlet:namespace />articlesSearchContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1" onSubmit="event.preventDefault();">
		<aui:row>
			<aui:col width="<%= 33 %>">
				<aui:input
					cssClass="keywords lfr-search-combobox-item"
					id="keywords"
					label=""
					name="keywords"
					type="text"
					value="<%= keywords %>"
				/>
			</aui:col>

			<aui:col width="<%= 33 %>">
				<aui:button
					cssClass="lfr-search-combobox-item search"
					name="search"
					type="submit"
					value="search"
				/>
			</aui:col>

			<aui:col cssClass="advanced-search-column" width="<%= 33 %>">
				<aui:button
					cssClass="article-advanced-search-button lfr-search-combobox-item"
					icon='<%= advancedSearch ? "icon-chevron-up" : "icon-chevron-down" %>'
					name="showAdvancedSearch"
					type="button"
				/>
			</aui:col>
		</aui:row>
	</aui:form>
</div>