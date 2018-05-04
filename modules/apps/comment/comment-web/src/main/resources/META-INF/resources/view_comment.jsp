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

<%
long commentId = ParamUtil.getLong(request, "commentId");

Comment comment = CommentManagerUtil.fetchComment(commentId);
%>

<article class="lfr-discussion">
	<div class="lfr-discussion-details">
		<liferay-ui:user-display
			displayStyle="2"
			showUserName="<%= false %>"
			userId="<%= comment.getUserId() %>"
		/>
	</div>

	<div class="lfr-discussion-message">
		<header class="lfr-discussion-message-body">
			<%= comment.getTranslatedBody(themeDisplay.getPathThemeImages()) %>
		</header>
	</div>
</article>