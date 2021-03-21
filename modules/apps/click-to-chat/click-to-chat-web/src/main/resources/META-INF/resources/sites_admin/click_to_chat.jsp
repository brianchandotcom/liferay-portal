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
boolean systemSettingsEnabled = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_SYSTEM_SETTINGS_ENABLED));
boolean clickToChatEnabled = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_ENABLED));
boolean clickToChatSignedInUsersOnly = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_SIGNED_IN_USERS_ONLY));
String groupProviderTokenStrategy = GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_PROVIDER_TOKEN_STRATEGY));
%>

<aui:input checked="<%= clickToChatEnabled %>" disabled="<%= !systemSettingsEnabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name='<%= "TypeSettingsProperties--" + ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_ENABLED + "--" %>' type="toggle-switch" value="<%= clickToChatEnabled %>" />
<aui:input checked="<%= clickToChatSignedInUsersOnly %>" disabled="<%= !systemSettingsEnabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "signed-in-users-only") %>' labelCssClass="simple-toggle-switch" name='<%= "TypeSettingsProperties--" + ClickToChatWebKeys.CLICK_TO_CHAT_SIGNED_IN_USERS_ONLY + "--" %>' type="toggle-switch" value="<%= clickToChatSignedInUsersOnly %>" />
<aui:input disabled="<%= !systemSettingsEnabled || Objects.equals(groupProviderTokenStrategy, GroupProviderTokenStrategy.ALWAYS_INHERIT.getValue()) %>" label="group-provider-account-token" name='<%= "TypeSettingsProperties--" + ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_PROVIDER_TOKEN_STRATEGY + "--" %>' type="text" value="<%= GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_PROVIDER_ACCOUNT_TOKEN)) %>" />