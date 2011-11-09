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

.lfr-image-gallery-actions {
	font-size: 11px;
	text-align: right;
}

.portlet-image-gallery-display {
	.lfr-asset-attributes {
		clear: both;
	}

	.folder-search {
		float: right;
		margin: 0 0 0.5em 0.5em;
	}

	.image-score {
		display: block;
		margin: 0 0 5px 35px;
		padding-top: 3px;
	}

	.image-thumbnail, .folder-thumbnail {
		border: 2px solid #eee;
		float: left;
		height: 162px;
		margin: 20px 4px 0;
		padding: 5px 5px 0;
		text-align: center;
		text-decoration: none;
		width: 150px;

		&:hover {
			border-color: #ccc;
		}
	}

	.taglib-webdav {
		margin-top: 3em;
	}

	.image-title {
		display: block;
	}
}