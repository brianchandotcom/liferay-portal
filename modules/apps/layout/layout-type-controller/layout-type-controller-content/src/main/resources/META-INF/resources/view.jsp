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
FragmentsEditorDisplayContext fragmentsEditorDisplayContext = new FragmentsEditorDisplayContext(request, renderResponse);
%>

<liferay-editor:resources
	editorName="alloyeditor"
/>

<soy:component-renderer
	context="<%= fragmentsEditorDisplayContext.getFragmentEntryLinkListContext() %>"
	module="layout-admin-web/js/fragments_editor/components/fragment_entry_link/FragmentEntryLinkList.es"
	templateNamespace="com.liferay.layout.admin.web.FragmentEntryLinkList.render"
/>