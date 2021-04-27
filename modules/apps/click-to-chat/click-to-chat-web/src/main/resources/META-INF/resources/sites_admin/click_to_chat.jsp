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
ClickToChatConfiguration clickToChatConfiguration = (ClickToChatConfiguration)request.getAttribute(ClickToChatConfiguration.class.getName());
%>

<div class="form-group row">
	<div class="col-md-12">
		<label class="control-label">
			<liferay-ui:message key="site-settings-strategy" />

			<liferay-ui:icon-help message="site-settings-strategy-description" />
		</label>
	</div>

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

		<aui:input checked="<%= clickToChatEnabled %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--clickToChatEnabled--" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeClickToChatEnabled(event);" %>' type="toggle-switch" value="<%= clickToChatEnabled %>" />
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

			<div class="hide mb-2" id="<portlet:namespace />clickToChatProviderLearnMessage<%= curClickToChatProviderId %>">
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
	function <portlet:namespace />hideUnselectedClickToChatProviderLearnMessages() {
		var clickToChatProviderIdOptions = clickToChatChatProviderId.querySelectorAll(
			'option'
		);

		clickToChatProviderIdOptions.forEach((option) => {
			<portlet:namespace />setVisibleClickToChatProviderLearnMessage(
				option.value,
				false
			);
		});
	}

	function <portlet:namespace />onChangeClickToChatEnabled() {
		Liferay.Util.toggleDisabled(
			clickToChatChatProviderAccountId,
			!clickToChatEnabled.checked
		);

		Liferay.Util.toggleDisabled(
			clickToChatChatProviderId,
			!clickToChatEnabled.checked
		);

		Liferay.Util.toggleDisabled(
			clickToChatGuestUsersAllowed,
			!clickToChatEnabled.checked
		);
	}

	function <portlet:namespace />onChangeClickToChatChatProviderId(event) {
		<portlet:namespace />hideUnselectedClickToChatProviderLearnMessages();

		<portlet:namespace />setVisibleClickToChatProviderLearnMessage(
			event.target.value,
			true
		);
	}

	function <portlet:namespace />setVisibleClickToChatProviderLearnMessage(
		clickToChatChatProviderAccountId,
		visible
	) {
		var clickToChatProviderLearnMessage = document.getElementById(
			'<portlet:namespace />clickToChatProviderLearnMessage' +
				clickToChatChatProviderAccountId
		);

		if (clickToChatProviderLearnMessage) {
			if (visible) {
				return clickToChatProviderLearnMessage.classList.remove('hide');
			}

			clickToChatProviderLearnMessage.classList.add('hide');
		}
	}

	var clickToChatChatProviderAccountId = document.getElementById(
		'<portlet:namespace />clickToChatChatProviderAccountId'
	);

	var clickToChatChatProviderId = document.getElementById(
		'<portlet:namespace />clickToChatChatProviderId'
	);

	var clickToChatEnabled = document.getElementById(
		'<portlet:namespace />clickToChatEnabled'
	);

	var clickToChatGuestUsersAllowed = document.getElementById(
		'<portlet:namespace />clickToChatGuestUsersAllowed'
	);

	if (<%= disabled %> || !clickToChatEnabled.checked) {
		Liferay.Util.toggleDisabled(clickToChatChatProviderAccountId, true);
		Liferay.Util.toggleDisabled(clickToChatChatProviderId, true);
		Liferay.Util.toggleDisabled(clickToChatGuestUsersAllowed, true);
	}

	<portlet:namespace />setVisibleClickToChatProviderLearnMessage(
		'<%= clickToChatChatProviderId %>',
		true
	);
</script>

No var outside function
Repeat is OK
Call the var *Element
Everything inside function