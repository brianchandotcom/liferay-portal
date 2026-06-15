<#assign logo_url = site_default_url />

<#if !is_signed_in && commerceOrderHttpHelper??>
	<#attempt>
		<#if commerceOrderHttpHelper.isGuestCheckoutEnabled(request)>
			<#assign logo_url = site_default_url + "/catalog" />
		</#if>
	<#recover>
	</#attempt>
</#if>

<div class="minium-sidebar">
	<div class="minium-sidebar__start">
		<div class="minium-logo">
			<a class="${logo_css_class}" href="${logo_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
				<img alt="${logo_description}" class="logo-image-sm" src="${site_logo}" />
			</a>
		</div>
	</div>

	<#if is_signed_in>
		<div class="minium-sidebar__middle">
			<@site_navigation_menu_main default_preferences = freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
		</div>
	</#if>

	<div class="minium-sidebar__end">
		<#include "${full_templates_path}/user_nav.ftl" />
	</div>
</div>