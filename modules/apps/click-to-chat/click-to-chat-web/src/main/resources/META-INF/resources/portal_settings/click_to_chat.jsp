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

<div class="row">
	<div class="col-md-12">
		<aui:input checked="<%= clickToChatConfiguration.enabled() %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name="enabled" type="toggle-switch" value="<%= clickToChatConfiguration.enabled() %>" />
	</div>
</div>

<div class="form-group row">
	<div class="col-md-12">
		<aui:select label="site-settings-strategy" name="siteSettingsStrategy" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeClickToChatSiteSettingsStrategy(event);" %>' required="<%= true %>" value="<%= clickToChatConfiguration.siteSettingsStrategy() %>">
			<aui:option label="" value="" />

			<%
			for (String clickToChatSiteSettingsStrategy : ClickToChatConstants.CLICK_TO_CHAT_SITE_SETTINGS_STRATEGIES) {
			%>

				<aui:option label='<%= "site-settings-strategy-" + clickToChatSiteSettingsStrategy %>' value="<%= clickToChatSiteSettingsStrategy %>" />

			<%
			}
			%>

		</aui:select>

		<label class="text-secondary">
			<liferay-ui:message key="site-settings-strategy-description" />
		</label>
	</div>
</div>

<div class="form-group row" id="<%= liferayPortletResponse.getNamespace() + "clickToChatChatProviders" %>">
	<div class="col-md-6">
		<aui:select label="chat-provider" name="chatProviderId" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeClickToChatChatProviderId(event);" %>' value="<%= clickToChatConfiguration.chatProviderId() %>">
			<aui:option label="" value="" />

			<%
			for (String clickToChatChatProviderId : ClickToChatConstants.CLICK_TO_CHAT_CHAT_PROVIDER_IDS) {
			%>

				<aui:option label='<%= "chat-provider-" + clickToChatChatProviderId %>' value="<%= clickToChatChatProviderId %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input checked="<%= clickToChatConfiguration.guestUsersAllowed() %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "guest-users-allowed") %>' labelCssClass="simple-toggle-switch" name="guestUsersAllowed" type="toggle-switch" value="<%= clickToChatConfiguration.guestUsersAllowed() %>" />
	</div>

	<div class="col-md-6">
		<aui:input label="chat-provider-account-id" name="chatProviderAccountId" type="text" value="<%= clickToChatConfiguration.chatProviderAccountId() %>" />

		<%
		for (String clickToChatChatProviderId : ClickToChatConstants.CLICK_TO_CHAT_CHAT_PROVIDER_IDS) {
		%>

			<div class="hide mb-2" id="<portlet:namespace />clickToChatChatProviderLearnMessage<%= clickToChatChatProviderId %>">
				<liferay-learn:message
					key='<%= "chat-provider-account-id-help-" + clickToChatChatProviderId %>'
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
			'<portlet:namespace />chatProviderId'
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

	function <portlet:namespace />onChangeClickToChatSiteSettingsStrategy(event) {
		var clickToChatChatProvidersElement = document.getElementById(
			'<portlet:namespace />clickToChatChatProviders'
		);

		var clickToChatSiteSettingsStrategyElement = document.getElementById(
			'<portlet:namespace />siteSettingsStrategy'
		);

		if (clickToChatSiteSettingsStrategyElement.value === 'always-override') {
			clickToChatChatProvidersElement.classList.add('hide');
		}
		else {
			clickToChatChatProvidersElement.classList.remove('hide');
		}
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

	<portlet:namespace />onChangeClickToChatSiteSettingsStrategy();
	<portlet:namespace />toggleClickToChatChatProviderLearnMessage(
		'<%= clickToChatConfiguration.chatProviderId() %>',
		true
	);
</script>