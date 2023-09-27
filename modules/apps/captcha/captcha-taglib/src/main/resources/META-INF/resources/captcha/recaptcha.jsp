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
	<script src="<%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaScriptURL()) %>?hl=<%= HtmlUtil.escapeAttribute(locale.getLanguage()) %>" type="text/javascript"></script>

	<div class="g-recaptcha" data-sitekey="<%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaPublicKey()) %>"></div>

	<noscript>
		<div style="height: 525px; width: 302px;">
			<div style="height: 525px; position: relative; width: 302px;">
				<div style="height: 525px; position: absolute; width: 302px;">
					<iframe frameborder="0" scrolling="no" src="<%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaNoScriptURL()) %><%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaPublicKey()) %>" style="border-style: none; height: 525px; width: 302px;"></iframe>
				</div>

				<div style="background: #F9F9F9; border: 1px solid #C1C1C1; border-radius: 3px; bottom: 25px; height: 60px; left: 0; margin: 0; padding: 0; position: absolute; right: 25px; width: 300px;">
					<textarea aria-labelledby="<portlet:namespace />g-recaptcha-response-error" class="g-recaptcha-response" id="g-recaptcha-response" name="g-recaptcha-response" style="border: 1px solid #C1C1C1; height: 40px; margin: 10px 25px; padding: 0; resize: none; width: 250px;"></textarea>
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