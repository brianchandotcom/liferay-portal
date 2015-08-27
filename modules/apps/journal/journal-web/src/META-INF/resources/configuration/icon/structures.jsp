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
String taglibOnClick = renderResponse.getNamespace() + "openStructuresView()";
%>

<liferay-ui:icon
	iconCssClass="icon-th-large"
	message="structures"
	onClick="<%= taglibOnClick %>"
	url="javascript:;"
/>

<aui:script>

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	%>

	function <portlet:namespace />openStructuresView() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.VIEW), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				dialog: {
					destroyOnHide: true,
					on: {
						visibleChange: function(event) {
							if (!event.newVal) {
								Liferay.Portlet.refresh('#p_p_id_' + '<%= portletDisplay.getId() %>' + '_');
							}
						}
					}
				},
				groupId: <%= scopeGroupId %>,
				refererPortletName: '<%= JournalPortletKeys.JOURNAL %>',
				refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',
				showAncestorScopes: true,
				showManageTemplates: true,
				title: '<%= UnicodeLanguageUtil.get(request, "structures") %>'
			}
		);
	}
</aui:script>