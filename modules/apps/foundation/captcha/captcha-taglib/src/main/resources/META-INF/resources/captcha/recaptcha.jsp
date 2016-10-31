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

<%@ include file="/html/taglib/ui/captcha/init.jsp" %>

<c:if test="<%= captchaEnabled %>">
	<script src="<%= PropsValues.CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT %>?hl=<%= locale.getLanguage() %>" type="text/javascript"></script>

	<div class="g-recaptcha" data-sitekey="<%= PrefsPropsUtil.getString(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC, PropsValues.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC) %>"></div>

	<noscript>
		<div style="height: 525px; width: 302px;">
			<div style="height: 525px; position: relative; width: 302px;">
				<div style="height: 525px; position: absolute; width: 302px;">
					<iframe frameborder="0" scrolling="no" src="<%= PropsValues.CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT %><%= PrefsPropsUtil.getString(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC, PropsValues.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC) %>" style="border-style: none; height: 525px; width: 302px;"></iframe>
				</div>

				<div style="background: #F9F9F9; border-radius: 3px; border: 1px solid #C1C1C1; bottom: 25px; height: 60px; left: 0; margin: 0; padding: 0; position: absolute; right: 25px; width: 300px;">
					<textarea class="g-recaptcha-response" id="g-recaptcha-response" name="g-recaptcha-response" style="border: 1px solid #C1C1C1; height: 40px; margin: 10px 25px; padding: 0; resize: none; width: 250px;"></textarea>
				</div>
			</div>
		</div>
	</noscript>
</c:if>