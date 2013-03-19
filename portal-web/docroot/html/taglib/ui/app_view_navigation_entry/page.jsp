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

<%@ include file="/html/taglib/init.jsp" %>

<%
String actionJsp = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:actionJsp");
boolean browseUp = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-navigation-entry:browseUp"));
String cssClass = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:cssClass");
Map<String, Object> dataExpand = (Map<String, Object>)request.getAttribute("liferay-ui:app-view-navigation-entry:dataExpand");
Map<String, Object> dataView = (Map<String, Object>)request.getAttribute("liferay-ui:app-view-navigation-entry:dataView");
String entryTitle = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:entryTitle");
String expandURL = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:expandURL");
String iconImage = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:iconImage");
String iconSrc = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:iconSrc");
boolean selected = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-navigation-entry:selected"));
boolean showExpand = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-navigation-entry:showExpand"));
String viewURL = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:viewURL");

String dataDirection = StringPool.BLANK;
String dataExpandFolder = "data-expand-folder=\"" + Boolean.TRUE.toString() + "\"";
String dataViewFolders = "data-view-folders=\"" + Boolean.FALSE.toString() + "\"";

if (browseUp) {
	dataDirection = "data-direction-right=\"" + Boolean.TRUE.toString() + "\"";
	dataExpandFolder = StringPool.BLANK;
	dataViewFolders = StringPool.BLANK;
}
%>

<li class="app-view-navigation-entry <%= selected ? cssClass + " selected" : cssClass %>">

	<%
	request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	%>

	<c:if test="<%= Validator.isNotNull(actionJsp) %>">
		<liferay-util:include page="<%= actionJsp %>" />
	</c:if>

	<c:if test="<%= showExpand %>">
		<a class="<%= "expand-" + cssClass %>" <%= dataDirection %> <%= dataExpandFolder %> data-view-entries="<%= Boolean.FALSE.toString() %>" <%= AUIUtil.buildData(dataExpand) %> href="<%= expandURL.toString() %>">
			<liferay-ui:icon cssClass='<%= "expand-" + cssClass + "-arrow" %>' image='<%= browseUp ? "../aui/carat-1-l" : "../aui/carat-1-r" %>' message="expand" />
		</a>
	</c:if>

	<a class="<%= "browse-" + cssClass %>" <%= dataDirection %> <%= dataViewFolders %> <%= AUIUtil.buildData(dataView) %> href="<%= viewURL.toString() %>">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(iconImage) %>">
				<liferay-ui:icon image="<%= iconImage %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:icon src="<%= iconSrc %>" />
			</c:otherwise>
		</c:choose>

		<span class="entry-title">
			<span class="entry-title-text">
				<%= entryTitle %>
			</span>
		</span>
	</a>
</li>