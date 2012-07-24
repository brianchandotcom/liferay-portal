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
String portletId = portletDisplay.getRootPortletId();

String mainPath = themeDisplay.getPathMain();

String doAsUserId = themeDisplay.getDoAsUserId();

if (Validator.isNull(doAsUserId)) {
	doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
}

long doAsGroupId = themeDisplay.getDoAsGroupId();

String ckEditorConfigFileName = ParamUtil.getString(request, "ckEditorConfigFileName", "ckconfig.jsp");

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
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");

String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

boolean resizable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:resizable"));
boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
String toolbarSet = (String)request.getAttribute("liferay-ui:input-editor:toolbarSet");
%>

<c:if test="<%= hideImageResizing %>">
	<liferay-util:html-top outputKey="js_editor_ckeditor_hide_image_resizing">
		<style type="text/css">
			td.cke_dialog_ui_hbox_first {
				display:none !important;
			}

			td.cke_dialog_footer td.cke_dialog_ui_hbox_first {
				display:block !important;
			}

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

<div class="<%= cssClass %>">
	<textarea id="<%= name %>" name="<%= name %>" style="display: none;"></textarea>
</div>

<aui:script>
	(function() {
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

		CKEDITOR.replace(
			'<%= name %>',
			{
				customConfig: '<%= PortalUtil.getPathContext() %>/html/js/editor/ckeditor/<%= HtmlUtil.escapeJS(ckEditorConfigFileName) %>?p_l_id=<%= plid %>&p_p_id=<%= HttpUtil.encodeURL(portletId) %>&p_main_path=<%= HttpUtil.encodeURL(mainPath) %>&doAsUserId=<%= HttpUtil.encodeURL(doAsUserId) %>&doAsGroupId=<%= HttpUtil.encodeURL(String.valueOf(doAsGroupId)) %>&cssPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>&cssClasses=<%= HttpUtil.encodeURL(cssClasses) %>&imagesPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeImages()) %>&languageId=<%= HttpUtil.encodeURL(LocaleUtil.toLanguageId(locale)) %>&resizable=<%= resizable %><%= configParams %>',
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

				<%
				if (useCustomDataProcessor) {
				%>

					instanceReady = true;

					if (customDataProcessorLoaded) {
						setData();
					}

				<%
				}
				else {
				%>

					setData();

				<%
				}

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
%>