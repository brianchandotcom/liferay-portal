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

<%@ include file="/headless_display/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_step_tracker") + StringPool.UNDERLINE;
%>

<div class="table-root" id="<%= randomNamespace + "table-id" %>">
	<span aria-hidden="true" class="loading-animation my-7"></span>

	<react:component
		module="js/FDSTag"
		props='<%= (Map<String, Object>)request.getAttribute("frontend-data-set:headless-display:data") %>'
	/>
</div>