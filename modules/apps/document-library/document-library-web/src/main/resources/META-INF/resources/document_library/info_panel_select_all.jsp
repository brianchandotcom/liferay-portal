<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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