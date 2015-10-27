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

<h3><liferay-ui:message key="mentions" /><h3>

<%
boolean companyMentionsEnabled = GetterUtil.getBoolean(request.getAttribute(MentionsWebKeys.COMPANY_MENTIONS_ENABLED));
%>

<aui:input checked="<%= companyMentionsEnabled %>" label='<%= LanguageUtil.get(resourceBundle, "enable-mentions") %>' name="settings--mentionsEnabled--" type="checkbox" value="<%= companyMentionsEnabled %>" />

<%
SocialInteractionsConfiguration mentionsSocialInteractionsConfiguration = SocialInteractionsConfigurationUtil.getSocialInteractionsConfiguration(company.getCompanyId(), request, MentionsPortletKeys.MENTIONS);
%>

<aui:input checked="<%= mentionsSocialInteractionsConfiguration.isSocialInteractionsAnyUserEnabled() %>" id="socialInteractionsAnyUser" label='<%= LanguageUtil.get(resourceBundle, "all-users-can-interact-with-each-other") %>' name='<%= "settings--socialInteractionsType" + MentionsPortletKeys.MENTIONS + "--" %>' type="radio" value="<%= SocialInteractionsConfiguration.SocialInteractionsType.ALL_USERS.toString() %>" />

<aui:input checked="<%= mentionsSocialInteractionsConfiguration.isSocialInteractionsSelectUsersEnabled() %>" id="socialInteractionsChooseUsers" label='<%= LanguageUtil.get(resourceBundle, "define-social-interactions-for-users") %>' name='<%= "settings--socialInteractionsType" + MentionsPortletKeys.MENTIONS + "--" %>' type="radio" value="<%= SocialInteractionsConfiguration.SocialInteractionsType.SELECT_USERS.toString() %>" />

<div class="social-interactions-users" id="<portlet:namespace />socialInteractionsUsersWrapper">
	<aui:input checked="<%= mentionsSocialInteractionsConfiguration.isSocialInteractionsSitesEnabled() %>" label='<%= LanguageUtil.get(resourceBundle, "site-members-can-interact-with-each-other") %>' name='<%= "settings--socialInteractionsSitesEnabled" + MentionsPortletKeys.MENTIONS + "--" %>' type="checkbox" value="<%= mentionsSocialInteractionsConfiguration.isSocialInteractionsSitesEnabled() %>" />

	<aui:input checked="<%= mentionsSocialInteractionsConfiguration.isSocialInteractionsFriendsEnabled() %>" label='<%= LanguageUtil.get(resourceBundle, "friends-can-interact-with-each-other") %>' name='<%= "settings--socialInteractionsFriendsEnabled" + MentionsPortletKeys.MENTIONS + "--" %>' type="checkbox" value="<%= mentionsSocialInteractionsConfiguration.isSocialInteractionsFriendsEnabled() %>" />
</div>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	Util.toggleRadio('<portlet:namespace />socialInteractionsAnyUser', '', '<portlet:namespace />socialInteractionsUsersWrapper');
	Util.toggleRadio('<portlet:namespace />socialInteractionsChooseUsers', '<portlet:namespace />socialInteractionsUsersWrapper', '');
</aui:script>