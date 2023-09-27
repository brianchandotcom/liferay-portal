<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/discussion/init.jsp" %>

<liferay-editor:editor
	autoCreate="<%= true %>"
	configKey="commentEditor"
	contents='<%= GetterUtil.getString(request.getAttribute("liferay-comment:editor:contents")) %>'
	editorName="ckeditor"
	name='<%= GetterUtil.getString(request.getAttribute("liferay-comment:editor:name")) %>'
	onChangeMethod='<%= GetterUtil.getString(request.getAttribute("liferay-comment:editor:onChangeMethod")) %>'
	placeholder="type-your-comment-here"
	showSource="<%= false %>"
	skipEditorLoading="<%= true %>"
/>