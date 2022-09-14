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

<%@ include file="/html/taglib/init.jsp" %>

<%
String alertIcon = (String)request.getAttribute("liferay-ui:error:alertIcon");
String alertMessage = (String)request.getAttribute("liferay-ui:error:alertMessage");
String alertStyle = (String)request.getAttribute("liferay-ui:error:alertStyle");
String alertTitle = (String)request.getAttribute("liferay-ui:error:alertTitle");
%>

<c:choose>
	<c:when test='<%= GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:error:embed")) %>'>
		<div class="alert alert-dismissible alert-<%= alertStyle %>" role="alert">
			<button aria-label="<%= LanguageUtil.get(request, "close") %>" class="close" data-dismiss="liferay-alert" type="button">
				<aui:icon image="times" markupView="lexicon" />

				<span class="sr-only"><liferay-ui:message key="close" /></span>
			</button>

			<span class="alert-indicator">
				<svg aria-hidden="true" class="lexicon-icon lexicon-icon-<%= alertIcon %>">
					<use xlink:href="<%= FrontendIconsUtil.getSpritemap(themeDisplay) %>#<%= alertIcon %>"></use>
				</svg>
			</span>

			<strong class="lead"><%= alertTitle %></strong><%= alertMessage %>
		</div>

		<%= (String)request.getAttribute("liferay-ui:error:rowBreak") %>
	</c:when>
	<c:otherwise>
		<aui:script>
			Liferay.Util.openToast({
			   message: '<%= HtmlUtil.escapeJS(alertMessage) %>',
			   title: '<%= alertTitle %>',
			   type: '<%= alertStyle %>'
			});
		</aui:script>
	</c:otherwise>
</c:choose>