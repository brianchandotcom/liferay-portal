<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CookiesBannerDisplayContext cookiesBannerDisplayContext = (CookiesBannerDisplayContext)request.getAttribute(CookiesBannerWebKeys.COOKIES_BANNER_DISPLAY_CONTEXT);
%>

<clay:container-fluid
	cssClass="container-view"
>
	<clay:row>
		<clay:content-row
			noGutters="true"
			verticalAlign="center"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<span>
					<%= cookiesBannerDisplayContext.getContent(locale) %>

					<clay:link
						href="<%= cookiesBannerDisplayContext.getPrivacyPolicyLink() %>"
						label="<%= cookiesBannerDisplayContext.getLinkDisplayText(locale) %>"
					/>
				</span>
			</clay:content-col>

			<clay:content-col>
				<clay:button
					displayType="link"
					id='<%= liferayPortletResponse.getNamespace() + "configurationButton" %>'
					label='<%= LanguageUtil.get(request, "configuration") %>'
					small="<%= true %>"
				/>
			</clay:content-col>

			<clay:content-col>
				<clay:button
					displayType="secondary"
					id='<%= liferayPortletResponse.getNamespace() + "acceptAllButton" %>'
					label='<%= LanguageUtil.get(request, "accept-all") %>'
					small="<%= true %>"
				/>
			</clay:content-col>

			<c:if test="<%= cookiesBannerDisplayContext.isIncludeDeclineAllButton() %>">
				<clay:content-col>
					<clay:button
						displayType="secondary"
						id='<%= liferayPortletResponse.getNamespace() + "declineAllButton" %>'
						label='<%= LanguageUtil.get(request, "decline-all") %>'
						small="<%= true %>"
					/>
				</clay:content-col>
			</c:if>
		</clay:content-row>
	</clay:row>
</clay:container-fluid>

<liferay-frontend:component
	componentId="CookiesBanner"
	context="<%= cookiesBannerDisplayContext.getContext(locale) %>"
	module="cookies_banner/js/CookiesBanner"
/>