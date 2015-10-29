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

<%@ include file="/html/taglib/ui/user_portrait/init.jsp" %>

<c:choose>
	<c:when test="<%= (userDisplay != null) && (userDisplay.getPortraitId() > 0) %>">

		<%
		String portraitURL = userDisplay.getPortraitURL(themeDisplay);
		%>

		<div class="<%= cssClass %> user-icon user-icon-lg">
			<img alt="thumbnail" class="img-responsive <%= imageCssClass %>" src="<%= HtmlUtil.escape(portraitURL) %>">
		</div>
	</c:when>
	<c:otherwise>

		<%
		if (Validator.isNull(userName)) {
			if (userDisplay != null) {
				userName = userDisplay.getFullName();
			}
			else {
				userName = LanguageUtil.get(request, "user");
			}
		}

		String[] userNames = StringUtil.split(userName, StringPool.SPACE);

		StringBundler sb = new StringBundler(userNames.length);

		for (String curUserName : userNames) {
			sb.append(StringUtil.toUpperCase(StringUtil.shorten(curUserName, 1)));
		}
		%>

		<div class="<%= cssClass %> user-icon user-icon-default">
			<span><%= sb.toString() %></span>
		</div>
	</c:otherwise>
</c:choose>