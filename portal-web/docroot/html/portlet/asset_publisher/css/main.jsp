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

.portlet-asset-publisher {
	.add-asset-selector {
		margin-bottom: 1em;
		overflow: auto;
	}

	.asset-abstract {
		.asset-content p {
			margin-bottom: 0;
		}

		.asset-description {
			clear: left;
		}
	}

	.asset-back-to {
		text-align: right;
	}

	.asset-content {
		clear: right;
		margin-bottom: 10px;
		margin-left: 25px;
		margin-right: 10px;

		.asset-description {
			clear: left;
			font-style: italic;
		}
	}

	.asset-flag {
		margin-top: 1.8em;
	}

	.asset-full-content {
		.asset-content {
			margin-right: 25px;
		}

		&.show-asset-title .asset-content {
			margin-right: 10px;
		}

		&.no-title .asset-user-actions{
			padding-bottom: 2em;
		}
	}

	.asset-edit {
		float: right;
		margin-left: 1em;
	}

	.asset-metadata {
		clear: both;
		margin-left: 25px;
		overflow: hidden;

		span {
			float: left;
		}

		.metadata-categories span {
			float: none;
		}
	}

	.asset-more {
		clear: left;
	}

	.asset-entries-group-label {
		background-color: #DDD;
		clear: both;
		padding: 3px;
	}

	.asset-small-image {
		float: right;
		padding-left: 0.5em;
		width: 150px;
	}

	.asset-actions {
		float: right;
		font-size: 11px;
		font-weight: normal;
		margin-bottom: 3px;
		margin-top: 0;
	}

	.asset-title {
		border-bottom: 1px solid #DDD;
		margin-bottom: 0.7em;
		margin-right: 8px;
		margin-top: 2em;
		padding-bottom: 1px;

		.asset-actions img {
			margin-left: 5px;
		}

		a {
			text-decoration: none;

			&:hover {
				text-decoration: underline;
			}
		}
	}

	.asset-user-actions {
		.export-actions, .print-action, .locale-actions {
			float: right;
		}

		.locale-separator {
			border-right: 1px solid #CCC;
			float: right;
			margin-right: 1em;
			padding: 0.8em 0.5em;
		}

		.print-action {
			margin: 0 1em;
		}
	}

	.edit-controls {
		margin-bottom: 20px;
	}

	.final-separator {
		border: 0;
		margin-bottom: 30px;
	}

	.lfr-meta-actions {
		margin-right: 1em;
		padding-top: 0;
	}

	.metadata-author {
		background: url(<%= themeImagesPath %>/portlet/edit_guest.png) no-repeat 0 0;
		float: left;
		font-weight: bold;
		margin-right: 10px;
		padding-left: 25px;
	}

	.metadata-entry {
		color: #999;
	}

	.metadata-modified-date, .metadata-create-date, .metadata-publish-date, .metadata-expiration-date {
		background: url(<%= themeImagesPath %>/common/date.png) no-repeat 0 0;
		color: #999;
		margin-bottom: 1em;
		padding-left: 25px;
	}

	.metadata-priority {
		background: url(<%= themeImagesPath %>/common/top.png) no-repeat 0 20%;
		margin-right: 10px;
		padding-left: 25px;
	}

	.metadata-view-count {
		margin-right: 10px;
	}

	.separator {
		border-right: 1px solid #999;
		clear: both;
		margin: 25px 25px;
	}

	.taglib-asset-categories-summary {
		float: left;
	}

	.taglib-asset-tags-summary {
		float: left;
	}

	.title-list {
		.asset-actions {
			left: 10px;
			position: relative;
		}

		.asset-metadata {
			padding: 0;
		}

		a {
			float: left;
		}
	}

	li.title-list {
		clear: both;
		list-style: none;
		margin-bottom: 0.15em;
		margin-right: 8px;
		padding-bottom: 1px;
	}

	ul.title-list {
		margin-left: 0;
	}
}

.portlet-configuration {
	.aui-form {
		.queryRules fieldset {
			padding: 0;

			legend {
				font-size: 1em;
				font-weight: normal;
				padding: 0;
			}
		}

		.lfr-form-row {
			background-color: #F3F3F3;

			&:hover {
				background-color: #DFFCCB;
			}

			.aui-field {
				line-height: 2;

				&.tags-selector, &.categories-selector {
					clear: both;
					line-height: 1.5;
					margin-top: 5px;
					width: 100%;
				}
			}
		}

		.general-display-settings {
			margin-bottom: 1em;
		}
	}

	.lfr-panel .lfr-panel-titlebar {
		margin-bottom: 0;
	}

	.lfr-panel-content {
		background-color: #F8F8F8;
		padding: 10px;
	}

	.dl-file-icon {
		border-width: 0;
		margin-right: 0.3em;
		position: relative;
		text-align: left;
		top: 0.4em;
	}

	.add-asset-selector {
		overflow: auto;
	}

	.asset-search {
		margin-bottom: 1em;
	}

	.lfr-actions.select-existing-selector {
		margin: 0 0 1em 1em;
	}

	.asset-subtype {
		margin-left: 2em;
	}
}

.ie {
	.portlet-asset-publisher {
		.asset-metadata {
			height: 1%;
		}
	}
}

.social-boomarks-options {
	margin-left: 2em;
}