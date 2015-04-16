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

<%@ include file="/html/js/editor/init.jsp" %>

<%
String portletId = portletDisplay.getRootPortletId();

String doAsUserId = themeDisplay.getDoAsUserId();

if (Validator.isNull(doAsUserId)) {
	doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
}

boolean autoCreate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:autoCreate"));

String contents = (String)request.getAttribute("liferay-ui:input-editor:contents");
String contentsLanguageId = (String)request.getAttribute("liferay-ui:input-editor:contentsLanguageId");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:input-editor:data");
JSONObject editorConfigJSONObject = (data != null) ? (JSONObject)data.get("editorConfig") : null;
JSONObject editorOptionsJSONObject = (data != null) ? (JSONObject)data.get("editorOptions") : null;

String editorName = (String)request.getAttribute("liferay-ui:input-editor:editorName");
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");

String onBlurMethod = (String)request.getAttribute("liferay-ui:input-editor:onBlurMethod");

if (Validator.isNotNull(onBlurMethod)) {
	onBlurMethod = namespace + onBlurMethod;
}

String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

String onFocusMethod = (String)request.getAttribute("liferay-ui:input-editor:onFocusMethod");

if (Validator.isNotNull(onFocusMethod)) {
	onFocusMethod = namespace + onFocusMethod;
}

String onInitMethod = (String)request.getAttribute("liferay-ui:input-editor:onInitMethod");

if (Validator.isNotNull(onInitMethod)) {
	onInitMethod = namespace + onInitMethod;
}

String placeholder = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:placeholder"));

boolean showSource = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:showSource"));

boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
%>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-util:html-top outputKey="js_editor_alloyeditor_skip_editor_loading">
		<link href="<%= PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() + "/editor/alloyeditor/assets/alloy-editor-ocean.css") %>" rel="stylesheet" type="text/css" />

		<%
		long javaScriptLastModified = PortalWebResourcesUtil.getLastModified();
		%>

		<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() + "/editor/ckeditor/ckeditor.js", javaScriptLastModified)) %>" type="text/javascript"></script>

		<script type="text/javascript">
			YUI.applyConfig(
				{
					groups: {
						AlloyEditor: {
							base: Liferay.AUI.getJavaScriptRootPath() + '/editor/alloyeditor/',
							combine: Liferay.AUI.getCombine(),
							comboBase: Liferay.AUI.getComboPath(),
							modules: {
								'button-imageselector': {
									path: 'buttons/button_image_selector.js',
									requires: [
										'aui-base',
										'button-base'
									]
								}
							},
							root: Liferay.AUI.getJavaScriptRootPath() + '/editor/alloyeditor/'
						}
					}
				}
			);
		</script>

		<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() + "/editor/alloyeditor/liferay-alloy-editor-no-ckeditor-min.js", javaScriptLastModified)) %>" type="text/javascript"></script>

		<script type="text/javascript">
			Liferay.namespace('EDITORS')['<%= editorName %>'] = true;
		</script>
	</liferay-util:html-top>
</c:if>

<script type="text/javascript">
	CKEDITOR.disableAutoInline = true;

	CKEDITOR.env.isCompatible = true;
</script>

<liferay-util:buffer var="alloyEditor">
	<div class="alloy-editor alloy-editor-placeholder <%= cssClass %>" contenteditable="false" data-placeholder="<%= LanguageUtil.get(request, placeholder) %>" id="<%= name %>" name="<%= name %>"><%= contents %></div>
</liferay-util:buffer>

<liferay-util:buffer var="editor">
	<c:choose>
		<c:when test="<%= showSource %>">
			<div class="alloy-editor-switch">
				<button class="btn btn-default btn-xs hide icon-fullscreen" id="<%= name %>Fullscreen" type="button"></button>

				<button class="btn btn-default btn-xs" id="<%= name %>Switch" type="button">
					&lt;&#47;&gt;
				</button>
			</div>

			<div class="alloy-editor-wrapper" id="<%= name %>Wrapper">
				<div class="wrapper">
					<%= alloyEditor %>

					<div id="<%= name %>Source">
						<div class="lfr-source-editor-code"></div>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<%= alloyEditor %>
		</c:otherwise>
	</c:choose>
</liferay-util:buffer>

<div id="<%= name %>Container">
	<c:if test="<%= autoCreate %>">
		<%= editor %>
	</c:if>
</div>

<%
String modules = "liferay-alloy-editor";

String uploadURL = editorOptionsJSONObject.getString("uploadURL");

if (Validator.isNotNull(data) && Validator.isNotNull(uploadURL)) {
	modules += ",liferay-editor-image-uploader";
}

if (showSource) {
	modules += ",liferay-alloy-editor-source";
}
%>

<aui:script use="<%= modules %>">

	<%
	Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

	contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);
	%>

	var alloyEditor;

	var createInstance = function() {
		document.getElementById('<%= name %>').setAttribute('contenteditable', true);

		var editorConfig = (<%= Validator.isNotNull(editorConfigJSONObject) %>) ? <%= editorConfigJSONObject %> : {};

		var plugins = [];

		<c:if test="<%= Validator.isNotNull(data) && Validator.isNotNull(uploadURL) %>">
			plugins.push(
				{
					cfg: {
						uploadUrl: '<%= uploadURL %>'
					},
					fn: A.Plugin.LiferayBlogsUploader
				}
			);
		</c:if>

		<c:if test="<%= showSource %>">
			plugins.push(A.Plugin.LiferayAlloyEditorSource);
		</c:if>

		alloyEditor = new A.LiferayAlloyEditor(
			{
				editorConfig: editorConfig,
				editorOptions: <%= editorOptionsJSONObject %>,
				initMethod: window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>'],
				namespace: '<%= name %>',
				onBlurMethod: window['<%= HtmlUtil.escapeJS(onBlurMethod) %>'],
				onChangeMethod: window['<%= HtmlUtil.escapeJS(onChangeMethod) %>'],
				onFocusMethod: window['<%= HtmlUtil.escapeJS(onFocusMethod) %>'],
				onInitMethod: window['<%= HtmlUtil.escapeJS(onInitMethod) %>'],
				plugins: plugins
			}
		).render();
	};

	window['<%= name %>'] = {
		create: function() {
			if (!alloyEditor) {
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
			if (alloyEditor) {
				alloyEditor.destroy();

				alloyEditor = null;
			}

			var editorNode = document.getElementById('<%= name %>');

			if (editorNode) {
				editorNode.parentNode.removeChild(editorNode);
			}
		},

		focus: function() {
			if (alloyEditor) {
				alloyEditor.focus();
			}
		},

		getCkData: function() {
			var data;

			if (alloyEditor && alloyEditor.instanceReady) {
				data = alloyEditor.getCkData();
			}
			else if (window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>']) {
				data = window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>']();
			}

			return data;
		},

		getHTML: function() {
			return alloyEditor ? alloyEditor.getHTML() : window['<%= name %>'].getCkData();
		},

		getText: function() {
			return window['<%= name %>'].getCkData();
		},

		initEditor: function() {
			createInstance();
		},

		setHTML: function(value) {
			if (alloyEditor) {
				alloyEditor.setHTML(value);
			}
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

<%!
public String marshallParams(Map<String, String> params) {
	if (params == null) {
		return StringPool.BLANK;
	}

	StringBundler sb = new StringBundler(4 * params.size());

	for (Map.Entry<String, String> configParam : params.entrySet()) {
		sb.append(StringPool.AMPERSAND);
		sb.append(configParam.getKey());
		sb.append(StringPool.EQUAL);
		sb.append(HttpUtil.encodeURL(configParam.getValue()));
	}

	return sb.toString();
}
%>