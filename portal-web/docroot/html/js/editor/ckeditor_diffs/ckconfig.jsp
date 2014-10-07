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

<%@ include file="/html/js/editor/ckeditor_init.jsp" %>

<%
String contentsLanguageId = ParamUtil.getString(request, "contentsLanguageId");

Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

String colorSchemeCssClass = ParamUtil.getString(request, "colorSchemeCssClass");
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
boolean inlineEdit = ParamUtil.getBoolean(request, "inlineEdit");

String languageId = ParamUtil.getString(request, "languageId");

Locale locale = LocaleUtil.fromLanguageId(languageId);

languageId = LocaleUtil.toLanguageId(locale);

String name = ParamUtil.getString(request, "name");
boolean resizable = ParamUtil.getBoolean(request, "resizable");

response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
%>

;window['<%= HtmlUtil.escapeJS(name) %>Config'] = function() {
	var ckEditor = CKEDITOR.instances['<%= HtmlUtil.escapeJS(name) %>'];

	if (!CKEDITOR.stylesSet.get('liferayStyles')) {
		CKEDITOR.addStylesSet(
			'liferayStyles',
			[

			// Block Styles

			{name: 'Normal', element: 'p'},
			{name: 'Heading 1', element: 'h1'},
			{name: 'Heading 2', element: 'h2'},
			{name: 'Heading 3', element: 'h3'},
			{name: 'Heading 4', element: 'h4'},

			// Special classes

			{name: 'Preformatted Text', element:'pre'},
			{name: 'Cited Work', element:'cite'},
			{name: 'Computer Code', element:'code'},

			// Custom styles

			{name: 'Info Message', element: 'div', attributes: {'class': 'portlet-msg-info'}},
			{name: 'Alert Message', element: 'div', attributes: {'class': 'portlet-msg-alert'}},
			{name: 'Error Message', element: 'div', attributes: {'class': 'portlet-msg-error'}}
			]
		);
	}

	var config = ckEditor.config;

	config.allowedContent = true;

	config.autoParagraph = false;

	config.autoSaveTimeout = 3000;

	config.bodyClass = 'html-editor <%= HtmlUtil.escapeJS(colorSchemeCssClass) %> <%= HtmlUtil.escapeJS(cssClasses) %>';

	config.closeNoticeTimeout = 8000;

	config.contentsCss = ['<%= HtmlUtil.escapeJS(cssPath) %>/aui.css', '<%= HtmlUtil.escapeJS(cssPath) %>/main.css'];

	<%
	String contentsLanguageDir = LanguageUtil.get(contentsLocale, "lang.dir");
	%>

	config.contentsLangDirection = '<%= HtmlUtil.escapeJS(contentsLanguageDir) %>';

	config.contentsLanguage = '<%= contentsLanguageId.replace("iw_", "he_") %>';

	config.entities = false;

	config.extraPlugins = 'a11yhelpbtn,lfrpopup,media,scayt,wsc';

	<c:if test="<%= inlineEdit %>">
		config.extraPlugins += ',ajaxsave,restore';
	</c:if>

	config.filebrowserWindowFeatures = 'title=<%= LanguageUtil.get(locale, "browse") %>';

	config.height = 265;

	config.language = '<%= languageId.replace("iw_", "he_") %>';

	config.pasteFromWordRemoveFontStyles = false;

	config.pasteFromWordRemoveStyles = false;

	config.resize_enabled = <%= resizable %>;

	<c:if test="<%= resizable %>">
		config.resize_dir = 'vertical';
	</c:if>

	config.stylesCombo_stylesSet = 'liferayStyles';

	config.toolbar_editInPlace = [
		['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript', '-', 'RemoveFormat'],
		['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
		'/',
		['Styles'],
		['SpellChecker', 'Scayt', '-', 'SpecialChar'],
		['Undo', 'Redo'],
		['Source'],
		['A11YBtn']
	];

	config.toolbar_email = [
		['Bold', 'Italic', 'Underline', 'Strike', '-', 'RemoveFormat'],
		['TextColor', 'BGColor'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		['FontSize'],
		['Link', 'Unlink'],
		['Image'],
		'/',
		['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', '-', 'SelectAll', '-', 'Undo', 'Redo' ],
		['SpellChecker', 'Scayt'],
		['Source'],
		['A11YBtn']
	];

	config.toolbar_liferay = [
		['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript', '-', 'RemoveFormat'],
		['TextColor', 'BGColor'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
		'/',
		['Styles', 'FontSize'],
		['Link', 'Unlink', 'Anchor'],

		['Table', '-', 'Image', <c:if test="<%= XugglerUtil.isEnabled() %>"> 'Audio', 'Video',</c:if> 'Flash', '-', 'Smiley', 'SpecialChar'],
		'/',

		<c:if test="<%= inlineEdit %>">
			['AjaxSave', '-', 'Restore'],
		</c:if>

		['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', '-', 'SelectAll' , '-', 'Undo', 'Redo'],
		['Find', 'Replace', '-', 'SpellChecker', 'Scayt'],

		<c:if test="<%= !inlineEdit %>">
			['Source'],
		</c:if>

		['A11YBtn']
	];

	config.toolbar_liferayArticle = [
		['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript', '-', 'RemoveFormat'],
		['TextColor', 'BGColor'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		['NumberedList', 'BulletedList', '-' ,'Outdent', 'Indent', '-', 'Blockquote'],
		'/',
		['Styles', 'FontSize'],
		['Link', 'Unlink', 'Anchor'],
		['Table', '-', 'Image', <c:if test="<%= XugglerUtil.isEnabled() %>">'Audio', 'Video',</c:if>, 'Flash', '-', 'LiferayPageBreak', '-', 'Smiley', 'SpecialChar'],
		'/',
		['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', '-', 'SelectAll', '-', 'Undo', 'Redo'],
		['Find', 'Replace', '-', 'SpellChecker', 'Scayt'],
		['Source'],
		['A11YBtn']
	];

	config.toolbar_phone = [
		['Bold', 'Italic', 'Underline'],
		['NumberedList', 'BulletedList'],
		['Link', 'Unlink'],
		['Image'],
		['Source']
	];

	config.toolbar_simple = [
		['Bold', 'Italic', 'Underline', 'Strike'],
		['NumberedList', 'BulletedList'],
		['Link', 'Unlink'],
		['Table', 'Image'],
		['Source']
	];

	config.toolbar_tablet = [
		['Bold', 'Italic', 'Underline', 'Strike'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		['NumberedList', 'BulletedList'],
		['Styles', 'FontSize'],
		['Link', 'Unlink'],
		['Image'],
		['Source']
	];

	ckEditor.on(
		'dialogDefinition',
		function(event) {
			var dialogDefinition = event.data.definition;

			var onShow = dialogDefinition.onShow;

			dialogDefinition.onShow = function() {
				if (typeof onShow === 'function') {
					onShow.apply(this, arguments);
				}

				if (window.top != window.self) {
					var editorElement = this.getParentEditor().container;

					var documentPosition = editorElement.getDocumentPosition();

					var dialogSize = this.getSize();

					var x = documentPosition.x + ((editorElement.getSize('width', true) - dialogSize.width) / 2);
					var y = documentPosition.y + ((editorElement.getSize('height', true) - dialogSize.height) / 2);

					this.move(x, y, false);
				}
			}
		}
	);

	<%@ include file="/html/js/editor/ckeditor/ckconfig-ext.jsp" %>
};

window['<%= HtmlUtil.escapeJS(name) %>Config']();