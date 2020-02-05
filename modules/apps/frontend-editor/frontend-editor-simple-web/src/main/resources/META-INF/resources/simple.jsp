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
String portletId = portletDisplay.getRootPortletId();

boolean autoCreate = GetterUtil.getBoolean((String)request.getAttribute(SimpleEditorConstants.ATTRIBUTE_NAMESPACE + ":autoCreate"));
String contents = (String)request.getAttribute(SimpleEditorConstants.ATTRIBUTE_NAMESPACE + ":contents");
String cssClass = GetterUtil.getString((String)request.getAttribute(SimpleEditorConstants.ATTRIBUTE_NAMESPACE + ":cssClass"));
String initMethod = (String)request.getAttribute(SimpleEditorConstants.ATTRIBUTE_NAMESPACE + ":initMethod");
String name = namespace + GetterUtil.getString((String)request.getAttribute(SimpleEditorConstants.ATTRIBUTE_NAMESPACE + ":name"));

String onChangeMethod = (String)request.getAttribute(SimpleEditorConstants.ATTRIBUTE_NAMESPACE + ":onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

String onInitMethod = (String)request.getAttribute(SimpleEditorConstants.ATTRIBUTE_NAMESPACE + ":onInitMethod");

if (Validator.isNotNull(onInitMethod)) {
	onInitMethod = namespace + onInitMethod;
}

boolean resizable = GetterUtil.getBoolean((String)request.getAttribute(SimpleEditorConstants.ATTRIBUTE_NAMESPACE + ":resizable"));

String modules = "aui-event-input";

if (resizable) {
	modules += ",resize";
}
%>

<liferay-util:buffer
	var="editor"
>
	<table bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" height="100%" width="100%">
		<tr>
			<td bgcolor="#FFFFFF" height="100%">
				<textarea class="lfr-editor-textarea" id="<%= HtmlUtil.escapeAttribute(name) %>" name="<%= HtmlUtil.escapeAttribute(name) %>" style="resize:<%= resizable ? "vertical" : "none" %>"><%= (contents != null) ? HtmlUtil.escape(contents) : StringPool.BLANK %></textarea>
			</td>
		</tr>
	</table>
</liferay-util:buffer>

<%
String containerId = HtmlUtil.escapeAttribute(name) + "Container";

name = HtmlUtil.escapeJS(name);
%>

<aui:script use="<%= modules %>">
	var onInputHandle;

	var onInput = function(event) {
		<%= HtmlUtil.escapeJS(onChangeMethod) %>(window['<%= name %>'].getHTML());
	};

	var getInitialContent = function() {
		var data;

		if (window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>']) {
			data = <%= HtmlUtil.escapeJS(namespace + initMethod) %>();
		}
		else {
			data =
				'<%= (contents != null) ? HtmlUtil.escapeJS(contents) : StringPool.BLANK %>';
		}

		return data;
	};

	window['<%= name %>'] = {
		create: function() {
			if (!window['<%= name %>'].instanceReady) {
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
			var editorEl = document.getElementById('<%= name %>Container');

			if (editorEl) {
				editorEl.parentNode.removeChild(editorEl);

				window['<%= name %>'].instanceReady = false;
			}

			if (onInputHandle) {
				onInputHandle.detach();
			}
		},

		focus: function() {
			var focus;

			if (window['<%= name %>'].instanceReady) {
				focus = document.getElementById('<%= name %>').focus();
			}

			return focus;
		},

		getHTML: function() {
			return window['<%= name %>'].getText();
		},

		getNativeEditor: function() {
			return document.getElementById('<%= name %>');
		},

		getText: function() {
			var value;

			var textArea = document.getElementById('<%= name %>');

			if (textArea.nodeName.toLowerCase() === 'textarea' && textArea.value) {
				value = textArea.value;
			}
			else if (window['<%= name %>'].instanceReady) {
				value = document.getElementById('<%= name %>').value;
			}
			else {
				value = getInitialContent();
			}

			return value;
		},

		initEditor: function() {
			<c:if test="<%= (contents == null) && Validator.isNotNull(initMethod) %>">

				var initEditorFunction =
					window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>'];

				if (typeof initEditorFunction === 'function') {
					<%= name %>.setHTML(initEditorFunction());
				}
			</c:if>

			<c:if test="<%= Validator.isNotNull(onChangeMethod) %>">
				onInputHandle = A.one('#<%= name %>').on(
					'input',
					A.bind(onInput, this)
				);
			</c:if>

			<c:if test="<%= resizable && BrowserSnifferUtil.isIe(request) %>">
				setTimeout(function() {
					new A.Resize({
						handles: 'br',
						node: '#<%= name %>Container',
						wrap: true
					});
				}, 0);
			</c:if>

			<c:if test="<%= Validator.isNotNull(onInitMethod) %>">
				window['<%= HtmlUtil.escapeJS(onInitMethod) %>']();
			</c:if>

			window['<%= name %>'].instanceReady = true;

			Liferay.component('<%= name %>', window['<%= name %>']);

			Liferay.on(
				'inputLocalized:localeChanged',
				this._onLocaleChangedHandler,
				this
			);
		},

		instanceReady: false,

		setHTML: function(value) {
			if (window['<%= name %>'].instanceReady) {
				document.getElementById('<%= name %>').value = value || '';
			}
		},

		_onLocaleChangedHandler: function(event) {
			var instance = this;

			var contentsLanguage = event.item.getAttribute('data-value');
			var contentsLanguageDir = Liferay.Language.direction[contentsLanguage];

			var nativeEditor = instance.getNativeEditor();

			nativeEditor.setAttribute('dir', contentsLanguageDir);
			nativeEditor.setAttribute('lang', contentsLanguage);
		}
	};

	Liferay.fire('editorAPIReady', {
		editor: window['<%= name %>'],
		editorName: '<%= name %>'
	});

	<c:if test="<%= autoCreate %>">
		window['<%= name %>'].initEditor();
	</c:if>

	var destroyInstance = function(event) {
		if (event.portletId === '<%= portletId %>') {
			try {
				window['<%= name %>'].destroy();
			}
			catch (e) {}

			Liferay.detach('destroyPortlet', destroyInstance);
		}
	};

	Liferay.on('destroyPortlet', destroyInstance);
</aui:script>

<div class="<%= HtmlUtil.escapeAttribute(cssClass) %>" id="<%= containerId %>">
	<c:if test="<%= autoCreate %>">
		<%= editor %>
	</c:if>
</div>