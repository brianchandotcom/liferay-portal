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

boolean autoCreate = GetterUtil.getBoolean((String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":autoCreate"));
String contents = (String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":contents");

String contentsLanguageId = (String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":contentsLanguageId");

Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

String cssClass = GetterUtil.getString((String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":data");
String editorName = (String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":editorName");
String initMethod = (String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":initMethod");
String name = namespace + GetterUtil.getString((String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":name"));

String onChangeMethod = (String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

String onInitMethod = (String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":onInitMethod");

if (Validator.isNotNull(onInitMethod)) {
	onInitMethod = namespace + onInitMethod;
}

boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":skipEditorLoading"));
%>

<liferay-util:buffer
	var="editor"
>
	<textarea id="<%= HtmlUtil.escapeAttribute(name) %>" name="<%= HtmlUtil.escapeAttribute(name) %>" style="height: 100%; visibility: hidden; width: 100%;"><%= (contents != null) ? HtmlUtil.escape(contents) : StringPool.BLANK %></textarea>
</liferay-util:buffer>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-editor:resources
		editorName="<%= editorName %>"
	/>
</c:if>

<div class="<%= cssClass %>" id="<%= HtmlUtil.escapeAttribute(name) %>Container">
	<c:if test="<%= autoCreate %>">
		<%= editor %>
	</c:if>
</div>

<%
name = HtmlUtil.escapeJS(name);
%>

<aui:script use="aui-node-base">
	var getInitialContent = function() {
		var data;

		if (window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>']) {
			data = <%= HtmlUtil.escapeJS(namespace + initMethod) %>();
		} else {
			data =
				'<%= (contents != null) ? HtmlUtil.escapeJS(contents) : StringPool.BLANK %>';
		}

		return data;
	};

	window['<%= name %>'] = {
		init: function(value) {
			if (typeof value != 'string') {
				value = '';
			}

			window['<%= name %>'].setHTML(value);
		},

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

			Liferay.namespace('EDITORS').tinymce.removeInstance();
		},

		dispose: function() {
			var editorNode = A.one('textarea#<%= name %>');

			if (editorNode) {
				editorNode.remove();
			}

			var tinyMCEEditor = tinyMCE.editors['<%= name %>'];

			if (tinyMCEEditor) {
				tinyMCEEditor.remove();

				tinyMCEEditor.destroy();

				window['<%= name %>'].instanceReady = false;
			}
		},

		fileBrowserCallback: function(field_name, url, type) {},

		focus: function() {
			if (window['<%= name %>'].instanceReady) {
				tinyMCE.editors['<%= name %>'].focus();
			} else {
				window['<%= name %>'].pendingFocus = true;
			}
		},

		getHTML: function() {
			var data;

			if (!window['<%= name %>'].instanceReady) {
				data = getInitialContent();
			} else {
				data = tinyMCE.editors['<%= name %>'].getBody().innerHTML;
			}

			return data;
		},

		getNativeEditor: function() {
			return tinyMCE.editors['<%= name %>'];
		},

		getText: function() {
			var data;

			if (!window['<%= name %>'].instanceReady) {
				data = getInitialContent();
			} else {
				var editorBody = tinyMCE.editors['<%= name %>'].getBody();

				data = editorBody.textContent || editorBody.innerText;
			}

			return data;
		},

		initEditor: function() {

			<%
			JSONObject editorConfigJSONObject = null;

			if (data != null) {
				editorConfigJSONObject = (JSONObject)data.get("editorConfig");
			}
			%>

			var editorConfig = <%= (editorConfigJSONObject != null) ? editorConfigJSONObject.toString() : "{}" %>;

			var defaultConfig = {
				file_browser_callback: window['<%= name %>'].fileBrowserCallback,
				init_instance_callback: window['<%= name %>'].initInstanceCallback
			};

			<c:if test="<%= Validator.isNotNull(onChangeMethod) %>">
				defaultConfig.setup = function(editor) {
					editor.on('keyup', function() {
						<%= HtmlUtil.escapeJS(onChangeMethod) %>(
							window['<%= name %>'].getHTML()
						);
					});
				};
			</c:if>

			var config = A.merge(editorConfig, defaultConfig);

			tinyMCE.init(config);

			var tinyMCEEditor = tinyMCE.editors['<%= name %>'];

			<liferay-util:dynamic-include key='<%= "com.liferay.frontend.editor.tinymce.web#" + editorName + "#onEditorCreate" %>' />

			Liferay.namespace('EDITORS').tinymce.addInstance();

			Liferay.on(
				'inputLocalized:localeChanged',
				this._onLocaleChangedHandler,
				this
			);
		},

		initInstanceCallback: function() {
			<c:if test="<%= (contents == null) && Validator.isNotNull(initMethod) %>">
				window['<%= name %>'].init(
					<%= HtmlUtil.escapeJS(namespace + initMethod) %>()
				);
			</c:if>

			var iframe = A.one('#<%= name %>_ifr');

			if (iframe) {
				var iframeWin = iframe.getDOM().contentWindow;

				if (iframeWin) {
					var iframeDoc = iframeWin.document.documentElement;

					A.one(iframeDoc).addClass('aui');
				}
			}

			<c:if test="<%= Validator.isNotNull(onInitMethod) %>">
				window['<%= HtmlUtil.escapeJS(onInitMethod) %>']();
			</c:if>

			window['<%= name %>'].instanceReady = true;

			if (window['<%= name %>'].pendingFocus) {
				window['<%= name %>'].pendingFocus = false;

				window['<%= name %>'].focus();
			}

			Liferay.component('<%= name %>', window['<%= name %>'], {
				portletId: '<%= portletId %>'
			});
		},

		instanceReady: false,

		setHTML: function(value) {
			if (window['<%= name %>'].instanceReady) {
				tinyMCE.editors['<%= name %>'].setContent(value);
			} else {
				document.getElementById('<%= name %>').innerHTML = value;
			}
		},

		_onLocaleChangedHandler: function(event) {
			var instance = this;

			var contentsLanguage = event.item.getAttribute('data-value');
			var contentsLanguageDir = Liferay.Language.direction[contentsLanguage];

			var nativeEditor = instance.getNativeEditor();
			var context = nativeEditor.$().context;

			context.setAttribute('dir', contentsLanguageDir);
			context.setAttribute('lang', contentsLanguage);
		}
	};

	<c:if test="<%= autoCreate %>">
		window['<%= name %>'].initEditor();
	</c:if>
</aui:script>