<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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