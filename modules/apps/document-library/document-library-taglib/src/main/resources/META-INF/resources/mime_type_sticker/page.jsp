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
String cssClass = (String)request.getAttribute("liferay-document-library:mime-type-sticker:cssClass");
DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = (DLViewFileVersionDisplayContext)request.getAttribute("liferay-document-library:mime-type-sticker:dlViewFileVersionDisplayContext");
%>

<div class="sticker sticker-document <%= cssClass %> <%= dlViewFileVersionDisplayContext.getCssClassFileMimeType() %>">
	<clay:icon
		symbol="<%= dlViewFileVersionDisplayContext.getIconFileMimeType() %>"
	/>
</div>