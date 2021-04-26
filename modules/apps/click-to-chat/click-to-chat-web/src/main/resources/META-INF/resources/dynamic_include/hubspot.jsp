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
String identificationToken = null;
Http.Response httpResponse = null;

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

		String jsonResponse = HttpUtil.URLtoString(options);

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(jsonResponse);

		httpResponse = options.getResponse();

		identificationToken = responseJSONObject.getString("token");
	}
	catch (Exception exception) {
		Log log = LogFactoryUtil.getLog("com_liferay_click_to_chat_web.hubspot_jsp");

		if (log.isErrorEnabled()) {
			log.error(exception, exception);
		}
	}
}
%>

<script async defer id="hs-script-loader" src="//js-na1.hs-scripts.com/<%= parts[0] %>.js" type="text/javascript"></script>

<c:choose>
	<c:when test="<%= (httpResponse == null) || (httpResponse.getResponseCode() >= 400) %>">

		<%
		String message = "your-request-failed-to-complete";

		if (httpResponse == null) {
			message = "invalid-chat-authentication";
		}
		else if (httpResponse.getResponseCode() == 401) {
			message = "not-authorized-chat-request";
		}
		else if (httpResponse.getResponseCode() == 403) {
			message = "no-access-chat-request";
		}
		%>

		<script>
			Liferay.Util.openToast({
				message: '<%= LanguageUtil.get(resourceBundle, message) %>',
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