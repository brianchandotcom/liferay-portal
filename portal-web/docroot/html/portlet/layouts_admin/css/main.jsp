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

.portlet-communities, .portlet-users-admin {
	.lfr-theme-list {
		h3 {
			background: #D3DADD;
			padding: 0.5em;
		}

		li {
			float: left;
			margin: 0 1.3em 1.3em 0;
			text-align: center;
		}
	}

	.layout-set-results {
		min-height: 300px;
	}

	.theme-title {
		display: block;
		font-weight: bold;
		margin: 0;
		padding: 2px;

		.aui-field-content {
			margin: 0;
		}
	}

	.lfr-current-theme {
		background: #F0F5F7;
		border: 1px solid #828F95;
		margin-bottom: 1em;
		padding: 3px 3px 1em;

		h3 {
			margin: 0 0 0.5em;
		}

		.selected-theme {
			border-bottom: 1px solid #CCC;
			font-size: 1.5em;

			.aui-field-content {
				display: inline-block;
			}
		}


		.theme-details {
			padding: 0 2px 0 170px;
		}

		.theme-fields {
			margin-left: 0.5em;
		}

		.theme-screenshot {
			border: 1px solid #CCC;
			float: left;
			height: 120px;
			margin: 0 0.5em;
			width: 150px;
		}
	}

	.lfr-page-layouts {
		.lfr-layout-template {
			height: 150px;
			margin: 0 0 1em;
			text-align: center;

			label {
				width: 65px;
			}

			img {
				margin: 4px;

				&.layout-selected {
					background-color: #99FF33;
					border: 4px solid #99FF33;
					margin: 0px;
				}
			}

			.aui-field-content {
				margin-top: 0;
			}

			.aui-field-element {
				float: left;
			}

			.layout-template-entry {
				cursor: pointer;
				width: 100px;
			}
		}

		.lfr-layout-template-column-content {
			padding: 0 2em 0 0;
		}

		.lfr-page-layouts-content {
			padding-left: 1em;
		}
	}

	.theme-entry {
		cursor: pointer;
		float: left;
		height: 96px;
		margin: 2px;
		padding: 0.3em;
		text-align: center;
		text-decoration: none;

		&:hover label, &:hover {

		}

		&:hover {
			&, & label {
				color: #369;
			}

			.theme-thumbnail {
				border-color: #369;
			}
		}
	}

	.theme-thumbnail {
		border: 1px solid #CCC;
		height: 68px;
		width: 85px;
	}

	.lfr-available-themes {
		h3 {
			margin: 0;
			overflow: hidden;
		}

		.lfr-theme-list {
			margin-top: 0.7em;
		}

		.header-title {
			float: left;
		}

		.install-themes {
			float: right;
			font-size: 11px;
		}
	}

	.lfr-theme-list .theme-details dd {
		margin: 0;
	}

	.theme-details {
		dl {
			margin-bottom: 1em;
		}

		dt {
			font-weight: bold;
			margin-top: 1em;
		}
	}

	.color-schemes {
		clear: both;

		.lfr-panel-content {
			overflow: hidden;
		}
	}

	.selected-color-scheme img {
		border: 3px solid #369;
	}

	table.lfr-top .taglib-header .header-title {
		margin: 0 0 0.5em 0;
	}

	.lfr-header-row {
		margin-bottom: 0.5em;
	}

	.manage-view {
		border: 1px solid #CCC;
	}

	.aui-button-holder.edit-toolbar {
		margin: 0;
	}

	.manage-layout-content, .manage-sitemap-content {
		padding: 0;
	}

	.manage-layout-content {
	}

	.edit-layout-form, .edit-layoutset-form {
		padding: 0.5em;

		.layout-description textarea {
			height: 100px;
			width: 350px;
		}
	}

	.layout-breadcrumb {
		ul {
			background-color: #FFF;
			border: 1px solid #DEDEDE;
			border-color: #C0C2C5;
			margin: 0 0 1em 0;
		}

		li {
			background-image: none;
			margin-right: 0;
			padding-left: 0.75em;
			padding-right: 0;

			span {
				background: url(<%= themeImagesPath %>/common/breadcrumbs.png) no-repeat 100% 50%;
				display: block;
				padding: 0.5em 15px 0.5em 0;

				a {
					text-decoration: none;
				}
			}

			&.first a {
				color: #369;
				font-weight: bold;
			}

			&.last a {
				color: #5C85AD;
				font-size: 1.3em;
				font-weight: bold;
			}
		}

		.last {
			font-size: 1em;
			margin-top: 0;
			padding-right: 0;

			span {
				background-image: none;
				padding: 0;
			}
		}
	}

	.customization-settings {
		.portlet-column {
			border: 1px solid #CCC;
			margin: 2px;

			.portlet-column-content {
				text-align: center;
			}

			h1 {
				font-size: 2em;
				font-weight: bold;
			}
		}
	}

	.layoutset-branch {
		color: #FFFFFF;
		display: inline-block;
		font-weight: bold;
		padding: 0.3em;
		text-shadow: 1px 1px #000;
	}

	.manage-pages-branch-menu.lfr-actions {
		.lfr-trigger strong {
			min-width: 0;

		}

		li img {
				padding: 0;
		}
	}

	.site-membership-type {
		font-weight: bold;
		padding: 0 0 15px;
	}
}

.aui-tree-node-selected {
	background: #eee;
}

.aui-tree-drag-helper a {
	text-decoration: none;
}

.aui-tree-drag-helper-label {
	margin-top: -1px;
}

.aui-tree-pages {
	.aui-tree-icon {
		background: transparent url() no-repeat 50% 50%;
		cursor: pointer;
		height: 18px;
		width: 18px;
	}

	.aui-tree-node-selected {
		.aui-tree-label a, .aui-tree-icon {
			cursor: move;
		}
	}
}

.aui-tree-expanded .aui-tree-icon {
	background-image: url(<%= themeImagesPath %>/trees/page_copy.png);
}

.aui-tree-collapsed .aui-tree-icon {
	background-image: url(<%= themeImagesPath %>/trees/page.png);
}

.lfr-export-dialog {
	.date-range-options {
		margin-bottom: 1em;
	}
}

.lfr-root-node {
	.aui-tree-icon {
		background-image: url(<%= themeImagesPath %>/trees/root.png);
		cursor: pointer;
	}

	.aui-tree-icon a, .aui-tree-label a {
		cursor: pointer;
	}
}

.lfr-tree-loading-icon {
	margin: 0 auto;
	padding-top: 5px;
}

.lfr-tree-controls {
	width: 165px;

	div, a {
		float: left;
	}
}

.lfr-tree-controls-label {
	line-height: 17px;
	padding: 0 2px;
	text-decoration: none;
}

.lfr-tree-controls-item {
	padding: 0 0 6px 6px;
}

.lfr-tree-controls-expand {
	background-image: url(<%= themeImagesPath %>/trees/expand_all.png);
}

.lfr-tree-controls-collapse {
	background-image: url(<%= themeImagesPath %>/trees/collapse_all.png);
}

.ie6 {
	.theme-thumbnail, .no-png-fix {
		behavior: none;
	}

	.lfr-available-themes h3 {
		height: 1%;
	}

	.lfr-page-layouts .lfr-layout-template-column {
		width: 30%;
	}
}