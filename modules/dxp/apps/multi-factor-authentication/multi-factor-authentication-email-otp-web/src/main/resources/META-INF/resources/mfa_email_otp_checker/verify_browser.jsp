<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />phaseOne">
	<div class="portlet-msg-info">
		<liferay-ui:message key="your-one-time-password-will-be-sent-to-your-email-address" />
	</div>

	<aui:button-row>
		<aui:button id="sendEmailButton" value="send" />
	</aui:button-row>
</div>

<div id="<portlet:namespace />messageContainer"></div>

<div id="<portlet:namespace />phaseTwo">
	<aui:input label="please-enter-the-otp-from-the-email" name="otp" showRequiredLabel="yes" />
</div>

<aui:script use="aui-base,aui-io-request">
	<liferay-portlet:resourceURL id="/mfa_email_otp_verify/send_mfa_email_otp" portletName="<%= MFAEmailOTPPortletKeys.MFA_EMAIL_OTP_VERIFY_PORTLET %>" var="sendEmailOTPURL" />

	A.one('#<portlet:namespace />sendEmailButton').on('click', function(event) {
		var sendEmailButton = A.one('#<portlet:namespace />sendEmailButton');

		sendEmailButton.setAttribute('disabled', 'disabled');

		var buttonText = sendEmailButton.text();

		var resendDuration = <%= mfaEmailOTPConfiguration.resendEmailTimeout() %>;

		var interval = setInterval(function() {
			if (resendDuration === 0) {
				sendEmailButton.text(buttonText);

				sendEmailButton.removeAttribute('disabled');

				clearInterval(interval);
			}
			else {
				sendEmailButton.text(--resendDuration);
			}
		}, 1000);

		var data = {
			p_auth: Liferay.authToken
		};

		var setupEmail = A.one('#<portlet:namespace />setupEmail');

		if (setupEmail) {
			data['email'] = setupEmail.val();
		}

		var sendEmailOTPURL = '<%= HtmlUtil.escapeJS(sendEmailOTPURL) %>';

		A.io.request(sendEmailOTPURL, {
			dataType: 'JSON',
			data: data,
			method: 'POST',
			on: {
				failure: function(event, id, obj) {
					var messageContainer = A.one(
						'#<portlet:namespace />messageContainer'
					);

					messageContainer.html(
						'<span class="alert alert-danger"><liferay-ui:message key="failed-to-send-email" /></span>'
					);

					sendEmailButton.text(buttonText);
					sendEmailButton.removeAttribute('disabled');

					clearInterval(interval);
				},
				success: function(event, id, obj) {
					var messageContainer = A.one(
						'#<portlet:namespace />messageContainer'
					);

					messageContainer.html(
						'<span class="alert alert-success"><liferay-ui:message key="your-otp-has-been-sent-by-email" /></span>'
					);

					var phaseTwo = A.one('#<portlet:namespace />phaseTwo');
					phaseTwo.disabled = false;
				}
			}
		});
	});
</aui:script>