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
boolean clickToChatGroupEnabled = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_ENABLED));
boolean clickToChatSignedInUsersOnly = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_SIGNED_IN_USERS_ONLY));
String sytrategy = GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_PROVIDER_TOKEN_STRATEGY));
%>

<aui:input checked="<%= clickToChatGroupEnabled %>" disabled="<%= !clickToChatEnabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name='<%= "TypeSettingsProperties--" + ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_ENABLED + "--" %>' type="toggle-switch" value="<%= clickToChatGroupEnabled %>" />
<aui:input checked="<%= clickToChatSignedInUsersOnly %>" disabled="<%= !clickToChatEnabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "signed-in-users-only") %>' labelCssClass="simple-toggle-switch" name='<%= "TypeSettingsProperties--" + ClickToChatWebKeys.CLICK_TO_CHAT_SIGNED_IN_USERS_ONLY + "--" %>' type="toggle-switch" value="<%= clickToChatSignedInUsersOnly %>" />
<aui:input disabled="<%= !clickToChatEnabled || Objects.equals(sytrategy, ClickToChatProviderSiteStrategy.ALWAYS_INHERIT.getValue()) %>" label="account-token" name='<%= "TypeSettingsProperties--" + ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_PROVIDER_ACCOUNT_TOKEN + "--" %>' type="text" value="<%= GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_PROVIDER_ACCOUNT_TOKEN)) %>" />

<liferay-learn:message
	key='<%= GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_NAME)) + "-token" %>'
	resource="click-to-chat-web"
/>

<liferay-learn:json
	resource="click-to-chat-web"
/>