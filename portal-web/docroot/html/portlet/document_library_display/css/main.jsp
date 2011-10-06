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

.portlet-document-library-display {
	.entry-title {
		display: block;
		font-size: 1.15em;
		font-weight: bold;
		padding: 1.4em 0 0;
	}

	.file-entry-list-description {
		font-style: italic;
		margin-left: 10px;
	}

	.file-entry-tags {
		margin-top: 5px;
	}

	.folder-search {
		float: right;
		margin: 0 0 0.5em 0.5em;
	}

	.entry-thumbnail {
		float: left;
		margin-right: 1em;
		position: relative;

		.thumbnail {
			float: left;
			max-height: 128px;
			max-width: 128px;
		}
	}

	.taglib-webdav {
		margin-top: 3em;
	}

	.taglib-workflow-status {
		margin-bottom: 5px;
	}

	.workflow-status-pending {
		&, a {
			color: orange;
		}
	}
}

.ie6 .portlet-document-library-display {
	img.entry-thumbnail {
		height: expression(this.height > 128 ? '128px' : this.height);
		width: expression(this.width > 128 ? '128px' : this.width);
	}
}