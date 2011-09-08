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

<%@ include file="/html/portlet/css_init.jsp" %>

.journal-article-component-list {
	margin: 10px 0 0 0;
}

.portlet-journal {
	.add-article-selector {
		display: inline-block;
		vertical-align: top;

		.lfr-meta-actions {
			padding-top: 0;
		}
	}

	.add-permission-button-row {
		float: left;
	}

	.article-separator {
		clear: both;
	}

	.form-section h3 {
		border-bottom: 1px solid #666;
	}

	.lfr-table .journal-metadata {
		background: #EEE;
		border: 1px solid #979798;
		border-color: #979798 #FAFAFB #FAFAFB #BDBDBE;
		min-height: 1.8em;

		-moz-border-radius: 4px;
		-webkit-border-radius: 4px;
		border-radius: 4px;

		-moz-box-shadow: 1px 1px 8px #CCC inset;
		-webkit-box-shadow: 1px 1px 8px #CCC inset;
		box-shadow: 1px 1px 8px #CCC inset;
	}

	.lfr-table .article-structure-template-toolbar {
		color: #34404F;
		display: block;
		margin: 0 auto;
		padding: 0.5em;

		label {
			font-weight: bold;
		}

		.article-structure-toolbar, .article-template-toolbar {
			display: inline;
			margin-left: 0.5em;
			margin-bottom: 0;
		}

		.article-template-image {
			max-height: 50px;
			max-width: 50px;
			vertical-align: middle;
		}
	}

	.lfr-table .article-translation-toolbar {
		color: #34404F;
		display: block;
		margin: 0 auto;
		padding: 0.5em;

		label {
			font-weight: bold;
		}

		.add-translations-menu {
			display: inline;
			float: none;
			margin-left: 2em;
			padding: 0.4em 0;

			li {
				display: inline;

				strong {
					display: inline;
				}
			}
		}

		.contains-translations {
			margin-top: 1em;
		}

		.journal-article-default-translation {
			background-color: #B3DAEF;
			font-weight: bold;
			padding: 0.3em 0.5em 0.3em;
			margin: 0 0.2em;
		}

		.journal-article-translation {
			background-color: #B3DAEF;
			border: 1px solid #5FA8FF;
			color: #444;
			padding: 0.3em 0.5em 0.3em;
			margin: 0 0.2em;
			text-decoration: none;

			&:hover {
				background-color: #D1E5EF;
			}

			img {
				margin-right: 0.3em;
			}
		}
	}

	.lfr-table .journal-article-header-edit {
		margin: 1em 0;
		width: 95%;
	}

	.subscribe-link {
		float: right;
		margin-bottom: 1em;
		margin-top: 2em;
	}

	.lfr-panel-basic {
		.lfr-panel-title {
			border-bottom: 1px solid #ccc;
			float: none;
			position: relative;
			top: -0.5em;

			span {
				background: #fff;
				padding: 0 8px 0 4px;
				position: relative;
				top: 0.55em;
			}
		}

		.lfr-panel-content {
			background-color: #F0F5F7;
			padding: 10px;
		}

		.lfr-tag-selector-input {
			width: 100%;
		}
	}

	.journal-article-container ul {
		margin: 0;
	}

	li {
		list-style: none;
		position: relative;
	}

	.structure-tree {
		position: relative;

		li.structure-field {
			&.repeatable-border {
				background: #F7FAFB;
			}

			&.selected {
				background: #EBFFEE;
				border: 1px #C3E7CC solid;
			}

			&.repeated-field .journal-article-variable-name {
				display: none;
			}
		}

		.placeholder {
			display: none;
		}

		.aui-tree-sub-placeholder {
			margin-top: 10px;
		}

		.folder .field-container .journal-article-required-message {
			display: none;
		}

		.folder .field-container.required-field .journal-article-required-message {
			display: block;
			margin: 0;
		}

		.journal-article-field-label {
			display: block;
			font-weight: bold;
			margin-left: 3px;
			padding-bottom: 5px;
		}

		.journal-subfield input {
			float: left;
		}

		.journal-subfield .journal-article-field-label {
			float: left;
			font-weight: normal;
			padding: 0 0 0 3px;
		}

		.journal-article-move-handler {
			display: none;
		}

		.journal-article-localized {
			font-size: 13px;
			padding-top: 5px;
		}

		.structure-field .structure-field {
			border-left: 10px solid #E0E0E0;
			margin: 7px 0 0 5px;
			padding: 7px 0 0 10px;
		}
	}

	.component-group .aui-tree-placeholder {
		display: none;
	}

	.journal-article-localized-checkbox {
		display: block;
		margin-top: 10px;
	}

	 .journal-article-header-edit .journal-article-localized-checkbox {
		margin-bottom: 10px;
	}

	.journal-article-variable-name .aui-field-label {
		font-weight: normal;
		margin-right: 5px;
	}

	.component-group-title {
		font-size: 12px;
		font-weight: bold;
		text-decoration: underline;
		padding: 4px 0 0;
	}

	.journal-article-component-container {
		margin: 3px;
		overflow: hidden;
	}

	.journal-component {
		color: #0E3F6F;
		cursor: move;
		line-height: 25px;
		padding-left: 30px;

		&.dragging {
			font-weight: bold;
		}
	}

	.journal-article-instructions-container {
		display: normal;
	}

	.journal-field-template {
		display: none;
	}

	.journal-fieldmodel-container {
		display: none;
	}

	.journal-icon-button {
		cursor: pointer;
	}

	.portlet-section-header td {
		background: #CFE5FF;
	}

	.journal-form-presentation-label {
		color: #0E3F6F;
		padding-top: 3px;
	}

	.journal-edit-field-control, .journal-list-subfield .journal-icon-button {
		display: none;
	}

	.journal-icon-button span img {
		margin-bottom: 3px;
	}

	.journal-article-instructions-message {
		margin: 5px 0 0 0;
	}

	.portlet-msg-info {
		clear:both;
	}

	.structure-controls {
		margin-top: 5px;
	}

	.structure-links {
		margin-right: 0.5em;
	}

	.taglib-workflow-status {
		margin: 0 0 1em 0;
	}

	.default-link {
		font-size: 0.9em;
		font-weight: normal;
	}

	.journal-image-preview {
		border: 1px dotted;
		margin-top: 2px;
		overflow: scroll;
		padding: 4px;
		width: 500px;
	}

	.repeatable-field-image {
		cursor: pointer;
		position: absolute;
		right: 0;
		top: 0;
	}

	.repeatable-field-delete {
		display: none;
	}

	.repeated-field .repeatable-field-delete {
		display: inline;
	}

	.lfr-textarea {
		width: 350px;
	}

	.lfr-template-editor {
		.aui-field-element, .aui-field-content, .aui-field-input-text {
			display: block;
		}

		.aui-field-input-text {
			height: 400px;
		}
	}

	.localization-disabled {
		.journal-article-language-options, .structure-field .journal-article-localized-checkbox {
			display: none;
		}
	}

	.journal-article-buttons {
		display: none;
	}

	.form-navigator {
		position: relative;
		z-index: 430;

		.article-info {
			font-weight: bold;
			margin-bottom: 15px;

			.article-name {
				color: #036;
				display: block;
				font-size: 14px;
			}

			.article-image {
				float: left;
				margin-right: 10px;
				padding: 0;
				width: 35px;
			}
		}
	}

	.display-page-item-container {
		margin-bottom: 1em;
		margin-top: 1em;

		.display-page-item {
			background-color: #DEE7F8;
			border-radius: 14px;
			border: 1px solid #CAD8F3;
			padding: 5px 28px 5px 11px;
			position: relative;

			.display-page-item-remove {
				border-radius: 14px;
				cursor: pointer;
				display: block;
				height: 16px;
				overflow: hidden;
				position: absolute;
				right: 8px;
				top: 3px;
				width: 16px;

				&:hover {
					background-color: #CAD8F3;
				}
			}
		}
	}

	.display-page-toolbar {
		margin-top: 0.5em;
	}
}

.portlet-journal-edit-mode {
	.structure-tree {
		li {
			background: url(<%= themeImagesPath %>/journal/form_builder_bg.png);
			border: 1px solid #C6D9F0;
			margin: 15px;
			padding: 10px 10px 10px 22px;

			&.structure-field {
				.structure-field {
					border-left: 10px solid #C6D9F0;
				}

				&.repeated-field {
					background: #F7FAFB;
					border: 1px dashed #C6D9F0;

					&.selected {
						border: 1px dashed #C3E7CC;
					}

					.journal-edit-field-control, .journal-delete-field {
						display: none;
					}
				}

				&.yui3-dd-draggable .journal-article-move-handler {
					background: transparent url(<%= themeImagesPath %>/application/handle_sort_vertical.png) no-repeat scroll right 50%;
					cursor: move;
					display: block;
					height: 20px;
					left: 7px;
					position: absolute;
					top: 8px;
					width: 16px;
					z-index: 420;
				}

				.journal-article-close {
					background: url(<%= themeImagesPath %>/journal/form_builder_close.png);
					cursor: pointer;
					display: block;
					height: 29px;
					position: absolute;
					right: -10px;
					top: -9px;
					width: 29px;
					z-index: 420;
				}

				.journal-edit-field-control {
					display: block;
				}

				.journal-article-instructions-container {
					display: none;
				}
			}

			&.parent-structure-field {
				background: none;
				background-color: #FFFFE6;
				border: 1px dotted #FFE67F;
				padding-bottom: 30px;

				.journal-article-close, .journal-delete-field {
					display: none;
				}
			}
		}

		span.folder, span.file {
			display: block;
			padding: 10px;
			padding-top: 0;
		}

		.aui-placeholder, .aui-tree-placeholder, .aui-tree-sub-placeholder {
			-ms-filter: alpha(opacity=75);
			background: #fff;
			border: 1px #cdcdcd dashed;
			filter: alpha(opacity=75);
			height: 100px;
			opacity: 0.75;
		}

		li {
			margin: 10px;
			padding-top: 5px;
			position: relative;
		}
	}

	.repeatable-field-delete {
		display: inline;
	}

	.journal-article-buttons {
		display: block;
		height: 27px;
		margin-top: 18px;
		text-align: right;

		.edit-button, .repeatable-button {
			float: left;
			margin-left: 3px;
		}
	}

	.journal-list-subfield .journal-icon-button {
		display: inline;
	}

	.portlet-journal .structure-field .journal-article-localized-checkbox {
		display: none;
	}

	.lfr-table .journal-article-header-edit {
		width: 100%;
	}

	.taglib-form-navigator .form-section.selected {
		width: 100%;
	}

	.taglib-form-navigator .form-navigator, .lfr-table .article-translation-toolbar, .journal-article-general-fields, .article-toolbar {
		display: none;
	}
}

.display-page-dialog {
	.aui-tree-label {
		a {
			color: black;
		}

		.layout-page-invalid {
			color: #AAA;
			font-style: italic;
		}
	}

	.selected-page-message {
		margin: 5px 0;
	}
}

.ie {
	.journal-article-edit-field-wrapper form {
		width: auto;
	}

	.journal-article-helper {
		-ms-filter: alpha(opacity=80);

		&.not-intersecting .forbidden-action {
			right: 2px;
			top: 2px;
		}
	}

	.portlet-journal {
		.structure-tree li {
			zoom: 1;
		}
	}
}

.ie6, .ie7 {
	.journal-article-helper {
		filter: alpha(opacity=80);
	}
}

.journal-component {
	background: transparent url() no-repeat scroll 3px 3px;
}

.journal-component-text {
	background-image: url(<%= themeImagesPath %>/journal/text_field.png);
	background-position: 3px 9px;
}

.journal-component-textbox {
	background-image: url(<%= themeImagesPath %>/journal/textbox.png);
	background-position: 3px 6px;
}

.journal-component-textarea {
	background-image: url(<%= themeImagesPath %>/journal/textarea.png);
	background-position: 3px 4px;
}

.journal-component-image {
	background-image: url(<%= themeImagesPath %>/journal/image_uploader.png);
	background-position: 3px 7px;
}

.journal-component-documentlibrary {
	background-image: url(<%= themeImagesPath %>/journal/document_library.png);
}

.journal-component-boolean {
	background-image: url(<%= themeImagesPath %>/journal/checkbox.png);
	background-position: 3px 7px;
}

.journal-component-options {
	background-image: url(<%= themeImagesPath %>/journal/options.png);
	background-position: 3px 5px;
}

.journal-component-list {
	background-image: url(<%= themeImagesPath %>/journal/selectbox.png);
	background-position: 3px 9px;
}

.journal-component-multilist {
	background-image: url(<%= themeImagesPath %>/journal/multiselection_list.png);
	background-position: 3px 4px;
}

.journal-component-linktolayout {
	background-image: url(<%= themeImagesPath %>/journal/link_to_page.png);
	background-position: 3px 9px;
}

.journal-component-formgroup {
	background-image: url(<%= themeImagesPath %>/journal/form_group.png);
	background-position: 3px 5px;
}

.journal-component-selectionbreak {
	background-image: url(<%= themeImagesPath %>/journal/selection_break.png);
	background-position: 3px 12px;
}

.component-group {
	&.form-controls {
		border-top: 1px solid #E0ECFF;
	}

	.component-dragging {
		background-color: #fff !important;
	}
}

.journal-article-helper {
	background: #dedede;
	border: 1px #555 dashed;
	cursor: move;
	opacity: 0.8;
	position: absolute;
	visibility: hidden;
	width: 100px;

	&.aui-draggable-dragging {
		font-size: 15px;
	}

	&.not-intersecting .forbidden-action {
		background: url(<%= themeImagesPath %>/application/forbidden_action.png) no-repeat;
		height: 32px;
		position: absolute;
		right: -15px;
		top: -15px;
		width: 32px;
	}

	.journal-component {
		height: 25px;
		line-height: 25px;
		margin-left: 5px;
		padding-left: 25px;
	}
}

.journal-article-edit-field-wrapper {
	&.aui-overlaycontextpanel {
		margin: 0 13px 0 0;
		padding: 0;
		position: relative;
	}

	&.aui-overlaycontextpanel-container, .aui-overlaycontextpanel-container {
		background-color: #EBFFEE;
		border-color: #C3E7CC;
	}

	&.aui-overlaycontextpanel-arrow-tl .aui-overlaycontextpanel-pointer-inner {
		border-bottom: 10px solid #EBFFEE;
	}

	.container-close {
		display: none;
	}
}

.journal-article-edit-field {
	padding: 5px;
	position: relative;
	width: 180px;
	z-index: 420;

	.aui-field {
		padding: 0;

		.textarea {
			height: 6em;
		}
	}

	strong {
		font-size: 14px;
		text-decoration: underline;
	}

	.journal-edit-label {
		margin-top: 10px;
	}
}

.journal-article-edit-field-wrapper {
	.cancel-button, .save-button {
		display: none;
	}

	&.save-mode {
		.close-button {
			display: none;
		}

		.save-button, .cancel-button {
			display: inline;
		}
	}

	.user-instructions {
		border-width: 0;
		padding: 0;
		margin-bottom: 1em;
	}

	.button-holder {
		margin-top: 1.5em;
	}
}

.structure-message {
	margin-top: 5px;
}

.save-structure-template-dialog textarea {
	height: 150px;
	width: 450px;
}

.save-structure-name {
	width: 470px;
}

.save-structure-description {
	height: 150px;
	width: 470px;
}

.journal-template-error {
	.scroll-pane {
		border: 1px solid #BFBFBF;
		max-height: 200px;
		min-height: 50px;
		overflow: auto;
		width: 100%;

		.inner-scroll-pane {
			min-width: 104%;
		}

		.error-line {
			background: #fdd;
		}

		pre {
			margin: 0;
			white-space: pre;

			span {
				background: #B5BFC4;
				border-right: 1px solid #BFBFBF;
				display: block;
				float: left;
				margin-right: 4px;
				padding-right: 4px;
				text-align: right;
				width: 40px;
			}
		}
	}
}