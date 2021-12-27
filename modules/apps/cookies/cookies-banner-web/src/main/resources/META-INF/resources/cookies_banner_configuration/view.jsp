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

<clay:container-fluid
	cssClass="container-view p-md-4"
>
	<clay:row>
		<clay:col
			cssClass="mb-3"
			size="12"
		>
			<p>When you visit any web site, it may store or retrieve information on your browser, mostly in the form of cookies. This information might be about you, your preferences or your device and is mostly used to make the site work as you expect it to. The information does not usually directly identify you, but it can give you a more personalized web experience.</p>
			<p>You can choose not to allow some types of cookies. Click on the different category headings to find out more and change our default settings. However, blocking some types of cookies may impact your experience of the site and the services we are able to offer. Visit our Cookie Policy for more information.</p>
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
					<h2>Strictly Necessary Cookies</h2>
				</clay:content-col>

				<clay:content-col>
					<clay:link
						href="#"
						label="Always Active"
					/>
				</clay:content-col>
			</clay:content-row>

			<clay:content-row
				cssClass="mb-3"
			>
				<p>These cookies are necessary for the website to function and cannot be switched off in our systems. They are usually only set in response to actions made by you which amount to a request for services, such as setting your privacy preferences, logging in or filling in forms. You can set your browser to block or alert you about these cookies, but some parts of the site will not then work. These cookies do not store any personally identifiable information.</p>
			</clay:content-row>

			<clay:content-row
				noGutters="true"
				verticalAlign="center"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h2>Performance Cookies</h2>
				</clay:content-col>

				<clay:content-col>
					<label class="toggle-switch">
						<span class="toggle-switch-check-bar">
							<input class="toggle-switch-check toggle-switch-check-performance" disabled type="checkbox" />

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
				<p>These cookies allow us to count visits and traffic sources, so we can measure and improve the performance of our site. They help us know which pages are the most and least popular and see how visitors move around the site. All information these cookies collect is aggregated and therefore anonymous. If you do not allow these cookies, we will not know when you have visited our site.</p>
			</clay:content-row>

			<clay:content-row
				noGutters="true"
				verticalAlign="center"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h2>Functional Cookies</h2>
				</clay:content-col>

				<clay:content-col>
					<label class="toggle-switch">
						<span class="toggle-switch-check-bar">
							<input class="toggle-switch-check toggle-switch-check-functional" disabled type="checkbox" />

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
				<p>These cookies enable the website to provide enhanced functionality and personalisation. They may be set by us or by third party providers whose services we have added to our pages. If you do not allow these cookies then some or all of these services may not function properly.</p>
			</clay:content-row>

			<clay:content-row
				noGutters="true"
				verticalAlign="center"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h2>Personalization Cookies</h2>
				</clay:content-col>

				<clay:content-col>
					<label class="toggle-switch">
						<span class="toggle-switch-check-bar">
							<input class="toggle-switch-check toggle-switch-check-personalization" disabled type="checkbox" />

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
				<p>These cookies may be set through our site by our advertising partners. They may be used by those companies to build a profile of your interests and show you relevant adverts on other sites. They do not store directly personal information, but are based on uniquely identifying your browser and internet device. If you do not allow these cookies, you will experience less targeted advertising.</p>
			</clay:content-row>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<liferay-frontend:component
	componentId="CookiesBannerConfiguration"
	module="cookies_banner_configuration/js/CookiesBannerConfiguration"
/>