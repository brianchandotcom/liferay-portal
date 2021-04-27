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

		<%
		boolean clickToChatEnabled = clickToChatConfiguration.enabled();
		%>

		<aui:input checked="<%= clickToChatEnabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name="settings--clickToChatEnabled--" type="toggle-switch" value="<%= clickToChatEnabled %>" />
	</div>
</div>

<div class="form-group row">
	<div class="col-md-12">
		<aui:select label="site-settings-strategy" name="settings--clickToChatSiteSettingsStrategy--" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeClickToChatSiteSettingsStrategy(event);" %>' required="<%= true %>" value="<%= clickToChatConfiguration.siteSettingsStrategy() %>">
			<aui:option label="" value="" />

			<%
			for (String curClickToChatSiteSettingsStrategy : ClickToChatConstants.CLICK_TO_CHAT_SITE_SETTINGS_STRATEGIES) {
			%>

				<aui:option label='<%= "site-settings-strategy-" + curClickToChatSiteSettingsStrategy %>' value="<%= curClickToChatSiteSettingsStrategy %>" />

			<%
			}
			%>

		</aui:select>

		<label class="control-label">
			<liferay-ui:message key="site-settings-strategy-description" />
		</label>
	</div>
</div>

<div class="row" id="<%= liferayPortletResponse.getNamespace() + "clickToChatDivProviderFields" %>">
	<div class="col-md-6">

		<%
		String clickToChatChatProviderId = clickToChatConfiguration.chatProviderId();
		%>

		<aui:select label="chat-provider" name="settings--clickToChatChatProviderId--" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeClickToChatChatProviderId(event);" %>' value="<%= clickToChatChatProviderId %>">
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
		boolean clickToChatGuestUsersAllowed = clickToChatConfiguration.guestUsersAllowed();
		%>

		<aui:input checked="<%= clickToChatGuestUsersAllowed %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "guest-users-allowed") %>' labelCssClass="simple-toggle-switch" name="settings--clickToChatGuestUsersAllowed--" type="toggle-switch" value="<%= clickToChatGuestUsersAllowed %>" />
	</div>

	<div class="col-md-6">
		<aui:input label="chat-provider-account-id" name="settings--clickToChatChatProviderAccountId--" type="text" value="<%= clickToChatConfiguration.chatProviderAccountId() %>" />

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

	function <portlet:namespace />onChangeClickToChatChatProviderId(event) {
		<portlet:namespace />hideUnselectedClickToChatProviderLearnMessages();

		<portlet:namespace />setVisibleClickToChatProviderLearnMessage(
			event.target.value,
			true
		);
	}

	function <portlet:namespace />onChangeClickToChatSiteSettingsStrategy(event) {
		if (clickToChatSiteSettingsStrategy.value === 'always-override') {
			divProviderFields.classList.add('hide');
		}
		else {
			divProviderFields.classList.remove('hide');
		}
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

	var clickToChatSiteSettingsStrategy = document.getElementById(
		'<portlet:namespace />clickToChatSiteSettingsStrategy'
	);

	var divProviderFields = document.getElementById(
		'<portlet:namespace />clickToChatDivProviderFields'
	);

	<portlet:namespace />onChangeClickToChatSiteSettingsStrategy();

	<portlet:namespace />setVisibleClickToChatProviderLearnMessage(
		'<%= clickToChatChatProviderId %>',
		true
	);
</script>