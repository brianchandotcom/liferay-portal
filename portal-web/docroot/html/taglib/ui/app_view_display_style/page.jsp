<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
	<div class="toolbar" id="<portlet:namespace />displayStyleButtons">
		<div class="btn-group btn-group-radio">

			<%
			for (int i = 0; i < displayStyles.length; i++) {
				String dataStyle = displayStyles[i];

				String iconClass = displayStyles[i];

				if (iconClass.equals("icon")) {
					iconClass = "icon-th-large";
				}
				else if (iconClass.equals("descriptive")) {
					iconClass = "icon-th-list";
				}
				else if (iconClass.equals("list")) {
					iconClass ="icon-align-justify";
				}
			%>

				<button class='btn <%= displayStyle.equals(displayStyles[i]) ? "active" : StringPool.BLANK %>' data-displayStyle="<%= dataStyle %>"><i class="<%= iconClass %>"></i></button>

			<%
			}
			%>

		</div>
	</div>
</c:if>

<c:if test="<%= displayStyles.length > 1 %>">
	<aui:script use="aui-base,aui-toolbar">
		var buttonRow = A.one('#<portlet:namespace />displayStyleButtons');

		function changeDisplayStyle(displayStyle) {
			var config = {};

			<%
			Set<String> requestParamNames = requestParams.keySet();

			for (String requestParamName : requestParamNames) {
				String requestParamValue = requestParams.get(requestParamName);
			%>

				config['<portlet:namespace /><%= requestParamName %>'] = '<%= HtmlUtil.escapeJS(requestParamValue) %>';

			<%
			}
			%>

			config['<portlet:namespace />displayStyle'] = displayStyle;
			config['<portlet:namespace />saveDisplayStyle'] = true;

			Liferay.fire(
				'<portlet:namespace />dataRequest',
				{
					requestParams: config,
					src: Liferay.DL_ENTRIES_PAGINATOR
				}
			);
		}

		var displayStyleToolbar = buttonRow.getData('displayStyleToolbar');

		if (displayStyleToolbar) {
			displayStyleToolbar.clear();
		}

		displayStyleToolbar = new A.Toolbar(
			{
				boundingBox: buttonRow,
				on: {
					click: function(event) {
						var btnNode = this.getEnclosingWidget(event).getSelectedButtons()[0];

						changeDisplayStyle(btnNode.attr('data-displayStyle'));
					}
				}
			}
		).render();

		buttonRow.setData('displayStyleToolbar', displayStyleToolbar);
	</aui:script>
</c:if>