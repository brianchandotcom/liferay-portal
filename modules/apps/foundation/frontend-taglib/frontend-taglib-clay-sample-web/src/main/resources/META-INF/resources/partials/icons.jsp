<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/init.jsp" %>

<blockquote><p>Icon is a visual metaphor representing a concept that lies behind the idea and/or action.</p></blockquote>

<h3>Liferay Icon Library</h3>

<div class="mb-3 row">

	<%
	String[] icons = {"add-cell", "add-column", "add-role", "add-row", "adjust", "align-center", "align-justify", "align-left", "align-right", "angle-down", "angle-left", "angle-right", "angle-up", "archive", "asterisk", "audio", "autosize", "bars", "blogs", "bold", "bookmarks", "calendar", "camera", "cards", "cards2", "caret-bottom", "caret-double-l", "caret-double", "caret-top", "categories", "chain-broken", "change", "check-circle", "check", "code", "cog", "columns", "comments", "compress", "control-panel", "custom-size", "cut", "date", "desktop", "documents-and-media", "download", "dynamic-data-list", "edit-layout", "effects", "ellipsis-h", "ellipsis-v", "embed", "envelope-closed", "envelope-open", "exclamation-circle", "exclamation-full", "expand", "flag-empty", "flag-full", "folder", "format", "forms", "full-size", "geolocation", "grid", "h1", "h2", "heart", "hidden", "home", "horizontal-scroll", "hr", "import-export", "indent-less", "indent-more", "info-circle-open", "info-circle", "info-panel-closed", "info-panel-open", "information-live", "italic", "link", "list-ol", "list-ul", "list", "live", "lock", "logout", "magic", "mark-as-answer", "mark-as-question", "message-boards", "mobile-device-rules", "mobile-landscape", "mobile-portrait", "moon", "move", "myspace", "order-arrow", "organizations", "page-template", "page", "pages-tree", "paperclip", "paragraph", "password-policies", "paste", "pause", "pencil", "phone", "picture", "play", "plus", "polls", "print", "product-menu-closed", "product-menu-open", "product-menu", "question-circle-full", "question-circle", "quote-left", "quote-right", "radio-button", "redo", "reload", "remove-role", "remove-style", "reply", "repository", "restore", "rss", "search", "select-from-list", "share-alt", "share", "shortcut", "simulation-menu-closed", "simulation-menu-open", "simulation-menu", "site-template", "sites", "staging", "star-half", "star-o", "star", "strikethrough", "subscript", "suitcase", "sun", "superscript", "table", "table2", "tablet-landscape", "tablet-portrait", "tag", "text-editor", "text", "thumbs-down", "thumbs-up", "time", "times-circle", "times", "transform", "trash", "twitter", "underline", "undo", "unlock", "user", "users", "vertical-scroll", "view", "vocabulary", "web-content", "wiki-page", "wiki", "workflow"};

	for (int i = 0; i < icons.length; i++) {
	%>

		<div class="col-md-3">
			<clay:icon
				symbol="<%= icons[i] %>"
			/>

			<span class="ml-2"><%= icons[i] %></span>
		</div>

	<%
	}
	%>

</div>

<h3>Language Flags</h3>

<div class="mb-3 row">

	<%
	String[] flags = {"ar-sa", "bg-bg", "ca-ad", "ca-es", "cs-cz", "da-dk", "de-de", "el-gr", "en-au", "en-gb", "en-us", "es-es", "et-ee", "eu-es", "fa-ir", "fi-fi", "fr-ca", "fr-fr", "gl-es", "hi-in", "hr-hr", "hu-hu", "in-id", "it-it", "iw-il", "ja-jp", "ko-kr", "lo-la", "lt-lt", "nb-no", "nl-be", "nl-nl", "pl-pl", "pt-br", "pt-pt", "ro-ro", "ru-ru", "sk-sk", "sl-si", "sr-rs-latin", "sr-rs", "sv-se", "th-th", "tr-tr", "uk-ua", "vi-vn", "zh-cn", "zh-tw"};

	for (int i = 0; i < flags.length; i++) {
	%>

		<div class="col-md-3">
			<clay:icon
				symbol="<%= flags[i] %>"
			/>

			<span class="ml-2"><%= flags[i] %></span>
		</div>

	<%
	}
	%>

</div>