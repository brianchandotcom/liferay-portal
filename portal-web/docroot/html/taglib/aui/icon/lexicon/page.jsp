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

<%@ include file="/html/taglib/aui/icon/init.jsp" %>

<liferay-util:buffer var="iconContent">
	<svg class="lexicon-icon lexicon-icon-<%= image %>" role="img" title="<%= HtmlUtil.escapeAttribute(LanguageUtil.get(request, label)) %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
		<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#<%= image %>" />
	</svg>

	<span class="taglib-icon-label">
		<liferay-ui:message key="<%= label %>" />
	</span>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= Validator.isNotNull(url) %>">
		<aui:a cssClass="<%= cssClass %>" data="<%= data %>" href="<%= url %>" id="<%= id %>" target="<%= target %>">
			<%= iconContent %>
		</aui:a>
	</c:when>
	<c:otherwise>
		<span class="<%= cssClass %>" <%= AUIUtil.buildData(data) %> id="<%= id %>">
			<%= iconContent %>
		</span>
	</c:otherwise>
</c:choose>

<liferay-util:html-bottom outputKey="taglib_aui_icon_lexicon">
	<aui:script>
		svg4everybody(
			{
				polyfill: true
			}
		);
	</aui:script>
</liferay-util:html-bottom>