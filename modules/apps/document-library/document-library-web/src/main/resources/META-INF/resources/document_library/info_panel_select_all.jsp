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

<%@ include file="/document_library/init.jsp" %>

<div class="sidebar-header">
	<h1 class="component-title">
		<liferay-ui:message key="selection" />
	</h1>
</div>

<div class="sidebar-body">
	<liferay-ui:tabs
		cssClass="navbar-no-collapse"
		names="details"
		refresh="<%= false %>"
	>
		<liferay-ui:section>
			<strong>
				<liferay-ui:message arguments="<%= GetterUtil.getLong(request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_SELECT_ALL_COUNT)) %>" key="x-items-are-selected" />
			</strong>
		</liferay-ui:section>
	</liferay-ui:tabs>
</div>