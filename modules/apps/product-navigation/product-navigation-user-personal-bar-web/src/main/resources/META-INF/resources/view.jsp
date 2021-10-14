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

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<span class="user-avatar-link">
			<liferay-product-navigation:personal-menu
				size="lg"
				user="<%= user %>"
			/>

			<%
			int notificationsCount = GetterUtil.getInteger(request.getAttribute(ProductNavigationUserPersonalBarWebKeys.NOTIFICATIONS_COUNT));
			%>

			<c:if test="<%= notificationsCount > 0 %>">

				<%
				String notificaitonsPortletId = PortletProviderUtil.getPortletId(UserNotificationEvent.class.getName(), PortletProvider.Action.VIEW);

				String notificationsURL = PersonalApplicationURLUtil.getPersonalApplicationURL(request, notificaitonsPortletId);
				%>

				<aui:a href="<%= (notificationsURL != null) ? notificationsURL : null %>">
					<clay:badge
						cssClass="panel-notifications-count"
						displayType="danger"
						label="<%= String.valueOf(notificationsCount) %>"
					/>
				</aui:a>
			</c:if>
		</span>
	</c:when>
	<c:when test="<%= themeDisplay.isShowSignInIcon() %>">
		<span class="sign-in text-default" role="presentation">
			<aui:icon
				cssClass="sign-in text-default"
				data='<%=
					HashMapBuilder.<String, Object>put(
						"redirect", String.valueOf(PortalUtil.isLoginRedirectRequired(request))
					).build()
				%>'
				image="user"
				label="sign-in"
				markupView="lexicon"
				url="<%= themeDisplay.getURLSignIn() %>"
			/>
		</span>

		<aui:script sandbox="<%= true %>">
			var signInLink = document.querySelector('.sign-in > a');

			if (signInLink && signInLink.dataset.redirect === 'false') {
				var signInURL = '<%= themeDisplay.getURLSignIn() %>';

				var modalSignInURL = Liferay.Util.addParams(
					'windowState=exclusive',
					signInURL
				);

				var setModalContent = function (html) {
					var modalBody = document.querySelector('.liferay-modal-body');

					if (modalBody) {
						var fragment = document
							.createRange()
							.createContextualFragment(html);

						modalBody.innerHTML = '';

						modalBody.appendChild(fragment);
					}
				};

				var loading = false;
				var redirect = false;
				var html = '';
				var modalOpen = false;

				var fetchModalSignIn = function () {
					if (loading || html) {
						return;
					}

					loading = true;

					Liferay.Util.fetch(modalSignInURL)
						.then((response) => {
							return response.text();
						})
						.then((response) => {
							if (!loading) {
								return;
							}

							loading = false;

							if (!response) {
								redirect = true;

								return;
							}

							html = response;

							if (modalOpen) {
								setModalContent(response);
							}
						})
						.catch(() => {
							redirect = true;
						});
				};

				signInLink.addEventListener('mouseover', fetchModalSignIn);
				signInLink.addEventListener('focus', fetchModalSignIn);

				signInLink.addEventListener('click', (event) => {
					event.preventDefault();

					if (redirect) {
						Liferay.Util.navigate(signInURL);

						return;
					}

					Liferay.Util.openModal({
						bodyHTML: html ? html : '<span class="loading-animation">',
						containerProps: {
							className: '',
						},
						height: '400px',
						onClose: function () {
							loading = false;
							redirect = false;
							html = '';
							modalOpen = false;
						},
						onOpen: function () {
							modalOpen = true;

							if (html && document.querySelector('.loading-animation')) {
								setModalContent(html);
							}
						},
						size: 'md',
						title: '<liferay-ui:message key="sign-in" />',
					});
				});
			}
		</aui:script>
	</c:when>
</c:choose>