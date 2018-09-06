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

<%@ include file="/discussion/init.jsp" %>

<%
String contents = GetterUtil.getString(request.getAttribute("liferay-comment:editor:contents"));
String name = GetterUtil.getString(request.getAttribute("liferay-comment:editor:name"));
String onChangeMethod = GetterUtil.getString(request.getAttribute("liferay-comment:editor:onChangeMethod"));
String placeholder = GetterUtil.getString(request.getAttribute("liferay-comment:editor:placeholder"));
%>

<liferay-ui:input-editor
	autoCreate="<%= true %>"
	configKey="commentEditor"
	contents="<%= contents %>"
	cssClass="form-control"
	editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.taglib.ui.discussion.jsp") %>'
	name="<%= name %>"
	onChangeMethod="<%= onChangeMethod %>"
	placeholder="<%= placeholder %>"
	showSource="<%= false %>"
	skipEditorLoading="<%= true %>"
/>