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

<div class="form-group row">
	<div class="col-md-6">

		<%
		String clickToChatChatProviderId = GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ID));
		%>

		<aui:select label="chat-provider" name="TypeSettingsProperties--clickToChatChatProviderId--" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeClickToChatChatProviderId(event);" %>' value="<%= clickToChatChatProviderId %>">
			<aui:option label="" value="" />

			<%
			for (String curClickToChatChatProviderId : ClickToChatConstants.CLICK_TO_CHAT_CHAT_PROVIDER_IDS) {
			%>

				<aui:option label='<%= "chat-provider-" + curClickToChatChatProviderId %>' value="<%= curClickToChatChatProviderId %>" />

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
		for (String curClickToChatChatProviderId : ClickToChatConstants.CLICK_TO_CHAT_CHAT_PROVIDER_IDS) {
		%>

			<div class="hide mb-2" id="<portlet:namespace />clickToChatChatProviderLearnMessage<%= curClickToChatChatProviderId %>">
				<liferay-learn:message
					key='<%= "chat-provider-account-id-help-" + curClickToChatChatProviderId %>'
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
		var clickToChatChatProviderIdElement = document.getElementById(
			'<portlet:namespace />clickToChatChatProviderId'
		);

		var clickToChatProviderIdOptions = clickToChatChatProviderIdElement.querySelectorAll(
			'option'
		);

		clickToChatProviderIdOptions.forEach((option) => {
			<portlet:namespace />toggleClickToChatChatProviderLearnMessage(
				option.value,
				false
			);
		});
	}

	function <portlet:namespace />onChangeClickToChatChatProviderId(event) {
		<portlet:namespace />hideUnselectedClickToChatProviderLearnMessages();
		<portlet:namespace />toggleClickToChatChatProviderLearnMessage(
			event.target.value,
			true
		);
	}

	function <portlet:namespace />onChangeClickToChatEnabled() {
		var clickToChatChatProviderAccountIdElement = document.getElementById(
			'<portlet:namespace />clickToChatChatProviderAccountId'
		);
		var clickToChatEnabledElement = document.getElementById(
			'<portlet:namespace />clickToChatEnabled'
		);

		Liferay.Util.toggleDisabled(
			clickToChatChatProviderAccountIdElement,
			!clickToChatEnabledElement.checked
		);

		var clickToChatChatProviderIdElement = document.getElementById(
			'<portlet:namespace />clickToChatChatProviderId'
		);

		Liferay.Util.toggleDisabled(
			clickToChatChatProviderIdElement,
			!clickToChatEnabledElement.checked
		);

		var clickToChatGuestUsersAllowedElement = document.getElementById(
			'<portlet:namespace />clickToChatGuestUsersAllowed'
		);

		Liferay.Util.toggleDisabled(
			clickToChatGuestUsersAllowedElement,
			!clickToChatEnabledElement.checked
		);
	}

	function <portlet:namespace />toggleClickToChatChatProviderLearnMessage(
		clickToChatChatProviderAccountId,
		visible
	) {
		var clickToChatChatProviderLearnMessageElement = document.getElementById(
			'<portlet:namespace />clickToChatChatProviderLearnMessage' +
				clickToChatChatProviderAccountId
		);

		if (clickToChatChatProviderLearnMessageElement) {
			if (visible) {
				return clickToChatChatProviderLearnMessageElement.classList.remove(
					'hide'
				);
			}

			clickToChatChatProviderLearnMessageElement.classList.add('hide');
		}
	}

	function <portlet:namespace />toggleClickToChatChatProviders() {
		var clickToChatEnabledElement = document.getElementById(
			'<portlet:namespace />clickToChatEnabled'
		);

		if (<%= disabled %> || !clickToChatEnabledElement.checked) {
			var clickToChatChatProviderAccountIdElement = document.getElementById(
				'<portlet:namespace />clickToChatChatProviderAccountId'
			);

			Liferay.Util.toggleDisabled(
				clickToChatChatProviderAccountIdElement,
				true
			);

			var clickToChatChatProviderIdElement = document.getElementById(
				'<portlet:namespace />clickToChatChatProviderId'
			);

			Liferay.Util.toggleDisabled(clickToChatChatProviderIdElement, true);

			var clickToChatGuestUsersAllowedElement = document.getElementById(
				'<portlet:namespace />clickToChatGuestUsersAllowed'
			);

			Liferay.Util.toggleDisabled(clickToChatGuestUsersAllowedElement, true);
		}
	}

	<portlet:namespace />toggleClickToChatChatProviderLearnMessage(
		'<%= clickToChatChatProviderId %>',
		true
	);
	<portlet:namespace />toggleClickToChatChatProviders();
</script>