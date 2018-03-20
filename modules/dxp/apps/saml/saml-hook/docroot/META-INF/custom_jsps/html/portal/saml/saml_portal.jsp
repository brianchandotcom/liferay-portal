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

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ include file="/html/portal/init.jsp" %>

<%
String tilesContent = (String)request.getAttribute("tilesContent");
String tilesPopUp = (String)request.getAttribute("tilesPopUp");
String tilesTitle = (String)request.getAttribute("tilesTitle");
%>

<tiles:insert
	flush="false"
	template="/html/common/themes/portal.jsp"
>
	<tiles:put
		name="content"
		value="<%= tilesContent %>"
	/>

	<tiles:put
		name="pop_up"
		value="<%= tilesPopUp %>"
	/>

	<tiles:put
		name="title"
		value="<%= tilesTitle %>"
	/>
</tiles:insert>