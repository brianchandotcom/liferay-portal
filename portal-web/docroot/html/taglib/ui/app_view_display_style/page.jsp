<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String displayStyle = (String)request.getAttribute("liferay-ui:app-view-display-style:displayStyle");
String[] displayStyles = (String[])request.getAttribute("liferay-ui:app-view-display-style:displayStyles");
Map<String, String> requestParams = (Map<String, String>)request.getAttribute("liferay-ui:app-view-display-style:requestParams");
%>

<c:if test="<%= displayStyles.length > 1 %>">
	<aui:script use="aui-base,aui-toolbar">
		var buttonRow = A.one('#<portlet:namespace />displayStyleToolbar');

		function onButtonClick(displayStyle) {
			var config = {};

			<%
			Set<String> requestParamNames = requestParams.keySet();

			for (String requestParamName : requestParamNames) {
				String requestParamValue = requestParams.get(requestParamName);
			%>

				config['<portlet:namespace /><%= requestParamName %>'] = '<%= requestParamValue %>';

			<%
			}
			%>

			config['<portlet:namespace />displayStyle'] = displayStyle;
			config['<portlet:namespace />saveDisplayStyle'] = true;

			updateDisplayStyle(config);
		}

		function updateDisplayStyle(config) {
			var displayStyle = config['<portlet:namespace />displayStyle'];

			<%
			for (int i = 0; i < displayStyles.length; i++) {
			%>

				displayStyleToolbar.item(<%= i %>).StateInteraction.set('active', (displayStyle === '<%= displayStyles[i] %>'));

			<%
			}
			%>

			Liferay.fire(
				'<portlet:namespace />dataRequest',
				{
					requestParams: config,
					src: Liferay.DL_ENTRIES_PAGINATOR
				}
			);
		}

		var displayStyleToolbarChildren = [];

		<%
		for (int i = 0; i < displayStyles.length; i++) {
		%>

			displayStyleToolbarChildren.push(
				{
					handler: A.bind(onButtonClick, null, '<%= displayStyles[i] %>'),
					icon: 'display-<%= displayStyles[i] %>',
					title: '<%= UnicodeLanguageUtil.get(pageContext, displayStyles[i] + "-view") %>'
				}
			);

		<%
		}
		%>

		var displayStyleToolbar = buttonRow.getData('displayStyleToolbar');

		if (displayStyleToolbar) {
			displayStyleToolbar.removeAll();
		}

		displayStyleToolbar = new A.Toolbar(
			{
				activeState: true,
				boundingBox: buttonRow,
				children: displayStyleToolbarChildren
			}
		).render();

		var index = 0;

		<%
		for (int i = 0; i < displayStyles.length; i++) {
			if (displayStyle.equals(displayStyles[i])) {
		%>

				index = <%= i %>;

		<%
				break;
			}
		}
		%>

		displayStyleToolbar.item(index).StateInteraction.set('active', true);

		buttonRow.setData('displayStyleToolbar', displayStyleToolbar);
	</aui:script>
</c:if>