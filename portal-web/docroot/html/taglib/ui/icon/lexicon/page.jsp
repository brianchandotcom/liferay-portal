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

<%@ include file="/html/taglib/ui/icon/init.jsp" %>

<%
boolean urlIsNotNull = Validator.isNotNull(url);
%>

<liferay-util:buffer var="linkContent">
	<liferay-ui:message key="<%= message %>" localizeKey="<%= localizeMessage %>" />
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= (iconListIconCount != null) && ((iconListSingleIcon == null) || iconListShowWhenSingleIcon) %>">
		<li class="<%= cssClass %>" role="presentation">
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a ariaRole="menuitem" cssClass="<%= linkCssClass %>" data="<%= data %>" href="<%= url %>" id="<%= id %>" lang="<%= lang %>" onClick="<%= onClick %>" target="<%= target %>">
						<%= linkContent %>
					</aui:a>
				</c:when>
				<c:otherwise>
					<%= linkContent %>
				</c:otherwise>
			</c:choose>
		</li>
	</c:when>
	<c:when test="<%= (iconMenuIconCount != null) && ((iconMenuSingleIcon == null) || iconMenuShowWhenSingleIcon) %>">
		<li class="<%= cssClass %>" role="presentation">
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a ariaRole="menuitem" cssClass="<%= linkCssClass %>" data="<%= data %>" href="<%= url %>" id="<%= id %>" lang="<%= lang %>" onClick="<%= onClick %>" target="<%= target %>">
						<%= linkContent %>
					</aui:a>
				</c:when>
				<c:otherwise>
					<span class="taglib-icon"><%= linkContent %></span>
				</c:otherwise>
			</c:choose>
		</li>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= urlIsNotNull %>">
				<aui:a ariaRole="<%= ariaRole %>" cssClass="<%= linkCssClass %>" data="<%= data %>" href="<%= url %>" id="<%= id %>" lang="<%= lang %>" onClick="<%= onClick %>" target="<%= target %>">
					<span class="icon-cog icon-monospaced"></span>
				</aui:a>
			</c:when>
			<c:otherwise>
				<%= linkContent %>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<c:if test="<%= Validator.isNotNull(srcHover) || forcePost || useDialog %>">
	<aui:script use="liferay-icon">
		Liferay.Icon.register(
			{
				forcePost: <%= forcePost %>,
				id: '<portlet:namespace /><%= id %>'

				<c:if test="<%= Validator.isNotNull(srcHover) %>">
					, src: '<%= src %>',
					srcHover: '<%= srcHover %>'
				</c:if>

				, useDialog: <%= useDialog %>
			}
		);
	</aui:script>
</c:if>