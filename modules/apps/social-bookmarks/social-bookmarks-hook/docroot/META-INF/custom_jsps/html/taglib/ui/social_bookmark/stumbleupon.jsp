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

<%@ include file="/html/taglib/ui/social_bookmark/init.jsp" %>

<%
String stumbleUponDisplayStyle = "2";

if (displayStyle.equals("simple")) {
	stumbleUponDisplayStyle = "4";
}
else if (displayStyle.equals("vertical")) {
	stumbleUponDisplayStyle = "5";
}
%>

<liferay-util:html-bottom outputKey="taglib_ui_social_bookmark_stumble_upon">
	<script data-senna-track="temporary" type="text/javascript">
		if (window.STMBLPN) {
			delete window.STMBLPN;
		}
	</script>

	<script data-senna-track="temporary" src="<%= HttpUtil.getProtocol(request) %>://platform.stumbleupon.com/1/widgets.js" type="text/javascript"></script>
</liferay-util:html-bottom>

<su:badge layout="<%= stumbleUponDisplayStyle %>" location="<%= HttpUtil.encodeURL(url) %>"></su:badge>