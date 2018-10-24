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

<%@ include file="/com.liferay.oauth2.provider.web/init.jsp" %>

<%
String registrationToken = (String)liferayPortletRequest.getAttribute(OAuth2AdminPortletWebKeys.REGISTRATION_TOKEN);

final String redirect = ParamUtil.getString(request, "redirect");

String headerTitle = LanguageUtil.get(request, "add-dynamic-client");

renderResponse.setTitle(headerTitle);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
%>

<div class="container-fluid container-fluid-max-xl container-view">
	<div class="sheet">
		<div class="row">
			<div class="col-lg-12">
				<aui:fieldset>
					<p>
						<liferay-ui:message key="please-use-following-information-to-create-a-new-oauth-2-application-using-oauth-2-dynamic-registration-protocol" />
					</p>

					<aui:input helpMessage="registration-endpoint-url-help" label="registration-endpoint-url" name="url" readonly="true" value='<%= PortalUtil.getPortalURL(request) + "/o/oauth2/register" %>' />

					<aui:input helpMessage="authorization-token-help" label="authorization-token" name="token" readonly="true" value="<%= registrationToken %>" />
				</aui:fieldset>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<aui:button-row>
					<aui:button cssClass="btn-lg" href="<%= portletDisplay.getURLBack() %>" type="cancel" />
				</aui:button-row>
			</div>
		</div>
	</div>
</div>