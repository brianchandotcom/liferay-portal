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

<div class="form-group row">
	<div class="col-md-12">
		<label class="control-label">
			<liferay-ui:message key="site-settings-strategy" />

			<liferay-ui:icon-help message="site-settings-strategy-description" />
		</label>
	</div>

	<%
	ClickToChatConfiguration clickToChatConfiguration = (ClickToChatConfiguration)request.getAttribute(ClickToChatConfiguration.class.getName());
	%>

	<c:if test="<%= Validator.isNotNull(clickToChatConfiguration.siteSettingsStrategy()) %>">
		<div class="col-md-12">
			<liferay-ui:message key='<%= "site-settings-strategy-" + clickToChatConfiguration.siteSettingsStrategy() %>' />
		</div>
	</c:if>
</div>

<div class="row">
	<div class="col-md-12">

		<%
		boolean clickToChatEnabled = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_ENABLED));

		boolean disabled = false;

		if (Objects.equals(clickToChatConfiguration.siteSettingsStrategy(), "always-inherit") || Validator.isNull(clickToChatConfiguration.siteSettingsStrategy())) {
			disabled = true;
		}
		%>

		<aui:input checked="<%= clickToChatEnabled %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--clickToChatEnabled--" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeEnableClickToChat(event);" %>' type="toggle-switch" value="<%= clickToChatEnabled %>" />
	</div>
</div>

<div class="row">
	<div class="col-md-6">

		<%
		String clickToChatChatProviderId = GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ID));
		%>

		<aui:select label="chat-provider" name="TypeSettingsProperties--clickToChatChatProviderId--" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeClickToChatChatProviderId(event);" %>' value="<%= clickToChatChatProviderId %>">
			<aui:option label="" value="" />

			<%
			for (String curClickToChatProviderId : ClickToChatConstants.CLICK_TO_CHAT_CHAT_PROVIDER_IDS) {
			%>

				<aui:option label='<%= "chat-provider-" + curClickToChatProviderId %>' value="<%= curClickToChatProviderId %>" />

			<%
			}
			%>

		</aui:select>

		<%
		boolean clickToChatGuestUsersAllowed = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GUEST_USERS_ALLOWED));
		%>

		<aui:input checked="<%= clickToChatGuestUsersAllowed %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "guest-users-allowed") %>' labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--clickToChatGuestUsersAllowed--" type="toggle-switch" value="<%= clickToChatGuestUsersAllowed %>" />
	</div>

	<div class="col-md-6">
		<aui:input label="chat-provider-account-id" name="TypeSettingsProperties--clickToChatChatProviderAccountId--" type="text" value="<%= GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ACCOUNT_ID)) %>" />

		<%
		for (String curClickToChatProviderId : ClickToChatConstants.CLICK_TO_CHAT_CHAT_PROVIDER_IDS) {
		%>

			<div id='<%= liferayPortletResponse.getNamespace() + curClickToChatProviderId %>' class='hide mb-2'">
				<liferay-learn:message
					key='<%= "chat-provider-account-id-help-" + curClickToChatProviderId %>'
					resource="click-to-chat-web"
				/>
			</div>

		<%
		}
		%>

	</div>
</div>

<script>
	var clickToChatEnabled = document.getElementById(
		'<portlet:namespace />clickToChatEnabled'
	);

	var clickToChatChatProviderId = document.getElementById(
		'<portlet:namespace />clickToChatChatProviderId'
	);

	var clickToChatChatProviderAccountId = document.getElementById(
		'<portlet:namespace />clickToChatChatProviderAccountId'
	);

	var clickToChatGuestUsersAllowed = document.getElementById(
		'<portlet:namespace />clickToChatGuestUsersAllowed'
	);

	if (<%= disabled %> || !clickToChatEnabled.checked) {
		Liferay.Util.toggleDisabled(clickToChatChatProviderId, true);
		Liferay.Util.toggleDisabled(clickToChatChatProviderAccountId, true);
		Liferay.Util.toggleDisabled(clickToChatGuestUsersAllowed, true);
	}

	function <portlet:namespace />hideContainers() {
		var clickToChatProviderIdOptions = clickToChatChatProviderId.querySelectorAll(
			'option'
		);

		clickToChatProviderIdOptions.forEach((option) => {
			<portlet:namespace />setVisibleClickToChatProviderIdHelp(option.value);
		});
	}

	function <portlet:namespace />onChangeEnableClickToChat() {
		Liferay.Util.toggleDisabled(
			clickToChatChatProviderAccountId,
			!clickToChatEnabled.checked
		);

		Liferay.Util.toggleDisabled(
			clickToChatGuestUsersAllowed,
			!clickToChatEnabled.checked
		);

		Liferay.Util.toggleDisabled(
			clickToChatChatProviderId,
			!clickToChatEnabled.checked
		);
	}

	function <portlet:namespace />setVisibleClickToChatProviderIdHelp(
		providerAccountId,
		visible
	) {
		var clickToChatProviderIdHelp = document.getElementById(
			'<portlet:namespace />' + providerAccountId
		);

		if (clickToChatProviderIdHelp) {
			if (visible) {
				return clickToChatProviderIdHelp.classList.remove('hide');
			}

			clickToChatProviderIdHelp.classList.add('hide');
		}
	}

	function <portlet:namespace />onChangeClickToChatChatProviderId(event) {
		<portlet:namespace />hideContainers();

		<portlet:namespace />setVisibleClickToChatProviderIdHelp(
			event.target.value,
			true
		);
	}

	<portlet:namespace />setVisibleClickToChatProviderIdHelp(
		'<%= clickToChatChatProviderId %>',
		true
	);
</script>