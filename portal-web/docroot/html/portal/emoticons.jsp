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

<%@ include file="/html/portal/init.jsp" %>

<%
String[][] emoticons = BBCodeTranslatorUtil.getEmoticons();

for (int i = 0; i < emoticons.length; i++) {
	String image = StringUtil.replace(emoticons[i][0], ThemeConstants.TOKEN_THEME_IMAGES_PATH, themeDisplay.getPathThemeImages());
%>

	<a class="emoticon lfr-button" emoticonCode="<%= emoticons[i][1] %>"><%= image %></a>

<%
}
%>