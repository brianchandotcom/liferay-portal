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

<span class="user-avatar-link">
	<liferay-util:buffer
		var="userAvatar"
	>
		<span class="sticker">
			<span class="inline-item">
				<liferay-ui:user-portrait
					cssClass="sticker"
					user="<%= user %>"
				/>
			</span>

			<c:if test="<%= themeDisplay.isImpersonated() %>">
				<span class="sticker sticker-bottom-right sticker-circle sticker-outside sticker-sm sticker-user-icon">
					<aui:icon image="user" markupView="lexicon" />
				</span>
			</c:if>
		</span>
	</liferay-util:buffer>

	<liferay-product-navigation:personal-menu
		expanded="<%= true %>"
		label="<%= userAvatar %>"
	/>
</span>