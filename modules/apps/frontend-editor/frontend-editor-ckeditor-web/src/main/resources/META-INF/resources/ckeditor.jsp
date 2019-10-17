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

boolean autoCreate = GetterUtil.getBoolean((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":autoCreate"));
String contents = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":contents");
String cssClass = GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":data");
String editorName = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":editorName");
String initMethod = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":initMethod");
boolean inlineEdit = GetterUtil.getBoolean((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":inlineEdit"));
String inlineEditSaveURL = GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":inlineEditSaveURL"));
String name = GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":name"));

String onBlurMethod = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":onBlurMethod");

if (Validator.isNotNull(onBlurMethod)) {
	onBlurMethod = namespace + onBlurMethod;
}

String onChangeMethod = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

String onFocusMethod = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":onFocusMethod");

if (Validator.isNotNull(onFocusMethod)) {
	onFocusMethod = namespace + onFocusMethod;
}

String onInitMethod = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":onInitMethod");

if (Validator.isNotNull(onInitMethod)) {
	onInitMethod = namespace + onInitMethod;
}

boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":skipEditorLoading"));
String toolbarSet = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":toolbarSet");

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
	<liferay-editor:resources
		editorName="<%= editorName %>"
		inlineEdit="<%= inlineEdit %>"
		inlineEditSaveURL="<%= inlineEditSaveURL %>"
	/>
</c:if>

<%
String textareaName = HtmlUtil.escapeAttribute(name);

String modules = "aui-node-base";

if (inlineEdit && Validator.isNotNull(inlineEditSaveURL)) {
	textareaName = textareaName + "_original";

	modules += ",inline-editor-ckeditor";
}
%>

<liferay-util:buffer
	var="editor"
>
	<textarea id="<%= textareaName %>" name="<%= textareaName %>" style="display: none;"></textarea>
</liferay-util:buffer>

<div class="<%= HtmlUtil.escapeAttribute(cssClass) %>" id="<%= HtmlUtil.escapeAttribute(name) %>Container">
	<c:if test="<%= autoCreate %>">
		<%= editor %>
	</c:if>
</div>

<script type="text/javascript">
	CKEDITOR.disableAutoInline = true;

	CKEDITOR.dtd.$removeEmpty.i = 0;
	CKEDITOR.dtd.$removeEmpty.span = 0;
</script>

<%
name = HtmlUtil.escapeJS(name);
%>

<aui:script use="<%= modules %>">
	var UA = A.UA;

	var windowNode = A.getWin();

	var instanceDataReady = false;
	var instancePendingData;

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

	var onLocaleChangedHandler = function(event) {
		var contentsLanguage = event.item.getAttribute('data-value');
		var contentsLanguageDir = Liferay.Language.direction[contentsLanguage];

		var nativeEditor = window['<%= name %>'].getNativeEditor();

		nativeEditor.config.contentsLanguage = contentsLanguage;
		nativeEditor.config.contentsLangDirection = contentsLanguageDir;
	};

	var preventImageDragoverHandler = windowNode.on('dragover', function(event) {
		var validDropTarget = event.target.getDOMNode().isContentEditable;

		if (!validDropTarget) {
			event.preventDefault();
		}
	});

	var preventImageDropHandler = windowNode.on('drop', function(event) {
		var validDropTarget = event.target.getDOMNode().isContentEditable;

		if (!validDropTarget) {
			event.preventDefault();
			event.stopImmediatePropagation();
		}
	});

	var eventHandles = [
		Liferay.on('inputLocalized:localeChanged', onLocaleChangedHandler),
		preventImageDragoverHandler,
		preventImageDropHandler
	];

	window['<%= name %>'] = {
		create: function() {
			if (!window['<%= name %>'].instanceReady) {
				createEditor();
			}
		},

		destroy: function() {
			window['<%= name %>'].dispose();

			window['<%= name %>'] = null;

			Liferay.namespace('EDITORS').ckeditor.removeInstance();
		},

		dispose: function() {
			var editor = CKEDITOR.instances['<%= name %>'];

			if (editor) {
				editor.destroy();

				window['<%= name %>'].instanceReady = false;
			}

			new A.EventHandle(eventHandles).detach();

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
			} else {
				data = CKEDITOR.instances['<%= name %>'].getData();

				if (CKEDITOR.env.gecko && CKEDITOR.tools.trim(data) == '<br />') {
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
			} else {
				var editor = CKEDITOR.instances['<%= name %>'];

				data = editor.editable().getText();
			}

			return data;
		},

		instanceReady: false,

		<c:if test="<%= Validator.isNotNull(onBlurMethod) %>">
			onBlurCallback: function() {
				window['<%= HtmlUtil.escapeJS(onBlurMethod) %>'](
					CKEDITOR.instances['<%= name %>']
				);
			},
		</c:if>

		<c:if test="<%= Validator.isNotNull(onChangeMethod) %>">
			onChangeCallback: function() {
				var ckEditor = CKEDITOR.instances['<%= name %>'];
				var dirty = ckEditor.checkDirty();

				if (dirty) {
					window['<%= HtmlUtil.escapeJS(onChangeMethod) %>'](
						window['<%= name %>'].getHTML()
					);

					ckEditor.resetDirty();
				}
			},
		</c:if>

		<c:if test="<%= Validator.isNotNull(onFocusMethod) %>">
			onFocusCallback: function() {
				window['<%= HtmlUtil.escapeJS(onFocusMethod) %>'](
					CKEDITOR.instances['<%= name %>']
				);
			},
		</c:if>

		setHTML: function(value) {
			var ckEditorInstance = CKEDITOR.instances['<%= name %>'];

			var win = window['<%= name %>'];

			var setHTML = function(data) {
				if (instanceDataReady) {
					ckEditorInstance.setData(data);
				} else {
					instancePendingData = data;
				}

				win._setStyles();
			};

			if (win.instanceReady) {
				setHTML(value);
			} else {
				instancePendingData = value;
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

	Liferay.fire('editorAPIReady', {
		editor: window['<%= name %>'],
		editorName: '<%= name %>'
	});

	<c:if test="<%= inlineEdit && Validator.isNotNull(inlineEditSaveURL) %>">
		var inlineEditor;

		Liferay.on('toggleControls', function(event) {
			if (event.src === 'ui') {
				var ckEditor = CKEDITOR.instances['<%= name %>'];

				if (event.enabled && !ckEditor) {
					createEditor();
				} else if (ckEditor) {
					inlineEditor.destroy();
					ckEditor.destroy();

					var editorNode = A.one('#<%= name %>');

					if (editorNode) {
						editorNode.removeAttribute('contenteditable');
						editorNode.removeClass('lfr-editable');
					}
				}
			}
		});
	</c:if>

	var ckEditorContent;
	var currentToolbarSet;

	var initialToolbarSet =
		'<%= TextFormatter.format(HtmlUtil.escapeJS(toolbarSet), TextFormatter.M) %>';

	function getToolbarSet(toolbarSet) {
		var Util = Liferay.Util;

		if (Util.isPhone()) {
			toolbarSet = 'phone';
		} else if (Util.isTablet()) {
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

		if (!editorNode) {
			var editorContainer = A.one('#<%= name %>Container');

			editorContainer.setHTML('');

			editorNode = A.Node.create('<%= HtmlUtil.escapeJS(editor) %>');

			editorContainer.appendChild(editorNode);
		}

		if (editorNode) {
			editorNode.attr('contenteditable', true);
			editorNode.addClass('lfr-editable');

			eventHandles.push(A.Do.after(afterVal, editorNode, 'val', this));
		}

		function initData() {
			if (!ckEditorContent) {
				ckEditorContent = getInitialContent();
			}

			ckEditor.setData(ckEditorContent, function() {
				ckEditor.resetDirty();

				ckEditorContent = '';
			});

			window['<%= name %>']._setStyles();

			<c:if test="<%= Validator.isNotNull(onInitMethod) %>">
				window['<%= HtmlUtil.escapeJS(onInitMethod) %>']();
			</c:if>

			window['<%= name %>'].instanceReady = true;

			Liferay.component('<%= name %>', window['<%= name %>'], {
				portletId: '<%= portletId %>'
			});
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

		Liferay.on('<%= name %>selectItem', function(event) {
			CKEDITOR.tools.callFunction(event.ckeditorfuncnum, event.value);
		});

		var ckEditor = CKEDITOR.instances['<%= name %>'];

		<liferay-util:dynamic-include key='<%= "com.liferay.frontend.editor.ckeditor.web#" + editorName + "#onEditorCreate" %>' />

		Liferay.namespace('EDITORS').ckeditor.addInstance();

		<c:if test="<%= inlineEdit && Validator.isNotNull(inlineEditSaveURL) %>">
			inlineEditor = new Liferay.CKEditorInline({
				editor: ckEditor,
				editorName: '<%= name %>',
				namespace: '<portlet:namespace />',
				saveURL: '<%= inlineEditSaveURL %>'
			});
		</c:if>

		var customDataProcessorLoaded = false;

		<%
		boolean useCustomDataProcessor = (editorOptionsDynamicAttributes != null) && GetterUtil.getBoolean(editorOptionsDynamicAttributes.get("useCustomDataProcessor"));
		%>

		<c:if test="<%= useCustomDataProcessor %>">
			ckEditor.on('customDataProcessorLoaded', function() {
				customDataProcessorLoaded = true;

				if (instanceReady) {
					initData();
				}
			});
		</c:if>

		var instanceReady = false;

		ckEditor.on('instanceReady', function() {
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
				CKEDITOR.instances['<%= name %>'].on(
					'blur',
					window['<%= name %>'].onBlurCallback
				);
			</c:if>

			<c:if test="<%= Validator.isNotNull(onChangeMethod) %>">
				var contentChangeHandle = setInterval(function() {
					try {
						window['<%= name %>'].onChangeCallback();
					} catch (e) {}
				}, 300);

				var clearContentChangeHandle = function(event) {
					if (event.portletId === '<%= portletId %>') {
						clearInterval(contentChangeHandle);

						Liferay.detach('destroyPortlet', clearContentChangeHandle);
					}
				};

				Liferay.on('destroyPortlet', clearContentChangeHandle);
			</c:if>

			<c:if test="<%= Validator.isNotNull(onFocusMethod) %>">
				CKEDITOR.instances['<%= name %>'].on(
					'focus',
					window['<%= name %>'].onFocusCallback
				);
			</c:if>

			<c:if test="<%= !(inlineEdit && Validator.isNotNull(inlineEditSaveURL)) %>">
				var initialEditor = CKEDITOR.instances['<%= name %>'].id;

				eventHandles.push(
					A.getWin().on(
						'resize',
						A.debounce(function() {
							if (currentToolbarSet != getToolbarSet(initialToolbarSet)) {
								var ckeditorInstance =
									CKEDITOR.instances['<%= name %>'];

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

										window['<%= name %>'].setHTML(ckEditorContent);

										initialEditor =
											CKEDITOR.instances['<%= name %>'].id;
									}
								}
							}
						}, 250)
					)
				);
			</c:if>
		});

		ckEditor.on('dataReady', function(event) {
			if (instancePendingData) {
				var pendingData = instancePendingData;

				instancePendingData = null;

				ckEditor.setData(pendingData);
			} else {
				instanceDataReady = true;
			}

			window['<%= name %>']._setStyles();
		});

		ckEditor.on('setData', function(event) {
			instanceDataReady = false;
		});

		if (UA.edge && parseInt(UA.edge, 10) >= 14) {
			var resetActiveElementValidation = function(activeElement) {
				activeElement = A.one(activeElement);

				var activeElementAncestor = activeElement.ancestor();

				if (
					activeElementAncestor.hasClass('has-error') ||
					activeElementAncestor.hasClass('has-success')
				) {
					activeElementAncestor.removeClass('has-error');
					activeElementAncestor.removeClass('has-success');

					var formValidatorStack = activeElementAncestor.one(
						'.form-validator-stack'
					);

					if (formValidatorStack) {
						formValidatorStack.remove();
					}
				}
			};

			var onBlur = function(activeElement) {
				resetActiveElementValidation(activeElement);

				setTimeout(function() {
					if (activeElement) {
						ckEditor.focusManager.blur(true);
						activeElement.focus();
					}
				}, 0);
			};

			ckEditor.on('instanceReady', function() {
				var editorWrapper = A.one('#cke_<%= name %>');

				if (editorWrapper) {
					editorWrapper.once('mouseenter', function(event) {
						ckEditor.once(
							'focus',
							onBlur.bind(this, document.activeElement)
						);
						ckEditor.focus();
					});
				}
			});
		}
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