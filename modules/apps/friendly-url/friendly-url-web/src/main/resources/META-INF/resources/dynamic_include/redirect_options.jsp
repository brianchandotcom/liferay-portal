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

<liferay-util:buffer
	var="alertMessage"
>
	<div>
		<div class="d-block">
			<%= LanguageUtil.get(request, "this-page-has-been-loaded-avoiding-any-redirections") %>
		</div>

		<aui:a cssClass="d-block" href='<%= PortalUtil.getLayoutFullURL(layout, themeDisplay) + "?redirect=" + URLCodec.encodeURL(themeDisplay.getURLCurrent()) + "&showRedirectOptionsMessage=false" %>' target="_blank">
			<%= LanguageUtil.get(request, "continue-to-the-redirection") %>
		</aui:a>
	</div>
</liferay-util:buffer>

<aui:script>
	Liferay.Util.openToast({
		message: '<%= HtmlUtil.escapeJS(alertMessage) %>',
		onClose: function (data) {
			if (data.event) {
				Liferay.Util.Session.set(
					'com.liferay.friendly.url.web_ignoreRedirectOptions',
					true,
					{
						useHttpSession: true,
					}
				);
			}
		},
		type: 'info',
	});
</aui:script>
