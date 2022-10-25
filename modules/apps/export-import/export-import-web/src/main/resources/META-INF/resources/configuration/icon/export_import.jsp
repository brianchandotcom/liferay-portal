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

<liferay-util:buffer
	var="onClickFn"
>
	Liferay.Portlet.openModal({
		iframeBodyCssClass: '',
		namespace: '<portlet:namespace />',
		onClose: function() {
			Liferay.Portlet.refresh('#p_p_id_<%= portletDisplay.getId() %>_')
		},
		portletSelector: '#p_p_id_<%= portletDisplay.getId() %>_',
		portletId: '<%= portletDisplay.getId() %>',
		title: '<liferay-ui:message key="export-import" />',
		url: '<%= HtmlUtil.escapeJS(portletDisplay.getURLExportImport()) %>'
	});
</liferay-util:buffer>

<liferay-ui:icon
	cssClass="portlet-export-import portlet-export-import-icon"
	iconCssClass="order-arrow"
	message="export-import"
	onClick="<%= onClickFn %>"
	url="javascript:void(0);"
/>