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

.portlet-document-library, .portlet-document-library-display {
	.file-entry-list-description {
		font-style: italic;
		margin-left: 10px;
	}

	.file-entry-tags {
		margin-top: 5px;
	}

	.edit-file-entry-type {
		h3 {
			margin: 1em 0 0.3em 0;
		}

		.select-metadata-set {
			display: block;
			margin-bottom: 2em;
		}
	}

	.lfr-asset-panels .lfr-panel.lfr-extended {
		border-bottom: none;
	}

	.document-container .search-info {
		background-color: #D3E8F1;
		min-height: 40px;
		position: relative;

		.keywords {
			float: none;
			font-size: 1.4em;
			font-weight: bold;
			position: absolute;
			top: 7px;
		}

		.change-search-folder {
			position: absolute;
			right: 60px;
			top: 7px;
		}

		.close-search {
			position: absolute;
			right: 20px;
			top: 10px;
		}
	}

	.move-list {
		.move-file, .move-folder {
			background: #F0FAF0 url() no-repeat 5px 50%;
			border-bottom: 1px solid #CCC;
			display: block;
			font-weight: bold;
			margin-bottom: 1px;
			padding: 5px;
			padding-left: 25px;
			position: relative;
		}

		.move-file {
			background-image: url(<%= themeImagesPath %>/file_system/small/jpg.png);
		}

		.move-folder {
			background-image: url(<%= themeImagesPath %>/common/folder.png);
		}

		.move-error {
			background-color: #FDD;
			background-image: url(<%= themeImagesPath %>/messages/error.png);
			font-weight: normal;
			opacity: 0.6;

			.error-message {
				position: absolute;
				right: 5px;
			}
		}

		.lfr-component {
			margin: 0;
		}
	}

	.move-list-info {
		margin: 5px 0;

		h4 {
			font-size: 1.3em;
		}
	}

	.document-container {
		overflow: auto;
	}

	.document-container, .document-entries-paginator {
		clear: both;
	}

	.select-documents {
		border-color: #5F5F5F;
		float: left;
		line-height: 0;
		margin-right: 2px;
		padding: 0;

		.aui-field-element {
			padding: 5px 4px 4px;
			border: 1px solid #FFF;
			border-color: #F0F1F2 #B2B2B2 #949494 #F0F1F1;

			.aui-field-input-choice {
				margin: 0;
			}
		}
	}

	.folder {
		margin-top: 2px;
		position: relative;

		&:hover {
			background-color: #D3E8F1;
		}

		.active-area, .active-area.hover {
			background-color: #5AD300;
		}
	}

	.expand-folder {
		float: right;
	}

	.folder-search {
		float: right;
		margin: 0 0 0.5em 0.5em;
	}

	img.locked-icon, img.shortcut-icon {
		bottom: 12px;
		position: absolute;
		right: 0;
		z-index: 10;
	}

	.lfr-menu-list ul {
		background: #F0F0F0;
		position: relative;

		&:after {
			border: 10px solid transparent;
			border-bottom-color: #F0F0F0;
			content: '';
			position: absolute;
			right: 5px;
			top: -10px;
		}
	}

	.lfr-actions {
		&.lfr-extended .lfr-trigger {
			min-width: auto;

			strong {
				min-width: 10px;
			}
		}

		&.show-arrow.direction-down .lfr-trigger strong a {
			background: url(<%= themeImagesPath %>/arrows/08_down.png) 100% -5px no-repeat;
		}
	}

	.document-display-style {
		a {
			color: #333;
		}

		.document-link {
			display: block;
			text-align: center;
			text-decoration: none;
		}

		.document-thumbnail {
			img {
				background: #EAEAEA;
				border: 1px solid #CCC;
				margin: 0;
				padding: 5px;
			}
		}

		&:hover .document-thumbnail img {
			background-color: #B0D2E1;
			border-color: #7ABFDD;
		}

		&.selected .document-thumbnail img {
			background-color: #B0D2E1;
			border-color: #057CB0;
		}

		&.descriptive {
			display: block;
			margin: 5px;
			padding: 5px 0;
			padding-left: 20px;
			position: relative;
			text-align: left;

			&:after {
				clear: both;
				content: ".";
				display: block;
				height: 0;
				visibility: hidden;
			}

			.entry-title {
				display: block;
				font-size: 1.15em;
				font-weight: bold;
				padding: 5px 0 0;
			}

			.document-description {
				display: block;
				height: 100px;
				overflow: hidden;
			}

			.document-action {
				height: 20px;
				overflow: hidden;
				position: absolute;
				right: 6px;
				top: 10px;
			}

			.document-selector {
				left: 5px;
				position: absolute;
				top: 10px;
			}

			.document-thumbnail {
				float: left;
				margin: 5px 10px 5px 5px;
				position: relative;
				text-align: center;
			}

			.document-link {
				display: block;
				text-align: left;
				text-decoration: none;
			}

			&:hover .document-selector, &.selected .document-selector {
				clip: rect(auto auto auto auto);
				position: absolute;
			}

			&.selected, .selected:hover {
				background-color: #00A2EA;
			}
		}

		&.icon {
			display: inline-block;
			float: left;
			margin: 5px;
			padding: 10px 0;
			position: relative;
			vertical-align: top;
			width: 200px;
			height: 160px;

			.document-action {
				overflow: hidden;
				position: absolute;
				right: 5px;
			}

			.document-selector {
				left: 10px;
				position: absolute;
				top: 10px;
			}

			.document-thumbnail {
				text-align: center;
				position: relative;
			}

			.entry-title {
				clear: both;
				display: block;
				padding: 0 10px;
				word-wrap: break-word;
			}

			&.selected, &.selected:hover {
				background-color: #00A2EA;
			}

			.document-selector, &:hover .document-selector, &.selected .document-selector {
				position: absolute;
			}
		}

		.document-action .direction-down {
			height: 20px;
		}

		&.selected a {
			color: #FFF;
		}

		&.active-area, &.active-area.hover {
			background-color: #5AD300;
		}

		.overlay.document-action a {
			display: block;
			float: right;
			min-height: 15px;
		}

		&:hover, &.hover {
			background-color: #D3E8F1;
		}

		&:hover .overlay, &.hover .overlay, &.selected .document-selector {
			clip: rect(auto auto auto auto);
		}
	}

	.folder-display-style li {
		.overlay.document-action {
			display: block;
			overflow: hidden;
			position: absolute;
			right: 15px;
			top: 5px;
			width: 25px;

			a {
				display: block;
				float: right;
				min-height: 15px;
			}
		}

		&:hover .overlay, &.hover .overlay {
			clip: rect(auto auto auto auto);
		}
	}

	.body-row li.selected {
		background-color: #00A2EA;

		a {
			color: #FFF;
		}
	}

	.taglib-search-iterator .results-header input {
		display: none;
	}

	.document-display-style, .lfr-search-container, .folder-display-style li {
		.overlay {
			clip: rect(0 0 0 0);
		}
	}

	.aui-button-holder.toolbar {
		display: inline;
		margin: 0;
	}

	.body-row {
		height: 100%;
		overflow: hidden;
		position: relative;
		width: 100%;

		ul li a .icon {
			float: left;
			margin-right: 5px;
		}
	}

	.lfr-header-row {
		.display-style .aui-icon {
			background-image: url(<%= themeImagesPath %>/document_library/layouts.png);
			background-repeat: no-repeat;
		}

		.aui-icon-display-list {
			background-position: 0 100%;
		}

		.aui-icon-display-icon {
			background-position: -16px 0;
		}

		.aui-icon-display-descriptive {
			background-position: -32px 0;
		}

		.aui-state-active {
			.aui-icon-display-icon {
				background-position: -16px 100%;
			}

			.aui-icon-display-descriptive {
				background-position: -32px 100%;
			}
		}

		.lfr-asset-summary {
			margin-left: 5px;

			.download-document, .conversions, .lfr-asset-author, .webdav-url {
				display: block;
				margin: 0.7em 0;
			}

			.download-document {
				margin-top: 1.2em;
			}

			.conversions {
				margin-bottom: 1.2em;
			}

			.version {
				display: block;
				font-size: 1.4em;
				font-weight: bold;
			}
		}

		.aui-icon-delete, .aui-icon-download, .aui-icon-edit, .aui-icon-lock, .aui-icon-move, .aui-icon-permissions, .aui-icon-undo, .aui-icon-unlock {
			background: url() no-repeat 0 0;
		}

		.aui-icon-delete {
			background-image: url(<%= themeImagesPath %>/common/delete.png);
		}

		.aui-icon-download {
			background-image: url(<%= themeImagesPath %>/common/download.png);
		}

		.aui-icon-edit {
			background-image: url(<%= themeImagesPath %>/common/edit.png);
		}

		.aui-icon-lock {
			background-image: url(<%= themeImagesPath %>/common/lock.png);
		}

		.aui-icon-move {
			background-image: url(<%= themeImagesPath %>/common/submit.png);
		}

		.aui-icon-permissions {
			background-image: url(<%= themeImagesPath %>/common/permissions.png);
		}

		.aui-icon-undo {
			background-image: url(<%= themeImagesPath %>/common/undo.png);
		}

		.aui-icon-unlock {
			background-image: url(<%= themeImagesPath %>/common/unlock.png);
		}
	}

	.lfr-header-row-content {
		.toolbar, .add-button {
			float: left;
		}

		.display-style {
			float: right;
		}

		.edit-toolbar {
			margin: 0;
		}

		.parent-folder-title {
			color: #FFF;
			font-weight: bold;
			padding: 6px 10px 6px 25px;
			text-shadow: -1px -1px #505050;
		}
	}

	.lfr-app-column-view {
		.portlet-msg-info, .portlet-msg-success {
			border-width: 0 0 1px;
			margin: 0 auto;
		}

		.lfr-app-column-view-content {
			background-color: #FAFAFA;
		}
	}

	.context-pane-content {
		position: relative;
	}

	.taglib-search-iterator-page-iterator-top, .taglib-search-iterator-page-iterator-bottom, .aui-paginator-container, .document-library-breadcrumb {
		background: #D9D9D9 url(<%= themeImagesPath %>/application/subheader_bg.png) 0 0 repeat-x;
		clear: both;
		padding: 5px;
		position: relative;
	}

	.taglib-search-iterator-page-iterator-top, .taglib-search-iterator-page-iterator-bottom, .aui-paginator-container {
		text-align: center;
	}

	.taglib-search-iterator-page-iterator-top.page-iterator-bottom, .lfr-app-column-view .taglib-search-iterator-page-iterator-bottom {
		bottom: 0;
		left: 0;
		position: absolute;
		right: 0;
	}

	.taglib-webdav {
		margin-top: 3em;
	}

	.taglib-workflow-status {
		margin-bottom: 5px;
	}

	.workflow-status-pending, .workflow-status-pending a {
		color: orange;
	}

	.document-library-breadcrumb {
		background-color: #EBEBEB;
		background-image: url(<%= themeImagesPath %>/application/button_bg_selected.png);

		ul {
			margin-bottom: 0;
		}
	}

	.lfr-list-view-content {
		ul .expand-folder {
			height: 10px;
			padding-left: 5px;
			padding-right: 5px;
			position: absolute;
			right: 0;
			top: 5px;
			width: 10px;

			.expand-folder-arrow {
				left: 2px;
				position: absolute;
				top: 1px;
			}
		}

		li a {
			display: block;
			padding: 6px 8px;
		}

		ul li a {
			color: #34404F;
			text-decoration: none;
		}

		li.selected {
			background-color: #00a2ea;

			a {
				font-weight: bold;
			}
		}
	}

	.lfr-list-view-data-container {
		background-color: #FAFAFA;
		height: 100%;
		position: absolute;
		width: 100%;
		z-index: 9999;
	}

	.document-info {
		background: #D2DDE7;
		border-bottom: 1px solid #6D6D6E;
		padding: 10px;

		&:after {
			clear: both;
			content: "";
			display:block;
			height: 0;
		}

		.document-title {
			margin: 0 0 10px;
			text-shadow: 1px 1px #FFF;
		}

		.user-date, .taglib-asset-tags-summary {
			font-weight: bold;
		}

		.document-description {
			margin: 8px 0;
			display: block;
		}

		.document-thumbnail {
			float: left;
			margin: 0 10px 0 0;
		}

		.clear {
			clear: both;
		}
	}

	.asset-details {
		h3.version {
			margin: 0 0 10px;
		}

		.taglib-custom-attributes-list {
			margin-bottom: 0;
		}

		.asset-details-content {
			padding: 10px;

			.taglib-workflow-status {
				margin: 0;
			}

			.lfr-asset-icon {
				background-position: 0 2px;
				background-repeat: no-repeat;
				border-right-width: 0;
				float: none;
				padding: 2px 0 2px 20px;
			}

			.lfr-asset-author {
				background-image: url(<%= themeImagesPath %>/portlet/edit_defaults.png);
			}

			.lfr-asset-downloads {
				background-image: url(<%= themeImagesPath %>/common/download.png);
			}

			.lfr-asset-ratings {
				margin: 0 0 1em;
			}
		}
	}

	.taglib-discussion {
		width: 90%;
		margin: 0 auto;
	}

	.lfr-document-library-comments.lfr-panel.lfr-extended {
		background: #FFF;
		border-width: 0;
		border-right: 1px solid #CCC;
		margin-bottom: 0;

		.lfr-panel-titlebar {
			margin-top: 0;
		}
	}

	.version.document-locked {
		background: url(<%= themeImagesPath %>/common/lock.png) no-repeat 0 50%;
		padding-left: 20px;
	}

	.common-file-metadata-container {
		padding: 1em;

		.aui-field-element {
			display: block;

			textarea {
				width: 100% !important;
			}
		}

		&.selected {
			background-color: #D3E8F1;
		}

		.selected-files-count {
			border-bottom: 1px solid #C8C9CA;
			color: #555;
			font-size: 1.8em;
			font-weight: bold;
			margin-bottom: 10px;
			overflow: hidden;
		}

		.document-type .lfr-panel-content {
			min-height: 35px;
		}

		.document-type-fields {
			clear: left;
			padding-top: 0.5em;
		}

		.categorization-panel {
			clear: left;
		}
	}

	.taglib-ratings.stars {
		border: 1px solid transparent;
		display: inline-block;
		margin: 0 3px;
		padding: 4px;
		position: relative;
		vertical-align: middle;

		.liferay-rating-vote {
			display: none;
			left: -1px;
			padding: 4px;
			position: absolute;
			right: -1px;
			top: -1px;
		}

		&:hover, &:hover .liferay-rating-vote {
			background: #B0D3F6;
			border: 1px solid #3F6F9F;
		}

		&:hover .liferay-rating-vote {
			display: block;
		}

		.aui-rating-content, .aui-rating-label-element {
			display: inline;
		}

		.aui-rating-label-element {
			color: #555;
			font-size: 0.9em;
			margin-left: 0.5em;
		}

		.liferay-rating-score {
			padding-left: 0;
		}
	}
}

.portlet-document-library {
	.lfr-asset-column-details .lfr-panel {
		margin-bottom: 0.5em;
		padding-bottom: 0;

		&.lfr-collapsed {
			margin-bottom: 0;

			.lfr-panel-titlebar {
				border-bottom-width: 0;
			}
		}
	}
}

.active-area-proxy {
	background: #FFFFE0 url(<%= themeImagesPath %>/portlet/pop_up.png) no-repeat 10px 50%;
	font-size: 1.2em;
	padding: 0.3em 0.3em 0.3em 2em;
}

.ie6, .ie7 {
	.portlet-document-library, .portlet-document-library-display {
		.document-display-style.descriptive {
			zoom: 1;
		}
	}
}