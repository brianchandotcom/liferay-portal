<#include "${templatesPath}/NAVIGATION-MACRO-FTL" />

<#if !entries?has_content>
	<#if themeDisplay.isSignedIn()>
		<div class="alert alert-info">
			<@liferay.language key="there-are-no-menu-items-to-display" />
		</div>
	</#if>
<#else>
	<#assign includeAllChildNavItems = false />

	<#if stringUtil.equals(includedLayouts, "all")>
		<#assign includeAllChildNavItems = true />
	</#if>

	<div aria-label="<@liferay.language key="site-pages" />" class="list-menu">
		<@buildNavigation
			branchNavItems=branchNavItems
			cssClass="layouts"
			displayDepth=displayDepth
			includeAllChildNavItems=includeAllChildNavItems
			navItemLevel=1
			navItems=entries
		/>
	</div>
</#if>