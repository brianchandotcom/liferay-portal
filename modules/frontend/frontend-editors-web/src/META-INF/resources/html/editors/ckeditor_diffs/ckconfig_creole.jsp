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

<%@ include file="/html/editors/ckeditor_init.jsp" %>

<%
String attachmentURLPrefix = ParamUtil.getString(request, "attachmentURLPrefix");

String contentsLanguageId = ParamUtil.getString(request, "contentsLanguageId");

Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

String cssClasses = ParamUtil.getString(request, "cssClasses");
String cssPath = ParamUtil.getString(request, "cssPath");

String languageId = ParamUtil.getString(request, "languageId");

Locale locale = LocaleUtil.fromLanguageId(languageId);

languageId = LocaleUtil.toLanguageId(locale);

String name = ParamUtil.getString(request, "name");
boolean resizable = ParamUtil.getBoolean(request, "resizable");
boolean showSource = ParamUtil.getBoolean(request, "showSource");
long wikiPageResourcePrimKey = ParamUtil.getLong(request, "wikiPageResourcePrimKey");

response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
%>

;window['<%= HtmlUtil.escapeJS(name) %>Config'] = function() {
	var ckEditor = CKEDITOR.instances['<%= HtmlUtil.escapeJS(name) %>'];

	var config = ckEditor.config;

	config.allowedContent = 'b strong i hr h1 h2 h3 h4 h5 h6 em ul ol li pre table tr th; img a[*]';

	config.attachmentURLPrefix = '<%= HtmlUtil.escapeJS(attachmentURLPrefix) %>';

	config.bodyClass = 'html-editor <%= HtmlUtil.escapeJS(cssClasses) %>';

	config.contentsCss = ['<%= HtmlUtil.escapeJS(cssPath) %>/aui.css', '<%= HtmlUtil.escapeJS(cssPath) %>/main.css'];

	<%
	String contentsLanguageDir = LanguageUtil.get(contentsLocale, "lang.dir");
	%>

	config.contentsLangDirection = '<%= HtmlUtil.escapeJS(contentsLanguageDir) %>';

	config.contentsLanguage = '<%= contentsLanguageId.replace("iw_", "he_") %>';

	config.decodeLinks = true;

	config.disableObjectResizing = true;

	config.extraPlugins = 'a11yhelpbtn,creole,imageselector,lfrpopup,wikilink';

	config.filebrowserWindowFeatures = 'title=<%= LanguageUtil.get(request, "browse") %>';

	config.format_tags = 'p;h1;h2;h3;h4;h5;h6;pre';

	config.height = 265;

	config.language = '<%= languageId.replace("iw_", "he_") %>';

	config.removePlugins = [
		'bidi',
		'colorbutton',
		'colordialog',
		'div',
		'elementspath',
		'flash',
		'font',
		'forms',
		'indentblock',
		'justify',
		'keystrokes',
		'link',
		'maximize',
		'newpage',
		'pagebreak',
		'preview',
		'print',
		'save',
		'showblocks',
		'smiley',
		'stylescombo',
		'templates',
		'video'
	].join();

	<c:if test="<%= resizable %>">
		config.resize_dir = 'vertical';
	</c:if>

	config.resize_enabled = <%= resizable %>;

	config.toolbar_creole = [
		['Bold', 'Italic', '-' ,'RemoveFormat'],
		['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
		['Format'],
		['Link', 'Unlink'],
		['Table', '-', <c:if test="<%= (wikiPageResourcePrimKey > 0) %>">'ImageSelector', '-', </c:if> 'HorizontalRule', '-', 'SpecialChar' ],
		'/',
		['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', '-', 'SelectAll', '-', 'Undo', 'Redo'],
		['Find','Replace'],

		<c:if test="<%= showSource %>">
			['Source'],
		</c:if>

		['A11YBtn']
	];

	config.toolbar_phone = [
		['Bold', 'Italic'],
		['NumberedList', 'BulletedList'],
		['Link', 'Unlink']
	];

	<c:if test="<%= (wikiPageResourcePrimKey > 0) %>">
		config.toolbar_phone.push(['Image']);
	</c:if>

	<c:if test="<%= showSource %>">
		config.toolbar_phone.push(['Source']);
	</c:if>

	config.toolbar_tablet = [
		['Bold', 'Italic'],
		['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
		['Format'],
		['Link', 'Unlink']
	];

	<c:if test="<%= (wikiPageResourcePrimKey > 0) %>">
		config.toolbar_tablet.push(['Image']);
	</c:if>

	<c:if test="<%= showSource %>">
		config.toolbar_tablet.push(['Source']);
	</c:if>

	ckEditor.on(
		'dialogDefinition',
		function(event) {
			var dialogName = event.data.name;

			var dialogDefinition = event.data.definition;

			var infoTab;

			if (dialogName === 'cellProperties') {
				infoTab = dialogDefinition.getContents('info');

				infoTab.remove('bgColor');
				infoTab.remove('bgColorChoose');
				infoTab.remove('borderColor');
				infoTab.remove('borderColorChoose');
				infoTab.remove('colSpan');
				infoTab.remove('hAlign');
				infoTab.remove('height');
				infoTab.remove('htmlHeightType');
				infoTab.remove('rowSpan');
				infoTab.remove('vAlign');
				infoTab.remove('width');
				infoTab.remove('widthType');
				infoTab.remove('wordWrap');

				dialogDefinition.minHeight = 40;
				dialogDefinition.minWidth = 210;
			}
			else if (dialogName === 'table' || dialogName === 'tableProperties') {
				infoTab = dialogDefinition.getContents('info');

				infoTab.remove('cmbAlign');
				infoTab.remove('cmbWidthType');
				infoTab.remove('cmbWidthType');
				infoTab.remove('htmlHeightType');
				infoTab.remove('txtBorder');
				infoTab.remove('txtCellPad');
				infoTab.remove('txtCellSpace');
				infoTab.remove('txtHeight');
				infoTab.remove('txtSummary');
				infoTab.remove('txtWidth');

				dialogDefinition.minHeight = 180;
				dialogDefinition.minWidth = 210;
			}
		}
	);

	<%@ include file="/html/editors/ckeditor/ckconfig_creole-ext.jsp" %>
};

window['<%= HtmlUtil.escapeJS(name) %>Config']();