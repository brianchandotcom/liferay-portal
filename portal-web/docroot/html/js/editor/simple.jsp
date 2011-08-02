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

<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));

String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}
%>

<aui:script>
	window['<%= name %>'] = {
		destroy: function() {
			var editorEl = document.getElementById('<%= name %>');

			if (editorEl) {
				editorEl.parentNode.removeChild(editorEl);
			}

			delete window['<%= name %>'];
		},

		focus: function() {
			return document.getElementById('<%= name %>').focus();
		},

		getHTML: function() {
			return document.getElementById('<%= name %>').value;
		},

		initEditor: function() {
			<c:if test="<%= Validator.isNotNull(initMethod) %>">
				<%= name %>.setHTML(<%= namespace + initMethod %>());
			</c:if>
		},

		setHTML: function(value) {
			document.getElementById('<%= name %>').value = value || '';
		}
	};

	window['<%= name %>'].initEditor();
</aui:script>

<div class="<%= cssClass %>">
	<table bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" height="100%" width="100%">
	<tr>
		<td bgcolor="#FFFFFF" height="100%">
			<textarea style="font-family: monospace; height: 100%; width: 100%;" id="<%= name %>" name="<%= name %>"

			<%
			if (Validator.isNotNull(onChangeMethod)) {
			%>

				onChange="<%= HtmlUtil.escape(onChangeMethod) %>(this.value)"

			<%
			}
			%>

			></textarea>
		</td>
	</tr>
	</table>
</div>