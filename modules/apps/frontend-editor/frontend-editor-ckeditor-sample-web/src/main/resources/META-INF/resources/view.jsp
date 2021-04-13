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

<div>
	<liferay-editor:editor
		contents="<h1>Balloon Editor</h1><p>This is a sample portlet with a <a href=\"https://example.com\">link</a>.</p><img src=\"https://images.unsplash.com/photo-1539037116277-4db20889f2d4?fit=crop&w=800\">"
		editorName="ballooneditor"
		name="contentEditor"
		placeholder="content"
	/>
</div>