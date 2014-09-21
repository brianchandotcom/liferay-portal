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

<%@ include file="/html/taglib/init.jsp" %>

<%
String actionJsp = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:actionJsp");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:app-view-navigation-entry:cssClass"));
Map<String, Object> dataView = (Map<String, Object>)request.getAttribute("liferay-ui:app-view-navigation-entry:dataView");
String entryTitle = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:entryTitle");
String iconImage = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:iconImage");
boolean selected = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app-view-navigation-entry:selected"));
String viewURL = (String)request.getAttribute("liferay-ui:app-view-navigation-entry:viewURL");

Map<String, Object> data = new HashMap<String, Object>();

data.putAll(dataView);

if (!data.containsKey("view-folders")) {
	data.put("view-folders", Boolean.FALSE);
}
%>

<aui:nav-item anchorCssClass='<%= "browse-" + cssClass %>' anchorData="<%= data %>" cssClass='<%= "app-view-navigation-entry list-group-item navigation-entry " + cssClass %>' href="<%= viewURL.toString() %>" iconCssClass="<%= iconImage %>" label="<%= HtmlUtil.escape(entryTitle) %>" localizeLabel="<%= false %>" selected="<%= selected %>">

	<%
	request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	%>

	<c:if test="<%= Validator.isNotNull(actionJsp) %>">
		<liferay-util:include page="<%= actionJsp %>" />
	</c:if>
</aui:nav-item>