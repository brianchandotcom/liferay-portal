<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", "icon");
}

String keywords = ParamUtil.getString(request, "keywords");
%>

<aui:script use="aui-dialog,aui-dialog-iframe">
	var buttonRow = A.one('#<portlet:namespace />displayStyleToolbar');

	function onButtonClick(displayStyle) {
		var config = {
			'<portlet:namespace />struts_action': '<%= Validator.isNull(keywords) ? "/document_library/view" : "/document_library/search" %>',
			'<portlet:namespace />displayStyle': displayStyle,
			'<portlet:namespace />folderId': '<%= String.valueOf(folderId) %>',
			'<portlet:namespace />saveDisplayStyle': <%= Boolean.TRUE.toString() %>
		};

		if (<%= Validator.isNull(keywords) %>) {
			config['<portlet:namespace />viewEntries'] = <%= Boolean.TRUE.toString() %>;
		}
		else {
			config['<portlet:namespace />keywords'] = '<%= HtmlUtil.escapeJS(keywords) %>';
		}

		if (<%= fileEntryTypeId != -1 %>) {
			config['<portlet:namespace />fileEntryTypeId'] = '<%= String.valueOf(fileEntryTypeId) %>';
		}

		updateDisplayStyle(config);
	}

	function updateDisplayStyle(config) {
		var displayStyle = config['<portlet:namespace />displayStyle'];

		displayStyleToolbar.item(0).StateInteraction.set('active', (displayStyle === 'icon'));
		displayStyleToolbar.item(1).StateInteraction.set('active', (displayStyle === 'descriptive'));
		displayStyleToolbar.item(2).StateInteraction.set('active', (displayStyle === 'list'));

		Liferay.fire(
			'<portlet:namespace />dataRequest',
			{
				requestParams: config
			}
		);
	}

	var displayStyleToolbar = new A.Toolbar(
		{
			activeState: true,
			boundingBox: buttonRow,
			children: [
				{
					handler: A.bind(onButtonClick, null, 'icon'),
					icon: 'display-icon',
					title: '<liferay-ui:message key="icon-view" />'
				},
				{
					handler: A.bind(onButtonClick, null, 'descriptive'),
					icon: 'display-descriptive',
					title: '<liferay-ui:message key="descriptive-view" />'
				},
				{
					handler: A.bind(onButtonClick, null, 'list'),
					icon: 'display-list',
					title: '<liferay-ui:message key="list-view" />'
				}
			]
		}
	).render();

	<c:choose>
		<c:when test='<%= displayStyle.equals("icon") %>'>
			var index = 0;
		</c:when>
		<c:when test='<%= displayStyle.equals("descriptive") %>'>
			var index = 1;
		</c:when>
		<c:when test='<%= displayStyle.equals("list") %>'>
			var index = 2;
		</c:when>
	</c:choose>

	displayStyleToolbar.item(index).StateInteraction.set('active', true);

	buttonRow.setData('displayStyleToolbar', displayStyleToolbar);
</aui:script>