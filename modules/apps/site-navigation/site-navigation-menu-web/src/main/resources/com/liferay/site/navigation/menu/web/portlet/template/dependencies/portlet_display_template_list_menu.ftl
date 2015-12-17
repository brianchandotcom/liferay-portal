<#include "${templatesPath}/NAVIGATION-MACRO-FTL" />

<#if !entries?has_content>
	<#if preview>
		<div class="alert alert-info">
			<@liferay.language key="there-are-no-pages-to-display-for-the-current-page-level" />
		</div>
	</#if>
<#else>
	<#assign includeAllChildNavItems = false />

	<#if includedLayouts == "all">
		<#assign includeAllChildNavItems = true />
	</#if>

	<div aria-label="<@liferay.language key="site-pages" />" class="list-menu">
		<@buildNavigation
			branchNavItems=branchNavItems
			includeAllChildNavItems=includeAllChildNavItems
			navItemLevel=1
			navItems=entries
		/>
	</div>
</#if>