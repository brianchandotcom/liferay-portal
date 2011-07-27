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

.portlet-blogs {
	.taglib-custom-attributes-list {
		margin-bottom: 1em;
	}

	.taglib-custom-attributes-list label {
		display: block;
		font-weight: bold;
	}

	.edit-actions {
		margin: 1.5em 0;
	}

	.entry {
		clear: both;
	}

	.entry-author {
		background: url(<%= themeImagesPath %>/portlet/edit_guest.png) no-repeat 0 50%;
		border-right: 1px solid #999;
		color: #999;
		float: left;
		font-weight: bold;
		margin-right: 10px;
		padding-left: 25px;
		padding-right: 10px;
	}

	.entry-body {
		margin-bottom: 10px;
	}

	.entry-date {
		background: url(<%= themeImagesPath %>/common/date.png) no-repeat 0 50%;
		color: #999;
		padding-left: 25px;
	}

	.taglib-asset-categories-summary, .taglib-asset-tags-summary {
		border-left: 1px solid #999;
		padding-left: 10px;
	}

	.entry-title {
		display: block;
		font-size: 1.5em;
		font-weight: bold;
		margin-bottom: 0.5em;
	}

	.entry.draft {
		background: #eee;
		border: 1px solid #ccc;
		color: #555;
		padding: 5px;

		h3 {
			background: url(<%= themeImagesPath %>/common/page.png) no-repeat 0 50%;
			margin-top: 0;
			padding-left: 20px;
		}
	}

	.entry-navigation {
		background: #eee;
		border-top: 1px solid #ccc;
		margin: 15px 0 0;
		overflow: hidden;
		padding: 5px;

		a, span {
			background: url() no-repeat;
		}

		.previous {
			background-image: url(<%= themeImagesPath %>/arrows/paging_previous.png);
			float: left;
			padding-left: 15px;
		}

		span.previous {
			background-position: 0 100%;
		}

		.next {
			background-image: url(<%= themeImagesPath %>/arrows/paging_next.png);
			background-position: 100% 0;
			float: right;
			padding-right: 15px;
		}

		span.next {
			background-position: 100% 100%;
		}
	}

	.preview {
		background: #ffc;
		border: 1px dotted gray;
		padding: 3px;
	}

	.search-form {
		float: right
	}

	.stats {
		color: #999;
		float: left;
		padding-right: 10px;
	}

	.subscribe {
		margin-bottom: 1.5em;
	}

	.taglib-flags {
		border-left: 1px solid #999;
		color: #999;
		padding-left: 10px;
		padding-right: 10px;
	}

	.taglib-ratings.stars {
		margin-top: 0.5em;
	}

	.taglib-social-bookmarks {
		clear: both;
		margin-top: 1.5em;

		ul {
			padding: 1em;
		}
	}

	#blogsCommentsPanelContainer {
		border-width: 0;
	}
}

.social-boomarks-options {
	margin-left: 2em;
}