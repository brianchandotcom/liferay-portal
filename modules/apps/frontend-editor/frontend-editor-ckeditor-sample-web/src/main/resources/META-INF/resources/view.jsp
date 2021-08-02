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

<liferay-util:buffer
	var="sampleEditorContents"
>
	<h1>Balloon Editor</h1>

	<p>
		Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nunc id cursus metus aliquam eleifend mi in nulla. Quam adipiscing vitae proin sagittis nisl rhoncus. Suspendisse faucibus interdum posuere lorem. Nullam ac tortor vitae purus faucibus ornare. Ac felis donec et odio pellentesque diam. Nulla at volutpat diam ut. Posuere urna nec tincidunt praesent semper feugiat nibh. Gravida quis blandit turpis cursus. Proin libero nunc consequat interdum varius. Sollicitudin ac orci phasellus egestas tellus rutrum tellus pellentesque. Neque volutpat ac tincidunt vitae semper quis lectus nulla at. Odio euismod lacinia at quis risus sed vulputate odio ut. Augue lacus viverra vitae congue eu consequat ac. Elementum sagittis vitae et leo duis ut diam. Diam quis enim lobortis scelerisque fermentum dui faucibus.
	</p>

	<p>
		This paragraph contains a <a href="https://example.com">link</a>.
	</p>

	<img src="https://images.unsplash.com/photo-1539037116277-4db20889f2d4?fit=crop&w=300" />
</liferay-util:buffer>

<liferay-editor:editor
	contents="<%= sampleEditorContents %>"
	editorName="ballooneditor"
	name="contentEditor"
	placeholder="content"
/>