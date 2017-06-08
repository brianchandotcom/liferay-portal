<%@ page import="com.liferay.document.library.web.portlet.action.ActionUtil" %>
<%@ page import="com.liferay.portal.kernel.repository.model.FileEntry" %>
<%@ page
		import="com.liferay.document.library.web.display.context.logic.UIItemsBuilder" %>
<%@ page
		import="com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptMenuItem" %>
<%@ page import="com.liferay.portal.kernel.repository.model.FileVersion" %>
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


<%
FileEntry fileEntry = ActionUtil.getFileEntry(liferayPortletRequest);

FileVersion fileVersion = ActionUtil.getFileVersion(liferayPortletRequest, fileEntry);

UIItemsBuilder uiItemsBuilder = new UIItemsBuilder(request, fileVersion);

JavaScriptMenuItem javaScriptMenuItem = uiItemsBuilder.getJavacriptEditWithImageEditorMenuItem();
%>

<liferay-ui:menu-item menuItem="<%= javaScriptMenuItem %>" />