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

Map<String, String> configParamsMap = (Map<String, String>)request.getAttribute("liferay-ui:input-editor:configParams");

String configParams = marshallParams(configParamsMap);

String contentsLanguageId = (String)request.getAttribute("liferay-ui:input-editor:contentsLanguageId");
String cssClasses = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClasses"));
String editorImpl = (String)request.getAttribute("liferay-ui:input-editor:editorImpl");
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));
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

boolean resizable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:resizable"));
boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
%>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-util:html-top outputKey="js_editor_alloyeditor_skip_editor_loading">
		<link href="<%= PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() + "/editor/alloyeditor/assets/alloy-editor.css") %>" rel="stylesheet" type="text/css" />

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
			return window['<%= name %>'].getCkData();
		},

		getText: function() {
			return window['<%= name %>'].getCkData();
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
					window['<%= HtmlUtil.escapeJS(onChangeMethod) %>'](window['<%= name %>'].getText());

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
			CKEDITOR.instances['<%= name %>'].setData(value);
		}
	};

	document.getElementById('<%= name %>').setAttribute('contenteditable', true);

	CKEDITOR.inline(
		'<%= name %>',
		{
			customConfig: '<%= PortalUtil.getPathContext() %>/html/js/editor/alloyeditor/<%= HtmlUtil.escapeJS(alloyEditorConfigFileName) %>?p_p_id=<%= HttpUtil.encodeURL(portletId) %>&p_main_path=<%= HttpUtil.encodeURL(mainPath) %>&contentsLanguageId=<%= HttpUtil.encodeURL(contentsLanguageId) %>&colorSchemeCssClass=<%= HttpUtil.encodeURL(themeDisplay.getColorScheme().getCssClass()) %>&cssClasses=<%= HttpUtil.encodeURL(cssClasses) %>&cssPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>&doAsGroupId=<%= HttpUtil.encodeURL(String.valueOf(doAsGroupId)) %>&doAsUserId=<%= HttpUtil.encodeURL(doAsUserId) %>&imagesPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeImages()) %>&inlineEdit=<%= inlineEdit %><%= configParams %>&languageId=<%= HttpUtil.encodeURL(LocaleUtil.toLanguageId(locale)) %>&name=<%= name %>&resizable=<%= resizable %>'
		}
	);

	if (window['<%= name %>Config']) {
		window['<%= name %>Config']();
	}

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