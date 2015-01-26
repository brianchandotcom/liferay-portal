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
String portletId = portletDisplay.getRootPortletId();

boolean autoCreate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:autoCreate"));
String contents = (String)request.getAttribute("liferay-ui:input-editor:contents");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));

String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

String onInitMethod = (String)request.getAttribute("liferay-ui:input-editor:onInitMethod");

if (Validator.isNotNull(onInitMethod)) {
	onInitMethod = namespace + onInitMethod;
}

boolean resizable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:resizable"));

String modules = "aui-event-input";

if (resizable) {
	modules += ",resize";
}
%>

<liferay-util:buffer var="editor">
	<table bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" height="100%" width="100%">
		<tr>
			<td bgcolor="#FFFFFF" height="100%">
				<textarea class="lfr-editor-textarea" id="<%= name %>" name="<%= name %>" style="resize:<%= resizable ? "vertical" : "none" %>"><%= (contents != null) ? contents : StringPool.BLANK %></textarea>
			</td>
		</tr>
	</table>
</liferay-util:buffer>

<aui:script use="<%= modules %>">
	var onInputHandle;

	var onInput = function(event) {
		<%= HtmlUtil.escapeJS(onChangeMethod) %>(window['<%= name %>'].getHTML());
	};

	window['<%= name %>'] = {
		create: function() {
			if (! window['<%= name %>'].instanceReady) {
				var editorNode = A.Node.create('<%= HtmlUtil.escapeJS(editor) %>');

				var editorContainer = A.one('#<%= name %>Container');

				editorContainer.appendChild(editorNode);

				window['<%= name %>'].initEditor();
			}
		},

		destroy: function() {
			window['<%= name %>'].dispose();

			window['<%= name %>'] = null;
		},

		dispose: function() {
			var editorEl = document.getElementById('<%= name %>');

			if (editorEl) {
				editorEl.parentNode.removeChild(editorEl);

				window['<%= name %>'].instanceReady = false;
			}

			if (onInputHandle) {
				onInputHandle.detach();
			}
		},

		focus: function() {
			return document.getElementById('<%= name %>').focus();
		},

		getHTML: function() {
			return document.getElementById('<%= name %>').value;
		},

		initEditor: function() {
			<c:if test="<%= (contents == null) && Validator.isNotNull(initMethod) %>">
				<%= name %>.setHTML(<%= namespace + initMethod %>());
			</c:if>

			<c:if test="<%= Validator.isNotNull(onChangeMethod) %>">
				onInputHandle = A.one('#<%= name %>').on('input', A.bind(onInput, this));
			</c:if>

			<c:if test="<%= resizable && BrowserSnifferUtil.isIe(request) %>">
				setTimeout(
					function() {
						new A.Resize(
							{
								handles: 'br',
								node: '#<%= name %>Container',
								wrap: true
							}
						);
					},
					0
				);
			</c:if>

			<c:if test="<%= Validator.isNotNull(onInitMethod) %>">
				window['<%= HtmlUtil.escapeJS(namespace + onInitMethod) %>']();
			</c:if>

			window['<%= name %>'].instanceReady = true;
		},

		instanceReady: false,

		setHTML: function(value) {
			document.getElementById('<%= name %>').value = value || '';
		}
	};

	<c:if test="<%= autoCreate %>">
		window['<%= name %>'].initEditor();
	</c:if>

	var destroyInstance = function(event) {
		if (event.portletId === '<%= portletId %>') {
			window['<%= name %>'].destroy();

			Liferay.detach('destroyPortlet', destroyInstance);
		}
	};

	Liferay.on('destroyPortlet', destroyInstance);
</aui:script>

<div class="<%= cssClass %>" id="<%= name %>Container">
	<c:if test="<%= autoCreate %>">
		<%= editor %>
	</c:if>
</div>