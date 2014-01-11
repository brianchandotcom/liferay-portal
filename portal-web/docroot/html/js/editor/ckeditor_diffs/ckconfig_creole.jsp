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
<%@ page import="com.liferay.portal.kernel.util.ContentTypes" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<%
String attachmentURLPrefix = ParamUtil.getString(request, "attachmentURLPrefix");
String contentsLanguageId = ParamUtil.getString(request, "contentsLanguageId");
String cssClasses = ParamUtil.getString(request, "cssClasses");
String languageId = ParamUtil.getString(request, "languageId");
String name = ParamUtil.getString(request, "name");
boolean resizable = ParamUtil.getBoolean(request, "resizable");
long wikiPageResourcePrimKey = ParamUtil.getLong(request, "wikiPageResourcePrimKey");

response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
%>

;(function() {
	var ckEditor = CKEDITOR.instances['<%= HtmlUtil.escapeJS(name) %>'];

	var config = ckEditor.config;

	config.attachmentURLPrefix = '<%= HtmlUtil.escapeJS(attachmentURLPrefix) %>';

	config.bodyClass = 'html-editor <%= HtmlUtil.escapeJS(cssClasses) %>';

	config.contentsLangDirection = '<%= PortalUtil.isRightToLeft(request) ? "rtl" : "ltr" %>';

	config.contentsLanguage = '<%= HtmlUtil.escapeJS(contentsLanguageId.replace("iw_", "he_")) %>';

	config.decodeLinks = true;

	config.disableObjectResizing = true;

	config.extraPlugins = 'creole,wikilink';

	config.format_tags = 'p;h1;h2;h3;h4;h5;h6;pre';

	config.height = 265;

	config.language = '<%= HtmlUtil.escapeJS(languageId.replace("iw_", "he_")) %>';

	config.removePlugins = [
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
		'templates',
		'video'
	].join();

	<c:if test="<%= resizable %>">
		config.resize_dir = 'vertical';
	</c:if>

	config.resize_enabled = <%= resizable %>;

	config.toolbar_creole = [
		['Cut','Copy','Paste','PasteText','PasteFromWord'],
		['Undo','Redo'],
		['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
		['Format'],

		<%
		String linkButtonBar = "['Link', 'Unlink']";

		if (wikiPageResourcePrimKey > 0) {
			linkButtonBar = "['Link', 'Unlink', 'Image']";
		}
		%>

		<%= linkButtonBar %>,

		['Table', '-', 'HorizontalRule', 'SpecialChar' ],
		['Find','Replace','-','SelectAll','RemoveFormat'],
		['Source']
	];

	config.toolbar_phone = [
		['Bold', 'Italic', 'Underline'],
		['NumberedList', 'BulletedList'],
		['Image', 'Link', 'Unlink']
	];

	config.toolbar_tablet = [
		['Bold', 'Italic', 'Underline', 'Strike'],
		['NumberedList', 'BulletedList'],
		['Image', 'Link', 'Unlink'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		['Styles', 'FontSize']
	];

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
})();