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

String mainPath = themeDisplay.getPathMain();

String doAsUserId = themeDisplay.getDoAsUserId();

if (Validator.isNull(doAsUserId)) {
	doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
}

long doAsGroupId = themeDisplay.getDoAsGroupId();

String alloyEditorConfigFileName = ParamUtil.getString(request, "ckEditorConfigFileName");

if (!_alloyEditorConfigFileNames.contains(alloyEditorConfigFileName)) {
	alloyEditorConfigFileName = "alloyconfig.jsp";
}

String alloyEditorMode = ParamUtil.getString(request, "alloyEditorMode");

Map<String, String> configParamsMap = (Map<String, String>)request.getAttribute("liferay-ui:input-editor:configParams");

String configParams = marshallParams(configParamsMap);

String contents = (String)request.getAttribute("liferay-ui:input-editor:contents");
String contentsLanguageId = (String)request.getAttribute("liferay-ui:input-editor:contentsLanguageId");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String cssClasses = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClasses"));
String editorImpl = (String)request.getAttribute("liferay-ui:input-editor:editorImpl");
Map<String, String> fileBrowserParamsMap = (Map<String, String>)request.getAttribute("liferay-ui:input-editor:fileBrowserParams");
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name")) + "Editor";
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");
boolean inlineEdit = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:inlineEdit"));

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
boolean resizable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:resizable"));
boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));

String toolbarSet = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:toolbarSet"));

if (alloyEditorMode.equals("text")) {
	toolbarSet = "none";
}
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

<div class="alloy-editor alloy-editor-placeholder <%= cssClass %>" contenteditable="true" data-placeholder="<%= LanguageUtil.get(request, placeholder) %>" id="<%= name %>" name="<%= name %>"><%= contents %></div>

<aui:script use="aui-base">
	window['<%= name %>'] = {
		destroy: function() {
			CKEDITOR.instances['<%= name %>'].destroy();

			window['<%= name %>'] = null;
		},

		focus: function() {
			CKEDITOR.instances['<%= name %>'].focus();
		},

		getCkData: function() {
			var data;

			if (!window['<%= name %>'].instanceReady && window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>']) {
				data = window['<%= HtmlUtil.escapeJS(namespace + initMethod) %>']();
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
			<c:choose>
				<c:when test='<%= alloyEditorMode.equals("text") %>'>
					var editorElement = CKEDITOR.instances['<%= name %>'].element.$;

					var text = '';

					var childElement;

					if (editorElement.children.length) {
						childElement = editorElement.children[0];
					}
					else if (editorElement.childNodes.length) {
						childElement = editorElement.childNodes[0];
					}

					if (childElement) {
						text = childElement.textContent || childElement.innerText || childElement;
					}

					return text;
				</c:when>
				<c:otherwise>
					return window['<%= name %>'].getCkData();
				</c:otherwise>
			</c:choose>
		},

		getText: function() {
			return window['<%= name %>'].getCkData();
		},

		instanceReady: false,

		setHTML: function(value) {
			CKEDITOR.instances['<%= name %>'].setData(value);
		}
	};

	CKEDITOR.inline(
		'<%= name %>',
		{
			customConfig: '<%= PortalUtil.getPathContext() %>/html/js/editor/alloyeditor/<%= HtmlUtil.escapeJS(alloyEditorConfigFileName) %>?p_p_id=<%= HttpUtil.encodeURL(portletId) %>&p_main_path=<%= HttpUtil.encodeURL(mainPath) %>&contentsLanguageId=<%= HttpUtil.encodeURL(contentsLanguageId) %>&colorSchemeCssClass=<%= HttpUtil.encodeURL(themeDisplay.getColorScheme().getCssClass()) %>&cssClasses=<%= HttpUtil.encodeURL(cssClasses) %>&cssPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>&doAsGroupId=<%= HttpUtil.encodeURL(String.valueOf(doAsGroupId)) %>&doAsUserId=<%= HttpUtil.encodeURL(doAsUserId) %>&imagesPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeImages()) %>&inlineEdit=<%= inlineEdit %><%= configParams %>&languageId=<%= HttpUtil.encodeURL(LocaleUtil.toLanguageId(locale)) %>&name=<%= name %>&resizable=<%= resizable %>&toolbarSet=<%= HttpUtil.encodeURL(toolbarSet) %>',
				<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_SELECTOR %>" varImpl="documentSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="struts_action" value="/document_selector/view" />
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
				filebrowserImageBrowseUrl: '<%= documentSelectorURL %>&Type=image'
		}
	);

	if (window['<%= name %>Config']) {
		window['<%= name %>Config']();
	}

	var alloyEditor = CKEDITOR.instances['<%= name %>'];

	<c:if test="<%= Validator.isNotNull(onChangeMethod) %>">
		alloyEditor.on(
			'change',
			function(event) {
				window['<%= HtmlUtil.escapeJS(onChangeMethod) %>'](window['<%= name %>'].getHTML());
			}
		);
	</c:if>

	<c:if test="<%= Validator.isNotNull(onBlurMethod) %>">
		alloyEditor.on(
			'blur',
			function(event) {
				window['<%= HtmlUtil.escapeJS(onBlurMethod) %>'](event.editor);
			}
		);
	</c:if>

	<c:if test="<%= Validator.isNotNull(onFocusMethod) %>">
		alloyEditor.on(
			'focus',
			function(event) {
				window['<%= HtmlUtil.escapeJS(onFocusMethod) %>'](event.editor);
			}
		);
	</c:if>

	<c:if test='<%= alloyEditorMode.equals("text") %>'>
		alloyEditor.on(
			'key',
			function(event) {
				if (event.data.keyCode === 13) {
					event.cancel();
				}
			}
		);
	</c:if>

	alloyEditor.on(
		'instanceReady',
		function(event) {
			<c:if test="<%= Validator.isNotNull(onInitMethod) %>">
				window['<%= HtmlUtil.escapeJS(onInitMethod) %>']();
			</c:if>

			window['<%= name %>'].instanceReady = true;
		}
	);

	var destroyInstance = function(event) {
		if (event.portletId === '<%= portletId %>') {
			try {
				var ckeditorInstances = window.CKEDITOR.instances;

				A.Object.each(
					ckeditorInstances,
					function(value, key) {
						var instance = ckeditorInstances[key];

						A.Object.each(
							instance.config.toolbars,
							function(value, key) {
								value.destroy();
							}
						);

						delete ckeditorInstances[key];

						instance.destroy();
					}
				);
			}
			catch (error) {
			}

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

private static Set<String> _alloyEditorConfigFileNames = SetUtil.fromArray(new String[] {"alloyconfig.jsp"});
%>