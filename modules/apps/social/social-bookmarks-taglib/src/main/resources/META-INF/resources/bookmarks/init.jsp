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
String className = GetterUtil.getString((String)request.getAttribute("liferay-social-bookmarks:bookmarks:className"));
long classPK = GetterUtil.getLong((Long)request.getAttribute("liferay-social-bookmarks:bookmarks:classPK"));
String displayStyle = GetterUtil.getString((String)request.getAttribute("liferay-social-bookmarks:bookmarks:displayStyle"));
int maxInlineItems = GetterUtil.getInteger(request.getAttribute("liferay-social-bookmarks:bookmarks:maxInlineItems"));
String target = GetterUtil.getString((String)request.getAttribute("liferay-social-bookmarks:bookmarks:target"));
String title = GetterUtil.getString((String)request.getAttribute("liferay-social-bookmarks:bookmarks:title"));
String[] types = SocialBookmarksRegistryUtil.getValidTypes((String[])request.getAttribute("liferay-social-bookmarks:bookmarks:types"));
String url = GetterUtil.getString((String)request.getAttribute("liferay-social-bookmarks:bookmarks:url"));
%>