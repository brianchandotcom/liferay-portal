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
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:app-view-navigation-entry:cssClass"));
Map<String, Object> dataExpand = (Map<String, Object>)request.getAttribute("liferay-ui:app-view-navigation-entry:dataExpand");
Map<String, Object> dataView = (Map<String, Object>)request.getAttribute("liferay-ui:app-view-navigation-entry:dataView");
String entryTitle = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:entryTitle");
String expandURL = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:expandURL");
String iconImage = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:iconImage");
String iconSrc = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:iconSrc");
boolean selected = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-navigation-entry:selected"));
boolean showExpand = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-navigation-entry:showExpand"));
String viewURL = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:viewURL");

Map<String, Object> data = new HashMap<String, Object>();

data.putAll(dataView);

if (browseUp) {
	data.put("direction-right", Boolean.TRUE);
}
else {
	data.put("view-folders", Boolean.FALSE);
}
%>

<aui:nav-item anchorCssClass='<%= "browse-" + cssClass %>' anchorData="<%= data %>" cssClass='<%= "app-view-navigation-entry " + cssClass %>' href="<%= viewURL.toString() %>" iconClass="<%= iconImage %>" label="<%= entryTitle %>" selected="<%= selected %>">

	<%
	request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	%>

	<c:if test="<%= Validator.isNotNull(actionJsp) %>">
		<liferay-util:include page="<%= actionJsp %>" />
	</c:if>

	<c:if test="<%= showExpand %>">
		<span class="expand-folder-container">
			<a class="<%= "expand-" + cssClass %>" data-view-entries="<%= Boolean.FALSE.toString() %>" <%= AUIUtil.buildData(dataExpand) %> href="<%= expandURL.toString() %>">
				<liferay-ui:icon cssClass='<%= "expand-" + cssClass + "-arrow" %>' image='<%= browseUp ? "../aui/carat-1-l" : "../aui/carat-1-r" %>' message="expand" />
			</a>
		</span>
	</c:if>
</aui:nav-item>