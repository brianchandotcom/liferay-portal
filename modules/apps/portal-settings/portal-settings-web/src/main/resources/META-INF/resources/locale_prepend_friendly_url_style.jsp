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

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<div class="row">
	<div class="col-md-12">
		<br />

		<aui:select label="locale-prepend-friendly-url-style" name='<%= "settings--" + PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE + "--" %>' type="text" value="<%= (int)request.getAttribute(PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE) %>">
			<aui:option label="locale-is-not-automatically-prepended-to-a-url" value="0" />
			<aui:option label="locale-is-automatically-prepended-to-a-url-when-the-requested-locale-is-not-the-default-locale" value="1" />
			<aui:option label="locale-is-automatically-prepended-to-every-url" value="2" />
			<aui:option label="locale-is-automatically-prepended-to-a-url-when-the-requested-locale-is-not-the-default-user-locale" value="3" />
		</aui:select>
	</div>
</div>