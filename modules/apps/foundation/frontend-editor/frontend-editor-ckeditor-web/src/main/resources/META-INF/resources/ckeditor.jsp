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
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:input-editor:data");
String editorName = (String)request.getAttribute("liferay-ui:input-editor:editorName");
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");
boolean inlineEdit = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:inlineEdit"));
String inlineEditSaveURL = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:inlineEditSaveURL"));
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));

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

boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
String toolbarSet = (String)request.getAttribute("liferay-ui:input-editor:toolbarSet");

if (!inlineEdit) {
	name = namespace + name;
}

JSONObject editorConfigJSONObject = null;

if (data != null) {
	editorConfigJSONObject = (JSONObject)data.get("editorConfig");
}

EditorOptions editorOptions = null;

if (data != null) {
	editorOptions = (EditorOptions)data.get("editorOptions");
}

Map<String, Object> editorOptionsDynamicAttributes = null;

if (editorOptions != null) {
	editorOptionsDynamicAttributes = editorOptions.getDynamicAttributes();
}
%>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-util:html-top outputKey="js_editor_ckeditor_skip_editor_loading">
		<style type="text/css">
			table.cke_dialog {
				position: absolute !important;
			}
		</style>

		<%
		long javaScriptLastModified = PortalWebResourcesUtil.getLastModified(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR);
		%>

		<script data-senna-track="temporary" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR) + "/ckeditor/ckeditor.js", javaScriptLastModified)) %>" type="text/javascript"></script>

		<c:if test="<%= inlineEdit && Validator.isNotNull(inlineEditSaveURL) %>">
			<script data-senna-track="temporary" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR) + "/ckeditor/main.js", javaScriptLastModified)) %>" type="text/javascript"></script>
		</c:if>

		<liferay-util:dynamic-include key='<%= "com.liferay.frontend.editor.ckeditor.web#" + editorName + "#additionalResources" %>' />

		<script data-senna-track="temporary" type="text/javascript">
			Liferay.namespace('EDITORS')['<%= editorName %>'] = true;

			CKEDITOR.scriptLoader.loadScripts = function(scripts, success, failure) {
				CKEDITOR.scriptLoader.load(scripts, success, failure);
			};

			CKEDITOR.getNextZIndex = function() {
				return CKEDITOR.dialog._.currentZIndex ? CKEDITOR.dialog._.currentZIndex + 10 : Liferay.zIndex.WINDOW + 10;
			};

			var destroyGlobalEditor = function() {
				window.CKEDITOR = undefined;

				Liferay.detach('beforeScreenFlip', destroyGlobalEditor);
			};

			Liferay.on('beforeScreenFlip', destroyGlobalEditor);
		</script>
	</liferay-util:html-top>
</c:if>

<%
String textareaName = HtmlUtil.escapeAttribute(name);

String modules = "aui-node-base";

if (inlineEdit && Validator.isNotNull(inlineEditSaveURL)) {
	textareaName = textareaName + "_original";

	modules += ",inline-editor-ckeditor";
}
%>

<liferay-util:buffer var="editor">
	<textarea id="<%= textareaName %>" name="<%= textareaName %>" style="display: none;"></textarea>
</liferay-util:buffer>

<div class="<%= HtmlUtil.escapeAttribute(cssClass) %>" id="<%= HtmlUtil.escapeAttribute(name) %>Container">
	<c:if test="<%= autoCreate %>">
		<%= editor %>
	</c:if>
</div>

<script type="text/javascript">
	CKEDITOR.disableAutoInline = true;
</script>

<%
name = HtmlUtil.escapeJS(name);
%>

<aui:script use="<%= modules %>">
	var getInitialContent = function() {
		var data;

		if (window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>']) {
			data = <%= HtmlUtil.escapeJS(namespace + initMethod) %>();
		}
		else {
			data = '<%= contents != null ? HtmlUtil.escapeJS(contents) : StringPool.BLANK %>';
		}

		return data;
	};

	window['<%= name %>'] = {
		create: function() {
			if (!window['<%= name %>'].instanceReady) {
				var editorNode = A.Node.create('<%= HtmlUtil.escapeJS(editor) %>');

				var editorContainer = A.one('#<%= name %>Container');

				editorContainer.appendChild(editorNode);

				createEditor();
			}
		},

		destroy: function() {
			window['<%= name %>'].dispose();

			window['<%= name %>'] = null;
		},

		dispose: function() {
			var editor = CKEDITOR.instances['<%= name %>'];

			if (editor) {
				editor.destroy();

				window['<%= name %>'].instanceReady = false;
			}

			var editorEl = document.getElementById('<%= name %>');

			if (editorEl) {
				editorEl.parentNode.removeChild(editorEl);
			}
		},

		focus: function() {
			CKEDITOR.instances['<%= name %>'].focus();
		},

		getCkData: function() {
			var data;

			if (!window['<%= name %>'].instanceReady) {
				data = getInitialContent();
			}
			else {
				data = CKEDITOR.instances['<%= name %>'].getData();

				if (CKEDITOR.env.gecko && (CKEDITOR.tools.trim(data) == '<br />')) {
					data = '';
				}
			}

			return data;
		},

		getHTML: function() {
			return window['<%= name %>'].getCkData();
		},

		getNativeEditor: function() {
			return CKEDITOR.instances['<%= name %>'];
		},

		getText: function() {
			var data;

			if (!window['<%= name %>'].instanceReady) {
				data = getInitialContent();
			}
			else {
				var editor = CKEDITOR.instances['<%= name %>'];

				data = editor.editable().getText();
			}

			return data;
		},

		instanceReady: false,

		<c:if test="<%= Validator.isNotNull(onBlurMethod) %>">
			onBlurCallback: function() {
				window['<%= HtmlUtil.escapeJS(onBlurMethod) %>'](CKEDITOR.instances['<%= name %>']);
			},
		</c:if>

		<c:if test="<%= Validator.isNotNull(onChangeMethod) %>">
			onChangeCallback: function() {
				var ckEditor = CKEDITOR.instances['<%= name %>'];
				var dirty = ckEditor.checkDirty();

				if (dirty) {
					window['<%= HtmlUtil.escapeJS(onChangeMethod) %>'](window['<%= name %>'].getHTML());

					ckEditor.resetDirty();
				}
			},
		</c:if>

		<c:if test="<%= Validator.isNotNull(onFocusMethod) %>">
			onFocusCallback: function() {
				window['<%= HtmlUtil.escapeJS(onFocusMethod) %>'](CKEDITOR.instances['<%= name %>']);
			},
		</c:if>

		setHTML: function(value) {
			var ckEditorInstance = CKEDITOR.instances['<%= name %>'];

			var win = window['<%= name %>'];

			var setHTML = function(data) {
				ckEditorInstance.setData(data);

				win._setStyles();
			};

			if (win.instanceReady) {
				setHTML(value);
			}
			else {
				ckEditorInstance.on(
					'instanceReady',
					function() {
						setHTML(value);
					}
				);
			}
		}
	};

	var addAUIClass = function(iframe) {
		if (iframe) {
			var iframeWin = iframe.getDOM().contentWindow;

			if (iframeWin) {
				var iframeDoc = iframeWin.document.documentElement;

				A.one(iframeDoc).addClass('aui');
			}
		}
	};

	window['<%= name %>']._setStyles = function() {
		var ckEditor = A.one('#cke_<%= name %>');

		if (ckEditor) {
			var iframe = ckEditor.one('iframe');

			addAUIClass(iframe);

			var ckePanelDelegate = Liferay.Data['<%= name %>Handle'];

			if (!ckePanelDelegate) {
				ckePanelDelegate = ckEditor.delegate(
					'click',
					function(event) {
						var panelFrame = A.one('.cke_combopanel .cke_panel_frame');

						addAUIClass(panelFrame);

						ckePanelDelegate.detach();

						Liferay.Data['<%= name %>Handle'] = null;
					},
					'.cke_combo'
				);

				Liferay.Data['<%= name %>Handle'] = ckePanelDelegate;
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

	<c:if test="<%= inlineEdit && Validator.isNotNull(inlineEditSaveURL) %>">
		var inlineEditor;

		Liferay.on(
			'toggleControls',
			function(event) {
				if (event.src === 'ui') {
					var ckEditor = CKEDITOR.instances['<%= name %>'];

					if (event.enabled && !ckEditor) {
						createEditor();
					}
					else if (ckEditor) {
						inlineEditor.destroy();
						ckEditor.destroy();

						var editorNode = A.one('#<%= name %>');

						if (editorNode) {
							editorNode.removeAttribute('contenteditable');
							editorNode.removeClass('lfr-editable');
						}
					}
				}
			}
		);
	</c:if>

	var ckEditorContent;
	var currentToolbarSet;

	var initialToolbarSet = '<%= TextFormatter.format(HtmlUtil.escapeJS(toolbarSet), TextFormatter.M) %>';

	function getToolbarSet(toolbarSet) {
		var Util = Liferay.Util;

		if (Util.isPhone()) {
			toolbarSet = 'phone';
		}
		else if (Util.isTablet()) {
			toolbarSet = 'tablet';
		}

		return toolbarSet;
	}

	var afterVal = function() {
		return new A.Do.AlterReturn(
			'Return editor content',
			window['<%= name %>'].getHTML()
		);
	};

	var createEditor = function() {
		var editorNode = A.one('#<%= name %>');

		if (editorNode) {
			editorNode.attr('contenteditable', true);
			editorNode.addClass('lfr-editable');

			var eventHandles = [
				A.Do.after(afterVal, editorNode, 'val', this)
			];
		}

		function initData() {
			<c:if test="<%= Validator.isNotNull(initMethod) && !(inlineEdit && Validator.isNotNull(inlineEditSaveURL)) %>">
				if (!ckEditorContent) {
					<c:choose>
						<c:when test="<%= contents != null %>">
							ckEditorContent = '<%= HtmlUtil.escapeJS(contents) %>';
						</c:when>
						<c:otherwise>
							ckEditorContent = window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>']();
						</c:otherwise>
					</c:choose>
				}

				ckEditor.setData(
					ckEditorContent,
					function() {
						ckEditor.resetDirty();

						ckEditorContent = '';
					}
				);
			</c:if>

			window['<%= name %>']._setStyles();

			<c:if test="<%= Validator.isNotNull(onInitMethod) %>">
				window['<%= HtmlUtil.escapeJS(onInitMethod) %>']();
			</c:if>

			window['<%= name %>'].instanceReady = true;
		}

		currentToolbarSet = getToolbarSet(initialToolbarSet);

		var defaultConfig = {
			filebrowserBrowseUrl: '',
			filebrowserFlashBrowseUrl: '',
			filebrowserImageBrowseLinkUrl: '',
			filebrowserImageBrowseUrl: '',
			filebrowserUploadUrl: null,
			toolbar: currentToolbarSet
		};

		var editorConfig = <%= Validator.isNotNull(editorConfigJSONObject) ? editorConfigJSONObject : "{}" %>;

		var config = A.merge(defaultConfig, editorConfig);

		CKEDITOR.<%= inlineEdit ? "inline" : "replace" %>('<%= name %>', config);

		Liferay.on(
			'<%= name %>selectItem',
			function(event) {
				CKEDITOR.tools.callFunction(event.ckeditorfuncnum, event.value);
			}
		);

		var ckEditor = CKEDITOR.instances['<%= name %>'];

		<liferay-util:dynamic-include key='<%= "com.liferay.frontend.editor.ckeditor.web#" + editorName + "#onEditorCreate" %>' />

		<c:if test="<%= inlineEdit && Validator.isNotNull(inlineEditSaveURL) %>">
			inlineEditor = new Liferay.CKEditorInline(
				{
					editor: ckEditor,
					editorName: '<%= name %>',
					namespace: '<portlet:namespace />',
					saveURL: '<%= inlineEditSaveURL %>'
				}
			);
		</c:if>

		var customDataProcessorLoaded = false;

		<%
		boolean useCustomDataProcessor = (editorOptionsDynamicAttributes != null) && GetterUtil.getBoolean(editorOptionsDynamicAttributes.get("useCustomDataProcessor"));
		%>

		<c:if test="<%= useCustomDataProcessor %>">
			ckEditor.on(
				'customDataProcessorLoaded',
				function() {
					customDataProcessorLoaded = true;

					if (instanceReady) {
						initData();
					}
				}
			);
		</c:if>

		var instanceReady = false;

		ckEditor.on(
			'instanceReady',
			function() {

				<c:choose>
					<c:when test="<%= useCustomDataProcessor %>">
						instanceReady = true;

						if (customDataProcessorLoaded) {
							initData();
						}
					</c:when>
					<c:otherwise>
						initData();
					</c:otherwise>
				</c:choose>

				<c:if test="<%= Validator.isNotNull(onBlurMethod) %>">
					CKEDITOR.instances['<%= name %>'].on('blur', window['<%= name %>'].onBlurCallback);
				</c:if>

				<c:if test="<%= Validator.isNotNull(onChangeMethod) %>">
					var contentChangeHandle = setInterval(
						function() {
							try {
								window['<%= name %>'].onChangeCallback();
							}
							catch (e) {
							}
						},
						300
					);

					var clearContentChangeHandle = function(event) {
						if (event.portletId === '<%= portletId %>') {
							clearInterval(contentChangeHandle);

							Liferay.detach('destroyPortlet', clearContentChangeHandle);
						}
					};

					Liferay.on('destroyPortlet', clearContentChangeHandle);
				</c:if>

				<c:if test="<%= Validator.isNotNull(onFocusMethod) %>">
					CKEDITOR.instances['<%= name %>'].on('focus', window['<%= name %>'].onFocusCallback);
				</c:if>

				<c:if test="<%= !(inlineEdit && Validator.isNotNull(inlineEditSaveURL)) %>">
					var initialEditor = CKEDITOR.instances['<%= name %>'].id;

					A.getWin().on(
						'resize',
						A.debounce(
							function() {
								if (currentToolbarSet != getToolbarSet(initialToolbarSet)) {
									var ckeditorInstance = CKEDITOR.instances['<%= name %>'];

									if (ckeditorInstance) {
										var currentEditor = ckeditorInstance.id;

										if (currentEditor === initialEditor) {
											var currentDialog = CKEDITOR.dialog.getCurrent();

											if (currentDialog) {
												currentDialog.hide();
											}

											ckEditorContent = ckeditorInstance.getData();

											window['<%= name %>'].dispose();

											window['<%= name %>'].create();

											initialEditor = CKEDITOR.instances['<%= name %>'].id;
										}
									}
								}
							},
							250
						)
					);
				</c:if>

				var destroyInstance = function(event) {
					if (event.portletId === '<%= portletId %>') {
						try {
							var ckeditorInstances = window.CKEDITOR.instances;

							A.Object.each(
								ckeditorInstances,
								function(value, key) {
									var inst = ckeditorInstances[key];

									delete ckeditorInstances[key];

									inst.destroy();
								}
							);
						}
						catch (e) {
						}

						(new A.EventHandle(eventHandles)).detach();

						Liferay.detach('destroyPortlet', destroyInstance);
					}
				};

				Liferay.on('destroyPortlet', destroyInstance);
			}
		);

		ckEditor.on('dataReady', window['<%= name %>']._setStyles);
	};

	<%
	String toogleControlsStatus = GetterUtil.getString(SessionClicks.get(request, "com.liferay.frontend.js.web_toggleControls", "visible"));
	%>

	<c:if test='<%= autoCreate && ((inlineEdit && toogleControlsStatus.equals("visible")) || !inlineEdit) %>'>
		createEditor();
	</c:if>
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
		sb.append(URLCodec.encodeURL(configParam.getValue()));
	}

	return sb.toString();
}
%>