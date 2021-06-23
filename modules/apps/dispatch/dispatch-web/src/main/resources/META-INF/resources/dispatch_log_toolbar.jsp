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
SearchContainer<DispatchLog> dispatchLogSearchContainer = DispatchLogSearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new ViewDispatchLogManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, dispatchLogSearchContainer) %>"
/>

<aui:script>
	function <portlet:namespace />deleteDispatchLogs() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-logs" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form.setAttibute('method', 'post');

			form['<%= Constants.CMD %>'].value = '<%= Constants.DELETE %>';
			form['redirect'].value = '<%= currentURL %>';
			form['deleteDispatchLogIds'].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(
				form,
				'<portlet:actionURL name="/dispatch/edit_dispatch_log" />'
			);
		}
	}
</aui:script>