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
				${currentOrganization.getName()}
			</span>
		</a>

		<@liferay.user_personal_bar />
	</div>

	<div class="collapse-hover application-bar-primary" id="cartIcon">
		<svg class="commerce-icon lexicon-icon lexicon-icon-archive text-light mr-2">
			<use xlink:href="${images_folder}/lexicon/icons.svg#archive" />
		</svg>

		<a class="text-light" href="${cartUrl}">
			${orderItemsCount}
		</a>

		<div class="collapse position-anchored">
			<div class="card card-horizontal small">
				<#include "${full_templates_path}/cart.ftl" />
			</div>
		</div>
	</div>
</div>