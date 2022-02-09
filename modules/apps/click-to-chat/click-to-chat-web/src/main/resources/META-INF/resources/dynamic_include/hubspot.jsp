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

<%@ include file="/dynamic_include/init.jsp" %>

<%
String errorMessage = null;
String identificationToken = null;

String[] parts = clickToChatChatProviderAccountId.split(StringPool.SLASH);

if (themeDisplay.isSignedIn() && (parts.length > 1)) {
	try {
		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.setBody(
			JSONUtil.put(
				"email", user.getEmailAddress()
			).put(
				"firstName", user.getFirstName()
			).put(
				"lastName", user.getLastName()
			).toJSONString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation("https://api.hubspot.com/conversations/v3/visitor-identification/tokens/create?hapikey=" + parts[1]);
		options.setPost(true);

		String responseJSON = HttpUtil.URLtoString(options);

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(responseJSON);

		errorMessage = HtmlUtil.escapeJS(responseJSONObject.getString("message"));

		identificationToken = responseJSONObject.getString("token");
	}
	catch (Exception exception) {
		if (_log.isWarnEnabled()) {
			_log.warn(exception);
		}
	}
}
%>

<script async defer id="hs-script-loader" src="//js-na1.hs-scripts.com/<%= parts[0] %>.js" type="text/javascript"></script>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() && (parts.length > 1) %>">
		<c:choose>
			<c:when test="<%= Validator.isNull(identificationToken) %>">
				<script>
					Liferay.Util.openToast({
						message:
							'<%= (errorMessage != null) ? errorMessage : LanguageUtil.get(resourceBundle, "unable-to-connect-to-hubspot") %>',
						type: 'danger',
					});
				</script>
			</c:when>
			<c:otherwise>
				<script type="text/javascript">
					window.hsConversationsSettings = {
						identificationEmail: '<%= user.getEmailAddress() %>',
						identificationToken: '<%= identificationToken %>',
					};

					window.HubSpotConversations.widget.load();
				</script>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>

<%!
private static final Log _log = LogFactoryUtil.getLog("com_liferay_click_to_chat_web.dynamic_include.hubspot_jsp");
%>