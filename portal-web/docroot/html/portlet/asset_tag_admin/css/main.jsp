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

.portlet-asset-tag-admin {
	.tags-admin-actions {
		clear: none;
		float: left;
		margin: 0;
	}

	.tags-admin-container .results-header {
		background: #F0F5F7;
		font-weight: bold;
		padding: 5px 10px;
		position: relative;
	}

	.tags-admin-toolbar {
		background: #F6F8FB;
		border-bottom: 1px solid #dedede;
		overflow: hidden;
		padding: 5px 0;
	}

	.tags-admin-search {
		.aui-field-content {
			display: inline-block;
		}

		.aui-field-input {
			background-image: url(<%= themeImagesPath %>/common/search.png);
			background-repeat: no-repeat;
			background-position: 5px 50%;
			padding-left: 25px;
			width: 250px;
		}
	}

	.tags-admin-content-wrapper {
		position: relative;
	}

	.tags-admin-content-wrapper, .header-title, .aui-tree-node-content .aui-tree-label {
		word-wrap: break-word;
	}

	.tags-admin-edit-tag {
		.tag-view-container {
			padding: 0 0.5em 0 0.5em;
		}

		.tag-details {
			font-weight: bold;
			vertical-align: middle;
		}
	}

	.tag-item a {
		display: block;
		padding: 8px 20px 8px 25px;
		text-decoration: none;
	}

	.tag-item-actions-trigger {
		 display: block;
		 margin-top: -8px;
		 position: absolute;
		 top: 50%;
	}

	.tag-item-actions-trigger {
		background-image: url(<%= themeImagesPath %>/common/edit.png);
		background-repeat: no-repeat;
		clip: rect(0pt, 0pt, 0pt, 0pt);
		height: 16px;
		margin-top: -8px;
		padding: 0;
		right: 0;
		width: 16px;
	}

	.tag-item-check {
		margin-left: 10px;
		position: static;
		margin-top: 10px;
		float: left;
	}

	.tags-admin-content {
		li.tag-item-container, li.tag-item {
			font-weight: bold;
			list-style: none;
		}
	}

	.tag-item-container {
		&:hover, &:focus {
			.tag-item-actions-trigger {
				clip: rect(auto auto auto auto);
			}
		}
	}

	.tag-item-actions-trigger:focus {
		clip: rect(auto auto auto auto);
	}

	.tags-admin-list {
		.selected {
			a:hover {
				color: #FFB683;
			}

			a, .tags-admin-content-wrapper, .tags-admin-content-wrapper:hover {
				background-color: #6F7D83;
				color: #FFF;
			}
		}

		.active-area {
			border: 1px solid #008000;

			a {
				background-color: #90EE90;
				color: #90EE90;
			}
		}

		.tags-admin-content-wrapper {
			background-color: #F5F5F5;

			&:hover {
				background: #D3DADD;
			}
		}
	}

	.select-tags {
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

	.tags-admin-list-container {
		&:after {
			clear: both;
			content: "";
			display: block;
			height: 0;
		}
	}

	.tags-admin-search-bar {
		float: left;
	}

	.view-tag {
		margin: 1em;

		label {
			display: block;
			font-weight: bold;
		}

		.tag-field {
			clear: left;
			margin: 1em auto;
		}
	}
}

.tag-item-merge {
	border: 1px solid #4E4848;
	display: block;
	font-weight: bold;
	list-style: none;
	padding: 8px 20px 8px 10px;

	a {
		text-decoration: none;
	}
}

.portlet-asset-tag-admin-dialog {
	.aui-fieldset {
		margin-bottom: 0;
	}

	.lfr-panel-container {
		background-color: transparent;
		border-width: 0;
	}

	.asset-tag-layer .aui-overlay {
		overflow: visible;
		width: 230px;
	}

	.yui3-widget-bd {
		.asset-tag-layer {
			padding: 0 10px;

			.aui-field-content {
				margin-bottom: 10px;
			}

			label {
				display: block;
				font-weight: bold;
			}

			.aui-field {
				input, select {
					width: 200px;
				}
			}

			.button-holder {
				margin-top: 10px;
			}
		}
	}

	.tags-admin-merge-tag {
		.arrows-container {
			top: 50%;
			margin-top: -20px;
		}

		label {
			color: #5C85AD;
			display: inline-block;
			margin-bottom: 0.5em;
		}

		.selected-tags-container, .target-tags-container {
			padding: 0.5em;
		}

		.selected-tags-list, .target-tags-list {
			width: 100%;
		}

		.tag-move {
			display: block;
			height: 18px;
			position: relative;
			top: 30px;
			width: 18px;
		}

		.tag-move-down {
			background-image: url(<%= themeImagesPath %>/arrows/02_down.png);
			margin-top: 0.2em;
		}

		.tag-move-up {
			background-image: url(<%= themeImagesPath %>/arrows/02_up.png);
		}

		.tag-options {
			text-align: left;
		}

		.tags-merge-label {
			margin-left: 0.5em;
			vertical-align: middle;
		}
	}
}

.ie {
	.portlet-asset-tag-admin {
		.tag-item a {
			zoom: 1;
		}
	}
}

.ie6, .ie7 {
	.portlet-asset-tag-admin {
		.tags-admin-content-wrapper {
			width: 100%;
		}

		.tag-item-actions-trigger, .tag-item-check {
			cursor: pointer;
		}
	}

	.portlet-asset-tag-admin-dialog {
		.yui3-widget-bd .asset-tag-layer .aui-field {
			width: 200px;
		}
	}
}

.ie6, .ie7, .ie8 {
	.portlet-asset-tag-admin {
		.tag-item-merge {
			font-weight: normal;
			font-size: 120%;
		}
	}
}