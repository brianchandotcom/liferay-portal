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

<%
boolean clickToChatEnabled = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_ENABLED));
%>

<aui:input checked="<%= clickToChatEnabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--clickToChatEnabled--" type="toggle-switch" value="<%= clickToChatEnabled %>" />

<liferay-learn:message
	key="chatwoot-token"
	resource="com.liferay.click.to.chat.web"
/>