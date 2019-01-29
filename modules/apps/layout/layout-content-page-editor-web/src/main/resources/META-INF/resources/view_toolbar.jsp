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
FragmentsEditorDisplayContext fragmentsEditorDisplayContext = (FragmentsEditorDisplayContext)request.getAttribute(ContentPageEditorWebKeys.LIFERAY_SHARED_FRAGMENTS_EDITOR_DISPLAY_CONTEXT);
%>

<soy:component-renderer
	componentId='<%= PortalUtil.getPortletNamespace(ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET) + "toolbar" %>'
	context="<%= fragmentsEditorDisplayContext.getFragmentsEditorToolbarContext() %>"
	module="layout-admin-web/js/fragments_editor/components/toolbar/FragmentsEditorToolbar.es"
	templateNamespace="com.liferay.layout.admin.web.FragmentsEditorToolbar.render"
	useNamespace="<%= false %>"
/>