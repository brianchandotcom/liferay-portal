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

<%@ include file="/global_menu/init.jsp" %>

<%
GlobalMenuDisplayContext globalMenuDisplayContext = new GlobalMenuDisplayContext(request);
%>

<div class="control-menu-icon lfr-portal-tooltip">
	<clay:button
		icon="grid"
		monospaced="<%= true %>"
		style="unstyled"
		title='<%= LanguageUtil.get(request, "global-menu") %>'
	/>

	<react:component
		data="<%= globalMenuDisplayContext.getGlobalMenuComponentData() %>"
		module="js/GlobalMenu"
	/>
</div>