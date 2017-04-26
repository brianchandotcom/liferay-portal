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

boolean autoCreate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:autoCreate"));
String contents = (String)request.getAttribute("liferay-ui:input-editor:contents");

String contentsLanguageId = (String)request.getAttribute("liferay-ui:input-editor:contentsLanguageId");

Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:input-editor:data");
String editorName = (String)request.getAttribute("liferay-ui:input-editor:editorName");
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
boolean showSource = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:showSource"));
boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
String toolbarSet = (String)request.getAttribute("liferay-ui:input-editor:toolbarSet");
%>

<liferay-util:buffer var="editor">
	<textarea id="<%= HtmlUtil.escapeAttribute(name) %>" name="<%= HtmlUtil.escapeAttribute(name) %>" style="height: 100%; visibility: hidden; width: 100%;"><%= (contents != null) ? HtmlUtil.escape(contents) : StringPool.BLANK %></textarea>
</liferay-util:buffer>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-util:html-top outputKey="js_editor_tinymce">

		<%
		long javaScriptLastModified = PortalWebResourcesUtil.getLastModified(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_TINYMCEEDITOR);
		%>

		<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_TINYMCEEDITOR) + "/tiny_mce/tinymce.min.js", javaScriptLastModified)) %>" type="text/javascript"></script>

		<liferay-util:dynamic-include key='<%= "com.liferay.frontend.editor.tinymce.web#" + editorName + "#additionalResources" %>' />

		<script type="text/javascript">
			Liferay.namespace('EDITORS')['<%= editorName %>'] = true;
		</script>
	</liferay-util:html-top>
</c:if>

<div class="<%= HtmlUtil.escapeAttribute(cssClass) %>" id="<%= HtmlUtil.escapeAttribute(name) %>Container">
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
		}
		else {
			data = '<%= (contents != null) ? HtmlUtil.escapeJS(contents) : StringPool.BLANK %>';
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

		fileBrowserCallback: function(field_name, url, type) {
		},

		focus: function() {
			tinyMCE.editors['<%= name %>'].focus();
		},

		getHTML: function() {
			var data;

			if (!window['<%= name %>'].instanceReady) {
				data = getInitialContent();
			}
			else {
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
			}
			else {
				var editorBody = tinyMCE.editors['<%= name %>'].getBody();

				data = editorBody.textContent;
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
					editor.on(
						'keyup',
						function() {
							<%= HtmlUtil.escapeJS(onChangeMethod) %>(window['<%= name %>'].getHTML());
						}
					);
				};
			</c:if>

			var config = A.merge(editorConfig, defaultConfig);

			tinyMCE.init(config);

			var tinyMCEEditor = tinyMCE.editors['<%= name %>'];

			<liferay-util:dynamic-include key='<%= "com.liferay.frontend.editor.tinymce.web#" + editorName + "#onEditorCreate" %>' />
		},

		initInstanceCallback: function() {
			<c:if test="<%= (contents == null) && Validator.isNotNull(initMethod) %>">
				window['<%= name %>'].init(<%= HtmlUtil.escapeJS(namespace + initMethod) %>());
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
		},

		instanceReady: false,

		setHTML: function(value) {
			if (window['<%= name %>'].instanceReady) {
				tinyMCE.editors['<%= name %>'].setContent(value);
			}
			else {
				document.getElementById('<%= name %>').innerHTML = value;
			}
		}
	};

	Liferay.fire(
		'editorAPIReady',
		{
			editor: window['<%= name %>'],
			editorName: '<%= name %>'
		}
	);

	<c:if test="<%= autoCreate %>">
		window['<%= name %>'].initEditor();
	</c:if>

	var destroyInstance = function(event) {
		if (event.portletId === '<%= portletId %>') {
			try {
				window['<%= name %>'].destroy();
			}
			catch (e) {
			}

			Liferay.detach('destroyPortlet', destroyInstance);
		}
	};

	Liferay.on('destroyPortlet', destroyInstance);
</aui:script>