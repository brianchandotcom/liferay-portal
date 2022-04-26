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

<%
CookiesBannerConfigurationDisplayContext cookiesBannerConfigurationDisplayContext = new CookiesBannerConfigurationDisplayContext(renderRequest, renderResponse);
%>

<clay:container-fluid
	cssClass="container-view p-md-4"
	id='<%= liferayPortletResponse.getNamespace() + "cookiesBannerConfigurationForm" %>'
>
	<clay:row>
		<clay:col
			cssClass="mb-3"
			size="12"
		>
			<p>
				<%= LanguageUtil.get(request, "cookies-banner-configuration-message") %>
			</p>
		</clay:col>

		<clay:col
			size="12"
		>
			<clay:content-row
				noGutters="true"
				verticalAlign="center"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h2><%= LanguageUtil.get(request, "strictly-necessary-cookies") %></h2>
				</clay:content-col>

				<clay:content-col>
					<span class="pr-2 text-primary"><%= LanguageUtil.get(request, "always-active") %></span>
				</clay:content-col>
			</clay:content-row>

			<clay:content-row
				cssClass="mb-3"
			>
				<p><%= LanguageUtil.get(request, "strictly-necessary-cookies-description") %></p>
			</clay:content-row>

			<clay:content-row
				noGutters="true"
				verticalAlign="center"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h2><%= LanguageUtil.get(request, "performance-cookies") %></h2>
				</clay:content-col>

				<clay:content-col>
					<label class="toggle-switch">
						<span class="toggle-switch-check-bar">
							<input class="toggle-switch-check" data-cookie-key="<%= CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE %>" disabled type="checkbox" />

							<span aria-hidden="true" class="toggle-switch-bar">
								<span class="toggle-switch-handle"></span>
							</span>
						</span>
					</label>
				</clay:content-col>
			</clay:content-row>

			<clay:content-row
				cssClass="mb-3"
			>
				<p><%= LanguageUtil.get(request, "performance-cookies-description") %></p>
			</clay:content-row>

			<clay:content-row
				noGutters="true"
				verticalAlign="center"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h2><%= LanguageUtil.get(request, "functional-cookies") %></h2>
				</clay:content-col>

				<clay:content-col>
					<label class="toggle-switch">
						<span class="toggle-switch-check-bar">
							<input class="toggle-switch-check" data-cookie-key="<%= CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL %>" disabled type="checkbox" />

							<span aria-hidden="true" class="toggle-switch-bar">
								<span class="toggle-switch-handle"></span>
							</span>
						</span>
					</label>
				</clay:content-col>
			</clay:content-row>

			<clay:content-row
				cssClass="mb-3"
			>
				<p><%= LanguageUtil.get(request, "functional-cookies-description") %></p>
			</clay:content-row>

			<clay:content-row
				noGutters="true"
				verticalAlign="center"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h2><%= LanguageUtil.get(request, "personalization-cookies") %></h2>
				</clay:content-col>

				<clay:content-col>
					<label class="toggle-switch">
						<span class="toggle-switch-check-bar">
							<input class="toggle-switch-check" data-cookie-key="<%= CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION %>" disabled type="checkbox" />

							<span aria-hidden="true" class="toggle-switch-bar">
								<span class="toggle-switch-handle"></span>
							</span>
						</span>
					</label>
				</clay:content-col>
			</clay:content-row>

			<clay:content-row
				cssClass="mb-3"
			>
				<p><%= LanguageUtil.get(request, "personalization-cookies-description") %></p>
			</clay:content-row>
		</clay:col>
	</clay:row>

	<c:if test="<%= cookiesBannerConfigurationDisplayContext.isShowButtons() %>">
		<clay:row
			cssClass="d-flex justify-content-end"
		>
			<clay:content-row
				noGutters="true"
				verticalAlign="center"
			>
				<clay:content-col>
					<clay:button
						displayType="secondary"
						id='<%= liferayPortletResponse.getNamespace() + "confirmButton" %>'
						label='<%= LanguageUtil.get(request, "confirm") %>'
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

				<clay:content-col>
					<clay:button
						displayType="secondary"
						id='<%= liferayPortletResponse.getNamespace() + "declineAllButton" %>'
						label='<%= LanguageUtil.get(request, "decline-all") %>'
						small="<%= true %>"
					/>
				</clay:content-col>
			</clay:content-row>
		</clay:row>
	</c:if>
</clay:container-fluid>

<liferay-frontend:component
	componentId="CookiesBannerConfiguration"
	context='<%=
		HashMapBuilder.<String, Object>put(
			"optionalCookies", cookiesBannerConfigurationDisplayContext.getOptionalCookies()
		).put(
			"requiredCookies", cookiesBannerConfigurationDisplayContext.getRequiredCookies()
		).put(
			"showButtons", cookiesBannerConfigurationDisplayContext.isShowButtons()
		).build()
	%>'
	module="cookies_banner_configuration/js/CookiesBannerConfiguration"
/>