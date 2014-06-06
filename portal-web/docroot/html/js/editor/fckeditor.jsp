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

StringBundler configParamsSB = new StringBundler();

Map<String, String> configParams = (Map<String, String>)request.getAttribute("liferay-ui:input-editor:configParams");

if (configParams != null) {
	for (Map.Entry<String, String> configParam : configParams.entrySet()) {
		configParamsSB.append(StringPool.AMPERSAND);
		configParamsSB.append(configParam.getKey());
		configParamsSB.append(StringPool.EQUAL);
		configParamsSB.append(HttpUtil.encodeURL(configParam.getValue()));
	}
}

String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String cssClasses = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClasses"));
String editorImpl = (String)request.getAttribute("liferay-ui:input-editor:editorImpl");
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));

String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
String toolbarSet = (String)request.getAttribute("liferay-ui:input-editor:toolbarSet");

// To upgrade FCKEditor, download the latest version and unzip it to fckeditor.
// Add custom configuration to fckeditor/fckconfig.jsp. Copy
// fckeditor/editor/filemanager/browser/default to
// fckeditor/editor/filemanager/browser/liferay. Modify browser.html,
// frmresourceslist.html, frmresourcetype.html, and frmupload.html.

%>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-util:html-top outputKey="js_editor_fckeditor">

		<%
		long javaScriptLastModified = ServletContextUtil.getLastModified(application, "/html/js/", true);
		%>

		<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() + "/editor/fckeditor/fckeditor.js", javaScriptLastModified)) %>" type="text/javascript"></script>

		<script type="text/javascript">
			Liferay.namespace('EDITORS')['<%= editorImpl %>'] = true;
		</script>
	</liferay-util:html-top>
</c:if>

<aui:script>
	window['<%= name %>'] = {
		destroy: function() {
			var fckEditor = FCKeditorAPI.GetInstance('<%= name %>');

			if (fckEditor) {
				var configEl = document.getElementById('<%= name %>__Config');
				var frameEl = document.getElementById('<%= name %>__Frame');

				if (configEl) {
					configEl.parentNode.removeChild(configEl);
				}

				if (frameEl) {
					frameEl.parentNode.removeChild(frameEl);
				}

				delete FCKeditorAPI.__Instances['<%= name %>'];
			}

			window['<%= name %>'] = null;
		},

		focus: function() {
			FCKeditorAPI.GetInstance('<%= name %>').Focus();
		},

		getHTML: function() {
			return FCKeditorAPI.GetInstance('<%= name %>').GetXHTML();
		},

		getText: function() {
			return FCKeditorAPI.GetInstance('<%= name %>').GetXHTML();
		},

		initFckArea: function() {
			var textArea = document.getElementById('<%= name %>');

			<c:if test="<%= Validator.isNotNull(initMethod) %>">
				textArea.value = <%= HtmlUtil.escape(namespace + initMethod) %>();
			</c:if>

			var fckEditor = new FCKeditor('<%= name %>');

			fckEditor.Config['CustomConfigurationsPath'] = '<%= PortalUtil.getPathContext() %>/html/js/editor/fckeditor/fckconfig.jsp?p_l_id=<%= plid %>&p_p_id=<%= HttpUtil.encodeURL(portletId) %>&p_main_path=<%= HttpUtil.encodeURL(mainPath) %>&doAsUserId=<%= HttpUtil.encodeURL(doAsUserId) %>&doAsGroupId=<%= HttpUtil.encodeURL(String.valueOf(doAsGroupId)) %>&cssPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>&cssClasses=<%= HttpUtil.encodeURL(cssClasses) %>&languageId=<%= HttpUtil.encodeURL(LocaleUtil.toLanguageId(locale)) %><%= configParamsSB.toString() %>';

			fckEditor.BasePath = '<%= PortalUtil.getPathContext() %>/html/js/editor/fckeditor/';
			fckEditor.Width = '100%';
			fckEditor.Height = '100%';
			fckEditor.ToolbarSet = '<%= HtmlUtil.escape(toolbarSet) %>';

			fckEditor.ReplaceTextarea();

			// LEP-5707

			var ua = navigator.userAgent, isFirefox2andBelow = false;
			var agent = /(Firefox)\/(.+)/.exec(ua);

			if (agent && agent.length && (agent.length == 3)) {
				if (parseInt(agent[2]) && parseInt(agent[2]) < 3) {
					isFirefox2andBelow = true;
				}
			}

			if (isFirefox2andBelow) {
				var fckInstanceName = fckEditor.InstanceName;
				var fckIframe = document.getElementById(fckInstanceName + '___Frame');

				var interval = setInterval(
					function() {
						var iframe = fckIframe.contentDocument.getElementsByTagName('iframe');

						if (iframe.length) {
							iframe = iframe[0];

							iframe.onload = function(event) {
								clearInterval(interval);
								parent.stop();
							};
						}
					},
					500);
				}

			<%
			if (Validator.isNotNull(onChangeMethod)) {
			%>

				var clearContentChangeHandle = function(event) {
					if (event.portletId === '<%= portletId %>') {
						clearInterval(contentChangeHandle);

						Liferay.detach('destroyPortlet', clearContentChangeHandle);
					}
				};

				Liferay.on('destroyPortlet', clearContentChangeHandle);

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

			<%
			}
			%>

			window['<%= name %>'].instanceReady = true;
		},

		instanceReady: false,

		<%
		if (Validator.isNotNull(onChangeMethod)) {
		%>

			onChangeCallback: function() {
				var dirty = FCKeditorAPI.GetInstance('<%= name %>').IsDirty();

				if (dirty) {
					<%= HtmlUtil.escapeJS(onChangeMethod) %>(window['<%= name %>'].getText());

					FCKeditorAPI.GetInstance('<%= name %>').ResetIsDirty();
				}
			},

		<%
		}
		%>

		setHTML: function(value) {
			FCKeditorAPI.GetInstance('<%= name %>').SetHTML(value);
		}
	};

	window['<%= name %>'].initFckArea();
</aui:script>

<div class="<%= HtmlUtil.escapeAttribute(cssClass) %>">
	<textarea id="<%= name %>" name="<%= name %>" style="display: none;"></textarea>
</div>