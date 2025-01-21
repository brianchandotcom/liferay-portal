<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/captcha/init.jsp" %>

<%
String errorMessage = (String)request.getAttribute("liferay-captcha:captcha:errorMessage");
%>

<c:if test="<%= captchaEnabled %>">
	<aui:script src='<%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaScriptURL()) + "?hl=" + HtmlUtil.escapeAttribute(locale.getLanguage()) %>' type="text/javascript"></aui:script>

	<label class="hidden" for="g-recaptcha-response">Google Recaptcha</label>

	<div class="g-recaptcha" data-sitekey="<%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaPublicKey()) %>"></div>

	<noscript>
		<div class="recaptcha-container">
			<div class="recaptcha-inner">
				<div class="recaptcha-no-script">
					<iframe class="recaptcha-no-script-iframe" frameborder="0" scrolling="no" src="<%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaNoScriptURL()) %><%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaPublicKey()) %>"></iframe>
				</div>

				<div class="recaptcha-wrapper">
					<textarea aria-labelledby="<portlet:namespace />g-recaptcha-response-error" class="g-recaptcha-response recaptcha-textarea" id="g-recaptcha-response" name="g-recaptcha-response"></textarea>
				</div>
			</div>
		</div>
	</noscript>

	<c:if test="<%= Validator.isNotNull(errorMessage) %>">
		<p class="font-weight-semi-bold mt-1 text-danger" id="<portlet:namespace />g-recaptcha-response-error">
			<clay:icon
				symbol="info-circle"
			/>

			<span><%= errorMessage %></span>
		</p>
	</c:if>
</c:if>