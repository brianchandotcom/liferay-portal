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

<%
long mfaEmailOTPFailedAttemptsRetryTimeout = GetterUtil.getLong(request.getAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP_FAILED_ATTEMPTS_RETRY_TIMEOUT));
long mfaEmailOTPSetAtTime = GetterUtil.getLong(request.getAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP_SET_AT_TIME));
%>

<c:if test="<%= mfaEmailOTPFailedAttemptsRetryTimeout > 0 %>">
	<div class="alert alert-danger">
		<liferay-ui:message arguments="<%= mfaEmailOTPFailedAttemptsRetryTimeout %>" key="maximum-allowed-attempts-error" translateArguments="<%= false %>" />
	</div>
</c:if>

<div id="<portlet:namespace />phaseOne">
	<div class="portlet-msg-info">
		<liferay-ui:message arguments="<%= GetterUtil.getString(request.getAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP_SEND_TO_ADDRESS_OBFUSCATED)) %>" key="press-the-button-below-to-obtain-your-one-time-password-it-will-be-sent-to-x" translateArguments="<%= false %>" />
	</div>

	<aui:button-row>
		<aui:button id="sendEmailButton" value="send" />
	</aui:button-row>
</div>

<div id="<portlet:namespace />messageContainer"></div>

<div id="<portlet:namespace />phaseTwo">
	<aui:input autocomplete="off" label="enter-the-otp-from-the-email" name="otp" showRequiredLabel="yes" />
</div>

<aui:button-row>
	<aui:button id="submitEmailButton" type="submit" value="submit" />
</aui:button-row>

<aui:script use="aui-base,aui-io-request">
	<liferay-portlet:resourceURL id="/mfa_email_otp_verify/send_mfa_email_otp" portletName="<%= MFAEmailOTPPortletKeys.MFA_EMAIL_OTP_VERIFY_PORTLET %>" var="sendEmailOTPURL" />

	var configuredResendDuration = <%= mfaEmailOTPConfiguration.resendEmailTimeout() %>;

	var failedAttemptsRetryTimeout = <%=mfaEmailOTPFailedAttemptsRetryTimeout %>;

	var countdown;

	var sendEmailButton = A.one('#<portlet:namespace />sendEmailButton');

	var submitEmailButton = A.one('#<portlet:namespace />submitEmailButton');

	var originalButtonText = sendEmailButton.text();

	var previousSetTime = <%= mfaEmailOTPSetAtTime %>;

	var elapsedTime = Math.floor((Date.now() - previousSetTime) / 1000);

	function <portlet:namespace />createCountdown(f, countdown, interval) {
		return setInterval(function () {
			--countdown;
			f(countdown);
		}, interval);
	}

	function <portlet:namespace />setResendCountdown(resendDuration) {
		if (resendDuration < 1) {
			sendEmailButton.text(originalButtonText);

			sendEmailButton.removeAttribute('disabled');

			clearInterval(countdown);
		}
		else {
			sendEmailButton.text(resendDuration);
		}
	}

	if (
		elapsedTime > 0 &&
		elapsedTime < configuredResendDuration &&
		previousSetTime > 0
	) {
		sendEmailButton.setAttribute('disabled', 'disabled');

		var resendDuration = configuredResendDuration - elapsedTime;

		countdown = <portlet:namespace />createCountdown(
			<portlet:namespace />setResendCountdown,
			resendDuration,
			1000
		);
	}

	if (failedAttemptsRetryTimeout > 0) {
		sendEmailButton.setAttribute('disabled', 'disabled');
		submitEmailButton.setAttribute('disabled', 'disabled');

		var originalSubmitButtonText = submitEmailButton.text();

		setInterval(function () {
			--failedAttemptsRetryTimeout;
			{
				if (failedAttemptsRetryTimeout < 1) {
					sendEmailButton.removeAttribute('disabled');

					submitEmailButton.text(originalSubmitButtonText);

					submitEmailButton.removeAttribute('disabled');

					clearInterval(failedAttemptsRetryTimeout);
				}
				else {
					submitEmailButton.text(failedAttemptsRetryTimeout);
				}
			}
		}, 1000);
	}

	A.one('#<portlet:namespace />sendEmailButton').on('click', function (event) {
		sendEmailButton.setAttribute('disabled', 'disabled');

		var resendDuration = <%= mfaEmailOTPConfiguration.resendEmailTimeout() %>;

		countdown = <portlet:namespace />createCountdown(
			<portlet:namespace />setResendCountdown,
			resendDuration,
			1000
		);

		var data = {
			p_auth: Liferay.authToken,
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
				failure: function (event, id, obj) {
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
				success: function (event, id, obj) {
					var messageContainer = A.one(
						'#<portlet:namespace />messageContainer'
					);

					messageContainer.html(
						'<span class="alert alert-success"><liferay-ui:message key="your-otp-has-been-sent-by-email" /></span>'
					);

					var phaseTwo = A.one('#<portlet:namespace />phaseTwo');
					phaseTwo.disabled = false;
				},
			},
		});
	});
</aui:script>