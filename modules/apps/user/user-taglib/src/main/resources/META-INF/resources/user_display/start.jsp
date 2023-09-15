<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/user_display/init.jsp" %>

<div class="profile-header">
	<div class="nameplate">
		<div class="nameplate-field">
			<liferay-user:user-portrait
				user="<%= userDisplay %>"
			/>
		</div>

		<div class="nameplate-content">
			<div class="heading4">

				<%
				if (Validator.isNull(url) && (userDisplay != null)) {
					url = userDisplay.getDisplayURL(themeDisplay);
				}
				%>

				<clay:link
					href="<%= url %>"
					label="<%= (userDisplay != null) ? HtmlUtil.escape(userDisplay.getFullName()) : HtmlUtil.escape(userName) %>"
				/>
			</div>
		</div>

		<div class="nameplate-content">