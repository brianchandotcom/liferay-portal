<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String langType = ParamUtil.getString(request, "langType");

String editorContentInputElement = ParamUtil.getString(request, "editorContentInputElement");
String editorContentOutputElement = ParamUtil.getString(request, "editorContentOutputElement");

String editorType = ParamUtil.getString(request, "editorType");

if (Validator.isNotNull(editorType)) {
	portalPreferences.setValue(PortletKeys.JOURNAL, "editor-type", editorType);
}
else {
	editorType = portalPreferences.getValue(PortletKeys.JOURNAL, "editor-type", "plain");
}

boolean useRichEditor = editorType.equals("rich");

String editorMode = "php";

if (langType.equals("css")) {
	editorMode = "css";
}
else if (langType.equals("xml") || langType.equals("xsl") || langType.equals("xsd")) {
	editorMode = "xml";
}
%>

<style type="text/css">
	.aui-ace-editor {
		position: relative;
		border: 1px solid #A1A2A4;
		border-radius: 4px;
		box-shadow: 0 1px 0 white, 0 1px 2px #CCC inset;
	}

	.ace_gutter {
		border-top-left-radius: 4px;
		border-bottom-left-radius: 4px;
	}

	.lfr-editor-textarea {
		width: 100%;
		padding: 0;
	}

	#plain-editor textarea,
	#rich-editor.ace_editor {
		font-family: 'Monaco', 'Menlo', 'Droid Sans Mono', 'Courier New', monospace;
		font-size: 12px;
	}
</style>

<aui:form method="post" name="editorForm">
	<aui:fieldset>
		<aui:select name="editorType">
			<aui:option label="plain" value="plain" />
			<aui:option label="rich" selected="<%= useRichEditor %>" value="rich" />
		</aui:select>

		<div id="plain-editor" style="display: <%= useRichEditor ? "none" : "block" %>">
			<aui:input cssClass="lfr-template-editor" inputCssClass="lfr-editor-textarea" label="" name="plain-editor-input" onKeyDown="Liferay.Util.checkTab(this); Liferay.Util.disableEsc();" type="textarea" wrap="off" value='' />
		</div>
		<div id="rich-editor" style="display: <%= useRichEditor ? "block" : "none" %>"></div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button name="update-button" value="update" />
		<aui:button type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
AUI().use('aui-dialog', 'aui-io-request', 'aui-ace-editor-base', 'aui-ace-editor-mode-<%= editorMode %>', function(A) {
	// Multiple functions need access to these variables
	var editorType = '<%= editorType %>';
	var editorContentOutputElement;
	var richEditor;

	A.ready(init);

	function init() {
		richEditor = new A.AceEditor({
			boundingBox: '#rich-editor',
			width: '100%',
			height: '400',
			mode: '<%= editorMode %>'
		}).render();

		var openerAUI = Liferay.Util.getOpener().AUI();

		editorContentOutputElement = openerAUI.one('<%= editorContentOutputElement %>');
		var editorContentInputElement = openerAUI.one('<%= editorContentInputElement %>');

		if (editorContentInputElement) {
			setEditorContentForType(editorType, decodeURIComponent(editorContentInputElement.val()));
		}

		// Register event handlers
		A.one('#<portlet:namespace />editorType').on('change', updateEditorType);
		A.one('#<portlet:namespace />update-button').on('click', updateTemplateXsl);
	}

	function getEditorContentForType(type) {
		if (type == 'plain') {
			return A.one('#<portlet:namespace />plain-editor-input').get('value');
		} else {
			return richEditor.getSession().getValue();
		}
	}

	function setEditorContentForType(type, content) {
		if (type == 'plain') {
			return A.one('#<portlet:namespace />plain-editor-input').set('value', content);
		} else {
			return richEditor.getSession().setValue(content);
		}
	}

	function updateEditorType(event) {
		var oldEditorType = editorType;
		var newEditorType = A.one('#<portlet:namespace />editorType').get('value');

		var oldEditorContent = getEditorContentForType(oldEditorType);

		setEditorContentForType(newEditorType, oldEditorContent);

		// Make old editor invisible
		if (oldEditorType == 'plain') {
			A.one('#plain-editor').setStyle('display', 'none');
		} else {
			A.one('#rich-editor').setStyle('display', 'none');
		}

		// Make new editor visible
		if (newEditorType == 'plain') {
			A.one('#plain-editor').setStyle('display', 'block');
		} else {
			A.one('#rich-editor').setStyle('display', 'block');

			// If Ace editor is initialized while it is set to "display: none",
			// the size will be wrong, so we resize it when we re-display it
			richEditor.editor.resize();
		}

		persistEditorType(newEditorType);

		editorType = newEditorType;
	}

	function persistEditorType(editorType) {
		var uri = '<portlet:renderURL><portlet:param name="struts_action" value="/journal/edit_template_xsl" /></portlet:renderURL>&editorType=' + editorType;
		var request = A.io.request(uri).start();
	}

	function updateTemplateXsl() {
		var content = getEditorContentForType(editorType);

		editorContentOutputElement.val(encodeURIComponent(content));

		var dialog = Liferay.Util.getWindow();

		if (dialog) {
			dialog.close();
			dialog.fire('update');
		}
	}
});
</aui:script>
