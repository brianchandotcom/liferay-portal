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

<div class="<%= HtmlUtil.escapeAttribute(cssClass) %>" id="<%= HtmlUtil.escapeAttribute(name) %>Container">
	<c:if test="<%= autoCreate %>">
		<%= editor %>
	</c:if>
</div>

<%
name = HtmlUtil.escapeJS(name);
%>

<aui:script use="aui-node-base">
	var browseUrls = {
		file: 'filebrowserBrowseUrl',
		image: 'filebrowserImageBrowseUrl',
		media: 'filebrowserVideoBrowseUrl'
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

		filePickerCallback: function(callback, value, meta) {
			var url = tinymce.activeEditor.settings[browseUrls[meta.filetype]];

			if (url) {
				var openItemSelectorDialog = function(itemSelectorDialog) {
					itemSelectorDialog.eventName = '<%= name %>selectItem';
					itemSelectorDialog.singleSelect = true;
					itemSelectorDialog.url = url;
					itemSelectorDialog.zIndex =
						tinymce.activeEditor.windowManager.windows[0].zIndex + 10;

					itemSelectorDialog.on('selectedItemChange', function(event) {
						var selectedItem = event.selectedItem
							? event.selectedItem
							: value;

						if (
							selectedItem.returnType ===
							'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
						) {
							try {
								var itemValue = JSON.parse(selectedItem.value);

								var attachmentPrefix =
									tinymce.activeEditor.settings
										.attachmentURLPrefix;

								selectedItem = attachmentPrefix
									? attachmentPrefix + itemValue.title
									: itemValue.url;
							}
							catch (e) {}
						}
						else {
							selectedItem = selectedItem.value;
						}

						callback(selectedItem);
					});

					itemSelectorDialog.open();
				};

				var itemSelectorDialog = window['<%= name %>']._itemSelectorDialog;

				if (itemSelectorDialog) {
					openItemSelectorDialog(itemSelectorDialog);
				}
				else {
					Liferay.Loader.require(
						'frontend-js-web/liferay/ItemSelectorDialog.es',
						function(ItemSelectorDialog) {
							var itemSelectorDialog = new ItemSelectorDialog.default();

							window[
								'<%= name %>'
							]._itemSelectorDialog = itemSelectorDialog;

							openItemSelectorDialog(itemSelectorDialog);
						}
					);
				}
			}
		},

		focus: function() {
			if (window['<%= name %>'].instanceReady) {
				tinyMCE.editors['<%= name %>'].focus();
			}
			else {
				window['<%= name %>'].pendingFocus = true;
			}
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
				file_picker_callback: window['<%= name %>'].filePickerCallback,
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
				window['<%= name %>'].init(getInitialContent());
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
			var editor;

			if (window['<%= name %>'].instanceReady) {
				editor = tinyMCE.editors['<%= name %>'];
				editor.setContent(value);

				if (this.contentsLanguage && this.contentsLanguageDir) {
					editor.$().context.setAttribute('lang', this.contentsLanguage);
					editor
						.$()
						.context.setAttribute('dir', this.contentsLanguageDir);
				}
			}
			else {
				editor = document.getElementById('<%= name %>');
				editor.innerHTML = value;

				if (this.contentsLanguage && this.contentsLanguageDir) {
					editor.setAttribute('lang', this.contentsLanguage);
					editor.setAttribute('dir', this.contentsLanguageDir);
				}
			}
		},

		_onLocaleChangedHandler: function(event) {
			var instance = this;

			this.contentsLanguage = event.item.getAttribute('data-value');
			this.contentsLanguageDir =
				Liferay.Language.direction[this.contentsLanguage];
		}
	};

	Liferay.fire('editorAPIReady', {
		editor: window['<%= name %>'],
		editorName: '<%= name %>'
	});

	<c:if test="<%= autoCreate %>">
		window['<%= name %>'].initEditor();
	</c:if>
</aui:script>