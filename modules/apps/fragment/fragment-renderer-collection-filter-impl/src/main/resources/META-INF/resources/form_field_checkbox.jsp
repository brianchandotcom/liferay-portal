<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.security.RandomUtil" %>
<%@ page import="com.liferay.portal.kernel.uuid.PortalUUIDUtil" %><%--
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
String name = (String) request.getAttribute("name");
String label = (String) request.getAttribute("label");
String placeholder = GetterUtil.getString(request.getAttribute("placeholder"));

String id = PortalUUIDUtil.generate();
%>
<div class="custom-control custom-checkbox">

	<label>
	<input
		class="custom-control-input"
		type="checkbox"
		name="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet_<%= name %>"
		id="<%= id %>"
	/>
		<span class="custom-control-label">
			<span class="custom-control-label-text">
				<%= label %>
			</span>
		</span>
	</label>
</div>