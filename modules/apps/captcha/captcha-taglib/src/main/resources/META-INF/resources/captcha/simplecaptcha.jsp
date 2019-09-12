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

<%@ include file="/captcha/init.jsp" %>

<%
String url = (String)request.getAttribute("liferay-captcha:captcha:url");
%>

<c:if test="<%= captchaEnabled %>">
	<div class="taglib-captcha">
		<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="text-to-identify" />" class="captcha" id="<portlet:namespace />captcha" src="<%= HtmlUtil.escapeAttribute(HttpUtil.addParameter(url, "t", String.valueOf(System.currentTimeMillis()))) %>" />

		<liferay-ui:icon
			cssClass="refresh"
			icon="refresh"
			id="refreshCaptcha"
			label="<%= false %>"
			localizeMessage="<%= true %>"
			markupView="lexicon"
			message="refresh-captcha"
			url="javascript:;"
		/>

		<aui:input ignoreRequestValue="<%= true %>" label="text-verification" name="captchaText" size="10" type="text" value="">
			<aui:validator name="required" />
		</aui:input>
	</div>

	<aui:script>
		var refreshCaptcha = document.getElementById(
			'<portlet:namespace />refreshCaptcha'
		);

		if (refreshCaptcha) {
			refreshCaptcha.addEventListener('click', function() {
				var url = Liferay.Util.addParams(
					't=' + Date.now(),
					'<%= HtmlUtil.escapeJS(url) %>'
				);

				var captcha = document.getElementById('<portlet:namespace />captcha');

				if (captcha) {
					captcha.setAttribute('src', url);
				}
			});
		}
	</aui:script>
</c:if>