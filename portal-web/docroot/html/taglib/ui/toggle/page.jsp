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

<%@ include file="/html/taglib/init.jsp" %>

<%
String id = (String)request.getAttribute("liferay-ui:toggle:id");
String showImage = (String)request.getAttribute("liferay-ui:toggle:showImage");
String hideImage = (String)request.getAttribute("liferay-ui:toggle:hideImage");
String showMessage = (String)request.getAttribute("liferay-ui:toggle:showMessage");
String hideMessage = (String)request.getAttribute("liferay-ui:toggle:hideMessage");
String stateVar = (String)request.getAttribute("liferay-ui:toggle:stateVar");
String defaultStateValue = (String)request.getAttribute("liferay-ui:toggle:defaultStateValue");
String defaultImage = (String)request.getAttribute("liferay-ui:toggle:defaultImage");
String defaultMessage = (String)request.getAttribute("liferay-ui:toggle:defaultMessage");
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(showMessage) %>">
		<a href="javascript:<%= stateVar %>Toggle();" id="<%= id %>_message"><%= defaultMessage %></a>
	</c:when>
	<c:otherwise>
		<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="toggle" />" id="<%= id %>_image" onclick="<%= stateVar %>Toggle();" src="<%= defaultImage %>" style="margin: 0px;" />
	</c:otherwise>
</c:choose>

<aui:script>
	var <%= stateVar %> = '<%= defaultStateValue %>';

	window.<%= stateVar %>Toggle = function(state, saveState) {
		var A = AUI();

		if (state == null) {
			state = <%= stateVar %>;
		}

		if (state == '') {
			<%= stateVar %> = 'none';

			document.getElementById('<%= id %>').style.display = 'none';

			<c:choose>
				<c:when test="<%= Validator.isNotNull(showMessage) %>">
					document.getElementById('<%= id %>_message').innerHTML = '<%= showMessage %>';
				</c:when>
				<c:otherwise>
					document.getElementById('<%= id %>_image').src = '<%= showImage %>';
				</c:otherwise>
			</c:choose>

			if ((saveState == null) || saveState) {
				Liferay.Store('<%= id %>', 'none');
			}

			Liferay.fire(
				'toggle:stateChange',
				{
					id: '<%= id %>',
					state: 0
				}
			);
		}
		else {
			<%= stateVar %> = '';

			document.getElementById('<%= id %>').style.display = '';

			<c:choose>
				<c:when test="<%= Validator.isNotNull(showMessage) %>">
					document.getElementById('<%= id %>_message').innerHTML = '<%= hideMessage %>';
				</c:when>
				<c:otherwise>
					document.getElementById('<%= id %>_image').src = '<%= hideImage %>';
				</c:otherwise>
			</c:choose>

			if ((saveState == null) || saveState) {
				Liferay.Store('<%= id %>', 'block');
			}

			Liferay.fire(
				'toggle:stateChange',
				{
					id: '<%= id %>',
					state: 1
				}
			);
		}
	}
</aui:script>