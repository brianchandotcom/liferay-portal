<div class="speedwell-user-nav" tabindex="0">
	<#if is_signed_in>
		<#assign
			userImageCssClass = ""
		/>

		<#if notification_count gt 0>
			<#assign
				userImageCssClass = "has-notification"
			/>
		</#if>

		<div class="speedwell-user-nav__avatar ${userImageCssClass}">
			<@liferay_ui["user-portrait"] user=user />

			<div class="speedwell-user-nav__name">${htmlUtil.escape(user_name)}</div>
		</div>

		<div class="speedwell-user-nav__menu">
			<#if show_my_account>
				<a class="main-link main-link--sub" href="${my_account_url}">
					<div class="main-link__label">${my_account_text}</div>
				</a>

				<a class="main-link main-link--sub" href="${wishlistUrl}">
					<div class="main-link__label">${wish_lists_text}</div>
				</a>

				<a class="main-link main-link--sub" href="${notification_url}">
					<div class="main-link__label">
						${notifications_text}:
						<div class="speedwell-notification-badge">${notification_count}</div>
					</div>
				</a>
			</#if>

			<#if show_sign_out>
				<a class="main-link main-link--sub" href="${sign_out_url}">
					<div class="main-link__label">${sign_out_text}</div>
				</a>
			</#if>
		</div>
	<#elseif show_sign_in>
		<div class="speedwell-user-nav__sign-in">
			<a class="main-link sign-in" href="${sign_in_url}">
				<div class="main-link__label">${sign_in_text}</div>
			</a>

			<script ${nonceAttribute}>
				const setGuestOrderEnabled = ({guestOrderEnabled}) => {
					const signInLink = document.querySelector('.speedwell-user-nav__sign-in .sign-in');

					if (signInLink && guestOrderEnabled) {
						signInLink.href = '#';

						const performGuestSignIn = (event) => {
							event.preventDefault();

							window.Liferay.fire('perform-sign-in');

							return false;
						};

						signInLink.addEventListener('click', performGuestSignIn);
					}

					window.Liferay.detach('guest-order-enabled', setGuestOrderEnabled);
				};

				window.Liferay.on('guest-order-enabled', setGuestOrderEnabled);
			</script>
		</div>
	</#if>
</div>