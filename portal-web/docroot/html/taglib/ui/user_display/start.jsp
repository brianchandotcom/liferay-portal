<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/user_display/init.jsp" %>

<liferay-util:buffer
	var="html"
>
	<liferay-ui:user-portrait
		imageCssClass="<%= imageCssClass %>"
		user="<%= userDisplay %>"
		userName="<%= (userDisplay != null) ? userDisplay.getFullName() : userName %>"
	/>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= showUserDetails || showUserName %>">
		<div class="profile-header">
			<div class="nameplate">
				<div class="nameplate-field">
					<%= html %>
				</div>

				<c:if test="<%= showUserName %>">
					<div class="nameplate-content">
						<div class="heading4">

							<%
							if (Validator.isNull(url) && (userDisplay != null)) {
								url = userDisplay.getDisplayURL(themeDisplay);
							}
							%>

							<aui:a href="<%= showLink ? url : null %>">
								<%= (userDisplay != null) ? HtmlUtil.escape(userDisplay.getFullName()) : HtmlUtil.escape(userName) %>
							</aui:a>
						</div>
					</div>
				</c:if>

				<c:if test="<%= showUserDetails %>">
					<div class="nameplate-content">
				</c:if>
	</c:when>
	<c:otherwise>
		<%= html %>
	</c:otherwise>
</c:choose>