<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.LocaleUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>

<%@ page import="java.util.Locale" %>

<%
String contentsLanguageId = ParamUtil.getString(request, "contentsLanguageId");
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
String languageId = ParamUtil.getString(request, "languageId");
long wikiPageResourcePrimKey = ParamUtil.getLong(request, "wikiPageResourcePrimKey");
String attachmentURLPrefix = ParamUtil.getString(request, "attachmentURLPrefix");
boolean resizable = ParamUtil.getBoolean(request, "resizable");

String linkButtonBar = "['Link', 'Unlink']";

if (wikiPageResourcePrimKey > 0) {
	linkButtonBar = "['Link', 'Unlink', 'Image']";
}

Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);
String contentsLanguageDir = LanguageUtil.get(contentsLocale, "lang.dir");

// LPS-35567

String ckEditorLanguageId = languageId.replace("iw_", "he_");
String ckEditorContentsLanguageId = contentsLanguageId.replace("iw_", "he_");
%>

CKEDITOR.config.attachmentURLPrefix = '<%= HtmlUtil.escapeJS(attachmentURLPrefix) %>';

CKEDITOR.config.bodyClass = 'html-editor <%= HtmlUtil.escapeJS(cssClasses) %>';

CKEDITOR.config.contentsLangDirection = '<%= HtmlUtil.escapeJS(contentsLanguageDir) %>';

CKEDITOR.config.contentsLanguage = '<%= HtmlUtil.escapeJS(ckEditorContentsLanguageId) %>';

CKEDITOR.config.decodeLinks = true;

CKEDITOR.config.disableObjectResizing = true;

CKEDITOR.config.extraPlugins = 'creole,wikilink';

CKEDITOR.config.format_tags = 'p;h1;h2;h3;h4;h5;h6;pre';

CKEDITOR.config.height = 265;

CKEDITOR.config.language = '<%= HtmlUtil.escapeJS(ckEditorLanguageId) %>';

CKEDITOR.config.removePlugins = [
	'elementspath',
	'save',
	'font',
	'bidi',
	'colordialog',
	'colorbutton',
	'div',
	'flash',
	'font',
	'forms',
	'justify',
	'keystrokes',
	'link',
	'maximize',
	'newpage',
	'pagebreak',
	'preview',
	'print',
	'save',
	'smiley',
	'showblocks',
	'stylescombo',
	'templates'
].join();

<c:if test="<%= resizable %>">
	CKEDITOR.config.resize_dir = 'vertical';
</c:if>

CKEDITOR.config.resize_enabled = <%= resizable %>;

CKEDITOR.config.toolbar_creole = [
	['Cut','Copy','Paste','PasteText','PasteFromWord'],
	['Undo','Redo'],
	['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
	['Format'],
	<%= linkButtonBar %>,
	['Table', '-', 'HorizontalRule', 'SpecialChar' ],
	['Find','Replace','-','SelectAll','RemoveFormat'],
	['Source']
];

CKEDITOR.on(
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