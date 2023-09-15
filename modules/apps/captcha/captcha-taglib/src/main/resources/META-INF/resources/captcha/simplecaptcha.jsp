<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/captcha/init.jsp" %>

<%
String errorMessage = (String)request.getAttribute("liferay-captcha:captcha:errorMessage");
String url = (String)request.getAttribute("liferay-captcha:captcha:url");
%>

<c:if test="<%= captchaEnabled %>">

	<%
	String cssClass = "my-3 taglib-captcha";

	if (Validator.isNotNull(errorMessage)) {
		cssClass += " has-error";
	}
	%>

	<div class="<%= cssClass %>">
		<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="text-to-identify" />" class="captcha d-inline-block mb-2" id="<portlet:namespace />captcha" src="<%= HtmlUtil.escapeAttribute(HttpComponentsUtil.addParameter(url, "t", String.valueOf(System.currentTimeMillis()))) %>" />

		<liferay-ui:icon
			cssClass="align-top d-inline-block refresh"
			icon="reload"
			id="refreshCaptcha"
			label="<%= false %>"
			localizeMessage="<%= true %>"
			markupView="lexicon"
			message="refresh-captcha"
			url="javascript:void(0);"
		/>

		<div class="form-group input-text-wrapper">
			<label class="control-label" id="<portlet:namespace />captchaLabel">
				<liferay-ui:message key="text-verification" />
			</label>

			<input
				aria-labelledby="<portlet:namespace />captchaLabel <portlet:namespace />captchaError" class="form-control" name="<portlet:namespace />captchaText" required="<%= true %>" size="10" type="text" value=""
			/>

			<c:if test="<%= Validator.isNotNull(errorMessage) %>">
				<p class="font-weight-semi-bold mt-1 text-danger" id="<portlet:namespace />captchaError">
					<clay:icon
						symbol="info-circle"
					/>

					<span><%= errorMessage %></span>
				</p>
			</c:if>
		</div>
	</div>

	<aui:script>
		var hasEventAttached = false;

		function attachEvent() {
			var refreshCaptcha = document.getElementById(
				'<portlet:namespace />refreshCaptcha'
			);

			if (refreshCaptcha && !hasEventAttached) {
				hasEventAttached = true;
				refreshCaptcha.addEventListener('click', () => {
					var url = Liferay.Util.addParams(
						't=' + Date.now(),
						'<%= HtmlUtil.escapeJS(url) %>'
					);

					var captcha = document.getElementById(
						'<portlet:namespace />captcha'
					);

					if (captcha) {
						captcha.setAttribute('src', url);
					}
				});
			}
		}

		attachEvent();

		Liferay.on('<portlet:namespace />simplecaptcha_attachEvent', attachEvent);
	</aui:script>
</c:if>