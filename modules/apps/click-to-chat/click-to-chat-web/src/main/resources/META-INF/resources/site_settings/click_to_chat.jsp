<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			<liferay-ui:icon-help message='<%= LanguageUtil.format(resourceBundle, "site-settings-strategy-description", "click-to-chat") %>' />
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

		<aui:input checked="<%= clickToChatEnabled %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name="enabled" type="toggle-switch" value="<%= clickToChatEnabled %>" />
	</div>
</div>

<div class="form-group row">
	<div class="col-md-6">
		<aui:select disabled="<%= disabled %>" id="chatProviderId" label="chat-provider" name="chatProviderId" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeClickToChatChatProviderId(event);" %>' value="<%= GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ID)) %>">
			<aui:option label="" value="" />

			<%
			for (String curClickToChatChatProviderId : ClickToChatConstants.CHAT_PROVIDER_IDS) {
			%>

				<aui:option label='<%= "chat-provider-" + curClickToChatChatProviderId %>' value="<%= curClickToChatChatProviderId %>" />

			<%
			}
			%>

		</aui:select>
	</div>

	<div class="col-md-6">
		<aui:input disabled="<%= disabled %>" label="chat-provider-account-id" name="chatProviderAccountId" type="text" value="<%= GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ACCOUNT_ID)) %>" />

		<%
		for (String curClickToChatChatProviderId : ClickToChatConstants.CHAT_PROVIDER_IDS) {
		%>

			<div class="hide mb-2" id="<portlet:namespace />clickToChatChatProviderLearnMessage<%= curClickToChatChatProviderId %>">
				<liferay-learn:message
					key='<%= "chat-provider-account-id-" + curClickToChatChatProviderId %>'
					resource="click-to-chat-web"
				/>
			</div>

		<%
		}
		%>

	</div>
</div>

<div class="form-group hide row" id="<portlet:namespace />zendeskWebWidgetFields">
	<div class="col-md-6">
		<aui:input disabled="<%= disabled %>" id="chatProviderKeyId" label="chat-provider-key-id" name="chatProviderKeyId" type="text" value="<%= clickToChatConfiguration.chatProviderKeyId() %>" />
	</div>

	<div class="col-md-6">
		<aui:input disabled="<%= disabled %>" id="chatProviderSecretKey" label="chat-provider-secret-key" name="chatProviderSecretKey" type="text" value="<%= clickToChatConfiguration.chatProviderSecretKey() %>" />
	</div>
</div>

<div class="form-group row">
	<div class="col-md-6">

		<%
		boolean clickToChatGuestUsersAllowed = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GUEST_USERS_ALLOWED));
		%>

		<aui:input checked="<%= clickToChatGuestUsersAllowed %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "guest-users-allowed") %>' labelCssClass="simple-toggle-switch" name="guestUsersAllowed" type="toggle-switch" value="<%= clickToChatGuestUsersAllowed %>" />

		<%
		boolean clickToChatHideInControlPanel = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_HIDE_IN_CONTROL_PANEL));
		%>

		<aui:input checked="<%= clickToChatHideInControlPanel %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "hide-in-control-panel") %>' labelCssClass="simple-toggle-switch" name="hideInControlPanel" type="toggle-switch" value="<%= clickToChatHideInControlPanel %>" />
	</div>
</div>

<script>
	document.addEventListener('DOMContentLoaded', () => {
		<portlet:namespace />toggleClickToChatZendeskWebWidgetFields();
	});

	function <portlet:namespace />toggleClickToChatZendeskWebWidgetFields() {
		var zendeskWebWidgetFieldsElement = document.getElementById(
			'<portlet:namespace />zendeskWebWidgetFields'
		);

		var selectedChat = document.getElementById(
			'<portlet:namespace />chatProviderId'
		);

		if (
			selectedChat.value ===
			'<%= ClickToChatConstants.CHAT_PROVIDER_ID_ZENDESK_WEB_WIDGET %>'
		) {
			zendeskWebWidgetFieldsElement.classList.remove('hide');
		}
		else {
			document.getElementById(
				'<portlet:namespace />chatProviderKeyId'
			).value = '';

			document.getElementById(
				'<portlet:namespace />chatProviderSecretKey'
			).value = '';

			zendeskWebWidgetFieldsElement.classList.add('hide');
		}
	}

	function <portlet:namespace />hideUnselectedClickToChatProviderLearnMessages() {
		<portlet:namespace />toggleClickToChatZendeskWebWidgetFields();

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

	<portlet:namespace />toggleClickToChatChatProviderLearnMessage(
		document.getElementById('<portlet:namespace />chatProviderId').value,
		true
	);
</script>