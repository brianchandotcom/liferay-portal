<div class="application-bar application-bar-b2b">
	<div class="navbar-title navbar-text-truncate">
		<#if show_site_name>
				<h1 class="h5">${site_name}</h1>
			<#else>
				<h1 class="h5">B2B Site</h1>
		</#if>
	</div>

	<div class="application-bar-search">
		<@liferay.search default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
	</div>

	<div class="application-bar-secondary">
		<a class="application-bar-account link-brand-underline" href="#">
			<span>
				${currentAccount.getName()}
			</span>
		</a>

		<@liferay.user_personal_bar />
	</div>

	<div class="application-bar-primary">
		<svg class="commerce-icon lexicon-icon lexicon-icon-archive text-light mr-2">
			<use xlink:href="${images_folder}/lexicon/icons.svg#archive" />
		</svg>

		<span class="sticker sticker-light">${cartItemsCount}</span>
	</div>
</div>