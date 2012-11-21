<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
System.out.println("test");

String portletId = portletDisplay.getRootPortletId();

String mainPath = themeDisplay.getPathMain();

String doAsUserId = themeDisplay.getDoAsUserId();

if (Validator.isNull(doAsUserId)) {
	doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
}

long doAsGroupId = themeDisplay.getDoAsGroupId();

String ckEditorConfigFileName = ParamUtil.getString(request, "ckEditorConfigFileName");

if (!_ckEditorConfigFileNames.contains(ckEditorConfigFileName)) {
	ckEditorConfigFileName = "ckconfig.jsp";
}

boolean useCustomDataProcessor = false;

if (!ckEditorConfigFileName.equals("ckconfig.jsp")) {
	useCustomDataProcessor = true;
}

boolean hideImageResizing = ParamUtil.getBoolean(request, "hideImageResizing");

Map<String, String> configParamsMap = (Map<String, String>)request.getAttribute("liferay-ui:input-editor:configParams");
Map<String, String> fileBrowserParamsMap = (Map<String, String>)request.getAttribute("liferay-ui:input-editor:fileBrowserParams");

String configParams = marshallParams(configParamsMap);
String fileBrowserParams = marshallParams(fileBrowserParamsMap);

String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String cssClasses = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClasses"));
String editorImpl = (String)request.getAttribute("liferay-ui:input-editor:editorImpl");
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");

String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

boolean inlineEdit = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:inlineEdit"));
String inlineEditSaveURL = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:inlineEditSaveURL"));
boolean resizable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:resizable"));
boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
String toolbarSet = (String)request.getAttribute("liferay-ui:input-editor:toolbarSet");

if (!inlineEdit) {
	name = namespace + name;
}
%>

<c:if test="<%= hideImageResizing %>">
	<liferay-util:html-top outputKey="js_editor_ckeditor_hide_image_resizing">
		<style type="text/css">
			a.cke_dialog_tab {
				display: none !important;
			}

			a.cke_dialog_tab_selected {
				display:block !important;
			}
		</style>
	</liferay-util:html-top>
</c:if>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-util:html-top outputKey="js_editor_ckeditor_skip_editor_loading">
		<style type="text/css">
			table.cke_dialog {
				position: absolute !important;
			}
		</style>

		<%
		long javaScriptLastModified = ServletContextUtil.getLastModified(application, "/html/js/", true);
		%>

		<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() + "/editor/ckeditor/ckeditor.js", javaScriptLastModified)) %>" type="text/javascript"></script>

		<script type="text/javascript">
			Liferay.namespace('EDITORS')['<%= editorImpl %>'] = true;
		</script>
	</liferay-util:html-top>
</c:if>

<aui:script>
	window['<%= name %>'] = {
		destroy: function() {
			CKEDITOR.instances['<%= name %>'].destroy();

			delete window['<%= name %>'];
		},

		focus: function() {
			CKEDITOR.instances['<%= name %>'].focus();
		},

		getCkData: function() {
			var data = CKEDITOR.instances['<%= name %>'].getData();

			if (CKEDITOR.env.gecko && (CKEDITOR.tools.trim(data) == '<br />')) {
				data = '';
			}

			return data;
		},

		getHTML: function() {
			return window['<%= name %>'].getCkData();
		},

		getText: function() {
			return window['<%= name %>'].getCkData();
		},

		<%
		if (Validator.isNotNull(onChangeMethod)) {
		%>

			onChangeCallback: function () {
				var ckEditor = CKEDITOR.instances['<%= name %>'];
				var dirty = ckEditor.checkDirty();

				if (dirty) {
					<%= HtmlUtil.escapeJS(onChangeMethod) %>(window['<%= name %>'].getText());

					ckEditor.resetDirty();
				}
			},

		<%
		}
		%>

		setHTML: function(value) {
			CKEDITOR.instances['<%= name %>'].setData(value);
		}
	};
</aui:script>

<%
	String textareaName = name;
%>

<c:if test="<%= inlineEdit %>">
<%
	textareaName = name + "_original";
%>
</c:if>

<div class="<%= cssClass %>">
	<textarea id="<%= textareaName %>" name="<%= textareaName %>" style="display: none;"></textarea>
</div>

<script type="text/javascript">
	CKEDITOR.disableAutoInline = true;
</script>

<style>
	div[contenteditable=true] {
		border: 1px solid #FFB914;
	}

	.notice-inline-edit {
		background-color: #ffc;
		border-radius: 5px;
		border: 1px solid #900;
		padding: 10px;
	}

	.notice-inline-edit-close {
		cursor: pointer;
		font-weight: bold;
		margin-left: 0.4em;
		text-decoration: underline;
	}

	.notice-inline-edit-error .notice-inline-edit {
		background-color: #ff0000;
	}

	.notice-inline-edit-error .notice-inline-edit-text {
		color: #ffc;
	}

	.notice-inline-edit-error .notice-inline-edit-close {
		color: #ffff64;
	}

	.notice-inline-edit-text {
		color: #900;
		font-weight: bold;
	}
</style>

<aui:script use="overlay,node-scroll-info">
	(function() {

		<c:if test="<%= inlineEdit && inlineEditSaveURL != null %>">

		var Lang = A.Lang;
		
		var saveTimer, closeTimer, scrollListener;

		function afterSaveContentFailure(responseData, autosaved) {
			var instance = this;

			instance.resetDirty();

			var notice = getEditNotice();

			var boundingBox = notice.get('boundingBox');

			boundingBox.addClass('notice-inline-edit-error');

			boundingBox.one('.notice-inline-edit-text').html(Liferay.Language.get('the-draft-was-not-saved-successfully'));

			notice.show();

			updateNoticePosition();

			addCloseNoticeListener();
		}

		function afterSaveContentSuccess(responseData, autosaved) {
			var instance = this;

			instance.resetDirty();

			var notice = getEditNotice();
			
			var boundingBox = notice.get('boundingBox');

			boundingBox.removeClass('.notice-inline-edit-error');

			var message = Liferay.Language.get('the-draft-was-saved-successfully-at-x');

			if (autosaved) {
				message = Liferay.Language.get('the-draft-was-autosaved-successfully-at-x');
			}

			message = Lang.sub(
				message,
				[
					new Date().toLocaleTimeString()
				]
			);

			boundingBox.one('.notice-inline-edit-text').html(message);

			notice.show();

			updateNoticePosition();

			addCloseNoticeListener();
		}

		function attachScrollListener() {
			var notice = getEditNotice();

			var noticeNode = notice.get('boundingBox');

			if (!noticeNode.getData('bodyScrollListener')) {
				var body = A.one('body');

				body.plug(A.Plugin.ScrollInfo);

				scrollListener = body.scrollInfo.on('scroll', updateNoticePosition);

				noticeNode.setData('bodyScrollListener', scrollListener);
			}
		}

		function attachCloseListener() {
			var notice = getEditNotice();

			var boundingBox = notice.get('boundingBox');

			boundingBox.one('.notice-inline-edit-close').on('click', A.bind(notice.hide, notice));
		}

		function addCloseNoticeListener() {
			window.clearTimeout(closeTimer);

			closeTimer = window.setTimeout(
				function() {
					var notice = getEditNotice();

					notice.hide();
				},
				CKEDITOR.config.closeNoticeTimeout
			);
		}

		function getEditNotice() {
			var triggerNode = A.one('#cke_<%= name %>');

			var editNoticeNode = A.one('.notice-inline-edit');

			if (!editNoticeNode) {
				editNotice = new A.Overlay(
					{
						bodyContent:
							'<div class="notice-inline-edit">' +
								'<span class="notice-inline-edit-text"></span>' +
								'<span class="notice-inline-edit-close" tab-index="0">Close</span>' +
							'</div>',
						visible: false,
						zIndex: triggerNode.getStyle('zIndex') + 2
					}
				).render();

				editNotice.get('boundingBox').one('.notice-inline-edit').setData('noticeInstance', editNotice);

				attachCloseListener();
			}
			else {
				editNotice = editNoticeNode.getData('noticeInstance');
			}

			return editNotice;
		}

		function onEditorBlur() {
			var instance = this;

			window.clearTimeout(saveTimer);

			saveContent.call(instance);
		}

		function onEditorFocus() {
			var instance = this;

			var originalContentNode = A.one('#<%= name %>_original');

			if (!originalContentNode.text()) {
				originalContentNode.text(window['<%= name %>'].getHTML());
			}

			var notice = getEditNotice();

			var noticeNode = notice.get('boundingBox');

			if (notice.get('visible') && noticeNode.getData('editor') !== '<%= name %>') {
				notice.set('visible', false);

				noticeNode.setData('bodyScrollListener', null);

				if (scrollListener) {
					scrollListener.detach();
				}
			}

			setNoticeEditor.call(instance);

			setSaveTimer.call(instance);

			attachScrollListener();

			instance.resetDirty();
		}

		function restoreContent() {
			var instance = this;

			var originalContentNode = A.one('#<%= name %>_original');

			var originalContent = originalContentNode.text();

			window['<%= name %>'].setHTML(originalContent);

			saveContent.call(instance);
		}
		
		function saveContent(autosaved) {
			var instance = this;

			if (instance.checkDirty()) {
				A.io.request(
					'<%= inlineEditSaveURL %>',
					{
						after: {
							failure: function() {
								var responseData = this.get('responseData');

								afterSaveContentFailure.call(instance, responseData, autosaved);
							},
							success: function() {
								var responseData = this.get('responseData');

								if (responseData.success) {
									afterSaveContentSuccess.call(instance, responseData, autosaved);
								}
								else {
									afterSaveContentFailure.call(instance, responseData, autosaved);
								}
							}
						},
						data: {
							entryData: window['<%= name %>'].getHTML()
						},
						dataType: 'json'
					}
				);
			}
		}

		function setNoticeEditor() {
			var notice = getEditNotice();

			var noticeNode = notice.get('boundingBox');

			noticeNode.setData('editor', '<%= name %>');
		}

		function setSaveTimer() {
			var instance = this;

			window.clearTimeout(saveTimer);

			saveTimer = window.setInterval(A.bind(saveContent, instance, true), CKEDITOR.config.autoSaveTimeout);
		}

		function updateNoticePosition() {
			var notice = getEditNotice();

			if (notice.get('visible')) {
				var editorToolbarNode = A.one('#cke_<%= name %>');

				var editorToolbarVisible = editorToolbarNode.getStyle('display') !== 'none';

				var alignNode;

				if (editorToolbarVisible) {
					var noticePosition = 'tl';
					var containerPostion = 'bl';

					if (parseInt(editorToolbarNode.getStyle('top'), 10) > 30) {
						noticePosition = 'bl';
						containerPostion = 'tl';
					}

					alignNode = {
						node: editorToolbarNode,
						points: [noticePosition, containerPostion]
					};

					notice.set('align', alignNode);
				}
				else {
					notice.set('align', null);

					var viewport = A.DOM.viewportRegion();

					notice.set('xy', [(viewport.right - viewport.left) / 2, viewport.top]);
				}
			}
		}

		</c:if>

		function setData() {
			<c:if test="<%= Validator.isNotNull(initMethod) %>">
				ckEditor.setData(<%= HtmlUtil.escapeJS(namespace + initMethod) %>());
			</c:if>
		}

		<%
		StringBundler sb = new StringBundler(10);

		sb.append(mainPath);
		sb.append("/portal/fckeditor?p_l_id=");
		sb.append(plid);
		sb.append("&p_p_id=");
		sb.append(HttpUtil.encodeURL(portletId));
		sb.append("&doAsUserId=");
		sb.append(HttpUtil.encodeURL(doAsUserId));
		sb.append("&doAsGroupId=");
		sb.append(HttpUtil.encodeURL(String.valueOf(doAsGroupId)));
		sb.append(fileBrowserParams);

		String connectorURL = HttpUtil.encodeURL(sb.toString());
		%>

		<c:choose>
			<c:when test="<%= inlineEdit %>">
				CKEDITOR.inline(
			</c:when>
			<c:otherwise>
				CKEDITOR.replace(
			</c:otherwise>
		</c:choose>

			'<%= name %>',
			{
				customConfig: '<%= PortalUtil.getPathContext() %>/html/js/editor/ckeditor/<%= HtmlUtil.escapeJS(ckEditorConfigFileName) %>?p_l_id=<%= plid %>&p_p_id=<%= HttpUtil.encodeURL(portletId) %>&p_main_path=<%= HttpUtil.encodeURL(mainPath) %>&doAsUserId=<%= HttpUtil.encodeURL(doAsUserId) %>&doAsGroupId=<%= HttpUtil.encodeURL(String.valueOf(doAsGroupId)) %>&cssPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>&cssClasses=<%= HttpUtil.encodeURL(cssClasses) %>&imagesPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeImages()) %>&languageId=<%= HttpUtil.encodeURL(LocaleUtil.toLanguageId(locale)) %>&resizable=<%= resizable %>&inlineEdit=<%= inlineEdit %><%= configParams %>',
				filebrowserBrowseUrl: '<%= PortalUtil.getPathContext() %>/html/js/editor/ckeditor/editor/filemanager/browser/liferay/browser.html?Connector=<%= connectorURL %><%= fileBrowserParams %>',
				filebrowserUploadUrl: null,
				toolbar: '<%= TextFormatter.format(HtmlUtil.escapeJS(toolbarSet), TextFormatter.M) %>'
			}
		);

		var ckEditor = CKEDITOR.instances['<%= name %>'];

		var customDataProcessorLoaded = false;

		<%
		if (useCustomDataProcessor) {
		%>

			ckEditor.on(
				'customDataProcessorLoaded',
				function() {
					customDataProcessorLoaded = true;

					if (instanceReady) {
						setData();
					}
				}
			);

		<%
		}
		%>

		var instanceReady = false;

		ckEditor.on(
			'instanceReady',
			function() {

			<c:choose>
				<c:when test="<%= inlineEdit && inlineEditSaveURL != null %>">
					var ckEditor = CKEDITOR.instances['<%= name %>'];

					ckEditor.on('blur', A.bind(onEditorBlur, ckEditor));

					ckEditor.on('focus', A.bind(onEditorFocus, ckEditor));

					ckEditor.on('saveContent', A.bind(saveContent, ckEditor));

					ckEditor.on('restoreContent', A.bind(restoreContent, ckEditor));
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="<%= useCustomDataProcessor %>">
							instanceReady = true;

							if (customDataProcessorLoaded) {
								setData();
							}
						</c:when>
						<c:otherwise>
							setData();
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>

	<%

			if (Validator.isNotNull(onChangeMethod)) {
				%>

					setInterval(
						function() {
							try {
								window['<%= name %>'].onChangeCallback();
							}
							catch (e) {
							}
						},
						300
					);

				<%
				}
				%>

			}
		);

		<%
		if (toolbarSet.equals("creole")) {
		%>

			Liferay.provide(
				window,
				'<%= name %>creoleDialogHandlers',
				function(event) {
					var A = AUI();

					var MODIFIED = 'modified';

					var SELECTOR_HBOX_FIRST = '.cke_dialog_ui_hbox_first';

					var dialog = event.data.definition.dialog;

					if (dialog.getName() == 'image') {
						var lockButton = A.one('.cke_btn_locked');

						if (lockButton) {
							var imageProperties = lockButton.ancestor(SELECTOR_HBOX_FIRST);

							if (imageProperties) {
								imageProperties.hide();
							}
						}

						var imagePreviewBox = A.one('.ImagePreviewBox');

						if (imagePreviewBox) {
							imagePreviewBox.setStyle('width', 410);
						}
					}
					else if (dialog.getName() == 'cellProperties') {
						var containerNode = A.one('#' + dialog.getElement('cellType').$.id);

						if (!containerNode.getData(MODIFIED)) {
							containerNode.one(SELECTOR_HBOX_FIRST).hide();

							containerNode.one('.cke_dialog_ui_hbox_child').hide();

							var cellTypeWrapper = containerNode.one('.cke_dialog_ui_hbox_last');

							cellTypeWrapper.replaceClass('cke_dialog_ui_hbox_last', 'cke_dialog_ui_hbox_first');

							cellTypeWrapper.setStyle('width', '100%');

							cellTypeWrapper.all('tr').each(
								function(item, index, collection) {
									if (index > 0) {
										item.hide();
									}
								}
							);

							containerNode.setData(MODIFIED, true);
						}
					}
				},
				['aui-base']
			);

			ckEditor.on('dialogShow', window['<%= name %>creoleDialogHandlers']);

		<%
		}
		%>

	})();

</aui:script>

<%!
public String marshallParams(Map<String, String> params) {
	StringBundler sb = new StringBundler();

	if (params != null) {
		for (Map.Entry<String, String> configParam : params.entrySet()) {
			sb.append(StringPool.AMPERSAND);
			sb.append(configParam.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(HttpUtil.encodeURL(configParam.getValue()));
		}
	}

	return sb.toString();
}

private static Set<String> _ckEditorConfigFileNames = SetUtil.fromArray(new String[] {"ckconfig.jsp", "ckconfig_bbcode.jsp", "ckconfig_creole.jsp"});
%>