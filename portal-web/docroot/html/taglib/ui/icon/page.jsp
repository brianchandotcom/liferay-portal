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
	<c:choose>
		<c:when test="<%= Validator.isNotNull(icon) %>">
			<aui:icon image="<%= icon %>" markupView="<%= markupView %>" />
		</c:when>
		<c:when test="<%= auiImage %>">
			<aui:icon image="<%= image.substring(_AUI_PATH.length()) %>" />
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="<%= Validator.isNotNull(src) %>">
					<c:choose>
						<c:when test="<%= Validator.isNotNull(id) %>">
							<img id="<%= id %>" src="<%= src %>" <%= details %> />
						</c:when>
						<c:otherwise>
							<img src="<%= src %>" <%= details %> />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:if test="<%= Validator.isNotNull(iconCssClass) %>">
						<i class="<%= iconCssClass %>"></i>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="<%= (iconMenuIconCount != null) && ((iconMenuSingleIcon == null) || iconMenuShowWhenSingleIcon) %>">
			<span class="taglib-text-icon"><liferay-ui:message key="<%= message %>" localizeKey="<%= localizeMessage %>" /></span>
		</c:when>
		<c:otherwise>
			<span class="taglib-text <%= label ? StringPool.BLANK : "hide-accessible" %>"><liferay-ui:message key="<%= message %>" localizeKey="<%= localizeMessage %>" /></span>
		</c:otherwise>
	</c:choose>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= (iconListIconCount != null) && ((iconListSingleIcon == null) || iconListShowWhenSingleIcon) %>">
		<li class="<%= cssClass %>" role="presentation">
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a ariaRole="menuitem" cssClass='<%= linkCssClass + " taglib-icon" %>' data="<%= data %>" href="<%= url %>" id="<%= id %>" lang="<%= lang %>" onClick="<%= onClick %>" target="<%= target %>">
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
					<aui:a ariaRole="menuitem" cssClass='<%= linkCssClass + " taglib-icon" %>' data="<%= data %>" href="<%= url %>" id="<%= id %>" lang="<%= lang %>" onClick="<%= onClick %>" target="<%= target %>">
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
		<span class="<%= cssClass %>"
			<c:if test="<%= !label && Validator.isNotNull(message) %>">
				onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key="<%= HtmlUtil.escapeJS(message) %>" />')"
			</c:if>
		>
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a ariaRole="<%= ariaRole %>" cssClass='<%= linkCssClass + " taglib-icon" %>' data="<%= data %>" href="<%= url %>" id="<%= id %>" lang="<%= lang %>" onClick="<%= onClick %>" target="<%= target %>">
						<%= linkContent %>
					</aui:a>
				</c:when>
				<c:otherwise>
					<%= linkContent %>
				</c:otherwise>
			</c:choose>
		</span>
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