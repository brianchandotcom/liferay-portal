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

<liferay-ui:input-editor
	autoCreate="<%= true %>"
	configKey="commentEditor"
	contents='<%= ParamUtil.getString(request, "contents") %>'
	editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.taglib.ui.discussion.jsp") %>'
	name='<%= ParamUtil.getString(request, "name") %>'
	onChangeMethod='<%= ParamUtil.getString(request, "onChangeMethod") %>'
	placeholder='<%= ParamUtil.getString(request, "placeholder") %>'
	showSource="<%= false %>"
	skipEditorLoading='<%= ParamUtil.getBoolean(request, "skipEditorLoading") %>'
/>