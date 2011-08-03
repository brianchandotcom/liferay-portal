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

.portlet-document-library .portlet-dynamic-data-mapping {
	.aui-form-builder-drop-container {
		width: 70%;
	}

	.aui-form-builder-tabs-container {
		width: 30%;

		.aui-field-input-text {
			width: 98%;
		}

		.aui-field-options-item-input {
			width: 78px;
		}
	}

	.aui-form-builder-settings .aui-field-input-checkbox {
		margin: 0 3px 0 0;
	}

	.aui-field-labels-inline {
		clear: both;
		display: block;

		.aui-field-label {
			margin-top: 0;
		}
	}

	.aui-field {
		.aui-field-content {
			clear: both;
			position: relative;

			.aui-form-validator-message {
				width: auto;
			}
		}
	}

	.ddm-field-icon-separator {
		background-image: url(<%= themeImagesPath %>/journal/selection_break.png);
		background-position: 0px 6px;
	}

	.form-fields-loading {
		list-style: none;
		padding: 10px 0;
		position: relative;
	}

	.lfr-structure-entry-details-container {
		margin-top: 10px;

		.lfr-panel-content {
			padding: 10px;
		}
	}

	.lfr-ddm-types-form-column .aui-column-content-first {
		margin-right: 10px;
	}

	.lfr-ddm-search-form .aui-column {
		margin-right: 10px;
	}

	.lfr-template-editor {
		.aui-field-element, .aui-field-content, .aui-field-input-text {
			display: block;
		}

		.aui-field-input-text {
			height: 400px;
		}
	}
}