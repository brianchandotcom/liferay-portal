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
	<#else>
		<div class="speedwell-user-nav__sign-in">
			<a class="main-link" href="${sign_in_url}">
				<div class="main-link__label">${sign_in_text}</div>
			</a>
		</div>
	</#if>
</div>