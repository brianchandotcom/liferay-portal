<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long commentId = ParamUtil.getLong(request, "commentId");

Comment comment = CommentManagerUtil.fetchComment(commentId);
%>

<article class="lfr-discussion">
	<div class="lfr-discussion-details">
		<liferay-user:user-portrait
			userId="<%= comment.getUserId() %>"
		/>
	</div>

	<div class="lfr-discussion-message">
		<header class="lfr-discussion-message-body">
			<%= comment.getTranslatedBody(themeDisplay.getPathThemeImages()) %>
		</header>
	</div>
</article>