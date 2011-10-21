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

.aui-document-library-folder-cell-editor-hidden {
	display: none;
}

.portlet-document-library, .portlet-dynamic-data-mapping {
	.aui-diagram-builder-drop-container {
		overflow: auto;
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
		}
	}

	.ddm-field-icon-separator {
		background-image: url(<%= themeImagesPath %>/journal/selection_break.png);
		background-position: 0px 6px;
	}

	.lfr-structure-entry-details-container {
		margin-top: 10px;

		.lfr-panel-content {
			padding: 10px;
		}
	}

	.lfr-translation-manager {
		margin-bottom: 15px;
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