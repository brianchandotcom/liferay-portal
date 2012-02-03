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

<%@ include file="/html/taglib/init.jsp" %>

<%
String id = (String)request.getAttribute("liferay-ui:upload-progress:id");
String iframeSrc = (String)request.getAttribute("liferay-ui:upload-progress:iframe-src");
String redirect = (String)request.getAttribute("liferay-ui:upload-progress:redirect");
String message = (String)request.getAttribute("liferay-ui:upload-progress:message");
%>

<c:if test="<%= Validator.isNotNull(iframeSrc) %>">
	<div><iframe frameborder="0" id="<%= id %>-iframe" src="<%= iframeSrc %>" style="width: 100%;"></iframe></div>
</c:if>

<div><iframe frameborder="0" id="<%= id %>-poller" src="" style="height: 0; width: 0;"></iframe></div>

<div id="<%= id %>-bar-div" style="display: none; text-align: center;">
	<br />

	<c:if test="<%= Validator.isNotNull(message) %>">
		<%= LanguageUtil.get(pageContext, message) %>...<br />
	</c:if>

	<div style="background: url(<%= themeDisplay.getPathThemeImages() %>/progress_bar/incomplete_middle.png) scroll repeat-x top left; margin: auto; text-align: left; width: 80%;">
		<div style="background: url(<%= themeDisplay.getPathThemeImages() %>/progress_bar/incomplete_left.png) scroll no-repeat top left;">
			<div style="height: 23px; background: url(<%= themeDisplay.getPathThemeImages() %>/progress_bar/incomplete_right.png) scroll no-repeat top right;">
				<div id="<%= id %>-bar" style="background: url(<%= themeDisplay.getPathThemeImages() %>/progress_bar/complete_middle.png) scroll repeat-x top left; overflow: hidden; width: 0;">
					<div style="background: url(<%= themeDisplay.getPathThemeImages() %>/progress_bar/complete_left.png) scroll no-repeat top left;">
						<div class="font-small" style="font-weight: bold; height: 23px; padding-top: 3px; text-align: center; background: url(<%= themeDisplay.getPathThemeImages() %>/progress_bar/complete_right.png) scroll no-repeat top right;">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="<%= themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() %>/liferay/upload_progress.js" type="text/javascript"></script>

<aui:script>
	var <%= id %> = new UploadProgress("<%= id %>", "<%= HttpUtil.encodeURL(redirect) %>");
</aui:script>