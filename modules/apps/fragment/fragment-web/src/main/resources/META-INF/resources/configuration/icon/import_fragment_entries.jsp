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
long fragmentCollectionId = ParamUtil.getLong(renderRequest, "fragmentCollectionId");
%>

<liferay-ui:icon
	message="import"
	onClick='<%= renderResponse.getNamespace() + "openImportView()" %>'
	url="javascript:;"
/>

<aui:script>
	function <portlet:namespace />openImportView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					after: {
						destroy: function(event) {
							window.location.reload();
						}
					},
					destroyOnHide: true
				},
				id: '<portlet:namespace />openInstallFromURLView',
				title: '<liferay-ui:message key="import" />',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/fragment/view_import_fragment_entries" /><portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentCollectionId) %>" /></portlet:renderURL>'
			}
		);
	}
</aui:script>