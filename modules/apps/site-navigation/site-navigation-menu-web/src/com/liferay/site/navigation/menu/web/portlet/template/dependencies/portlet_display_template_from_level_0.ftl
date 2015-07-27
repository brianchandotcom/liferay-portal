<#include "${templatesPath}/NAVIGATION-MACRO-FTL" />

<#assign rootNavigationItems = navItem.fromLayouts(request, themeDisplay.getLayouts(), null) />

<#if rootNavigationItems?has_content>
	<#assign layoutLevel = 0 />

	<div class="nav-menu nav-menu-style-${bulletStyle}">
		<ul class="layouts level-${layoutLevel}">
			<#list rootNavigationItems as rootNavigationItem>
				<li class="open">
					<a href="${rootNavigationItem.getRegularURL()!""} ">${htmlUtil.escape(rootNavigationItem.getName())}</a>

					<#if rootNavigationItem.isBelongsToNavigationEntries(entries) >
						<@displayChildNavigation childLayoutLevel=(layoutLevel + 1) childNavigationItems=rootNavigationItem.getChildren() includeAllChildEntries=false />
					</#if>
				</li>
			</#list>
		</ul>
	</div>
</#if>