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

<liferay-ui:error key="reCaptchaPrivateKey" message="the-recaptcha-private-key-is-not-valid" />
<liferay-ui:error key="reCaptchaPublicKey" message="the-recaptcha-public-key-is-not-valid" />

<aui:fieldset>
	<aui:input helpMessage='<%= LanguageUtil.format(request, "recaptcha-help", "https://www.google.com/recaptcha/admin/create", false) %>' label="enable-recaptcha" name="reCaptchaEnabled" type="checkbox" value="<%= PrefsPropsUtil.getString(PropsKeys.CAPTCHA_ENGINE_IMPL, PropsValues.CAPTCHA_ENGINE_IMPL).equals(ReCaptchaImpl.class.getName()) %>" />

	<aui:input cssClass="lfr-input-text-container" label="recaptcha-public-key" name="reCaptchaPublicKey" type="text" value="<%= PrefsPropsUtil.getString(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC, PropsValues.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC) %>" />

	<aui:input cssClass="lfr-input-text-container" label="recaptcha-private-key" name="reCaptchaPrivateKey" type="text" value="<%= PrefsPropsUtil.getString(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE, PropsValues.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE) %>" />
</aui:fieldset>

<aui:button-row>
	<aui:button cssClass="btn-lg save-server-button" data-cmd="updateCaptcha" value="save" />
</aui:button-row>