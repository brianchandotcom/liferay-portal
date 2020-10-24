<div class="minium-user-nav" tabindex="0">
	<#if is_signed_in>
		<div class="minium-user-nav__menu">
			<a class="main-link main-link--sub" href="${userManagementUrl}">
				<div class="main-link__label">${my_profile_text}</div>
			</a>

			<a class="main-link main-link--sub" href="${wishlistUrl}">
				<div class="main-link__label">${wish_lists_text}</div>
			</a>

			<a class="main-link main-link--sub" href="${notification_url}">
				<div class="main-link__label">
					${notifications_text}
					<div class="minium-notification-badge">${notification_count}</div>
				</div>
			</a>

			<#if show_sign_out>
				<a class="main-link main-link--sub" href="${sign_out_url}">
					<div class="main-link__label">${sign_out_text}</div>
				</a>
			</#if>
		</div>

		<#assign
			userImageCssClass = ""
		/>

		<#if notification_count gt 0>
			<#assign
				userImageCssClass = "has-notification"
			/>
		</#if>

		<div class="minium-user-nav__avatar ${userImageCssClass}">
			<@liferay_ui["user-portrait"] user=user />
		</div>

		<div class="minium-user-nav__name">${htmlUtil.escape(user_name)}</div>
	<#else>
		<a class="main-link" href="${sign_in_url}">
			<div class="main-link__label">${sign_in_text}</div>
		</a>
	</#if>
</div>