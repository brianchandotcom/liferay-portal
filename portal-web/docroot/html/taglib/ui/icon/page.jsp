<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
if (Validator.isNotNull(src) && themeDisplay.isThemeImagesFastLoad() && !auiImage) {
	SpriteImage spriteImage = null;
	String spriteFileName = null;
	String spriteFileURL = null;

	String imageFileName = StringUtil.replace(src, "common/../", "");

	if (imageFileName.contains(Http.PROTOCOL_DELIMITER)) {
		URL imageURL = new URL(imageFileName);

		imageFileName = imageURL.getPath();
	}

	String contextPath = theme.getContextPath();

	String imagesPath = contextPath.concat(theme.getImagesPath());

	if (imageFileName.startsWith(imagesPath)) {
		spriteImage = theme.getSpriteImage(imageFileName);

		if (spriteImage != null) {
			spriteFileName = spriteImage.getSpriteFileName();

			if (BrowserSnifferUtil.isIe(request) && (BrowserSnifferUtil.getMajorVersion(request) < 7)) {
				spriteFileName = StringUtil.replace(spriteFileName, ".png", ".gif");
			}

			String cdnBaseURL = themeDisplay.getCDNBaseURL();

			spriteFileURL = cdnBaseURL.concat(spriteFileName);
		}
	}

	if (spriteImage == null) {
		Portlet portlet = (Portlet)request.getAttribute("liferay-portlet:icon_portlet:portlet");

		if (portlet == null) {
			portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);
		}

		if (portlet != null) {
			PortletApp portletApp = portlet.getPortletApp();

			spriteImage = portletApp.getSpriteImage(imageFileName);

			if (spriteImage != null) {
				spriteFileName = spriteImage.getSpriteFileName();

				if (BrowserSnifferUtil.isIe(request) && (BrowserSnifferUtil.getMajorVersion(request) < 7)) {
					spriteFileName = StringUtil.replace(spriteFileName, ".png", ".gif");
				}

				String cdnBaseURL = themeDisplay.getCDNBaseURL();

				spriteFileURL = cdnBaseURL.concat(spriteFileName);
			}
		}
	}

	if (spriteImage != null) {
		String themeImagesPath = themeDisplay.getPathThemeImages();

		src = themeImagesPath.concat("/spacer.png");

		StringBundler sb = new StringBundler(10);

		sb.append(details);
		sb.append(" style=\"background-image: url('");
		sb.append(spriteFileURL);
		sb.append("'); background-position: 50% -");
		sb.append(spriteImage.getOffset());
		sb.append("px; background-repeat: no-repeat; height: ");
		sb.append(spriteImage.getHeight());
		sb.append("px; width: ");
		sb.append(spriteImage.getWidth());
		sb.append("px;\"");

		details = sb.toString();
	}
}

boolean urlIsNotNull = Validator.isNotNull(url);
%>

<liferay-util:buffer var="linkContent">
	<c:choose>
		<c:when test="<%= auiImage %>">
			<aui:icon image="<%= image.substring(_AUI_PATH.length()) %>" />
		</c:when>
		<c:otherwise>
			<c:if test="<%= Validator.isNotNull(src) %>">
				<c:choose>
					<c:when test="<%= urlIsNotNull %>">
						<img src="<%= src %>" <%= details %> />
					</c:when>
					<c:otherwise>
						<img id="<%= id %>" src="<%= src %>" <%= details %> />
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="<%= (iconMenuIconCount != null) && ((iconMenuSingleIcon == null) || iconMenuShowWhenSingleIcon) %>">
			<liferay-ui:message key="<%= message %>" localizeKey="<%= localizeMessage %>" />
		</c:when>
		<c:when test="<%= (iconListIconCount != null) && ((iconListSingleIcon == null) || iconListShowWhenSingleIcon) %>">
			<span class="taglib-text"><liferay-ui:message key="<%= message %>" localizeKey="<%= localizeMessage %>" /></span>
		</c:when>
		<c:otherwise>
			<c:if test="<%= label %>">
				<span class="taglib-text"><liferay-ui:message key="<%= message %>" localizeKey="<%= localizeMessage %>" /></span>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= (iconListIconCount != null) && ((iconListSingleIcon == null) || iconListShowWhenSingleIcon) %>">
		<li class="<%= cssClass %>">
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a cssClass='<%= linkCssClass + " taglib-icon" %>' data="<%= data %>" href="<%= url %>" id="<%= id %>" lang="<%= lang %>" target="<%= target %>">
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
		<li class="<%= cssClass %>">
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a cssClass='<%= linkCssClass + " taglib-icon" %>' data="<%= data %>" href="<%= url %>" id="<%= id %>" lang="<%= lang %>" onClick='<%= Validator.isNotNull(onClick) ? onClick : "" %>' target="<%= target %>">
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
		<span class="<%= cssClass %>">
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a cssClass='<%= linkCssClass + " taglib-icon" %>' data="<%= data %>" href="<%= url %>" id="<%= id %>" lang="<%= lang %>" onClick='<%= Validator.isNotNull(onClick) ? onClick : "" %>' target="<%= target %>">
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

<%
boolean forcePost = method.equals("post") && (url.startsWith(Http.HTTP_WITH_SLASH) || url.startsWith(Http.HTTPS_WITH_SLASH));
%>

<c:if test="<%= Validator.isNotNull(srcHover) || forcePost %>">
	<aui:script use="liferay-icon">
		Liferay.Icon.register(
			{
				forcePost: <%= forcePost %>,
				id: '<portlet:namespace /><%= id %>'

				<c:if test="<%= Validator.isNotNull(srcHover) %>">
					, src: '<%= src %>',
					srcHover: '<%= srcHover %>'
				</c:if>
			}
		);
	</aui:script>
</c:if>