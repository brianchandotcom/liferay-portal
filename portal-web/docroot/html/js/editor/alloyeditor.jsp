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

String doAsUserId = themeDisplay.getDoAsUserId();

if (Validator.isNull(doAsUserId)) {
	doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
}

boolean autoCreate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:autoCreate"));

String contents = (String)request.getAttribute("liferay-ui:input-editor:contents");
String contentsLanguageId = (String)request.getAttribute("liferay-ui:input-editor:contentsLanguageId");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:input-editor:data");
JSONObject editorConfig = (data != null) ? (JSONObject) data.get("editorConfig") : null;
JSONObject editorOptions = (data != null) ? (JSONObject) data.get("editorOptions") : null;

String editorImpl = (String)request.getAttribute("liferay-ui:input-editor:editorImpl");
Map<String, String> fileBrowserParamsMap = (Map<String, String>)request.getAttribute("liferay-ui:input-editor:fileBrowserParams");
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

if (Validator.isNotNull(editorOptions) && !editorOptions.getBoolean("showSource")) {
	showSource = false;
}

boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
%>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-util:html-top outputKey="js_editor_alloyeditor_skip_editor_loading">
		<link href="<%= PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() + "/editor/alloyeditor/assets/alloy-editor-ocean.css") %>" rel="stylesheet" type="text/css" />

		<%
		long javaScriptLastModified = ServletContextUtil.getLastModified(application, "/html/js/", true);
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

		<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() + "/editor/alloyeditor/alloy-editor-core.js", javaScriptLastModified)) %>" type="text/javascript"></script>

		<script type="text/javascript">
			Liferay.namespace('EDITORS')['<%= editorImpl %>'] = true;
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

if (Validator.isNotNull(data) &&  Validator.isNotNull(data.get("uploadURL"))) {
	modules += ",liferay-editor-image-uploader";
}
%>

<aui:script use="<%= modules %>">
	<%
	Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

	contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

	String contentsLanguageDir = LanguageUtil.get(contentsLocale, "lang.dir");
	String languageId = LocaleUtil.toLanguageId(locale);
	%>

	var alloyEditor;

	var createInstance = function() {
		document.getElementById('<%= name %>').setAttribute('contenteditable', true);

		var defaultConfig = {
			contentsLangDirection: '<%= HtmlUtil.escapeJS(contentsLanguageDir) %>',

			contentsLanguage: '<%= contentsLanguageId.replace("iw_", "he_") %>',

			<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_SELECTOR %>" varImpl="documentSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
				<portlet:param name="eventName" value='<%= name + "selectDocument" %>' />
				<portlet:param name="showGroupsSelector" value="true" />
			</liferay-portlet:renderURL>

			<%
			if (fileBrowserParamsMap != null) {
				for (Map.Entry<String, String> entry : fileBrowserParamsMap.entrySet()) {
					documentSelectorURL.setParameter(entry.getKey(), entry.getValue());
				}
			}
			%>

			filebrowserBrowseUrl: '<%= documentSelectorURL %>',
			filebrowserFlashBrowseUrl: '<%= documentSelectorURL %>&Type=flash',
			filebrowserImageBrowseLinkUrl: '<%= documentSelectorURL %>&Type=image',
			filebrowserImageBrowseUrl: '<%= documentSelectorURL %>&Type=image',

			language: '<%= languageId.replace("iw_", "he_") %>',

			srcNode: '#<%= name %>',

			toolbars: {
				add: ['imageselector'],
				image: ['left', 'right'],
				styles: ['strong', 'em', 'u', 'h1', 'h2', 'a', 'twitter']
			}
		};

		var customConfig = (<%= Validator.isNotNull(editorConfig) %> ) ? <%= editorConfig %> : {};

		var config = A.merge(defaultConfig, customConfig);

		var plugins = [];

		<c:if test='<%= Validator.isNotNull(data) &&  Validator.isNotNull(data.get("uploadURL")) %>'>
			plugins.push(
				{
					cfg: {
						uploadUrl: '<%= data.get("uploadURL") %>'
					},
					fn: A.Plugin.LiferayBlogsUploader
				}
			);
		</c:if>

		alloyEditor = new A.LiferayAlloyEditor(
			{
				editorConfig:   config,
				editorOptions:  <%= editorOptions %>,
				initMethod:     window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>'],
				onBlurMethod:   window['<%= HtmlUtil.escapeJS(onBlurMethod) %>'],
				onChangeMethod: window['<%= HtmlUtil.escapeJS(onChangeMethod) %>'],
				onFocusMethod:  window['<%= HtmlUtil.escapeJS(onFocusMethod) %>'],
				onInitMethod:   window['<%= HtmlUtil.escapeJS(onInitMethod) %>'],
				plugins: plugins
			}
		).render();

		<c:if test="<%= showSource %>">
			var CSS_SHOW_SOURCE = 'show-source';

			var STR_VALUE = 'value';

			var editorWrapper = A.one('#<%= name %>Wrapper');
			var editorSwitch = A.one('#<%= name %>Switch');
			var editorFullscreen = A.one('#<%= name %>Fullscreen');

			var editorSwitchContainer = editorSwitch.ancestor();

			var toggleEditorModeUI = function() {
				editorWrapper.toggleClass(CSS_SHOW_SOURCE);
				editorSwitchContainer.toggleClass(CSS_SHOW_SOURCE);
				editorFullscreen.toggleClass('hide');

				editorSwitch.setHTML(editorWrapper.hasClass(CSS_SHOW_SOURCE) ? 'abc' : '&lt;/&gt;');
			};

			var createSourceEditor = function() {
				A.use(
					'liferay-source-editor',
					function(A) {
						var sourceEditor = new A.LiferaySourceEditor(
							{
								boundingBox: A.one('#<%= name %>Source'),
								mode: 'html',
								value: window['<%= name %>'].getHTML()
							}
						).render();

						toggleEditorModeUI();

						Liferay.component('<%= name %>Source', sourceEditor);
					}
				);
			};

			var switchMode = function(event) {
				var editor = Liferay.component('<%= name %>Source');

				if (editorWrapper.hasClass(CSS_SHOW_SOURCE)) {
					var content = event.content || (editor ? editor.get(STR_VALUE) : '');

					window['<%= name %>'].setHTML(content);

					toggleEditorModeUI();
				}
				else if (editor) {
					var currentContent = event.content || window['<%= name %>'].getHTML();

					if (currentContent !== editor.get(STR_VALUE)) {
						editor.set(STR_VALUE, currentContent);
					}

					toggleEditorModeUI();
				}
				else {
					createSourceEditor();
				}
			};

			editorSwitch.on('click', switchMode);

			var fullScreenDialog;
			var fullScreenEditor;

			editorFullscreen.on(
				'click',
				function(event) {
					if (fullScreenDialog) {
						fullScreenEditor.set('value', window['<%= name %>'].getHTML());

						fullScreenDialog.show();
					}
					else {
						Liferay.Util.openWindow(
							{
								dialog: {
									constrain: true,
									cssClass: 'lfr-fulscreen-source-editor-dialog',
									modal: true,
									'toolbars.footer': [
										{
											cssClass: 'btn-primary',
											label: '<liferay-ui:message key="done" />',
											on: {
												click: function(event) {
													fullScreenDialog.hide();
													switchMode(
														{
															content: fullScreenEditor.get('value')
														}
													);
												}
											}
										},
										{
											label: '<liferay-ui:message key="cancel" />',
											on: {
												click: function(event) {
													fullScreenDialog.hide();
												}
											}
										}
									]
								},
								title: '<liferay-ui:message key="edit-content" />'
							},
							function(dialog) {
								fullScreenDialog = dialog;

								A.use(
									'liferay-fullscreen-source-editor',
									function(A) {
										fullScreenEditor = new A.LiferayFullScreenSourceEditor(
											{
												boundingBox: dialog.getStdModNode(A.WidgetStdMod.BODY).appendChild('<div></div>'),
												previewCssClass: 'alloy-editor alloy-editor-placeholder',
												value: window['<%= name %>'].getHTML()
											}
										).render();
									}
								);
							}
						);
					}
				}
			);
		</c:if>
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

			<c:if test="<%= showSource %>">
				var sourceEditor = Liferay.component('<%= name %>Source');

				if (sourceEditor) {
					sourceEditor.destroy();
				}
			</c:if>
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