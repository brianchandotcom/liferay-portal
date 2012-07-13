<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%
String thumbnailSrc = "/html/themes/classic/images/file_system/large/default.png";

String thumbnailStyle = DLUtil.getThumbnailStyle();
%>

<div class="app-view-entry-taglib aui-helper-hidden entry-display-style display-icon" data-draggable="<%= Boolean.FALSE.toString() %>" data-title="{title}">
	<a class="entry-link" data-folder="<%= Boolean.FALSE.toString() %>" href="<%= uploadURL %>" title="{title}">
		<span class="entry-thumbnail">
			<img alt="" border="no" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />
		</span>

		<span class="entry-title">
			{title}
		</span>
	</a>
</div>

<div class="app-view-entry-taglib aui-helper-hidden entry-display-style display-descriptive" data-draggable="<%= Boolean.FALSE.toString() %>" data-title="{title}">
	<a class="entry-link" data-folder="<%= Boolean.FALSE.toString() %>" href="<%= uploadURL %>" title="{title}">
		<span class="entry-thumbnail">
			<img alt="" border="no" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />
		</span>

		<span class="entry-title">
			{title}
		</span>

		<span class="entry-description"></span>
	</a>
</div>